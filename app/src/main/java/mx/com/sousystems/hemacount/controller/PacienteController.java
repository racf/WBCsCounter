package mx.com.sousystems.hemacount.controller;

import android.content.Context;

import java.util.List;

import mx.com.sousystems.hemacount.domain.Paciente;
import mx.com.sousystems.hemacount.repository.PacienteRepository;
import mx.com.sousystems.hemacount.repository.PacienteRepositoryImpl;

public class PacienteController {
    PacienteRepository pacienteRepository;

    public PacienteController(Context context){
        pacienteRepository = new PacienteRepositoryImpl(context);
    }

    public boolean crearPaciente(Paciente paciente) {
        return pacienteRepository.crearPaciente(paciente);
    }

    public boolean actualizarPaciente(Paciente paciente) {
        return pacienteRepository.actualizarPaciente(paciente);
    }

    public boolean eliminarPaciente(Paciente paciente) {
        return pacienteRepository.eliminarPaciente(paciente);
    }

    public List<Paciente> obtenerTodosPacientes() {
        return pacienteRepository.obtenerTodosPacientes();
    }

    public Paciente obtenerPacientePorId(String id) {
        return pacienteRepository.obtenerPacientePorId(id);
    }
}
