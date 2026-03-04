package dao;

import capas.Database;
import capas.DatabaseManager;
import modelo.Curso;
import java.util.*;

public class CursoDAO {

    private final Database db;

    public CursoDAO() {
        this.db = DatabaseManager.getInstance().getDbCurso();
    }

    public Curso guardar(Curso c) {
        Object[] data = {c.getIdCurso() == 0 ? "" : c.getIdCurso(),
                c.getNombreCurso(), c.getNivel()};
        data = db.store(data);
        c.setIdCurso((int) data[0]);
        return c;
    }

    public void eliminar(int id) {
        db.delete(String.valueOf(id));
    }

    public Curso buscar(int id) {
        Object[] data = db.find(String.valueOf(id));
        if (data == null) return null;
        return mapear(data);
    }

    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<>();
        for (Object[] data : db.list().values()) {
            lista.add(mapear(data));
        }
        return lista;
    }

    private Curso mapear(Object[] d) {
        return new Curso((int) d[0], (String) d[1], (String) d[2]);
    }
}
