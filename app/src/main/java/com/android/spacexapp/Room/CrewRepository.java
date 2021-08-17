package com.android.spacexapp.Room;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.android.spacexapp.NetowerkClass;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CrewRepository {

    private CrewDao crewDao;
    private LiveData<List<User>> allUser;

    public CrewRepository(Application application){
        CrewDatabase db=CrewDatabase.getDatabase(application);
        crewDao=db.getCrewDao();
        allUser=crewDao.getAll();
    }

    LiveData<List<User>> getAllUser(){

        return allUser;
    }

    void Insert(final User user){
        CrewDatabase.databaseWriteExecutor.execute(() -> crewDao.insertAll(user));
    }

    void Delete(User user){
        CrewDatabase.databaseWriteExecutor.execute(()->{crewDao.delete(user);});
    }

    void DeleteAll(){
        CrewDatabase.databaseWriteExecutor.execute(()->{
            crewDao.DeleteAll();
        });
    }


    void getDataFromApi(Context context)
    {

        String url="https://api.spacexdata.com/v4/crew";
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET,url,null, response ->
        {
            for (int i=0;i<response.length();i++){
                JSONObject jsonObject;
                try {
                    jsonObject= (JSONObject) response.get(i);
                    User user=new User(jsonObject.getString("name"),jsonObject.getString("agency"),jsonObject.getString("image")
                            ,jsonObject.getString("wikipedia"),jsonObject.getString("status")
                            ,jsonObject.getString("id"));
                    Insert(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        },error ->
        {
            Log.w("reqError",error);
        });


        if (context!=null){

            NetowerkClass.getINSTANCE(context).getRequestQueue().add(request);

        }
        else {
            Log.w("contex","null");
        }
    }

}
