package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class InternshipViewHolder extends RecyclerView.ViewHolder{
    public TextView keyword_inter,name_company,description_inter,period_inter,payment_inter,salary;
    public ImageView logo_photo_company;
    public ConstraintLayout internship_item;
    public InternshipViewHolder(@NonNull View itemView) {
        super(itemView);
        keyword_inter=itemView.findViewById(R.id.tv_keyword);
        name_company=itemView.findViewById(R.id.tv_name_company);
        description_inter=itemView.findViewById(R.id.tv_description);
        period_inter=itemView.findViewById(R.id.tv_period);
        payment_inter=itemView.findViewById(R.id.tv_payment);
        logo_photo_company=itemView.findViewById(R.id.img_logo);
        salary=itemView.findViewById(R.id.tv_salary);
        internship_item=itemView.findViewById(R.id.item_internship);
    }
}
