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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.MainAppActivity;
import com.example.myapplication.Activity.RegisterActivity;
import com.example.myapplication.Adapters.UserAdapter;
import com.example.myapplication.Modele.User;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {



RecyclerView recyclerView;
    UserAdapter adapter;
    ImageView img_logout;
    FirebaseAuth firebaseAuth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list, container, false);

        TextView btn =  root.findViewById(R.id.button);
        recyclerView=root.findViewById(R.id.recycle_list);
        img_logout=root.findViewById(R.id.imgv_logout);
        firebaseAuth=FirebaseAuth.getInstance();
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent=new Intent(getContext(), MainAppActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        DatabaseReference myref;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
          myref= database.getReference("Users");
        final List<User> listall=new ArrayList<>();
        final List<User> mylist=new ArrayList<>();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for(DataSnapshot dataValues:dataSnapshot.getChildren()){
                    User user = dataValues.getValue(User.class);
                    mylist.add(user);
                    listall.add(user);
                }
              adapter=new UserAdapter(getActivity(),mylist);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

root.findViewById(R.id.btn_company).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mylist.clear();
        for(User user:listall){
            if(user.getSpinner().equals("Representant d'entreprise")){

                mylist.add(user);
            }

        }
       adapter.notifyDataSetChanged();

    }
});

root.findViewById(R.id.btn_student).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mylist.clear();
        for(User user:listall){
            if(user.getSpinner().equals("Etudiant")){

                mylist.add(user);
            }

        }
        adapter.notifyDataSetChanged();
    }
});
        root.findViewById(R.id.btn_teacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylist.clear();
                for(User user:listall){
                    if(user.getSpinner().equals("Mentor enseignant")){

                        mylist.add(user);
                    }

                }
                adapter.notifyDataSetChanged();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });


        return root;
    }
}