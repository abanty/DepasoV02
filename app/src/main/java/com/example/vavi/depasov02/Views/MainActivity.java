package com.example.vavi.depasov02.Views;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;


import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.vavi.depasov02.Models.AnuncioModel;
import com.example.vavi.depasov02.R;
//import com.example.vavi.depasov02.Views.Fragments.CuentaFragment;
//import com.example.vavi.depasov02.Views.Fragments.InicioFragment;
//import com.example.vavi.depasov02.Views.Fragments.NotificacionesFragment;
import com.example.vavi.depasov02.Views.Fragments.CuentaFragment;
import com.example.vavi.depasov02.Views.Fragments.InicioFragment;
import com.example.vavi.depasov02.Views.Fragments.NotificacionesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{


    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private String current_user_id;
    private ArrayList<AnuncioModel>ListaAnuncios;

//    private FloatingActionButton addanunciobtn;
    private BottomNavigationView bottommenu_nav;

    // 3 Fragmentos
    private InicioFragment inicioFragment;
    private NotificacionesFragment notificacionesFragment;
    private CuentaFragment cuentaFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        //addanunciobtn = findViewById(R.id.btn_add_anuncio);


        if(mAuth.getCurrentUser() != null) {

            bottommenu_nav = findViewById(R.id.barra_nav_menu);

//        FRAGMENTOS
            inicioFragment = new InicioFragment();
            notificacionesFragment = new NotificacionesFragment();
            cuentaFragment = new CuentaFragment();

            remplazarfragment(inicioFragment);


            bottommenu_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.bottom_action_inicio:
                            remplazarfragment(inicioFragment);
                            return true;

                        case R.id.bottom_action_not:
                            remplazarfragment(notificacionesFragment);
                            return true;

                        case R.id.bottom_action_cuenta:
                            remplazarfragment(cuentaFragment);
                            return true;

                        default:
                            return false;
                    }
                }
            });


            /*FLOATINGBOTTOM CON EVENTO PARA MANDAR A LA ACTIVIDAD DE REGISTRAR ANUNCIOS*/
//            addanunciobtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent nuevo_anuncio = new Intent(MainActivity.this, AnunciosActivity.class);
//                    startActivity(nuevo_anuncio);
//
//                }
//            });

        }
    }
//
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioactual = FirebaseAuth.getInstance().getCurrentUser(); //Comprobamos la existencia de una sesion de usuario iniciada
        if (usuarioactual == null){

           EnviaralLogin();

        }else{

            current_user_id = mAuth.getCurrentUser().getUid();

            firebaseFirestore.collection("Usuarios").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()){

                        if (!task.getResult().exists()){

                               Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
                               startActivity(setupIntent);
                        }

                    }else{

                        String MensajeError = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error : " + MensajeError, Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.accion_cerrarsesion:
                cerrarsesion();

                return true;

//            case R.id.accion_buscar:
//                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//                return true;

            case R.id.accion_configurar:
                Intent opcionesIntent = new Intent(MainActivity.this,SetupActivity.class);
                startActivity(opcionesIntent);

            default:

                return false;

        }

    }


    private void cerrarsesion() {

        mAuth.signOut();
        EnviaralLogin();

    }


    private void EnviaralLogin() {

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }

    public void logout(View v) {
        mAuth.signOut();
        EnviaralLogin();
    }


    private void remplazarfragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_principal,fragment);
        fragmentTransaction.commit();
    }




}
