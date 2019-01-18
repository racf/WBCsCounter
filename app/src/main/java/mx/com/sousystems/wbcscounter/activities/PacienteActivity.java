package mx.com.sousystems.wbcscounter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.controller.PacienteController;
import mx.com.sousystems.wbcscounter.domain.Paciente;

public class PacienteActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnCrearPaciente;
    Button btnCancelarPaciente;
    EditText etNombre;
    EditText etPrimerApellido;
    EditText etSegundoApellido;
    RadioGroup radioGroupSexo;
    RadioButton radioSexo;
    EditText etTelefono;
    PacienteController pacienteController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cargarComponente();
        pacienteController = new PacienteController(this);
    }

    private void cargarComponente(){
        etNombre = findViewById(R.id.etNombre);
        etNombre.setHint(this.getString(R.string.nombre)+" "+this.getString(R.string.campo_obligatorio));
        etPrimerApellido = findViewById(R.id.etPrimerApellido);
        etPrimerApellido.setHint(this.getString(R.string.primer_apellido)+" "+this.getString(R.string.campo_obligatorio));
        etSegundoApellido = findViewById(R.id.etSegundoApellido);
        radioGroupSexo = findViewById(R.id.radioGroupSexo);
        etTelefono = findViewById(R.id.etTelefono);

        btnCrearPaciente = findViewById(R.id.btnCrearPaciente);
        btnCrearPaciente.setOnClickListener(this);
        btnCancelarPaciente = findViewById(R.id.btnCancelarPaciente);
        btnCancelarPaciente.setOnClickListener(this);
    }
    private void limpiarTexto(){
        etNombre.setText("");
        etPrimerApellido.setText("");
        etSegundoApellido.setText("");
        etTelefono.setText("");

    }

    @Override
    public boolean onSupportNavigateUp(){
        pacienteController = null;
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCrearPaciente:
                crearPaciente();
                break;
            case R.id.btnCancelarPaciente:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                pacienteController = null;
                this.finish();
                break;
            default:
                break;
        }
    }
    private void crearPaciente(){
        Paciente paciente = new Paciente();
        paciente.setNombre(String.valueOf(etNombre.getText()));
        paciente.setPrimerApellido(String.valueOf(etPrimerApellido.getText()));
        paciente.setSegundoApellido(String.valueOf(etSegundoApellido.getText()));
        //Obtenemos el Id del RadioButton seleccionado
        int selectedId = radioGroupSexo.getCheckedRadioButtonId();
        radioSexo = findViewById(selectedId);
        paciente.setSexo(String.valueOf(radioSexo.getText()));
        paciente.setTelefono(String.valueOf(etTelefono.getText()));
        if(!TextUtils.isEmpty(paciente.getNombre().trim()) && !TextUtils.isEmpty(paciente.getPrimerApellido().trim())){
            if(pacienteController.crearPaciente(paciente)){
                limpiarTexto();
                Toast.makeText(this, this.getString(R.string.mensaje_exito_guardado), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, this.getString(R.string.mensaje_error_guardar), Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, this.getString(R.string.mensaje_campo_obligatorio), Toast.LENGTH_SHORT).show();
        }
    }
}
