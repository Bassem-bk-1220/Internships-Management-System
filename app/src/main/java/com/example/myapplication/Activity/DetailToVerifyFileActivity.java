package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailToVerifyFileActivity extends AppCompatActivity {
        TextView txtv_file_pratique,txtv_state,txtv_upload_file_sign;
        Button btn_accept,btn_refuse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_to_verify_file);
        String file_cadre_pratique=getIntent().getStringExtra("filestud");
        String state=getIntent().getStringExtra("state");
        String iddata=getIntent().getStringExtra("iddata");
        txtv_file_pratique=findViewById(R.id.tv_file_cadre_pratique);
        btn_accept=findViewById(R.id.btn_accepte);
        btn_refuse=findViewById(R.id.btn_refuse);
        txtv_state=findViewById(R.id.tv_state);
        txtv_upload_file_sign=findViewById(R.id.txt_upload_file_sign);
        if(state.equals("accepte teacher")){
            txtv_upload_file_sign.setVisibility(View.VISIBLE);

        }
        txtv_upload_file_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailToVerifyFileActivity.this,FileSignatureActivity.class);
                intent.putExtra("iddata",iddata);
                intent.putExtra("from","prof");
                startActivity(intent);

            }
        });
        if(file_cadre_pratique!=null) {
            txtv_file_pratique.setVisibility(View.VISIBLE);
            txtv_file_pratique.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailToVerifyFileActivity.this, ShowCVActivity.class);
                    intent.putExtra("file_upload", file_cadre_pratique);
                    startActivity(intent);
                }
            });
        }
        btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference().child("apply_offre");
                databaseReference.child(iddata).child("state").setValue("refuse teacher");
                btn_accept.setVisibility(View.INVISIBLE);
                btn_refuse.setVisibility(View.INVISIBLE);
                txtv_state.setVisibility(View.VISIBLE);
                txtv_state.setText("refuse teacher");
            }
        });
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference  databaseReference = database.getReference().child("apply_offre");
                databaseReference.child(iddata).child("state").setValue("accepte teacher");
                btn_accept.setVisibility(View.INVISIBLE);
                btn_refuse.setVisibility(View.INVISIBLE);
                txtv_state.setVisibility(View.VISIBLE);
                txtv_state.setText("accepte teacher");
                txtv_upload_file_sign.setVisibility(View.VISIBLE);
            }
        });
        if(!state.equals("accepte")){
            btn_accept.setVisibility(View.INVISIBLE);
            btn_refuse.setVisibility(View.INVISIBLE);
            txtv_state.setVisibility(View.VISIBLE);
            txtv_state.setText(state);
        }
    }
}