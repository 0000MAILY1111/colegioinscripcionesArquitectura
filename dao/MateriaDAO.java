package dao;

import capas.Database;
import capas.DatabaseManager;
import modelo.Materia;
import java.util.*;

public class MateriaDAO {

    private final Database db;

    public MateriaDAO() {
        this.db = DatabaseManager.getInstance().getDbMateria();
    }

    public Materia guardar(Materia m) {
        Object[] data = {m.getIdMateria() == 0 ? "" : m.getIdMateria(),
                m.getNombreMateria(), m.getDescripcion(), m.getCargaHoraria()};
        data = db.store(data);
        m.setIdMateria((int) data[0]);
        return m;
    }

    public void eliminar(int id) {
        db.delete(String.valueOf(id));
    }

    public Materia buscar(int id) {
        Object[] data = db.find(String.valueOf(id));
        if (data == null) return null;
        return mapear(data);
    }

    public List<Materia> listar() {
        List<Materia> lista = new ArrayList<>();
        for (Object[] data : db.list().values()) {
            lista.add(mapear(data));
        }
        return lista;
    }

    private Materia mapear(Object[] d) {
        return new Materia((int) d[0], (String) d[1], (String) d[2], (int) d[3]);
    }
}
