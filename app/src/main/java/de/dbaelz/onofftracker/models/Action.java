package de.dbaelz.onofftracker.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

@DatabaseTable(tableName = "actions")
public class Action {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.ENUM_INTEGER)
    private ActionType type;

    @DatabaseField(dataType = DataType.TIME_STAMP)
    Timestamp time;

    Action() {
    }

    public Action(ActionType type) {
        this.type = type;
    }

    public enum ActionType {
        SCREEN_ON, SCREEN_OFF, UNLOCKED;
    }
}
