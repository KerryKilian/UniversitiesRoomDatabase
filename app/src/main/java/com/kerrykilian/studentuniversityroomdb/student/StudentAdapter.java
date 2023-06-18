package com.kerrykilian.studentuniversityroomdb.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.kerrykilian.studentuniversityroomdb.R;

public class StudentAdapter extends ListAdapter<Student, StudentViewHolder> {

    public StudentAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getMatrikel().equals(newItem.getMatrikel());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                boolean [] comparisons = {false, false, false, false, false};
                comparisons[0] = oldItem.getName().equals(newItem.getName());
                comparisons[1] = oldItem.getMatrikel().equals(newItem.getMatrikel());
                comparisons[2] = oldItem.getBirthday().equals(newItem.getBirthday());
                comparisons[3] = oldItem.getCourse().equals(newItem.getCourse());
                comparisons[4] = oldItem.getUniversity().equals(newItem.getUniversity());
                for (boolean comparison : comparisons) {
                    if (comparison == false) {
                        return false;
                    }
                }
                return true;
            }
        });
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = getItem(position);
        holder.bind(student);
    }
}