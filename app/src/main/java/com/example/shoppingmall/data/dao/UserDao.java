package com.example.shoppingmall.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoppingmall.data.bean.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM User WHERE name=:name and password=:password")
    List<User> getUser(String name, String password);

    @Query("SELECT * FROM User WHERE name=:name")
    List<User> getUserByName(String name);

}
