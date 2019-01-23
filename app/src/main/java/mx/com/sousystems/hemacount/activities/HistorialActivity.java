package mx.com.sousystems.hemacount.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.com.sousystems.hemacount.R;
import mx.com.sousystems.hemacount.adapters.HeaderHistorialAdapter;
import mx.com.sousystems.hemacount.adapters.TablaHistorialReciclerView;
import mx.com.sousystems.hemacount.controller.MuestraController;
import mx.com.sousystems.hemacount.controller.MuestraDetalleController;
import mx.com.sousystems.hemacount.controller.PacienteController;
import mx.com.sousystems.hemacount.domain.Muestra;
import mx.com.sousystems.hemacount.domain.MuestraDetalle;
import mx.com.sousystems.hemacount.domain.Paciente;
import mx.com.sousystems.hemacount.dto.HeaderTablaDTO;
import mx.com.sousystems.hemacount.dto.ReporteDTO;
import mx.com.sousystems.hemacount.service.ExportarService;
import mx.com.sousystems.hemacount.service.ExportarServiceImpl;
import mx.com.sousystems.hemacount.util.Util;

import static mx.com.sousystems.hemacount.adapters.TablaHistorialReciclerView.*;

public class HistorialActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    Spinner spinnerPaciente;
    TextView tvFechaIni;
    TextView tvFechaFin;
    ImageButton btnFechaIni;
    ImageButton btnFechaFin;
    Button btnBuscar;
    PacienteController pacienteController;
    MuestraController muestraController;
    MuestraDetalleController muestraDetalleController;
    List<Paciente> listaPacienteAux;
    private static final int OPCION_1 = 1;
    private static final int OPCION_2 = 2;
    Integer pacienteId = 1;
    ListView listViewHeaderTabla;
    //Recicler View
    List<Muestra> listaMuestra;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    CharSequence[] values = {" Excel "," PDF "};
    int itemValue = 0;

    //Reporte
    ReporteDTO reporteDTO;
    ExportarService exportarService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        setToolbar();
        cargarComponente();
        pacienteController = new PacienteController(this);
        muestraController = new MuestraController(this);
        muestraDetalleController = new MuestraDetalleController(this);
        listaPacienteAux = new ArrayList<>();
        agregarDatosSpinner();
        obtenerFechaDefault();
        cargarHeaderTabla();
        //Reporte
        exportarService = new ExportarServiceImpl();
    }

    private void cargarComponente(){
        spinnerPaciente = findViewById(R.id.spPaciente);
        spinnerPaciente.setOnItemSelectedListener(this);
        tvFechaIni = findViewById(R.id.tvFechaIni);
        tvFechaIni.setOnClickListener(this);
        tvFechaFin = findViewById(R.id.tvFechaFin);
        tvFechaFin.setOnClickListener(this);
        btnFechaIni = findViewById(R.id.btnFechaIni);
        btnFechaIni.setOnClickListener(this);
        btnFechaFin = findViewById(R.id.btnFechaFin);
        btnFechaFin.setOnClickListener(this);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
    }
    //Cargar el Header de la tabla
    private void cargarHeaderTabla(){
        HeaderTablaDTO headerTablaDTO = new HeaderTablaDTO();
        headerTablaDTO.setFecha(this.getString(R.string.tabla_fecha));
        headerTablaDTO.setNombre(this.getString(R.string.tabla_nombre));
        List<HeaderTablaDTO> listaHeaderTabla = new ArrayList<>();
        listaHeaderTabla.add(headerTablaDTO);
        listViewHeaderTabla = findViewById(R.id.listViewHeaderHistorialTabla);
        HeaderHistorialAdapter headerHistorialAdapter = new HeaderHistorialAdapter(this, R.id.listViewHeaderTabla, listaHeaderTabla);
        headerHistorialAdapter.notifyDataSetChanged();
        listViewHeaderTabla.setAdapter(headerHistorialAdapter);
    }

    private void cargarDatosReciclerView(){
        if(Util.compareDate(String.valueOf(tvFechaIni.getText()), String.valueOf(tvFechaFin.getText()))) {
            //Recicler View
            this.listaMuestra = buscar();
            if (!this.listaMuestra.isEmpty()) {
                //Para un liner layout
                layoutManager = new LinearLayoutManager(this);
                adapter = new TablaHistorialReciclerView(listaMuestra, R.layout.recicler_view_historial, new ViewHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(Muestra muestra, int position) {
                        //
                    }

                    @Override
                    public void btnOnClick(View v, final Muestra muestra, int position) {
                        ImageButton btnMore = (ImageButton) v.findViewById(R.id.btnOpciones);
                        //creating a popup menu
                        PopupMenu popup = new PopupMenu(v.getContext(), btnMore);
                        //inflating menu from xml resource
                        popup.inflate(R.menu.menu_recicler_view);
                        //adding click listener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.menu_exportar:
                                        alertaExportar(muestra);
                                        return true;
                                    case R.id.menu_eliminar:
                                        eliminarRegistro(muestra);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        //displaying the popup
                        popup.show();
                    }
                });

                //Generar animaciones para el RecyclerView
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(itemDecoration);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            } else {
                recyclerView.setAdapter(null);
                Toast.makeText(this, this.getString(R.string.mensaje_sin_historial), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, this.getString(R.string.mensaje_fechas), Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarDatosSpinner(){
        List<Paciente> listaPaciente = pacienteController.obtenerTodosPacientes();
        List<String> listaNombre = new ArrayList<>();
        listaNombre.add(this.getString(R.string.mensaje_seleccione_paciente));
        listaPacienteAux.add(null);
        for (int i = 0; i < listaPaciente.size(); i++){
            if(!listaPaciente.get(i).getNombre().equals("default")){
                listaNombre.add(listaPaciente.get(i).getNombre());
                listaPacienteAux.add(listaPaciente.get(i));
            }

        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listaNombre);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaciente.setAdapter(dataAdapter);
    }

    private void setToolbar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_record);
    }

    @Override
    public boolean onSupportNavigateUp(){
        muestraController = null;
        pacienteController = null;
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            pacienteId = 1;
        }else{
            pacienteId = listaPacienteAux.get(position).getId();
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
            case R.id.tvFechaIni:
                obtenerFecha(OPCION_1);
                break;
            case R.id.tvFechaFin:
                obtenerFecha(OPCION_2);
                break;
            case R.id.btnBuscar:
                cargarDatosReciclerView();
                break;
            default:
                break;
        }
    }

    public List<Muestra> buscar(){
        String fechaIni = String.valueOf(tvFechaIni.getText());
        String fechaFin = String.valueOf(tvFechaFin.getText());
        return muestraController.obtenerMuestras(pacienteId, fechaIni, fechaFin);
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
                        String month = "";
                        String day = "";
                        if(String.valueOf(monthOfYear).length()==1){
                            month = "0"+(monthOfYear + 1);
                        }else{
                            month = String.valueOf((monthOfYear + 1));
                        }

                        if(String.valueOf(dayOfMonth).length()==1){
                            day = "0"+dayOfMonth;
                        }else{
                            day = String.valueOf(dayOfMonth);
                        }
                        if(opcion == 1){
                            tvFechaIni.setText(year + "-" + month + "-" + day);
                        }else{
                            tvFechaFin.setText(year + "-" + month + "-" + day);
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void obtenerFechaDefault(){
        tvFechaIni.setText(Util.fecha());
        tvFechaFin.setText(Util.fecha());
    }

    private void alertaExportar(final Muestra muestra){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.mensaje_alerta_titulo)
                .setIcon(R.mipmap.ic_launcher_foreground)
                .setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        itemValue = item;
                        Log.i("ITEM: ", ""+item);
                    }
                })
                .setNegativeButton(R.string.cancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //ACTIONS IF THE ANSWER IS NO
                            }
                        })
                .setPositiveButton(R.string.aceptar,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                //Generar el archivo----
                                if(itemValue == 0){//Gerar el archivo en excel
                                    exportarArchivo(muestra, itemValue);
                                }else{//Generar el archcivo en PDF
                                    exportarArchivo(muestra, itemValue);
                                }
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void eliminarRegistro(final Muestra muestra){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String fecha = muestra.getFecha();
        String nombre = muestra.getPaciente().getNombre()+" "+muestra.getPaciente().getPrimerApellido();
        builder.setTitle(R.string.eliminar)
                .setIcon(R.drawable.ic_delete_red80)
                .setMessage("¿Esta seguro de eliminar el registro?\n"+fecha+"\n"+nombre+"")
                .setNegativeButton(R.string.cancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //ACTIONS IF THE ANSWER IS NO
                            }
                        })
                .setPositiveButton(R.string.aceptar,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                               eliminar(muestra);
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void eliminar(Muestra muestra){
        if(muestraController.eliminarMuestraTransaccion(muestra)){
            cargarDatosReciclerView();
            Toast.makeText(this, this.getString(R.string.mensaje_registro_eliminado_exitoso), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, this.getString(R.string.mensaje_registro_eliminado_error), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param muestra el objeto de la muestra cargado con la información del paciente que se seleccione en el historial
     * @param itemValue valor de 0 cuando se exporta a excel y valor de 1 cuando se exporta a PDF* @param itemValue valor de 0 cuando se exporta a excel y valor de 1 cuando se exporta a PDF
     */
    private void exportarArchivo(Muestra muestra, int itemValue){
        if(Util.generarDirectorio()){
            List<MuestraDetalle> listaMuestraDetalle = muestraDetalleController.obtenerMuestraDetallePorMuestraId(muestra.getId());
            reporteDTO = Util.cargarReporteHistorico(this, muestra, listaMuestraDetalle);
            if(itemValue == 0){
                if(exportarService.reporteExcel(this, reporteDTO, 1) != null){
                    Toast.makeText(this, R.string.mensaje_exito_exportar, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, R.string.mensaje_error_exportar, Toast.LENGTH_SHORT).show();
                }
            }else{
                if(exportarService.reportePDF(this, reporteDTO, 1) != null){
                    Toast.makeText(this, R.string.mensaje_exito_exportar, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, R.string.mensaje_error_exportar, Toast.LENGTH_SHORT).show();
                }
            }

        }else{
            Toast.makeText(this, R.string.mensaje_error_exportar, Toast.LENGTH_SHORT).show();
        }
    }
}
