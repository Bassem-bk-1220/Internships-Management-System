package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.ApplyInternshipActivity;
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

public class InternshipDetailsAdapter extends RecyclerView.Adapter<InternshipDetailsViewHolder> {
    private Context context;
    private List<Internships> internshipList;
    private StorageReference storageReference;
    public InternshipDetailsAdapter(Context context, List<Internships>internships){
        this.context=context;
        this.internshipList=internships;
        storageReference= FirebaseStorage.getInstance().getReference();
    }
    @NonNull
    @Override
    public InternshipDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_internship_details,parent,false);
        return new InternshipDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InternshipDetailsViewHolder holder, int position) {
        Internships internships=internshipList.get(position);
        DatabaseReference myref;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref= database.getReference("Profil company/"+internships.getId_company());
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                ProfilCompany profilCompany = dataSnapshot.getValue(ProfilCompany.class);
                holder.description_inter.setText(profilCompany.getDescription());
                holder.company.setText(profilCompany.getNameCompany());
                holder.country.setText(profilCompany.getAddress());
                Picasso.get().load(profilCompany.getProfileimage()).into(holder.logo_photo_company);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        holder.keyword_inter.setText(internships.getKeyword());
        holder.description_requirement.setText(internships.getDescription_intern());

        holder.btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ApplyInternshipActivity.class);
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return internshipList.size();
    }
}
