

import java.time.LocalDate;
import java.util.ArrayList;

public class Reserva {

    // Valores validos para "estado"
    public static final String PENDIENTE = "Pendiente";
    public static final String CONFIRMADA = "Confirmada";
    public static final String EN_CURSO = "En curso";
    public static final String FINALIZADA = "Finalizada";
    public static final String CANCELADA = "Cancelada";

    // Valores validos para "metodoPago"
    public static final String TARJETA_CREDITO = "Tarjeta de credito";
    public static final String TRANSFERENCIA_BANCARIA = "Transferencia bancaria";
    public static final String EFECTIVO = "Efectivo";

    // Reglas de negocio como constantes con nombre.
    private static final double DESCUENTO_HUESPED_FRECUENTE = 0.10; // 10%
    private static final int RESERVAS_PARA_SER_FRECUENTE = 3;

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

    // crea una reserva nueva.
    // La fecha de realizacion se toma como "hoy" automaticamente.
    // Arranca en estado Pendiente y sin habitaciones ni servicios todavia.
    public Reserva(String codigoReserva, Huesped huesped, LocalDate fechaEntrada, LocalDate fechaSalida,
                   String metodoPago) {
        this.codigoReserva = codigoReserva;
        this.huesped = huesped;
        this.fechaRealizacion = LocalDate.now();
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.metodoPago = metodoPago;
        this.estado = PENDIENTE;
        this.habitaciones = new ArrayList<Habitacion>();
        this.serviciosUtilizados = new ArrayList<ServicioAdicional>();
        this.valorTotal = 0.0;
    }

    // Calcula cuantas noches dura la estadia. toEpochDay() convierte cada
    // fecha en un numero de dias, y restando obtenemos la diferencia.
    // Se deja minimo en 1 noche para evitar reservas de 0 noches.
    public long getNoches() {
        long noches = fechaSalida.toEpochDay() - fechaEntrada.toEpochDay();
        if (noches < 1) {
            noches = 1;
        }
        return noches;
    }

    // Agrega una habitacion a esta reserva.
    // Primero se valida disponibilidad y solo si esta libre, se agrega.
    public boolean agregarHabitacion(Habitacion habitacion, ArrayList<Reserva> todasLasReservas) {
        if (!habitacion.estaDisponibleEnRango(fechaEntrada, fechaSalida, todasLasReservas)) {
            return false;
        }
        habitaciones.add(habitacion);
        calcularValorTotal();
        return true;
    }

    // Quita una habitacion de la reserva.
    public boolean quitarHabitacion(Habitacion habitacion) {
        boolean seQuito = habitaciones.remove(habitacion);
        if (seQuito) {
            calcularValorTotal();
        }
        return seQuito;
    }

    // Agrega un servicio adicional, solo si esta disponible en el catalogo.
    public boolean agregarServicio(ServicioAdicional servicio) {
        if (!servicio.isDisponible()) {
            return false;
        }
        serviciosUtilizados.add(servicio);
        calcularValorTotal();
        return true;
    }

    // Quita un servicio de la reserva.
    public boolean quitarServicio(ServicioAdicional servicio) {
        boolean seQuito = serviciosUtilizados.remove(servicio);
        if (seQuito) {
            calcularValorTotal();
        }
        return seQuito;
    }

    // Calcula el valor total de la reserva:
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

        if (huesped.contarReservasFinalizadas() >= RESERVAS_PARA_SER_FRECUENTE) {
            subtotal = subtotal - (subtotal * DESCUENTO_HUESPED_FRECUENTE);
        }

        this.valorTotal = subtotal;
        return valorTotal;
    }

    // Confirma la reserva: solo funciona si esta Pendiente.
    // Al confirmar, cada habitacion incluida pasa a estado Reservada.
    public boolean confirmar() {
        if (!estado.equals(PENDIENTE)) {
            return false;
        }
        estado = CONFIRMADA;
        for (int i = 0; i < habitaciones.size(); i++) {
            habitaciones.get(i).setEstado(Habitacion.RESERVADA);
        }
        return true;
    }

    // Valida si el texto recibido es uno de los 5 estados permitidos.
    public static boolean esEstadoValido(String estado) {
        if (estado.equals(PENDIENTE) || estado.equals(CONFIRMADA) || estado.equals(EN_CURSO)
                || estado.equals(FINALIZADA) || estado.equals(CANCELADA)) {
            return true;
        }
        return false;
    }

    // Cambia el estado de la reserva y sincroniza el estado de las
    // habitaciones asociadas:
    // - En curso -> las habitaciones pasan a Ocupada
    // - Finalizada/Cancelada -> las habitaciones vuelven a Disponible
    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
        if (nuevoEstado.equals(EN_CURSO)) {
            for (int i = 0; i < habitaciones.size(); i++) {
                habitaciones.get(i).setEstado(Habitacion.OCUPADA);
            }
        } else if (nuevoEstado.equals(FINALIZADA) || nuevoEstado.equals(CANCELADA)) {
            for (int i = 0; i < habitaciones.size(); i++) {
                habitaciones.get(i).setEstado(Habitacion.DISPONIBLE);
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

    -
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