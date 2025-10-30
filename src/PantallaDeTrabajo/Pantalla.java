package PantallaDeTrabajo;

import Excepciones.PersistenciaException;
import Huesped.*;
import enums.PosIva;
import enums.TipoDocumento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Pantalla {

    private GestorHuesped gestorHuesped;
    private final Scanner scanner;//para la entrada por teclado

    //constructor (hay que ver como lo vamos a llamar)
    public Pantalla() {
        //inicializamos el gestor huesped
        DaoHuespedInterfaz daoHuesped = new DaoHuesped();
        DaoDireccionInterfaz daoDireccion = new DaoDireccion();
        this.gestorHuesped = new GestorHuesped(daoHuesped, daoDireccion);
        //inicializamos el scanner
        this.scanner = new Scanner(System.in);
    }

    //METODO PARA CU9 DAR DE ALTA HUESPED
    public void iniciarAltaHuesped() {//este metodo debe tener el mismo nombre que el CU?

        //no se si es necesario, despues habra que ver la parte estetica
        System.out.println("-- Iniciando CU9 'dar de alta huesped' --");

        boolean continuarCargando = true;//bandera

        while (continuarCargando) {
            //metodo para pedir datos
            DtoHuesped datosIngresados = mostrarYPedirDatosFormulario();

            System.out.println("Acciones: 1 = SIGUIENTE, 2 = CANCELAR");
            System.out.println("Ingrese una opción: ");
            int opcionBoton = scanner.nextInt();
            scanner.nextLine();//para consumir el salto de linea

            if (opcionBoton == 1) {//presiono SIGUIENTE
                System.out.println("Procesando datos...");


                //aca hay que llamar al gestor para que valide los datos
                List<String> errores = new ArrayList<>();
                errores = gestorHuesped.validarDatosHuesped(datosIngresados);


                if (!errores.isEmpty()) {
                    System.out.println("ERROR: Se encontraron los siguientes errores: ");
                    for (String error : errores) {
                        System.out.println("- " + error);
                    }
                    System.out.println("Por favor, ingrese los datos nuevamente");
                    continue; //fuerzaa al inicio del while
                }

                try {
                    DtoHuesped duplicado = gestorHuesped.chequearDuplicado(datosIngresados);

                    if (duplicado != null) {
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("⚠️ ¡CUIDADO! El tipo y número de documento ya existen en el sistema:");
                        System.out.println("   Huésped existente: " + duplicado.getNombres() + " " + duplicado.getApellido());
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("Opciones: 1 = ACEPTAR IGUALMENTE, 2 = CORREGIR");
                        System.out.print("Ingrese una opción: ");

                        int opcionDuplicado = scanner.nextInt();
                        scanner.nextLine(); // Consumir salto de línea

                        if (opcionDuplicado == 2) { // Eligió CORREGIR
                            System.out.println("Seleccionó CORREGIR. Vuelva a ingresar los datos.");
                            continue; // Vuelve al inicio del while para pedir todo de nuevo
                        }
                        // Si elige 1 (ACEPTAR IGUALMENTE), no hacemos nada y el código sigue
                    }


                    //paso todas las validaciones, creamos el Huesped en la db
                    gestorHuesped.crearHuespedCompleto(datosIngresados);


                } catch (PersistenciaException e) {
                    System.out.println("ERROR DE BASE DE DATOS: No se pudo verificar el duplicado.");
                    e.printStackTrace();
                    continue; // Volver a empezar
                }

                System.out.println("El huésped '" + datosIngresados.getNombres() + " " + datosIngresados.getApellido() + "' ha sido satisfactoriamente cargado al sistema. ¿Desea cargar otro? (SI/NO)");

                System.out.println("¿Desea cargar otro huésped? (SI/NO): ");

                //validacion de ingreso correcto
                String ingresoOtroHuesped = scanner.nextLine();
                while (!ingresoOtroHuesped.equalsIgnoreCase("NO") && !ingresoOtroHuesped.equalsIgnoreCase("SI")) {
                    //no se si aca hay que consumir salto de linea o no
                    System.out.println("Ingreso invalido. ¿Desea cargar otro huésped? (SI/NO): ");
                    ingresoOtroHuesped = scanner.nextLine();
                }

                //si ingreso NO termina el bucle, si ingreso SI se repite
                if (ingresoOtroHuesped.equalsIgnoreCase("NO")) {
                    continuarCargando = false;
                }

            } else if (opcionBoton == 2) {//presiono CANCELAR
                System.out.println("¿Desea cancelar el alta del huésped? (SI/NO): ");

                //validacion de ingreso correcto (capaz esto puede ser una funcion aparte, habria que ver como manejar los mensajes distintos)
                String ingresoCancelarAlta = scanner.nextLine();
                while (!ingresoCancelarAlta.equalsIgnoreCase("NO") && !ingresoCancelarAlta.equalsIgnoreCase("SI")) {
                    //no se si aca hay que consumir salto de linea o no
                    System.out.println("Ingreso invalido. ¿Desea cancelar el alta de huesped? (SI/NO): ");
                    ingresoCancelarAlta = scanner.nextLine();
                }

                if (ingresoCancelarAlta.equalsIgnoreCase("SI")) {
                    System.out.println("Alta cancelada.");
                    continuarCargando = false;//termina el bucle
                }
                //si ingresa NO, el bucle se repite y vuelve a pedir los datos (no se si esta bien que tenga que ingresar todo de 0)
            } else {
                System.out.println("Ingreso invalido. Intente nuevamente.");
            }
        }//fin while

        System.out.println("-- Fin CU9 'dar de alta huesped' ---");
    }//fin iniciarAltaHuesped

    //metodo privado para pedir los datos del huesped a ingresar
    private DtoHuesped mostrarYPedirDatosFormulario() {

        System.out.println("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.println("Nombres: ");
        String nombres = scanner.nextLine();

        TipoDocumento tipoDocumento = pedirTipoDocumento();

        Long numeroDocumento = pedirLong("Número de Documento: ");

        System.out.println("CUIT (opcional, presione Enter para omitir): ");//no obligatorio
        String cuit = scanner.nextLine();
        if (cuit.trim().isEmpty()) { // trim() quita espacios en blanco al inicio y final
            cuit = null;
        }

        PosIva posIva = pedirPosIva();

        Date fechaNacimiento = pedirFecha("Fecha de Nacimiento (dd/MM/yyyy): ");

        System.out.println("Calle: ");
        String calleDireccion = scanner.nextLine();

        Integer numeroDireccion = pedirEntero("Número de calle: ");

        System.out.println("Departamento (ingrese 0 si no aplica): ");//supongo que es opcional
        String departamentoDireccion = scanner.nextLine();

        System.out.println("Piso (ingrese - si no aplica): ");
        String pisoDireccion = scanner.nextLine();

        Integer codPostalDireccion = pedirEntero("Código Postal: ");

        System.out.println("Localidad: ");
        String localidadDireccion = scanner.nextLine();

        System.out.println("Provincia: ");
        String provinciaDireccion = scanner.nextLine();

        System.out.println("Pais: ");
        String paisDireccion = scanner.nextLine();

        System.out.println("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.println("Email (opcional, presione Enter para omitir): ");//no obligatorio
        String email = scanner.nextLine();
        if (email.trim().isEmpty()) {
            email = null;
        }

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


    private Integer pedirEntero(String mensaje) {
        Integer valor = null; // Usamos la clase wrapper para permitir null
        boolean valido = false;
        while (!valido) {
            System.out.println(mensaje);
            String entrada = scanner.nextLine(); // leemos siempre como String

            if (entrada.trim().isEmpty()) {
                valido = true; // omitir es una entrada válida (valida el gestor, regla de negocio)
                valor = null;
            } else {
                try {
                    valor = Integer.parseInt(entrada); // intentamos convertir el String a int
                    valido = true;      // Si funciona, es válido
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número entero válido o presione Enter para omitir.");
                }
            }
        }
        return valor;
    }

    private Long pedirLong(String mensaje) { // Devuelve Long (wrapper)
        Long valor = null; // Usamos la clase wrapper Long
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje + " (Enter para omitir): ");
            String entrada = scanner.nextLine(); // Leemos siempre como String

            if (entrada.trim().isEmpty()) {
                valido = true; // Omitir es válido
                valor = null;
            } else {
                try {
                    valor = Long.parseLong(entrada); // Intentamos convertir String a long
                    valido = true;      // Si funciona, es válido
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número entero válido o presione Enter para omitir.");
                }
            }
        }
        return valor;
    }

    private Date pedirFecha(String mensaje) {
        Date fecha = null;
        boolean valida = false;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        formatoFecha.setLenient(false);

        while (!valida) {
            System.out.print(mensaje + " (Enter para omitir): ");
            String fechaStr = scanner.nextLine();

            if (fechaStr.trim().isEmpty()) {
                valida = true; // Omitir es válido
                fecha = null;
            } else {
                try {
                    fecha = formatoFecha.parse(fechaStr);
                    valida = true; // Formato válido
                } catch (ParseException e) {
                    System.out.println("Error: Formato de fecha inválido. Use dd/MM/yyyy o presione Enter para omitir.");
                }
            }
        }
        return fecha;
    }

    private TipoDocumento pedirTipoDocumento() {
        TipoDocumento tipoDoc = null;
        boolean valido = false;

        // Mostrar opciones válidas construyendo un String
        StringBuilder opciones = new StringBuilder("Tipo de Documento (");
        TipoDocumento[] valores = TipoDocumento.values();
        for (int i = 0; i < valores.length; i++) {
            opciones.append(valores[i].name()); // .name() devuelve el nombre del enum (DNI, LE, etc.)
            if (i < valores.length - 1) {
                opciones.append("/");
            }
        }
        opciones.append("): ");

        while (!valido) {
            System.out.print(opciones.toString());
            String tipoDocStr = scanner.nextLine().toUpperCase().trim(); // A mayúsculas y sin espacios
            if (tipoDocStr.isEmpty()) {
                valido = true; // Omitir es válido
                tipoDoc = null;
            } else {
                try {
                    tipoDoc = TipoDocumento.valueOf(tipoDocStr);
                    valido = true; // Opción válida
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: Tipo de documento inválido. Ingrese una de las opciones o Enter para omitir.");
                }
            }
        }
        return tipoDoc;
    }

    private PosIva pedirPosIva() {
        PosIva posIva = null;
        boolean valido = false;

        // Mostrar opciones
        StringBuilder opciones = new StringBuilder("Posición IVA (");
        PosIva[] valores = PosIva.values();
        for (int i = 0; i < valores.length; i++) {
            opciones.append(valores[i].name());
            if (i < valores.length - 1) {
                opciones.append("/");
            }
        }
        opciones.append(", por defecto Consumidor_Final): "); // Aclarar el default

        while (!valido) {
            System.out.print(opciones.toString());
            String posIvaStr = scanner.nextLine().toUpperCase().trim();

            // Permitir Enter para el valor por defecto
            if (posIvaStr.isEmpty()) {
                posIva = PosIva.Consumidor_Final; // Asignar el default
                valido = true;
            } else {
                try {
                    posIva = PosIva.valueOf(posIvaStr);
                    valido = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: Posición IVA inválida. Ingrese una opción válida o Enter para Consumidor_Final.");
                }
            }
        }
        return posIva;
    }

}

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






