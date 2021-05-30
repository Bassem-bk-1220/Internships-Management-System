package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class LoginActivity extends AppCompatActivity {
    EditText edittxt_email, edittxt_password;
    TextView txtview_forget_password, txtview_login;
    ImageView logo,img_back;
    FirebaseAuth mFirebaseAuth ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logo=findViewById(R.id.img_logo);
        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MainAppActivity.class);
                startActivity(intent);
                finish();
            }
        });
       String from= getIntent().getStringExtra("from");
        if(from.equals("etudiant")){
            logo.setImageResource(R.drawable.img_students);
        }else  if(from.equals("represetant d'entreprise")){
            logo.setImageResource(R.drawable.img_company);
        }else  if(from.equals("mentor enseignant")){
            logo.setImageResource(R.drawable.img_professor);
        }else  if(from.equals("doyen")){
            logo.setImageResource(R.drawable.img_doyen);
        }

        edittxt_email = findViewById(R.id.et_email);
        edittxt_password = findViewById(R.id.et_password);
        txtview_forget_password = findViewById(R.id.txt_forget_password);
        txtview_login = findViewById(R.id.txt_login);


        mFirebaseAuth = FirebaseAuth.getInstance();
        txtview_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edittxt_email.getText().toString();
                final String password = edittxt_password.getText().toString();
              String response =  validate_sign(username, password);
                if (response.equals("please provide your email")) {
                    edittxt_email.setError("please provide your username");
                    edittxt_email.requestFocus();
                } else if (response.equals("please provide your password")) {
                    edittxt_password.setError("please provide your password");
                    edittxt_password.requestFocus();
                } else if (response.equals("the email and password correct")) {
                    mFirebaseAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        database.getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                // This method is called once with the initial value and again
                                                // whenever data at this location is updated.
                                                User user = dataSnapshot.getValue(User.class);
                                                if(user.spinner.equals("Etudiant")) {
                                                    Intent intent = new Intent(getApplicationContext(), MenuStudentActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else if(user.spinner.equals("Mentor enseignant")) {
                                                   Intent intent = new Intent(getApplicationContext(), VerifyFilePratiqueActivity.class);
                                                   startActivity(intent);
                                                   finish();
                                               }else if(user.spinner.equals("Representant d'entreprise")) {
                                                    Intent intent = new Intent(getApplicationContext(), MenuCompanyActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else if(user.spinner.equals("Doyen")) {
                                                    Intent intent = new Intent(getApplicationContext(), MenuDoyenActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                // Failed to read value

                                            }
                                        });


                                    } else {

                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        txtview_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public String validate_sign(String email, String password) {

        if (email.isEmpty()) {
           return "please provide your email";
        } else if (password.isEmpty()) {
            return "please provide your password";
        } else {
            return "the email and password correct";
        }
    }
}