package mx.com.sousystems.wbcscounter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.controller.PacienteController;
import mx.com.sousystems.wbcscounter.domain.Paciente;

public class CalculoActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, View.OnClickListener{
    Spinner spinnerPaciente;
    Button btnCalcular;
    PacienteController pacienteController;
    List<Paciente> listaPacienteAux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pacienteController = new PacienteController(this);
        listaPacienteAux = new ArrayList<>();
        cargarComponente();
        agregarDatosSpinner();
    }

    private void cargarComponente(){
        spinnerPaciente = findViewById(R.id.spinnerPaciente);
        spinnerPaciente.setOnItemSelectedListener(this);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(this);
    }

    private void agregarDatosSpinner(){
        List<Paciente> listaPaciente = pacienteController.obtenerTodosPacientes();
        List<String> listaNombre = new ArrayList<>();
        listaNombre.add(this.getString(R.string.mensaje_seleccione_paciente));
        listaPacienteAux.add(null);
        for (int i = 0; i < listaPaciente.size(); i++){
            listaNombre.add(listaPaciente.get(i).getNombre());
            listaPacienteAux.add(listaPaciente.get(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listaNombre);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaciente.setAdapter(dataAdapter);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){

        }else{
            Toast.makeText(getApplicationContext(), listaPacienteAux.get(position).getId()+" "+listaPacienteAux.get(position).getNombre(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Vacio
    }

    @Override
    public void onClick(View v) {

    }
}
