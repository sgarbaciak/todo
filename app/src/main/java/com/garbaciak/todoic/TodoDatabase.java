package com.garbaciak.todoic;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by SGA on 02.03.2017.
 */

@Database(name = TodoDatabase.NAME, version = TodoDatabase.VERSION)
public class TodoDatabase {
    public static final String NAME = "TodoDataBase";

    public static final int VERSION = 1;
}
