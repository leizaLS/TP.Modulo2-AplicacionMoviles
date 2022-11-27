package com.example.tp_modulo2.adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_modulo2.R;
import com.example.tp_modulo2.model.Post;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class AdaptadorPost extends RecyclerView.Adapter<AdaptadorPost.PostViewHolder> implements View.OnClickListener {

    ArrayList<Post> posts;
    private View.OnClickListener listener;
    private Context context;

    public AdaptadorPost(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        view.setOnClickListener(this);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {



        holder.txtCountry.setText(posts.get(position).getCountry());
        holder.txtDescription.setText(posts.get(position).getDescription());
        //holder.foto.setImageResource(posts.get(position).getImagenId());
        //FUNCA Y TRAE LAS IMAGENES DE FIREBASE
        holder.foto.setImageURI(Uri.parse(posts.get(position).getImagen()));
        Picasso.with(context).load(posts.get(position).getImagen()).into(holder.foto);


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener= listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        TextView txtCountry, txtDescription;
        ImageView foto;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCountry = (TextView) itemView.findViewById(R.id.idCountry);
            txtDescription = (TextView) itemView.findViewById(R.id.idDescription);
            foto = (ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}
