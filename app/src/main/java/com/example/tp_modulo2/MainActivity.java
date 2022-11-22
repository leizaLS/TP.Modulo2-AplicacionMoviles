package com.example.tp_modulo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = firebaseAuth.getInstance();
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