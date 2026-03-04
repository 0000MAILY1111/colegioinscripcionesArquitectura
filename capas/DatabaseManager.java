package capas;

/**
 * PATRÓN SINGLETON
 * Garantiza una sola instancia de cada base de datos en toda la aplicación.
 * Así todos los DAOs acceden a los mismos datos sin duplicar instancias.
 */
public class DatabaseManager {

    private static DatabaseManager instancia;

    private final Database dbColegio;
    private final Database dbCurso;
    private final Database dbParalelo;
    private final Database dbProfesor;
    private final Database dbMateria;
    private final Database dbAlumno;
    private final Database dbGestionAcademica;
    private final Database dbInscripcion;

    private DatabaseManager() {
        dbColegio          = new Database();
        dbCurso            = new Database();
        dbParalelo         = new Database();
        dbProfesor         = new Database();
        dbMateria          = new Database();
        dbAlumno           = new Database();
        dbGestionAcademica = new Database();
        dbInscripcion      = new Database();
    }

    public static DatabaseManager getInstance() {
        if (instancia == null) {
            instancia = new DatabaseManager();
        }
        return instancia;
    }

    public Database getDbColegio()          { return dbColegio; }
    public Database getDbCurso()            { return dbCurso; }
    public Database getDbParalelo()         { return dbParalelo; }
    public Database getDbProfesor()         { return dbProfesor; }
    public Database getDbMateria()          { return dbMateria; }
    public Database getDbAlumno()           { return dbAlumno; }
    public Database getDbGestionAcademica() { return dbGestionAcademica; }
    public Database getDbInscripcion()      { return dbInscripcion; }
}
