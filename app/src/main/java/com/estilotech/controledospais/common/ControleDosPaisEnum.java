package com.estilotech.controledospais.common;

/**
 * Created by vinic on 15/06/2017.
 */

public enum ControleDosPaisEnum {

    CONFIGURACAO_APPS_LIBERADOS(0),
    CONFIGURACAO_RESTAURAR_LAUNCHER(1),
    CONFIGURACAO_ALTERAR_SENHA(2),
    CONFIGURACAO_ESQUECI_SENHA(3),
    CONFIGURACAO_DOACAO(4);



    private final int codigo;
    ControleDosPaisEnum(int position) {
        this.codigo = position;
    }

    public int getCodigo() {
        return codigo;
    }
}
