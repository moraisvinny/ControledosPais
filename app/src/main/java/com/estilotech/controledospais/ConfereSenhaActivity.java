package com.estilotech.controledospais;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.estilotech.controledospais.common.SenhaVO;
import com.estilotech.controledospais.dao.SenhaDAO;

/**
 * Created by vinic on 16/06/2017.
 */

public class ConfereSenhaActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confere_senha);

    }

    public void conferirSenha(View view) {

        SenhaDAO senhaDAO = new SenhaDAO(this);
        EditText senhaET = (EditText) findViewById(R.id.confere_senha_senha);
        Integer senha = Integer.valueOf(senhaET.getText().toString());

        SenhaVO senhaVO = senhaDAO.obter();
        if(senhaVO == null) {
            Toast.makeText(this, "Ocorreu um erro ao executar a operação", Toast.LENGTH_SHORT).show();
            finish();
        } else if(!senhaVO.getSenha().equals(senha)){
            Toast.makeText(this, "Senha incorreta", Toast.LENGTH_SHORT).show();
        } else {
            setResult(RESULT_OK);
            finish();
        }
    }
}
