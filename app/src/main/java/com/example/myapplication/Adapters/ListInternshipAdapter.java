package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.DetailsInternshipActivity;
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

public class ListInternshipAdapter extends RecyclerView.Adapter<ListInternshipViewHolder>  {
    private Context context;
    private List<Internships> listinternship;
    private List<Internships> list_inter;
    private StorageReference storageReference;
    public ListInternshipAdapter(Context context,List<Internships>list_inter){
        this.context=context;
        this.listinternship=list_inter;
        storageReference= FirebaseStorage.getInstance().getReference();
    }
    @NonNull
    @Override
    public ListInternshipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_internships,parent,false);
        return new ListInternshipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListInternshipViewHolder holder, int position) {

        Internships internships=listinternship.get(position);
        DatabaseReference myref;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref= database.getReference("Profil company/"+internships.getId_company());
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                    ProfilCompany profilCompany = dataSnapshot.getValue(ProfilCompany.class);
                holder.txtv_description.setText(profilCompany.getDescription());
                Picasso.get().load(profilCompany.getProfileimage()).into(holder.img_logo);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        holder.txtv_name.setText(internships.getKeyword());
        holder.txtv_period.setText(internships.getPeriod());
        holder.txtv_payment.setText(internships.getPayment());
        holder.txtv_salary.setText(internships.getSalary());

        holder.txtv_see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(context, DetailsInternshipActivity.class);
               intent.putExtra("idcompany",internships.getId_company());
                intent.putExtra("idinternship",internships.getIdinternship());
               context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listinternship.size();
    }


}
