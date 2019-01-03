package mx.com.sousystems.wbcscounter.activities;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.adapters.TablaHistorialReciclerView;
import mx.com.sousystems.wbcscounter.controller.MuestraController;
import mx.com.sousystems.wbcscounter.controller.PacienteController;
import mx.com.sousystems.wbcscounter.domain.Muestra;
import mx.com.sousystems.wbcscounter.domain.Paciente;

import static mx.com.sousystems.wbcscounter.adapters.TablaHistorialReciclerView.*;

public class HistorialActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    Spinner spinnerPaciente;
    TextView tvFechaIni;
    TextView tvFechaFin;
    ImageButton btnFechaIni;
    ImageButton btnFechaFin;
    Button btnBuscar;
    PacienteController pacienteController;
    MuestraController muestraController;
    List<Paciente> listaPacienteAux;
    private static final int OPCION_1 = 1;
    private static final int OPCION_2 = 2;
    Integer pacienteId = 1;
    //Recicler View
    List<Muestra> listaMuestra;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    CharSequence[] values = {" Excel "," PDF "};
    int itemValue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cargarComponente();
        pacienteController = new PacienteController(this);
        muestraController = new MuestraController(this);
        listaPacienteAux = new ArrayList<>();
        agregarDatosSpinner();
        obtenerFechaDefault();
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

    private void cargarDatosReciclerView(){
        //Recicler View
        this.listaMuestra  = buscar();

        //Para un liner layout
        layoutManager = new LinearLayoutManager(this);
        //para un GridLayout
        //layoutManager = new GridLayoutManager(this, 2);
        //Renderizara dependiendo el formato de la vista que se tenga.
        //layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);

        adapter = new TablaHistorialReciclerView(listaMuestra, R.layout.recicler_view_historial, new ViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(Muestra muestra, int position) {
                //Toast.makeText(HistorialActivity.this, muestra.getPaciente().getNombre()+" - "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void btnOnClick(View v, Muestra muestra, int position) {
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
                                alertaExportar();
                                return true;
                            case R.id.menu_eliminar:
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


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            pacienteId = 1;
        }else{
            pacienteId = listaPacienteAux.get(position).getId();
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
        List<Muestra> lista = muestraController.obtenerMuestras(pacienteId, fechaIni, fechaFin);
        for(int i=0; i < lista.size(); i++){
            Log.i("DATO--- ", ""+lista.get(i).getId()+" "+lista.get(i).getFecha()+" "+lista.get(i).getPaciente().getNombre());
        }

        return lista;
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
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String month = "";
        String day = "";
        if(String.valueOf(mMonth).length()==1){
            month = "0"+(mMonth + 1);
        }else{
            month = String.valueOf((mMonth + 1));
        }

        if(String.valueOf(mDay).length()==1){
            day = "0"+mDay;
        }else{
            day = String.valueOf(mDay);
        }

        tvFechaIni.setText(mYear + "-" + month + "-" + day);
        tvFechaFin.setText(mYear + "-" + month + "-" + day);
    }

    private void alertaExportar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.mensaje_alerta_titulo)
                .setIcon(R.mipmap.ic_logo_foreground)
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
                                    Log.i("ITEM EXCEL: ", ""+itemValue);
                                }else{//Generar el archcivo en PDF
                                    Log.i("ITEM PDF: ", ""+itemValue);
                                }
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
