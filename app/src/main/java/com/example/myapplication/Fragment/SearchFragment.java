package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.MainAppActivity;
import com.example.myapplication.Adapters.ListInternshipAdapter;
import com.example.myapplication.Modele.Internships;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

EditText editwxt_search,editxt_payment;
ImageView img_logout;
FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    ListInternshipAdapter adapter;
    ArrayList<Internships>listall;
    ArrayList<Internships>listSearch;
    Spinner spinner;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView=root.findViewById(R.id.recycle_search);
        editwxt_search=root.findViewById(R.id.et_keyword_search);
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
        editwxt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               String textSearch= editwxt_search.getText().toString();
               if(textSearch.isEmpty()){
                   adapter=new ListInternshipAdapter(getActivity(),listall);
                   recyclerView.setAdapter(adapter);
               }else{
               listSearch.clear();
                for (Internships item:listall) {
                   if(item.getKeyword().contains(textSearch)){
                       listSearch.add(item);
                   }
                }
                adapter=new ListInternshipAdapter(getActivity(),listSearch);
                recyclerView.setAdapter(adapter);
            }}
        });
        spinner =root.findViewById(R.id.spinner_pay);
        editxt_payment=root.findViewById(R.id.et_payment_filter);
        List<String> categories=new ArrayList<>();
        categories.add("choose option");
        categories.add("Pay");
        categories.add("NotPay");
        ArrayAdapter<String> dataAdapter;
        dataAdapter =new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,categories);
// Specify the layout to use when the list of choices appears
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("choose option")){

                        adapter=new ListInternshipAdapter(getActivity(),listall);
                        recyclerView.setAdapter(adapter);
                    }
                else if(parent.getItemAtPosition(position).equals("Pay")){
                    listSearch.clear();
                    for (Internships item:listall) {
                        if(item.getPayment().equals("Paid")){
                            listSearch.add(item);
                        }
                    }
                    adapter=new ListInternshipAdapter(getActivity(),listSearch);
                    recyclerView.setAdapter(adapter);
                }
                else if(parent.getItemAtPosition(position).equals("NotPay")){
                    listSearch.clear();
                    for (Internships item:listall) {
                        if(item.getPayment().equals("Not Paid")){
                            listSearch.add(item);
                        }
                    }
                    adapter=new ListInternshipAdapter(getActivity(),listSearch);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        DatabaseReference refer;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        refer=database.getReference("AnnonceStage");
        listall=new ArrayList<>();
        listSearch=new ArrayList<>();
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataValues:snapshot.getChildren()){
                    Internships internships = dataValues.getValue(Internships.class);
                    listall.add(internships);

                }
                adapter=new ListInternshipAdapter(getActivity(),listall);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

}