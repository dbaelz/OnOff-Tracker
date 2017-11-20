/*
 * Copyright 2015 Daniel Bälz
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

package de.dbaelz.onofftracker.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import de.dbaelz.onofftracker.R;
import de.dbaelz.onofftracker.helpers.ActionHelper;
import de.dbaelz.onofftracker.models.ActionsInterval;
import de.dbaelz.onofftracker.services.OnOffCountService;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Inject
    ActionHelper actionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, OnOffCountService.class);
        startService(serviceIntent);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(new CardItemAdapter(getCardItems(), this));
    }

    private List<ActionsInterval> getCardItems() {
        ArrayList<ActionsInterval> items = new ArrayList<>(3);
        items.add(actionHelper.getActionsIntervalToday(getString(R.string.cardview_action_title_today)));
        items.add(actionHelper.getActionsIntervalLastSevenDays(getString(R.string.cardview_action_title_lastSevenDays)));
        items.add(actionHelper.getActionsIntervalOverall(getString(R.string.cardview_action_title_overall)));
        return items;
    }
}
