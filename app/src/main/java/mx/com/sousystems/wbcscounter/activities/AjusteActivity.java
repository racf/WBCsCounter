package mx.com.sousystems.wbcscounter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.domain.Paciente;
import mx.com.sousystems.wbcscounter.util.Util;

public class AjusteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private Spinner spAlertarCantidad;
    private CheckBox cbActivarVibracion, cbActivarSonido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuste);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cargarComponentes();
        agregarDatosSpinner();
    }

    private void cargarComponentes() {
        spAlertarCantidad = findViewById(R.id.spAlertarCantidad);
        cbActivarSonido = findViewById(R.id.cbActivarSonido);
        cbActivarVibracion = findViewById(R.id.cbActivarVibracion);
        cbActivarSonido.setOnCheckedChangeListener(this);
        cbActivarVibracion.setOnCheckedChangeListener(this);
        String sActivarSonido = Util.readSharedPreference(this, R.string.activar_sonido_preference);
        cbActivarSonido.setChecked(true);
        if (!sActivarSonido.isEmpty()){
            cbActivarSonido.setChecked(Boolean.parseBoolean(sActivarSonido));
        }
        String sActivarVibracion = Util.readSharedPreference(this, R.string.activar_vibracion_preference);
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
        String sCantidadAlertar = Util.readSharedPreference(this, R.string.cantidad_alertar_preference);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String value = (String) parent.getItemAtPosition(position);
        Util.writeSharedPreference(this, R.string.cantidad_alertar_preference, value);
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
                Util.writeSharedPreference(this, R.string.activar_sonido_preference, String.valueOf(isChecked));
                break;
            case R.id.cbActivarVibracion:
                Util.writeSharedPreference(this, R.string.activar_vibracion_preference, String.valueOf(isChecked));
                break;
            default:
        }
    }
}
