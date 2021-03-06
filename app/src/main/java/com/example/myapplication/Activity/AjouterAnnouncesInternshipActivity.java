package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Modele.Internships;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class AjouterAnnouncesInternshipActivity extends AppCompatActivity {
    private ImageView img_logo_company;
    private EditText editxt_key_word,editxt_period,editxt_description_internship,editxt_salary;
    private RadioButton btn_radio_paid,btn_radio_notpaid;
    private TextView btn_validate_internship;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    String CurrentUserID;
    private ProgressDialog loadingbar;
    private static final int Gallery_Pick=1;
    Uri imageUri;
    Internships internships;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_announces_internship);
        editxt_key_word=findViewById(R.id.et_key_word);
        editxt_period=findViewById(R.id.et_period);
        editxt_description_internship=findViewById(R.id.et_description_inter);
        editxt_salary=findViewById(R.id.et_salary);
        btn_radio_paid=findViewById(R.id.radio_paid);
        btn_radio_notpaid=findViewById(R.id.radio_not_paid);
        btn_validate_internship=findViewById(R.id.btn_validate_inter);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        CurrentUserID = mAuth.getCurrentUser().getUid();
        loadingbar = new ProgressDialog(AjouterAnnouncesInternshipActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("AnnonceStage");

        internships=new Internships();


        btn_validate_internship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateData();

            }
        });

    }
    private void ValidateData() {
        final String keyword = editxt_key_word.getText().toString();
        final String period = editxt_period.getText().toString();
        final String description_intern = editxt_description_internship.getText().toString();
        final String salary = editxt_salary.getText().toString();

        if(keyword.isEmpty()){
            editxt_key_word.setError("keyword is required");
            editxt_key_word.requestFocus();
        }else if(period.isEmpty()){
            editxt_period.setError("periode is required");
            editxt_period.requestFocus();
        }else if(description_intern.isEmpty()){
            editxt_description_internship.setError("description is required");
            editxt_description_internship.requestFocus();
        }else{
            loadingbar.setTitle("save the data");
            loadingbar.setMessage("the data are updating to firebase....");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();
            String randomID =databaseReference.push().getKey();

            String paid=btn_radio_paid.getText().toString();
            String notpaid=btn_radio_notpaid.getText().toString();


            internships.setKeyword(keyword);

            internships.setPeriod(period);

            internships.setDescription_intern(description_intern);

            internships.setSalary(salary);
            CurrentUserID = mAuth.getCurrentUser().getUid();
            internships.setId_company(CurrentUserID);
            internships.setIdinternship(randomID);

            if(btn_radio_paid.isChecked()){
                internships.setPayment(paid);

            }else{
                internships.setPayment(notpaid);

            }
                            databaseReference.child(randomID).setValue(internships).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {

                                    loadingbar.dismiss();
                                    Toast.makeText(AjouterAnnouncesInternshipActivity.this, "Internships announcement publish", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loadingbar.dismiss();
                                    Toast.makeText(AjouterAnnouncesInternshipActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });

        }

    }


}