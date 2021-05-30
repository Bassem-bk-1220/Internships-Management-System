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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FileSignatureActivity extends AppCompatActivity {
    private TextView tv_upload_file,tv_send_file;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;
    Uri PdfUri;
    ProgressDialog progressDialog;
    String iddata,from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_signature);
        tv_upload_file=findViewById(R.id.txt_upload_file);
        tv_send_file=findViewById(R.id.btn_send_sig);
        iddata=getIntent().getStringExtra("iddata");
        from=getIntent().getStringExtra("from");
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        tv_upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FileSignatureActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectFile();
                } else {
                    ActivityCompat.requestPermissions((Activity) FileSignatureActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });
        tv_send_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PdfUri != null){
                    UploadFile(PdfUri);
                }else {
                    Toast.makeText(FileSignatureActivity.this, "failed to upload the file ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void UploadFile(Uri pdfUri) {
        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String filename=System.currentTimeMillis()+"";
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("signaturefiles").child(filename).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.child("signaturefiles").child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String url = uri.toString();

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference=database.getReference();
                                if(from.equals("company")){
                                    databaseReference.child("apply_offre").child(iddata).child("filecompany").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(FileSignatureActivity.this, "data successufully ", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else{
                                                Toast.makeText(FileSignatureActivity.this, "data not successufully uploaded", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else if(from.equals("student")){
                                    databaseReference.child("apply_offre").child(iddata).child("filestud").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(FileSignatureActivity.this, "data successufully ", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else{
                                                Toast.makeText(FileSignatureActivity.this, "data not successufully uploaded", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else if(from.equals("prof")){
                                    databaseReference.child("apply_offre").child(iddata).child("fileprof").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(FileSignatureActivity.this, "data successufully ", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else{
                                                Toast.makeText(FileSignatureActivity.this, "data not successufully uploaded", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else if(from.equals("doyen")){
                                    databaseReference.child("apply_offre").child(iddata).child("filedoyen").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(FileSignatureActivity.this, "data successufully ", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else{
                                                Toast.makeText(FileSignatureActivity.this, "data not successufully uploaded", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(FileSignatureActivity.this, "File not successufully uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot snapshot) {
                int currentProgress= (int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    private void selectFile() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectFile();
        }else{
            Toast.makeText(FileSignatureActivity.this, "Please provide permission", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            PdfUri = data.getData();
            tv_upload_file.setText("file selected:" + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(FileSignatureActivity.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }
}