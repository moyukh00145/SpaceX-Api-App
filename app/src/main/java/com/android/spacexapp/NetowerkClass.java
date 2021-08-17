package com.android.spacexapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetowerkClass {

    private  static NetowerkClass INSTANCE;
    private static Context ctx;
    private static RequestQueue requestQueue;

    private NetowerkClass(Context context){
        ctx=context;

        requestQueue=getRequestQueue();
    }

    public static synchronized NetowerkClass getINSTANCE(Context context1){
        if (INSTANCE==null){
            INSTANCE=new NetowerkClass(context1);
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addRequestQueue(Request<T>request){
        getRequestQueue().add(request);
    }

}
