package com.example.myapplication;

import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.UserAdapter;
import com.example.myapplication.R;
import com.example.myapplication.RegisterActivity;
import com.example.myapplication.User;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list, container, false);

        Button btn = (Button) root.findViewById(R.id.button);
        recyclerView=root.findViewById(R.id.recycle_list);

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