package com.clemente.danilo.androiddevelopment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.clemente.danilo.androiddevelopment.MapsActivity;
import com.clemente.danilo.androiddevelopment.adapter.ComprasAdapter;
import com.clemente.danilo.androiddevelopment.adapter.OnItemClickListener;
import com.clemente.danilo.androiddevelopment.dao.ListDAO;
import com.clemente.danilo.androiddevelopment.model.Compra;
import com.clemente.danilo.androiddevelopment.model.ListaDeCompra;
import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rvLista;
    private ComprasAdapter comprasAdapter;
    ListaDeCompra lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //inicia manutencao
        carregaLista();

    }

    private void carregaLista() {
        final ListDAO dao = new ListDAO(getApplicationContext());
        if (getIntent()!=null && getIntent().getExtras()!=null) {
            int listId = getIntent().getExtras().getInt("ID_LISTA_SELECT");
            if (listId > 0)
                lista = dao.getListById(listId);
        }
        if (lista == null)
            lista = dao.getLastList();

        TextView tvNomeLista = (TextView) findViewById(R.id.nomeLista);
        tvNomeLista.setText(lista.getNome());
        comprasAdapter = new ComprasAdapter(lista.getItems(), new OnItemClickListener() {
            @Override
            public void onItemClick(Compra compra) {
                if (dao.boughtItem(compra.getRowid(), compra.getFlagComprado() == 1? 0:1) > 0) {
                    lista = null;
                    carregaLista();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDeleteClick(Compra item) {
                ListDAO dao = new ListDAO(getApplicationContext());
                dao.deleteItem(item.getRowid());
                lista = null;
                carregaLista();
            }

            @Override
            public void onEditItemClick(Compra item) {
                Intent i = new Intent(MainActivity.this, NovoItemActivity.class);
                i.putExtra("ITEM_ID", item.getRowid());
                i.putExtra("LISTA_ID",lista.getRowid());
                i.putExtra("NOME_LISTA", lista.getNome());
                startActivity(i);
            }
        });
        rvLista = (RecyclerView) findViewById(R.id.rvLista);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(layoutManager);
        rvLista.setAdapter(comprasAdapter);
        rvLista.setHasFixedSize(true);
        comprasAdapter.update(lista.getItems());
        //TODO se tiver lista poem add
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (lista.getRowid() == 0) {
            fab.setVisibility(View.INVISIBLE);
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, NovoItemActivity.class);
                    i.putExtra("LISTA_ID", lista.getRowid());
                    i.putExtra("NOME_LISTA", lista.getNome());
                    startActivity(i);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_about: {
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
                return true;
            }
            case R.id.action_disconnect: {
                disconnect();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void disconnect() {
        LoginManager.getInstance().logOut();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        i.putExtra("LOGOUT", true);
        startActivity(i);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newlist) {
            Intent i = new Intent(this, NovaListaActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(this, ListasActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_mapshow) {
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
