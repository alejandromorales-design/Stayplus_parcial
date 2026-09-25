

import java.time.LocalDate;
import java.util.ArrayList;

public class Reserva {

    // Valores validos para "estado"
    public static final String pendiente= "Pendiente";
    public static final String confirmada = "Confirmada";
    public static final String enCurso = "En curso";
    public static final String finalizada = "Finalizada";
    public static final String cancelada= "Cancelada";

    // Valores validos para "metodoPago"
    public static final String tarjetaDeCredito= "Tarjeta de credito";
    public static final String tranferenciaBancaria = "Transferencia bancaria";
    public static final String efectivo = "Efectivo";

    // Reglas de negocio como constantes con nombre.
    private static final double descuentuHuespedFrecuente = 0.10; // 10%
    private static final int reservasParaSerFrecuente = 3;

    //  Atributos
    private String codigoReserva;
    private LocalDate fechaRealizacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String estado;
    private String metodoPago;
    private double valorTotal;

    private Huesped huesped;
    private ArrayList<Habitacion> habitaciones;
    private ArrayList<ServicioAdicional> serviciosUtilizados;

    /**
     * metodo para crear una reserva nueva.
     * @param codigoReserva
     * @param huesped
     * @param fechaEntrada
     * @param fechaSalida
     * @param metodoPago
     */
    public Reserva(String codigoReserva, Huesped huesped, LocalDate fechaEntrada, LocalDate fechaSalida,
                   String metodoPago) {
        this.codigoReserva = codigoReserva;
        this.huesped = huesped;
        this.fechaRealizacion = LocalDate.now();
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.metodoPago = metodoPago;
        this.estado = pendiente;
        this.habitaciones = new ArrayList<Habitacion>();
        this.serviciosUtilizados = new ArrayList<ServicioAdicional>();
        this.valorTotal = 0.0;
    }

    /**
     * metodo para Calcula cuantas noches dura la estadia.
     * @return
     */

    public long getNoches() {
        long noches = fechaSalida.toEpochDay() - fechaEntrada.toEpochDay();
        if (noches < 1) {
            noches = 1;
        }
        return noches;
    }

    /**
     * metodo para Agrega una habitacion a esta reserva.
     * @param habitacion
     * @param todasLasReservas
     * @return
     */

    public boolean agregarHabitacion(Habitacion habitacion, ArrayList<Reserva> todasLasReservas) {
        if (!habitacion.estaDisponibleEnRango(fechaEntrada, fechaSalida, todasLasReservas)) {
            return false;
        }
        habitaciones.add(habitacion);
        calcularValorTotal();
        return true;
    }

    /**
     * metodo para / Quita una habitacion de la reserva.
     * @param habitacion
     * @return
     */

    public boolean quitarHabitacion(Habitacion habitacion) {
        boolean seQuito = habitaciones.remove(habitacion);
        if (seQuito) {
            calcularValorTotal();
        }
        return seQuito;
    }

    /**
     * metodo para agregar un servicio adicional, solo si esta disponible en el catalogo.
     * @param servicio
     * @return
     */
    public boolean agregarServicio(ServicioAdicional servicio) {
        if (!servicio.isDisponible()) {
            return false;
        }
        serviciosUtilizados.add(servicio);
        calcularValorTotal();
        return true;
    }

    /**
     * metodo para // Quita un servicio de la reserva.
     * @param servicio
     * @return
     */
    public boolean quitarServicioAdicional(ServicioAdicional servicio) {
        boolean seQuito = serviciosUtilizados.remove(servicio);
        if (seQuito) {
            calcularValorTotal();
        }
        return seQuito;
    }

    /**
     * metodo para calcula el valor total de la reserva:
     * @return
     */
    public double calcularValorTotal() {
        double subtotalHabitaciones = 0.0;
        for (int i = 0; i < habitaciones.size(); i++) {
            subtotalHabitaciones = subtotalHabitaciones + habitaciones.get(i).getPrecioPorNoche();
        }
        subtotalHabitaciones = subtotalHabitaciones * getNoches();

        double subtotalServicios = 0.0;
        for (int i = 0; i < serviciosUtilizados.size(); i++) {
            subtotalServicios = subtotalServicios + serviciosUtilizados.get(i).getPrecio();
        }

        double subtotal = subtotalHabitaciones + subtotalServicios;

        if (huesped.contarReservasFinalizadas() >= reservasParaSerFrecuente) {
            subtotal = subtotal - (subtotal * descuentuHuespedFrecuente);
        }

        this.valorTotal = subtotal;
        return valorTotal;
    }

    /**
     * metodo para Confirma la reserva
     * @return
     */

    public boolean confirmar() {
        if (!estado.equals(pendiente)) {
            return false;
        }
        estado = confirmada;
        for (int i = 0; i < habitaciones.size(); i++) {
            habitaciones.get(i).setEstado(Habitacion.reservada);
        }
        return true;
    }

    /**
     * metodo para Validar si el texto recibido es uno de los 5 estados permitidos.
     * @param estado
     * @return
     */

    public static boolean esEstadoValido(String estado) {
        if (estado.equals(pendiente) || estado.equals(confirmada) || estado.equals(enCurso)
                || estado.equals(finalizada) || estado.equals(cancelada)) {
            return true;
        }
        return false;
    }

    /**
     * Cambia el estado de la reserva y sincroniza el estado de las
     * habitaciones asociadas:
     * En curso -> las habitaciones pasan a Ocupada
     * Finalizada/Cancelada -> las habitaciones vuelven a Disponible
     * metodo para
     * @param nuevoEstado
     */

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
        if (nuevoEstado.equals(enCurso)) {
            for (int i = 0; i < habitaciones.size(); i++) {
                habitaciones.get(i).setEstado(Habitacion.ocupada);
            }
        } else if (nuevoEstado.equals(finalizada) || nuevoEstado.equals(cancelada)) {
            for (int i = 0; i < habitaciones.size(); i++) {
                habitaciones.get(i).setEstado(Habitacion.disponible);
            }
        }
    }

    public String getCodigoReserva() { return codigoReserva; }
    public LocalDate getFechaRealizacion() { return fechaRealizacion; }
    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public LocalDate getFechaSalida() { return fechaSalida; }
    public String getEstado() { return estado; }
    public String getMetodoPago() { return metodoPago; }
    public double getValorTotal() { return valorTotal; }
    public Huesped getHuesped() { return huesped; }
    public ArrayList<Habitacion> getHabitaciones() { return habitaciones; }
    public ArrayList<ServicioAdicional> getServiciosUtilizados() { return serviciosUtilizados; }


    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
        calcularValorTotal();
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
        calcularValorTotal();
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String toString() {
        return "Reserva " + codigoReserva + " | " + huesped.getNombreCompleto() + " | "
                + fechaEntrada + " -> " + fechaSalida + " | " + getNoches() + " noche(s) | "
                + metodoPago + " | " + estado + " | Total: $" + (long) valorTotal;
    }
}