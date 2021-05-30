package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class DetailsToSignDoyenActivity extends AppCompatActivity {
TextView txtv_file_sign_doyen;
Button btn_file_sign;
String iddata="";
String fileprof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_to_sign_doyen);
        txtv_file_sign_doyen=findViewById(R.id.tv_file_sign_doyen);
        btn_file_sign=findViewById(R.id.btn_upload_file_sign);
        iddata=getIntent().getStringExtra("iddata");
        fileprof=getIntent().getStringExtra("fileprof");
        if(fileprof!=null) {
            txtv_file_sign_doyen.setVisibility(View.VISIBLE);
            txtv_file_sign_doyen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailsToSignDoyenActivity.this, ShowCVActivity.class);
                    intent.putExtra("file_upload", fileprof);
                    startActivity(intent);
                }
            });
            btn_file_sign.setVisibility(View.VISIBLE);
            btn_file_sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(DetailsToSignDoyenActivity.this,FileSignatureActivity.class);
                    intent.putExtra("iddata",iddata);
                    intent.putExtra("from","doyen");
                    startActivity(intent);
                }
            });
        }
    }
}