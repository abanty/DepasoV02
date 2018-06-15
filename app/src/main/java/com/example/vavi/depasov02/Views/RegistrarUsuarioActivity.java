package com.example.vavi.depasov02.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vavi.depasov02.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText edtcorreo,edtnombre, edtpassword,edtconfirmarpass,edtusuario;
    private Button crear,irprincipal;
    private ProgressBar pgbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_registrar_usuario);
        showToolbar(getResources().getString(R.string.toolbar_tittle_createaccount), true);

        mAuth = FirebaseAuth.getInstance();

        edtcorreo = (EditText) findViewById(R.id.email);
        edtnombre = (EditText) findViewById(R.id.name);
        edtpassword = (EditText) findViewById(R.id.password_createaccount);
        edtconfirmarpass = (EditText) findViewById(R.id.confirmPassword);
        edtusuario = (EditText) findViewById(R.id.user);
        crear = (Button) findViewById(R.id.joinUs);
        irprincipal = (Button) findViewById(R.id.gologin);

        pgbar = (ProgressBar) findViewById(R.id.progressBar2);

        irprincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent igoprincipal= new Intent(RegistrarUsuarioActivity.this, LoginActivity.class);
//                startActivity(igoprincipal);
                finish();
            }
        });


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correo = edtcorreo.getText().toString();
                String clave = edtpassword.getText().toString();
                String confirmarpass = edtconfirmarpass.getText().toString();

                if (TextUtils.isEmpty(correo)) {
                    Toast.makeText(getApplicationContext(), "Ingresar email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(clave)) {
                    Toast.makeText(getApplicationContext(), "Ingresa password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confirmarpass)) {
                    Toast.makeText(getApplicationContext(), "Confirma password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isEmpty(correo)&&!TextUtils.isEmpty(clave)&&!TextUtils.isEmpty(confirmarpass)){
                    if (clave.equals(confirmarpass)){

                        pgbar.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

//                                    Toast.makeText(RegistrarUsuarioActivity.this, "Usuario Registrado con Exito", Toast.LENGTH_SHORT).show();
//                                    Enviaralprincipal();
                                    Intent setupIntent = new Intent(RegistrarUsuarioActivity.this, SetupActivity.class);
                                    startActivity(setupIntent);
                                    finish();


                                }else{

                                    String Mensajedeerror = task.getException().getMessage();
                                    Toast.makeText(RegistrarUsuarioActivity.this, "Error : " + Mensajedeerror, Toast.LENGTH_SHORT).show();

                                }
                                pgbar.setVisibility(View.GONE);
                            }
                        });
                    }else{
                        Toast.makeText(RegistrarUsuarioActivity.this, "Confirmar password Correctamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void showToolbar(String title, boolean upButton) {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(title);
            getSupportActionBar().getThemedContext();
            toolbar.setTitleTextColor(0xFFFFFFFF);
           getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_atras)); //Marca ERROR ADVERTENCIA
            getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioreciente = mAuth.getCurrentUser();
        if (usuarioreciente!=null){
            Enviaralprincipal();
        }
    }

    private void Enviaralprincipal() {

        Intent minuevointent = new Intent(RegistrarUsuarioActivity.this, MainActivity.class);
        startActivity(minuevointent);
        finish();
    }
}
