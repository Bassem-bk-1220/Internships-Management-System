package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Modele.Data;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ApplyInternshipActivity extends AppCompatActivity {
    EditText txt_name, txt_group, txt_address, txt_country, txt_email, txt_phone;
    TextView cv_upload, btn_apply;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    String CurrentUserID;
    private ProgressDialog loadingbar;
    Uri PdfUri;
String idcompany="";
String idinternship="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_internship);
        txt_country = findViewById(R.id.editxt_country);
        cv_upload = findViewById(R.id.txt_upload_cv);
        btn_apply = findViewById(R.id.btn_apply);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        CurrentUserID = mAuth.getCurrentUser().getUid();
        loadingbar = new ProgressDialog(ApplyInternshipActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("apply_offre");
        idcompany=getIntent().getStringExtra("idcompany");
        idinternship=getIntent().getStringExtra("idinternship");
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();


            }
        });
        cv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ApplyInternshipActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectFile();
                } else {
                    ActivityCompat.requestPermissions((Activity)ApplyInternshipActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });
    }

    private void SaveData() {

        final String country = txt_country.getText().toString();
        final String file = cv_upload.getText().toString();
         if (country.isEmpty()) {
            txt_country.setError("country is required");
            txt_country.requestFocus();
        } else if (file.isEmpty()) {
            cv_upload.setError("put your CV ");
            cv_upload.requestFocus();
        } else {
            loadingbar.setTitle("save the data");
            loadingbar.setMessage("the data are updating to firebase....");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            final String filename=System.currentTimeMillis()+"";
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            storageRef.child("Uploadfiles").child(filename).putFile(PdfUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.child("Uploadfiles").child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String url = uri.toString();
                                    String randomID = databaseReference.push().getKey();

                                    Data data=new Data(url,CurrentUserID,idcompany,idinternship,country,"pending",randomID);
                                    databaseReference.child(randomID).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(ApplyInternshipActivity.this, "data successufully ", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else{
                                                Toast.makeText(ApplyInternshipActivity.this, "data not successufully uploaded", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(ApplyInternshipActivity.this, "File not successufully uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot snapshot) {
                    int currentProgress= (int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    loadingbar.setProgress(currentProgress);
                }
            });

        }
    }
    private void selectFile() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectFile();
        }else{
            Toast.makeText(ApplyInternshipActivity.this, "Please provide permission", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            PdfUri = data.getData();
            cv_upload.setText("file selected:" + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(ApplyInternshipActivity.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }


}