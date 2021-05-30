package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Modele.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private Context context;
    private List<User> userList;
    public UserAdapter(Context context,List<User>user){
        this.context=context;
        this.userList=user;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
       User person=userList.get(position);
        holder.name_user.setText(person.getFname());
        if(person.getUploadimage()!=null) {
            Picasso.get().load(person.getUploadimage()).into(holder.logo);
        }else{
            holder.logo.setImageResource(R.drawable.profile_picture);
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
