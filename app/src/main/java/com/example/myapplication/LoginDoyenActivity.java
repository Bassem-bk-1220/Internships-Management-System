package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginDoyenActivity extends AppCompatActivity {
    EditText edittxt_email, edittxt_password;
    TextView txtview_forget_password, txtview_login;
    ImageView logo;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recyclerView;
    FirebaseAuth mFirebaseAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doyen);
        toolbar=findViewById(R.id.main_page_tollbar);
        setSupportActionBar(toolbar);
        logo=findViewById(R.id.img_logo);
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
        drawerLayout=findViewById(R.id.drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(LoginDoyenActivity.this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=findViewById(R.id.navigation_view);
        View navView =navigationView.inflateHeaderView(R.layout.navigation_header);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        txtview_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edittxt_email.getText().toString();
                final String password = edittxt_password.getText().toString();
                if (username.isEmpty()) {
                    edittxt_email.setError("please provide your username");
                    edittxt_email.requestFocus();
                } else if (password.isEmpty()) {
                    edittxt_password.setError("please provide your password");
                    edittxt_password.requestFocus();
                } else if (!(username.isEmpty() && password.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(LoginDoyenActivity.this, new OnCompleteListener<AuthResult>() {
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
                                                   Intent intent = new Intent(getApplicationContext(), SignatureFragment.class);
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

                                        Toast.makeText(LoginDoyenActivity.this, "Authentication failed.",
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}