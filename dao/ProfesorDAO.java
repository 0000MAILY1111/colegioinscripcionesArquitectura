package dao;

import capas.Database;
import capas.DatabaseManager;
import modelo.Profesor;
import java.util.*;

public class ProfesorDAO {

    private final Database db;

    public ProfesorDAO() {
        this.db = DatabaseManager.getInstance().getDbProfesor();
    }

    public Profesor guardar(Profesor p) {
        Object[] data = {p.getIdProfesor() == 0 ? "" : p.getIdProfesor(),
                p.getNombres(), p.getApellidos(), p.getCedulaIdentidad(),
                p.getEspecialidad(), p.getCorreo(), p.getTelefono()};
        data = db.store(data);
        p.setIdProfesor((int) data[0]);
        return p;
    }

    public void eliminar(int id) {
        db.delete(String.valueOf(id));
    }

    public Profesor buscar(int id) {
        Object[] data = db.find(String.valueOf(id));
        if (data == null) return null;
        return mapear(data);
    }

    public List<Profesor> listar() {
        List<Profesor> lista = new ArrayList<>();
        for (Object[] data : db.list().values()) {
            lista.add(mapear(data));
        }
        return lista;
    }

    private Profesor mapear(Object[] d) {
        return new Profesor(
                (int) d[0], (String) d[1], (String) d[2],
                (String) d[3], (String) d[4], (String) d[5], (String) d[6]);
    }
}
