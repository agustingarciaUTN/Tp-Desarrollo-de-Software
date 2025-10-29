package PantallaDeTrabajo;

import Huesped.*;
import enums.PosIva;
import enums.TipoDocumento;
import Usuario.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Pantalla {

    private GestorHuesped gestorHuesped;
    private final Scanner scanner;//para la entrada por teclado
    private GestorUsuario gestorUsuario;
    private boolean usuarioAutenticado;
    private String nombreUsuarioActual;


    //constructor (hay que ver como lo vamos a llamar)
    public Pantalla(){
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
            System.out.println("1. Buscar huesped (CU2)");
            System.out.println("2. Dar de alta huesped (CU9)");
            System.out.println("3. Dar de baja huesped ");
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

            switch(opcion){
                case 1:
                    iniciarBusquedaHuesped();
                    break;
                case 2:
                    // iniciarAltaHuesped();
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
                    System.out.println("Funcionalidad en desarrollo...\n");
                    pausa();
                    break;
                case 6:
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
                //this.iniciarAltaHuesped();
            }
        } else {
            this.seleccionarHuespedDeLista(huespedesEncontrados);
        }
        pausa();
    }
    
    private DtoHuesped leerCriteriosDeBusqueda() {
        DtoHuesped criterios = new DtoHuesped();
        System.out.println("Ingrese uno o más criterios (presione ENTER para omitir).");
        System.out.print("Apellido que comience con: ");
        criterios.setApellido(scanner.nextLine().trim());
        System.out.print("Nombres que comiencen con: ");
        criterios.setNombres(scanner.nextLine().trim());
        criterios.setTipoDocumento(validarYLeerTipoDocumento());
        if (criterios.getTipoDocumento() != null) {
            criterios.setDocumento(validarYLeerNumeroDocumento());
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
    
    private long validarYLeerNumeroDocumento() {
        while (true) {
            System.out.print("Número de Documento: ");
            String numeroStr = scanner.nextLine().trim();
            if (numeroStr.isEmpty()) {
                return 0; // Se devuelve 0 si se omite, el gestor lo ignorará.
            }
            try {
                return Long.parseLong(numeroStr); // Intenta convertir el String a long.
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: El número de documento debe ser un valor numérico. Intente de nuevo.");
            }
        }
    }
    
    private void seleccionarHuespedDeLista(List<DtoHuesped> huespedes) {
        mostrarListaHuespedes(huespedes);
        System.out.print("Ingrese el ID del huésped para modificar, o 0 para dar de alta uno nuevo: ");
        int seleccion = leerOpcionNumerica();

        if (seleccion > 0 && seleccion <= huespedes.size()) {
            DtoHuesped huespedSeleccionado = huespedes.get(seleccion - 1);
            this.iniciarModificacionHuesped(huespedSeleccionado);
        } else {
           // this.iniciarAltaHuesped();
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

                System.out.print("\n¿Desea cancelar el alta del huesped? (SI/NO): ");

        //Aqui iria la logica para pedir todos los datos del huesped
        //Por ahora retorna un DTO vacio















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



}
        System.out.println("Nombres: ");
        String nombres = scanner.nextLine();

        System.out.println("Tipo de Documento (DNI, LE, LC, PASAPORTE, OTRO): ");//poner todos en mayuscula facilita la validacion
        String tipoDocStr = scanner.nextLine().toUpperCase(); // Convertir a mayúsculas
        TipoDocumento tipoDocumento = null;
        try {
            tipoDocumento = TipoDocumento.valueOf(tipoDocStr); // Convierte String a Enum
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Tipo de documento inválido.");
            // Manejar el error, volver a pedir
            //ACA HAY QUE VER COMO MANEJAR QUE INGRESA MAL. NO SE SI ESTA BIEN VERIFICARLO ACA PERO
            //ES LA UNICA FORMA QUE ENCONTRE DE PASAR DE STRING A ENUM

            /*Poner la lectura dentro de un bucle while.

            Dentro del while, usar un try-catch.

            Si el try funciona (el dato se parsea bien), salís del while.

            Si el catch se activa (el usuario puso algo mal), mostrás un mensaje de error claro y el while se repite, volviendo a pedir el dato.*/

       /*  System.out.println("Numero de Documento: ");
        long numeroDocumento = scanner.nextLong();
        scanner.nextLine(); //consumir salto de línea

        System.out.println("CUIT: ");//no obligatorio
        String cuit = scanner.nextLine();

        System.out.println("Posición Frente al IVA (Consumidor Final, Monotributista, Responsable Inscripto, Excento): ");//por defecto consumidor final
        String posIvaStr = scanner.nextLine().toUpperCase(); // Convertir a mayúsculas
        PosIva posIva = null;
        try {
            posIva = PosIva.valueOf(posIvaStr); // Convierte String a Enum
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Posición Frente al IVA inválida.");
            // Manejar el error, volver a pedir
            //ACA HAY QUE VER COMO MANEJAR QUE INGRESA MAL. NO SE SI ESTA BIEN VERIFICARLO ACA PERO
            //ES LA UNICA FORMA QUE ENCONTRE DE PASAR DE STRING A ENUM
            //aca se aniade la complejidad de validar consumidor final, teniendo en cuenta que el valor del enum es ConsumidorFinal, sin espacios
        }

        System.out.println("Fecha de Nacimiento (dd/mm/aaaa): ");
        String fechaNacimientoString = scanner.nextLine();
        Date fechaNacimiento = null;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fechaNacimiento = formatoFecha.parse(fechaNacimientoString);
        } catch (ParseException e) {
            System.out.println("Formato de fecha inválido. Use dd/MM/yyyy.");
            // manejar reintento o dejar fechaNacimiento en null según la lógica de la aplicación
            //ACA HAY QUE VER COMO MANEJAR QUE INGRESA MAL. NO SE SI ESTA BIEN VERIFICARLO ACA PERO
            //ES LA UNICA FORMA QUE ENCONTRE DE PASAR DE STRING A DATE
        }

        System.out.println("Calle: ");
        String calleDireccion = scanner.nextLine();

        System.out.println("Numero: ");
        int numeroDireccion = scanner.nextInt();
        scanner.nextLine(); //consumir salto de línea

        System.out.println("Departamento: ");//supongo que es opcional
        String departamentoDireccion = scanner.nextLine();

        System.out.println("Piso: ");//supongo que es opcional
        int pisoDireccion = scanner.nextInt();
        scanner.nextLine(); //consumir salto de línea

        System.out.println("Codigo Postal: ");
        int codPostalDireccion = scanner.nextInt();
        scanner.nextLine(); //consumir salto de línea

        System.out.println("Localidad: ");
        String localidadDireccion = scanner.nextLine();

        System.out.println("Provincia: ");
        String provinciaDireccion = scanner.nextLine();

        System.out.println("Pais: ");
        String paisDireccion = scanner.nextLine();

        System.out.println("Telefono: ");
        int telefono = scanner.nextInt();
        scanner.nextLine(); //consumir salto de línea

        System.out.println("Email: ");//no obligatorio
        String email = scanner.nextLine();

        System.out.println("Ocupacion: ");
        String ocupacion = scanner.nextLine();

        System.out.println("Nacionalidad: ");
        String nacionalidad = scanner.nextLine();

        // Crear los DTOs (aún no tenemos el ID de dirección)
        DtoDireccion direccionDto = new DtoDireccion(calleDireccion, numeroDireccion, departamentoDireccion, pisoDireccion, codPostalDireccion, localidadDireccion, provinciaDireccion, paisDireccion);
        DtoHuesped huespedDto = new DtoHuesped(nombres, apellido, telefono, tipoDocumento, numeroDocumento, cuit, posIva, fechaNacimiento, email, ocupacion, nacionalidad);

        //asociamos el la direccion con el huesped
        huespedDto.setDireccion(direccionDto);


        System.out.println("--- Fin Formulario ---");
        return huespedDto; // Devolver el DTO con los datos cargados

    }

        return new DtoHuesped();
    }*/


 private void iniciarModificacionHuesped(DtoHuesped dtoHuesped){ //Metodo para Modificar Huesped CU10
    boolean salir = false;
    DtoHuesped dtoHuespedModificado = dtoHuesped;
    String tipoDocStr = "";
    String posIvaStr = "";
        while(!salir){
            
            System.out.println("========================================");

            mostrarDatosHuesped(dtoHuespedModificado);

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
                    dtoHuespedModificado.setApellido(scanner.nextLine().trim());
                    break;
                case 2:
                   dtoHuespedModificado.setNombres(scanner.nextLine().trim());
                    break;
                case 3:
                   tipoDocStr = scanner.nextLine().trim().toUpperCase();
                    break;
                case 4:
                   dtoHuespedModificado.setDocumento(scanner.nextLong());
                    break;
                case 5:
                    dtoHuespedModificado.setCuit(scanner.nextLine().trim());
                    break;
                case 6:
                    posIvaStr = scanner.nextLine().trim().toUpperCase();
                    break;
                 case 7:
                    dtoHuespedModificado.setFechaNacimiento(leerFecha("Fecha de nacimiento", dtoHuespedModificado.getFechaNacimiento()));
                     break;
                case 8:
                    cambiarDireccionHuesped(dtoHuespedModificado.getDireccion());
                    break;
                case 9:
                    dtoHuespedModificado.setTelefono(scanner.nextInt());
                    break;
                case 10:
                    dtoHuespedModificado.setEmail(scanner.nextLine().trim());
                    break;
                case 11:
                    dtoHuespedModificado.setOcupacion(scanner.nextLine().trim());
                    break;
                case 12:
                    dtoHuespedModificado.setNacionalidad(scanner.nextLine().trim());
                    break;
                 case 13: 
                    // Al pulsar SIGUIENTE validamos omisiones. Si hay errores, no salimos. Paso2 CU10
                    if (gestorHuesped.validarDatos(dtoHuespedModificado, tipoDocStr, posIvaStr)) {
                        if(gestorHuesped.tipoynroDocExistente(dtoHuespedModificado)){
                            System.out.println("¡CUIDADO! El tipo y número de documento ya existen en el sistema");
                            System.out.println("1. ACEPTAR IGUALMENTE");
                            System.out.println("2. CORREGIR");
                            System.out.print("Ingrese una opción: ");
                            int opcionDoc = -1;
                            try {
                                opcionDoc = scanner.nextInt();
                                scanner.nextLine(); //consumir salto de linea
                            } catch (Exception e) {
                                scanner.nextLine(); //limpiar buffer
                                System.out.println("\nOpción inválida. Intente nuevamente.\n");
                                break;
                            }
                            if (opcionDoc == 2) {
                                // quedarse en la pantalla para que el actor corrija
                                break;
                            }
                        } else{
                            gestorHuesped.modificarHuesped(dtoHuesped, dtoHuespedModificado);
                            System.out.println("La operacion ha culminado con éxito.\n");
                            salir = true;
                        }
                    } else {
                        // quedarse en la pantalla para que el actor corrija. Mensajes enviados en validarDatos
                    }
                    break;
                case 14:
                    pulsarCancelar(salir);
                    System.out.println("Modificación cancelada.\n");
                    break;
                case 15:
                    //CU11
                    System.out.println("Falta CU11, Huésped borrado del sistema. \n");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.\n");
            }
        }
    }
    
public void cambiarDireccionHuesped(DtoDireccion direccion){
    boolean salir = false;
    while(!salir){
        mostrarDireccionHuesped(direccion);
        System.out.println("Selecciona el dato a cambiar /n");
        System.out.println("1. Calle");
        System.out.println("2. Numero");
        System.out.println("3. Departamento");
        System.out.println("4. Piso");
        System.out.println("5. Codigo Postal");
        System.out.println("6. Localidad");
        System.out.println("7. Provincia");
        System.out.println("8. Pais");
        System.out.println("9. VOLVER");
        System.out.print("Ingrese una opción: ");
        int opcion = -1;
        try {
            opcion = scanner.nextInt();
            scanner.nextLine(); //consumir salto de linea
        } catch (Exception e) {
            scanner.nextLine(); //limpiar buffer
            System.out.println("\nOpción inválida. Intente nuevamente.\n");
            return;
        }
        switch(opcion){
            case 1:
                direccion.setCalle(scanner.nextLine().trim());
                break;
            case 2:
                direccion.setNumero(scanner.nextInt());
                break;
            case 3:
                direccion.setDepartamento(scanner.nextLine().trim());
                break;
            case 4:
                direccion.setPiso(scanner.nextInt());
                break;
            case 5:
                direccion.setCodPostal(scanner.nextInt());
                break;
            case 6:
                direccion.setLocalidad(scanner.nextLine().trim());
                break;
            case 7:
                direccion.setProvincia(scanner.nextLine().trim());
                break;
            case 8:
                direccion.setPais(scanner.nextLine().trim());
            break;
            case 9:
                return;
            default:
                System.out.println("Opción inválida. Intente nuevamente.\n");
        }
    }
}
    private void pulsarCancelar(boolean salir){ //Paso3 CU10
        System.out.print("\n¿Desea cancelar la modificación del huésped? ");
        System.out.print("1. SI");
        System.out.print("2. NO");
        int opt = -1;
        while (true) {
            System.out.print("Ingrese una opción: ");
            try {
                opt = scanner.nextInt();
                scanner.nextLine(); //consumir salto de linea
            } catch (Exception e) {
                scanner.nextLine(); //limpiar buffer
                System.out.println("\nOpción inválida. Intente nuevamente.\n");
                
            }
            switch (opt){
                case 1:
                    System.out.println("\nModificación cancelada.\n");
                    salir = true;
                    return;
                case 2:
                    System.out.println("\nContinuando con la modificación.\n");
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.\n");
            }
        }
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
        mostrarDireccionHuesped(dtoHuesped.getDireccion()); 
        System.out.println("9. Teléfono: " + dtoHuesped.getTelefono());
        System.out.println("10. Email: " + dtoHuesped.getEmail());
        System.out.println("11. Ocupación: " + dtoHuesped.getOcupacion());
        System.out.println("12. Nacionalidad: " + dtoHuesped.getNacionalidad());
        System.out.println("13. SIGUIENTE");
        System.out.println("14. CANCELAR");
        System.out.println("15. BORRAR HUESPED");
        System.out.println("---------------------------\n");
    }

    private void mostrarDireccionHuesped(DtoDireccion direccion){
        System.out.println("8. DIRECCIÓN DEL HUESPED");
        System.out.println("Calle: " + direccion.getCalle());
        System.out.println("Número: " + direccion.getNumero());
        System.out.println("Departamento: " + direccion.getDepartamento());
        System.out.println("Piso: " + direccion.getPiso());
        System.out.println("Código Postal: " + direccion.getCodPostal());
        System.out.println("Localidad: " + direccion.getLocalidad());
        System.out.println("Provincia: " + direccion.getProvincia());
        System.out.println("País: " + direccion.getPais());
        System.out.println("-------------------------------\n");
    }


    /*
     * Lee una fecha desde la entrada en formato dd/MM/yyyy.
     * Si el usuario ingresa línea vacía, devuelve current (mantiene la fecha actual).
     */
    private Date leerFecha(String etiqueta, Date current) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.setLenient(false);
        while (true) {
            String actual = (current == null) ? "vacío" : formato.format(current);
            System.out.print(etiqueta + " (dd/MM/yyyy) [ENTER para mantener: " + actual + "]: ");
            String linea = scanner.nextLine().trim();
            if (linea.isEmpty()) {
                return current;
            }
            try {
                return formato.parse(linea);
            } catch (ParseException e) {
                System.out.println("Formato inválido. Use dd/MM/yyyy. Intente nuevamente.");
            }
        }
    }
}
