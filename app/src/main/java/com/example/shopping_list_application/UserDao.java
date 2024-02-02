package com.example.shopping_list_application;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);


    @Query("SELECT * FROM user WHERE username LIKE :userName")
    List<User> findByName(String userName);

    @Query("SELECT * FROM user ORDER BY id DESC limit 1;")
    User loadUser();

    @Insert
    void insert(User... user);

    @Update
    void update(User... user);
    @Delete
    void delete(User user);

}
