package dao;

import capas.Database;
import capas.DatabaseManager;
import modelo.Alumno;
import java.util.*;

public class AlumnoDAO {

    private final Database db;

    public AlumnoDAO() {
        this.db = DatabaseManager.getInstance().getDbAlumno();
    }

    public Alumno guardar(Alumno a) {
        Object[] data = {a.getIdAlumno() == 0 ? "" : a.getIdAlumno(),
                a.getNombres(), a.getApellidos(), a.getCedulaIdentidad(),
                a.getFechaNacimiento(), a.getDireccion(), a.getTelefonoContacto()};
        data = db.store(data);
        a.setIdAlumno((int) data[0]);
        return a;
    }

    public void eliminar(int id) {
        db.delete(String.valueOf(id));
    }

    public Alumno buscar(int id) {
        Object[] data = db.find(String.valueOf(id));
        if (data == null) return null;
        return mapear(data);
    }

    public List<Alumno> listar() {
        List<Alumno> lista = new ArrayList<>();
        for (Object[] data : db.list().values()) {
            lista.add(mapear(data));
        }
        return lista;
    }

    private Alumno mapear(Object[] d) {
        return new Alumno(
                (int) d[0], (String) d[1], (String) d[2], (String) d[3],
                d[4] != null ? (java.util.Date) d[4] : null,
                (String) d[5], (String) d[6]);
    }
}
