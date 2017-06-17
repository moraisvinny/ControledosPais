package com.estilotech.controledospais.common;

/**
 * Created by vinic on 15/06/2017.
 */

public enum ControleDosPaisEnum {

    CONFIGURACAO_ALTERAR_SENHA(0),
    CONFIGURACAO_ESQUECI_SENHA(1);

    private final int codigo;
    ControleDosPaisEnum(int position) {
        this.codigo = position;
    }

    public int getCodigo() {
        return codigo;
    }
}
