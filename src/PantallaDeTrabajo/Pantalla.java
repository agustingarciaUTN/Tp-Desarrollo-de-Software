package PantallaDeTrabajo;

import Huesped.*;
import enums.PosIva;
import enums.TipoDocumento;
import Usuario.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Pantalla {

    private GestorHuesped gestorHuesped;
    private final Scanner scanner;//para la entrada por teclado
    private GestorUsuario gestorUsuario;
    private boolean usuarioAutenticado;
    private String nombreUsuarioActual;


    //constructor (hay que ver como lo vamos a llamar)
    public Pantalla() {
        //inicializamos el gestor huesped
        DaoHuespedInterfaz daoHuesped = new DaoHuesped();
        DaoDireccionInterfaz daoDireccion = new DaoDireccion();
        this.gestorHuesped = new GestorHuesped(daoHuesped, daoDireccion);

        //inicializamos el gestor usuario
        DaoUsuarioInterfaz daoUsuario = new DaoUsuario();
        this.gestorUsuario = new GestorUsuario(daoUsuario);

        //inicializamos el scanner
        this.scanner = new Scanner(System.in);
        this.usuarioAutenticado = false;
        this.nombreUsuarioActual = "";
    }

    //METODO PRINCIPAL PARA INICIAR EL SISTEMA
    public void iniciarSistema() {
        System.out.println("========================================");
        System.out.println("   SISTEMA DE GESTION HOTELERA");
        System.out.println("========================================\n");

        //Primero autenticar
        if (autenticarUsuario()) {
            //Si la autenticacion es exitosa, mostrar menu principal
            mostrarMenuPrincipal();
        } else {
            System.out.println("No se pudo acceder al sistema.");
        }

        System.out.println("\n========================================");
        System.out.println("   FIN DEL SISTEMA");
        System.out.println("========================================");
    }

    //METODO PARA CU AUTENTICAR USUARIO
    private boolean autenticarUsuario() {
        System.out.println("-- AUTENTICACION DE USUARIO --\n");

        boolean autenticacionExitosa = false;

        while (!autenticacionExitosa) {
            //Paso 2: El sistema presenta la pantalla para autenticar al usuario
            System.out.println("Por favor, ingrese sus credenciales:");

            //Paso 3: El actor ingresa su nombre (en forma visible) y su contraseña (oculta)
            System.out.print("Nombre de usuario: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Contraseña: ");
            String contrasenia = scanner.nextLine(); //en consola no se puede ocultar realmente

            //Validar con el gestor
            boolean credencialesValidas = gestorUsuario.autenticarUsuario(nombre, contrasenia);

            if (credencialesValidas) {
                //Autenticacion exitosa
                this.usuarioAutenticado = true;
                this.nombreUsuarioActual = nombre;
                System.out.println("\n¡Autenticación exitosa! Bienvenido, " + nombre + "\n");
                autenticacionExitosa = true;
            } else {
                //Paso 3.A: El usuario o la contraseña son inválidos
                //Paso 3.A.1: El sistema muestra el mensaje de error
                System.out.println("\n*** ERROR ***");
                System.out.println("El usuario o la contraseña no son válidos");
                System.out.println("*************\n");

                //Paso 3.A.2: El actor cierra la pantalla de error
                System.out.print("Presione ENTER para continuar...");
                System.out.print("\033[H\033" +
                        "[2J");
                System.out.flush();
                scanner.nextLine();

                //Paso 3.A.3: El sistema blanquea los campos (se hace automaticamente al repetir el ciclo)

                //Preguntar qué desea hacer
                System.out.println("\n¿Qué desea hacer?");
                System.out.println("1. Volver a ingresar credenciales");
                System.out.println("2. Cerrar el sistema");
                System.out.print("Ingrese una opción: ");

                int opcion = -1;
                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); //consumir salto de linea
                } catch (Exception e) {
                    scanner.nextLine(); //limpiar buffer
                    System.out.println("\nOpción inválida. Intente nuevamente.\n");
                    continue;
                }

                if (opcion == 2) {
                    System.out.println("\nCerrando el sistema...");
                    return false; //Sale sin autenticar
                } else if (opcion == 1) {
                    System.out.println("\n-- Intente nuevamente --\n");
                    //Paso 3.A.4: El CU continua en el paso 2 (se repite el while)
                } else {
                    System.out.println("\nOpción inválida. Intente nuevamente.\n");
                }
            }
        }

        return true;
    }

    //METODO PARA MOSTRAR MENU PRINCIPAL
    private void mostrarMenuPrincipal() {
        //Paso 4: El sistema presenta la pantalla principal
        boolean salir = false;

        while (!salir && usuarioAutenticado) {
            System.out.println("========================================");
            System.out.println("        MENU PRINCIPAL");
            System.out.println("========================================");
            System.out.println("Usuario: " + nombreUsuarioActual);
            System.out.println("----------------------------------------");
            System.out.println("1. Buscar huesped (CU2)");
            System.out.println("2. Dar de alta huesped (CU9)");
            System.out.println("3. Dar de baja huesped (CU11) ");
            System.out.println("4. Modificar Huesped");
            System.out.println("5. Cerrar sesión");
            System.out.println("========================================");
            System.out.print("Ingrese una opción: ");

            int opcion = -1;
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); //consumir salto de linea
            } catch (Exception e) {
                scanner.nextLine(); //limpiar buffer
                System.out.println("\nOpción inválida. Intente nuevamente.\n");
                continue;
            }

            System.out.println();

            switch (opcion) {
                case 1:
                    iniciarBusquedaHuesped();
                    break;
                case 2:
                    // iniciarAltaHuesped();
                    break;
                case 3:
                    iniciarBajaHuesped();
                    break;
                case 4:
                    System.out.println("Funcionalidad en desarrollo...\n");
                    pausa();
                    break;
                case 5:
                    System.out.println("Funcionalidad en desarrollo...\n");
                    pausa();
                    break;
                case 6:
                    System.out.print("¿Está seguro que desea cerrar sesión? (SI/NO): ");
                    String confirmar = scanner.nextLine().trim();
                    if (confirmar.equalsIgnoreCase("SI")) {
                        System.out.println("\nCerrando sesión...\n");
                        salir = true;
                        usuarioAutenticado = false;
                    }
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.\n");
            }
        }
        //Paso 5: El CU termina
    }

    //METODO AUXILIAR PARA PAUSAR
    public void pausa() {
        System.out.print("Presione ENTER para continuar...");
        scanner.nextLine();
        System.out.println();
    }

    public void iniciarBusquedaHuesped() {
        System.out.println("========================================");
        System.out.println("        BÚSQUEDA DE HUÉSPED 🔎");
        System.out.println("========================================");

        DtoHuesped datos = leerCriteriosDeBusqueda();
        ArrayList<DtoHuesped> huespedesEncontrados = gestorHuesped.buscarHuesped(datos);

        if (huespedesEncontrados.isEmpty()) {
            System.out.println("\nNo se encontraron huéspedes con los criterios especificados.");
            System.out.print("¿Desea dar de alta un nuevo huésped? (SI/NO): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("SI")) {
                System.out.println("llegamos a seguir dando de alta otro huesped");//this.iniciarAltaHuesped();
            }
        } else {
            this.seleccionarHuespedDeLista(huespedesEncontrados);
        }
        pausa();
    }

    private DtoHuesped leerCriteriosDeBusqueda() {
        DtoHuesped criterios = new DtoHuesped();
        System.out.println("Ingrese uno o más criterios (presione ENTER para omitir).");

        // VALIDACIÓN DE APELLIDO
        while (true) {
            System.out.print("Apellido que comience con: ");
            String apellido = scanner.nextLine().trim();

            if (apellido.isEmpty()) {
                break; // Usuario omite este criterio
            }

            // Validar longitud
            if (apellido.length() < 2) {
                System.out.println("⚠ El apellido debe tener al menos 2 caracteres. Intente nuevamente.");
                continue;
            }

            if (apellido.length() > 100) {
                System.out.println("⚠ El apellido no puede exceder los 100 caracteres. Intente nuevamente.");
                continue;
            }

            // Validar que solo contenga letras, espacios y caracteres válidos (á, é, í, ó, ú, ñ)
            if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                System.out.println("⚠ El apellido solo puede contener letras y espacios. Intente nuevamente.");
                continue;
            }

            criterios.setApellido(apellido);
            break;
        }

        // VALIDACIÓN DE NOMBRES
        while (true) {
            System.out.print("Nombres que comiencen con: ");
            String nombres = scanner.nextLine().trim();

            if (nombres.isEmpty()) {
                break; // Usuario omite este criterio
            }

            // Validar longitud
            if (nombres.length() < 2) {
                System.out.println("⚠ Los nombres deben tener al menos 2 caracteres. Intente nuevamente.");
                continue;
            }

            if (nombres.length() > 100) {
                System.out.println("⚠ Los nombres no pueden exceder los 100 caracteres. Intente nuevamente.");
                continue;
            }

            // Validar que solo contenga letras, espacios y caracteres válidos
            if (!nombres.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                System.out.println("⚠ Los nombres solo pueden contener letras y espacios. Intente nuevamente.");
                continue;
            }

            criterios.setNombres(nombres);
            break;
        }

        // VALIDACIÓN DE TIPO DE DOCUMENTO (sin cambios, ya está bien)
        criterios.setTipoDocumento(validarYLeerTipoDocumento());

        // VALIDACIÓN DE NÚMERO DE DOCUMENTO
        if (criterios.getTipoDocumento() != null) {
            criterios.setDocumento(validarYLeerNumeroDocumento(criterios.getTipoDocumento()));
        }

        return criterios;
    }


    private TipoDocumento validarYLeerTipoDocumento() {
        while (true) {
            System.out.print("Tipo de Documento (DNI, Pasaporte, Libreta de Enrolamiento (LE), Libreta Civica(LC)): ");
            String tipoStr = scanner.nextLine().trim().toUpperCase();
            if (tipoStr.isEmpty()) {
                return null; // El usuario omitió este criterio.
            }
            try {
                return TipoDocumento.valueOf(tipoStr); // Intenta convertir el String al enum.
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error: Tipo de documento no válido. Los valores posibles son DNI, PASAPORTE, Libreta de Enrolamiento, Libreta Civica.");
            }
        }
    }

    private long validarYLeerNumeroDocumento(TipoDocumento tipoDoc) {
        while (true) {
            System.out.print("Número de Documento: ");
            String numeroStr = scanner.nextLine().trim();

            if (numeroStr.isEmpty()) {
                return 0; // Se devuelve 0 si se omite
            }

            try {
                long numero = Long.parseLong(numeroStr);

                // VALIDACIÓN DE RANGO SEGÚN TIPO DE DOCUMENTO
                if (tipoDoc == TipoDocumento.DNI) {
                    if (numero < 0 || numero > 99999999) {
                        System.out.println("⚠ El DNI debe estar entre 0 y 99.999.999. Intente nuevamente.");
                        continue;
                    }
                } else if (tipoDoc == TipoDocumento.LE || tipoDoc == TipoDocumento.LC) {
                    if (numero < 0 || numero > 99999999) {
                        System.out.println("⚠ La " + tipoDoc.name() + " debe estar entre 0 y 99.999.999. Intente nuevamente.");
                        continue;
                    }
                } else if (tipoDoc == TipoDocumento.PASAPORTE) {
                    if (numero <= 0) {
                        System.out.println("⚠ El número de pasaporte debe ser mayor a 0. Intente nuevamente.");
                        continue;
                    }
                }

                return numero;

            } catch (NumberFormatException e) {
                System.out.println("⚠ El número de documento debe ser un valor numérico. Intente nuevamente.");
            }
        }
    }

    private void seleccionarHuespedDeLista(List<DtoHuesped> huespedes) {
        mostrarListaHuespedes(huespedes);
        System.out.print("Ingrese el ID del huésped para modificar, o 0 para dar de alta uno nuevo: ");
        int seleccion = leerOpcionNumerica();

        if (seleccion > 0 && seleccion <= huespedes.size()) {
            DtoHuesped huespedSeleccionado = huespedes.get(seleccion - 1);
            System.out.println("llegamos a modificar el huesped");//this.iniciarModificacionHuesped(huespedSeleccionado);
        } else {
            System.out.println("llegamos a seguir dando de alta otro huesped");//this.iniciarAltaHuesped();
        }
    }

    private void mostrarListaHuespedes(List<DtoHuesped> huespedes) {
        System.out.println("\n-- Huéspedes Encontrados --");
        System.out.printf("%-5s %-20s %-20s %s%n", "ID", "APELLIDO", "NOMBRES", "DOCUMENTO");
        System.out.println("-----------------------------------------------------------------");
        for (int i = 0; i < huespedes.size(); i++) {
            DtoHuesped h = huespedes.get(i);
            String docCompleto = (h.getTipoDocumento() != null ? h.getTipoDocumento().name() : "N/A") + " " + h.getDocumento();
            System.out.printf("[%d]   %-20s %-20s %s%n", i + 1, h.getApellido(), h.getNombres(), docCompleto);
        }
        System.out.println("-----------------------------------------------------------------");
    }

    private int leerOpcionNumerica() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            return -1; // Devuelve un valor inválido si el usuario no ingresa un número
        } finally {
            scanner.nextLine(); // Limpia el buffer del scanner
        }
    }


    public void iniciarBajaHuesped() {
        System.out.println("\n========================================");
        System.out.println("   CU11: DAR DE BAJA HUÉSPED");
        System.out.println("========================================\n");

        // Paso 1: Buscar el huésped a eliminar
        System.out.println("Primero debe buscar el huésped que desea eliminar.\n");

        DtoHuesped criterios = leerCriteriosDeBusqueda();
        ArrayList<DtoHuesped> huespedesEncontrados = gestorHuesped.buscarHuesped(criterios);

        if (huespedesEncontrados.isEmpty()) {
            System.out.println("\nNo se encontraron huéspedes con los criterios especificados.");
            System.out.println("No hay huéspedes para eliminar.\n");
            pausa();
            return; // Termina el CU
        }

        // Mostrar lista de huéspedes encontrados
        mostrarListaHuespedes(huespedesEncontrados);

        System.out.print("\nIngrese el número del huésped que desea eliminar (0 para cancelar): ");
        int seleccion = leerOpcionNumerica();

        if (seleccion == 0) {
            System.out.println("Eliminación cancelada.\n");
            return; // Termina el CU
        }

        if (seleccion < 1 || seleccion > huespedesEncontrados.size()) {
            System.out.println("Selección inválida. Eliminación cancelada.\n");
            pausa();
            return; // Termina el CU
        }

        // Huésped seleccionado
        DtoHuesped huespedSeleccionado = huespedesEncontrados.get(seleccion - 1);

        // Paso 2: Validar si el huésped puede ser eliminado
        String tipoDoc = huespedSeleccionado.getTipoDocumento().name();
        long nroDoc = huespedSeleccionado.getDocumento();

        boolean puedeEliminar = gestorHuesped.puedeEliminarHuesped(tipoDoc, nroDoc);

        if (!puedeEliminar) {
            // Flujo Alternativo 2.A: El huésped se alojó alguna vez
            System.out.println("\n*** NO SE PUEDE ELIMINAR ***");
            System.out.println("El huésped se ha alojado en el hotel en alguna oportunidad.");
            System.out.println("Por razones de auditoría, el huésped NO puede ser eliminado del sistema.");
            System.out.println("*****************************\n");

            pausa();
            return; // Termina el CU (Flujo Alternativo 2.A.1)
        }

        // Paso 2 (continuación): El huésped NUNCA se alojó, se puede eliminar
        System.out.println("\nLos datos del huésped que será eliminado son:");
        System.out.println("----------------------------------------");
        System.out.println("Nombre:    " + huespedSeleccionado.getNombres());
        System.out.println("Apellido:  " + huespedSeleccionado.getApellido());
        System.out.println("Documento: " + huespedSeleccionado.getTipoDocumento().name() + " " + huespedSeleccionado.getDocumento());
        System.out.println("----------------------------------------\n");

        System.out.println("¿Está seguro que desea ELIMINAR este huésped?");
        System.out.println("1. ELIMINAR");
        System.out.println("2. CANCELAR");
        System.out.print("Ingrese una opción: ");

        int opcion = leerOpcionNumerica();

        if (opcion == 1) {
            // Paso 3: El actor presiona "ELIMINAR"
            System.out.println("\nEliminando huésped...");

            boolean eliminado = gestorHuesped.eliminarHuesped(tipoDoc, nroDoc);

            if (eliminado) {
                // Éxito
                System.out.println("\n*** ELIMINACIÓN EXITOSA ***");
                System.out.println("Los datos del huésped " + huespedSeleccionado.getNombres() + " " + huespedSeleccionado.getApellido());
                System.out.println("(" + huespedSeleccionado.getTipoDocumento().name() + " " + huespedSeleccionado.getDocumento() + ")");
                System.out.println("han sido eliminados del sistema.");
                System.out.println("***************************\n");
            } else {
                // Error
                System.out.println("\n*** ERROR ***");
                System.out.println("No se pudo eliminar el huésped.");
                System.out.println("Intente nuevamente o contacte al administrador.");
                System.out.println("*************\n");
            }

        } else if (opcion == 2) {
            // Flujo Alternativo 3.A: El actor presiona "CANCELAR"
            System.out.println("\nEliminación cancelada.\n");
        } else {
            System.out.println("\nOpción inválida. Eliminación cancelada.\n");
        }

        // Paso 4: El actor presiona cualquier tecla
        pausa();

        System.out.println("========================================");
        System.out.println("   FIN CU11: DAR DE BAJA HUÉSPED");
        System.out.println("========================================\n");
    }
    // Paso 5: El CU termina
}
