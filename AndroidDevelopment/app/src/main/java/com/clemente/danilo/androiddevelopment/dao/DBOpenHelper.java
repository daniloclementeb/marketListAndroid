package com.clemente.danilo.androiddevelopment.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 23/07/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "todolist.db";
    private static final int VERSAO_BANCO​ = 1;
    private Context ctx;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSAO_BANCO​);
        this.ctx = context;
    }


    public DBOpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, VERSAO_BANCO​);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER (" +
                "USUARIO TEXT NOT NULL PRIMARY KEY," +
                "SENHA TEXT NOT NULL);");
        db.execSQL("CREATE TABLE LISTA (" +
                "NOME TEXT NOT NULL, " +
                "ALERTA NUMBER NOT NULL," +
                "USUARIO TEXT NOT NULL);");
        db.execSQL("CREATE TABLE ITEM_COMPRA (" +
                "ID_LISTA NUMBER NOT NULL, " +
                "NOME_ITEM TEXT NOT NULL, " +
                "FLAG_COMPRADO NUMBER NOT NULL, " +
                "PRECO NUMBER, " +
                "QUANTIDADE NUMBER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
