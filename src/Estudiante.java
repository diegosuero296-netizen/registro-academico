/**
 * Representa a un estudiante con sus datos personales y sus calificaciones parciales.
 * <p>
 * La clase es inmutable: una vez creada, sus datos no cambian. Toda la lógica de negocio
 * (validación de rangos, cálculo de la nota total y determinación de aprobado/reprobado)
 * vive aquí, separada de la interfaz gráfica.
 * </p>
 *
 * @author Estudiante
 * @version 1.0
 */
public class Estudiante {

    /** Puntaje máximo permitido en Prácticas. */
    public static final double MAX_PRACTICAS = 40;
    /** Puntaje máximo permitido en Parciales. */
    public static final double MAX_PARCIALES = 20;
    /** Puntaje máximo permitido en Asignaciones. */
    public static final double MAX_ASIGNACIONES = 20;
    /** Puntaje máximo permitido en el Examen Final. */
    public static final double MAX_EXAMEN_FINAL = 20;
    /** Nota mínima (inclusive) para aprobar la materia. */
    public static final double NOTA_APROBACION = 60;

    private final String nombreCompleto;
    private final String matricula;
    private final String carrera;
    private final double practicas;
    private final double parciales;
    private final double asignaciones;
    private final double examenFinal;

    /**
     * Crea un estudiante validando todos sus datos.
     *
     * @param nombreCompleto nombre completo (no puede estar vacío)
     * @param matricula      matrícula del estudiante (no puede estar vacía)
     * @param carrera        carrera que cursa (no puede estar vacía)
     * @param practicas      puntos en prácticas, entre 0 y {@link #MAX_PRACTICAS}
     * @param parciales      puntos en parciales, entre 0 y {@link #MAX_PARCIALES}
     * @param asignaciones   puntos en asignaciones, entre 0 y {@link #MAX_ASIGNACIONES}
     * @param examenFinal    puntos en el examen final, entre 0 y {@link #MAX_EXAMEN_FINAL}
     * @throws IllegalArgumentException si algún texto está vacío o alguna nota está fuera de rango
     */
    public Estudiante(String nombreCompleto, String matricula, String carrera,
                      double practicas, double parciales,
                      double asignaciones, double examenFinal) {
        this.nombreCompleto = validarTexto("El nombre completo", nombreCompleto);
        this.matricula = validarTexto("La matrícula", matricula);
        this.carrera = validarTexto("La carrera", carrera);
        this.practicas = validarRango("Prácticas", practicas, MAX_PRACTICAS);
        this.parciales = validarRango("Parciales", parciales, MAX_PARCIALES);
        this.asignaciones = validarRango("Asignaciones", asignaciones, MAX_ASIGNACIONES);
        this.examenFinal = validarRango("Examen Final", examenFinal, MAX_EXAMEN_FINAL);
    }

    /**
     * Calcula la nota total sumando las cuatro calificaciones.
     *
     * @return nota total entre 0 y 100
     */
    public double calcularNotaTotal() {
        return practicas + parciales + asignaciones + examenFinal;
    }

    /**
     * Indica si el estudiante aprobó la materia.
     *
     * @return {@code true} si la nota total es mayor o igual a {@link #NOTA_APROBACION}
     */
    public boolean estaAprobado() {
        return calcularNotaTotal() >= NOTA_APROBACION;
    }

    /**
     * Devuelve el mensaje de resultado final.
     *
     * @return "APROBADO" o "REPROBADO"
     */
    public String getResultado() {
        return estaAprobado() ? "APROBADO" : "REPROBADO";
    }

    /** @return nombre completo del estudiante */
    public String getNombreCompleto() { return nombreCompleto; }

    /** @return matrícula del estudiante */
    public String getMatricula() { return matricula; }

    /** @return carrera del estudiante */
    public String getCarrera() { return carrera; }

    /** @return puntos obtenidos en prácticas */
    public double getPracticas() { return practicas; }

    /** @return puntos obtenidos en parciales */
    public double getParciales() { return parciales; }

    /** @return puntos obtenidos en asignaciones */
    public double getAsignaciones() { return asignaciones; }

    /** @return puntos obtenidos en el examen final */
    public double getExamenFinal() { return examenFinal; }

    /**
     * Verifica que un texto no sea nulo ni esté vacío.
     *
     * @param campo nombre del campo, usado en el mensaje de error
     * @param valor texto a validar
     * @return el texto sin espacios al inicio ni al final
     */
    private static String validarTexto(String campo, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
        return valor.trim();
    }

    /**
     * Verifica que una nota esté entre 0 y su máximo permitido.
     *
     * @param campo  nombre de la calificación, usado en el mensaje de error
     * @param valor  nota a validar
     * @param maximo puntaje máximo permitido
     * @return la misma nota si es válida
     */
    private static double validarRango(String campo, double valor, double maximo) {
        if (Double.isNaN(valor) || valor < 0 || valor > maximo) {
            throw new IllegalArgumentException(
                campo + " debe estar entre 0 y " + formatear(maximo) + ".");
        }
        return valor;
    }

    /**
     * Formatea un número sin decimales innecesarios (40.0 se muestra como 40).
     *
     * @param valor número a formatear
     * @return texto del número
     */
    private static String formatear(double valor) {
        return valor == Math.floor(valor) ? String.valueOf((long) valor) : String.valueOf(valor);
    }
}
