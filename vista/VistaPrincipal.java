package vista;

import dao.*;
import modelo.*;
import servicio.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * VISTA PRINCIPAL - Colegio Nuevo Amanecer
 * Arquitectura de capas: Vista -> Servicio -> DAO -> Database
 * Patrón Singleton: DatabaseManager garantiza una sola instancia de cada BD.
 */
public class VistaPrincipal extends JFrame {

    // Servicios (capa de negocio)
    private final ProfesorServicio svcProfesor     = new ProfesorServicio();
    private final AlumnoServicio   svcAlumno       = new AlumnoServicio();
    private final CursoServicio    svcCurso        = new CursoServicio();
    private final InscripcionServicio svcInscripcion = new InscripcionServicio();

    // DAOs directos para combos (lectura simple)
    private final CursoDAO    daoCurso    = new CursoDAO();
    private final ParaleloDAO daoParalelo = new ParaleloDAO();
    private final MateriaDAO  daoMateria  = new MateriaDAO();
    private final AlumnoDAO   daoAlumno   = new AlumnoDAO();
    private final GestionAcademicaDAO daoGestion = new GestionAcademicaDAO();

    public VistaPrincipal() {
        setTitle("Colegio Nuevo Amanecer - Sistema de Gestión");
        setSize(900, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("👨‍🏫 Profesores",   panelProfesor());
        tabs.addTab("🎓 Alumnos",       panelAlumno());
        tabs.addTab("📚 Cursos",        panelCurso());
        tabs.addTab("🔢 Paralelos",     panelParalelo());
        tabs.addTab("📖 Materias",      panelMateria());
        tabs.addTab("🗂 Gestión Acad.", panelGestion());
        tabs.addTab("📋 Inscripciones", panelInscripcion());

        add(tabs);
        setVisible(true);
    }

    // ─────────────────────────────────────────────────────────────────
    //  PANEL PROFESOR
    // ─────────────────────────────────────────────────────────────────
    private JPanel panelProfesor() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulario
        JTextField txtNombres      = new JTextField(15);
        JTextField txtApellidos    = new JTextField(15);
        JTextField txtCedula       = new JTextField(12);
        JTextField txtEspecialidad = new JTextField(15);
        JTextField txtCorreo       = new JTextField(15);
        JTextField txtTelefono     = new JTextField(10);

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.add(new JLabel("Nombres:")); form.add(txtNombres);
        form.add(new JLabel("Apellidos:")); form.add(txtApellidos);
        form.add(new JLabel("Cédula:")); form.add(txtCedula);
        form.add(new JLabel("Especialidad:")); form.add(txtEspecialidad);
        form.add(new JLabel("Correo:")); form.add(txtCorreo);
        form.add(new JLabel("Teléfono:")); form.add(txtTelefono);

        // Tabla
        String[] cols = {"ID", "Nombres", "Apellidos", "Cédula", "Especialidad", "Correo", "Teléfono"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        // Botones
        JButton btnGuardar  = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar  = new JButton("Limpiar");

        JPanel botones = new JPanel();
        botones.add(btnGuardar); botones.add(btnEliminar); botones.add(btnLimpiar);

        btnGuardar.addActionListener(e -> {
            try {
                Profesor p = svcProfesor.registrar(
                        txtNombres.getText(), txtApellidos.getText(), txtCedula.getText(),
                        txtEspecialidad.getText(), txtCorreo.getText(), txtTelefono.getText());
                recargarTablaProfesor(modelo);
                limpiarCampos(txtNombres, txtApellidos, txtCedula, txtEspecialidad, txtCorreo, txtTelefono);
                JOptionPane.showMessageDialog(panel, "Profesor registrado con ID: " + p.getIdProfesor());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(panel, "Seleccione un registro."); return; }
            int id = (int) modelo.getValueAt(fila, 0);
            svcProfesor.eliminar(id);
            recargarTablaProfesor(modelo);
        });

        btnLimpiar.addActionListener(e ->
                limpiarCampos(txtNombres, txtApellidos, txtCedula, txtEspecialidad, txtCorreo, txtTelefono));

        // Seleccionar fila → llenar formulario
        tabla.getSelectionModel().addListSelectionListener(ev -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) return;
            txtNombres.setText((String) modelo.getValueAt(fila, 1));
            txtApellidos.setText((String) modelo.getValueAt(fila, 2));
            txtCedula.setText((String) modelo.getValueAt(fila, 3));
            txtEspecialidad.setText((String) modelo.getValueAt(fila, 4));
            txtCorreo.setText((String) modelo.getValueAt(fila, 5));
            txtTelefono.setText((String) modelo.getValueAt(fila, 6));
        });

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        panel.add(scroll, BorderLayout.SOUTH);
        scroll.setPreferredSize(new Dimension(0, 280));

        recargarTablaProfesor(modelo);
        return panel;
    }

    private void recargarTablaProfesor(DefaultTableModel m) {
        m.setRowCount(0);
        for (Profesor p : svcProfesor.listar())
            m.addRow(new Object[]{p.getIdProfesor(), p.getNombres(), p.getApellidos(),
                    p.getCedulaIdentidad(), p.getEspecialidad(), p.getCorreo(), p.getTelefono()});
    }

    // ─────────────────────────────────────────────────────────────────
    //  PANEL ALUMNO
    // ─────────────────────────────────────────────────────────────────
    private JPanel panelAlumno() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtNombres   = new JTextField(15);
        JTextField txtApellidos = new JTextField(15);
        JTextField txtCedula    = new JTextField(12);
        JTextField txtFecha     = new JTextField(10); // dd/MM/yyyy
        JTextField txtDireccion = new JTextField(20);
        JTextField txtTelefono  = new JTextField(10);

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.add(new JLabel("Nombres:")); form.add(txtNombres);
        form.add(new JLabel("Apellidos:")); form.add(txtApellidos);
        form.add(new JLabel("Cédula:")); form.add(txtCedula);
        form.add(new JLabel("Fecha Nac. (dd/MM/yyyy):")); form.add(txtFecha);
        form.add(new JLabel("Dirección:")); form.add(txtDireccion);
        form.add(new JLabel("Teléfono:")); form.add(txtTelefono);

        String[] cols = {"ID", "Nombres", "Apellidos", "Cédula", "Fecha Nac.", "Dirección", "Teléfono"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);

        JButton btnGuardar  = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar  = new JButton("Limpiar");
        JPanel botones = new JPanel();
        botones.add(btnGuardar); botones.add(btnEliminar); botones.add(btnLimpiar);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        btnGuardar.addActionListener(e -> {
            try {
                Date fecha = null;
                if (!txtFecha.getText().trim().isEmpty())
                    fecha = sdf.parse(txtFecha.getText().trim());
                Alumno a = svcAlumno.registrar(txtNombres.getText(), txtApellidos.getText(),
                        txtCedula.getText(), fecha, txtDireccion.getText(), txtTelefono.getText());
                recargarTablaAlumno(modelo, sdf);
                limpiarCampos(txtNombres, txtApellidos, txtCedula, txtFecha, txtDireccion, txtTelefono);
                JOptionPane.showMessageDialog(panel, "Alumno registrado con ID: " + a.getIdAlumno());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(panel, "Seleccione un registro."); return; }
            svcAlumno.eliminar((int) modelo.getValueAt(fila, 0));
            recargarTablaAlumno(modelo, sdf);
        });

        btnLimpiar.addActionListener(e ->
                limpiarCampos(txtNombres, txtApellidos, txtCedula, txtFecha, txtDireccion, txtTelefono));

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 280));
        panel.add(scroll, BorderLayout.SOUTH);

        recargarTablaAlumno(modelo, sdf);
        return panel;
    }

    private void recargarTablaAlumno(DefaultTableModel m, SimpleDateFormat sdf) {
        m.setRowCount(0);
        for (Alumno a : svcAlumno.listar())
            m.addRow(new Object[]{a.getIdAlumno(), a.getNombres(), a.getApellidos(),
                    a.getCedulaIdentidad(),
                    a.getFechaNacimiento() != null ? sdf.format(a.getFechaNacimiento()) : "",
                    a.getDireccion(), a.getTelefonoContacto()});
    }

    // ─────────────────────────────────────────────────────────────────
    //  PANEL CURSO
    // ─────────────────────────────────────────────────────────────────
    private JPanel panelCurso() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtNombre = new JTextField(15);
        JTextField txtNivel  = new JTextField(10);

        JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));
        form.add(new JLabel("Nombre del Curso:")); form.add(txtNombre);
        form.add(new JLabel("Nivel:")); form.add(txtNivel);

        String[] cols = {"ID", "Nombre", "Nivel"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);

        JButton btnGuardar  = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar  = new JButton("Limpiar");
        JPanel botones = new JPanel();
        botones.add(btnGuardar); botones.add(btnEliminar); botones.add(btnLimpiar);

        btnGuardar.addActionListener(e -> {
            try {
                Curso c = svcCurso.guardar(txtNombre.getText(), txtNivel.getText());
                recargarTablaCurso(modelo);
                txtNombre.setText(""); txtNivel.setText("");
                JOptionPane.showMessageDialog(panel, "Curso guardado con ID: " + c.getIdCurso());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(panel, "Seleccione un registro."); return; }
            svcCurso.eliminar((int) modelo.getValueAt(fila, 0));
            recargarTablaCurso(modelo);
        });

        btnLimpiar.addActionListener(e -> { txtNombre.setText(""); txtNivel.setText(""); });

        tabla.getSelectionModel().addListSelectionListener(ev -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) return;
            txtNombre.setText((String) modelo.getValueAt(fila, 1));
            txtNivel.setText((String) modelo.getValueAt(fila, 2));
        });

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 300));
        panel.add(scroll, BorderLayout.SOUTH);

        recargarTablaCurso(modelo);
        return panel;
    }

    private void recargarTablaCurso(DefaultTableModel m) {
        m.setRowCount(0);
        for (Curso c : daoCurso.listar())
            m.addRow(new Object[]{c.getIdCurso(), c.getNombreCurso(), c.getNivel()});
    }

    // ─────────────────────────────────────────────────────────────────
    //  PANEL PARALELO
    // ─────────────────────────────────────────────────────────────────
    private JPanel panelParalelo() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtLetra = new JTextField(5);
        JTextField txtCupo  = new JTextField(5);
        JComboBox<Object> cbCurso = new JComboBox<>();
        cargarCombo(cbCurso, daoCurso.listar().toArray());

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.add(new JLabel("Letra Paralelo:")); form.add(txtLetra);
        form.add(new JLabel("Cupo Máximo:")); form.add(txtCupo);
        form.add(new JLabel("Curso:")); form.add(cbCurso);

        String[] cols = {"ID", "Letra", "Cupo Máx.", "ID Curso"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);

        JButton btnGuardar  = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnRefrescar = new JButton("↺ Refrescar cursos");
        JPanel botones = new JPanel();
        botones.add(btnGuardar); botones.add(btnEliminar); botones.add(btnRefrescar);

        btnGuardar.addActionListener(e -> {
            try {
                Curso cursoSel = (Curso) cbCurso.getSelectedItem();
                if (cursoSel == null) throw new IllegalArgumentException("Seleccione un curso.");
                int cupo = Integer.parseInt(txtCupo.getText().trim());
                Paralelo p = new Paralelo(0, txtLetra.getText().trim(), cupo, cursoSel.getIdCurso());
                daoParalelo.guardar(p);
                recargarTablaParalelo(modelo);
                txtLetra.setText(""); txtCupo.setText("");
                JOptionPane.showMessageDialog(panel, "Paralelo guardado con ID: " + p.getIdParalelo());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(panel, "Seleccione un registro."); return; }
            daoParalelo.eliminar((int) modelo.getValueAt(fila, 0));
            recargarTablaParalelo(modelo);
        });

        btnRefrescar.addActionListener(e -> cargarCombo(cbCurso, daoCurso.listar().toArray()));

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 280));
        panel.add(scroll, BorderLayout.SOUTH);

        recargarTablaParalelo(modelo);
        return panel;
    }

    private void recargarTablaParalelo(DefaultTableModel m) {
        m.setRowCount(0);
        for (Paralelo p : daoParalelo.listar())
            m.addRow(new Object[]{p.getIdParalelo(), p.getLetraParalelo(), p.getCupoMaximo(), p.getIdCurso()});
    }

    // ─────────────────────────────────────────────────────────────────
    //  PANEL MATERIA
    // ─────────────────────────────────────────────────────────────────
    private JPanel panelMateria() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtNombre      = new JTextField(15);
        JTextField txtDescripcion = new JTextField(20);
        JTextField txtCarga       = new JTextField(5);

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.add(new JLabel("Nombre Materia:")); form.add(txtNombre);
        form.add(new JLabel("Descripción:")); form.add(txtDescripcion);
        form.add(new JLabel("Carga Horaria (h):")); form.add(txtCarga);

        String[] cols = {"ID", "Nombre", "Descripción", "Carga Horaria"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);

        JButton btnGuardar  = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar  = new JButton("Limpiar");
        JPanel botones = new JPanel();
        botones.add(btnGuardar); botones.add(btnEliminar); botones.add(btnLimpiar);

        btnGuardar.addActionListener(e -> {
            try {
                int carga = Integer.parseInt(txtCarga.getText().trim());
                Materia m = new Materia(0, txtNombre.getText(), txtDescripcion.getText(), carga);
                daoMateria.guardar(m);
                recargarTablaMateria(modelo);
                limpiarCampos(txtNombre, txtDescripcion, txtCarga);
                JOptionPane.showMessageDialog(panel, "Materia guardada con ID: " + m.getIdMateria());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(panel, "Seleccione un registro."); return; }
            daoMateria.eliminar((int) modelo.getValueAt(fila, 0));
            recargarTablaMateria(modelo);
        });

        btnLimpiar.addActionListener(e -> limpiarCampos(txtNombre, txtDescripcion, txtCarga));

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 290));
        panel.add(scroll, BorderLayout.SOUTH);

        recargarTablaMateria(modelo);
        return panel;
    }

    private void recargarTablaMateria(DefaultTableModel m) {
        m.setRowCount(0);
        for (Materia mat : daoMateria.listar())
            m.addRow(new Object[]{mat.getIdMateria(), mat.getNombreMateria(),
                    mat.getDescripcion(), mat.getCargaHoraria()});
    }

    // ─────────────────────────────────────────────────────────────────
    //  PANEL GESTIÓN ACADÉMICA
    // ─────────────────────────────────────────────────────────────────
    private JPanel panelGestion() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<Object> cbCurso    = new JComboBox<>();
        JComboBox<Object> cbParalelo = new JComboBox<>();
        JComboBox<Object> cbMateria  = new JComboBox<>();
        JComboBox<Object> cbProfesor = new JComboBox<>();
        JTextField txtAnio = new JTextField(10);

        cargarCombo(cbCurso, daoCurso.listar().toArray());
        cargarCombo(cbParalelo, daoParalelo.listar().toArray());
        cargarCombo(cbMateria, daoMateria.listar().toArray());
        cargarCombo(cbProfesor, new ProfesorDAO().listar().toArray());

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.add(new JLabel("Curso:")); form.add(cbCurso);
        form.add(new JLabel("Paralelo:")); form.add(cbParalelo);
        form.add(new JLabel("Materia:")); form.add(cbMateria);
        form.add(new JLabel("Profesor:")); form.add(cbProfesor);
        form.add(new JLabel("Año Lectivo:")); form.add(txtAnio);

        String[] cols = {"ID", "Curso", "Paralelo", "Materia", "Profesor", "Año Lectivo"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);

        JButton btnGuardar   = new JButton("Asignar");
        JButton btnEliminar  = new JButton("Eliminar");
        JButton btnRefrescar = new JButton("↺ Refrescar");
        JPanel botones = new JPanel();
        botones.add(btnGuardar); botones.add(btnEliminar); botones.add(btnRefrescar);

        btnGuardar.addActionListener(e -> {
            try {
                Curso    c = (Curso)    cbCurso.getSelectedItem();
                Paralelo p = (Paralelo) cbParalelo.getSelectedItem();
                Materia  m = (Materia)  cbMateria.getSelectedItem();
                Profesor pr = (Profesor) cbProfesor.getSelectedItem();
                if (c == null || p == null || m == null || pr == null)
                    throw new IllegalArgumentException("Complete todos los campos.");
                GestionAcademica g = new GestionAcademica(0, c.getIdCurso(), p.getIdParalelo(),
                        m.getIdMateria(), pr.getIdProfesor(), txtAnio.getText());
                daoGestion.guardar(g);
                recargarTablaGestion(modelo);
                txtAnio.setText("");
                JOptionPane.showMessageDialog(panel, "Gestión académica registrada ID: " + g.getIdGestion());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(panel, "Seleccione un registro."); return; }
            daoGestion.eliminar((int) modelo.getValueAt(fila, 0));
            recargarTablaGestion(modelo);
        });

        btnRefrescar.addActionListener(e -> {
            cargarCombo(cbCurso,    daoCurso.listar().toArray());
            cargarCombo(cbParalelo, daoParalelo.listar().toArray());
            cargarCombo(cbMateria,  daoMateria.listar().toArray());
            cargarCombo(cbProfesor, new ProfesorDAO().listar().toArray());
        });

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 260));
        panel.add(scroll, BorderLayout.SOUTH);

        recargarTablaGestion(modelo);
        return panel;
    }

    private void recargarTablaGestion(DefaultTableModel m) {
        m.setRowCount(0);
        for (GestionAcademica g : daoGestion.listar())
            m.addRow(new Object[]{g.getIdGestion(), g.getIdCurso(), g.getIdParalelo(),
                    g.getIdMateria(), g.getIdProfesor(), g.getAnioLectivo()});
    }

    // ─────────────────────────────────────────────────────────────────
    //  PANEL INSCRIPCIÓN
    // ─────────────────────────────────────────────────────────────────
    private JPanel panelInscripcion() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<Object> cbAlumno  = new JComboBox<>();
        JComboBox<Object> cbGestion = new JComboBox<>();

        cargarCombo(cbAlumno,  daoAlumno.listar().toArray());
        cargarCombo(cbGestion, daoGestion.listar().toArray());

        JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));
        form.add(new JLabel("Alumno:")); form.add(cbAlumno);
        form.add(new JLabel("Gestión Académica:")); form.add(cbGestion);

        String[] cols = {"ID", "Alumno (ID)", "Gestión (ID)", "Fecha", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        JButton btnInscribir  = new JButton("Inscribir");
        JButton btnCancelar   = new JButton("Cancelar Inscripción");
        JButton btnEliminar   = new JButton("Eliminar");
        JButton btnRefrescar  = new JButton("↺ Refrescar");
        JPanel botones = new JPanel();
        botones.add(btnInscribir); botones.add(btnCancelar);
        botones.add(btnEliminar); botones.add(btnRefrescar);

        btnInscribir.addActionListener(e -> {
            try {
                Alumno a = (Alumno) cbAlumno.getSelectedItem();
                GestionAcademica g = (GestionAcademica) cbGestion.getSelectedItem();
                if (a == null || g == null) throw new IllegalArgumentException("Seleccione alumno y gestión.");
                Inscripcion i = svcInscripcion.inscribir(a.getIdAlumno(), g.getIdGestion());
                recargarTablaInscripcion(modelo, sdf);
                JOptionPane.showMessageDialog(panel, "Inscripción realizada ID: " + i.getIdInscripcion());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(panel, "Seleccione una inscripción."); return; }
            svcInscripcion.cancelar((int) modelo.getValueAt(fila, 0));
            recargarTablaInscripcion(modelo, sdf);
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(panel, "Seleccione un registro."); return; }
            svcInscripcion.eliminar((int) modelo.getValueAt(fila, 0));
            recargarTablaInscripcion(modelo, sdf);
        });

        btnRefrescar.addActionListener(e -> {
            cargarCombo(cbAlumno,  daoAlumno.listar().toArray());
            cargarCombo(cbGestion, daoGestion.listar().toArray());
        });

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 280));
        panel.add(scroll, BorderLayout.SOUTH);

        recargarTablaInscripcion(modelo, sdf);
        return panel;
    }

    private void recargarTablaInscripcion(DefaultTableModel m, SimpleDateFormat sdf) {
        m.setRowCount(0);
        for (Inscripcion i : svcInscripcion.listar())
            m.addRow(new Object[]{i.getIdInscripcion(), i.getIdAlumno(), i.getIdGestion(),
                    i.getFechaInscripcion() != null ? sdf.format(i.getFechaInscripcion()) : "",
                    i.getEstado()});
    }

    // ─────────────────────────────────────────────────────────────────
    //  UTILIDADES
    // ─────────────────────────────────────────────────────────────────
    private void limpiarCampos(JTextField... campos) {
        for (JTextField f : campos) f.setText("");
    }

    private void cargarCombo(JComboBox<Object> cb, Object[] items) {
        cb.removeAllItems();
        for (Object item : items) cb.addItem(item);
    }

    // ─────────────────────────────────────────────────────────────────
    //  MAIN
    // ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VistaPrincipal::new);
    }
}
