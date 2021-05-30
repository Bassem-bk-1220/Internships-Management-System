package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapters.OffreAdapter;
import com.example.myapplication.Modele.Data;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOffreFragment extends Fragment {
    RecyclerView recyclerView;
    OffreAdapter adapter;
    ArrayList<Data> listall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View  root= inflater.inflate(R.layout.fragment_list_offre, container, false);
        recyclerView=root.findViewById(R.id.recycle_offre);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        DatabaseReference myref;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref= database.getReference("apply_offre");
        listall=new ArrayList<>();

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listall.clear();
                for(DataSnapshot dataValues:snapshot.getChildren()){
                  Data demande= dataValues.getValue(Data.class);
                    if(demande.iduser.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        listall.add(demande);



                }
                adapter=new OffreAdapter(getActivity(),listall);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
}