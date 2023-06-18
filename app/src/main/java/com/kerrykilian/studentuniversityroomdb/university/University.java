package com.kerrykilian.studentuniversityroomdb.university;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "university_table")
public class University {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "location")
    private String location;

    @NonNull
    @ColumnInfo(name = "state")
    private boolean state;

    @NonNull
    @ColumnInfo(name = "applied")
    private boolean applied;

    @NonNull
    @ColumnInfo(name = "number")
    private int number;

    public University(@NonNull String name, @NonNull String location, @NonNull boolean state, @NonNull boolean applied, @NonNull int number) {
        this.name = name;
        this.location = location;
        this.state = state;
        this.applied = applied;
        this.number = number;
    }

    public String getName(){return this.name;}
    public String getLocation(){return this.location;}
    public boolean getState(){return this.state;}
    public boolean getApplied(){return this.applied;}
    public int getNumber(){return this.number;}

}