package PantallaDeTrabajo;

//import Huesped.*;
import Usuario.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Huesped.DtoHuesped;

public class Pantalla {

    // private GestorHuesped gestorHuesped;
    private GestorUsuario gestorUsuario;
    private final Scanner scanner;//para la entrada por teclado
    private boolean usuarioAutenticado;
    private String nombreUsuarioActual;

    //constructor (hay que ver como lo vamos a llamar)
    public Pantalla(){
        //inicializamos el gestor huesped
        // DaoHuespedInterfaz daoHuesped = new DaoHuesped();
        // DaoDireccionInterfaz daoDireccion = new DaoDireccion();
        //this.gestorHuesped = new GestorHuesped(daoHuesped, daoDireccion);

        //inicializamos el gestor usuario
        DaoUsuarioInterfaz daoUsuario = new DaoUsuario();
        this.gestorUsuario = new GestorUsuario(daoUsuario);

        //inicializamos el scanner
        this.scanner = new Scanner(System.in);
        this.usuarioAutenticado = false;
        this.nombreUsuarioActual = "";
    }

    //METODO PRINCIPAL PARA INICIAR EL SISTEMA
    public void iniciarSistema(){
        System.out.println("========================================");
        System.out.println("   SISTEMA DE GESTION HOTELERA");
        System.out.println("========================================\n");

        //Primero autenticar
        if(autenticarUsuario()){
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
    private boolean autenticarUsuario(){
        System.out.println("-- AUTENTICACION DE USUARIO --\n");

        boolean autenticacionExitosa = false;

        while(!autenticacionExitosa){
            //Paso 2: El sistema presenta la pantalla para autenticar al usuario
            System.out.println("Por favor, ingrese sus credenciales:");

            //Paso 3: El actor ingresa su nombre (en forma visible) y su contraseña (oculta)
            System.out.print("Nombre de usuario: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Contraseña: ");
            String contrasenia = scanner.nextLine(); //en consola no se puede ocultar realmente

            //Validar con el gestor
            boolean credencialesValidas = gestorUsuario.autenticarUsuario(nombre, contrasenia);

            if(credencialesValidas){
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

                if(opcion == 2){
                    System.out.println("\nCerrando el sistema...");
                    return false; //Sale sin autenticar
                } else if(opcion == 1){
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
    private void mostrarMenuPrincipal(){
        //Paso 4: El sistema presenta la pantalla principal
        boolean salir = false;

        while(!salir && usuarioAutenticado){
            System.out.println("========================================");
            System.out.println("        MENU PRINCIPAL");
            System.out.println("========================================");
            System.out.println("Usuario: " + nombreUsuarioActual);
            System.out.println("----------------------------------------");
            System.out.println("1. Dar de alta huesped (CU9)");
            System.out.println("2. Gestionar reservas");
            System.out.println("3. Gestionar habitaciones");
            System.out.println("4. Gestionar servicios");
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

            switch(opcion){
                case 1:
                    // iniciarAltaHuesped();
                    break;
                case 2:
                    System.out.println("Funcionalidad en desarrollo...\n");
                    pausa();
                    break;
                case 3:
                    System.out.println("Funcionalidad en desarrollo...\n");
                    pausa();
                    break;
                case 4:
                    System.out.println("Funcionalidad en desarrollo...\n");
                    pausa();
                    break;
                case 5:
                    System.out.print("¿Está seguro que desea cerrar sesión? (SI/NO): ");
                    String confirmar = scanner.nextLine().trim();
                    if(confirmar.equalsIgnoreCase("SI")){
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
    private void pausa(){
        System.out.print("Presione ENTER para continuar...");
        scanner.nextLine();
        System.out.println();
    }

  /*  //METODO PARA CU9 DAR DE ALTA HUESPED
    public void iniciarAltaHuesped(){//este metodo debe tener el mismo nombre que el CU?

        //no se si es necesario, despues habra que ver la parte estetica
        System.out.println("========================================");
        System.out.println("-- CU9: DAR DE ALTA HUESPED --");
        System.out.println("========================================\n");

        boolean continuarCargando = true;//bandera

        while(continuarCargando) {
            //metodo para pedir datos
            DtoHuesped datosIngresados = mostrarYPedirDatosFormulario();

            System.out.println("\nAcciones: 1 = SIGUIENTE, 2 = CANCELAR");
            System.out.print("Ingrese una opcion: ");
            int opcionBoton = scanner.nextInt();
            scanner.nextLine();//para consumir el salto de linea

            if (opcionBoton == 1) {//presiono SIGUIENTE
                System.out.println("Procesando datos...\n");


                //aca hay que llamar al gestor para que valide los datos
                //List<String> errores = gestorHuesped.validarDatos(datosIngresados);
                List<String> errores = new ArrayList<>();

                if(!errores.isEmpty()){
                    System.out.println("*** ERROR: Se encontraron los siguientes errores: ***");
                    for(String error : errores){
                        System.out.println("- " + error);
                    }
                    System.out.println("Por favor, ingrese los datos nuevamente\n");
                    pausa();
                    continue; //fuerza al inicio del while
                }

                System.out.println("El huesped '" + datosIngresados.getNombres() + " " + datosIngresados.getApellido() +"' ha sido satisfactoriamente cargado al sistema.");

                System.out.print("\n¿Desea cargar otro huesped? (SI/NO): ");

                //validacion de ingreso correcto
                String ingresoOtroHuesped = scanner.nextLine().trim();
                while (!ingresoOtroHuesped.equalsIgnoreCase("NO") && !ingresoOtroHuesped.equalsIgnoreCase("SI")) {
                    System.out.print("Ingreso invalido. ¿Desea cargar otro huesped? (SI/NO): ");
                    ingresoOtroHuesped = scanner.nextLine().trim();
                }

                //si ingreso NO termina el bucle, si ingreso SI se repite
                if (ingresoOtroHuesped.equalsIgnoreCase("NO")) {
                    continuarCargando = false;
                }
                System.out.println();

            } else if (opcionBoton == 2) {//presiono CANCELAR
                System.out.print("\n¿Desea cancelar el alta del huesped? (SI/NO): ");

                //validacion de ingreso correcto (capaz esto puede ser una funcion aparte, habria que ver como manejar los mensajes distintos)
                String ingresoCancelarAlta = scanner.nextLine().trim();
                while (!ingresoCancelarAlta.equalsIgnoreCase("NO") && !ingresoCancelarAlta.equalsIgnoreCase("SI")) {
                    System.out.print("Ingreso invalido. ¿Desea cancelar el alta de huesped? (SI/NO): ");
                    ingresoCancelarAlta = scanner.nextLine().trim();
                }

                if (ingresoCancelarAlta.equalsIgnoreCase("SI")) {
                    System.out.println("\nAlta cancelada.\n");
                    continuarCargando = false;//termina el bucle
                } else {
                    System.out.println();
                }
                //si ingresa NO, el bucle se repite y vuelve a pedir los datos (no se si esta bien que tenga que ingresar todo de 0)
            } else {
                System.out.println("Ingreso invalido. Intente nuevamente.\n");
            }
        }//fin while

        System.out.println("========================================");
        System.out.println("-- FIN CU9: DAR DE ALTA HUESPED --");
        System.out.println("========================================\n");
        pausa();
    }//fin iniciarAltaHuesped

    //metodo privado para pedir los datos del huesped a ingresar
    private DtoHuesped mostrarYPedirDatosFormulario(){
        System.out.println("--- FORMULARIO DE DATOS DEL HUESPED ---");
        System.out.println("(Ingrese los datos solicitados)\n");

        //Aqui iria la logica para pedir todos los datos del huesped
        //Por ahora retorna un DTO vacio

        return new DtoHuesped();
    }*/















    //logica de la pantalla
    //Interfaz
    //Llamar gestores, y sus caso de uso


    //recordar que existen distintos tipo de habtiacion
    //queremos especificar eso? diria que si pq en diseno lo van a pedir

    //recordad que limite de habitaciones es 48
    //Tipo | Cantidad de habitaciones | Costo x noche
    //Individual Estándar | 10 | $ 50.800
    //Doble Estándar | 18 | $ 70.230
    //Doble Superior | 8 | $ 90.560
    //Superior Family Plan | 10 | $ 110.500
    //Suite Doble | 2 | $ 128.600

    //Tener en cuenta
    //1. El que hace la reserva tiene que tener 18 o mas anos
    //2. Existe un descuento especial si la reserva es por mas de x cantidad de noche, variable por habitacion.
    //3. Si factura un 3ero, se piden datos y debe ser persona juridica.
    //4. Se debe dar la posibilidad de que nos digan si quieren facturar los consumos aparte, en otra factura.
    //5. La factura se tiene que saldar al abandonar la habitacion
    //6. Las facturas no pueden re imprimirse, ni anularse sino que se generarán las notas de crédito pertinente para subsanar los problemas contables
    //7. El check in se hace a partir de las 12
    //8, El check out se hace a las 10 hasta las 11
    //9. Se permite permanencia en el hotel por un costo extra del 50% a la habitacion.
    //10. A las 18 los rajamos o les hacemos una nueva reserva, pde ser en las mismas o diff habitaciones.

    //Servicios disponibles, Lavado y Planchado, Sauna y Bar.




    private void iniciarModificacionHuesped(DtoHuesped dtoHuesped){ //Paso1 CU10
    boolean salir = false;
        while(!salir){
            
            System.out.println("========================================");

            mostrarDatosHuesped(dtoHuesped);

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

            switch(opcion){
                case 1:
                    // cambiar apellido;
                    clearScreen();
                    break;
                case 2:
                   // cambiar nombre;
                   clearScreen();
                    break;
                case 3:
                    // cambiar tipo documento;
                    clearScreen();
                    break;
                case 4:
                   // cambiar numero documento;
                   clearScreen();
                    break;
                case 5:
                    // cambiar cuit;
                    clearScreen();
                    break;
                case 6:
                    // cambiar posicion iva;
                    clearScreen();
                    break;
                case 7:
                    // cambiar fecha nacimiento;   
                    clearScreen();
                    break;
                case 8:
                    // cambiar direccion;
                    clearScreen();
                    break;
                case 9:
                    // cambiar telefono;
                    clearScreen();
                    break;
                case 10:
                    // cambiar email;
                    clearScreen();
                    break;
                case 11:
                    // cambiar ocupacion;
                    clearScreen();
                    break;
                case 12:
                    // cambiar nacionalidad;
                    clearScreen();
                    break;
                 case 13: 
                    // Al pulsar SIGUIENTE validamos omisiones. Si hay errores, no salimos. Paso2 CU10
                    if (pulsarSiguiente(dtoHuesped)) {
                        salir = true;
                    } else {
                        // quedarse en la pantalla para que el actor corrija
                        salir = false;
                    }
                    break;
                case 14:
                    //cancelar
                    salir = true;
                    break;
                case 15:
                    //borrar
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.\n");
            }
        }
    }
    private boolean pulsarSiguiente(DtoHuesped dtoHuesped){ //Paso2.A` CU10
        List<String> errores = new ArrayList<>();

        // helper lambda para pruebas de "blank"
        java.util.function.Predicate<Object> isBlank = o -> {
            if (o == null) return true;
            if (o instanceof String) return ((String)o).trim().isEmpty();
            if (o instanceof Number) return ((Number)o).longValue() == 0;
            return false;
        };

        if (isBlank.test(dtoHuesped.getApellido())) errores.add("Apellido");
        if (isBlank.test(dtoHuesped.getNombres())) errores.add("Nombre");
        if (isBlank.test(dtoHuesped.getTipoDocumento())) errores.add("Tipo de documento");
        if (isBlank.test(dtoHuesped.getDocumento())) errores.add("Número de documento");
         if (isBlank.test(dtoHuesped.getPosicionIva())) {
           // setear pos iva como consumidor final (valor por defecto)
        }
        if (isBlank.test(dtoHuesped.getFechaNacimiento())) errores.add("Fecha de nacimiento");
        // validar direccion (no implementado aun)
        if (isBlank.test(dtoHuesped.getTelefono())) errores.add("Teléfono");
        if (isBlank.test(dtoHuesped.getOcupacion())) errores.add("Ocupación");
        if (isBlank.test(dtoHuesped.getNacionalidad())) errores.add("Nacionalidad");

        if (!errores.isEmpty()){ //Paso2.A.1 CU10
            System.out.println("\n*** ERROR: Faltan los siguientes datos obligatorios (omisiones): ***");
            for (String e : errores){
                System.out.println("- " + e);
            }
            System.out.println("Por favor complete los campos indicados. Los campos no se han blanqueado.\n");
            pausa(); 
            return false; //Paso2.A.2 CU10
        }

        // No hay omisiones -> puede continuar
        return true;
    }
    private void mostrarDatosHuesped(DtoHuesped dtoHuesped){
        System.out.println("---- DATOS DEL HUESPED ----");
        System.out.println("1. Apellido: " + dtoHuesped.getApellido());
        System.out.println("2. Nombre: " + dtoHuesped.getNombres());
        System.out.println("3. Tipo de documento: " + dtoHuesped.getTipoDocumento());
        System.out.println("4. Número de documento: " + dtoHuesped.getDocumento());
        System.out.println("5. CUIT: " + dtoHuesped.getCuit());
        System.out.println("6. Posición IVA: " + dtoHuesped.getPosicionIva());
        System.out.println("7. Fecha de nacimiento: " + dtoHuesped.getFechaNacimiento());
       // mostrarDireccionHuesped(dtoHuesped); // ns como c hace esto tdv
        System.out.println("9. Teléfono: " + dtoHuesped.getTelefono());
        System.out.println("10. Email: " + dtoHuesped.getEmail());
        System.out.println("11. Ocupación: " + dtoHuesped.getOcupacion());
        System.out.println("12. Nacionalidad: " + dtoHuesped.getNacionalidad());
        System.out.println("13. SIGUIENTE");
        System.out.println("14. CANCELAR");
        System.out.println("15. BORRAR HUESPED");
        System.out.println("---------------------------\n");
    }

    public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
}

    /*mostrarDireccionHuesped(DtoHuesped dtoHuesped){
        System.out.println("---- DIRECCIÓN DEL HUESPED ----");
        System.out.println("Calle: " + dtoHuesped.getDireccion().getCalle());
        System.out.println("Número: " + dtoHuesped.getDireccion().getNumero());
        System.out.println("Piso: " + dtoHuesped.getDireccion().getPiso());
        System.out.println("Departamento: " + dtoHuesped.getDireccion().getDepartamento());
        System.out.println("Ciudad: " + dtoHuesped.getDireccion().getCiudad());
        System.out.println("Provincia: " + dtoHuesped.getDireccion().getProvincia());
        System.out.println("Código Postal: " + dtoHuesped.getDireccion().getCodigoPostal());
        System.out.println("-------------------------------\n");
    } */


} 