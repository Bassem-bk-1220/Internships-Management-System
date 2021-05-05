package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.InternshipOfTheCompanyActivity;
import com.example.myapplication.Internships;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InternshipAdapter extends RecyclerView.Adapter<InternshipViewHolder> {
    private Class<InternshipOfTheCompanyActivity> context;
    private List<Internships> internshipList;
    private StorageReference storageReference;
    public InternshipAdapter(Class<InternshipOfTheCompanyActivity> context, List<Internships>internships){
        this.context=context;
        this.internshipList=internships;
        storageReference= FirebaseStorage.getInstance().getReference();
    }
    @NonNull
    @Override
    public InternshipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_internships,parent,false);
        return new InternshipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InternshipViewHolder holder, int position) {
   Internships annonuce=internshipList.get(position);
   holder.keyword_inter.setText(annonuce.getKeyword());
   holder.name_company.setText(annonuce.getName_company());
   holder.description_inter.setText(annonuce.getDescription_intern());
   holder.period_inter.setText(annonuce.getPeriod());
   holder.payment_inter.setText(annonuce.getPayment());
   holder.salary.setText(annonuce.getSalary());

    Picasso.get().load(annonuce.getLogo_company()).into(holder.logo_photo_company);

       holder.internship_item.setOnClickListener((View)->{
           Intent intent=new Intent(View.getContext(),InternshipOfTheCompanyActivity.class);
           intent.putExtra("keyword",internshipList.get(position).getKeyword());
           intent.putExtra("name_company",internshipList.get(position).getName_company());
           intent.putExtra("description_intern",internshipList.get(position).getDescription_intern());
           intent.putExtra("period",internshipList.get(position).getPeriod());
           intent.putExtra("payment",internshipList.get(position).getPayment());
           intent.putExtra("salary",internshipList.get(position).getSalary());
           //intent.putExtra("logo_company",internshipList.get(position).getLogo_company());
       });

    }

    @Override
    public int getItemCount() {
        return internshipList.size();
    }
}
