package com.example.vavi.depasov02.Views.InicioSistema;

import android.content.Intent;
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

import com.example.vavi.depasov02.Interfaces.LoginInterface;
import com.example.vavi.depasov02.Presentators.LoginPresentator;
import com.example.vavi.depasov02.R;
import com.example.vavi.depasov02.Views.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,LoginInterface.View {

    private EditText loginemail, loginpassword;
    private Button loginbtn, loginbtnface, loginbtngoogle;
    private TextView txtresetpass, txtcrearcuenta;
    private ProgressBar loginprogressBar;
    private FirebaseAuth mAuth;

    private LoginPresentator loginPresentator;

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

        loginbtn.setOnClickListener(this);
        txtcrearcuenta.setOnClickListener(this);

        loginPresentator = new LoginPresentator(this);


//        loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                String correo = loginemail.getText().toString();
//                String clave = loginpassword.getText().toString();
//
//                if (TextUtils.isEmpty(correo)) {
//                    Toast.makeText(getApplicationContext(), "Ingresar Correo Electronico!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(clave)) {
//                    Toast.makeText(getApplicationContext(), "Ingresa Contraseña!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (!TextUtils.isEmpty(correo) && !TextUtils.isEmpty(clave)){
//                    loginprogressBar.setVisibility(View.VISIBLE);
//
//                    mAuth.signInWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            if (task.isSuccessful()){
//
//                                Enviaralprincipal();
//
//                            }else{
//
//                                String MensajeError = task.getException().getMessage();
//                                Toast.makeText(LoginActivity.this, "Error :" + MensajeError, Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            loginprogressBar.setVisibility(View.GONE);
//
//
//                        }
//                    });
//                }
//
//            }
//        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                validarlogin();
                break;
            case R.id.createHere:
                EnviaralRegistrar();
                break;

        }
    }


    @Override
    public void onLoginSuccess(String mensaje) {

        Enviaralprincipal();
        loginprogressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoginFailure(String mensaje) {

        Toast.makeText(LoginActivity.this, "Error :" + mensaje, Toast.LENGTH_SHORT).show();

    }


    /*----------------------------------------------------------------------------------*/

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioactual = mAuth.getCurrentUser(); //Comprobamos la existencia de una sesion de usuario iniciada
        if (usuarioactual != null){

           Enviaralprincipal();

        }
    }



    private void validarlogin(){

        String correo = loginemail.getText().toString();
        String clave = loginpassword.getText().toString();

        if(!TextUtils.isEmpty(correo) && !TextUtils.isEmpty(clave)){
            loginprogressBar.setVisibility(View.VISIBLE);
            iniciarLogin(correo, clave);
        }else{
            if (TextUtils.isEmpty(correo)) {
                    Toast.makeText(getApplicationContext(), "Por favor ingresar email valido!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(clave)) {
                    Toast.makeText(getApplicationContext(), "Por favor ingresa contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }
        }
    }


    private void iniciarLogin(String email, String password) {
        loginPresentator.login(this, email, password);
    }


    private void Enviaralprincipal() {
        Intent PrincipalIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(PrincipalIntent);
        finish();
    }


    private void EnviaralRegistrar(){
        Intent i = new Intent(LoginActivity.this, RegistrarUsuarioActivity.class);
                startActivity(i);
    }



}
