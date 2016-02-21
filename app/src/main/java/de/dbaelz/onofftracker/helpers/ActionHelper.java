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
import com.j256.ormlite.stmt.PreparedQuery;

import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.List;

import de.dbaelz.onofftracker.models.Action;
import de.dbaelz.onofftracker.models.ActionsInterval;

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

    public DateTime getFirstDate() {
        try {
            PreparedQuery<Action> date = actionDao.queryBuilder().orderBy("date", true).limit(1l).prepare();
            List<Action> result = actionDao.query(date);
            if (result.size() == 1) {
                return new DateTime(result.get(0).getDate());
            } else {
                return DateTime.now();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return DateTime.now();
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

    public ActionsInterval getActionsIntervalToday(String title) {
        DateTime today = DateTime.now();
        return new ActionsInterval(
                title,
                today.withTimeAtStartOfDay(),
                today,
                countActionsToday(Action.ActionType.SCREENON),
                countActionsToday(Action.ActionType.SCREENOFF),
                countActionsToday(Action.ActionType.UNLOCKED));
    }

    public ActionsInterval getActionsIntervalLastSevenDays(String title) {
        DateTime today = DateTime.now();
        return new ActionsInterval(
                title,
                today.minusDays(6).withTimeAtStartOfDay(),
                today,
                countActionsLastSevenDays(Action.ActionType.SCREENON),
                countActionsLastSevenDays(Action.ActionType.SCREENOFF),
                countActionsLastSevenDays(Action.ActionType.UNLOCKED));
    }

    public ActionsInterval getActionsIntervalOverall(String title) {
        DateTime today = DateTime.now();
        return new ActionsInterval(
                title,
                getFirstDate(),
                today,
                countAllActions(Action.ActionType.SCREENON),
                countAllActions(Action.ActionType.SCREENOFF),
                countAllActions(Action.ActionType.UNLOCKED));
    }
}
