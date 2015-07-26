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
