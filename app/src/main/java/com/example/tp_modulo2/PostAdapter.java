package com.example.tp_modulo2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    ArrayList<MainPosts> list;
    Context context;

    public PostAdapter(ArrayList<MainPosts> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_list_item, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainPosts model = list.get(position);
        //Imagen
        Picasso.get().load(model.getPImage()).placeholder(R.drawable.transparent_picture).into(holder.pImage);
        //Otros datos
        holder.title.setText(model.getPTitle());
        holder.publicationDate.setText(model.getPublicationDate());
        holder.city.setText(model.getCity());

        //Al hacer click al boton, mostramos detalles
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostDetails.class);
                //Asignamos cada variable
                intent.putExtra("details_img", model.getPImage());
                intent.putExtra("details_title", model.getPTitle());
                intent.putExtra("details_desc", model.getpDescr());
                intent.putExtra("details_date", model.getPublicationDate());
                intent.putExtra("details_location", model.getCity() + ", "+ model.getState());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, publicationDate, city;
        ImageView pImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //values del post (lista)
            title = itemView.findViewById(R.id.post_title);
            publicationDate = itemView.findViewById(R.id.post_date);
            city = itemView.findViewById(R.id.post_city);

            pImage = itemView.findViewById(R.id.post_img);
        }
    }
}
