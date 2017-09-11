package com.clemente.danilo.androiddevelopment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.clemente.danilo.androiddevelopment.model.User;
import com.clemente.danilo.androiddevelopment.model.Usuario;

/**
 * Created by HP on 23/07/2017.
 */

public class UserDAO {
    private DBOpenHelper banco;

    public UserDAO(Context ctx) {
        banco = new DBOpenHelper(ctx);
    }

    public Usuario getUser(String user, String pass) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USUARIO", user);
        values.put("SENHA", pass);
        Cursor c = db.rawQuery("SELECT rowId, * FROM USER WHERE USUARIO = ? and SENHA = ?", new String[] {user, pass});
        Usuario usuario = new Usuario();
        if (c.getCount() > 0) {
            c.moveToNext();
            usuario.setLogin(c.getString(c.getColumnIndex("USUARIO")));
            usuario.setRowid(c.getString(c.getColumnIndex("rowid")));
        }
        return usuario;
    }

    public boolean insert(String user, String pass) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USUARIO", user);
        values.put("SENHA", pass);
        long resultado = db.insert("USER", null, values);

        if (resultado == -1) {
            return false;
        }
        return true;
    }

    public boolean insert(User user) {
        return insert(user.getUsuario(), user.getSenha());
    }

    public Usuario getUserByRowId(int rowId) {
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USER WHERE ROWID = ?;", new String[] {String.valueOf(rowId)});
        Usuario usuario = new Usuario();
        if (c.getCount() > 0) {
            usuario.setLogin(c.getString(c.getColumnIndex("USUARIO")));
            usuario.setRowid(c.getString(c.getColumnIndex("ROWID")));
        }
        return usuario;
    }
}
