package com.example.vavi.depasov02.Views.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vavi.depasov02.R;
import com.example.vavi.depasov02.Views.RegistrarAnunciosActivity;
import com.example.vavi.depasov02.Views.LoginActivity;
import com.example.vavi.depasov02.Views.UserPhotoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CuentaFragment extends Fragment {
    private Button closesession;
    private Button registerAnuncio;
    private TextView textusername;
    private CircleImageView userphotoimagecircle;
    private String user_id;
    private Uri mypersonalimagenUri = null;

    //SERVICES FIREBASE
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;



    public CuentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_cuenta, container, false);




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user_id = firebaseAuth.getCurrentUser().getUid();

        textusername = view.findViewById(R.id.usernamefragment);
        userphotoimagecircle = view.findViewById(R.id.imagencuenta);
        closesession =view.findViewById(R.id.buttonCloseSession);
        registerAnuncio=view.findViewById(R.id.buttonRegistrar);


        userphotoimagecircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), UserPhotoActivity.class));
            }
        });


        firebaseFirestore.collection("Usuarios").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        String name = task.getResult().getString("nombre");
                        String image = task.getResult().getString("imagen");

                        mypersonalimagenUri = Uri.parse(image);
                        textusername.setText(name);

                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.ic_android);
                        Glide.with(getActivity()).setDefaultRequestOptions(placeholderRequest).load(image).into(userphotoimagecircle);
                    }

                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(getActivity(), "FIRESTORE Error: " + error, Toast.LENGTH_SHORT).show();

                }
            }
        });


        registerAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent nuevo_anuncio = new Intent(getActivity(), RegistrarAnunciosActivity.class);
                    startActivity(nuevo_anuncio);
            }
        });


        closesession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                GoLogin();
            }
        });




        return view;


    }



    public void GoLogin()
    {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}
