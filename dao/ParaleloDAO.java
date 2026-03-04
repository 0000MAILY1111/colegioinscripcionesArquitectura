package dao;

import capas.Database;
import capas.DatabaseManager;
import modelo.Paralelo;
import java.util.*;

public class ParaleloDAO {

    private final Database db;

    public ParaleloDAO() {
        this.db = DatabaseManager.getInstance().getDbParalelo();
    }

    public Paralelo guardar(Paralelo p) {
        Object[] data = {p.getIdParalelo() == 0 ? "" : p.getIdParalelo(),
                p.getLetraParalelo(), p.getCupoMaximo(), p.getIdCurso()};
        data = db.store(data);
        p.setIdParalelo((int) data[0]);
        return p;
    }

    public void eliminar(int id) { db.delete(String.valueOf(id)); }

    public Paralelo buscar(int id) {
        Object[] data = db.find(String.valueOf(id));
        if (data == null) return null;
        return mapear(data);
    }

    public List<Paralelo> listar() {
        List<Paralelo> lista = new ArrayList<>();
        for (Object[] data : db.list().values()) {
            lista.add(mapear(data));
        }
        return lista;
    }

    public List<Paralelo> listarPorCurso(int idCurso) {
        List<Paralelo> lista = new ArrayList<>();
        for (Object[] data : db.list().values()) {
            if ((int) data[3] == idCurso) lista.add(mapear(data));
        }
        return lista;
    }

    private Paralelo mapear(Object[] d) {
        return new Paralelo((int) d[0], (String) d[1], (int) d[2], (int) d[3]);
    }
}
