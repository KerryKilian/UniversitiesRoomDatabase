package com.kerrykilian.studentuniversityroomdb.student;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Student student);

    @Query("DELETE FROM student_table")
    void deleteAll();

    @Query("SELECT * FROM student_table ORDER BY name ASC")
    LiveData<List<Student>> getAlphabetizedStudents();

    @Query("SELECT * FROM student_table WHERE university = :universityName ORDER BY name ASC")
    LiveData<List<Student>> getStudentsByUniversity(String universityName);
}