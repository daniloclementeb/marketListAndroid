package com.clemente.danilo.androiddevelopment.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clemente.danilo.androiddevelopment.dao.ListDAO;
import com.clemente.danilo.androiddevelopment.model.Compra;

import java.util.ArrayList;

public class NovoItemActivity extends AppCompatActivity {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private int listaId, rowid;
    private String nomeLista;
    TextView tvNomeLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);
        if (getIntent() != null) {
            listaId = getIntent().getExtras().getInt("LISTA_ID");
            nomeLista = getIntent().getExtras().getString("NOME_LISTA");
            tvNomeLista = (TextView) findViewById(R.id.novoItemNomeLista);
            rowid = getIntent().getExtras().getInt("ITEM_ID");
            if (rowid > 0) {
                configuraEdicao();
            }
        }
    }

    private void configuraEdicao() {
        ListDAO dao = new ListDAO(getApplicationContext());
        EditText novoItemQtt = (EditText) findViewById(R.id.novoItemQtt);
        EditText novoItemPreco = (EditText) findViewById(R.id.novoItemPreco);
        EditText novoItemNomeItem = (EditText) findViewById(R.id.novoItemNomeItem);
        ArrayList<Compra> item = dao.getItemFromList(listaId, rowid);
        novoItemQtt.setText(String.valueOf(item.get(0).getQuantidade()));
        novoItemPreco.setText(String.valueOf(item.get(0).getPreco()));
        novoItemNomeItem.setText(item.get(0).getNomeItem());
    }

    public void home(View v) {
        Intent i = new Intent(NovoItemActivity.this, MainActivity.class);
        i.putExtra("ID_LISTA_SELECT", listaId);
        startActivity(i);
    }

    public void saveItem(View v) {
        EditText novoItemQtt = (EditText) findViewById(R.id.novoItemQtt);
        EditText novoItemPreco = (EditText) findViewById(R.id.novoItemPreco);
        EditText novoItemNomeItem = (EditText) findViewById(R.id.novoItemNomeItem);
        ListDAO dao = new ListDAO(getApplicationContext());
        double preco = Double.parseDouble(novoItemPreco.getText().toString().isEmpty()?"0":novoItemPreco.getText().toString());
        int qtt = Integer.parseInt(novoItemQtt.getText().toString().isEmpty()?"0":novoItemQtt.getText().toString());
        if (novoItemNomeItem.getText().toString().isEmpty()) {
            novoItemNomeItem.setError(getString(R.string.empty_name));
        } else {
            if (dao.saveItemInList(listaId, 0, novoItemNomeItem.getText().toString(),
                    preco, qtt, rowid)) {
                Intent i;
                if (rowid > 0) {
                    Toast.makeText(getApplication(), getString(R.string.item_update_error), Toast.LENGTH_LONG).show();
                    i = new Intent(NovoItemActivity.this, MainActivity.class);
                    i.putExtra("ID_LISTA_SELECT", listaId);

                } else {
                    Toast.makeText(getApplication(), R.string.item_registered, Toast.LENGTH_LONG).show();
                    i = new Intent(NovoItemActivity.this, NovoItemActivity.class);
                    i.putExtra("LISTA_ID", listaId);
                    i.putExtra("NOME_LISTA", nomeLista);
                }
                startActivity(i);
            }
        }
    }

    public void speak(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speakItemName));
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    EditText novoItemNomeItem = (EditText) findViewById(R.id.novoItemNomeItem);
                    novoItemNomeItem.setText(result.get(0));
                }
                break;
            }
        }
    }

}
