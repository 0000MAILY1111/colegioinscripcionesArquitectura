package modelo;

import java.util.Date;

public class Inscripcion {
    private int idInscripcion;
    private int idAlumno;
    private int idGestion;
    private Date fechaInscripcion;
    private String estado;

    public Inscripcion() {}

    public Inscripcion(int idInscripcion, int idAlumno, int idGestion,
                       Date fechaInscripcion, String estado) {
        this.idInscripcion = idInscripcion;
        this.idAlumno = idAlumno;
        this.idGestion = idGestion;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }

    public int getIdInscripcion() { return idInscripcion; }
    public void setIdInscripcion(int idInscripcion) { this.idInscripcion = idInscripcion; }
    public int getIdAlumno() { return idAlumno; }
    public void setIdAlumno(int idAlumno) { this.idAlumno = idAlumno; }
    public int getIdGestion() { return idGestion; }
    public void setIdGestion(int idGestion) { this.idGestion = idGestion; }
    public Date getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(Date fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Inscripción #" + idInscripcion + " | Alumno:" + idAlumno
                + " Gestión:" + idGestion + " Estado:" + estado;
    }
}
