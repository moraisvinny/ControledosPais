package com.moraisvinny.controledospais;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.moraisvinny.controledospais.common.SenhaVO;
import com.moraisvinny.controledospais.dao.SenhaDAO;
import com.moraisvinny.controledospais.helper.FocoETecladoHelper;


/**
 * Created by vinic on 16/06/2017.
 */

public class ConfereSenhaActivity extends Activity {

    private EditText senhaET;
    private InputMethodManager imm;
    private FocoETecladoHelper fkh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confere_senha);


        senhaET = (EditText) findViewById(R.id.confere_senha_senha);
        fkh = new FocoETecladoHelper(this,senhaET);
        fkh.focaEMostraTeclado();

    }

    public void conferirSenha(View view) {

        fkh.escondeTeclado();

        SenhaDAO senhaDAO = new SenhaDAO(this);


        if(senhaET.getText().toString().equals("")) {
            Toast.makeText(this,"Informe a senha para prosseguir", Toast.LENGTH_SHORT).show();
        } else{
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
}
