package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activity.ProfilStudentActivity;
import com.example.myapplication.Activity.ShowCVActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilStudentFragment extends Fragment {
    private CircleImageView img_stud;
    private TextView txtv_name_stud,txtv_email_stud,txtv_bio,txtv_group,txtv_specialisation,txtv_phone,txtv_address,txtv_update_stud,txtv_file;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private String CurrentUserid;
    String logo_stud="";
    String bio="";
    String group="";
    String specialisation="";
    String file_upload="";
    String address="";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profil_student, container, false);
        txtv_name_stud=root.findViewById(R.id.tv_name_stud);
        txtv_email_stud=root.findViewById(R.id.tv_email_stud);
        txtv_bio=root.findViewById(R.id.tv_bio);
        txtv_group=root.findViewById(R.id.tv_group);
        txtv_specialisation=root.findViewById(R.id.tv_specialisation);
        txtv_phone=root.findViewById(R.id.tv_phone);
        txtv_update_stud=root.findViewById(R.id.btn_update_info_stud);
        txtv_address=root.findViewById(R.id.tv_address);
        img_stud=root.findViewById(R.id.logo_student);
        txtv_file=root.findViewById(R.id.tv_file);
        mAuth=FirebaseAuth.getInstance();
        CurrentUserid=mAuth.getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserid);
        txtv_update_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ProfilStudentActivity.class);
                intent.putExtra("uploadimage",logo_stud);
                intent.putExtra("bio",bio);
                intent.putExtra("group",group);
                intent.putExtra("address",address);
                intent.putExtra("specialisation",specialisation);
                intent.putExtra("uploadfile",file_upload);
                startActivity(intent);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("uploadimage").getValue()!=null){
                        logo_stud=snapshot.child("uploadimage").getValue().toString();
                        Picasso.get().load(logo_stud).into(img_stud);
                    }
                  String name_stud=snapshot.child("fname").getValue().toString();
                    String phone_stud = snapshot.child("phone").getValue().toString();
                   String email_stud=snapshot.child("email").getValue().toString();

                    if(snapshot.child("bio").getValue()!=null) {
                        bio=snapshot.child("bio").getValue().toString();
                        txtv_bio.setText(bio);
                    }
                    if(snapshot.child("address").getValue()!=null) {
                        address=snapshot.child("address").getValue().toString();
                        txtv_address.setText(address);
                    }
                    if(snapshot.child("group").getValue()!=null) {
                        group=snapshot.child("group").getValue().toString();
                        txtv_group.setText(group);
                    }
                    if(snapshot.child("specialisation").getValue()!=null) {
                        specialisation=snapshot.child("specialisation").getValue().toString();
                        txtv_specialisation.setText(specialisation);
                    }
                    if(snapshot.child("uploadfile").getValue()!=null) {
                        file_upload=snapshot.child("uploadfile").getValue().toString();
                       txtv_file.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Intent intent=new Intent(getActivity(), ShowCVActivity.class);
                               intent.putExtra("file_upload",file_upload);
                               startActivity(intent);
                           }
                       });
                    }
                    txtv_name_stud.setText(name_stud);
                    txtv_email_stud.setText(email_stud);
                    txtv_phone.setText(phone_stud);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }

}