package com.bahaa.task.subject.viewmodel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bahaa.task.core.firebase.FirebaseRepository;
import com.bahaa.task.subject.model.SubjectModel;

public class SubjectViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<SubjectModel>> subjectMutableLiveData = new MutableLiveData<>();

    public LiveData<List<SubjectModel>> getSubjectMutableLiveData() {
        return subjectMutableLiveData;
    }

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public SubjectViewModel() {
        firebaseRepository.getSubjectData();
    }

    @Override
    public void subjectDataAdded(List<SubjectModel> subjectModelList) {
        subjectMutableLiveData.setValue(subjectModelList);
    }

    @Override
    public void onError(Exception e) {
    }

}
