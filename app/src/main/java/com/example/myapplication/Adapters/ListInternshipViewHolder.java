package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ListInternshipViewHolder extends RecyclerView.ViewHolder{
   public ImageView img_logo;
  public   TextView txtv_name,txtv_description,txtv_company,txtv_country,txtv_see_more;
   public ConstraintLayout itemlist_internships;
    public ListInternshipViewHolder(@NonNull View itemView) {
        super(itemView);
        img_logo=itemView.findViewById(R.id.img_logo);
        txtv_name=itemView.findViewById(R.id.tv_name_poste);
        txtv_description=itemView.findViewById(R.id.tv_description);
        txtv_company=itemView.findViewById(R.id.tv_company);
        txtv_country=itemView.findViewById(R.id.tv_country);
        txtv_see_more=itemView.findViewById(R.id.tv_see_more);
        itemlist_internships=itemView.findViewById(R.id.item_list_internships);
    }
}
