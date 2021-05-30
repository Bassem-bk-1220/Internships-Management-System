package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Modele.Internships;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsNotificationActivity extends AppCompatActivity {
TextView txtvname,txtv_group,txtv_address,txtv_email,txtv_phone,txtv_country,txtv_file,txtv_name_offre,tv_state,txtv_file_cadre_pratique,txtv_upload_file;
CircleImageView  logo_photo_stud;
Button btn_accepte,btn_refuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_notification);
        txtvname=findViewById(R.id.tv_name_student);
        txtv_group=findViewById(R.id.tv_group_stud);
        txtv_address=findViewById(R.id.tv_address_student);
        txtv_email=findViewById(R.id.tv_email_student);
        txtv_phone=findViewById(R.id.tv_phone_student);
        txtv_country=findViewById(R.id.tv_country_stud);
        txtv_file=findViewById(R.id.tv_CV_student);
        txtv_name_offre=findViewById(R.id.tv_name_intern);
        btn_accepte=findViewById(R.id.btn_accepte);
        btn_refuse=findViewById(R.id.btn_refuse);
        tv_state=findViewById(R.id.tv_state);
        logo_photo_stud=findViewById(R.id.logo_photo_profile_stud);
        txtv_upload_file=findViewById(R.id.txt_upload_file);
        txtv_file_cadre_pratique=findViewById(R.id.tv_file_cadre_pratique);
        String file=getIntent().getStringExtra("file");
        String file_cadre_pratique=getIntent().getStringExtra("filestud");
        String country=getIntent().getStringExtra("country");
        String iduser=getIntent().getStringExtra("iduser");
        String idoffre=getIntent().getStringExtra("idoffre");
        String idcompany=getIntent().getStringExtra("idcompany");
        String iddata=getIntent().getStringExtra("iddata");
        String state=getIntent().getStringExtra("state");
        String name=getIntent().getStringExtra("fname");
        String group=getIntent().getStringExtra("group");
        String email=getIntent().getStringExtra("email");
        String phone=getIntent().getStringExtra("phone");
        String address=getIntent().getStringExtra("address");
        String imglogo= getIntent().getStringExtra("imglogo");
        txtv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailsNotificationActivity.this, ShowCVActivity.class);
                intent.putExtra("file_upload",file);
                 startActivity(intent);
            }
        });
        if(file_cadre_pratique!=null){
            txtv_file_cadre_pratique.setVisibility(View.VISIBLE);
            txtv_file_cadre_pratique.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(DetailsNotificationActivity.this, ShowCVActivity.class);
                    intent.putExtra("file_upload",file_cadre_pratique);
                    startActivity(intent);
                }
            });
            txtv_upload_file.setVisibility(View.VISIBLE);
            txtv_upload_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(DetailsNotificationActivity.this,FileSignatureActivity.class);
                    intent.putExtra("iddata",iddata);
                    intent.putExtra("from","company");
                    startActivity(intent);
                }
            });
        }
        Picasso.get().load(imglogo).into(logo_photo_stud);
        txtv_country.setText(country);
        txtvname.setText(name);
        txtv_group.setText(group);
        txtv_address.setText(address);
        txtv_email.setText(email);
        txtv_phone.setText(phone);
        btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference  databaseReference = database.getReference().child("apply_offre");
                databaseReference.child(iddata).child("state").setValue("refuse");
                btn_accepte.setVisibility(View.INVISIBLE);
                btn_refuse.setVisibility(View.INVISIBLE);
                tv_state.setVisibility(View.VISIBLE);
                tv_state.setText("refuse");
            }
        });
        btn_accepte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
           DatabaseReference  databaseReference = database.getReference().child("apply_offre");
                databaseReference.child(iddata).child("state").setValue("accepte");
                btn_accepte.setVisibility(View.INVISIBLE);
                btn_refuse.setVisibility(View.INVISIBLE);
                tv_state.setVisibility(View.VISIBLE);
                tv_state.setText("accepte");
            }
        });
        if(!state.equals("pending")){
            btn_accepte.setVisibility(View.INVISIBLE);
            btn_refuse.setVisibility(View.INVISIBLE);
            tv_state.setVisibility(View.VISIBLE);
            tv_state.setText(state);
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AnnonceStage").child(idoffre);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Internships internships = snapshot.getValue(Internships.class);
                txtv_name_offre.setText( internships.getKeyword());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}