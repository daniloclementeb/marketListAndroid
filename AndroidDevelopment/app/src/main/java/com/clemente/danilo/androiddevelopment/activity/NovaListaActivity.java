package com.clemente.danilo.androiddevelopment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.clemente.danilo.androiddevelopment.activity.R;
import com.clemente.danilo.androiddevelopment.dao.ListDAO;
import com.clemente.danilo.androiddevelopment.model.ListaDeCompra;

public class NovaListaActivity extends AppCompatActivity {
    private boolean update = false;
    private int idLista;
    private ListaDeCompra lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_lista);

        if (getIntent() != null && getIntent().getExtras() != null) {
            idLista = getIntent().getExtras().getInt("LISTA_ID");
            if (idLista > 0) {
                update = true;
                ListDAO dao = new ListDAO(getApplicationContext());
                lista = dao.getListById(idLista);
                EditText etListName = (EditText) findViewById(R.id.etListName);
                CheckBox chkAlert = (CheckBox) findViewById(R.id.chkAlert);
                etListName.setText(lista.getNome());
                chkAlert.setChecked(lista.getAlerta() > 0);
            }
        }

    }

    public void salvarLista(View v) {
        EditText etListName = (EditText) findViewById(R.id.etListName);
        CheckBox chkAlert = (CheckBox) findViewById(R.id.chkAlert);
        ListDAO dao = new ListDAO(getApplicationContext());
        Intent i = new Intent(NovaListaActivity.this, MainActivity.class);
        if (etListName.getText().toString().isEmpty()) {
            etListName.setError(getString(R.string.please_insert_name_to_list));
        } else {
            if (update) {
                if (dao.upateList(idLista, etListName.getText().toString(), chkAlert.isChecked() ? 1 : 0) > 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.item_updated), Toast.LENGTH_SHORT).show();
                    i.putExtra("ID_LISTA_SELECT", idLista);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.item_update_error), Toast.LENGTH_LONG).show();
                    i.putExtra("ID_LISTA_SELECT", idLista);
                }
            } else {
                dao.insertList(etListName.getText().toString(), chkAlert.isChecked() ? 1 : 0);
            }
            startActivity(i);
        }

    }
}
