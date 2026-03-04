package modelo;

public class Curso {
    private int idCurso;
    private String nombreCurso;
    private String nivel;

    public Curso() {}

    public Curso(int idCurso, String nombreCurso, String nivel) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.nivel = nivel;
    }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }
    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    @Override
    public String toString() { return nombreCurso + " (" + nivel + ")"; }
}
