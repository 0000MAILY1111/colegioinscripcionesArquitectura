package servicio;

import dao.ProfesorDAO;
import modelo.Profesor;
import java.util.List;

public class ProfesorServicio {

    private final ProfesorDAO dao = new ProfesorDAO();

    public Profesor registrar(String nombres, String apellidos, String cedula,
                               String especialidad, String correo, String telefono) {
        if (nombres == null || nombres.trim().isEmpty())
            throw new IllegalArgumentException("El nombre es obligatorio.");
        if (cedula == null || cedula.trim().isEmpty())
            throw new IllegalArgumentException("La cédula es obligatoria.");
        Profesor p = new Profesor(0, nombres, apellidos, cedula, especialidad, correo, telefono);
        return dao.guardar(p);
    }

    public Profesor actualizar(int id, String nombres, String apellidos, String cedula,
                                String especialidad, String correo, String telefono) {
        Profesor p = new Profesor(id, nombres, apellidos, cedula, especialidad, correo, telefono);
        return dao.guardar(p);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }

    public Profesor buscar(int id) {
        return dao.buscar(id);
    }

    public List<Profesor> listar() {
        return dao.listar();
    }
}
