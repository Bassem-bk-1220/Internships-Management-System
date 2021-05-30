package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfilCompanyActivity extends AppCompatActivity {
    private EditText editxt_name_company,editxt_email_company,editxt_field,editxt_address,editxt_description_company;
    private TextView tv_btn_data;
    private CircleImageView logo_company_photo;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private static final int Gallery_Pick=1;
    private StorageReference storageReference;
    private ProgressDialog loadingbar;
    String CurrentUserID;
    Uri imageUri;
    private boolean response;

    public UpdateProfilCompanyActivity(Context mMockContext) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_company_update);
        editxt_name_company=findViewById(R.id.et_company_name);
        editxt_field=findViewById(R.id.et_field);
        editxt_address=findViewById(R.id.et_address);
        editxt_email_company=findViewById(R.id.et_email_company);
        editxt_description_company=findViewById(R.id.et_description_company);
        logo_company_photo=findViewById(R.id.logo_profile_company);
        tv_btn_data=findViewById(R.id.btn_save_data_comp);
        String name_company=getIntent().getStringExtra("nameCompany");
        String address=getIntent().getStringExtra("address");
        String field=getIntent().getStringExtra("field");
        String description=getIntent().getStringExtra("description");
        String logo_image=getIntent().getStringExtra("profileimage");
        String email_company= getIntent().getStringExtra("email_company");
        if(!logo_image.isEmpty()) {
            Picasso.get().load(logo_image).into(logo_company_photo);
        }

        editxt_name_company.setText(name_company);
        editxt_field.setText(field);
        editxt_address.setText(address);
        editxt_description_company.setText(description);
        editxt_email_company.setText(email_company);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        loadingbar = new ProgressDialog(this);
        CurrentUserID = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Profil company");
        storageReference = FirebaseStorage.getInstance().getReference().child("image company");
        logo_company_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Pick);
            }
        });
        tv_btn_data.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ValidateData();


            }
        });

    }

    private void ValidateData() {

        final String name_company = editxt_name_company.getText().toString();
        final String field = editxt_field.getText().toString();
        final String address = editxt_address.getText().toString();
        final String description = editxt_description_company.getText().toString();
        final String email_comp=editxt_email_company.getText().toString();
boolean response=checkEmailForValidity(email_comp);
        if (TextUtils.isEmpty(name_company)) {
            Toast.makeText(UpdateProfilCompanyActivity.this, "Please write your name company...", Toast.LENGTH_LONG).show();
        }if (response==false) {
            Toast.makeText(UpdateProfilCompanyActivity.this, "Please write your email...", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(field)) {
            Toast.makeText(UpdateProfilCompanyActivity.this, "Please write your field...", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(UpdateProfilCompanyActivity.this, "Please write your address...", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(UpdateProfilCompanyActivity.this, "Please write your description...", Toast.LENGTH_LONG).show();
        } if (imageUri == null) {
            Toast.makeText(UpdateProfilCompanyActivity.this, "Please select an image", Toast.LENGTH_LONG).show();
        }else{
            loadingbar.setTitle("adding setup  profile");
            loadingbar.setMessage("the data are updating to firebase....");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();
         if(imageUri!=null) {
             storageReference.child(CurrentUserID).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                     storageReference.child(CurrentUserID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {
                             HashMap hashMap = new HashMap();
                             hashMap.put("nameCompany", name_company);
                             hashMap.put("email_company", email_comp);
                             hashMap.put("address", address);
                             hashMap.put("field", field);
                             hashMap.put("description", description);
                             hashMap.put("profileimage", uri.toString());
                             databaseReference.child(CurrentUserID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                 @Override
                                 public void onSuccess(Object o) {
                                     finish();
                                     loadingbar.dismiss();
                                     Toast.makeText(UpdateProfilCompanyActivity.this, "Setup profile completed", Toast.LENGTH_SHORT).show();
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     loadingbar.dismiss();
                                     Toast.makeText(UpdateProfilCompanyActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                 }
                             });
                         }
                     });
                 }
             });
         }else{
             HashMap hashMap = new HashMap();
             hashMap.put("name company", name_company);
             hashMap.put("email_company", email_comp);
             hashMap.put("address", address);
             hashMap.put("field", field);
             hashMap.put("description", description);
             databaseReference.child(CurrentUserID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                 @Override
                 public void onSuccess(Object o) {
                     finish();
                     loadingbar.dismiss();
                     Toast.makeText(UpdateProfilCompanyActivity.this, "Setup profile completed", Toast.LENGTH_SHORT).show();
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     loadingbar.dismiss();
                     Toast.makeText(UpdateProfilCompanyActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                 }
             });
         }
        }

    }
    public static boolean checkEmailForValidity(String email) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();

    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null) {
            imageUri=data.getData();
            logo_company_photo.setImageURI(imageUri);
        }
    }
}