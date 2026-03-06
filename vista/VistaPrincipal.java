package vista;

import controlador.AlumnoControlador;
import modelo.Alumno;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VistaPrincipal extends JFrame {

    private final AlumnoControlador controlador = new AlumnoControlador();
    private JTextField txtNombres, txtApellidos, txtCedula, txtDireccion, txtTelefono;
    private JFormattedTextField txtFechaNac;
    private JList<Alumno> listaAlumnos;
    private DefaultListModel<Alumno> listModel;
    private JLabel lblId;

    public VistaPrincipal() {
        setTitle("Colegio - Inscripciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelPrincipal.add(crearPanelFormulario(), BorderLayout.NORTH);
        panelPrincipal.add(crearPanelLista(), BorderLayout.CENTER);
        panelPrincipal.add(crearPanelBotones(), BorderLayout.SOUTH);

        add(panelPrincipal);
        actualizarLista();
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del alumno"));

        lblId = new JLabel("(Nuevo)");
        panel.add(new JLabel("ID:"));
        panel.add(lblId);

        txtNombres = new JTextField(20);
        panel.add(new JLabel("Nombres:"));
        panel.add(txtNombres);

        txtApellidos = new JTextField(20);
        panel.add(new JLabel("Apellidos:"));
        panel.add(txtApellidos);

        txtCedula = new JTextField(15);
        panel.add(new JLabel("Cédula:"));
        panel.add(txtCedula);

        try {
            txtFechaNac = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
            txtFechaNac.setColumns(10);
        } catch (Exception e) {
            txtFechaNac = new JFormattedTextField();
        }
        panel.add(new JLabel("Fecha nacimiento (dd/MM/yyyy):"));
        panel.add(txtFechaNac);

        txtDireccion = new JTextField(25);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);

        txtTelefono = new JTextField(15);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);

        return panel;
    }

    private JPanel crearPanelLista() {
        listModel = new DefaultListModel<>();
        listaAlumnos = new JList<>(listModel);
        listaAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaAlumnos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarAlumnoSeleccionado();
            }
        });

        JScrollPane scroll = new JScrollPane(listaAlumnos);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Alumnos registrados"));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());

        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.addActionListener(e -> limpiarFormulario());

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminar());

        panel.add(btnGuardar);
        panel.add(btnNuevo);
        panel.add(btnEliminar);

        return panel;
    }

    private void guardar() {
        try {
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String cedula = txtCedula.getText().trim();
            String dir = txtDireccion.getText().trim();
            String tel = txtTelefono.getText().trim();

            Date fechaNac = null;
            String f = txtFechaNac.getText().trim();
            if (!f.isEmpty()) {
                try {
                    fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(f);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Fecha inválida. Use dd/MM/yyyy.");
                    return;
                }
            }

            String idStr = lblId.getText();
            if ("(Nuevo)".equals(idStr)) {
                controlador.registrar(nombres, apellidos, cedula, fechaNac, dir, tel);
                JOptionPane.showMessageDialog(this, "Alumno registrado.");
            } else {
                int id = Integer.parseInt(idStr);
                controlador.actualizar(id, nombres, apellidos, cedula, fechaNac, dir, tel);
                JOptionPane.showMessageDialog(this, "Alumno actualizado.");
            }
            limpiarFormulario();
            actualizarLista();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void eliminar() {
        String idStr = lblId.getText();
        if ("(Nuevo)".equals(idStr)) {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno para eliminar.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar este alumno?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controlador.eliminar(Integer.parseInt(idStr));
            limpiarFormulario();
            actualizarLista();
        }
    }

    private void cargarAlumnoSeleccionado() {
        Alumno a = listaAlumnos.getSelectedValue();
        if (a == null) return;
        lblId.setText(String.valueOf(a.getIdAlumno()));
        txtNombres.setText(a.getNombres());
        txtApellidos.setText(a.getApellidos() != null ? a.getApellidos() : "");
        txtCedula.setText(a.getCedulaIdentidad() != null ? a.getCedulaIdentidad() : "");
        txtDireccion.setText(a.getDireccion() != null ? a.getDireccion() : "");
        txtTelefono.setText(a.getTelefonoContacto() != null ? a.getTelefonoContacto() : "");
        if (a.getFechaNacimiento() != null) {
            txtFechaNac.setText(new SimpleDateFormat("dd/MM/yyyy").format(a.getFechaNacimiento()));
        } else {
            txtFechaNac.setText("");
        }
    }

    private void limpiarFormulario() {
        lblId.setText("(Nuevo)");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCedula.setText("");
        txtFechaNac.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        listaAlumnos.clearSelection();
    }

    private void actualizarLista() {
        listModel.clear();
        List<Alumno> alumnos = controlador.listar();
        for (Alumno a : alumnos) {
            listModel.addElement(a);
        }
    }

    @Override
    public String toString() {
        return "VistaPrincipal";
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> {
            VistaPrincipal v = new VistaPrincipal();
            v.setVisible(true);
        });
    }
}
