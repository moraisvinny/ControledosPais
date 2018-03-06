package com.estilotech.controledospais;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by vinic on 21/06/2017.
 */

public class VigilanteServico extends IntentService {

    Vigilante vigilante;

    public VigilanteServico() {
        super("VigilanteServico");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        //vigilante = new Vigilante();



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Para de fazer o que tava fazendo.
    }
}
