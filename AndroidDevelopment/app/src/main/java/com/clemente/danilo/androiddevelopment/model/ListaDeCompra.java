package com.clemente.danilo.androiddevelopment.model;

import java.util.ArrayList;

/**
 * Created by HP on 26/08/2017.
 */

public class ListaDeCompra {
    private int rowid;
    private String Nome;
    private int alerta;
    ArrayList<Compra> items;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getAlerta() {
        return alerta;
    }

    public void setAlerta(int alerta) {
        this.alerta = alerta;
    }

    public ArrayList<Compra> getItems() {
        return items;
    }

    public void setItems(ArrayList<Compra> items) {
        this.items = items;
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }
}
