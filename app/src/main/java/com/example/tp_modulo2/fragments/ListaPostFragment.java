package com.example.tp_modulo2.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tp_modulo2.R;
import com.example.tp_modulo2.adaptador.AdaptadorPost;
import com.example.tp_modulo2.interfaces.IComunicaFragment;
import com.example.tp_modulo2.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFramentInteractionListener mListener;

    ArrayList<Post> posts;
    RecyclerView recyclerPost;

    FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();

    DatabaseReference databaseReference;

    Activity activity;
    IComunicaFragment comunicaFragment;

    private OnFragmentInteractionListener mlistener;


    public ListaPostFragment() {
        // Required empty public constructor
    }



        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListaPostFragment.
         */
    // TODO: Rename and change types and number of parameters
    public static ListaPostFragment newInstance(String param1, String param2) {
        ListaPostFragment fragment = new ListaPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_lista_post, container, false);
        posts= new ArrayList<>();
        recyclerPost = vista.findViewById(R.id.recyclerId);
        recyclerPost.setLayoutManager(new LinearLayoutManager(getContext()));
        generarPost();
        //AGREGUE CONTEXT AQUI
        AdaptadorPost adaptadorPost=  new AdaptadorPost(posts, this.getContext());
        recyclerPost.setAdapter(adaptadorPost);

        adaptadorPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Toast.makeText(getContext(), "Selecciona: " +
                        posts.get(recyclerPost.getChildAdapterPosition(view)).getCountry()
                        , Toast.LENGTH_LONG).show();*/

                comunicaFragment.enviarPost(posts.get(recyclerPost.getChildAdapterPosition(view)));
            }
        });

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void generarPost(){
        databaseReference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference  postDb = databaseReference.child("Post");

        String urlImg= "https://firebasestorage.googleapis.com/v0/b/tp-modulo2.appspot.com/o/Post%2Fpost_1669423488782?alt=media&token=fa36a9b9-bf7e-4238-9d2c-352197a42a73";
        String urlImg2= "https://firebasestorage.googleapis.com/v0/b/tp-modulo2.appspot.com/o/Post%2Fpost_1669562866495?alt=media&token=35f02991-2073-4668-905f-d30103974704";

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String city = ds.child("city").getValue(String.class);
                    String country = ds.child("country").getValue(String.class);
                    String img = ds.child("pImage").getValue(String.class);
                    String desp = ds.child("pDescr").getValue(String.class);

                    posts.add(new Post(city, country, img, desp));

                    //FUNCIONA CON UN INT
                    //posts.add(new Post(zona,estado,R.drawable.perdidos3));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        postDb.addValueEventListener(eventListener);

     /*   posts.add(new Post("Berazategui","false", R.drawable.perdidos));
        posts.add(new Post("Ezpeleta","false", R.drawable.perdidos3));
        posts.add(new Post("Quilmes","false", R.drawable.perdidos2));*/
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            this.activity = (Activity) context;
            comunicaFragment = (IComunicaFragment) this.activity;
        }
    }



    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


}