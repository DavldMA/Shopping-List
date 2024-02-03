package com.example.shopping_list_application;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ListsDao {
    @Query("SELECT * FROM lists order by id desc")
    List<Lists> getAll();

    @Insert
    void insert(Lists... list);

    @Delete
    void delete(Lists list);
    @Update
    void update(Lists... list);
}
