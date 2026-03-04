package servicio;

import dao.GestionAcademicaDAO;
import dao.InscripcionDAO;
import modelo.GestionAcademica;
import modelo.Inscripcion;
import java.util.Date;
import java.util.List;

public class InscripcionServicio {

    private final InscripcionDAO dao = new InscripcionDAO();

    public Inscripcion inscribir(int idAlumno, int idGestion) {
        if (idAlumno <= 0) throw new IllegalArgumentException("Alumno inválido.");
        if (idGestion <= 0) throw new IllegalArgumentException("Gestión inválida.");
        Inscripcion i = new Inscripcion(0, idAlumno, idGestion, new Date(), "ACTIVO");
        return dao.guardar(i);
    }

    public void cancelar(int idInscripcion) {
        Inscripcion i = dao.buscar(idInscripcion);
        if (i != null) {
            i.setEstado("CANCELADO");
            dao.guardar(i);
        }
    }

    public void eliminar(int id) { dao.eliminar(id); }
    public Inscripcion buscar(int id) { return dao.buscar(id); }
    public List<Inscripcion> listar() { return dao.listar(); }
}

class GestionAcademicaServicio {

    private final GestionAcademicaDAO dao = new GestionAcademicaDAO();

    public GestionAcademica asignar(int idCurso, int idParalelo,
                                    int idMateria, int idProfesor, String anioLectivo) {
        if (anioLectivo == null || anioLectivo.trim().isEmpty())
            throw new IllegalArgumentException("El año lectivo es obligatorio.");
        GestionAcademica g = new GestionAcademica(0, idCurso, idParalelo, idMateria, idProfesor, anioLectivo);
        return dao.guardar(g);
    }

    public void eliminar(int id) { dao.eliminar(id); }
    public GestionAcademica buscar(int id) { return dao.buscar(id); }
    public List<GestionAcademica> listar() { return dao.listar(); }
}
