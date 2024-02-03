package com.example.shopping_list_application;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

//import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
public class Lists implements Parcelable {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "Name")
    public String name;
    @ColumnInfo(name = "Products")
    public ArrayList<String> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }


    public Lists(String name, ArrayList<String> items) {
        this.name = name;
        this.items = items;
    }

    protected Lists(Parcel in) {
        id = in.readInt();
        name = in.readString();
        items = in.createStringArrayList();
    }

    public static final Creator<Lists> CREATOR = new Creator<Lists>() {
        @Override
        public Lists createFromParcel(Parcel in) {
            return new Lists(in);
        }
        @Override
        public Lists[] newArray(int size) {
            return new Lists[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeStringList(items);
    }
}
