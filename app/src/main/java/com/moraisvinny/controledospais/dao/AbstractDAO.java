package com.moraisvinny.controledospais.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vinic on 19/06/2017.
 */

public abstract class AbstractDAO extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "ControlePais";
    private static final int VERSAO_BANCO = 1;

    public AbstractDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE Senhas (id INTEGER PRIMARY KEY, senha INTEGER NOT NULL, email TEXT NOT NULL)";
        String msg = "Passei no cria tabela Senhas";
        Log.wtf(msg, msg);
        db.execSQL(sql);

        sql = "CREATE TABLE Aplicativos (id INTEGER PRIMARY KEY, launcher TEXT NOT NULL)";
        msg = "Passei no cria tabela Aplicativos";
        Log.wtf(msg, msg);
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Senhas";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS Aplicativos";
        db.execSQL(sql);


        onCreate(db);
    }
}
