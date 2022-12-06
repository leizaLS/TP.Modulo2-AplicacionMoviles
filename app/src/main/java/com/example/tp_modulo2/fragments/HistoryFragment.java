package com.example.tp_modulo2.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Set;

public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<MainPosts> recyclerList;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    Set<String> favoritos;
    SharedPreferences preferences;
    String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generarFavoritos(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        clear();
        generarFavoritos(this.getView());
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
    private void generarFavoritos(View view){
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
        userId = firebaseAuth.getCurrentUser().getUid();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("favoritos", Context.MODE_PRIVATE);
        favoritos = preferences.getStringSet(userId, null);







        if (favoritos != null){
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recyclerList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MainPosts mainPosts = dataSnapshot.getValue(MainPosts.class);
                    if (favoritos.contains(mainPosts.getpId())) {
                        recyclerList.add(mainPosts);
                    }
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        } else {
            Toast.makeText(getActivity(),"NO HAY FAVORITOS", Toast.LENGTH_SHORT).show();
        }
    }
}
