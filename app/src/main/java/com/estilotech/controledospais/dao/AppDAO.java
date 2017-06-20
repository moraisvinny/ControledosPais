package com.estilotech.controledospais.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinic on 17/06/2017.
 */

public class AppDAO extends AbstractDAO {


    public AppDAO(Context context) {
        super(context);
    }

    public List<String> obterTodosAppsLiberados() {

        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM Aplicativos", null);
        List<String> apps = new ArrayList<>();

        while (cursor.moveToNext()) {
            apps.add(cursor.getString(cursor.getColumnIndex("launcher")));
        }

        return apps;
    }

    public void remover(String launcher) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete("Aplicativos", "launcher = ?", new String[]{launcher});
    }

    public void inserir(String launcher) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put("launcher", launcher);
        db.insert("Aplicativos", null, cv);
    }

    public Boolean conferirLauncherDoApp(String launcher) {
        Cursor cursor = null;
        Boolean retorno = Boolean.FALSE;
        SQLiteDatabase db = getReadableDatabase();

        try {
            cursor = db.query(
                    false,  // distinct
                    "Aplicativos", // tabela
                    new String[]{"launcher"}, // coluna selecionada
                    "launcher = ?", // where (selection)
                    new String[]{launcher}, // parametro
                    null,null,null,null); // group by, order by, etc...
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                retorno = Boolean.TRUE;
            }
            return retorno;
        }finally {
            cursor.close();
        }
    }
}