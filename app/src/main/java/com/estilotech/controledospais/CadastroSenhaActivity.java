package com.estilotech.controledospais;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.estilotech.controledospais.common.SenhaVO;
import com.estilotech.controledospais.dao.SenhaDAO;

/**
 * Created by vinic on 14/06/2017.
 */

public class CadastroSenhaActivity extends AppCompatActivity {

    private SenhaDAO senhaDAO;
    private SenhaVO senhaBanco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_senha);

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
    public void salvarSenha(MenuItem item) {
        salvarSenha(new View(this));
    }

    public void salvarSenha(View view) {

        EditText senha1 = (EditText) findViewById(R.id.formulario_login_senha_um);
        EditText senha2 = (EditText) findViewById(R.id.formulario_login_senha_dois);
        EditText email = getEmailEditText();

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
            finish();
        }
    }
}
