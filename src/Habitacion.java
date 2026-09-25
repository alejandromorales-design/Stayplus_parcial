import java.time.LocalDate;
import java.util.ArrayList;


public class Habitacion {


    public static final String individual = "Individual";
    public static final String doble = "Doble";
    public static final String suiete = "Suite";

    public static final String disponible = "Disponible";
    public static final String reservada= "Reservada";
    public static final String ocupada= "Ocupada";
    public static final String mantenimiento = "Mantenimiento";


    private int numero;
    private int piso;
    private String tipo;
    private int capacidadMaxima;
    private double precioPorNoche;
    private String estado;


    public Habitacion(int numero, int piso, String tipo, int capacidadMaxima, double precioPorNoche) {
        this.numero = numero;
        this.piso = piso;
        this.tipo = tipo;
        this.capacidadMaxima = capacidadMaxima;
        this.precioPorNoche = precioPorNoche;
        this.estado = disponible;
    }

    /**
     * Revisa si esta habitacion esta libre entre las fechas "entrada" y
     *  "salida" que alguien quiere reservar.
     * Como lo hace:
     *  1. Si la habitacion esta en MANTENIMIENTO, no esta disponible, punto.
     *  2. Si no, recorre TODAS las reservas del hotel (nos las pasan como
     *  parametro) y busca las que ya incluyen esta misma habitacion.
     *  3. Ignora las CANCELADAS (una reserva cancelada no bloquea fechas).
     *  4. Para las demas, revisa si el rango de fechas nuevo se cruza con el
     *  rango de fechas de la reserva existente. Si se cruzan, no esta
     *  disponible.
     * @param entrada
     * @param salida
     * @param todasLasReservas
     * @return
     */

    public boolean estaDisponibleEnRango(LocalDate entrada, LocalDate salida, ArrayList<Reserva> todasLasReservas) {
        if (estado.equals(mantenimiento)) {
            return false;
        }
        for (int i = 0; i < todasLasReservas.size(); i++) {
            Reserva r = todasLasReservas.get(i);

            if (r.getEstado().equals(Reserva.cancelada)) {
                continue; // una reserva cancelada no cuenta
            }
            boolean laIncluye = false;
            ArrayList<Habitacion> habsDeEsaReserva = r.getHabitaciones();
            for (int j = 0; j < habsDeEsaReserva.size(); j++) {
                if (habsDeEsaReserva.get(j).getNumero() == this.numero) {
                    laIncluye = true;
                }
            }
            if (!laIncluye) {
                continue;
            }

            // Dos rangos de fechas se solapan si:
            // la entrada nueva es ANTES de la salida existente, Y
            // la entrada existente es ANTES de la salida nueva.
            boolean seSolapan = entrada.isBefore(r.getFechaSalida()) && r.getFechaEntrada().isBefore(salida);
            if (seSolapan) {
                return false;
            }
        }
        return true;
    }

    /**
     * metodo  que valida si el texto recibido es uno de los 4 estados permitidos.
     * @param estado
     * @return
     */

    // Valida si el texto recibido es uno de los 4 estados permitidos.
    public static boolean esEstadoValido(String estado) {
        if (estado.equals(disponible) || estado.equals(reservada)
                || estado.equals(ocupada) || estado.equals(mantenimiento)) {
            return true;
        }
        return false;
    }


    public int getNumero() { return numero; }
    public int getPiso() { return piso; }
    public String getTipo() { return tipo; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public double getPrecioPorNoche() { return precioPorNoche; }
    public String getEstado() { return estado; }

    public void setPiso(int piso) { this.piso = piso; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
    public void setPrecioPorNoche(double precioPorNoche) { this.precioPorNoche = precioPorNoche; }
    public void setEstado(String estado) { this.estado = estado; }

    public String toString() {
        return "Hab. " + numero + " (Piso " + piso + ", " + tipo + ", cap " + capacidadMaxima + ") - $"
                + (long) precioPorNoche + "/noche - " + estado;
    }
}