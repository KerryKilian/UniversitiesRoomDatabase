package com.kerrykilian.studentuniversityroomdb.university;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kerrykilian.studentuniversityroomdb.student.Student;

import java.util.List;

public class UniversityViewModel extends AndroidViewModel {

    private UniversityRepository mRepository;

    private final LiveData<List<University>> mAllUniversities;

    private final LiveData<List<Student>> mStudentsByUniversity;

    public UniversityViewModel(Application application) {
        super(application);
        mRepository = new UniversityRepository(application);
        mAllUniversities = mRepository.getAllUniversities();
        mStudentsByUniversity = mRepository.getStudentsByUniversity();
    }

    public LiveData<List<University>> getAllUniversities() { return mAllUniversities; }

    public LiveData<List<Student>> getStudentsByUniversity() { return mStudentsByUniversity; }

    public void insertUniversity(University university) { mRepository.insertUniversity(university); }

    public void insertStudent(Student student) {
        mRepository.insertStudent(student);
    }
}