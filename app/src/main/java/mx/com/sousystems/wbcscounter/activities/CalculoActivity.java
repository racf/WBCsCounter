package mx.com.sousystems.wbcscounter.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.adapters.HeaderAdapter;
import mx.com.sousystems.wbcscounter.adapters.TablaAdapter;
import mx.com.sousystems.wbcscounter.controller.CelulaController;
import mx.com.sousystems.wbcscounter.controller.PacienteController;
import mx.com.sousystems.wbcscounter.domain.MuestraDetalle;
import mx.com.sousystems.wbcscounter.domain.Paciente;
import mx.com.sousystems.wbcscounter.dto.HeaderTablaDTO;
import mx.com.sousystems.wbcscounter.dto.MuestraDTO;
import mx.com.sousystems.wbcscounter.util.Util;

public class CalculoActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, View.OnClickListener{
    Spinner spinnerPaciente;
    Button btnCalcular;
    Button btnGuardar;
    Button btnExportar;
    EditText etCantidad;
    TextView tvTotalConNrbc;
    TextView tvTotalSinNRbc;
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
    Integer cantidadtTotalCelula;
    double cantidadWbc;
    CharSequence[] values = {" Excel "," PDF "};
    AlertDialog alertDialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pacienteController = new PacienteController(this);
        listaPacienteAux = new ArrayList<>();
        cantidadtTotalCelula = 100;
        cantidadWbc = 0;
        cargarComponente();
        agregarDatosSpinner();
        cargarHeaderTabla();

        celulaController = new CelulaController(this);


        //Inicio de la carga del cuerpo de la tabla
        //Cargando el listView
        listaMuestraDTO = cargarDatosTabla(cantidadWbc);
        listViewTabla = findViewById(R.id.listViewTabla);
        tablaAdapter = new TablaAdapter(this, R.id.listViewTabla, listaMuestraDTO);
        tablaAdapter.notifyDataSetChanged();
        listViewTabla.setAdapter(tablaAdapter);
        //Fin de la carga del cuerpo de la tabla
    }

    private void cargarComponente(){
        spinnerPaciente = findViewById(R.id.spinnerPaciente);
        spinnerPaciente.setOnItemSelectedListener(this);
        etCantidad = findViewById(R.id.etCantidad);
        etCantidad.setText(String.valueOf(cantidadWbc));
        tvTotalConNrbc = findViewById(R.id.tvTotalConNrbc);
        tvTotalConNrbc.setText(String.valueOf(0));
        tvTotalSinNRbc = findViewById(R.id.tvTotalSinNRbc);
        tvTotalSinNRbc.setText(String.valueOf(0));
        btnCalcular = findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(this);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);
        btnExportar = findViewById(R.id.btnExportar);
        btnExportar.setOnClickListener(this);
    }

    private List<MuestraDTO> cargarDatosTabla(double cantidadWbc){
        List<MuestraDTO> listaMuestrasDTO = new ArrayList<>();
        List<MuestraDetalle> listaMuestraDetalle  = new ArrayList<>();
        MuestraDetalle muestraDetalle = new MuestraDetalle();
        muestraDetalle.setCelulaId("BASO");
        muestraDetalle.setCantidad(0);
        listaMuestraDetalle.add(muestraDetalle);

        muestraDetalle = new MuestraDetalle();
        muestraDetalle.setCelulaId("EOS");
        muestraDetalle.setCantidad(1);
        listaMuestraDetalle.add(muestraDetalle);

        muestraDetalle = new MuestraDetalle();
        muestraDetalle.setCelulaId("MONO");
        muestraDetalle.setCantidad(7);
        listaMuestraDetalle.add(muestraDetalle);


        muestraDetalle = new MuestraDetalle();
        muestraDetalle.setCelulaId("ST");
        muestraDetalle.setCantidad(14);
        listaMuestraDetalle.add(muestraDetalle);

        muestraDetalle = new MuestraDetalle();
        muestraDetalle.setCelulaId("SEG");
        muestraDetalle.setCantidad(70);
        listaMuestraDetalle.add(muestraDetalle);

        muestraDetalle = new MuestraDetalle();
        muestraDetalle.setCelulaId("LYM");
        muestraDetalle.setCantidad(7);
        listaMuestraDetalle.add(muestraDetalle);

        muestraDetalle = new MuestraDetalle();
        muestraDetalle.setCelulaId("NRBC");
        muestraDetalle.setCantidad(1);
        listaMuestraDetalle.add(muestraDetalle);

        muestraDetalle = new MuestraDetalle();
        muestraDetalle.setCelulaId("OTHER1");
        muestraDetalle.setCantidad(0);
        listaMuestraDetalle.add(muestraDetalle);

        muestraDetalle = new MuestraDetalle();
        muestraDetalle.setCelulaId("OTHER1");
        muestraDetalle.setCantidad(0);
        listaMuestraDetalle.add(muestraDetalle);

        double muestraTotalConNrbc = 0;
        double muestraTotalSinNrbc = 0;

        for(int i = 0; i <listaMuestraDetalle.size(); i++){
            muestraDTO = new MuestraDTO();
            muestraDTO.setTipo(listaMuestraDetalle.get(i).getCelulaId());
            muestraDTO.setCantidad(listaMuestraDetalle.get(i).getCantidad());
            if(muestraDTO.getCantidad() != 0){
                double porcentaje = Util.calcularPorcentaje(cantidadtTotalCelula, muestraDTO.getCantidad());
                muestraDTO.setPorcentaje(Util.numeroDosDecimales(porcentaje));
                double resultado = calcularResultados(cantidadWbc, porcentaje, muestraDTO);
                muestraTotalConNrbc += resultado;
                if(muestraDTO.getTipo().equals("NRBC")){
                    muestraTotalSinNrbc = muestraTotalConNrbc - resultado;
                }else{
                    muestraTotalSinNrbc = muestraTotalConNrbc;
                }
            }else{
                muestraDTO.setPorcentaje(0.0);
                muestraDTO.setUnidadMedida(0.0);
            }
            listaMuestrasDTO.add(muestraDTO);
        }
        tvTotalConNrbc.setText(String.valueOf(muestraTotalConNrbc)+""+this.getString(R.string.tabla_medida));
        tvTotalSinNRbc.setText(String.valueOf(muestraTotalSinNrbc)+""+this.getString(R.string.tabla_medida));
        return listaMuestrasDTO;
    }

    public double calcularResultados(double cantidadTotalWBC, double porcentaje, MuestraDTO muestraDTO){
        if(!TextUtils.isEmpty(etCantidad.getText().toString().trim()) && cantidadTotalWBC > 0){
            double unidaMedida = Util.calcularUnidadMedida(cantidadTotalWBC, porcentaje);
            muestraDTO.setUnidadMedida(Util.numeroDosDecimales(unidaMedida));
            return muestraDTO.getUnidadMedida();
        }
        return 0;
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
        headerTablaDTO.setCantidad(this.getString(R.string.tabla_cantidad)+" "+cantidadtTotalCelula);
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
        //
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
                if(!TextUtils.isEmpty(etCantidad.getText().toString().trim())){
                    cantidadWbc = Double.valueOf(etCantidad.getText().toString());
                    listaMuestraDTO = cargarDatosTabla(cantidadWbc);
                    tablaAdapter = new TablaAdapter(this, R.id.listViewTabla, listaMuestraDTO);
                    tablaAdapter.notifyDataSetChanged();
                    listViewTabla.setAdapter(tablaAdapter);
                }else{
                    Toast.makeText(this, R.string.mensaje_campo_obligatorio_calculo, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnGuardar:
                break;
            case R.id.btnExportar:
                alertaExportar();
                //onCreateDialog();
                break;
            default:
                break;
        }
    }

    private void alertaExportar(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.mensaje_alerta_titulo)
                .setIcon(R.drawable.button_background_exportar)
                .setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                    }
                })
                .setNegativeButton(R.string.cancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //ACTIONS IF THE ANSWER IS NO
                                //appPreferences.saveOnPreferenceBoolean(getApplicationContext(), ConstantSdk.PREFERENCE_NAME_GENERAL, ConstantSdk.PREFERENCE_USER_IS_DRIVING, false);
                            }
                        })
                .setPositiveButton(R.string.aceptar,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){


                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
