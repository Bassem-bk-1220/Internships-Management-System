package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    public TextView txtv_name_stud,txtv_group_stud,txtv_address_stud,txtv_country_stud,txtv_email_stud,txtv_phone_stud,txtv_cv_stud,txtv_details;
    public CircleImageView logo_photo_stud;
    public ConstraintLayout item_notification;
    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        txtv_name_stud=itemView.findViewById(R.id.tv_name_student);
        txtv_group_stud=itemView.findViewById(R.id.tv_group_stud);
        txtv_address_stud=itemView.findViewById(R.id.tv_address_student);
        txtv_country_stud=itemView.findViewById(R.id.tv_country_stud);
        txtv_email_stud=itemView.findViewById(R.id.tv_email_student);
        txtv_phone_stud=itemView.findViewById(R.id.tv_phone_student);
        txtv_cv_stud=itemView.findViewById(R.id.tv_CV_student);
        logo_photo_stud=itemView.findViewById(R.id.logo_photo_profile_stud);
        txtv_details=itemView.findViewById(R.id.tv_details);
        item_notification=itemView.findViewById(R.id.item_notification_entr);
    }
}
