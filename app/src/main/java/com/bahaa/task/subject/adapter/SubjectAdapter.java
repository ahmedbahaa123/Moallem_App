package com.bahaa.task.subject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahaa.task.R;
import com.bahaa.task.subject.model.SubjectModel;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<SubjectModel> subjectModels;
    private onSubjectItemClicked onSubjectItemClicked;

    public SubjectAdapter(onSubjectItemClicked onSubjectItemClicked) {
        this.onSubjectItemClicked = onSubjectItemClicked;
    }

    public void setSubjectModels(List<SubjectModel> subjectModels) {
        this.subjectModels = subjectModels;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {

        holder.subjectName.setText(subjectModels.get(position).getName());

        String imageUrl = subjectModels.get(position).getImage();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .into(holder.subjectImage);
    }

    @Override
    public int getItemCount() {
        if (subjectModels == null) {
            return 0;
        } else {
            return subjectModels.size();
        }

    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView subjectName;
        private ImageView subjectImage;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectName = itemView.findViewById(R.id.subject_name);
            subjectImage = itemView.findViewById(R.id.subject_image);

            subjectImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onSubjectItemClicked.onSubjectClicked(getAdapterPosition());
        }
    }

    public interface onSubjectItemClicked {
        void onSubjectClicked(int position);
    }

}
