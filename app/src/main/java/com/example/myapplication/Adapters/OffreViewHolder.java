package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class OffreViewHolder extends RecyclerView.ViewHolder{
    public ImageView img_company;
    public TextView tv_company,tv_offre,tv_see;
    public ConstraintLayout item_offre;
    public OffreViewHolder(@NonNull View itemView) {
        super(itemView);
        img_company=itemView.findViewById(R.id.img_logo);
        tv_company=itemView.findViewById(R.id.tv_name_company);
        tv_offre=itemView.findViewById(R.id.tv_name_offre);
        tv_see=itemView.findViewById(R.id.tv_see_more);
        item_offre=itemView.findViewById(R.id.item_offre);
    }
}
