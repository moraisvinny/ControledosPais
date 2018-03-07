package com.estilotech.controledospais.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import com.estilotech.controledospais.ConfiguracoesActivity;
import com.estilotech.controledospais.R;
import com.estilotech.controledospais.common.SenhaVO;
import com.estilotech.controledospais.mail.SendMail;

/**
 * Created by vinic on 16/06/2017.
 */

public class DialogHelper {

    /**
     * Método que cria dialog e dispara o envio do email de recuperação de senha
     *
     * @param context Contexto
     * @param senhaBanco SenhaBancoVO
     * @param webView Webview
     */
    public static void criarDialogRecuperacaoSenha(final Context context, final SenhaVO senhaBanco, final WebView webView) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        webView.loadUrl("file:///android_asset/email.html");
        AlertDialog dialog = null;

        builder.setMessage(R.string.recupera_senha_pergunta)
                .setTitle(R.string.recupera_senha_titulo);
        // Add the buttons
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                webView.evaluateJavascript(
                        this.montaChamadaJS(senhaBanco),
                        null);
                Toast.makeText(context, R.string.recupera_senha_enviado, Toast.LENGTH_SHORT).show();

            }

            private String montaChamadaJS(SenhaVO senhaBanco) {

                StringBuilder sb = new StringBuilder();

                sb.append("emailjs.send(\"controle_dos_pais\", \"controle_dos_pais\", {\"dest\":\"");
                sb.append(senhaBanco.getEmail());
                sb.append("\",\"senha\":\"");
                sb.append(senhaBanco.getSenha());
                sb.append("\"});");


                return sb.toString();
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        dialog = builder.create();
        dialog.show();

    }


}
