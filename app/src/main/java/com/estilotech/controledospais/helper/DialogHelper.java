package com.estilotech.controledospais.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;

import com.estilotech.controledospais.ConfiguracoesActivity;
import com.estilotech.controledospais.common.SenhaVO;
import com.estilotech.controledospais.mail.SendMail;

/**
 * Created by vinic on 16/06/2017.
 */

public class DialogHelper {

    public static void criarDialogRecuperacaoSenha(final Context context, final SenhaVO senhaBanco) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = null;

        builder.setMessage("Deseja receber sua senha por email?")
                .setTitle("Recuperação de Senha");
        // Add the buttons
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String message = "Sua senha no aplicativo Controle dos Pais é: " + senhaBanco.getSenha();
                SendMail sm = new SendMail(
                        context,
                        senhaBanco.getEmail(),
                        "Controle dos Pais - Recuperação de Senha",
                        message);
                if(Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                    sm.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    sm.execute();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        dialog = builder.create();
        dialog.show();

    }


}
