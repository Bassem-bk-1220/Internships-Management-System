package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class InternshipDetailsViewHolder extends RecyclerView.ViewHolder{
    public TextView keyword_inter,name_company,description_inter,description_requirement,country,company,btn_apply;
    public ImageView logo_photo_company;
    public ConstraintLayout internship_detail_item;
    public InternshipDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        keyword_inter=itemView.findViewById(R.id.tv_name_poste);
        name_company=itemView.findViewById(R.id.tv_name_company);
        description_inter=itemView.findViewById(R.id.tv_description);
        description_requirement=itemView.findViewById(R.id.tv_description_req);
        country=itemView.findViewById(R.id.tv_country);
        company=itemView.findViewById(R.id.tv_company);
        logo_photo_company=itemView.findViewById(R.id.img_logo_company);
        btn_apply=itemView.findViewById(R.id.tv_apply);
        internship_detail_item=itemView.findViewById(R.id.item_internship_details);
    }
}
