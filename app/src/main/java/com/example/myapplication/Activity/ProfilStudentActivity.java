package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilStudentActivity extends AppCompatActivity {
    EditText edtxtbio,edtxtgroup,edtxtspecialisation, edtxt_address;
    TextView txtvfile,txtvupdate;
    CircleImageView txtphoto;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;
    Uri PdfUri;
    private DatabaseReference databaseReference;
    private static final int Gallery_Pick=1;
    private StorageReference storageReference;
    ProgressDialog progressDialog;
    String CurrentUserID;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_student);
        edtxtgroup = findViewById(R.id.et_group);
        edtxtspecialisation =findViewById(R.id.et_specialization);
        txtvfile =findViewById(R.id.txt_upload);
        txtphoto=findViewById(R.id.logo_profile_student);
        txtvupdate =findViewById(R.id.btn_update_stud);
        edtxt_address=findViewById(R.id.et_address);
        edtxtbio=findViewById(R.id.edt_bio);
        String logo_stud=getIntent().getStringExtra("uploadimage");
        if(!logo_stud.isEmpty()) {
            Picasso.get().load(logo_stud).into(txtphoto);
        }
        String bio=getIntent().getStringExtra("bio");
        String group=getIntent().getStringExtra("group");
        String specialisation=getIntent().getStringExtra("specialisation");
        String file_upload=getIntent().getStringExtra("uploadfile");
        String address=getIntent().getStringExtra("address");

        edtxtbio.setText(bio);
        edtxtgroup.setText(group);
        edtxtspecialisation.setText(specialisation);
        txtvfile.setText(file_upload);
        edtxt_address.setText(address);
        mFirebaseAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        CurrentUserID = mFirebaseAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("image Student");
        txtphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Pick);
            }
        });
        txtvfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ProfilStudentActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectFile();
                } else {
                    ActivityCompat.requestPermissions(ProfilStudentActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });
        txtvupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String group=edtxtgroup.getText().toString();
                final String specialisation=edtxtspecialisation.getText().toString();
                final String uploadfile=txtvfile.getText().toString();
                final String bio=edtxtbio.getText().toString();
                final String address=edtxt_address.getText().toString();

if(imageUri!=null){


                storageReference.child(CurrentUserID).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                storageReference.child(CurrentUserID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final String uploadimage=uri.toString();
                                        HashMap profilStudent=new HashMap();
                                        profilStudent.put("group",group);
                                        profilStudent.put("specialisation",specialisation);
                                        profilStudent.put("bio",bio);
                                        profilStudent.put("address",address);
                                        profilStudent.put("uploadimage",uploadimage);

                                        databaseReference.child(CurrentUserID).updateChildren(profilStudent).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                if(PdfUri != null){
                                                    UploadFile(PdfUri);
                                                }else {
                                                    Toast.makeText(ProfilStudentActivity.this, " ", Toast.LENGTH_SHORT).show();
                                                }
                                                Toast.makeText(ProfilStudentActivity.this, "Profil Students has been registered successuful!",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(ProfilStudentActivity.this, "failed to register, try again!",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                });
                            }
                        });
            }else{
    HashMap profilStudent=new HashMap();
    profilStudent.put("group",group);
    profilStudent.put("specialisation",specialisation);
    profilStudent.put("bio",bio);
    profilStudent.put("address",address);
    databaseReference.child(CurrentUserID).updateChildren(profilStudent).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            if(PdfUri != null){
                UploadFile(PdfUri);
            }else {
                Toast.makeText(ProfilStudentActivity.this, " ", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(ProfilStudentActivity.this, "Profil Students has been registered successuful!",
                    Toast.LENGTH_LONG).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(ProfilStudentActivity.this, "failed to register, try again!",
                    Toast.LENGTH_LONG).show();
        }
    });
            }
            }
        });

    }

    private void UploadFile(Uri pdf) {
        progressDialog=new ProgressDialog(ProfilStudentActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.show();
        final String filename=System.currentTimeMillis()+"";
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        if(pdf!=null) {
            storageRef.child("Uploadfiles").child(filename).putFile(pdf)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.child("Uploadfiles").child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String uploadfile = uri.toString();

                                    HashMap profilStudent = new HashMap();
                                    profilStudent.put("uploadfile", uploadfile);

                                    databaseReference.child(CurrentUserID).updateChildren(profilStudent).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                finish();
                                                Toast.makeText(ProfilStudentActivity.this, "File successufully uploaded", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ProfilStudentActivity.this, "File not successufully uploaded", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(ProfilStudentActivity.this, "File not successufully uploaded", Toast.LENGTH_SHORT).show();
                }

            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot snapshot) {
                    int currentProgress = (int) (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setProgress(currentProgress);
                }
            });
        }else{


            HashMap profilStudent = new HashMap();

            databaseReference.child(CurrentUserID).updateChildren(profilStudent).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        finish();
                        Toast.makeText(ProfilStudentActivity.this, "File successufully uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfilStudentActivity.this, "File not successufully uploaded", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectFile();
        }else{
            Toast.makeText(ProfilStudentActivity.this, "Please provide permission", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            PdfUri = data.getData();
            txtvfile.setText( PdfUri.getLastPathSegment());

        } else if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            imageUri= data.getData();
            txtphoto.setImageURI(imageUri);

        }else {
            Toast.makeText(ProfilStudentActivity.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectFile() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }
}