package com.estilotech.controledospais;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.estilotech.controledospais.common.ControleDosPaisEnum;
import com.estilotech.controledospais.common.SenhaVO;
import com.estilotech.controledospais.dao.SenhaDAO;
import com.estilotech.controledospais.helper.DialogHelper;
import com.estilotech.controledospais.mail.SendMail;

/**
 * Created by vinic on 14/06/2017.
 */

public class ConfiguracoesActivity extends AppCompatActivity {

    ListView opcoes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        SenhaDAO senhaDAO = new SenhaDAO(ConfiguracoesActivity.this);
        final SenhaVO senhaBanco = senhaDAO.obter();
        if(senhaBanco == null) {

            chamarCadastroSenha(true);

        } else {
            opcoes = (ListView) findViewById(R.id.activity_configuracoes_opcoes);

            opcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if(ControleDosPaisEnum.CONFIGURACAO_ALTERAR_SENHA.getCodigo() == position) {
                        Intent intentConfereSenha =
                                new Intent(ConfiguracoesActivity.this,ConfereSenhaActivity.class);
                        ConfiguracoesActivity.this.startActivityForResult(
                                intentConfereSenha,
                                ControleDosPaisEnum.CONFIGURACAO_ALTERAR_SENHA.getCodigo());
                    } else if(ControleDosPaisEnum.CONFIGURACAO_ESQUECI_SENHA.getCodigo() == position) {

                        DialogHelper.criarDialogRecuperacaoSenha(ConfiguracoesActivity.this,senhaBanco);

                    }

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(RESULT_OK == resultCode) {
            if(ControleDosPaisEnum.CONFIGURACAO_ALTERAR_SENHA.getCodigo() == requestCode) {
                chamarCadastroSenha(false);
            }
        }

    }

    private void chamarCadastroSenha(boolean primeiroAcesso) {
        Intent intentSenha = new Intent(ConfiguracoesActivity.this, CadastroSenhaActivity.class);
        intentSenha.putExtra("primeiroAcesso", primeiroAcesso);
        ConfiguracoesActivity.this.startActivity(intentSenha);
    }

}
