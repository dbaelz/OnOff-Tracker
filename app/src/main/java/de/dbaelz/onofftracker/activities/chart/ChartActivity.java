/*
 * Copyright 2016 Daniel Bälz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dbaelz.onofftracker.activities.chart;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import de.dbaelz.onofftracker.R;
import de.dbaelz.onofftracker.helpers.ActionHelper;
import de.dbaelz.onofftracker.models.Action;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChartActivity extends AppCompatActivity {
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";

    @Inject
    ActionHelper actionHelper;

    private DateTimeFormatter fmt = DateTimeFormat.shortDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final BarChart chart = findViewById(R.id.barChart);
        chart.setDescription("");

        if (getIntent() != null) {
            final DateTime startDate = (DateTime) getIntent().getSerializableExtra(START_DATE);
            final DateTime endDate = (DateTime) getIntent().getSerializableExtra(END_DATE);

            chart.setNoDataText(getString(R.string.chart_loading_data));

            Observable.create(new Observable.OnSubscribe<BarData>() {
                @Override
                public void call(Subscriber<? super BarData> subscriber) {
                    subscriber.onNext(loadData(startDate, endDate));
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BarData>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            chart.setNoDataText(getString(R.string.chart_error_loading));
                        }

                        @Override
                        public void onNext(BarData barData) {
                            chart.setData(barData);
                            chart.invalidate();
                            onCompleted();
                        }
                    });
        } else {
            chart.setNoDataText(getString(R.string.chart_no_data));
        }
    }

    private BarData loadData(DateTime startDate, DateTime endDate) {
        ArrayList<IBarDataSet> sets = new ArrayList<>();
        ArrayList<String> days = new ArrayList<>();
        ArrayList<BarEntry> entriesScreenOn = new ArrayList<>();
        ArrayList<BarEntry> entriesScreenOff = new ArrayList<>();
        ArrayList<BarEntry> entriesScreenUnlocked = new ArrayList<>();

        DateTime inter = startDate;
        int index = 0;

        while (inter.compareTo(endDate) < 0) {
            days.add(fmt.print(inter));
            entriesScreenOn.add(new BarEntry(actionHelper.countActionsForDate(inter, Action.ActionType.SCREENON), index));
            entriesScreenOff.add(new BarEntry(actionHelper.countActionsForDate(inter, Action.ActionType.SCREENOFF), index));
            entriesScreenUnlocked.add(new BarEntry(actionHelper.countActionsForDate(inter, Action.ActionType.UNLOCKED), index));

            inter = inter.plusDays(1);
            index++;
        }

        BarDataSet screenOn = new BarDataSet(entriesScreenOn, getString(R.string.chart_action_on));
        screenOn.setColor(ContextCompat.getColor(this, R.color.primary));
        BarDataSet screenOff = new BarDataSet(entriesScreenOff, getString(R.string.chart_action_off));
        screenOff.setColor(ContextCompat.getColor(this, R.color.primary_light));
        BarDataSet unlocked = new BarDataSet(entriesScreenUnlocked, getString(R.string.chart_action_unlocked));
        unlocked.setColor(ContextCompat.getColor(this, R.color.primary_text));
        sets.add(screenOn);
        sets.add(screenOff);
        sets.add(unlocked);
        BarData barData = new BarData(days, sets);
        barData.setValueFormatter(new LargeValueFormatter());
        return barData;
    }
}
