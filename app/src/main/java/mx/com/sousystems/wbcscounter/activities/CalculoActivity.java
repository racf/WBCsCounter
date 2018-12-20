package mx.com.sousystems.wbcscounter.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.adapters.HeaderAdapter;
import mx.com.sousystems.wbcscounter.adapters.TablaAdapter;
import mx.com.sousystems.wbcscounter.controller.CelulaController;
import mx.com.sousystems.wbcscounter.controller.PacienteController;
import mx.com.sousystems.wbcscounter.domain.Celula;
import mx.com.sousystems.wbcscounter.domain.Paciente;
import mx.com.sousystems.wbcscounter.dto.HeaderTablaDTO;
import mx.com.sousystems.wbcscounter.dto.MuestraDTO;

public class CalculoActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, View.OnClickListener{
    Spinner spinnerPaciente;
    Button btnCalcular;
    PacienteController pacienteController;
    List<Paciente> listaPacienteAux;
    ListView listViewTabla;
    TablaAdapter tablaAdapter;
    ListView listViewHeaderTabla;
    HeaderAdapter headerAdapter;
    MuestraDTO muestraDTO;
    List<MuestraDTO> listaMuestraDTO;
    HeaderTablaDTO headerTablaDTO;
    List<HeaderTablaDTO> listaHeaderTabla;
    CelulaController celulaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pacienteController = new PacienteController(this);
        listaPacienteAux = new ArrayList<>();
        cargarComponente();
        agregarDatosSpinner();
        cargarHeaderTabla();

        celulaController = new CelulaController(this);


        //Inicio de la carga del cuerpo de la tabla
        //Cargando el listView
        muestraDTO = new MuestraDTO();
        muestraDTO.setTipo("ST");
        muestraDTO.setCantidad(14);
        muestraDTO.setPorcentaje(14.00);
        muestraDTO.setUnidadMedida(1600.20);
        MuestraDTO muestraDTO2 = new MuestraDTO();
        muestraDTO2.setTipo("SEG");
        muestraDTO2.setCantidad(70);
        muestraDTO2.setPorcentaje(70.00);
        muestraDTO2.setUnidadMedida(8001.00);
        //Lista para el adapter
        listaMuestraDTO = new ArrayList<>();
        listaMuestraDTO.add(muestraDTO);
        listaMuestraDTO.add(muestraDTO2);

        listViewTabla = findViewById(R.id.listViewTabla);
        tablaAdapter = new TablaAdapter(this, R.id.listViewTabla, listaMuestraDTO);
        tablaAdapter.notifyDataSetChanged();
        listViewTabla.setAdapter(tablaAdapter);
        //Fin de la carga del cuerpo de la tabla
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
    //Cargar el Header de la tabla
    private void cargarHeaderTabla(){
        headerTablaDTO = new HeaderTablaDTO();
        headerTablaDTO.setTipo(this.getString(R.string.tabla_tipo));
        headerTablaDTO.setCantidad(this.getString(R.string.tabla_cantidad));
        headerTablaDTO.setPorcentaje(this.getString(R.string.tabla_porcentaje));
        headerTablaDTO.setUnidadMedida(this.getString(R.string.tabla_medida));
        listaHeaderTabla = new ArrayList<>(1);
        listaHeaderTabla.add(headerTablaDTO);
        listViewHeaderTabla = findViewById(R.id.listViewHeaderTabla);
        headerAdapter = new HeaderAdapter(this, R.id.listViewHeaderTabla, listaHeaderTabla);
        headerAdapter.notifyDataSetChanged();
        listViewHeaderTabla.setAdapter(headerAdapter);
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
        switch (v.getId()){
            case R.id.btnCalcular:
                List<Celula> lista = celulaController.obtenerTodasCelulas();
                for(int i=0; i< lista.size(); i++){
                    Log.i("CELULA", lista.get(i).getId());
                }
                break;

            default:
                break;
        }
    }
}
