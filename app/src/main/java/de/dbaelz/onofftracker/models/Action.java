package de.dbaelz.onofftracker.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.Date;

@DatabaseTable(tableName = "actions")
public class Action {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.ENUM_INTEGER)
    private ActionType type;

    @DatabaseField(dataType = DataType.DATE)
    Date date;

    Action() {
    }

    public Action(ActionType type) {
        this.type = type;
        this.date = Calendar.getInstance().getTime();
    }

    public enum ActionType {
        SCREENON, SCREENOFF, UNLOCKED
    }
}
