package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.User;

import java.util.List;

public class ListInternshipAdapter extends RecyclerView.Adapter<ListInternshipViewHolder>{
    private Context context;
    private List<> listinternshipList;
    public ListInternshipAdapter(Context context,List<>list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public ListInternshipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListInternshipViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
