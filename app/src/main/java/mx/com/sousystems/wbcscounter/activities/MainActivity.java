package mx.com.sousystems.wbcscounter.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    private String sActivarVibracion, sActivarSonido;
    private int iCantidadAlertar;
    private List<Celula> celulasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MobileAds.initialize(this, getString(R.string.admob_app_id));

        setNavigationDrawer();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);

        llCelulas = findViewById(R.id.llCelulas);
        tvCantidad = findViewById(R.id.tvCantidad);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

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
        //cargarPreferencias();
        permisos();
    }

    @Override
    protected void onResume() {
        cargarPreferencias();
        super.onResume();
    }

    private void cargarPreferencias(){
        String sCantidadAlertar = Util.readSharedPreference(this, R.string.cantidad_alertar_preference);
        if (!sCantidadAlertar.isEmpty()){
            try {
                iCantidadAlertar = Integer.parseInt(sCantidadAlertar);
            }catch (Exception e){
                iCantidadAlertar = -1;
            }
        }
        sActivarSonido = Util.readSharedPreference(this, R.string.activar_sonido_preference);
        sActivarVibracion = Util.readSharedPreference(this, R.string.activar_vibracion_preference);

    }
    private void permisos(){
        //Comprobando la version de Android...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final List<String> permissionsList = new ArrayList<>();
            addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permissionsList.size() > 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }

        }
    }

    /**
     * Añade los permisos a una lista en caso de que no esten autorizados por el usuario.
     * @param permissionsList lista de los permisos
     * @param permission los permisos que se van a permitir.
     * @return verdadero si los permisos ya fueron autorizados por el usuario.
     */
    private boolean addPermission(List<String> permissionsList, String permission) {
        if(checkPermission(permission) == false){
            permissionsList.add(permission);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    /**
     * Metodo que comprueba si tenemos activo algun determinado permiso.
     * @param permission los permisos que se verifican si estan activos o no.
     * @return verdadero si los permisos se encuentran activos.
     */
    private boolean checkPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }


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
        celulasList = celulaController.obtenerTodasCelulas();
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
            muestraDetalle.setCelula(celula);
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

    private void calcularResultado(){
        int cantidadTotal = 0;
        for (MuestraDetalle muestraDetalle: muestraDetalleArrayList){
            cantidadTotal += muestraDetalle.getCantidad();
        }
        if (cantidadTotal>0){
            Intent intent = new Intent(this, CalculoActivity.class);
            intent.putExtra("muestraDetalle", muestraDetalleArrayList);
            intent.putExtra("cantidadTotal", cantidadTotal);
            startActivity(intent);
        }else{
            Toast.makeText(this, R.string.cantidad_total_mayor_cero, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnResultado:
                calcularResultado();
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
                int indexCelula = -1;

                for (int i = 0; i < celulasList.size(); i++) {
                    if (celulasList.get(i) !=null && celulasList.get(i).getId().equals(celulaId)) {
                        indexCelula = i;
                    }
                }


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
                if (sActivarVibracion.isEmpty() || (!sActivarVibracion.isEmpty() && Boolean.parseBoolean(sActivarVibracion))) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(100);
                    }
                }
                if (sActivarSonido.isEmpty() || (!sActivarSonido.isEmpty() && Boolean.parseBoolean(sActivarSonido))) {
                    int frequency = 100 * indexCelula;
                    Util.playSound(2500 + frequency, 44100);
                }
                if(iCantidadAlertar!=-1 && total==iCantidadAlertar){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.cantidad_alerta)
                            .setPositiveButton(R.string.calcular, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    calcularResultado();
                                }
                            })
                            .setNegativeButton(R.string.continuar_contando, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    // Create the AlertDialog object and return it
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
            default:
                break;
        }
    }


    /**
     * Devolución de llamada para el resultado de la solicitud de permisos. Este método se invoca para cada llamada en requestPermissions(android.app.Activity, String[], int).
     * @param requestCode El código de solicitud pasó en requestPermissions (android.app.Activity, String [], int)
     * @param permissions String: Los permisos solicitados. Nunca nulo
     * @param grantResults int: Los resultados de la concesión para los permisos correspondientes son PERMISSION_GANTED o PERMISSION_DENIED. Nunca nulo
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                //perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                } else {
                    // Permission Denied
                    this.finish();
                    Toast.makeText(MainActivity.this, R.string.message_permission_denied, Toast.LENGTH_SHORT).show();
                }

            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
