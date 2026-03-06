package controlador;

import dao.CursoDAO;
import dao.MateriaDAO;
import dao.ParaleloDAO;
import modelo.Curso;
import modelo.Materia;
import modelo.Paralelo;
import java.util.List;

public class CursoControlador {
    private final CursoDAO dao = new CursoDAO();

    public Curso guardar(String nombre, String nivel) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre del curso es obligatorio.");
        return dao.guardar(new Curso(0, nombre, nivel));
    }

    public Curso actualizar(int id, String nombre, String nivel) {
        return dao.guardar(new Curso(id, nombre, nivel));
    }

    public void eliminar(int id) { dao.eliminar(id); }
    public Curso buscar(int id) { return dao.buscar(id); }
    public List<Curso> listar() { return dao.listar(); }
}

class ParaleloControlador {
    private final ParaleloDAO dao = new ParaleloDAO();

    public Paralelo guardar(String letra, int cupoMaximo, int idCurso) {
        if (letra == null || letra.trim().isEmpty())
            throw new IllegalArgumentException("La letra del paralelo es obligatoria.");
        return dao.guardar(new Paralelo(0, letra, cupoMaximo, idCurso));
    }

    public Paralelo actualizar(int id, String letra, int cupoMaximo, int idCurso) {
        return dao.guardar(new Paralelo(id, letra, cupoMaximo, idCurso));
    }

    public void eliminar(int id) { dao.eliminar(id); }
    public Paralelo buscar(int id) { return dao.buscar(id); }
    public List<Paralelo> listar() { return dao.listar(); }
    public List<Paralelo> listarPorCurso(int idCurso) { return dao.listarPorCurso(idCurso); }
}

class MateriaControlador {
    private final MateriaDAO dao = new MateriaDAO();

    public Materia guardar(String nombre, String descripcion, int cargaHoraria) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre de la materia es obligatorio.");
        return dao.guardar(new Materia(0, nombre, descripcion, cargaHoraria));
    }

    public Materia actualizar(int id, String nombre, String descripcion, int cargaHoraria) {
        return dao.guardar(new Materia(id, nombre, descripcion, cargaHoraria));
    }

    public void eliminar(int id) { dao.eliminar(id); }
    public Materia buscar(int id) { return dao.buscar(id); }
    public List<Materia> listar() { return dao.listar(); }
}
