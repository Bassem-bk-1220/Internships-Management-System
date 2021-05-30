package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Modele.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity  {
    EditText text_fullname,text_email,text_phone,text_password;
    TextView btntxt_send_email;
     Spinner spinner;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;


    int maxid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        text_fullname = findViewById(R.id.et_fullname);
        text_email = findViewById(R.id.et_email);
         spinner =findViewById(R.id.spinner_occupation);
        text_phone = findViewById(R.id.edt_phone);
        text_password = findViewById(R.id.edt_password);
        btntxt_send_email =findViewById(R.id.btn_send_email);
        List<String> categories=new ArrayList<>();
        categories.add("Choose occupation");
        categories.add("Etudiant");
        categories.add("Mentor enseignant");
        categories.add("Representant d'entreprise");
        categories.add("Doyen");
        ArrayAdapter<String> dataAdapter;
        dataAdapter =new ArrayAdapter(this,android.R.layout.simple_spinner_item,categories);
// Specify the layout to use when the list of choices appears
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("choose occupation")){

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        btntxt_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fname = text_fullname.getText().toString().trim();
                final String email = text_email.getText().toString().trim();
                final String phone = text_phone.getText().toString().trim();
                final String password = text_password.getText().toString().trim();
                if (fname.isEmpty()) {
                    text_fullname.setError("please provide your fullname");
                    text_fullname.requestFocus();
                } else if (email.isEmpty()) {
                    text_email.setError("Email is required");
                    text_email.requestFocus();
                } else if (phone.isEmpty()) {
                    text_phone.setError("please provide your phone");
                    text_phone.requestFocus();
                } else if (password.isEmpty()) {
                    text_password.setError("please provide your password");
                    text_password.requestFocus();
                }else if (spinner.getSelectedItem().toString().equals("Choose occupation")) {
                    Toast.makeText(RegisterActivity.this, "please provide the occupation", Toast.LENGTH_SHORT).show();
                } else if (password.length() <6) {
                text_password.setError("Min password  length should 6 characters!");
                text_password.requestFocus();
            }
                else if (!(email.isEmpty() && password.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        User user=new User(fname,email,spinner.getSelectedItem().toString(),phone,password);
                                        // Write a message to the database
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        Task<Void> myRef = database.getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            user.setSpinner(spinner.getSelectedItem().toString());

                                                            Toast.makeText(RegisterActivity.this, "user has been registered successuful!",
                                                                    Toast.LENGTH_LONG).show();
                                                              sendemail();
                                                        }else {
                                                            Toast.makeText(RegisterActivity.this, "failed to register, try again!",
                                                                    Toast.LENGTH_LONG).show();

                                                        }
                                                    }
                                                });
                                        database.getReference("Users").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.exists()){
                                                    maxid= (int) snapshot.getChildrenCount();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }

            private void sendemail() {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ text_email.getText().toString()});
                email.putExtra(Intent.EXTRA_SUBJECT, "this your credential");
                email.putExtra(Intent.EXTRA_TEXT, "username"+text_email.getText().toString()+" \n password "+text_password.getText().toString());

                //need this to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }


}