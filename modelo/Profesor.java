package modelo;

public class Profesor {
    private int idProfesor;
    private String nombres;
    private String apellidos;
    private String cedulaIdentidad;
    private String especialidad;
    private String correo;
    private String telefono;

    public Profesor() {}

    public Profesor(int idProfesor, String nombres, String apellidos,
                    String cedulaIdentidad, String especialidad,
                    String correo, String telefono) {
        this.idProfesor = idProfesor;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedulaIdentidad = cedulaIdentidad;
        this.especialidad = especialidad;
        this.correo = correo;
        this.telefono = telefono;
    }

    public int getIdProfesor() { return idProfesor; }
    public void setIdProfesor(int idProfesor) { this.idProfesor = idProfesor; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getCedulaIdentidad() { return cedulaIdentidad; }
    public void setCedulaIdentidad(String cedulaIdentidad) { this.cedulaIdentidad = cedulaIdentidad; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() { return nombres + " " + apellidos; }
}
