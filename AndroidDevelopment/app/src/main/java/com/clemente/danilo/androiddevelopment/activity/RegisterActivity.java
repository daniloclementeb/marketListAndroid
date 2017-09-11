package com.clemente.danilo.androiddevelopment.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.clemente.danilo.androiddevelopment.dao.UserDAO;

public class RegisterActivity extends AppCompatActivity {
    EditText etUser;
    EditText etPassword;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        view = (View) findViewById(R.id.activity_register);
    }

    public void register(View v) {
        UserDAO userdao = new UserDAO(getApplicationContext());
        String user = etUser.getText().toString();
        String pass = etPassword.getText().toString();
        if (!user.isEmpty()) {
            if (!pass.isEmpty()) {
                if (userdao.insert(user, pass)) {
                    Snackbar.make(view, getString(R.string.user_register_success), Snackbar.LENGTH_LONG).show();
                    Intent data = new Intent();
                    data.putExtra("EMAIL", user);
                    data.putExtra("SENHA", pass);
                    setResult(100, data);
                    finish();
                } else {
                    Snackbar.make(view, R.string.register_error, Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(view, getString(R.string.pass_empty_register), Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(view, getString(R.string.register_error_user_empty), Snackbar.LENGTH_LONG).show();
        }

    }
}
