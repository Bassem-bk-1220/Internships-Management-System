package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Modele.Data;
import com.example.myapplication.Activity.DetailOffreActivity;
import com.example.myapplication.Modele.Internships;
import com.example.myapplication.Modele.ProfilCompany;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OffreAdapter  extends RecyclerView.Adapter<OffreViewHolder>{
    private Context context;
    private List<Data> listdemande;
    public OffreAdapter(Context context,List<Data>data){
        this.context=context;
        this.listdemande=data;
    }
    @NonNull
    @Override
    public OffreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offre,parent,false);
        return new OffreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffreViewHolder holder, int position) {
        Data demande=listdemande.get(position);
        DatabaseReference myref,databaseReference;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref= database.getReference("AnnonceStage").child(demande.getIdoffre());
        databaseReference= database.getReference("Profil company").child(demande.getIdcompany());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfilCompany profilCompany = snapshot.getValue(ProfilCompany.class);
                holder.tv_company.setText(profilCompany.getNameCompany());
                Picasso.get().load(profilCompany.getProfileimage()).into(holder.img_company);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Internships internships = dataSnapshot.getValue(Internships.class);

                holder.tv_offre.setText(internships.getKeyword());
                holder.tv_see.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, DetailOffreActivity.class);
                        intent.putExtra("idcompany",demande.getIdcompany());
                        intent.putExtra("idoffre",demande.getIdoffre());
                        intent.putExtra("state",demande.getState());
                        intent.putExtra("iddata",demande.getIddata());
                        intent.putExtra("fileprof",demande.getFileprof());
                        intent.putExtra("filecompany",demande.getFilecompany());
                        intent.putExtra("filedoyen",demande.getFiledoyen());
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


    }

    @Override
    public int getItemCount() {
        return listdemande.size();
    }
}
