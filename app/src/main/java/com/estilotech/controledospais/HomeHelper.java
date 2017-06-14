package com.estilotech.controledospais;

import android.app.Activity;
import android.content.Intent;


/**
 * Created by vinic on 14/06/2017.
 */

public class HomeHelper {


    public void sair(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

}
