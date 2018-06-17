package com.example.vavi.depasov02.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vavi.depasov02.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginemail, loginpassword;
    private Button loginbtn, loginbtnface, loginbtngoogle;
    private TextView txtresetpass, txtcrearcuenta;
    private ProgressBar loginprogressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginemail = findViewById(R.id.username);
        loginpassword= findViewById(R.id.password);
        loginbtn = findViewById(R.id.login);
        txtcrearcuenta = findViewById(R.id.createHere);

        loginprogressBar = findViewById(R.id.progressBar);


        txtcrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistrarUsuarioActivity.class);
                startActivity(i);

            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String correo = loginemail.getText().toString();
                String clave = loginpassword.getText().toString();

                if (TextUtils.isEmpty(correo)) {
                    Toast.makeText(getApplicationContext(), "Ingresar Correo Electronico!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(clave)) {
                    Toast.makeText(getApplicationContext(), "Ingresa Contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isEmpty(correo) && !TextUtils.isEmpty(clave)){
                    loginprogressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                                Enviaralprincipal();

                            }else{

                                String MensajeError = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error :" + MensajeError, Toast.LENGTH_SHORT).show();

                            }

                            loginprogressBar.setVisibility(View.GONE);


                        }
                    });
                }

            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioactual = mAuth.getCurrentUser(); //Comprobamos la existencia de una sesion de usuario iniciada
        if (usuarioactual != null){

           Enviaralprincipal();

        }
    }

    private void Enviaralprincipal() {

        Intent PrincipalIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(PrincipalIntent);
        finish();
    }
}
