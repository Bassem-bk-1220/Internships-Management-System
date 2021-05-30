package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.Adapters.ProfFileAdapter;
import com.example.myapplication.Modele.Data;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerifyFilePratiqueActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProfFileAdapter adapter;
    ArrayList<Data> listall;
    ImageView img_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_file_pratique);
        recyclerView = findViewById(R.id.recycle_file_pratique);
        img_logout=findViewById(R.id.imgv_logout);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent=new Intent(VerifyFilePratiqueActivity.this, MainAppActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        DatabaseReference myref;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref = database.getReference("apply_offre");
        listall = new ArrayList<>();

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listall.clear();
                for (DataSnapshot dataValues : snapshot.getChildren()) {
                    Data data = dataValues.getValue(Data.class);
                    if(data.getFilecompany()!=null) {
                        listall.add(data);
                    }
                }
                adapter = new ProfFileAdapter(VerifyFilePratiqueActivity.this, listall);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}