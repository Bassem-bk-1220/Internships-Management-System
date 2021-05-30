package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Modele.Data;
import com.example.myapplication.Activity.DetailsNotificationActivity;
import com.example.myapplication.R;
import com.example.myapplication.Activity.ShowCVActivity;
import com.example.myapplication.Modele.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder>{
    private Context context;
    private List<Data>ListData;
    private StorageReference storageReference;
    User user;
    public NotificationAdapter(Context context,List<Data>data){
        this.context=context;
        this.ListData=data;
        storageReference= FirebaseStorage.getInstance().getReference();
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
  Data offre=ListData.get(position);
  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(offre.getIduser());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 user = snapshot.getValue(User.class);
                holder.txtv_name_stud.setText(user.getFname());
                holder.txtv_group_stud.setText(user.getGroup());
                holder.txtv_address_stud.setText(user.getAddress());
                holder.txtv_email_stud.setText(user.getEmail());
                holder.txtv_phone_stud.setText(user.getPhone());
                holder.txtv_country_stud.setText(offre.getCountry());
                Picasso.get().load(user.getUploadimage()).into(holder.logo_photo_stud);
                holder.txtv_cv_stud.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, ShowCVActivity.class);
                        intent.putExtra("file_upload",offre.getFile());
                       context.startActivity(intent);
                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.txtv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailsNotificationActivity.class);
                intent.putExtra("file",offre.getFile());
                intent.putExtra("country",offre.getCountry());
                intent.putExtra("idcompany",offre.getIdcompany());
                intent.putExtra("idoffre",offre.getIdoffre());
                intent.putExtra("iduser",offre.getIduser());
                intent.putExtra("fname",user.getFname());
                intent.putExtra("group",user.getGroup());
                intent.putExtra("phone",user.getPhone());
                intent.putExtra("address",user.getAddress());
                intent.putExtra("email",user.getEmail());
                intent.putExtra("state",offre.getState());
                intent.putExtra("iddata",offre.getIddata());
                intent.putExtra("filestud",offre.getFilestud());
                intent.putExtra("imglogo",user.getUploadimage());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ListData.size() ;
    }
}
