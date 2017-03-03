package com.garbaciak.todoic;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by SGA on 02.03.2017.
 */

@Table(database = TodoDatabase.class)
public class Task extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String text;

    @Column
    Date dueDate;

    @Column
    Priority priority;

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
