package dao;

import capas.Database;
import capas.DatabaseManager;
import modelo.GestionAcademica;
import java.util.*;

public class GestionAcademicaDAO {

    private final Database db;

    public GestionAcademicaDAO() {
        this.db = DatabaseManager.getInstance().getDbGestionAcademica();
    }

    public GestionAcademica guardar(GestionAcademica g) {
        Object[] data = {g.getIdGestion() == 0 ? "" : g.getIdGestion(),
                g.getIdCurso(), g.getIdParalelo(), g.getIdMateria(),
                g.getIdProfesor(), g.getAnioLectivo()};
        data = db.store(data);
        g.setIdGestion((int) data[0]);
        return g;
    }

    public void eliminar(int id) {
        db.delete(String.valueOf(id));
    }

    public GestionAcademica buscar(int id) {
        Object[] data = db.find(String.valueOf(id));
        if (data == null) return null;
        return mapear(data);
    }

    public List<GestionAcademica> listar() {
        List<GestionAcademica> lista = new ArrayList<>();
        for (Object[] data : db.list().values()) {
            lista.add(mapear(data));
        }
        return lista;
    }

    private GestionAcademica mapear(Object[] d) {
        return new GestionAcademica(
                (int) d[0], (int) d[1], (int) d[2],
                (int) d[3], (int) d[4], (String) d[5]);
    }
}
