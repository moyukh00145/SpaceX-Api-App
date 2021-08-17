package com.android.spacexapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "crew")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "user_name")
    public String user_name;

    @ColumnInfo(name = "agency")
    public String agency;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "wikipedia")
    public String wikipedia;

    @ColumnInfo(name = "status")
    public String status;

    public User(String user_name,String agency,String image,String wikipedia,String status,String  id){

        this.user_name=user_name;
        this.agency=agency;
        this.image=image;
        this.status=status;
        this.wikipedia=wikipedia;
        this.id=id;
    }

}
