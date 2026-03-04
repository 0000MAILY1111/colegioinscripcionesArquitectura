package modelo;

public class Paralelo {
    private int idParalelo;
    private String letraParalelo;
    private int cupoMaximo;
    private int idCurso;

    public Paralelo() {}

    public Paralelo(int idParalelo, String letraParalelo, int cupoMaximo, int idCurso) {
        this.idParalelo = idParalelo;
        this.letraParalelo = letraParalelo;
        this.cupoMaximo = cupoMaximo;
        this.idCurso = idCurso;
    }

    public int getIdParalelo() { return idParalelo; }
    public void setIdParalelo(int idParalelo) { this.idParalelo = idParalelo; }
    public String getLetraParalelo() { return letraParalelo; }
    public void setLetraParalelo(String letraParalelo) { this.letraParalelo = letraParalelo; }
    public int getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }
    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    @Override
    public String toString() { return "Paralelo " + letraParalelo + " (Cupo: " + cupoMaximo + ")"; }
}
