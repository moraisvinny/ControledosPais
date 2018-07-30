package com.moraisvinny.controledospais;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.moraisvinny.controledospais.common.ControleDosPaisEnum;
import com.moraisvinny.controledospais.common.SenhaVO;
import com.moraisvinny.controledospais.dao.SenhaDAO;
import com.moraisvinny.controledospais.helper.DialogHelper;
import com.moraisvinny.controledospais.helper.ResetLauncherHelper;

/**
 * Created by vinic on 14/06/2017.
 */

public class ConfiguracoesActivity extends AppCompatActivity {

    ListView opcoes;
    private Intent intentConfereSenha;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        final WebView webView = (WebView) findViewById(R.id.emailwv);
        webView.getSettings().setJavaScriptEnabled(Boolean.TRUE);

        SenhaDAO senhaDAO = new SenhaDAO(ConfiguracoesActivity.this);
        final SenhaVO senhaBanco = senhaDAO.obter();
        if(senhaBanco == null) {

            chamarCadastroSenha(true);

        } else {
            opcoes = (ListView) findViewById(R.id.activity_configuracoes_opcoes);
            intentConfereSenha = new Intent(ConfiguracoesActivity.this,ConfereSenhaActivity.class);

            opcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    
                    if(ControleDosPaisEnum.CONFIGURACAO_ALTERAR_SENHA.getCodigo() == position) {
                        ConfiguracoesActivity.this.startActivityForResult(
                                intentConfereSenha,
                                position);
                    } else if(ControleDosPaisEnum.CONFIGURACAO_ESQUECI_SENHA.getCodigo() == position) {

                        DialogHelper.criarDialogRecuperacaoSenha(ConfiguracoesActivity.this,senhaBanco, webView);

                    } else if(ControleDosPaisEnum.CONFIGURACAO_APPS_LIBERADOS.getCodigo() == position) {
                        ConfiguracoesActivity.this.startActivityForResult(
                                intentConfereSenha,
                                position);
                    } else if(ControleDosPaisEnum.CONFIGURACAO_RESTAURAR_LAUNCHER.getCodigo() == position) {
                        ConfiguracoesActivity.this.startActivityForResult(
                                intentConfereSenha,
                                position);

                    } else if(ControleDosPaisEnum.CONFIGURACAO_DOACAO.getCodigo() == position) {
                        ConfiguracoesActivity.this.startActivityForResult(
                                intentConfereSenha,
                                position);
                    }

                }
            });
        }

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(RESULT_OK == resultCode) {
            if(ControleDosPaisEnum.CONFIGURACAO_ALTERAR_SENHA.getCodigo() == requestCode) {
                chamarCadastroSenha(false);
            } else if(ControleDosPaisEnum.CONFIGURACAO_APPS_LIBERADOS.getCodigo() == requestCode) {
                Intent intentAppsLiberados = new Intent(ConfiguracoesActivity.this, AppsLiberadosActivity.class);
                ConfiguracoesActivity.this.startActivity(intentAppsLiberados);
            } else if(ControleDosPaisEnum.CONFIGURACAO_RESTAURAR_LAUNCHER.getCodigo() == requestCode) {
                ResetLauncherHelper resetLauncherHelper = new ResetLauncherHelper(ConfiguracoesActivity.this);
                resetLauncherHelper.resetDefault();
            } else if(ControleDosPaisEnum.CONFIGURACAO_DOACAO.getCodigo() == requestCode) {
                String url = "http://moraisvinny.com/doacoes/";
                Intent intentDoacao = new Intent(Intent.ACTION_VIEW);
                intentDoacao.setData(Uri.parse(url));
                startActivity(intentDoacao);
            }
        }

    }

    private void chamarCadastroSenha(boolean primeiroAcesso) {
        Intent intentSenha = new Intent(ConfiguracoesActivity.this, CadastroSenhaActivity.class);
        intentSenha.putExtra("primeiroAcesso", primeiroAcesso);
        ConfiguracoesActivity.this.startActivity(intentSenha);
    }

}
