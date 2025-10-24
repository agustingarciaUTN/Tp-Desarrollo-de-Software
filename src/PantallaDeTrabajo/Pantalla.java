package PantallaDeTrabajo;

import Huesped.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pantalla {

    private GestorHuesped gestorHuesped;
    private Scanner scanner;//para la entrada por teclado

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
                System.out.println("¿Desea cancelar el alta del huesped? (SI/NO): ");

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


        return new DtoHuesped();
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
