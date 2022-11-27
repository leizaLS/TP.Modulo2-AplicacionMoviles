package com.example.tp_modulo2.fragments;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tp_modulo2.R;
import com.example.tp_modulo2.model.Post;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetallePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallePostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView descripcion;
    ImageView imagenDetalle;

    public DetallePostFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetallePostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallePostFragment newInstance(String param1, String param2) {
        DetallePostFragment fragment = new DetallePostFragment();
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
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_detalle_post, container, false);

        descripcion = (TextView) view.findViewById(R.id.descripcionId);
        imagenDetalle =(ImageView) view.findViewById(R.id.imagenDetalleId);

        Bundle objectoPost = getArguments();
        Post post = null;

        if (objectoPost != null) {
            post = (Post) objectoPost.getSerializable("post");
            descripcion.setText(post.getDescription());
            imagenDetalle.setImageURI(Uri.parse(post.getImagen()));
            Picasso.with(getContext()).load(post.getImagen()).into(imagenDetalle);

        }

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}