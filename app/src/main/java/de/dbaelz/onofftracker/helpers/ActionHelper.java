package de.dbaelz.onofftracker.helpers;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

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

    public long countTodaysActions(Action.ActionType type) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        try {
            return actionDao.queryBuilder().where()
                    .eq(TABLE_ACTIONTYPE, type)
                    .and()
                    .between(TABLE_DATE, calendar.getTime(), currentDate)
                    .countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
