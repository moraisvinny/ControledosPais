package com.estilotech.controledospais.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.estilotech.controledospais.ResetLauncherActivity;

/**
 * Created by vinic on 22/06/2017.
 */

public class ResetLauncherHelper {

    private Context context;

    public ResetLauncherHelper(Context context) {
        this.context = context;
    }

    public void resetDefault() {
        PackageManager p = context.getPackageManager();
        ComponentName cN = new ComponentName(context, ResetLauncherActivity.class);
        p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        Intent selector = new Intent(Intent.ACTION_MAIN);
        selector.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(selector);

        p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
}
