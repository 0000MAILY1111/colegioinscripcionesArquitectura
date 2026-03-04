package modelo;

import java.util.Date;

public class Alumno {
    private int idAlumno;
    private String nombres;
    private String apellidos;
    private String cedulaIdentidad;
    private Date fechaNacimiento;
    private String direccion;
    private String telefonoContacto;

    public Alumno() {}

    public Alumno(int idAlumno, String nombres, String apellidos,
                  String cedulaIdentidad, Date fechaNacimiento,
                  String direccion, String telefonoContacto) {
        this.idAlumno = idAlumno;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedulaIdentidad = cedulaIdentidad;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefonoContacto = telefonoContacto;
    }

    public int getIdAlumno() { return idAlumno; }
    public void setIdAlumno(int idAlumno) { this.idAlumno = idAlumno; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getCedulaIdentidad() { return cedulaIdentidad; }
    public void setCedulaIdentidad(String cedulaIdentidad) { this.cedulaIdentidad = cedulaIdentidad; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    @Override
    public String toString() { return nombres + " " + apellidos; }
}
