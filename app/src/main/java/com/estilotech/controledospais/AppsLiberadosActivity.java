package com.estilotech.controledospais;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.estilotech.controledospais.common.AppVO;
import com.estilotech.controledospais.dao.AppDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinic on 17/06/2017.
 */

public class AppsLiberadosActivity extends AppCompatActivity{

    List<AppVO> appsInstalados = null;
    List<String> appsBanco = null;
    private ArrayAdapter<AppVO> adapter;
    private AppDAO appDAO;
    private ListView listaAppsLiberados;
    boolean[] checks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_liberados);
        appDAO = new AppDAO(this);

        obterAppsInstalados();
        popularCheckLiberado();
        carregarListView();

    }

    private void carregarListView() {
        listaAppsLiberados = (ListView) findViewById(R.id.lista_apps_liberados);
        adapter = new ArrayAdapter<AppVO>(this, R.layout.lista_apps_instalados,appsInstalados) {

            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.lista_apps_instalados, null);
                }

                CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.app_instalado_liberado);
                checkBox.setChecked(appsInstalados.get(position).isSelecionado());

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CheckBox check = (CheckBox) v;
                        appsInstalados.get(position).setSelecionado(check.isChecked());
                    }
                });

                ImageView icone = (ImageView) convertView.findViewById(R.id.app_instalado_icone);
                icone.setImageDrawable(appsInstalados.get(position).getIcon());

                TextView nomeApp = (TextView) convertView.findViewById(R.id.app_instalado_nome);
                nomeApp.setText(appsInstalados.get(position).getLabel());


                return convertView;
            }
        };

        listaAppsLiberados.setAdapter(adapter);
    }

    public void salvar(MenuItem menuItem) {

        for(AppVO vo : appsInstalados) {
            if(vo.isSelecionado()) {
                appDAO.inserir(vo.getName().toString());
            } else {
                appDAO.remover(vo.getName().toString());
            }
        }

        Intent intentHome = new Intent(this, HomeActivity.class);
        startActivity(intentHome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void popularCheckLiberado() {

        appsBanco = appDAO.obterTodosAppsLiberados();
        boolean existe = false;

        for(AppVO appVO : appsInstalados) {

            if(appsBanco.contains(appVO.getName().toString())) {
                appVO.setSelecionado(true);
            } else {
                appDAO.remover(appVO.getName().toString());
            }

        }

    }

    private void obterAppsInstalados() {
        PackageManager manager = getPackageManager();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        appsInstalados = new ArrayList<>();
        for(ResolveInfo ri:availableActivities){
            AppVO app = new AppVO();
            app.setLabel(ri.loadLabel(manager));
            app.setName(ri.activityInfo.packageName);
            app.setIcon(ri.activityInfo.loadIcon(manager));
            appsInstalados.add(app);
        }
    }
}
