package mx.com.sousystems.wbcscounter.activities;

import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.controller.CelulaController;
import mx.com.sousystems.wbcscounter.domain.Celula;
import mx.com.sousystems.wbcscounter.domain.MuestraDetalle;
import mx.com.sousystems.wbcscounter.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button btnResultado, btnEliminarUltimo, btnReiniciar;
    private GridView gvCelulas;
    private ArrayList<MuestraDetalle> muestraDetalleArrayList;
    private ArrayList<MuestraDetalle> previousMuestraDetalleArrayList;
    private CelulaController celulaController;
    private LinearLayout llCelulas;
    private TextView tvCantidad;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationDrawer();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);

        llCelulas = findViewById(R.id.llCelulas);
        tvCantidad = findViewById(R.id.tvCantidad);

        vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
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

    private void cargarComponente() {
        btnResultado = findViewById(R.id.btnResultado);
        btnEliminarUltimo = findViewById(R.id.btnEliminarUltimo);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        btnResultado.setOnClickListener(this);
        btnEliminarUltimo.setOnClickListener(this);
        btnReiniciar.setOnClickListener(this);
    }

    private void crearCelulas() {
        llCelulas.removeAllViews();
        tvCantidad.setText("0");
        muestraDetalleArrayList = new ArrayList<>();
        celulaController = new CelulaController(this);
        List<Celula> celulasList = celulaController.obtenerTodasCelulas();
        int vistasAgregadas = 0;
        LinearLayout llCelulasRow = null;
        for (Celula celula : celulasList) {
            if (vistasAgregadas == 0) {
                llCelulasRow = new LinearLayout(this);
                llCelulasRow.setTag("LinearLayoutCelulasRow");
                LinearLayout.LayoutParams lPCelulasRowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llCelulasRow.setLayoutParams(lPCelulasRowParams);
                llCelulasRow.setWeightSum(3);
            }

            MuestraDetalle muestraDetalle = new MuestraDetalle();
            muestraDetalle.setCelulaId(celula.getId());
            muestraDetalle.setCantidad(0);
            muestraDetalleArrayList.add(muestraDetalle);
            celula.setNombre(Util.getStringFromResourcesByName(this, celula.getId()));

            Log.d("WBCsCounter", Util.getStringFromResourcesByName(this, celula.getId()));


            RelativeLayout rlCelula = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_celula, null);

            ImageView ivIconoCelula = (ImageView) rlCelula.findViewById(R.id.ivIconoCelula);
            TextView tvNombreCelula = (TextView) rlCelula.findViewById(R.id.tvNombreCelula);
            TextView tvCantidadPorCelula = (TextView) rlCelula.findViewById(R.id.tvCantidadPorCelula);
            tvNombreCelula.setText(celula.getNombre());
            tvCantidadPorCelula.setText("0");
            rlCelula.setTag(celula.getId());
            rlCelula.setClickable(true);
            rlCelula.setOnClickListener(this);
            LinearLayout.LayoutParams lpCelulaParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            llCelulasRow.addView(rlCelula, lpCelulaParams);
            vistasAgregadas++;

            int resId = Util.getDrawableResourceIDFromResourcesByName(this, celula.getId());
            Log.d("WBCsCounter", "ResId: " + resId);
            if (resId!=0) {
                ivIconoCelula.setImageResource(resId);
            }
            if (vistasAgregadas == 3) {
                llCelulas.addView(llCelulasRow);
                vistasAgregadas = 0;
            }

        }
        //gvCelulas.setAdapter(new CelulasAdapter(celulasList, muestraDetalleArrayList, this));
        //gvCelulas.setOnItemClickListener(this);

    }

    /**
     * Funcion para cargar  el icono del Drawer en la barra tool
     */
    private void setNavigationDrawer() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_dark);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        switch (v.getId()) {
            case R.id.btnResultado:
                Intent intent = new Intent(this, CalculoActivity.class);
                intent.putExtra("muestraDetalle", muestraDetalleArrayList);
                int cantidadTotal = 0;
                for (MuestraDetalle muestraDetalle: muestraDetalleArrayList){
                    cantidadTotal += muestraDetalle.getCantidad();
                }
                intent.putExtra("cantidadTotal", cantidadTotal);
                startActivity(intent);
                break;
            case R.id.btnEliminarUltimo:
                if (previousMuestraDetalleArrayList != null && previousMuestraDetalleArrayList.size() > 0) {
                    muestraDetalleArrayList = new ArrayList<>();
                    int total = 0;

                    for (MuestraDetalle muestraDetalle : previousMuestraDetalleArrayList) {
                        MuestraDetalle muestraDetalleNueva = new MuestraDetalle();
                        muestraDetalleNueva.setCelulaId(muestraDetalle.getCelulaId());
                        muestraDetalleNueva.setCantidad(muestraDetalle.getCantidad());
                        Log.d("WBCsCounter", String.valueOf(muestraDetalle.getCantidad()));
                        total += muestraDetalleNueva.getCantidad();
                        muestraDetalleArrayList.add(muestraDetalleNueva);
                    }

                    tvCantidad.setText(String.valueOf(total));


                    llCelulas = findViewById(R.id.llCelulas);
                    for (int index = 0; index < llCelulas.getChildCount(); index++) { //PARA CADA VISTA HIJO
                        LinearLayout llCelulasRow = (LinearLayout) llCelulas.getChildAt(index); // VISTA HIJO, FILA
                        for (int indexCelula = 0; indexCelula < llCelulasRow.getChildCount(); indexCelula++) { //VISTA HIJO DE FILA, COLUMNA - CELULA
                            RelativeLayout rlCelula = (RelativeLayout) llCelulasRow.getChildAt(indexCelula);
                            String celulaId = (String) rlCelula.getTag();
                            for (MuestraDetalle muestraDetalle : muestraDetalleArrayList) {
                                if (muestraDetalle.getCelulaId().equalsIgnoreCase(celulaId)) {
                                    TextView tvCantidadPorCelula = (TextView) rlCelula.findViewById(R.id.tvCantidadPorCelula);
                                    tvCantidadPorCelula.setText(String.valueOf(muestraDetalle.getCantidad()));
                                }
                            }
                        }

                    }
                }
                break;
            case R.id.btnReiniciar:
                crearCelulas();
                break;
            case R.id.rlCelula:
                Log.d("WBCsCounter", (String) v.getTag());
                String celulaId = (String) v.getTag();
                previousMuestraDetalleArrayList = new ArrayList<>();
                for (MuestraDetalle muestraDetalle : muestraDetalleArrayList) {
                    MuestraDetalle muestraDetalleNueva = new MuestraDetalle();
                    muestraDetalleNueva.setCelulaId(muestraDetalle.getCelulaId());
                    muestraDetalleNueva.setCantidad(muestraDetalle.getCantidad());
                    Log.d("WBCsCounter", String.valueOf(muestraDetalle.getCantidad()));
                    previousMuestraDetalleArrayList.add(muestraDetalleNueva);
                }
                int total = 0;
                for (MuestraDetalle muestraDetalle : muestraDetalleArrayList) {
                    if (muestraDetalle.getCelulaId().equalsIgnoreCase(celulaId)) {
                        muestraDetalle.setCantidad(muestraDetalle.getCantidad() + 1);

                        TextView tvCantidadPorCelula = (TextView) v.findViewById(R.id.tvCantidadPorCelula);
                        tvCantidadPorCelula.setText(String.valueOf(muestraDetalle.getCantidad()));

                    }
                    total += muestraDetalle.getCantidad();
                }
                tvCantidad.setText(String.valueOf(total));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                }else{
                    vibrator.vibrate(100);
                }
                break;
            default:
                break;
        }
    }


}
