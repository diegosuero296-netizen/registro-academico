import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Interfaz gráfica (Swing) para capturar los datos de un estudiante y calcular su resultado.
 * <p>
 * Se encarga únicamente de la presentación y de convertir el texto de los campos a números;
 * las reglas de negocio las aplica la clase {@link Estudiante}.
 * </p>
 *
 * @author Estudiante
 * @version 1.0
 */
public class RegistroAcademicoGUI extends JFrame {

    private static final Font FUENTE_BASE = new Font("SansSerif", Font.PLAIN, 15);
    private static final Font FUENTE_TITULO = new Font("SansSerif", Font.BOLD, 22);
    private static final Color COLOR_APROBADO = new Color(0x1B7F3B);
    private static final Color COLOR_REPROBADO = new Color(0xB3261E);

    private final JTextField campoNombre = new JTextField(22);
    private final JTextField campoMatricula = new JTextField(22);
    private final JTextField campoCarrera = new JTextField(22);
    private final JTextField campoPracticas = new JTextField(8);
    private final JTextField campoParciales = new JTextField(8);
    private final JTextField campoAsignaciones = new JTextField(8);
    private final JTextField campoExamenFinal = new JTextField(8);
    private final JLabel etiquetaNota = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel etiquetaEstado = new JLabel(" ", SwingConstants.CENTER);
    private final JButton botonCalcular = new JButton("Calcular Resultado");
    private final JButton botonLimpiar = new JButton("Limpiar");

    /** Construye la ventana principal con todos sus componentes. */
    public RegistroAcademicoGUI() {
        super("Registro Académico");
        aplicarLookAndFeel();
        construirInterfaz();
        configurarEventos();
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /** Usa el estilo Nimbus si está disponible; de lo contrario deja el predeterminado. */
    private void aplicarLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Se mantiene el estilo por defecto.
        }
    }

    /** Arma los paneles: título, datos personales, calificaciones y resultado. */
    private void construirInterfaz() {
        JPanel raiz = new JPanel(new BorderLayout(0, 14));
        raiz.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        JLabel titulo = new JLabel("Registro Académico", SwingConstants.CENTER);
        titulo.setFont(FUENTE_TITULO);
        raiz.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 12, 0);
        centro.add(panelDatosPersonales(), c);
        c.gridy = 1;
        centro.add(panelCalificaciones(), c);
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        centro.add(panelResultado(), c);
        raiz.add(centro, BorderLayout.CENTER);

        raiz.add(panelBotones(), BorderLayout.SOUTH);
        setContentPane(raiz);
    }

    /** @return panel con los campos de nombre, matrícula y carrera */
    private JPanel panelDatosPersonales() {
        JPanel panel = nuevoPanel("Datos personales");
        agregarFila(panel, 0, "Nombre completo:", campoNombre, "");
        agregarFila(panel, 1, "Matrícula:", campoMatricula, "");
        agregarFila(panel, 2, "Carrera:", campoCarrera, "");
        return panel;
    }

    /** @return panel con los cuatro campos numéricos de calificaciones */
    private JPanel panelCalificaciones() {
        JPanel panel = nuevoPanel("Calificaciones");
        agregarFila(panel, 0, "Prácticas:", campoPracticas, "(máx. 40)");
        agregarFila(panel, 1, "Parciales:", campoParciales, "(máx. 20)");
        agregarFila(panel, 2, "Asignaciones:", campoAsignaciones, "(máx. 20)");
        agregarFila(panel, 3, "Examen Final:", campoExamenFinal, "(máx. 20)");
        return panel;
    }

    /** @return panel que muestra la nota total y el mensaje APROBADO / REPROBADO */
    private JPanel panelResultado() {
        JPanel panel = nuevoPanel("Resultado");
        panel.setLayout(new BorderLayout(0, 6));
        etiquetaNota.setFont(new Font("SansSerif", Font.BOLD, 18));
        etiquetaEstado.setFont(new Font("SansSerif", Font.BOLD, 26));
        panel.add(etiquetaNota, BorderLayout.NORTH);
        panel.add(etiquetaEstado, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(400, 110));
        return panel;
    }

    /** @return panel con los botones de acción */
    private JPanel panelBotones() {
        JPanel panel = new JPanel();
        botonCalcular.setFont(FUENTE_BASE);
        botonLimpiar.setFont(FUENTE_BASE);
        panel.add(botonCalcular);
        panel.add(botonLimpiar);
        return panel;
    }

    /**
     * Crea un panel con borde y título.
     *
     * @param titulo texto del borde
     * @return panel con {@link GridBagLayout}
     */
    private JPanel nuevoPanel(String titulo) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        return panel;
    }

    /**
     * Añade una fila "etiqueta + campo + ayuda" a un panel.
     *
     * @param panel    panel destino
     * @param fila     número de fila
     * @param texto    texto de la etiqueta
     * @param campo    campo de texto
     * @param ayuda    texto auxiliar a la derecha (por ejemplo, el máximo permitido)
     */
    private void agregarFila(JPanel panel, int fila, String texto, JTextField campo, String ayuda) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = fila;
        c.insets = new Insets(5, 8, 5, 8);
        c.anchor = GridBagConstraints.WEST;

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(FUENTE_BASE);
        campo.setFont(FUENTE_BASE);
        c.gridx = 0;
        panel.add(etiqueta, c);
        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campo, c);
        c.gridx = 2;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(ayuda), c);
    }

    /** Asocia las acciones de los botones. */
    private void configurarEventos() {
        botonCalcular.addActionListener(e -> calcularResultado());
        botonLimpiar.addActionListener(e -> limpiarFormulario());
        getRootPane().setDefaultButton(botonCalcular);
    }

    /**
     * Lee los campos, crea el {@link Estudiante} y muestra la nota y el estado.
     * Si hay un dato inválido, muestra un mensaje de error en lugar del resultado.
     */
    private void calcularResultado() {
        try {
            Estudiante estudiante = new Estudiante(
                campoNombre.getText(),
                campoMatricula.getText(),
                campoCarrera.getText(),
                leerNumero(campoPracticas, "Prácticas"),
                leerNumero(campoParciales, "Parciales"),
                leerNumero(campoAsignaciones, "Asignaciones"),
                leerNumero(campoExamenFinal, "Examen Final"));
            mostrarResultado(estudiante);
        } catch (IllegalArgumentException ex) {
            etiquetaNota.setText(" ");
            etiquetaEstado.setText(" ");
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Dato inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Convierte el texto de un campo a número.
     *
     * @param campo  campo de texto a leer
     * @param nombre nombre de la calificación, usado en el mensaje de error
     * @return el valor numérico (acepta punto o coma como separador decimal)
     * @throws IllegalArgumentException si el campo está vacío o no es un número
     */
    private double leerNumero(JTextField campo, String nombre) {
        String texto = campo.getText().trim().replace(',', '.');
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombre + " no puede estar vacío.");
        }
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "El campo " + nombre + " solo acepta valores numéricos.");
        }
    }

    /**
     * Muestra la nota total y el estado del estudiante con su color correspondiente.
     *
     * @param estudiante estudiante ya validado
     */
    private void mostrarResultado(Estudiante estudiante) {
        Color color = estudiante.estaAprobado() ? COLOR_APROBADO : COLOR_REPROBADO;
        etiquetaNota.setText(String.format("Nota total: %.1f / 100", estudiante.calcularNotaTotal()));
        etiquetaEstado.setText(estudiante.getResultado());
        etiquetaNota.setForeground(color);
        etiquetaEstado.setForeground(color);
    }

    /** Vacía todos los campos y borra el resultado mostrado. */
    private void limpiarFormulario() {
        for (JTextField campo : new JTextField[] {campoNombre, campoMatricula, campoCarrera,
                campoPracticas, campoParciales, campoAsignaciones, campoExamenFinal}) {
            campo.setText("");
        }
        etiquetaNota.setText(" ");
        etiquetaEstado.setText(" ");
        campoNombre.requestFocusInWindow();
    }
}
