package com.android.spacexapp.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CrewDao {

    @Query("SELECT * FROM crew")
   LiveData<List<User>> getAll();

    @Query("DELETE FROM crew")
    void DeleteAll();

    @Insert
    void insertAll(User users);

    @Delete
    void delete(User user);
}
