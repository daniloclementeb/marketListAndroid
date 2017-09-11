package com.clemente.danilo.androiddevelopment.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void ligarPraMim(View v) {
        String telefone = "+55 11 989965766";

        Uri uri = Uri.parse("tel:"+telefone);
        Intent intent = new Intent(Intent.ACTION_DIAL,uri);

        startActivity(intent);
    }

}
