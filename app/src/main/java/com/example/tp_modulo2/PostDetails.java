package com.example.tp_modulo2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PostDetails extends AppCompatActivity {
    //Partes del detalle (ids)
    ImageView details_img;
    TextView details_title, details_desc, details_date, details_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        details_img = findViewById(R.id.details_img);
        details_title = findViewById(R.id.details_title);
        details_desc = findViewById(R.id.details_desc);
        details_date= findViewById(R.id.details_date);
        details_location = findViewById(R.id.details_location);

        Picasso.get().load(getIntent().getStringExtra("details_img"))
                .placeholder(R.drawable.transparent_picture).into(details_img);

        details_title.setText(getIntent().getStringExtra("details_title"));
        details_desc.setText(getIntent().getStringExtra("details_desc"));
        details_date.setText(getIntent().getStringExtra("details_date"));
        details_location.setText(getIntent().getStringExtra("details_location"));
    }
}