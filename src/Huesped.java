import java.util.ArrayList;

public class Huesped {

    // Atributos
    private String nombreCompleto;
    private String documentoIdentidad;
    private String telefono;
    private String correoElectronico;
    private String paisProcedencia;

    // Lista de reservas que este huesped ha hecho a lo largo del tiempo.
    private ArrayList<Reserva> reservas;

    /**
     * metodo Constructor: se usa para CREAR un nuevo huesped.
     * @param nombreCompleto
     * @param documentoIdentidad
     * @param telefono
     * @param correoElectronico
     * @param paisProcedencia
     */
     public Huesped(String nombreCompleto, String documentoIdentidad, String telefono,
                    String correoElectronico, String paisProcedencia) {
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.paisProcedencia = paisProcedencia;
        this.reservas = new ArrayList<Reserva>(); // lista vacia al inicio
    }

    // Agrega una reserva a la lista de reservas de este huesped.
    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    /**
     * Cuenta cuantas reservas de este huesped ya estan en estado "Finalizada".
     *  Se usa para decidir si el huesped es "frecuente" y aplicarle descuento.
     * metodo
     * @return
     */
    public int contarReservasFinalizadas() {
        int contador = 0;
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getEstado().equals(Reserva.finalizada)) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public String getDocumentoIdentidad() { return documentoIdentidad; }
    public String getTelefono() { return telefono; }
    public String getCorreoElectronico() { return correoElectronico; }
    public String getPaisProcedencia() { return paisProcedencia; }
    public ArrayList<Reserva> getReservas() { return reservas; }

    //  Set para poder ACTUALIZAR los datos
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public void setPaisProcedencia(String paisProcedencia) { this.paisProcedencia = paisProcedencia; }

    // toString():  llama este metodo cuando hacemos System.out.println(unHuesped).
    // Lo redefinimos para que se vea ordenado en la consola.
    public String toString() {
        return nombreCompleto + " | Doc: " + documentoIdentidad + " | Tel: " + telefono
                + " | " + correoElectronico + " | " + paisProcedencia;
    }
}