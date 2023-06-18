package com.kerrykilian.studentuniversityroomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kerrykilian.studentuniversityroomdb.university.UniversityViewModel;


public class NewStudentActivity extends AppCompatActivity {

    String selectedItem = "";
    private EditText editBirthday;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap photo;
    ImageView imageView;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        EditText editName = findViewById(R.id.editName);
        EditText editMatrikel = findViewById(R.id.editMatrikel);
        editBirthday = findViewById(R.id.editBirthday);
        EditText editCourse = findViewById(R.id.editCourse);
        imageView = findViewById(R.id.personImageView);

        editBirthday.addTextChangedListener(new DateTextWatcher());

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (
                        TextUtils.isEmpty(editName.getText()) ||
                        TextUtils.isEmpty(editMatrikel.getText()) ||
                        TextUtils.isEmpty(editBirthday.getText()) ||
                        TextUtils.isEmpty(editCourse.getText()) ||
                                photo == null
                ) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String date = editBirthday.getText().toString();
                    String name = editName.getText().toString();
                    String matrikel = editMatrikel.getText().toString();
                    String course = editCourse.getText().toString();
                    replyIntent.putExtra("name", name);
                    replyIntent.putExtra("matrikel", matrikel);
                    replyIntent.putExtra("birthday", date);
                    replyIntent.putExtra("course", course);
                    replyIntent.putExtra("university", selectedItem);
                    replyIntent.putExtra("photo", photo);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });


        // Spinner
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        UniversityViewModel universityViewModel = new ViewModelProvider(this).get(UniversityViewModel.class);
        universityViewModel.getAllUniversities().observe(this, universities -> {
            universities.forEach(university -> {
                adapter.add(university.getName());
            });
        });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selected item
                selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("KILIANBRINKNERDEBUG", "imageview clicked");
                requestCameraPermission();

            }
        });




    }


    /**
     * Opens the camera if Permission is granted
     */
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now proceed with camera-related tasks
                openCamera();
            } else {
                Toast.makeText(this, "Die Berechtigung Kamera muss aktiviert sein, damit die App funktioniert", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkCameraPermission() {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE);
    }



    /**
     * get image from camera intent
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            imageView.setImageBitmap(photo);
        }
    }

    private class DateTextWatcher implements TextWatcher {

        private static final String DATE_PATTERN = "\\d{2}.\\d{2}.\\d{4}";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String input = s.toString().trim();
            if (!input.matches(DATE_PATTERN)) {
                editBirthday.setError("Please enter a date in the format DD-MM-YYYY");
            } else {
                editBirthday.setError(null);
            }
        }
    }


}