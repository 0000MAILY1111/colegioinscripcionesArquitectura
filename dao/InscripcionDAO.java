package dao;

import capas.Database;
import capas.DatabaseManager;
import modelo.Inscripcion;
import java.util.*;

public class InscripcionDAO {

    private final Database db;

    public InscripcionDAO() {
        this.db = DatabaseManager.getInstance().getDbInscripcion();
    }

    public Inscripcion guardar(Inscripcion i) {
        Object[] data = {i.getIdInscripcion() == 0 ? "" : i.getIdInscripcion(),
                i.getIdAlumno(), i.getIdGestion(),
                i.getFechaInscripcion(), i.getEstado()};
        data = db.store(data);
        i.setIdInscripcion((int) data[0]);
        return i;
    }

    public void eliminar(int id) {
        db.delete(String.valueOf(id));
    }

    public Inscripcion buscar(int id) {
        Object[] data = db.find(String.valueOf(id));
        if (data == null) return null;
        return mapear(data);
    }

    public List<Inscripcion> listar() {
        List<Inscripcion> lista = new ArrayList<>();
        for (Object[] data : db.list().values()) {
            lista.add(mapear(data));
        }
        return lista;
    }

    private Inscripcion mapear(Object[] d) {
        return new Inscripcion(
                (int) d[0], (int) d[1], (int) d[2],
                d[3] != null ? (java.util.Date) d[3] : null, (String) d[4]);
    }
}
