package de.dbaelz.onofftracker.helpers;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import de.dbaelz.onofftracker.models.Action;

public class ActionHelper {
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

    public long countActions(Action.ActionType type) {
        try {
            return actionDao.queryBuilder().where().eq("type", type).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
