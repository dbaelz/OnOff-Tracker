package de.dbaelz.onofftracker;

import android.app.Application;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import de.dbaelz.onofftracker.helpers.ActionHelper;
import de.dbaelz.onofftracker.helpers.DatabaseHelper;

public class OnOffTrackerApplication extends Application {
    private DatabaseHelper databaseHelper;
    private ActionHelper actionHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        actionHelper = ActionHelper.getInstance(databaseHelper.getActionDao());
    }


    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public ActionHelper getActionHelper() {
        return actionHelper;
    }
}
