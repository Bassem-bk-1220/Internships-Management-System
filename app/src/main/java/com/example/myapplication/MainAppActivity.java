package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainAppActivity extends AppCompatActivity {
    TextView txt_rep_entreprise,txt_etudiant,txt_mentor_enseignant,txt_doyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        txt_rep_entreprise=findViewById(R.id.et_rep_entreprise);
        txt_etudiant=findViewById(R.id.et_etudiant);
        txt_mentor_enseignant=findViewById(R.id.et_mentor_enseignant);
        txt_doyen=findViewById(R.id.et_doyen);

        txt_rep_entreprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),LoginDoyenActivity.class);
                intent.putExtra("from","representant d'entreprise");
                startActivity(intent);
                finish();
            }
        });
        txt_etudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginDoyenActivity.class);
                intent.putExtra("from","etudiant");
                startActivity(intent);
                finish();
            }
        });
        txt_mentor_enseignant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginDoyenActivity.class);
                intent.putExtra("from","mentor enseignant");
                startActivity(intent);
                finish();
            }
        });
        txt_doyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginDoyenActivity.class);
                intent.putExtra("from","doyen");

                startActivity(intent);
                finish();
            }
        });
    }
}