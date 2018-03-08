package com.moraisvinny.controledospais.common;

import android.graphics.drawable.Drawable;

/**
 * Created by vinic on 13/06/2017.
 */

public class AppVO {



    private boolean selecionado;
    private CharSequence label;
    private CharSequence name;
    private Drawable icon;

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public CharSequence getLabel() {
        return label;
    }

    public void setLabel(CharSequence label) {
        this.label = label;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
