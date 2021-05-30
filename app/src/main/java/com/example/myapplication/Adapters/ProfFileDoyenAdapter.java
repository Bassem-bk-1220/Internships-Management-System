package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Modele.Data;
import com.example.myapplication.Activity.DetailsToSignDoyenActivity;
import com.example.myapplication.R;
import com.example.myapplication.Modele.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfFileDoyenAdapter extends RecyclerView.Adapter<ProfFileViewHolder>{
    private Context context;
    private List<Data> listdemande;
    public ProfFileDoyenAdapter(Context context, List<Data>data){
        this.context=context;
        this.listdemande=data;
    }
    @NonNull
    @Override
    public ProfFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_pratique_prof,parent,false);
        return new ProfFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfFileViewHolder holder, int position) {
        Data demande=listdemande.get(position);
        DatabaseReference databaseReference;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference= database.getReference("Users").child(demande.getIduser());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user= snapshot.getValue(User.class);
                holder.txtv_name_stud.setText(user.getFname());
                Picasso.get().load(user.getUploadimage()).into(holder.img_stud);
                holder.txtv_see_details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, DetailsToSignDoyenActivity.class);
                        //intent.putExtra("filecompany",demande.getFilecompany());
                        intent.putExtra("filestud",demande.getFilestud());
                        intent.putExtra("state",demande.getState());
                        intent.putExtra("iddata",demande.getIddata());
                        intent.putExtra("iduser",demande.getIduser());
                        intent.putExtra("fileprof",demande.getFileprof());
                        intent.putExtra("filedoyen",demande.getFiledoyen());
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listdemande.size();
    }
}
