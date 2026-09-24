import java.util.Scanner;
    import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

    public class Main {

        private static Hotel hotel;
        private static Scanner sc = new Scanner(System.in);

        public static void main(String[] args) {
            hotel = new Hotel("Hotel StayPlus", "900123456-7", "Cra 10 # 20-30, Armenia", "6067654321", "www.stayplus.com");
            precargarDatosDeEjemplo();

            int opcion = -1;
            while (opcion != 0) {
                mostrarMenuPrincipal();
                opcion = leerEntero("Selecciona una opcion: ");

                if (opcion == 1) {
                    menuHuespedes();
                } else if (opcion == 2) {
                    menuHabitaciones();
                } else if (opcion == 3) {
                    menuServicios();
                } else if (opcion == 4) {
                    menuReservas();
                } else if (opcion == 5) {
                    consultarNumeroPerfecto();
                } else if (opcion == 6) {
                    consultarIngresosPorFecha();
                } else if (opcion == 0) {
                    System.out.println("Gracias por usar el sistema de " + hotel.getNombreComercial());
                } else {
                    System.out.println("Opcion invalida.\n");
                }
            }
            sc.close();
        }

        private static void mostrarMenuPrincipal() {
            System.out.println("=========================================");
            System.out.println(" " + hotel.getNombreComercial() + " - Menu principal");
            System.out.println("=========================================");
            System.out.println("1. CRUD Huespedes");
            System.out.println("2. CRUD Habitaciones");
            System.out.println("3. CRUD Servicios adicionales");
            System.out.println("4. CRUD Reservas");
            System.out.println("5. Consultar numero perfecto por telefono");
            System.out.println("6. Consultar ingresos por fecha");
            System.out.println("0. Salir");
        }

        // ========================================================================
        // CRUD Huespedes
        // ========================================================================

        private static void menuHuespedes() {
            int op = -1;
            while (op != 0) {
                System.out.println("\n--- CRUD Huespedes ---");
                System.out.println("1. Crear   2. Leer/Listar   3. Actualizar   4. Eliminar   0. Volver");
                op = leerEntero("Opcion: ");

                if (op == 1) {
                    crearHuespedUI();
                } else if (op == 2) {
                    leerHuespedesUI();
                } else if (op == 3) {
                    actualizarHuespedUI();
                } else if (op == 4) {
                    eliminarHuespedUI();
                } else if (op != 0) {
                    System.out.println("Opcion invalida.");
                }
            }
        }

        // CREATE: pide los datos por consola y llama a hotel.crearHuesped().
        private static void crearHuespedUI() {
            System.out.println("--- Crear huesped ---");
            String nombre = leerTexto("Nombre completo: ");
            String doc = leerTexto("Documento de identidad: ");
            String tel = leerTexto("Telefono: ");
            String correo = leerTexto("Correo electronico: ");
            String pais = leerTexto("Pais de procedencia: ");
            hotel.crearHuesped(new Huesped(nombre, doc, tel, correo, pais));
            System.out.println("Huesped creado.");
        }

        // READ: muestra todos los huespedes registrados.
        private static void leerHuespedesUI() {
            System.out.println("--- Listado de huespedes ---");
            ArrayList<Huesped> lista = hotel.listarHuespedes();
            if (lista.size() == 0) {
                System.out.println("(sin registros)");
                return;
            }
            for (int i = 0; i < lista.size(); i++) {
                System.out.println(lista.get(i));
            }
        }

        // UPDATE: pide el documento y los nuevos datos.
        private static void actualizarHuespedUI() {
            System.out.println("--- Actualizar huesped ---");
            String doc = leerTexto("Documento del huesped a actualizar: ");
            String tel = leerTexto("Nuevo telefono: ");
            String correo = leerTexto("Nuevo correo: ");
            String pais = leerTexto("Nuevo pais: ");
            boolean ok = hotel.actualizarHuesped(doc, tel, correo, pais);
            if (ok) {
                System.out.println("Huesped actualizado.");
            } else {
                System.out.println("No se encontro un huesped con ese documento.");
            }
        }

        // DELETE
        private static void eliminarHuespedUI() {
            System.out.println("--- Eliminar huesped ---");
            String doc = leerTexto("Documento del huesped a eliminar: ");
            boolean ok = hotel.eliminarHuesped(doc);
            if (ok) {
                System.out.println("Huesped eliminado.");
            } else {
                System.out.println("No se pudo eliminar (no existe, o tiene reservas registradas).");
            }
        }

        // ========================================================================
        // CRUD Habitaciones
        // ========================================================================

        private static void menuHabitaciones() {
            int op = -1;
            while (op != 0) {
                System.out.println("\n--- CRUD Habitaciones ---");
                System.out.println("1. Crear   2. Leer/Listar   3. Actualizar   4. Eliminar   0. Volver");
                op = leerEntero("Opcion: ");

                if (op == 1) {
                    crearHabitacionUI();
                } else if (op == 2) {
                    leerHabitacionesUI();
                } else if (op == 3) {
                    actualizarHabitacionUI();
                } else if (op == 4) {
                    eliminarHabitacionUI();
                } else if (op != 0) {
                    System.out.println("Opcion invalida.");
                }
            }
        }

        private static void crearHabitacionUI() {
            System.out.println("--- Crear habitacion ---");
            int numero = leerEntero("Numero: ");
            int piso = leerEntero("Piso: ");
            String tipo = leerTipoHabitacion();
            int capacidad = leerEntero("Capacidad maxima: ");
            double precio = leerDecimal("Precio por noche: ");
            hotel.crearHabitacion(new Habitacion(numero, piso, tipo, capacidad, precio));
            System.out.println("Habitacion creada.");
        }

        private static void leerHabitacionesUI() {
            System.out.println("--- Listado de habitaciones ---");
            ArrayList<Habitacion> lista = hotel.listarHabitaciones();
            if (lista.size() == 0) {
                System.out.println("(sin registros)");
                return;
            }
            for (int i = 0; i < lista.size(); i++) {
                System.out.println(lista.get(i));
            }
        }

        private static void actualizarHabitacionUI() {
            System.out.println("--- Actualizar habitacion ---");
            int numero = leerEntero("Numero de la habitacion: ");
            double nuevoPrecio = leerDecimal("Nuevo precio por noche: ");
            boolean okPrecio = hotel.actualizarPrecioHabitacion(numero, nuevoPrecio);
            if (!okPrecio) {
                System.out.println("No se encontro esa habitacion.");
                return;
            }

            System.out.println("1. Disponible  2. Reservada  3. Ocupada  4. Mantenimiento");
            int op = leerEntero("Nuevo estado: ");
            String nuevoEstado = null;
            if (op == 1) {
                nuevoEstado = Habitacion.DISPONIBLE;
            } else if (op == 2) {
                nuevoEstado = Habitacion.RESERVADA;
            } else if (op == 3) {
                nuevoEstado = Habitacion.OCUPADA;
            } else if (op == 4) {
                nuevoEstado = Habitacion.MANTENIMIENTO;
            }

            if (nuevoEstado != null) {
                hotel.actualizarEstadoHabitacion(numero, nuevoEstado);
            }
            System.out.println("Habitacion actualizada.");
        }

        private static void eliminarHabitacionUI() {
            System.out.println("--- Eliminar habitacion ---");
            int numero = leerEntero("Numero de la habitacion a eliminar: ");
            boolean ok = hotel.eliminarHabitacion(numero);
            if (ok) {
                System.out.println("Habitacion eliminada.");
            } else {
                System.out.println("No se pudo eliminar (no existe, o no esta Disponible).");
            }
        }

        // ========================================================================
        // CRUD Servicios adicionales
        // ========================================================================

        private static void menuServicios() {
            int op = -1;
            while (op != 0) {
                System.out.println("\n--- CRUD Servicios adicionales ---");
                System.out.println("1. Crear   2. Leer/Listar   3. Actualizar   4. Eliminar   0. Volver");
                op = leerEntero("Opcion: ");

                if (op == 1) {
                    crearServicioUI();
                } else if (op == 2) {
                    leerServiciosUI();
                } else if (op == 3) {
                    actualizarServicioUI();
                } else if (op == 4) {
                    eliminarServicioUI();
                } else if (op != 0) {
                    System.out.println("Opcion invalida.");
                }
            }
        }

        private static void crearServicioUI() {
            System.out.println("--- Crear servicio adicional ---");
            String codigo = leerTexto("Codigo: ");
            String nombre = leerTexto("Nombre: ");
            String descripcion = leerTexto("Descripcion: ");
            double precio = leerDecimal("Precio: ");
            hotel.crearServicio(new ServicioAdicional(codigo, nombre, descripcion, precio, true));
            System.out.println("Servicio creado.");
        }

        private static void leerServiciosUI() {
            System.out.println("--- Listado de servicios ---");
            ArrayList<ServicioAdicional> lista = hotel.listarServicios();
            if (lista.size() == 0) {
                System.out.println("(sin registros)");
                return;
            }
            for (int i = 0; i < lista.size(); i++) {
                System.out.println(lista.get(i));
            }
        }

        private static void actualizarServicioUI() {
            System.out.println("--- Actualizar servicio ---");
            String codigo = leerTexto("Codigo del servicio: ");
            double nuevoPrecio = leerDecimal("Nuevo precio: ");
            boolean ok = hotel.actualizarPrecioServicio(codigo, nuevoPrecio);
            if (ok) {
                System.out.println("Servicio actualizado.");
            } else {
                System.out.println("No se encontro ese servicio.");
            }
        }

        private static void eliminarServicioUI() {
            System.out.println("--- Eliminar servicio ---");
            String codigo = leerTexto("Codigo del servicio a eliminar: ");
            boolean ok = hotel.eliminarServicio(codigo);
            if (ok) {
                System.out.println("Servicio eliminado.");
            } else {
                System.out.println("No se encontro ese servicio.");
            }
        }

        // ========================================================================
        // CRUD Reservas
        // ========================================================================

        private static void menuReservas() {
            int op = -1;
            while (op != 0) {
                System.out.println("\n--- CRUD Reservas ---");
                System.out.println("1. Crear   2. Leer/Listar   3. Actualizar (agregar hab./servicio, cambiar estado)   4. Eliminar   0. Volver");
                op = leerEntero("Opcion: ");

                if (op == 1) {
                    crearReservaUI();
                } else if (op == 2) {
                    leerReservasUI();
                } else if (op == 3) {
                    actualizarReservaUI();
                } else if (op == 4) {
                    eliminarReservaUI();
                } else if (op != 0) {
                    System.out.println("Opcion invalida.");
                }
            }
        }

        // CREATE: crea la reserva "vacia" (sin habitaciones); se completa desde Actualizar.
        private static void crearReservaUI() {
            System.out.println("--- Crear reserva ---");
            if (hotel.listarHuespedes().size() == 0) {
                System.out.println("Primero registra al menos un huesped.");
                return;
            }
            leerHuespedesUI();
            String docHuesped = leerTexto("Documento del huesped: ");
            Huesped huesped = hotel.buscarHuespedPorDocumento(docHuesped);
            if (huesped == null) {
                System.out.println("Huesped no encontrado.");
                return;
            }
            String codigo = leerTexto("Codigo de la reserva: ");
            LocalDate entrada = leerFecha("Fecha de entrada (yyyy-MM-dd): ");
            LocalDate salida = leerFecha("Fecha de salida (yyyy-MM-dd): ");
            String metodoPago = leerMetodoPago();

            Reserva reserva = new Reserva(codigo, huesped, entrada, salida, metodoPago);
            hotel.crearReserva(reserva);
            System.out.println("Reserva creada en estado Pendiente. Usa 'Actualizar' para agregarle habitaciones.");
        }

        private static void leerReservasUI() {
            System.out.println("--- Listado de reservas ---");
            ArrayList<Reserva> lista = hotel.listarReservas();
            if (lista.size() == 0) {
                System.out.println("(sin registros)");
                return;
            }
            for (int i = 0; i < lista.size(); i++) {
                System.out.println(lista.get(i));
            }
        }

        // UPDATE: agrupa las formas de "actualizar" una reserva: agregar
        // habitacion, agregar servicio, confirmar o cambiar de estado.
        private static void actualizarReservaUI() {
            Reserva reserva = seleccionarReserva();
            if (reserva == null) {
                return;
            }

            System.out.println("1. Agregar habitacion  2. Agregar servicio  3. Confirmar  4. Cambiar estado  0. Cancelar");
            int op = leerEntero("Opcion: ");

            if (op == 1) {
                int numero = leerEntero("Numero de habitacion a agregar: ");
                Habitacion habitacion = hotel.buscarHabitacionPorNumero(numero);
                if (habitacion == null) {
                    System.out.println("Habitacion no encontrada.");
                    return;
                }
                boolean ok = reserva.agregarHabitacion(habitacion, hotel.listarReservas());
                if (ok) {
                    System.out.println("Habitacion agregada. Nuevo total: $" + (long) reserva.getValorTotal());
                } else {
                    System.out.println("La habitacion NO esta disponible en esas fechas.");
                }

            } else if (op == 2) {
                leerServiciosUI();
                String codigo = leerTexto("Codigo del servicio a agregar: ");
                ServicioAdicional servicio = hotel.buscarServicioPorCodigo(codigo);
                if (servicio == null) {
                    System.out.println("Servicio no encontrado.");
                    return;
                }
                boolean ok = reserva.agregarServicio(servicio);
                if (ok) {
                    System.out.println("Servicio agregado. Nuevo total: $" + (long) reserva.getValorTotal());
                } else {
                    System.out.println("El servicio no esta disponible.");
                }

            } else if (op == 3) {
                boolean ok = reserva.confirmar();
                if (ok) {
                    System.out.println("Reserva confirmada.");
                } else {
                    System.out.println("Solo se puede confirmar una reserva Pendiente.");
                }

            } else if (op == 4) {
                System.out.println("1. En curso  2. Finalizada  3. Cancelada");
                int estadoOp = leerEntero("Nuevo estado: ");
                String nuevo = null;
                if (estadoOp == 1) {
                    nuevo = Reserva.EN_CURSO;
                } else if (estadoOp == 2) {
                    nuevo = Reserva.FINALIZADA;
                } else if (estadoOp == 3) {
                    nuevo = Reserva.CANCELADA;
                }
                if (nuevo != null) {
                    hotel.actualizarEstadoReserva(reserva.getCodigoReserva(), nuevo);
                    System.out.println("Estado actualizado a " + nuevo + ".");
                }

            } else {
                System.out.println("Operacion cancelada.");
            }
        }

        private static void eliminarReservaUI() {
            System.out.println("--- Eliminar reserva ---");
            String codigo = leerTexto("Codigo de la reserva a eliminar: ");
            boolean ok = hotel.eliminarReserva(codigo);
            if (ok) {
                System.out.println("Reserva eliminada.");
            } else {
                System.out.println("No se pudo eliminar (no existe, o no esta Pendiente/Cancelada).");
            }
        }

        // ========================================================================
        // Consultas especiales
        // ========================================================================

        private static void consultarNumeroPerfecto() {
            System.out.println("\n--- Consultar huesped por telefono / numero perfecto ---");
            String telefono = leerTexto("Telefono a consultar: ");
            Huesped huesped = hotel.buscarHuespedPorTelefono(telefono);
            if (huesped == null) {
                System.out.println("No hay huesped registrado con ese telefono.\n");
                return;
            }
            System.out.println("Huesped: " + huesped);
            boolean esPerfecto = hotel.telefonoEsPerfecto(telefono);
            String respuesta = "NO";
            if (esPerfecto) {
                respuesta = "SI";
            }
            System.out.println("El numero de telefono, es un numero perfecto? " + respuesta + "\n");
        }

        private static void consultarIngresosPorFecha() {
            System.out.println("\n--- Ingresos por fecha ---");
            LocalDate fecha = leerFecha("Fecha de realizacion a consultar (yyyy-MM-dd): ");
            double total = hotel.calcularIngresosPorFecha(fecha);
            System.out.println("Ingresos por reservas realizadas el " + fecha + ": $" + (long) total + "\n");
        }

        // ========================================================================
        // Metodos de apoyo para leer datos por consola
        // ========================================================================

        private static Reserva seleccionarReserva() {
            if (hotel.listarReservas().size() == 0) {
                System.out.println("No hay reservas registradas.");
                return null;
            }
            leerReservasUI();
            String codigo = leerTexto("Codigo de la reserva: ");
            Reserva reserva = hotel.buscarReservaPorCodigo(codigo);
            if (reserva == null) {
                System.out.println("Reserva no encontrada.");
                return null;
            }
            return reserva;
        }

        private static String leerTipoHabitacion() {
            System.out.println("1. Individual  2. Doble  3. Suite");
            int op = leerEntero("Tipo: ");
            if (op == 2) {
                return Habitacion.DOBLE;
            } else if (op == 3) {
                return Habitacion.SUITE;
            }
            return Habitacion.INDIVIDUAL;
        }

        private static String leerMetodoPago() {
            System.out.println("1. Tarjeta de credito  2. Transferencia bancaria  3. Efectivo");
            int op = leerEntero("Metodo de pago: ");
            if (op == 1) {
                return Reserva.TARJETA_CREDITO;
            } else if (op == 2) {
                return Reserva.TRANSFERENCIA_BANCARIA;
            }
            return Reserva.EFECTIVO;
        }

        private static String leerTexto(String mensaje) {
            System.out.print(mensaje);
            return sc.nextLine().trim();
        }

        private static int leerEntero(String mensaje) {
            while (true) {
                try {
                    System.out.print(mensaje);
                    return Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Ingresa un numero valido.");
                }
            }
        }

        private static double leerDecimal(String mensaje) {
            while (true) {
                try {
                    System.out.print(mensaje);
                    return Double.parseDouble(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Ingresa un valor numerico valido.");
                }
            }
        }

        private static LocalDate leerFecha(String mensaje) {
            while (true) {
                try {
                    System.out.print(mensaje);
                    return LocalDate.parse(sc.nextLine().trim());
                } catch (Exception e) {
                    System.out.println("Formato invalido. Usa yyyy-MM-dd (ej: 2026-09-23).");
                }
            }
        }

        // Carga datos de ejemplo al iniciar el programa, para que el menu tenga
        // informacion inmediata con la cual probar el CRUD.
        private static void precargarDatosDeEjemplo() {
            Huesped h1 = new Huesped("Alejandro Morales", "1094123456", "6", "alejo@correo.com", "Colombia");
            Huesped h2 = new Huesped("Maria Perez", "1098765432", "28", "maria@correo.com", "Colombia");
            hotel.crearHuesped(h1);
            hotel.crearHuesped(h2);

            hotel.crearHabitacion(new Habitacion(101, 1, Habitacion.INDIVIDUAL, 1, 120000));
            hotel.crearHabitacion(new Habitacion(102, 1, Habitacion.DOBLE, 2, 180000));
            hotel.crearHabitacion(new Habitacion(201, 2, Habitacion.SUITE, 4, 350000));

            hotel.crearServicio(new ServicioAdicional("SRV01", "Restaurante", "Servicio de comidas", 45000, true));
            hotel.crearServicio(new ServicioAdicional("SRV02", "Lavanderia", "Lavado y planchado", 25000, true));
            hotel.crearServicio(new ServicioAdicional("SRV03", "Transporte", "Traslado aeropuerto-hotel", 60000, true));

            System.out.println("Datos de ejemplo cargados (huespedes 1094123456/1098765432, habitaciones 101/102/201, servicios SRV01-03).");
            System.out.println("Tip: el telefono '6' es un numero perfecto (1+2+3=6) - pruebalo en la opcion 5.\n");
        }
    }

