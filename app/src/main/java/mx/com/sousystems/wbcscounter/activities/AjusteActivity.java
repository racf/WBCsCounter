package mx.com.sousystems.wbcscounter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.domain.Paciente;

public class AjusteActivity extends AppCompatActivity {

    private Spinner spAlertarCantidad;

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
    }

    private void agregarDatosSpinner() {
        String[] alertQuantity = {getString(R.string.seleccione_cantidad_alertar), "100", "200", "500", "1000"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, alertQuantity);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAlertarCantidad.setAdapter(dataAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
