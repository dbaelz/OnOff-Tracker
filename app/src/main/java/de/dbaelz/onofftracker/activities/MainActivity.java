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

package de.dbaelz.onofftracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.dbaelz.onofftracker.OnOffTrackerApplication;
import de.dbaelz.onofftracker.R;
import de.dbaelz.onofftracker.helpers.ActionHelper;
import de.dbaelz.onofftracker.models.Action;
import de.dbaelz.onofftracker.models.CardItem;
import de.dbaelz.onofftracker.services.OnOffCountService;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, OnOffCountService.class);
        startService(serviceIntent);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(new CardItemsAdapter(getCardItems()));
    }

    private List<CardItem> getCardItems() {
        OnOffTrackerApplication app = (OnOffTrackerApplication) getApplication();
        ActionHelper actionHelper = app.getActionHelper();

        ArrayList<CardItem> items = new ArrayList<>(3);
        // Today
        items.add(new CardItem(getString(R.string.cardview_action_title_today),
                actionHelper.countActionsToday(Action.ActionType.SCREENON),
                actionHelper.countActionsToday(Action.ActionType.SCREENOFF),
                actionHelper.countActionsToday(Action.ActionType.UNLOCKED)));
        // Last 7 days
        items.add(new CardItem(getString(R.string.cardview_action_title_lastSevenDays),
                actionHelper.countActionsLastSevenDays(Action.ActionType.SCREENON),
                actionHelper.countActionsLastSevenDays(Action.ActionType.SCREENOFF),
                actionHelper.countActionsLastSevenDays(Action.ActionType.UNLOCKED)));
        // Overall
        items.add(new CardItem(getString(R.string.cardview_action_title_overall),
                actionHelper.countAllActions(Action.ActionType.SCREENON),
                actionHelper.countAllActions(Action.ActionType.SCREENOFF),
                actionHelper.countAllActions(Action.ActionType.UNLOCKED)));
        return items;
    }
}
