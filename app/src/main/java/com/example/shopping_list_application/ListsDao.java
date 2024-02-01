package com.example.shopping_list_application;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ListsDao {
    @Query("SELECT * FROM lists")
    List<Lists> getAll();
/*
    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);
*/
    @Insert
    void insert(Lists... lists);

    @Delete
    void delete(Lists lists);

}
