package com.bahaa.task.subject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bahaa.task.R;
import com.bahaa.task.core.utils.Utils;
import com.bahaa.task.subject.adapter.SubjectAdapter;
import com.bahaa.task.subject.viewmodel.SubjectViewModel;
import com.bahaa.task.subject.model.SubjectModel;
import com.bahaa.task.video.activity.VideoActivity;
import com.bahaa.task.video.adapter.VideoAdapter;
import com.bahaa.task.video.model.VideoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SubjectAdapter.onSubjectItemClicked, VideoAdapter.onVideoItemClicked {

    private RecyclerView recyclerView;
    private RecyclerView videoRecycler;
    private SubjectAdapter adapter;
    private VideoAdapter videoAdapter;
    private SubjectViewModel subjectViewModel;
    private FirebaseFirestore firebaseFirestore;

    private List<VideoModel> allVideosList = new ArrayList<>();
    private List<SubjectModel> subjectsModelList;
    private String subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        if (Utils.isNetworkConnected(this)) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            recyclerView = findViewById(R.id.subject_recycler);
            videoRecycler = findViewById(R.id.video_recycler);
            adapter = new SubjectAdapter(this);
            videoAdapter = new VideoAdapter(this);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

            LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            videoRecycler.setLayoutManager(layoutManager2);
            videoRecycler.setHasFixedSize(true);
            videoRecycler.setAdapter(videoAdapter);

            subjectViewModel = ViewModelProviders.of(this).get(SubjectViewModel.class);

            subjectViewModel.getSubjectMutableLiveData().observe(this, new Observer<List<SubjectModel>>() {
                @Override
                public void onChanged(List<SubjectModel> subjectsModels) {

                    adapter.setSubjectModels(subjectsModels);
                    adapter.notifyDataSetChanged();

                    subjectsModelList = subjectsModels;
                }
            });

        } else {
            Toast.makeText(this, getString(R.string.internet_check), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSubjectClicked(int position) {
        subjectId = subjectsModelList.get(position).getSubjectId();

        firebaseFirestore.collection("SubjectList").
                document(subjectId).collection("Videos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                allVideosList = task.getResult().toObjects(VideoModel.class);
                videoAdapter.setVideosModels(allVideosList);
                videoAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onVideoClicked(int position) {
        String url = allVideosList.get(position).getUrl();
        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }


}
