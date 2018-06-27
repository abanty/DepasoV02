package com.example.vavi.depasov02.Presentators;

import android.app.Activity;

import com.example.vavi.depasov02.Interfaces.LoginInterface;

public class LoginPresentator implements LoginInterface.Presenter, LoginInterface.onLoginListener {
    private LoginInterface.View loginview;
    private LoginInteractor loginInteractor;

    public LoginPresentator(LoginInterface.View loginview) {
        this.loginview = loginview;
        loginInteractor = new LoginInteractor(this);
    }

    @Override
    public void login(Activity activity, String email, String password) {
        loginInteractor.FirebaseLogin(activity, email, password);
    }

    @Override
    public void onSuccess(String mensaje) {
        loginview.onLoginSuccess(mensaje);
    }

    @Override
    public void onFailure(String mensaje) {
        loginview.onLoginFailure(mensaje);
    }
}
