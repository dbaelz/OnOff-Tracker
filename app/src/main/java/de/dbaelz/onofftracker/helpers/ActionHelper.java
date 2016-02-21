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

package de.dbaelz.onofftracker.helpers;

import com.j256.ormlite.dao.Dao;

import org.joda.time.DateTime;

import java.sql.SQLException;

import de.dbaelz.onofftracker.models.Action;

public class ActionHelper {
    private final String TABLE_ACTIONTYPE = "type";
    private final String TABLE_DATE = "date";

    private static ActionHelper instance;
    private final Dao<Action, Integer> actionDao;

    public static ActionHelper getInstance(Dao<Action, Integer> actionDao) {
        if (instance == null) {
            instance = new ActionHelper(actionDao);
        }
        return instance;
    }

    public ActionHelper(Dao<Action, Integer> actionDao) {
        this.actionDao = actionDao;
    }

    public void addAction(Action.ActionType type) {
        try {
            actionDao.create(new Action(type));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long countAllActions(Action.ActionType type) {
        try {
            return actionDao.queryBuilder().where().eq(TABLE_ACTIONTYPE, type).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long countActionsBetween(DateTime startDate, DateTime endDate, Action.ActionType type) {
        try {
            return actionDao.queryBuilder().where()
                    .eq(TABLE_ACTIONTYPE, type)
                    .and()
                    .between(TABLE_DATE, startDate.toDate(), endDate.toDate())
                    .countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long countActionsLastSevenDays(Action.ActionType type) {
        DateTime today = DateTime.now();
        return countActionsBetween(today.minusDays(6).withTimeAtStartOfDay(), today, type);
    }

    public long countActionsToday(Action.ActionType type) {
        DateTime today = DateTime.now();
        return countActionsBetween(today.withTimeAtStartOfDay(), today, type);
    }
}
