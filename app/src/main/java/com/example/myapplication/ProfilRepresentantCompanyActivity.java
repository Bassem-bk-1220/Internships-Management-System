package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilRepresentantCompanyActivity extends AppCompatActivity {
    private CircleImageView CIV_profile;
    private EditText txt_field,txt_description;
    private TextView txtupdate_profil_representant;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private static final int Gallery_Pick=1;
    private StorageReference storageReference;
    private ProgressDialog loadingbar;
    String CurrentUserID;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_company_representant);

        txt_field = findViewById(R.id.et_field);
        txt_description = findViewById(R.id.et_description);
        String field=getIntent().getStringExtra("field");
        String description=getIntent().getStringExtra("description");
        String profil_logo=getIntent().getStringExtra("profile image");

        txt_field.setText(field);
        txt_description.setText(description);
        txtupdate_profil_representant = findViewById(R.id.btn_update_profil_representant);
        CIV_profile = findViewById(R.id.logo_profile_representant_company);
        Picasso.get().load(profil_logo).into(CIV_profile);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        loadingbar = new ProgressDialog(this);
        CurrentUserID = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("image profile");
        txtupdate_profil_representant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ValidateData();

            }
        });
        CIV_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Pick);
            }
        });
    }
        private void ValidateData() {


            final String field = txt_field.getText().toString();
            final String description = txt_description.getText().toString();





            if (TextUtils.isEmpty(field)) {
                Toast.makeText(ProfilRepresentantCompanyActivity.this, "Please write your field...", Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(description)) {
                Toast.makeText(ProfilRepresentantCompanyActivity.this, "Please write your description...", Toast.LENGTH_LONG).show();
            } if (imageUri == null) {
                Toast.makeText(ProfilRepresentantCompanyActivity.this, "Please select an image", Toast.LENGTH_LONG).show();
            } else {
                loadingbar.setTitle("adding setup  profile");
                loadingbar.setMessage("the data are updating to firebase....");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

                storageReference.child(CurrentUserID).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        storageReference.child(CurrentUserID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap hashMap = new HashMap();
                                hashMap.put("field", field);
                                hashMap.put("description", description);
                                hashMap.put("profile image", uri.toString());
                                databaseReference.child(CurrentUserID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        finish();
                                        loadingbar.dismiss();
                                        Toast.makeText(ProfilRepresentantCompanyActivity.this, "Setup profile completed", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        loadingbar.dismiss();
                                        Toast.makeText(ProfilRepresentantCompanyActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null) {
                imageUri=data.getData();
                CIV_profile.setImageURI(imageUri);
            }
        }

}