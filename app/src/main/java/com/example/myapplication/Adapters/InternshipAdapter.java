package com.example.myapplication.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.InternshipOfTheCompanyActivity;
import com.example.myapplication.Modele.Internships;
import com.example.myapplication.Modele.ProfilCompany;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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

        DatabaseReference myref;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref= database.getReference("Profil company/"+annonuce.getId_company());
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                ProfilCompany profilCompany = dataSnapshot.getValue(ProfilCompany.class);
                holder.name_company.setText(profilCompany.getNameCompany());
                Picasso.get().load(profilCompany.getProfileimage()).into(holder.logo_photo_company);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

       holder.internship_item.setOnClickListener((View)->{
           Intent intent=new Intent(View.getContext(),InternshipOfTheCompanyActivity.class);
           intent.putExtra("keyword",internshipList.get(position).getKeyword());
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
