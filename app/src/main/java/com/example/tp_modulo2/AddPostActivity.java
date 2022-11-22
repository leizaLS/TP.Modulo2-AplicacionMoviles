package com.example.tp_modulo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddPostActivity extends AppCompatActivity {
    //Utils
    FirebaseAuth firebaseAuth;
    DatabaseReference userDbRef;
    //ActionBar actionBar;

    EditText titleEt, descriptionEt;
    ImageView imageIv;
    Button uploadBtn;

    //Datos usuario
    String name, email, uid, dp;
    Uri image_rui = null;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //Le pongo titulo a la seccion
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add new post");

        //pd
        pd = new ProgressDialog(this);

        //UsuarioActual
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        email = user.getEmail();
        uid = user.getUid();

        userDbRef = FirebaseDatabase.getInstance().getReference("Users");

        Query query = userDbRef.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    name = "" + ds.child("name").getValue();
                    email = "" + ds.child("image").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //====

        titleEt = findViewById(R.id.pTiTleEt);
        descriptionEt = findViewById(R.id.pDescriptionEt);
        imageIv = findViewById(R.id.pImageIv);
        uploadBtn = findViewById(R.id.pUploadBtn);

        //Listener del boton Publicar
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEt.getText().toString().trim();
                String description = descriptionEt.getText().toString().trim();

                //Subir publicacion, falta validaciones y otros
//                pd.setMessage("Publicando...");
//                pd.show();
                //StorageReference ref = FirebaseStorage.getInstance().getReference();


            }
        });
    }
}