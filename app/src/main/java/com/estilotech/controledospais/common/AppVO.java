package com.estilotech.controledospais.common;

import android.graphics.drawable.Drawable;

/**
 * Created by vinic on 13/06/2017.
 */

public class AppVO {

    private CharSequence label;
    private CharSequence name;
    private Drawable icon;

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
