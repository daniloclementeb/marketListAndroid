package com.clemente.danilo.androiddevelopment.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.clemente.danilo.androiddevelopment.dao.UserDAO;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import static android.content.ContentValues.TAG;


public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    CallbackManager callbackManager;
    EditText etUser;
    EditText etPassword;
    private View view;
    CheckBox keep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getPreferences(MODE_PRIVATE);

        //caso tenha desconectado
        if (this.getIntent() != null && this.getIntent().getExtras() != null) {
            Boolean v = getIntent().getExtras().getBoolean("LOGOUT", false);
            if (v) {
                SharedPreferences.Editor ed = pref.edit();
                ed.clear();
                ed.commit();
                DefaultActivity.USUARIO = "";
            }
        }
        //se tiver logado e mantido conectado
        if (pref.getBoolean("manter", false)) {
            nextStep(pref.getString("username", ""), pref.getString("password", ""));
        }
        if (verificaFacebook()) {
            //se for usuario FB
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            DefaultActivity.USUARIO = Profile.getCurrentProfile().getId();
            startActivity(i);
        } else {
            //default
            setContentView(R.layout.activity_login);
            view = (View) findViewById(R.id.activity_login);
            etUser = (EditText) findViewById(R.id.etUser);
            etPassword = (EditText) findViewById(R.id.etPassword);
            keep = (CheckBox) findViewById(R.id.chkKeep);
            callbackManager = CallbackManager.Factory.create();
            // Callback registration
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    DefaultActivity.USUARIO = Profile.getCurrentProfile().getId();
                    startActivity(i);
                }

                @Override
                public void onCancel() {
                    Log.d(TAG, "cancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d(TAG, "Erro");
                }

            });
        }
    }

    private boolean verificaFacebook() {
        callbackManager = CallbackManager.Factory.create();

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                Toast.makeText(getApplicationContext(), getString(R.string.facebook_connected), Toast.LENGTH_LONG).show();
            }
        };
        // If the access token is available already assign it.
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null || accessToken.isExpired())
            return false;
        return true;
    }

    private boolean nextStep(String user, String pass) {
        if (validaUsuario(user, pass)) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            DefaultActivity.USUARIO = new UserDAO(getApplicationContext()).getUser(user, pass).getRowid();
            startActivity(i);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 100) {
                String user = data.getExtras().get("EMAIL").toString();
                String pass = data.getExtras().get("SENHA").toString();
                if (!user.isEmpty() && !pass.isEmpty()) {
                    etUser.setText(user);
                    etPassword.setText(pass);
                }
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void registrar(View v) {
        Intent intent = new Intent(LoginActivity.this,
                RegisterActivity.class);
        startActivityForResult(intent, 100);
    }

    public void logar(View v) {
        if (keep.isChecked()) {
            //gravar usuario keep
            keepAlive();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
        if (validaUsuario(etUser.getText().toString(), etPassword.getText().toString())) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            DefaultActivity.USUARIO = new UserDAO(getApplicationContext()).getUser(etUser.getText().toString(), etPassword.getText().toString()).getRowid();
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.invalid_user), Toast.LENGTH_SHORT).show();
        }
    }

    private void keepAlive() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString("username", etUser.getText().toString());
        ed.putString("password", etPassword.getText().toString());
        ed.putBoolean("manter", keep.isChecked());
        ed.apply();
    }

    private boolean validaUsuario(String s, String s1) {
        String row = new UserDAO(getApplicationContext()).getUser(s, s1).getRowid();
        return row != null && !row.isEmpty();
    }
}
