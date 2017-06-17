package com.estilotech.controledospais;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.estilotech.controledospais.common.AppVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinic on 13/06/2017.
 */

public class HomeActivity extends Activity {

    private PackageManager manager;
    private List<AppVO> apps;
    private GridView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadApps();
        loadGridView();
        addClickListener();
    }
    private void loadApps(){
//        manager = getPackageManager();
        apps = new ArrayList<AppVO>();
        AppVO appSair = new AppVO();
        Drawable iconeSair = ContextCompat.getDrawable(this,R.drawable.icone_sair);
        appSair.setLabel("Sair");
        appSair.setName("Sair");
        appSair.setIcon(iconeSair);
        apps.add(appSair);


        AppVO appConfig = new AppVO();
        Drawable iconeConfig = ContextCompat.getDrawable(this,R.drawable.icone_sett);
        appConfig.setLabel("Configurações");
        appConfig.setName("Config");
        appConfig.setIcon(iconeConfig);
        apps.add(appConfig);

//        Intent i = new Intent(Intent.ACTION_MAIN, null);
//        i.addCategory(Intent.CATEGORY_LAUNCHER);

//        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
//        for(ResolveInfo ri:availableActivities){
//            AppVO app = new AppVO();
//            app.label = ri.loadLabel(manager);
//            app.name = ri.activityInfo.packageName;
//            app.icon = ri.activityInfo.loadIcon(manager);
//            apps.add(app);
//        }
    }
    private void loadGridView(){
        list = (GridView)findViewById(R.id.grid_apps);

        ArrayAdapter<AppVO> adapter = new ArrayAdapter<AppVO>(this,
                R.layout.list_item,
                apps) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.list_item, null);
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
                if(appVO.getName().equals("Sair")) {
                    HomeHelper homeHelper = new HomeHelper();
                    homeHelper.sair(HomeActivity.this);
                } else if(appVO.getName().equals("Config")) {
                    Intent intentConfig = new Intent(HomeActivity.this, ConfiguracoesActivity.class);
                    HomeActivity.this.startActivity(intentConfig);

                } else {
                    Intent i = manager.getLaunchIntentForPackage(appVO.getName().toString());
                    HomeActivity.this.startActivity(i);
                }

            }
        });


    }
}
