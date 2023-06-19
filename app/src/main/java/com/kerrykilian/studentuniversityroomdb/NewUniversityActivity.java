package com.kerrykilian.studentuniversityroomdb;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class NewUniversityActivity extends AppCompatActivity {

    boolean state = true;
    boolean applied = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_university);

        ChipGroup chipGroup1 = findViewById(R.id.chipGroup1);
        ChipGroup chipGroup2 = findViewById(R.id.chipGroup2);
        Chip chipState = findViewById(R.id.chipState);
        Chip chipPrivate = findViewById(R.id.chipPrivate);
        Chip chipApplied = findViewById(R.id.chipApplied);
        Chip chipTheory = findViewById(R.id.chipTheory);
        Button buttonSave = findViewById(R.id.buttonSave);
        EditText editName = findViewById(R.id.editName);
        EditText editLocation = findViewById(R.id.editLocation);
        EditText editNumber = findViewById(R.id.editNumber);


        int colorPrimary = ContextCompat.getColor(this, R.color.orange_medium);
        int colorGrey = ContextCompat.getColor(this, R.color.grey);
        chipPrivate.setChipBackgroundColor(ColorStateList.valueOf(colorGrey));
        chipTheory.setChipBackgroundColor(ColorStateList.valueOf(colorGrey));
        chipGroup1.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId == chipState.getId()) {
                    chipPrivate.setChecked(false); // Deselect Option 2 if selected
                    chipState.setChipBackgroundColor(ColorStateList.valueOf(colorPrimary));
                    chipPrivate.setChipBackgroundColor(ColorStateList.valueOf(colorGrey));
                    state = true;
                } else if (checkedId == chipPrivate.getId()) {
                    chipState.setChecked(false); // Deselect Option 1 if selected
                    chipPrivate.setChipBackgroundColor(ColorStateList.valueOf(colorPrimary));
                    chipState.setChipBackgroundColor(ColorStateList.valueOf(colorGrey));
                    state = false;
                }
            }
        });

        chipGroup2.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId == chipApplied.getId()) {
                    chipTheory.setChecked(false); // Deselect Option 2 if selected
                    chipApplied.setChipBackgroundColor(ColorStateList.valueOf(colorPrimary));
                    chipTheory.setChipBackgroundColor(ColorStateList.valueOf(colorGrey));
                    applied = true;
                } else if (checkedId == chipTheory.getId()) {
                    chipApplied.setChecked(false); // Deselect Option 1 if selected
                    chipTheory.setChipBackgroundColor(ColorStateList.valueOf(colorPrimary));
                    chipApplied.setChipBackgroundColor(ColorStateList.valueOf(colorGrey));
                    applied = false;
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editName.getText()) || TextUtils.isEmpty(editLocation.getText()) || TextUtils.isEmpty(editNumber.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = editName.getText().toString();
                    String location = editLocation.getText().toString();
                    int number = Integer.parseInt(editNumber.getText().toString());
                    replyIntent.putExtra("name", name);
                    replyIntent.putExtra("location", location);
                    replyIntent.putExtra("state", state);
                    replyIntent.putExtra("applied", applied);
                    replyIntent.putExtra("number", number);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}