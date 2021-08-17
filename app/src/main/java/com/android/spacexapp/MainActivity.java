package com.android.spacexapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.spacexapp.Adapter.DataAdapter;
import com.android.spacexapp.Room.CrewViewModel;
import com.android.spacexapp.Room.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataAdapter.listClicked{


    CrewViewModel crewViewModel;
    List<User> all;
    RecyclerView recyclerView;
    DataAdapter adapter;
    FloatingActionButton fab_delete,fab_refresh;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        all=new ArrayList<>();

        recyclerView=findViewById(R.id.crew_recycle);
        adapter=new DataAdapter(this,all);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab_refresh=findViewById(R.id.refresh);
        fab_delete=findViewById(R.id.delete);


        crewViewModel=new ViewModelProvider(this).get(CrewViewModel.class);

        preferences=getSharedPreferences("room",MODE_PRIVATE);
        editor=preferences.edit();
        if (preferences.getBoolean("exist",false)){
            if (preferences.getBoolean("load",false)){
                if (isNetworkConnected()){
                    crewViewModel.getDataFromApi1(this);
                }
                else {
                    Toast.makeText(this, "Turn on Internet for the first time to load data", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            editor.putBoolean("exist",true);
            editor.putBoolean("load",false);
            if (isNetworkConnected()){
                crewViewModel.getDataFromApi1(this);
            }
            else {
                Toast.makeText(this, "Turn on Internet for the first time to load data", Toast.LENGTH_SHORT).show();
            }
        }

        editor.commit();




        crewViewModel.getAllUser().observe(this, users -> {

            adapter.setUserData(users);

        });

        fab_refresh.setOnClickListener(view -> {

            if (isNetworkConnected()){
                if (all.size()>0){
                    Log.w("list","full");
                }
                else {
                    crewViewModel.getDataFromApi1(this);
                }
            }
            else{
                Toast.makeText(this, "Please turn on your internet", Toast.LENGTH_SHORT).show();
            }

        });

        fab_delete.setOnClickListener(view -> {


            editor.putBoolean("load",true);
            editor.commit();

                crewViewModel.DeleteAll();

        });

    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    @Override
    public void onClick(User user) {

        String url=user.wikipedia;

        CustomTabsIntent.Builder builder=new CustomTabsIntent.Builder();

        int color= Color.parseColor("#8D3EFF");
        CustomTabColorSchemeParams.Builder colourBuilder=new CustomTabColorSchemeParams.Builder();
        colourBuilder.setToolbarColor(color);

        builder.setDefaultColorSchemeParams(colourBuilder.build());
        CustomTabsIntent intent=builder.build();
       if (isNetworkConnected()){
           intent.launchUrl(this, Uri.parse(url));
       }
       else {
           Toast.makeText(this, "Please turn on Internet", Toast.LENGTH_SHORT).show();
       }

    }
}