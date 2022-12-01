package com.example.tp_modulo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import javax.security.auth.callback.PasswordCallback;

public class PostDetails extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnCameraMoveListener {
    //Partes del detalle (ids)
    ImageView details_img;
    TextView details_title, details_desc, details_date, details_location;
    String latitude, longitude;
    GoogleMap mMap;
    Button whatsappMsg;
    String phoneNumber = "";

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

        Picasso.get().load(getIntent().getStringExtra("details_img"))
                .placeholder(R.drawable.transparent_picture).into(details_img);

        details_title.setText(getIntent().getStringExtra("details_title"));
        details_desc.setText(getIntent().getStringExtra("details_desc"));
        details_date.setText(getIntent().getStringExtra("details_date"));
        details_location.setText(getIntent().getStringExtra("details_location"));
        phoneNumber = getIntent().getStringExtra("phoneNumber");

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