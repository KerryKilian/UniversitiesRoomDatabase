package com.kerrykilian.studentuniversityroomdb.student;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kerrykilian.studentuniversityroomdb.R;
import com.kerrykilian.studentuniversityroomdb.utils.ImageSaver;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewStudentName;
    private TextView textViewStudentMatrikel;
    private TextView textViewStudentCourse;
    private TextView textViewStudentBirthday;
    private ImageView imageViewPhoto;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewStudentName = itemView.findViewById(R.id.textViewStudentName);
        textViewStudentMatrikel = itemView.findViewById(R.id.textViewStudentMatrikel);
        textViewStudentCourse = itemView.findViewById(R.id.textViewStudentCourse);
        textViewStudentBirthday = itemView.findViewById(R.id.textViewStudentBirthday);
        imageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
    }

    public void bind(Student student) {
        textViewStudentName.setText(student.getName());
        textViewStudentMatrikel.setText(student.getMatrikel());
        textViewStudentCourse.setText(student.getCourse());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        String germanDateString = dateFormat.format(student.getBirthday());
        textViewStudentBirthday.setText(germanDateString);

        Bitmap bitmap = new ImageSaver(imageViewPhoto.getContext()).
                setFileName(student.getMatrikel() + ".jpg").
                setDirectoryName("images").
                load();
        imageViewPhoto.setImageBitmap(bitmap);
    }
}