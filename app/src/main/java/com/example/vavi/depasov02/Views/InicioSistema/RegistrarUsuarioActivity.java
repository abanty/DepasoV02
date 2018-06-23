package com.example.vavi.depasov02.Views.InicioSistema;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vavi.depasov02.R;
import com.example.vavi.depasov02.Views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText edtcorreo, edtpassword,edtconfirmarpass;
    private Button btnregistrar, btnirprincipal;
    private ProgressBar ProgressBarRegistrarACC;
    private FirebaseAuth mAuth;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_registrar_usuario);

        showToolbar(getResources().getString(R.string.toolbar_tittle_createaccount), true);

        mAuth = FirebaseAuth.getInstance();

        edtcorreo =  findViewById(R.id.email);
        edtpassword =  findViewById(R.id.password_createaccount);
        edtconfirmarpass =  findViewById(R.id.confirmPassword);
        btnregistrar =  findViewById(R.id.joinUs);
        btnirprincipal =  findViewById(R.id.gologin);
        ProgressBarRegistrarACC =  findViewById(R.id.progressBar2);

        btnirprincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              Enviarallogin();
                finish();
            }
        });

        btnregistrar.setOnClickListener(new View.OnClickListener() {
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

                        ProgressBarRegistrarACC.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    EnviaralSetup();

                                }else{

                                    String Mensajedeerror = task.getException().getMessage();
                                    Toast.makeText(RegistrarUsuarioActivity.this, "Error al Registrar : " + Mensajedeerror, Toast.LENGTH_SHORT).show();

                                }
                                ProgressBarRegistrarACC.setVisibility(View.GONE);
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

            Toolbar toolbar = findViewById(R.id.toolbar);
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

    private void Enviarallogin() {

        Intent minuevointent = new Intent(RegistrarUsuarioActivity.this, LoginActivity.class);
        startActivity(minuevointent);
        finish();

    }


    private void EnviaralSetup(){
        Intent setupIntent = new Intent(RegistrarUsuarioActivity.this, UserPhotoActivity.class);
        startActivity(setupIntent);
        finish();
    }
}
