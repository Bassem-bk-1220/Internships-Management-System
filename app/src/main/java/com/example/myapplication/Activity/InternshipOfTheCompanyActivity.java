package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapters.InternshipAdapter;
import com.example.myapplication.Modele.Internships;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InternshipOfTheCompanyActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    InternshipAdapter adapter;
    ImageView imgv_back;
    TextView tv_add_internships;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship_of_the_company);
        recyclerView=findViewById(R.id.recycle_search);
        imgv_back=findViewById(R.id.img_back);
        tv_add_internships=findViewById(R.id.btn_add_internships);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(InternshipOfTheCompanyActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        DatabaseReference myref;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref= database.getReference("AnnonceStage");
        final List<Internships> mylist=new ArrayList<>();
        tv_add_internships.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InternshipOfTheCompanyActivity.this,AjouterAnnouncesInternshipActivity.class);
                startActivity(intent);
            }
        });
        imgv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mylist.clear();
                for(DataSnapshot dataValues:snapshot.getChildren()) {
                   Internships internships = dataValues.getValue(Internships.class);

                   if(internships.getId_company().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    mylist.add(internships);
                }
                adapter=new InternshipAdapter(InternshipOfTheCompanyActivity.class,mylist);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}