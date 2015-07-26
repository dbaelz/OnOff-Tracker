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
