package com.kerrykilian.studentuniversityroomdb.university;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kerrykilian.studentuniversityroomdb.R;

class UniversityViewHolder extends RecyclerView.ViewHolder {
    private final TextView universityItemView;
    private UniversityListAdapter.OnItemClickListener listener;

    private UniversityViewHolder(View itemView, UniversityListAdapter.OnItemClickListener listener) {
        super(itemView);
        universityItemView = itemView.findViewById(R.id.textView);
        this.listener = listener;
    }

    public void bind(String text) {
        universityItemView.setText(text);
    }

    static UniversityViewHolder create(ViewGroup parent, UniversityListAdapter.OnItemClickListener listener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new UniversityViewHolder(view, listener);
    }

    public void onItemClick(University university) {
        listener.onItemClick(university);
    }
}
