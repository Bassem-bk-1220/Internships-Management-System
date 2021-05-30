package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ProfFileDoyenViewHolder extends RecyclerView.ViewHolder {
   public TextView txtv_name_stud,txtv_see_details;
    public ImageView img_stud;
    public ConstraintLayout item_prof_file;
    public ProfFileDoyenViewHolder(@NonNull View itemView) {
        super(itemView);
        txtv_name_stud=itemView.findViewById(R.id.txt_name_user);
        txtv_see_details=itemView.findViewById(R.id.tv_details);
        img_stud=itemView.findViewById(R.id.img_logo);
        item_prof_file=itemView.findViewById(R.id.item_prof_file_pratique);
    }
}
