package com.example.tp_modulo2.fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tp_modulo2.MainActivity;
import com.example.tp_modulo2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class PerfilFragment extends Fragment{

    //Fifebase
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //views from xml
    ImageView avatarU;
    TextView nombreU, correoU, telefonoU;

    //idiomas
    private ListView cambiarIdioma;
    private ArrayList<String> listaIdiomas;
    private int changeCounter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        View view2 = inflater.inflate(R.layout.activity_add_post,container,false);

        //init firebase

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        //init list idiomas
        cambiarIdioma = view.findViewById(R.id.listaIdiomas);
        listaIdiomas = new ArrayList<String>();
        listaIdiomas.add(getString(R.string.perfilFSpanish));
        listaIdiomas.add(getString(R.string.perfilFEnglish));
        listaIdiomas.add(getString(R.string.perfilFFrench));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, listaIdiomas);
        cambiarIdioma.setAdapter(adapter);
        cambiarIdioma.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                save(listaIdiomas.get(position));
            }
        });



        //init views
        nombreU = view.findViewById(R.id.nombreU);
        correoU = view.findViewById(R.id.correoU);
        telefonoU = view.findViewById(R.id.telefonoU);
        avatarU = view.findViewById(R.id.avatarU);

        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //check

                for(DataSnapshot ds: snapshot.getChildren()){

                    //get

                    String nombre = "" + ds.child("name").getValue();
                    String correo = "" + ds.child("email").getValue();
                    String telefono = "" + ds.child("phoneNumber").getValue();
                    String imagen = "" + ds.child("image").getValue();
                    //set

                    nombreU.setText(nombre);
                    correoU.setText(correo);
                    telefonoU.setText(telefono);

                    try {
                        Picasso.get().load(imagen).into(avatarU);
                    }
                    catch (Exception e){
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
    public PerfilFragment(){

    }
    public void save (String langSelect){
        try {
            String lenguajeSeleccionado = "es";
            switch (langSelect){
                case "Español":
                    lenguajeSeleccionado = "es";
                    break;
                case "English":
                    lenguajeSeleccionado = "en";
                    break;
                case "Français":
                    lenguajeSeleccionado = "fr";
                    break;
                default:
                    lenguajeSeleccionado = "es";
                    break;
            }
            OutputStreamWriter archivo = new OutputStreamWriter(getActivity().openFileOutput("lang.txt", Activity.MODE_PRIVATE));
            archivo.write(lenguajeSeleccionado);
            archivo.flush();
            archivo.close();

        }catch (IOException e) {

        }
    }

}