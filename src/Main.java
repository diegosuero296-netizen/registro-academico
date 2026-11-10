import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicación de Registro Académico.
 *
 * @author Estudiante
 * @version 1.0
 */
public class Main {

    /**
     * Inicia la interfaz gráfica en el hilo de eventos de Swing.
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroAcademicoGUI().setVisible(true));
    }
}
