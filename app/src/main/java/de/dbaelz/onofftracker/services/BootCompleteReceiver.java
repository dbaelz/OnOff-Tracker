package de.dbaelz.onofftracker.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, OnOffCountService.class);
        context.startService(serviceIntent);
    }
}
