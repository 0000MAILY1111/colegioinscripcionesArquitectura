

import dao.AlumnoDAO;
import modelo.Alumno;
import java.util.Date;
import java.util.List;

public class AlumnoControlador {

    private final AlumnoDAO dao = new AlumnoDAO();

    public Alumno registrar(String nombres, String apellidos, String cedula,
            Date fechaNacimiento, String direccion, String telefono) {
        if (nombres == null || nombres.trim().isEmpty())
            throw new IllegalArgumentException("El nombre es obligatorio.");
        if (cedula == null || cedula.trim().isEmpty())
            throw new IllegalArgumentException("La cédula es obligatoria.");
        Alumno a = new Alumno(0, nombres, apellidos, cedula, fechaNacimiento, direccion, telefono);
        return dao.guardar(a);
    }

    public Alumno actualizar(int id, String nombres, String apellidos, String cedula,
            Date fechaNacimiento, String direccion, String telefono) {
        Alumno a = new Alumno(id, nombres, apellidos, cedula, fechaNacimiento, direccion, telefono);
        return dao.guardar(a);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }

    public Alumno buscar(int id) {
        return dao.buscar(id);
    }

    public List<Alumno> listar() {
        return dao.listar();
    }
}
