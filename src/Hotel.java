public class Hotel {
    import java.time.LocalDate;
import java.util.ArrayList;

    public class Hotel {

        //Datos basicos del hotel
        private String nombreComercial;
        private String nit;
        private String direccion;
        private String telefono;
        private String paginaWeb;

        //  Las 4 listas que administra el hotel
        private ArrayList<Huesped> huespedes;
        private ArrayList<Habitacion> habitaciones;
        private ArrayList<ServicioAdicional> servicios;
        private ArrayList<Reserva> reservas;

        public Hotel(String nombreComercial, String nit, String direccion, String telefono, String paginaWeb) {
            this.nombreComercial = nombreComercial;
            this.nit = nit;
            this.direccion = direccion;
            this.telefono = telefono;
            this.paginaWeb = paginaWeb;
            this.huespedes = new ArrayList<Huesped>();
            this.habitaciones = new ArrayList<Habitacion>();
            this.servicios = new ArrayList<ServicioAdicional>();
            this.reservas = new ArrayList<Reserva>();
        }


        public void crearHuesped(Huesped h) {
            huespedes.add(h);
        }

        public ArrayList<Huesped> listarHuespedes() {
            return huespedes;
        }

        // Busca un huesped por documento. Si no lo encuentra, devuelve null.
        public Huesped buscarHuespedPorDocumento(String documento) {
            for (int i = 0; i < huespedes.size(); i++) {
                Huesped h = huespedes.get(i);
                if (h.getDocumentoIdentidad().equals(documento)) {
                    return h;
                }
            }
            return null;
        }

        // Busca un huesped por telefono (lo usa la consulta de numero perfecto).
        public Huesped buscarHuespedPorTelefono(String telefono) {
            for (int i = 0; i < huespedes.size(); i++) {
                Huesped h = huespedes.get(i);
                if (h.getTelefono().equals(telefono)) {
                    return h;
                }
            }
            return null;
        }

        public boolean actualizarHuesped(String documento, String nuevoTelefono, String nuevoCorreo, String nuevoPais) {
            Huesped h = buscarHuespedPorDocumento(documento);
            if (h == null) {
                return false;
            }
            h.setTelefono(nuevoTelefono);
            h.setCorreoElectronico(nuevoCorreo);
            h.setPaisProcedencia(nuevoPais);
            return true;
        }

        // Elimina un huesped (solo si no tiene reservas registradas).
        public boolean eliminarHuesped(String documento) {
            Huesped h = buscarHuespedPorDocumento(documento);
            if (h == null) {
                return false;
            }
            if (h.getReservas().size() > 0) {
                return false;
            }
            huespedes.remove(h);
            return true;
        }

        public void crearHabitacion(Habitacion h) {
            habitaciones.add(h);
        }

        public ArrayList<Habitacion> listarHabitaciones() {
            return habitaciones;
        }

        public Habitacion buscarHabitacionPorNumero(int numero) {
            for (int i = 0; i < habitaciones.size(); i++) {
                Habitacion h = habitaciones.get(i);
                if (h.getNumero() == numero) {
                    return h;
                }
            }
            return null;
        }

        public boolean actualizarPrecioHabitacion(int numero, double nuevoPrecio) {
            Habitacion h = buscarHabitacionPorNumero(numero);
            if (h == null) {
                return false;
            }
            h.setPrecioPorNoche(nuevoPrecio);
            return true;
        }

        // Cambia el estado de una habitacion, validando primero que el texto
        // recibido sea uno de los 4 estados permitidos.
        public boolean actualizarEstadoHabitacion(int numero, String nuevoEstado) {
            if (!Habitacion.esEstadoValido(nuevoEstado)) {
                return false;
            }
            Habitacion h = buscarHabitacionPorNumero(numero);
            if (h == null) {
                return false;
            }
            h.setEstado(nuevoEstado);
            return true;
        }

        // Elimina una habitacion (solo si esta Disponible).
        public boolean eliminarHabitacion(int numero) {
            Habitacion h = buscarHabitacionPorNumero(numero);
            if (h == null) {
                return false;
            }
            if (!h.getEstado().equals(Habitacion.DISPONIBLE)) {
                return false;
            }
            habitaciones.remove(h);
            return true;
        }


        public void crearServicio(ServicioAdicional s) {
            servicios.add(s);
        }

        public ArrayList<ServicioAdicional> listarServicios() {
            return servicios;
        }

        public ServicioAdicional buscarServicioPorCodigo(String codigo) {
            for (int i = 0; i < servicios.size(); i++) {
                ServicioAdicional s = servicios.get(i);
                if (s.getCodigo().equals(codigo)) {
                    return s;
                }
            }
            return null;
        }

        public boolean actualizarPrecioServicio(String codigo, double nuevoPrecio) {
            ServicioAdicional s = buscarServicioPorCodigo(codigo);
            if (s == null) {
                return false;
            }
            s.setPrecio(nuevoPrecio);
            return true;
        }

        public boolean eliminarServicio(String codigo) {
            ServicioAdicional s = buscarServicioPorCodigo(codigo);
            if (s == null) {
                return false;
            }
            servicios.remove(s);
            return true;
        }


        // Registra una reserva nueva en el hotel Y en el huesped, para mantener
        // sincronizadas las dos listas.
        public void crearReserva(Reserva r) {
            reservas.add(r);
            r.getHuesped().agregarReserva(r);
        }

        public ArrayList<Reserva> listarReservas() {
            return reservas;
        }

        public Reserva buscarReservaPorCodigo(String codigo) {
            for (int i = 0; i < reservas.size(); i++) {
                Reserva r = reservas.get(i);
                if (r.getCodigoReserva().equals(codigo)) {
                    return r;
                }
            }
            return null;
        }

        // Cambia el estado de una reserva, validando primero que sea uno de los
        // 5 estados permitidos.
        public boolean actualizarEstadoReserva(String codigo, String nuevoEstado) {
            if (!Reserva.esEstadoValido(nuevoEstado)) {
                return false;
            }
            Reserva r = buscarReservaPorCodigo(codigo);
            if (r == null) {
                return false;
            }
            r.cambiarEstado(nuevoEstado);
            return true;
        }

        // Elimina una reserva (solo si esta Pendiente o Cancelada).
        public boolean eliminarReserva(String codigo) {
            Reserva r = buscarReservaPorCodigo(codigo);
            if (r == null) {
                return false;
            }
            if (!r.getEstado().equals(Reserva.PENDIENTE) && !r.getEstado().equals(Reserva.CANCELADA)) {
                return false;
            }
            reservas.remove(r);
            r.getHuesped().getReservas().remove(r);
            return true;
        }


        // Determina si un numero es "perfecto": la suma de sus divisores propios
        public boolean esNumeroPerfecto(long numero) {
            if (numero <= 1) {
                return false;
            }
            long sumaDivisores = 0;
            for (long i = 1; i <= numero / 2; i++) {
                if (numero % i == 0) {
                    sumaDivisores = sumaDivisores + i;
                }
            }
            return sumaDivisores == numero;
        }

        // valida si ese numero es perfecto.
        public boolean telefonoEsPerfecto(String telefono) {
            String soloDigitos = telefono.replaceAll("[^0-9]", "");
            if (soloDigitos.equals("")) {
                return false;
            }
            try {
                long numero = Long.parseLong(soloDigitos);
                return esNumeroPerfecto(numero);
            } catch (NumberFormatException e) {
                return false; // el telefono tiene demasiados digitos
            }
        }

        // Suma el valor total de todas las reservas cuya fecha de realizacion
        // sea igual a la fecha consultada.
        public double calcularIngresosPorFecha(LocalDate fecha) {
            double totalAcumulado = 0.0;
            for (int i = 0; i < reservas.size(); i++) {
                Reserva r = reservas.get(i);
                if (r.getFechaRealizacion().equals(fecha)) {
                    totalAcumulado = totalAcumulado + r.getValorTotal();
                }
            }
            return totalAcumulado;
        }

        public String getNombreComercial() { return nombreComercial; }
        public String getNit() { return nit; }
        public String getDireccion() { return direccion; }
        public String getTelefono() { return telefono; }
        public String getPaginaWeb() { return paginaWeb; }
    }
}
