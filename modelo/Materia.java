package modelo;

public class Materia {
    private int idMateria;
    private String nombreMateria;
    private String descripcion;
    private int cargaHoraria;

    public Materia() {}

    public Materia(int idMateria, String nombreMateria, String descripcion, int cargaHoraria) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.descripcion = descripcion;
        this.cargaHoraria = cargaHoraria;
    }

    public int getIdMateria() { return idMateria; }
    public void setIdMateria(int idMateria) { this.idMateria = idMateria; }
    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    @Override
    public String toString() { return nombreMateria + " (" + cargaHoraria + "h)"; }
}
