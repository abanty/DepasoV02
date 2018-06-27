package com.example.vavi.depasov02.Interfaces;

import android.app.Activity;

public interface LoginInterface {

    interface View{
        void onLoginSuccess(String mensaje);
        void onLoginFailure(String mensaje);
    }

    interface Presenter{
        void login(Activity activity, String email, String password);
    }

    interface Intractor{
        void FirebaseLogin(Activity activity, String email, String password);
    }

    interface onLoginListener{
        void onSuccess(String mensaje);
        void onFailure(String mensaje);
    }

}
