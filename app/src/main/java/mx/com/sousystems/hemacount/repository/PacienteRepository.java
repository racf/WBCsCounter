package mx.com.sousystems.hemacount.repository;

import java.util.List;

import mx.com.sousystems.hemacount.domain.Paciente;

public interface PacienteRepository {
    public boolean crearPaciente(Paciente paciente);
    public boolean actualizarPaciente(Paciente paciente);
    public boolean eliminarPaciente(Paciente paciente);
    public List<Paciente> obtenerTodosPacientes();
    public Paciente obtenerPacientePorId(String id);
}
