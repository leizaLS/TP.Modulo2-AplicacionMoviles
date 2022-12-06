package com.example.tp_modulo2.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp_modulo2.MainPosts;
import com.example.tp_modulo2.PostAdapter;
import com.example.tp_modulo2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserPosts extends Fragment {

    RecyclerView recyclerView;
    ArrayList<MainPosts> recyclerList;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void clear() {

        if (recyclerList !=null &&recyclerList.size() > 0){
        int size = recyclerList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                recyclerList.remove(0);
            }
        }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        clear();
        generarPostUsuario(view);

       /* firebaseAuth = firebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerList = new ArrayList<>();
        PostAdapter recyclerAdapter = new PostAdapter(recyclerList, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(recyclerAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        String userEmail = firebaseAuth.getCurrentUser().getEmail();
        DatabaseReference db =firebaseDatabase.getReference("Post");

        Query query = db.orderByChild("uEmail").equalTo(userEmail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MainPosts mainPosts = dataSnapshot.getValue(MainPosts.class);
                    recyclerList.add(mainPosts);
                }
                recyclerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }





    private void generarPostUsuario(View view){
        firebaseAuth = firebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerList = new ArrayList<>();
        PostAdapter recyclerAdapter = new PostAdapter(recyclerList, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(recyclerAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        String userEmail = firebaseAuth.getCurrentUser().getEmail();
        DatabaseReference db =firebaseDatabase.getReference("Post");

        Query query = db.orderByChild("uEmail").equalTo(userEmail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MainPosts mainPosts = dataSnapshot.getValue(MainPosts.class);
                    recyclerList.add(mainPosts);
                }
                recyclerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}