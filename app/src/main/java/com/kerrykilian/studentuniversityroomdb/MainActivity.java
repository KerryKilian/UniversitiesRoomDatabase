package com.kerrykilian.studentuniversityroomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kerrykilian.studentuniversityroomdb.student.Student;
import com.kerrykilian.studentuniversityroomdb.university.University;
import com.kerrykilian.studentuniversityroomdb.university.UniversityListAdapter;
import com.kerrykilian.studentuniversityroomdb.university.UniversityViewModel;
import com.kerrykilian.studentuniversityroomdb.utils.ImageSaver;
import com.kerrykilian.studentuniversityroomdb.utils.ImageStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements UniversityListAdapter.OnItemClickListener {
    private UniversityViewModel universityViewModel;

    public static final int NEW_UNIVERSITY_ACTIVITY_REQUEST_CODE = 1;
    public static final int NEW_STUDENT_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton buttonAddUni = findViewById(R.id.buttonAddUni);
        buttonAddUni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewUniversityActivity.class);
                startActivityForResult(intent, NEW_UNIVERSITY_ACTIVITY_REQUEST_CODE);
            }
        });

        ImageButton buttonAddStudent = findViewById(R.id.buttonAddStudent);
        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewStudentActivity.class);
                startActivityForResult(intent, NEW_STUDENT_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final UniversityListAdapter adapter = new UniversityListAdapter(new UniversityListAdapter.UniversityDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ViewModel
        universityViewModel = new ViewModelProvider(this).get(UniversityViewModel.class);


        // Observe the LiveData and update the adapter when the data changes
        universityViewModel.getAllUniversities().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });




        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_UNIVERSITY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            University university = new University(
                    data.getStringExtra("name"),
                    data.getStringExtra("location"),
                    data.getBooleanExtra("state", true),
                    data.getBooleanExtra("applied", false),
                    data.getIntExtra("number", 1)
            );
            universityViewModel.insertUniversity(university);
        } else if (requestCode == NEW_STUDENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // get Birthday
            String pattern = "dd.MM.yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = null;
            try {
                date = sdf.parse(data.getStringExtra("birthday"));
            } catch (ParseException e) {
                Toast.makeText(MainActivity.this, "Birthday must be in format: dd.MM.yyyy", Toast.LENGTH_LONG).show();
            }

            // get Image
            Bitmap capturedImage = data.getParcelableExtra("photo");
            if (capturedImage != null) {
                new ImageSaver(this).
                        setFileName(data.getStringExtra("matrikel") + ".jpg").
                        setDirectoryName("images").
                        save(data.getParcelableExtra("photo"));
//                ImageStorage.saveImageToStorage(data.getParcelableExtra("photo"), data.getStringExtra("matrikel"), this);


            }

            Student student = new Student(
                    data.getStringExtra("name"),
                    data.getStringExtra("matrikel"),
                    date,
                    data.getStringExtra("course"),
                    data.getStringExtra("university")
            );
            universityViewModel.insertStudent(student);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(University university) {
        // Handle item click event here
        Intent intent = new Intent(MainActivity.this, ShowUniversityActivity.class);
        intent.putExtra("name", university.getName());
        intent.putExtra("location", university.getLocation());
        intent.putExtra("state", university.getState());
        intent.putExtra("applied", university.getApplied());
        intent.putExtra("number", university.getNumber());
        // Add other data as needed
        startActivity(intent);
    }
}