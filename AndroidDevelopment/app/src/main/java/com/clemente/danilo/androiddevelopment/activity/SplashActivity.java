package com.clemente.danilo.androiddevelopment.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.clemente.danilo.androiddevelopment.dao.UserDAO;
import com.clemente.danilo.androiddevelopment.model.User;
import com.clemente.danilo.androiddevelopment.service.APIDataBase;
import com.clemente.danilo.androiddevelopment.service.APIUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        view = (View) findViewById(R.id.splash);
        carregaDados();
    }

    private void carregaDados() {
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.anim_splash);
        anim.reset();
//Pegando o nosso objeto criado no layout
        ImageView iv = (ImageView) findViewById(R.id.splash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }

        APIDataBase db = APIUtils.getAPIDataBaseVersion();
        db.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    UserDAO user = new UserDAO(getApplicationContext());
                    if (user.insert(response.body())) {
                        Snackbar.make(view, getString(R.string.user_registered), Snackbar.LENGTH_LONG).show();
                        carregaLogin();
                    } else {
                        Snackbar.make(view, getString(R.string.error_user_exists), Snackbar.LENGTH_LONG).show();
                        carregaLogin();
                    }
                } else {
                    Snackbar.make(view, getString(R.string.error_server_off), Snackbar.LENGTH_LONG).show();
                    carregaLogin();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(view,  getString(R.string.error_server_off), Snackbar.LENGTH_LONG).show();
                carregaLogin();
            }
        });




    }

    private void carregaLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
