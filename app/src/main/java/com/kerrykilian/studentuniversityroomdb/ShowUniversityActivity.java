package com.kerrykilian.studentuniversityroomdb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kerrykilian.studentuniversityroomdb.student.Student;
import com.kerrykilian.studentuniversityroomdb.student.StudentAdapter;
import com.kerrykilian.studentuniversityroomdb.student.StudentDao;
import com.kerrykilian.studentuniversityroomdb.university.UniversityRoomDatabase;
import com.kerrykilian.studentuniversityroomdb.university.UniversityViewModel;

import java.util.List;

public class ShowUniversityActivity extends AppCompatActivity {
    private StudentDao studentDao;
    private LiveData<List<Student>> studentsByUniversity;
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;

    UniversityRoomDatabase universityRoomDatabase;
    private UniversityViewModel universityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_university);

        // Get the data passed from MainActivity
        Intent intent = getIntent();
        String universityName = intent.getStringExtra("name");
        String universityLocation = intent.getStringExtra("location");
        boolean universityState = intent.getBooleanExtra("state", false);
        boolean universityApplied = intent.getBooleanExtra("applied", false);
        int universityNumber = intent.getIntExtra("number", 0);

        // Use the universityName to display the details in the activity
        TextView tvUniversityName = findViewById(R.id.universityName);
        TextView tvUniversityLocation = findViewById(R.id.universityLocation);
        TextView tvUniversityState = findViewById(R.id.universityState);
        TextView tvUniversityApplied = findViewById(R.id.universityApplied);
        TextView tvUniversityNumber = findViewById(R.id.universityNumber);
        tvUniversityName.setText(universityName);
        tvUniversityLocation.setText(universityLocation);
        tvUniversityState.setText(universityState ? "Staatlich" : "Privat");
        tvUniversityApplied.setText(universityApplied ? "Angewandt" : "Grundlagen");
        tvUniversityNumber.setText(universityNumber + " Studiengänge");



        universityRoomDatabase = UniversityRoomDatabase.getDatabase(getApplicationContext());
        studentDao = universityRoomDatabase.studentDao();
        studentsByUniversity = studentDao.getStudentsByUniversity(universityName);



        // Set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter = new StudentAdapter();
        recyclerView.setAdapter(studentAdapter);

        // Observe the students and update the RecyclerView adapter accordingly
        studentsByUniversity.observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                studentAdapter.submitList(students);

            }
        });
    }
}