package com.android.spacexapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.spacexapp.MainActivity;
import com.android.spacexapp.R;
import com.android.spacexapp.Room.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {


    Context context;
    List<User>list;
    listClicked listClicked;
    public DataAdapter(Context context,List<User>userList) {
        this.context=context;
        this.list=userList;
        this.listClicked= (MainActivity) context;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.single_item,parent,false);

        return new DataHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {

        holder.name.setText("Name : "+list.get(position).user_name);
        holder.agency.setText("Agency : "+list.get(position).agency);
        holder.status.setText("Status : "+list.get(position).status);
        holder.wikipedia.setText("Wikipedia: "+list.get(position).wikipedia);
        Glide.with(context).load(list.get(position).image).into(holder.imageView);

        holder.wikipedia.setOnClickListener(view -> {
            listClicked.onClick(list.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DataHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,agency,status,wikipedia;

        public DataHolder(@NonNull View itemView)
        {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            agency=itemView.findViewById(R.id.agency);
            status=itemView.findViewById(R.id.status);
            wikipedia=itemView.findViewById(R.id.wikipedia);
            imageView=itemView.findViewById(R.id.profile_pic);

        }
    }

    public void setUserData(List<User>list1){
        this.list=list1;
        notifyDataSetChanged();
    }

    public interface listClicked{
        void onClick(User user);
    }
}
