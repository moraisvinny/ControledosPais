package com.estilotech.controledospais;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estilotech.controledospais.common.AppVO;
import com.estilotech.controledospais.common.SenhaVO;
import com.estilotech.controledospais.dao.AppDAO;
import com.estilotech.controledospais.dao.SenhaDAO;
import com.estilotech.controledospais.helper.ResetLauncherHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinic on 13/06/2017.
 */

public class HomeActivity extends Activity {

    public static final String APP_CONFIG = "Config";
    private static final int OVERLAY_PERMISSION_CODE = 777;
    private PackageManager manager;
    private List<AppVO> apps;
    private GridView list;

    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE;
    private static WindowManager windowManager;
    private static CustomViewGroup view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Verifica a permissão para criar camada que previne click na barra de status
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_CODE);
            } else {
                preventStatusBarExpansion(this);
                verificaPrimeiroAcesso();
            }
        } else {
            preventStatusBarExpansion(this);
            verificaPrimeiroAcesso();
        }

        setContentView(R.layout.activity_home);

        loadApps();
        loadGridView();
        addClickListener();
    }

    private void verificaPrimeiroAcesso() {
        SenhaDAO senhaDAO = new SenhaDAO(this);
        final SenhaVO senhaBanco = senhaDAO.obter();
        if(senhaBanco == null) {
            ResetLauncherHelper resetLauncherHelper = new ResetLauncherHelper(this);
            resetLauncherHelper.resetDefault();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    //Toast.makeText(MyProtector.getContext(), "ACTION_MANAGE_OVERLAY_PERMISSION Permission Granted", Toast.LENGTH_SHORT).show();
                    preventStatusBarExpansion(this);
                    verificaPrimeiroAcesso();

                }
            }
        }
    }
    private void loadApps(){
        manager = getPackageManager();
        apps = new ArrayList<AppVO>();

        AppVO appConfig = new AppVO();
        Drawable iconeConfig = ContextCompat.getDrawable(this,R.drawable.sett_icon);
        appConfig.setLabel("Configurações");
        appConfig.setName(APP_CONFIG);
        appConfig.setIcon(iconeConfig);
        apps.add(appConfig);

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        AppDAO appDAO = new AppDAO(this);
        List<String> appsBanco = appDAO.obterTodosAppsLiberados();
        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for(ResolveInfo ri:availableActivities){

            if(appsBanco.contains(ri.activityInfo.packageName.toString())) {
                AppVO app = new AppVO();
                app.setLabel(ri.loadLabel(manager));
                app.setName(ri.activityInfo.packageName);
                app.setIcon(ri.activityInfo.loadIcon(manager));
                apps.add(app);
            }

        }
    }
    private void loadGridView(){
        list = (GridView)findViewById(R.id.grid_apps);

        ArrayAdapter<AppVO> adapter = new ArrayAdapter<AppVO>(this,
                R.layout.icones_home,
                apps) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.icones_home, null);
                }

                ImageView appIcon = (ImageView)convertView.findViewById(R.id.item_app_icon);
                appIcon.setImageDrawable(apps.get(position).getIcon());

                TextView appLabel = (TextView)convertView.findViewById(R.id.item_app_label);
                appLabel.setText(apps.get(position).getLabel());

                return convertView;
            }
        };

        list.setAdapter(adapter);
    }

    private void addClickListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos, long id) {

                AppVO appVO = apps.get(pos);
                if(appVO.getName().equals(APP_CONFIG)) {
                    Intent intentConfig = new Intent(HomeActivity.this, ConfiguracoesActivity.class);
                    HomeActivity.this.startActivity(intentConfig);

                } else {
                    Intent i = manager.getLaunchIntentForPackage(appVO.getName().toString());
                    HomeActivity.this.startActivity(i);
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadApps();
        loadGridView();
        addClickListener();
    }

    public static void preventStatusBarExpansion(Context context) {
        windowManager = ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE));

        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int result = 0;
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            // Use Fallback size:
            result = 60; // 60px Fallback
        }

        localLayoutParams.height = result;
        localLayoutParams.format = PixelFormat.TRANSPARENT;

        view = new CustomViewGroup(context);
        windowManager.addView(view, localLayoutParams);


    }

    @Override
    protected void onDestroy() {
        //previne a tela de ficar com a barra de status desabilitada mesmo quando sair do app
        windowManager.removeViewImmediate(view);
        super.onDestroy();
    }

    public static class CustomViewGroup extends ViewGroup {
        public CustomViewGroup(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            // Intercepted touch!
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        //Faz nada mesmo
    }
}

