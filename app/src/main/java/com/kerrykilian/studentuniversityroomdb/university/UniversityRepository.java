package com.kerrykilian.studentuniversityroomdb.university;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kerrykilian.studentuniversityroomdb.student.Student;
import com.kerrykilian.studentuniversityroomdb.student.StudentDao;

import java.util.List;

public class UniversityRepository {

    private UniversityDao mUniversityDao;
    private StudentDao mStudentDao;
    private LiveData<List<University>> mAlluniversities;
    private LiveData<List<Student>> mStudentsByUniversity;
    private LiveData<List<Student>> mAllStudents;
    private String universityName = "";

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    UniversityRepository(Application application) {
        UniversityRoomDatabase db = UniversityRoomDatabase.getDatabase(application);
        mUniversityDao = db.universityDao();
        mAlluniversities = mUniversityDao.getAlphabetizedUniversities();
        mStudentDao = db.studentDao();
        mAllStudents = mStudentDao.getAlphabetizedStudents();
        mStudentsByUniversity = mStudentDao.getStudentsByUniversity(universityName);
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<University>> getAllUniversities() {
        return mAlluniversities;
    }

    LiveData<List<Student>> getStudentsByUniversity() {
        return mStudentsByUniversity;
    }

    public void setStudentsByUniversity(String universityName) {
        this.universityName = universityName;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insertUniversity(University university) {
        UniversityRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUniversityDao.insert(university);
        });
    }

    void insertStudent(Student student) {
        UniversityRoomDatabase.databaseWriteExecutor.execute(() -> {
            mStudentDao.insert(student);
        });
    }
}