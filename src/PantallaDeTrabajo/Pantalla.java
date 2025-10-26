package PantallaDeTrabajo;

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
    public Pantalla(){
        //inicializamos el gestor huesped
        DaoHuespedInterfaz daoHuesped = new DaoHuesped();
        DaoDireccionInterfaz daoDireccion = new DaoDireccion();
        this.gestorHuesped = new GestorHuesped(daoHuesped, daoDireccion);
        //inicializamos el scanner
        this.scanner = new Scanner(System.in);
    }

    //METODO PARA CU9 DAR DE ALTA HUESPED
    public void iniciarAltaHuesped(){//este metodo debe tener el mismo nombre que el CU?

        //no se si es necesario, despues habra que ver la parte estetica
        System.out.println("-- Iniciando CU9 'dar de alta huesped' --");

        boolean continuarCargando = true;//bandera

        while(continuarCargando) {
            //metodo para pedir datos
            DtoHuesped datosIngresados = mostrarYPedirDatosFormulario();

            System.out.println("Acciones: 1 = SIGUIENTE, 2 = CANCELAR");
            System.out.println("Ingrese una opcion: ");
            int opcionBoton = scanner.nextInt();
            scanner.nextLine();//para consumir el salto de linea

            if (opcionBoton == 1) {//presiono SIGUIENTE
                System.out.println("Procesando datos...");


                //aca hay que llamar al gestor para que valide los datos
                //List<String> errores = gestorHuesped.validarDatos(datosIngresados);
                List<String> errores = new ArrayList<>();

                if(!errores.isEmpty()){
                    System.out.println("ERROR: Se encontraron los siguientes errores: ");
                    for(String error : errores){
                        System.out.println("- " + error);
                    }
                    System.out.println("Por favor, ingrese los datos nuevamente");
                    continue; //fuerza al inicio del while
                }

                System.out.println("El huesped '" + datosIngresados.getNombres() + " " + datosIngresados.getApellido() +"' ha sido satisfactoriamente cargado al sistema. ¿Desea cargar otro? (SI/NO)");

                System.out.println("¿Desea cargar otro huesped? (SI/NO): ");

                //validacion de ingreso correcto
                String ingresoOtroHuesped = scanner.nextLine();
                while (!ingresoOtroHuesped.equalsIgnoreCase("NO") && !ingresoOtroHuesped.equalsIgnoreCase("SI")) {
                    //no se si aca hay que consumir salto de linea o no
                    System.out.println("Ingreso invalido. ¿Desea cargar otro huesped? (SI/NO): ")
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
    private DtoHuesped mostrarYPedirDatosFormulario(){

        System.out.println("Apellido: ");
        String apellido = scanner.nextLine();

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

            .Si el catch se activa (el usuario puso algo mal), mostrás un mensaje de error claro y el while se repite, volviendo a pedir el dato.*/
        }

        System.out.println("Numero de Documento: ");
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
