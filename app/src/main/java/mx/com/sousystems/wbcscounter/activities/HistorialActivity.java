package mx.com.sousystems.wbcscounter.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.controller.PacienteController;
import mx.com.sousystems.wbcscounter.domain.Paciente;

public class HistorialActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    Spinner spinnerPaciente;
    EditText etFechaIni;
    EditText etFechaFin;
    ImageButton btnFechaIni;
    ImageButton btnFechaFin;
    Button btnBuscar;
    PacienteController pacienteController;
    List<Paciente> listaPacienteAux;
    private static final int OPCION_1 = 1;
    private static final int OPCION_2 = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cargarComponente();
        pacienteController = new PacienteController(this);
        listaPacienteAux = new ArrayList<>();
        agregarDatosSpinner();
        obtenerFechaDefault();
    }

    private void cargarComponente(){
        spinnerPaciente = findViewById(R.id.spPaciente);
        spinnerPaciente.setOnItemSelectedListener(this);
        etFechaIni = findViewById(R.id.etFechaIni);
        etFechaIni.setOnClickListener(this);
        etFechaFin = findViewById(R.id.etFechaFin);
        etFechaFin.setOnClickListener(this);
        btnFechaIni = findViewById(R.id.btnFechaIni);
        btnFechaIni.setOnClickListener(this);
        btnFechaFin = findViewById(R.id.btnFechaFin);
        btnFechaFin.setOnClickListener(this);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);
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
            //
        }else{
            Toast.makeText(getApplicationContext(), listaPacienteAux.get(position).getId()+" "+listaPacienteAux.get(position).getNombre(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
       //
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFechaIni:
                obtenerFecha(OPCION_1);
                break;
            case R.id.btnFechaFin:
                obtenerFecha(OPCION_2);
                break;
            case R.id.etFechaIni:
                obtenerFecha(OPCION_1);
                break;
            case R.id.etFechaFin:
                obtenerFecha(OPCION_2);
                break;
            case R.id.btnBuscar:
                buscar();
                break;
            default:
                break;
        }
    }

    public void buscar(){
        //
    }

    private void obtenerFecha(final int opcion){
        // Get Current Date
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(opcion == 1){
                            etFechaIni.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }else{
                            etFechaFin.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void obtenerFechaDefault(){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        etFechaIni.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
        etFechaFin.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
    }
}
