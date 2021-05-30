package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Modele.ProfilCompany;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailsInternshipActivity extends AppCompatActivity {
    ImageView img_log;
    TextView txtv_name_poste,txtv_title,txtv_description,txtv_title_exigence,txtv_description_req,txtv_company,txtv_text,txtv_email,txtv_country,txtv_apply;
    private DatabaseReference databaseReference,databaseRef;
    private FirebaseAuth mAuth;
    private String CurrentUserid;
    String name_poste="";
    String logo_company="";
    String description_req="";
    String company_name="";
    String description_company="";
    String email_company="";
    String country="";
    String idcompany="";
    String idinternship="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_internship);
        mAuth = FirebaseAuth.getInstance();
        CurrentUserid = mAuth.getCurrentUser().getUid();
        idcompany=getIntent().getStringExtra("idcompany");
        idinternship=getIntent().getStringExtra("idinternship");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Profil company").child(idcompany);
        databaseRef = FirebaseDatabase.getInstance().getReference().child("AnnonceStage").child(idinternship);
        txtv_name_poste = findViewById(R.id.tv_name_poste);
        txtv_title = findViewById(R.id.tv_title);
        txtv_description = findViewById(R.id.tv_description);
        txtv_title_exigence = findViewById(R.id.tv_title_exigence);
        txtv_description_req = findViewById(R.id.tv_description_req);
        txtv_text = findViewById(R.id.tv_text);
        txtv_email = findViewById(R.id.tv_email);
        txtv_company = findViewById(R.id.tv_company);
        txtv_country = findViewById(R.id.tv_country);
        img_log = findViewById(R.id.img_logo_company);
        txtv_apply = findViewById(R.id.tv_apply);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ProfilCompany profilCompany = snapshot.getValue(ProfilCompany.class);
                        logo_company = profilCompany.getProfileimage();
                        Picasso.get().load(logo_company).into(img_log);
                        description_company = profilCompany.getDescription();
                        email_company = profilCompany.getEmail_company();
                        country = profilCompany.getAddress();
                        company_name = profilCompany.getNameCompany();

                    txtv_description.setText(description_company);
                    txtv_email.setText(email_company);
                    txtv_country.setText(country);
                    txtv_company.setText(company_name);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("keyword").getValue() != null) {
                        name_poste = snapshot.child("keyword").getValue().toString();
                        txtv_name_poste.setText(name_poste);
                    }
                    if (snapshot.child("description_intern").getValue() != null) {
                        description_req = snapshot.child("description_intern").getValue().toString();
                        txtv_description_req.setText(description_req);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        txtv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailsInternshipActivity.this,ApplyInternshipActivity.class);
                intent.putExtra("idcompany",idcompany);
                intent.putExtra("idinternship",idinternship);
                startActivity(intent);
            }
        });
    }

}