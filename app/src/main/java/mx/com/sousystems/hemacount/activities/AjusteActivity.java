package mx.com.sousystems.hemacount.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import mx.com.sousystems.hemacount.R;
import mx.com.sousystems.hemacount.util.Util;

public class AjusteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private Spinner spAlertarCantidad;
    private CheckBox cbActivarVibracion, cbActivarSonido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuste);
        setToolbar();
        cargarComponentes();
        agregarDatosSpinner();
    }

    private void cargarComponentes() {
        spAlertarCantidad = findViewById(R.id.spAlertarCantidad);
        cbActivarSonido = findViewById(R.id.cbActivarSonido);
        cbActivarVibracion = findViewById(R.id.cbActivarVibracion);
        cbActivarSonido.setOnCheckedChangeListener(this);
        cbActivarVibracion.setOnCheckedChangeListener(this);
        String sActivarSonido = Util.readSharedPreference(this, R.string.preference_activar_sonido);
        cbActivarSonido.setChecked(true);
        if (!sActivarSonido.isEmpty()){
            cbActivarSonido.setChecked(Boolean.parseBoolean(sActivarSonido));
        }
        String sActivarVibracion = Util.readSharedPreference(this, R.string.preference_activar_vibracion);
        cbActivarVibracion.setChecked(true);
        if (!sActivarVibracion.isEmpty()){
            cbActivarVibracion.setChecked(Boolean.parseBoolean(sActivarVibracion));
        }
    }

    private void agregarDatosSpinner() {
        String[] alertQuantity = {getString(R.string.seleccione_cantidad_alertar), "100", "200", "500", "1000"}; //CANTIDADES DE CONTEO
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alertQuantity);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAlertarCantidad.setAdapter(dataAdapter);
        spAlertarCantidad.setOnItemSelectedListener(this);
        String sCantidadAlertar = Util.readSharedPreference(this, R.string.preference_cantidad_alertar);
        if (!sCantidadAlertar.isEmpty()){
            int index = 0;
            for (int i=0;i<alertQuantity.length;i++) {
                if (alertQuantity[i].equals(sCantidadAlertar)) {
                    index = i;
                    break;
                }
            }
            spAlertarCantidad.setSelection(index);
        }
    }

    private void setToolbar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_setting);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String value = (String) parent.getItemAtPosition(position);
        Util.writeSharedPreference(this, R.string.preference_cantidad_alertar, value);
        //Log.d("WBCsCounter", value);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // CODE
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //Log.d("WBCsCounter", "isChecked: " + isChecked);
        switch (buttonView.getId()){
            case R.id.cbActivarSonido:
                Util.writeSharedPreference(this, R.string.preference_activar_sonido, String.valueOf(isChecked));
                break;
            case R.id.cbActivarVibracion:
                Util.writeSharedPreference(this, R.string.preference_activar_vibracion, String.valueOf(isChecked));
                break;
            default:
        }
    }
}
