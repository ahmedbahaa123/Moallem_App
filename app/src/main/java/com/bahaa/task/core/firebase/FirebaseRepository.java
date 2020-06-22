package com.bahaa.task.core.firebase;

import com.bahaa.task.subject.model.SubjectModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import androidx.annotation.NonNull;

public class FirebaseRepository {

    // Interface
    private OnFirestoreTaskComplete onFirestoreTaskComplete;

    // to get ref from my collection in firebase using collection name.
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Query subjectRef = firebaseFirestore.collection("SubjectList");

    public FirebaseRepository(OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    // get data from firebase
    public void getSubjectData() {
        subjectRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // if get data successful then assign result toObject SubjectsModel class
                    onFirestoreTaskComplete.subjectDataAdded(task.getResult().toObjects(SubjectModel.class));
                } else {
                    //get Error
                    onFirestoreTaskComplete.onError(task.getException());
                }

            }
        });
    }

    public interface OnFirestoreTaskComplete {
        void subjectDataAdded(List<SubjectModel> subjectModelList);

        void onError(Exception e);
    }
}
