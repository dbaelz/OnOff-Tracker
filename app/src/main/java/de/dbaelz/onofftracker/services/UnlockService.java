package de.dbaelz.onofftracker.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import de.dbaelz.onofftracker.OnOffTrackerApplication;
import de.dbaelz.onofftracker.models.Action;

public class UnlockService extends Service {
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
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                OnOffTrackerApplication app = (OnOffTrackerApplication) getApplication();
                Action.ActionType type;
                if (intent.getAction().equals(ACTION_SCREEN_ON)) {
                    type = Action.ActionType.SCREENON;
                } else if (intent.getAction().equals(ACTION_SCREEN_OFF)) {
                    type = Action.ActionType.SCREENOFF;
                } else {
                    type = Action.ActionType.UNLOCKED;
                }
                app.getActionHelper().addAction(type);
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
