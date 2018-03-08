package com.moraisvinny.controledospais.helper;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * Classe responsável por requisitar o foco para uma view (Normalmente EditText)
 * e já abrir o teclado para preenchimento do campo
 * Possui método para esconder o teclado
 * Created by vinic on 22/06/2017.
 */

public class FocoETecladoHelper {

    private View view;
    private Context context;
    private InputMethodManager imm;

    /**
     * Construtor
     * @param context Quem está chamando
     * @param view Campo da tela que receberá o foco
     */
    public FocoETecladoHelper(Context context, View view){
        this.view = view;
        this.context = context;
        imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
    }

    /**
     * Foca no campo e exibe o teclado
     */
    public void focaEMostraTeclado(){

        view.requestFocus();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    /**
     * Esconde o Teclado
     */
    public void escondeTeclado(){
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
