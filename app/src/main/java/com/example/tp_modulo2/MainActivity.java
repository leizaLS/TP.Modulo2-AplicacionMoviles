package com.example.tp_modulo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    //Tabs de navegacion
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MyViewerPageAdapter myViewerPageAdapter;

    //ShowData
    RecyclerView recyclerView;
    ArrayList<MainPosts> recyclerList;
    FirebaseDatabase firebaseDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = firebaseAuth.getInstance();

        //Tabs
//        tabLayout = findViewById(R.id.tab_layout);
//        viewPager2 = findViewById(R.id.view_pager);
//        myViewerPageAdapter = new MyViewerPageAdapter(this);
//        viewPager2.setAdapter(myViewerPageAdapter);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager2.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) { }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) { }
//        });
//
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                tabLayout.getTabAt(position).select();
//            }
//        });

        //ShowData
        recyclerView = findViewById(R.id.recyclerView);
        recyclerList = new ArrayList<>();
        PostAdapter recyclerAdapter = new PostAdapter(recyclerList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "Actualizando posts de firebase", Toast.LENGTH_LONG).show();
        recyclerList = new ArrayList<>();
        PostAdapter recyclerAdapter = new PostAdapter(recyclerList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(recyclerAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recyclerList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MainPosts mainPosts = dataSnapshot.getValue(MainPosts.class);
                    recyclerList.add(mainPosts);
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    //Mostrar menu de log out en main
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Accion de boton log out
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //boton deslogin
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this, Login.class));
        }
        //boton crearPost
        if(id == R.id.action_add_post) {
            startActivity(new Intent(MainActivity.this, AddPostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}