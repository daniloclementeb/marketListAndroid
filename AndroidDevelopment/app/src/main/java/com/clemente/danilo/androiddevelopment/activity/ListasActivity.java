package com.clemente.danilo.androiddevelopment.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.clemente.danilo.androiddevelopment.adapter.ListasAdapter;
import com.clemente.danilo.androiddevelopment.adapter.OnListaItemClickListener;
import com.clemente.danilo.androiddevelopment.dao.ListDAO;
import com.clemente.danilo.androiddevelopment.model.Compra;
import com.clemente.danilo.androiddevelopment.model.ListaDeCompra;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ListasActivity extends AppCompatActivity {
    private RecyclerView rvLista;
    private ListasAdapter listasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        carregaFrame();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ListasActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void carregaFrame() {
        final ListDAO dao = new ListDAO(getApplicationContext());
        ArrayList<ListaDeCompra> list = dao.getAllLists();
        listasAdapter = new ListasAdapter(list, new OnListaItemClickListener() {
            @Override
            public void onItemClick(ListaDeCompra item) {
                Intent i = new Intent(ListasActivity.this, MainActivity.class);
                i.putExtra("ID_LISTA_SELECT", item.getRowid());
                startActivity(i);
            }

            @Override
            public void onDeleteClick(ListaDeCompra item) {
                ListDAO dao = new ListDAO(getApplicationContext());
                dao.deleteList(item.getRowid());
                carregaFrame();
            }

            @Override
            public void onShareItemClick(ListaDeCompra item) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                String textoCompartilhado = item.getNome() + ": \n";

                for (Compra e:item.getItems()) {
                    textoCompartilhado = textoCompartilhado.concat(getString(R.string.name_tv) + e.getNomeItem() + " " + getString(R.string.quantity_tv) + " " + String.valueOf(e.getQuantidade()) + "\n");
                }

                sendIntent.putExtra(Intent.EXTRA_TEXT, textoCompartilhado);
                sendIntent.setType("text/plain");
                Log.d(TAG, "Compartilhado: " + textoCompartilhado);
                startActivity(Intent.createChooser(sendIntent, getString(R.string.share_tv)));
            }

            @Override
            public void onEditItemClick(ListaDeCompra item) {
                Intent i = new Intent(ListasActivity.this, NovaListaActivity.class);
                i.putExtra("LISTA_ID", item.getRowid());
                startActivity(i);
            }

            private String montaListaString(ArrayList<Compra> items) {
                String res = "";
                for (Compra item : items) {
                    res = res.concat(getString(R.string.item_tv) + item.getNomeItem() + " " + getString(R.string.quantity_tv) + " "  + item.getQuantidade() + "\n");
                }
                return res;
            }
        });
        rvLista = (RecyclerView) findViewById(R.id.rvListaListas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(layoutManager);
        rvLista.setAdapter(listasAdapter);
        rvLista.setHasFixedSize(true);
        listasAdapter.update(list);
    }

}
