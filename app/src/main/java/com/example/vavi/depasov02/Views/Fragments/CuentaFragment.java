package com.example.vavi.depasov02.Views.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vavi.depasov02.R;
import com.example.vavi.depasov02.Views.RegistrarAnunciosActivity;
import com.example.vavi.depasov02.Views.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class CuentaFragment extends Fragment {
    private FirebaseAuth mAuthClose;
    private Button closesession;
    private Button registerAnuncio;


    public CuentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_cuenta, container, false);

        mAuthClose = FirebaseAuth.getInstance();

        closesession =view.findViewById(R.id.buttonCloseSession);
        registerAnuncio=view.findViewById(R.id.buttonRegistrar);

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
                mAuthClose.signOut();
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
