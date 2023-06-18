package com.kerrykilian.studentuniversityroomdb.student;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.kerrykilian.studentuniversityroomdb.university.University;
import com.kerrykilian.studentuniversityroomdb.utils.DateTypeConverter;

import java.util.Date;


@Entity(tableName = "student_table",
        foreignKeys = @ForeignKey(
                entity = University.class,
                parentColumns = "name",
                childColumns = "university"
        ))
@TypeConverters({DateTypeConverter.class})
public class Student {

    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "matrikel")
    private String matrikel;

    @NonNull
    @ColumnInfo(name = "birthday")
    private Date birthday;

    @NonNull
    @ColumnInfo(name = "course")
    private String course;

    @NonNull
    @ColumnInfo(name = "university")
    private String university;

    public Student(@NonNull String name, @NonNull String matrikel, @NonNull Date birthday, @NonNull String course, @NonNull String university) {
        this.name = name;
        this.matrikel = matrikel;
        this.birthday = birthday;
        this.course = course;
        this.university = university;
    }

    public String getName(){return this.name;}
    public String getMatrikel(){return this.matrikel;}
    public Date getBirthday(){return this.birthday;}
    public String getCourse(){return this.course;}
    public String getUniversity(){return this.university;}

}