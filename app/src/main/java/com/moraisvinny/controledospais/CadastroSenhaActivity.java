package com.moraisvinny.controledospais;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moraisvinny.controledospais.common.SenhaVO;
import com.moraisvinny.controledospais.dao.SenhaDAO;
import com.moraisvinny.controledospais.helper.FocoETecladoHelper;

/**
 * Created by vinic on 14/06/2017.
 */

public class CadastroSenhaActivity extends AppCompatActivity {

    private SenhaDAO senhaDAO;
    private SenhaVO senhaBanco;
    private FocoETecladoHelper focoETecladoHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_senha);
        focoETecladoHelper = new FocoETecladoHelper(this,findViewById(R.id.formulario_login_senha_um));
        focoETecladoHelper.focaEMostraTeclado();

        senhaDAO = new SenhaDAO(this);
        TextView texto = (TextView) findViewById(R.id.formulario_login_texto);
        if(getIntent().getBooleanExtra("primeiroAcesso", false)) {
            texto.setText("Como é o seu primeiro acesso, por favor, cadastre uma senha:");
        } else {
            senhaBanco = senhaDAO.obter();
            EditText email = getEmailEditText();
            email.setText(senhaBanco.getEmail());
        }

    }

    private EditText getEmailEditText() {
        return (EditText) findViewById(R.id.formulario_login_email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void salvar(MenuItem item) {
        salvarSenha(new View(this));
    }

    public void salvarSenha(View view) {

        focoETecladoHelper.escondeTeclado();
        EditText senha1 = (EditText) findViewById(R.id.formulario_login_senha_um);
        EditText senha2 = (EditText) findViewById(R.id.formulario_login_senha_dois);
        EditText email = getEmailEditText();

        if(senha1.getText().toString().equals("")
                || senha2.getText().toString().equals("")) {
            Toast.makeText(
                    this,
                    "Preencha os campos de senha para continuar",
                    Toast.LENGTH_SHORT).show();

        } else{
            Integer senha1Int = Integer.valueOf(senha1.getText().toString());
            Integer senha2Int = Integer.valueOf(senha2.getText().toString());
            int tamanhoCampoSenha = senha1.getText().toString().length();
            if(!senha1Int.equals(senha2Int)) {
                Toast.makeText(
                        this,
                        "Senhas não conferem. Tente novamente.",
                        Toast.LENGTH_SHORT).show();
            } else if(tamanhoCampoSenha < 4 || tamanhoCampoSenha > 8){
                Toast.makeText(
                        this,
                        "A senha deve possuir entre 4 e 8 números",
                        Toast.LENGTH_LONG).show();

            } else if(email.getText().toString().equals("")) {
                Toast.makeText(
                        this,
                        "Email é um campo obrigatório",
                        Toast.LENGTH_SHORT).show();
            } else {

                SenhaVO senhaVO = new SenhaVO();
                senhaVO.setSenha(senha1Int);
                senhaVO.setEmail(email.getText().toString());

                if(senhaBanco == null) {
                    senhaBanco = senhaDAO.obter();
                }

                if(senhaBanco == null) {
                    senhaDAO.inserir(senhaVO);
                } else {

                    senhaBanco.setEmail(senhaVO.getEmail());
                    senhaBanco.setSenha(senhaVO.getSenha());
                    senhaDAO.atualizar(senhaBanco);
                }

                Toast.makeText(this, "Senha salva com sucesso.", Toast.LENGTH_SHORT).show();
                Intent intentConfig = new Intent(this, ConfiguracoesActivity.class);
                startActivity(intentConfig);
            }

        }

    }
}
