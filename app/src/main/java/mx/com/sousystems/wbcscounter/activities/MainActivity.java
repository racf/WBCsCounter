package mx.com.sousystems.wbcscounter.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.controller.CelulaController;
import mx.com.sousystems.wbcscounter.domain.Celula;
import mx.com.sousystems.wbcscounter.domain.MuestraDetalle;
import mx.com.sousystems.wbcscounter.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button btnResultado;
    private GridView gvCelulas;
    private ArrayList<MuestraDetalle> muestraDetalleArrayList;
    private ArrayList<MuestraDetalle> previousMuestraDetalleArrayList;
    private CelulaController celulaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationDrawer();
        drawerLayout =  findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);
        gvCelulas = findViewById(R.id.gvCelulas);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_patient:
                        drawerLayout.closeDrawers();
                        Intent intentPaciente = new Intent(getApplicationContext(), PacienteActivity.class);
                        startActivity(intentPaciente);
                        return true;
                    case R.id.menu_record:
                        drawerLayout.closeDrawers();
                        Intent intentHistorial = new Intent(getApplicationContext(), HistorialActivity.class);
                        startActivity(intentHistorial);
                        return true;
                    case R.id.menu_setting:
                        drawerLayout.closeDrawers();
                        Intent intentAjuste = new Intent(getApplicationContext(), AjusteActivity.class);
                        startActivity(intentAjuste);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        cargarComponente();
        crearCelulas();
    }

    //RELLENA CON VALORES CADA CELULA
    //VERIFICO SI HAY POR LO MENOS RELLENA
    //ENVIO LOS DATOS
    //  SE ENVIA LOS DATOS (MUESTRA DETALLE CON INFORMACION)
    //  SE RECIBEN LOS DATOS
    //  RELLENAS EL OBJETO *MUESTRA*

    private void cargarComponente(){
        btnResultado = findViewById(R.id.btnResultado);
        btnResultado.setOnClickListener(this);
    }

    private void crearCelulas(){
        muestraDetalleArrayList = new ArrayList<>();
        celulaController = new CelulaController(this);
        List<Celula> celulasList = celulaController.obtenerTodasCelulas();
        for (Celula celula:celulasList) {
            MuestraDetalle muestraDetalle = new MuestraDetalle();
            muestraDetalle.setCelulaId(celula.getId());
            muestraDetalle.setCantidad(0);
            muestraDetalleArrayList.add(muestraDetalle);
            celula.setNombre(Util.getStringFromResourcesByName(this, celula.getId()));

            Log.d("WBCsCounter", Util.getStringFromResourcesByName(this, celula.getId()));
        }
        gvCelulas.setAdapter(new CelulasAdapter(celulasList, muestraDetalleArrayList, this));
        gvCelulas.setOnItemClickListener(this);

    }
    /**
     * Funcion para cargar  el icono del Drawer en la barra tool
     */
    private void setNavigationDrawer(){
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_dark);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                 drawerLayout.openDrawer(Gravity.START);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnResultado:
                Intent intent = new Intent(this, CalculoActivity.class);
                intent.putExtra("muestraDetalle", muestraDetalleArrayList);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CelulasAdapter celulasAdapter = (CelulasAdapter) gvCelulas.getAdapter();
        Celula celula = (Celula) celulasAdapter.getItem(position);
        Log.d("WBCsCounter", celula.getNombre());
        previousMuestraDetalleArrayList = muestraDetalleArrayList;
        for (MuestraDetalle muestraDetalle:muestraDetalleArrayList){
            if (muestraDetalle.getCelulaId().equalsIgnoreCase(celula.getId())){
                muestraDetalle.setCantidad(muestraDetalle.getCantidad()+1);
            }
        }
        celulasAdapter.notifyDataSetChanged();
    }
}
