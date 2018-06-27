package com.example.vavi.depasov02.Presentators;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.vavi.depasov02.Interfaces.LoginInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginInteractor implements LoginInterface.Intractor{

    private LoginInterface.onLoginListener loginListener;

    public LoginInteractor(LoginInterface.onLoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public void FirebaseLogin(Activity activity, String email, String password) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            loginListener.onSuccess(task.getResult().toString());
                        }
                        else {
                            loginListener.onFailure(task.getException().toString());
                        }
                    }
                });
    }
}
