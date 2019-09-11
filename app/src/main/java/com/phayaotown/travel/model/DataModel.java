package com.phayaotown.travel.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataModel {

    DatabaseReference modelRef = FirebaseDatabase.getInstance().getReference("ShowAll");

    @SerializedName("data")
    private List<ShowAll> detailModels;


    public List<ShowAll> getDetailModels(){ return detailModels;}

    public void setDetailModels(List<ShowAll> detailModels) {
        this.detailModels = detailModels;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataModel dataModel = (DataModel) o;
        return detailModels != null ? detailModels.equals(dataModel.detailModels) : dataModel.detailModels == null;
    }

    @Override
    public int hashCode() {
        return detailModels != null ? detailModels.hashCode() : 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "DataModel{" +
                "detailModels=" +detailModels +
                '}';
    }
}
