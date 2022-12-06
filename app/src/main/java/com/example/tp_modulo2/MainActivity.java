package com.example.tp_modulo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tp_modulo2.fragments.HomeFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

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
        //Cambiar idioma
        cambiarIdioma();

        firebaseAuth = firebaseAuth.getInstance();

        //Tabs
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        myViewerPageAdapter = new MyViewerPageAdapter(this);
        viewPager2.setAdapter(myViewerPageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

    }

    public void cambiarIdioma(){

        String[] archivos = fileList();
        if(Archivoexiste(archivos,"lang.txt")){
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("lang.txt"));
                BufferedReader bufferedReader = new BufferedReader(archivo);
                String linea = bufferedReader.readLine();
                String lenguaje = linea;
                bufferedReader.close();
                archivo.close();
                Resources recursos = getResources();
                DisplayMetrics metrics = recursos.getDisplayMetrics();
                Configuration configuracion = recursos.getConfiguration();
                Locale local;
                configuracion.setLocale(new Locale(lenguaje));
                local = new Locale(lenguaje);
                Locale.setDefault(local);
                configuracion.setLocale(local);
                recursos.updateConfiguration(configuracion,metrics);
                configuracion.setLayoutDirection(local);
            }catch (IOException e){

            }

        }
    }

    private boolean Archivoexiste(String[] archivos, String s) {
        for (int i = 0; i< archivos.length;i++){
            if (s.equals(archivos[i])){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
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