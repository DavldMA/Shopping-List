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
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Name")
    public String name;
    @ColumnInfo(name = "Products")
    public ArrayList<String> products;

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
        return products;
    }

    public void setItems(ArrayList<String> items) {
        this.products = items;
    }


    public Lists(String name, ArrayList<String> products) {
        this.name = name;
        this.products = products;
    }

    protected Lists(Parcel in) {
        id = in.readInt();
        name = in.readString();
        products = in.createStringArrayList();
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
        dest.writeStringList(products);
    }
}
