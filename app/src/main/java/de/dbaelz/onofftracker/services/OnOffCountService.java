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

package de.dbaelz.onofftracker.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import de.dbaelz.onofftracker.OnOffTrackerApplication;
import de.dbaelz.onofftracker.helpers.ActionHelper;
import de.dbaelz.onofftracker.models.Action;

public class OnOffCountService extends Service {

    @Inject
    ActionHelper actionHelper;

    private final String ACTION_SCREEN_ON = "android.intent.action.SCREEN_ON";
    private final String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";
    private final String ACTION_USER_PRESENT = "android.intent.action.USER_PRESENT";

    private BroadcastReceiver receiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Action.ActionType type;
                if (intent.getAction().equals(ACTION_SCREEN_ON)) {
                    type = Action.ActionType.SCREENON;
                } else if (intent.getAction().equals(ACTION_SCREEN_OFF)) {
                    type = Action.ActionType.SCREENOFF;
                } else {
                    type = Action.ActionType.UNLOCKED;
                }
                actionHelper.addAction(type);
            }
        };

        IntentFilter filterScreenOn = new IntentFilter(ACTION_SCREEN_ON);
        IntentFilter filterScreenOff = new IntentFilter(ACTION_SCREEN_OFF);
        IntentFilter filterUserPresent = new IntentFilter(ACTION_USER_PRESENT);
        registerReceiver(receiver, filterScreenOn);
        registerReceiver(receiver, filterScreenOff);
        registerReceiver(receiver, filterUserPresent);

        super.onCreate();
    }
}
