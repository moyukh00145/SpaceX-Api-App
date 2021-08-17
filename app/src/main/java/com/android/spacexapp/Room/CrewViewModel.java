package com.android.spacexapp.Room;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CrewViewModel extends AndroidViewModel {

    private CrewRepository crewRepository;
    private final LiveData<List<User>> allUser;

    public CrewViewModel(@NonNull Application application) {
        super(application);
        crewRepository=new CrewRepository(application);
        allUser=crewRepository.getAllUser();
    }

    public LiveData<List<User>> getAllUser(){
        return allUser;
    }
    public void insert(User user) { crewRepository.Insert(user);}
    public void Delete(User user){
        crewRepository.Delete(user);
    }
    public void DeleteAll(){crewRepository.DeleteAll();}

    public void getDataFromApi1(Context context){
        crewRepository.getDataFromApi(context);
    }
}
