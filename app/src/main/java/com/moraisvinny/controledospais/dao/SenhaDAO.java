package com.moraisvinny.controledospais.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.moraisvinny.controledospais.common.SenhaVO;

/**
 * Created by vinic on 15/06/2017.
 */

public class SenhaDAO extends AbstractDAO {

    public static final String SELECT_FROM_SENHAS = "Select * from Senhas";

    public SenhaDAO(Context context) {
        super(context);
    }

    public void inserir(SenhaVO senhaVO) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = getContentValues(senhaVO);

        db.insert("Senhas", null,cv);

    }

    @NonNull
    private ContentValues getContentValues(SenhaVO senhaVO) {
        ContentValues cv = new ContentValues();
        cv.put("senha", senhaVO.getSenha());
        cv.put("email", senhaVO.getEmail());
        return cv;
    }

    public SenhaVO obter(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_FROM_SENHAS,new String[]{});

        SenhaVO senhaVO = null;
        boolean temRegistro = cursor.moveToNext();
        if(temRegistro) {
            senhaVO = new SenhaVO();
            senhaVO.setId(cursor.getInt(cursor.getColumnIndex("id")));
            senhaVO.setSenha(cursor.getInt(cursor.getColumnIndex("senha")));
            senhaVO.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        }

        return senhaVO;

    }

    public void atualizar(SenhaVO senhaVO) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = getContentValues(senhaVO);
        db.update("Senhas",cv,"id = ?",new String[]{senhaVO.getId().toString()});
    }
}
