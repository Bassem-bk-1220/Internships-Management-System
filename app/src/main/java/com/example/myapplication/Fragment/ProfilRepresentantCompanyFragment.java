package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activity.MainAppActivity;
import com.example.myapplication.Activity.ProfilRepresentantCompanyActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilRepresentantCompanyFragment extends Fragment {

    private CircleImageView editxt_profil_logo;
    private TextView editxt_profile_name,editxt_email_rep,editxt_field,editxt_description_representant,editxt_phone,update_rep_comp;
    ImageView img_logout;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private String CurrentUserid;
      String field="";
      String  description="";
    String profil_logo="";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profil_company_representant, container, false);
        mAuth=FirebaseAuth.getInstance();
        CurrentUserid=mAuth.getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserid);
        editxt_profil_logo=root.findViewById(R.id.CIV_logo_profile_company);
        editxt_profile_name=root.findViewById(R.id.txt_profile_name);
        editxt_email_rep=root.findViewById(R.id.txt_email_rep);
        editxt_field=root.findViewById(R.id.txt_field);
        update_rep_comp=root.findViewById(R.id.btn_update_representant_company);
        img_logout=root.findViewById(R.id.imgv_logout);
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(getContext(), MainAppActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        update_rep_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ProfilRepresentantCompanyActivity.class);
                intent.putExtra("field",field);
                intent.putExtra("description",description);
                intent.putExtra("uploadimage", profil_logo);
                startActivity(intent);
            }
        });
        editxt_description_representant=root.findViewById(R.id.txt_description_representant);
        editxt_phone=root.findViewById(R.id.txt_phone);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("uploadimage").getValue()!=null){
                         profil_logo=snapshot.child("uploadimage").getValue().toString();
                        Picasso.get().load(profil_logo).into(editxt_profil_logo);
                    }

                    String profile_name=snapshot.child("fname").getValue().toString();
                    String phone_rep = snapshot.child("phone").getValue().toString();

                    String email_rep=snapshot.child("email").getValue().toString();

                    if(snapshot.child("field").getValue()!=null) {
                       field=snapshot.child("field").getValue().toString();
                        editxt_field.setText(field);
                    }
                        if(snapshot.child("description").getValue()!=null) {
                             description = snapshot.child("description").getValue().toString();
                            editxt_description_representant.setText(description);
                        }
                    editxt_profile_name.setText(profile_name);
                    editxt_email_rep.setText(email_rep);
                    editxt_phone.setText(phone_rep);
                    //
                   //
                   //
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }




}