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
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import de.dbaelz.onofftracker.OnOffTrackerApplication;
import de.dbaelz.onofftracker.R;
import de.dbaelz.onofftracker.helpers.ActionHelper;
import de.dbaelz.onofftracker.models.Action;

public class ChartActivity extends AppCompatActivity {
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";

    private DateTimeFormatter fmt = DateTimeFormat.shortDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null) {
            DateTime startDate = (DateTime) getIntent().getSerializableExtra(START_DATE);
            DateTime endDate = (DateTime) getIntent().getSerializableExtra(END_DATE);

            BarChart chart = (BarChart) findViewById(R.id.barChart);
            chart.setLogEnabled(true);
            chart.setData(generateData(startDate, endDate));
        }
    }

    private BarData generateData(DateTime startDate, DateTime endDate) {
        OnOffTrackerApplication app = (OnOffTrackerApplication) getApplication();
        ActionHelper actionHelper = app.getActionHelper();

        ArrayList<IBarDataSet> sets = new ArrayList<>();
        ArrayList<String> days = new ArrayList<>();
        ArrayList<BarEntry> entriesScreenOn = new ArrayList<>();
        ArrayList<BarEntry> entriesScreenOff = new ArrayList<>();
        ArrayList<BarEntry> entriesScreenUnlocked = new ArrayList<>();

        DateTime inter = startDate;
        int index = 0;

        // TODO: Fetch data from database is slow: Improve performance of queries and/or add waitscreen while fetching
        while (inter.compareTo(endDate) < 0) {
            days.add(fmt.print(inter));
            entriesScreenOn.add(new BarEntry(actionHelper.countActionsForDate(inter, Action.ActionType.SCREENON), index));
            entriesScreenOff.add(new BarEntry(actionHelper.countActionsForDate(inter, Action.ActionType.SCREENOFF), index));
            entriesScreenUnlocked.add(new BarEntry(actionHelper.countActionsForDate(inter, Action.ActionType.UNLOCKED), index));

            inter = inter.plusDays(1);
            index++;
        }

        BarDataSet screenOn = new BarDataSet(entriesScreenOn, "On");
        screenOn.setColor(ContextCompat.getColor(this, R.color.primary));
        BarDataSet screenOff = new BarDataSet(entriesScreenOff, "Off");
        screenOff.setColor(ContextCompat.getColor(this, R.color.accent));
        BarDataSet unlocked = new BarDataSet(entriesScreenUnlocked, "Unlocked");
        unlocked.setColor(ContextCompat.getColor(this, R.color.primary_text));
        sets.add(screenOn);
        sets.add(screenOff);
        sets.add(unlocked);
        return new BarData(days, sets);
    }
}
