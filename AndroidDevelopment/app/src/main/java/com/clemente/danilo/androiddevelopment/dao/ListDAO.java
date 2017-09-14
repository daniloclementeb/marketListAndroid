package com.clemente.danilo.androiddevelopment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.clemente.danilo.androiddevelopment.activity.DefaultActivity;
import com.clemente.danilo.androiddevelopment.model.Compra;
import com.clemente.danilo.androiddevelopment.model.ListaDeCompra;

import java.util.ArrayList;

/**
 * Created by HP on 26/08/2017.
 */

public class ListDAO {

    private DBOpenHelper banco;

    public ListDAO(Context ctx) {
        banco = new DBOpenHelper(ctx);
    }

    public int upateList(int rowId, String s, int i) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOME", s);
        values.put("ALERTA", i);
        values.put("USUARIO", DefaultActivity.USUARIO);
        return db.update("LISTA", values, "rowid = ?", new String[] {String.valueOf(rowId)});
    }
    public boolean insertList(String name, int alerta) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOME", name);
        values.put("ALERTA", alerta);
        values.put("USUARIO", DefaultActivity.USUARIO);
        long resultado = db.insert("LISTA", null, values);

        if (resultado == -1) {
            return false;
        }
        return true;
    }
    public ArrayList<ListaDeCompra> getAllLists() {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor c = db.rawQuery("SELECT rowid, * FROM LISTA WHERE USUARIO = ? ORDER BY rowid DESC;", new String[] {DefaultActivity.USUARIO});
        ArrayList<ListaDeCompra> listas = new ArrayList<ListaDeCompra>();

        while (c.moveToNext()) {
            ListaDeCompra lista = new ListaDeCompra();
            lista.setNome(c.getString(c.getColumnIndex("NOME")));
            lista.setAlerta(c.getInt(c.getColumnIndex("ALERTA")));
            lista.setRowid(c.getInt(c.getColumnIndex("rowid")));
            lista.setItems(getItemFromList(lista.getRowid()));
            listas.add(lista);

        }
        return listas;
    }

    public ListaDeCompra getListById(int listId) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor c = db.rawQuery("SELECT rowid, * FROM LISTA WHERE USUARIO = ? AND rowid = ? ORDER BY rowid DESC LIMIT 1;", new String[] {DefaultActivity.USUARIO, String.valueOf(listId)});
        ListaDeCompra lista = new ListaDeCompra();
        if (c.moveToNext()) {
            lista.setNome(c.getString(c.getColumnIndex("NOME")));
            lista.setAlerta(c.getInt(c.getColumnIndex("ALERTA")));
            lista.setRowid(c.getInt(c.getColumnIndex("rowid")));
        }
        lista.setItems(getItemFromList(lista.getRowid()));
        return lista;
    }

    public ListaDeCompra getLastList() {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor c = db.rawQuery("SELECT rowid, * FROM LISTA WHERE USUARIO = ? ORDER BY rowid DESC LIMIT 1;", new String[] {DefaultActivity.USUARIO});
        ListaDeCompra lista = new ListaDeCompra();
        if (c.moveToNext()) {
            lista.setNome(c.getString(c.getColumnIndex("NOME")));
            lista.setAlerta(c.getInt(c.getColumnIndex("ALERTA")));
            lista.setRowid(c.getInt(c.getColumnIndex("rowid")));
        }
        lista.setItems(getItemFromList(lista.getRowid()));
        return lista;
    }

    public ArrayList<Compra> getItemFromList(int listid, int rowid) {
        ArrayList<Compra> items = new ArrayList<Compra>();
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor c = db.rawQuery("SELECT rowid, * FROM ITEM_COMPRA WHERE ID_LISTA = ? and rowid = ?;", new String[] {String.valueOf(listid), String.valueOf(rowid)});

        while(c.moveToNext()) {
            Compra i = new Compra();
            i.setRowid(c.getInt(c.getColumnIndex("rowid")));
            i.setIdLista(c.getInt(c.getColumnIndex("ID_LISTA")));
            i.setFlagComprado(c.getInt(c.getColumnIndex("FLAG_COMPRADO")));
            i.setNomeItem(c.getString(c.getColumnIndex("NOME_ITEM")));
            i.setPreco(c.getDouble(c.getColumnIndex("PRECO")));
            i.setQuantidade(c.getInt(c.getColumnIndex("QUANTIDADE")));
            items.add(i);
        }
        return items;
    }

    public ArrayList<Compra> getItemFromList(int rowid) {
        ArrayList<Compra> items = new ArrayList<Compra>();
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor c = db.rawQuery("SELECT rowid, * FROM ITEM_COMPRA WHERE ID_LISTA = ?;", new String[] {String.valueOf(rowid)});

        while(c.moveToNext()) {
            Compra i = new Compra();
            i.setRowid(c.getInt(c.getColumnIndex("rowid")));
            i.setIdLista(c.getInt(c.getColumnIndex("ID_LISTA")));
            i.setFlagComprado(c.getInt(c.getColumnIndex("FLAG_COMPRADO")));
            i.setNomeItem(c.getString(c.getColumnIndex("NOME_ITEM")));
            i.setPreco(c.getDouble(c.getColumnIndex("PRECO")));
            i.setQuantidade(c.getInt(c.getColumnIndex("QUANTIDADE")));
            items.add(i);
        }
        return items;
    }

    public boolean saveItemInList(int idLista, int flag, String nome, double preco, int qtt) {
        return saveItemInList(idLista, flag, nome, preco, qtt, 0) ;
    }

    public boolean validaItem(String item) {
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT rowid, * FROM ITEM_COMPRA WHERE NOME_ITEM = ?;", new String[] {item});
        if (c.getCount() > 0)
            return true;
        return false;
    }

    public boolean saveItemInList(int idLista, int flag, String nome, double preco, int qtt, int rowid) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_LISTA", idLista);
        values.put("FLAG_COMPRADO", flag);
        values.put("NOME_ITEM", nome);
        values.put("PRECO", preco);
        values.put("QUANTIDADE", qtt);
        long resultado;
        if (rowid > 0) {
            resultado = db.update("ITEM_COMPRA", values, "rowid = ?", new String[] {String.valueOf(rowid)});
        } else {
            resultado = db.insert("ITEM_COMPRA", null, values);
        }


        if (resultado == -1) {
            return false;
        }
        return true;
    }

    public int boughtItem(int rowid, int flag) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FLAG_COMPRADO", flag);
        return db.update("ITEM_COMPRA", values, "rowid = ?", new String[] {String.valueOf(rowid)});
    }

    public void deleteItem(int rowid) {
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete("ITEM_COMPRA", "rowid = ?", new String[] {String.valueOf(rowid)});
    }


    public void deleteList(int rowid) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ArrayList<Compra> items = getItemFromList(rowid);
        for (Compra item:items) {
            deleteItem(item.getRowid());
        }
        db.delete("LISTA", "rowid = ?", new String[] {String.valueOf(rowid)});
    }



}
