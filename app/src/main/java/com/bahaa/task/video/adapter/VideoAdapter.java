package com.bahaa.task.video.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bahaa.task.R;
import com.bahaa.task.video.model.VideoModel;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<VideoModel> videosModels;
    private onVideoItemClicked onVideoItemClicked;

    public VideoAdapter(VideoAdapter.onVideoItemClicked onVideoItemClicked) {
        this.onVideoItemClicked = onVideoItemClicked;
    }

    public void setVideosModels(List<VideoModel> videosModels) {
        this.videosModels = videosModels;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        String imageUrl = videosModels.get(position).getImage();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .into(holder.videoImage);
    }

    @Override
    public int getItemCount() {
        if (videosModels == null) {
            return 0;
        } else {
            return videosModels.size();
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView videoImage;
        private ImageButton playBtn;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoImage = itemView.findViewById(R.id.video_image);
            playBtn = itemView.findViewById(R.id.play_icon);

            playBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onVideoItemClicked.onVideoClicked(getAdapterPosition());
        }
    }

    public interface onVideoItemClicked {
        void onVideoClicked(int position);
    }
}
