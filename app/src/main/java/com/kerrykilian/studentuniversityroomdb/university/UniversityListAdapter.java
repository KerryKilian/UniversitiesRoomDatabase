package com.kerrykilian.studentuniversityroomdb.university;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class UniversityListAdapter extends ListAdapter<University, UniversityViewHolder> {

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(University university);
    }

    public UniversityListAdapter(@NonNull DiffUtil.ItemCallback<University> diffCallback, OnItemClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @Override
    public UniversityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UniversityViewHolder.create(parent, listener);
    }

    @Override
    public void onBindViewHolder(UniversityViewHolder holder, int position) {
        University current = getItem(position);
        holder.bind(current.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(current);
            }
        });
    }

    public static class UniversityDiff extends DiffUtil.ItemCallback<University> {

        @Override
        public boolean areItemsTheSame(@NonNull University oldItem, @NonNull University newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull University oldItem, @NonNull University newItem) {
            boolean [] comparisons = {false, false, false, false, false};
            comparisons[0] = oldItem.getName().equals(newItem.getName());
            comparisons[1] = oldItem.getLocation().equals(newItem.getLocation());
            comparisons[2] = oldItem.getState() == newItem.getState();
            comparisons[3] = oldItem.getApplied() == newItem.getApplied();
            comparisons[4] = oldItem.getNumber() == newItem.getNumber();
            for (boolean comparison : comparisons) {
                if (comparison == false) {
                    return false;
                }
            }
            return true;
        }
    }
}