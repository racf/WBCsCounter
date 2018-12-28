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
import android.view.Menu;
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
    List<Paciente> listaPacienteAux;
    private static final int OPCION_1 = 1;
    private static final int OPCION_2 = 2;
    //Recicler View
    List<Muestra> listaMuestra;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    int count = 0;

    CharSequence[] values = {" Excel "," PDF "};
    int itemValue = 0;
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

        cargarDatosReciclerView();
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
        this.listaMuestra  = getAllMuestra();

        //recyclerView.setHasFixedSize(true);
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
            case R.id.tvFechaIni:
                obtenerFecha(OPCION_1);
                break;
            case R.id.tvFechaFin:
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

    private List<Muestra> getAllMuestra(){
        List<Muestra> lista = new ArrayList<>();
        Muestra muestra = null;
        Paciente paciente = null;

        muestra = new Muestra();
        muestra.setId(1);
        muestra.setFecha("2018-12-27");
        paciente = new Paciente();
        paciente.setNombre("Fernando");
        paciente.setPrimerApellido("Ramirez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(2);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Carlos Alberto");
        paciente.setPrimerApellido("Alvarez");
        muestra.setPaciente(paciente);
        lista.add(muestra);


        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

        muestra = new Muestra();
        muestra.setId(3);
        muestra.setFecha("2018-12-28");
        paciente = new Paciente();
        paciente.setNombre("Diana Laura");
        paciente.setPrimerApellido("Ramírez");
        muestra.setPaciente(paciente);
        lista.add(muestra);

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
                        if(opcion == 1){
                            tvFechaIni.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }else{
                            tvFechaFin.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
        tvFechaIni.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
        tvFechaFin.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
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
