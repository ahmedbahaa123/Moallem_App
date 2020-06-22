package com.bahaa.task.subject.model;


import com.google.firebase.firestore.DocumentId;
import com.google.gson.annotations.SerializedName;

public class SubjectModel {

    @DocumentId
    @SerializedName("subject_id")
    private String subjectId;
    private String name, image;

    public SubjectModel() {
    }

    public SubjectModel(String subjectId, String name, String image) {
        this.subjectId = subjectId;
        this.name = name;
        this.image = image;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
