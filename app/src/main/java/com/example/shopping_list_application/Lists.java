package com.example.shopping_list_application;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

//import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
public class Lists {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "Name")
    public String name;
    @ColumnInfo(name = "Products")
    public ArrayList<String> items;
    @ColumnInfo(name = "Quantity")
    public ArrayList<String> quantities;

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

    public ArrayList getItems() {
        return items;
    }

    public void setItems(ArrayList items) {
        this.items = items;
    }

    public ArrayList getQuantities() {
        return quantities;
    }

    public void setQuantities(ArrayList quantities) {
        this.quantities = quantities;
    }

}
