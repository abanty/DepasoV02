package com.example.vavi.depasov02.Views;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.vavi.depasov02.R;
import com.example.vavi.depasov02.Views.Fragments.CuentaFragment;
import com.example.vavi.depasov02.Views.Fragments.InicioFragment;
import com.example.vavi.depasov02.Views.Fragments.NotificacionesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private FirebaseAuth mAuth;

    private FloatingActionButton addanunciobtn;
    private BottomNavigationView bottommenu_nav;
    private InicioFragment inicioFragment;
    private NotificacionesFragment notificacionesFragment;
    private CuentaFragment cuentaFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("INMUEBLES");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        bottommenu_nav = findViewById(R.id.barra_nav_menu);

        if (mAuth.getCurrentUser() != null){

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


        addanunciobtn = (FloatingActionButton) findViewById(R.id.btn_add_anuncio);
        addanunciobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nuevo_anuncio = new Intent(MainActivity.this, AnunciosActivity.class);
                startActivity(nuevo_anuncio);

            }
          });

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioactual = FirebaseAuth.getInstance().getCurrentUser(); //Comprobamos la existencia de una sesion de usuario iniciada
        if (usuarioactual == null){
           Enviaralprincipal();
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

            case R.id.accion_configurar:
                Intent opcionesIntent = new Intent(MainActivity.this,SetupActivity.class);
                startActivity(opcionesIntent);

            default:
                return false;

        }

        
    }

    private void cerrarsesion() {
        mAuth.signOut();
        Enviaralprincipal();
    }


    private void Enviaralprincipal() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void remplazarfragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_principal,fragment);
        fragmentTransaction.commit();
    }

}
