package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class UserViewHolder extends RecyclerView.ViewHolder{
   public ImageView logo;
   public  TextView name_user;
   public ConstraintLayout itemlist;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        logo=(ImageView) itemView.findViewById(R.id.img_logo);
        name_user=(TextView) itemView.findViewById(R.id.txt_name_user);
        itemlist=(ConstraintLayout) itemView.findViewById(R.id.item_list);
    }
}
