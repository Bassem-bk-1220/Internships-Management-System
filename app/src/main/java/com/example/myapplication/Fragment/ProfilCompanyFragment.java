package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activity.InternshipOfTheCompanyActivity;
import com.example.myapplication.Activity.UpdateProfilCompanyActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilCompanyFragment extends Fragment {

    private CircleImageView photo_company;
    private TextView tv_name,tv_field,tv_description,tv_email,tv_address_company,btn_offres,update_info_company;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private String CurrentUserid;
    String name_company="";
    String logo_company="";
    String address_company="";
    String field_company="";
    String description_company="";
    String email_company="";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profil_company, container, false);
        mAuth=FirebaseAuth.getInstance();
        CurrentUserid=mAuth.getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Profil company").child(CurrentUserid);
        tv_name=root.findViewById(R.id.tv_name_company);
        tv_field=root.findViewById(R.id.tv_field);
        tv_description=root.findViewById(R.id.tv_description_company);
        tv_email=root.findViewById(R.id.tv_email_company);
        tv_address_company=root.findViewById(R.id.tv_address);
        btn_offres=root.findViewById(R.id.btn_internships);
        update_info_company=root.findViewById(R.id.btn_update_info_company);

        update_info_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UpdateProfilCompanyActivity.class);
                intent.putExtra("nameCompany",name_company);
                intent.putExtra("profileimage",logo_company);
                intent.putExtra("address",address_company);
                intent.putExtra("field",field_company);
                intent.putExtra("email_company",email_company);
                intent.putExtra("description",description_company);

                startActivity(intent);

            }
        });

        photo_company=root.findViewById(R.id.logo_photo_profile_company);
        btn_offres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), InternshipOfTheCompanyActivity.class);
                startActivity(intent);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("profileimage").getValue()!=null){
                        logo_company=snapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(logo_company).into(photo_company);
                    }
                    if(snapshot.child("nameCompany").getValue()!=null){
                         name_company=snapshot.child("nameCompany").getValue().toString();
                        tv_name.setText(name_company);
                    }
                    if(snapshot.child("email_company").getValue()!=null){
                        email_company=snapshot.child("email_company").getValue().toString();
                        tv_email.setText(email_company);
                    }
                    if(snapshot.child("address").getValue()!=null){
                         address_company=snapshot.child("address").getValue().toString();
                        tv_address_company.setText(address_company);
                    }
                    if(snapshot.child("field").getValue()!=null){
                        field_company=snapshot.child("field").getValue().toString();
                        tv_field.setText(field_company);
                    }
                    if(snapshot.child("description").getValue()!=null){
                         description_company=snapshot.child("description").getValue().toString();
                        tv_description.setText(description_company);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }




}