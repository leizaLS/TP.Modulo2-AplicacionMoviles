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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tp_modulo2.MainActivity;
import com.example.tp_modulo2.MainPosts;
import com.example.tp_modulo2.PostAdapter;
import com.example.tp_modulo2.R;
import com.example.tp_modulo2.Register;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<MainPosts> recyclerList;
    FirebaseDatabase firebaseDatabase;
    Spinner spinner;
    String[] options = {"Más recientes", "Más antiguos", "Por zona"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Spinner
        spinner = (Spinner) view.findViewById(R.id.optionPost);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        //Botones de Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //mas nuevos
                    recyclerView.removeAllViewsInLayout(); //limpia recyclerView
                    //Peticion de datos Firebase
                    recyclerList.clear();
                    PostAdapter recyclerAdapter = new PostAdapter(recyclerList, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setAdapter(recyclerAdapter);

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                MainPosts mainPosts = dataSnapshot.getValue(MainPosts.class);
                                recyclerList.add(mainPosts);
                            }
                            Collections.reverse(recyclerList);
                            recyclerAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                    //
                }

                if (position == 1) {
                    //mas antiguos
                    recyclerView.removeAllViewsInLayout(); //limpia recyclerView
                    //Peticion de datos Firebase
                    recyclerList.clear();
                    PostAdapter recyclerAdapter = new PostAdapter(recyclerList, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setAdapter(recyclerAdapter);

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                MainPosts mainPosts = dataSnapshot.getValue(MainPosts.class);
                                recyclerList.add(mainPosts);
                            }
                            recyclerAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                    //
                }
                if (position == 2) {
                    //por ciudad
                    recyclerView.removeAllViewsInLayout(); //limpia recyclerView
                    //Peticion de datos Firebase
                    recyclerList.clear();
                    PostAdapter recyclerAdapter = new PostAdapter(recyclerList, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setAdapter(recyclerAdapter);

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("Post").orderByChild("city").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                MainPosts mainPosts = dataSnapshot.getValue(MainPosts.class);
                                recyclerList.add(mainPosts);
                            }
                            recyclerAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                    //
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Lo usaremos mas tarde
        //Nota: En los context, this va getActivity()

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerList = new ArrayList<>();

        PostAdapter recyclerAdapter = new PostAdapter(recyclerList, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(recyclerAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.getReference().child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    MainPosts mainPosts = dataSnapshot.getValue(MainPosts.class);
//                    recyclerList.add(mainPosts);
//                }
//                Collections.reverse(recyclerList);
//                recyclerAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) { }
//        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        //Pedimos de vuelta los datos
//        recyclerView = getActivity().findViewById(R.id.recyclerView);
//        recyclerList = new ArrayList<>();
//        PostAdapter recyclerAdapter = new PostAdapter(recyclerList, getActivity());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setAdapter(recyclerAdapter);
//
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.getReference().child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                recyclerList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    MainPosts mainPosts = dataSnapshot.getValue(MainPosts.class);
//                    recyclerList.add(mainPosts);
//                }
//                Collections.reverse(recyclerList);
//                recyclerAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) { }
//        });
//    }
}