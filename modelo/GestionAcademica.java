package modelo;

public class GestionAcademica {
    private int idGestion;
    private int idCurso;
    private int idParalelo;
    private int idMateria;
    private int idProfesor;
    private String anioLectivo;

    public GestionAcademica() {}

    public GestionAcademica(int idGestion, int idCurso, int idParalelo,
                             int idMateria, int idProfesor, String anioLectivo) {
        this.idGestion = idGestion;
        this.idCurso = idCurso;
        this.idParalelo = idParalelo;
        this.idMateria = idMateria;
        this.idProfesor = idProfesor;
        this.anioLectivo = anioLectivo;
    }

    public int getIdGestion() { return idGestion; }
    public void setIdGestion(int idGestion) { this.idGestion = idGestion; }
    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }
    public int getIdParalelo() { return idParalelo; }
    public void setIdParalelo(int idParalelo) { this.idParalelo = idParalelo; }
    public int getIdMateria() { return idMateria; }
    public void setIdMateria(int idMateria) { this.idMateria = idMateria; }
    public int getIdProfesor() { return idProfesor; }
    public void setIdProfesor(int idProfesor) { this.idProfesor = idProfesor; }
    public String getAnioLectivo() { return anioLectivo; }
    public void setAnioLectivo(String anioLectivo) { this.anioLectivo = anioLectivo; }

    @Override
    public String toString() {
        return "Gestión " + anioLectivo + " | Curso:" + idCurso
                + " Paralelo:" + idParalelo + " Materia:" + idMateria;
    }
}
