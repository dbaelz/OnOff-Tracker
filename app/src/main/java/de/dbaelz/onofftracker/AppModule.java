package de.dbaelz.onofftracker;

import android.app.Application;
import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.dbaelz.onofftracker.helpers.ActionHelper;
import de.dbaelz.onofftracker.helpers.DatabaseHelper;

@Module
public class AppModule {
    @Provides
    @Singleton
    public Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper(Context context) {
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    @Provides
    @Singleton
    public ActionHelper provideActionHelper(DatabaseHelper databaseHelper) {
        return ActionHelper.getInstance(databaseHelper.getActionDao());
    }
}
