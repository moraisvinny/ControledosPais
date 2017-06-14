package com.estilotech.controledospais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by vinic on 14/06/2017.
 */

public class ConfiguracoesActivity extends AppCompatActivity {

    ListView opcoes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        opcoes = (ListView) findViewById(R.id.activity_configuracoes_opcoes);

        opcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //TODO refatorar esta merda
                        Intent intentSenha = new Intent(ConfiguracoesActivity.this, CadastroSenhaActivity.class);
                        ConfiguracoesActivity.this.startActivity(intentSenha);
                        break;
                    case 1:
                        break;
                }

            }
        });

    }

}
