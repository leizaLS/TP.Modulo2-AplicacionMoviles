package com.example.tp_modulo2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class PostDetails extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnCameraMoveListener {
    //Partes del detalle (ids)
    ImageView details_img;
    TextView details_title, details_desc, details_date, details_location;
    String latitude, longitude;
    GoogleMap mMap;
    Button whatsappMsg;
    String phoneNumber = "";
    Button btnDescarga;
    Button btnEliminarPost;
    Button btnEditarPost;
    private static final int REQUEST_CODE = 1;
    LinearLayout linearLayout;
    FirebaseDatabase firebaseDatabase;
    String postEmail;
    String emailUser;
    String pId;
    ImageView favoritoImg;
    Set<String> favoritos;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        details_img = findViewById(R.id.details_img);
        details_title = findViewById(R.id.details_title);
        details_desc = findViewById(R.id.details_desc);
        details_date= findViewById(R.id.details_date);
        details_location = findViewById(R.id.details_location);
        whatsappMsg = findViewById(R.id.details_msg);
        btnDescarga = (Button) findViewById(R.id.btn_descarga);
        linearLayout = findViewById(R.id.crud_post);
        btnEliminarPost = (Button) findViewById(R.id.btn_eliminar_post);
        btnEditarPost = findViewById(R.id.btn_editar_post);
        favoritoImg = findViewById(R.id.favorito);

        favoritoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarImagenFavorito();
            }
        });



        Picasso.get().load(getIntent().getStringExtra("details_img"))
                .placeholder(R.drawable.transparent_picture).into(details_img);

        emailUser = getIntent().getStringExtra("userEmail");
        postEmail = getIntent().getStringExtra("postEmail");
        pId = getIntent().getStringExtra("pId");

        cargarDatosSP();
        comprobarFavorito();


        details_title.setText(getIntent().getStringExtra("details_title"));
        details_desc.setText(getIntent().getStringExtra("details_desc"));
        details_date.setText(getIntent().getStringExtra("details_date"));
        details_location.setText(getIntent().getStringExtra("details_location"));
        phoneNumber = getIntent().getStringExtra("phoneNumber");


        //SI ES PROPIETARIO HABILITA LOS EDIT TEXT PARA EDITAR
        if (emailUser.equals(postEmail)) {
            linearLayout.setVisibility(View.VISIBLE);

            details_title.setClickable(true);
            details_title.setEnabled(true);

            details_desc.setClickable(true);
            details_desc.setEnabled(true);


        }else{
            details_title.setEnabled(false);
            details_desc.setEnabled(false);
        }


        //datos Mapa
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        //Toast.makeText(PostDetails.this, latitude + "-" + longitude, Toast.LENGTH_SHORT).show();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.post_map);
        mapFragment.getMapAsync(this);

        //Listener boton whatsapp
        whatsappMsg.setOnClickListener(new View.OnClickListener() {
            private PackageManager packageManager;

            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:" + phoneNumber);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(i);
            }
        });
        btnDescarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PostDetails.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    guardarImagen();
                } else {
                    ActivityCompat.requestPermissions(PostDetails.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },REQUEST_CODE);
                }
            }
        });

        // ELIMINAR POST
        btnEliminarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPost();
            }
        });

        //EDITAR UN POST
        btnEditarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), pId,Toast.LENGTH_SHORT).show();
                firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference db =firebaseDatabase.getReference("Post");
                db.child(pId).child("pTitle").setValue(details_title.getText().toString());
                db.child(pId).child("pDescr").setValue(details_desc.getText().toString());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

        });
    }

    private void eliminarPost(){
        Toast.makeText(getApplicationContext(), pId,Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(this)
                .setTitle("Eliminacion post")
                .setMessage("¿Desea eliminar el post?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference db =firebaseDatabase.getReference("Post");
                        Query query = db.orderByChild("pId").equalTo(pId);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot post : snapshot.getChildren()){
                                    post.getRef().removeValue();
                                }
                                Toast.makeText(getApplicationContext(), "ELIMINANDO POST",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "MMMMMMMMMM",Toast.LENGTH_SHORT).show();
                    }
                }).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if ( requestCode == REQUEST_CODE ){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                guardarImagen();
            } else {
                Toast.makeText(getApplicationContext(), "proporcione el permiso requerido", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void guardarImagen(){
        Uri images;
        ContentResolver contentResolver = getContentResolver();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
        images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*");
        Uri uri = contentResolver.insert(images, contentValues);


        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) details_img.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            OutputStream outputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Objects.requireNonNull(outputStream);

            Toast.makeText(getApplicationContext(), "imagen guardada", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "imagen no guardada", Toast.LENGTH_SHORT).show();
        }
    }

    private void comprobarFavorito(){
/*
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();
        SharedPreferences preferences = getSharedPreferences("favoritos", Context.MODE_PRIVATE);
        Set<String> favoritos = preferences.getStringSet(userId, null);

        if (favoritos != null){
        for (String str : favoritos) {
                if (str.equals(pId)){
                    favoritoImg.setImageResource(R.drawable.fav_color);
                } else {
                    favoritoImg.setImageResource(R.drawable.fav_sin_color);
                }
        }
        }
*/

    }

    private void cargarDatosSP(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();
        preferences = getSharedPreferences("favoritos", MODE_PRIVATE);
        favoritos = preferences.getStringSet(userId, null);

        if (favoritos == null ){
            favoritos = new HashSet<>();
        }

    }


    private void cambiarImagenFavorito(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();

        SharedPreferences.Editor editor = preferences.edit();
        favoritos = preferences.getStringSet(userId, new HashSet<>());


        if (favoritos.contains(pId)){
            Toast.makeText(getApplicationContext(),"BORRANDO",Toast.LENGTH_SHORT).show();
            Set<String> favoritos2= favoritos;
            favoritos2.remove(pId);
            editor.remove(userId).commit();
           /* editor.putStringSet(userId, favoritos2);
            editor.commit();*/
            favoritoImg.setImageResource(R.drawable.fav_sin_color);
        }

        if (!favoritos.contains(pId)){
            //favoritos.add(pId);
            Toast.makeText(getApplicationContext(),"AGREGANDO",Toast.LENGTH_SHORT).show();
            Set<String> favoritos2= favoritos;
            favoritos2.add(pId);
            editor.remove(userId).commit();
            editor.putStringSet(userId, favoritos2);
            editor.commit();
            favoritoImg.setImageResource(R.drawable.fav_color);
        }




    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        LatLng postLocation = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        mMap.addMarker(new MarkerOptions().position(postLocation).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(postLocation));

        //Desactivar scrollview, pero activar scroll mapa
        mMap.setOnCameraMoveListener((GoogleMap.OnCameraMoveListener) this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) { }

    @Override
    public void onCameraMove() {
        ScrollView mScrollView = findViewById(R.id.scrollView);
        mScrollView.requestDisallowInterceptTouchEvent(true);
    }


}