package Huesped;


import Dominio.Huesped;

public class GestorHuesped {
    //debe presentarse en pantalla la opcion para ejecutar el metodo de buscar huesped
    // solo si se autentico antes el conserje
    private final DaoHuespedInterfaz dao;
    public GestorHuesped(DaoHuespedInterfaz dao) {
        this.dao = dao;
    }

    public boolean buscarHuesped(DtoHuesped datos){
        // presentar en pantalla los inputs necesarios de
        //apellido.
        //▪ Nombres.
        //▪ Tipo de documento: [DNI, LE, LC,
        //Pasaporte, Otro]
        //▪ Número de documento.
        //puede no poner ninguno, como algunos. Manejar esto.
        // Presiona buscar (hacer boton o algo por el estilo)
        //Según el diagrama de secuencia el ingreso y presionar buscar se hace en la pantalla
        // y luego se le pasan los datos ingresados al gestor, y se maneja lo de que no 
        // ingrese ningún dato o algunos.
        //dao busca con los criterios pasados los huespedes, devuelve dtos? -> Esto va en la
        // parte de DAO, el gestor nomás le pasa los datos y luego recibe los resultados
       
        List<DtoHuesped> listaHuespedes;
        
        if (datos.estanVacios()) {
            listaHuespedes = DaoHuesped.obtenerTodosLosHuespedes(); 
        }
        else {
            listaHuespedes = DaoHuesped.obtenerHuespedesPorCriterios(datos);
        }
        
        //ver si existe concordancia, si no pasar al cu11 y finalizar este cu.
        //si encuentra seguir
        // presentar los datos de los dto encontrados en pantalla de la manera correcta
        // manera: Esta lista contiene como columnas los datos mencionados en el paso 2.
        //Seleccionar una persona de alguna manera -> no lo hace el gestor
        //Presiona siguiente. -> no lo hace el gestor
        //Chequear si no selecciono nadie y apreto siguiente, ir al cu11.
        //si apreto bien ir al cu10. y terminar. -> Segun el diagrama de secuencia
        // esto tampoco lo hace el gestor
        
        if (listaHuespedes.isEmpty()) {
            pantalla.mostrarListaVacia();
            this.darAltaHuesped ();
        }
        else {
            pantalla.mostrarListaHuespedes(listaHuespedes);
        }
        
    }

    public boolean darAltaHuesped(){
        //precondicion haberse ejecutado el cu2, buscar huesped
        //una vez ejecutado el buscarHuesped(), se puede ahora ejecutar este

        //pantalla presenta datos, inputs (algunos obligariorios y otros no)
        // y botones Siguiente y Cancelar -> no lo hace el gestor

            //no ingresa todos los obligatorios y toca siguiente
            //Mostramos un mensaje de error detallando cada una de las omisiones. (podemos concatenar un string si es que falla)
            //vuelve a pedir ingreso de datos ->Puede haber otro error de validación

            //ya existe el huesped y por lo tanto el dni, mostrar mensaje CUIDADO ... -> según el 
            // diagrama de secuencia lo hace la pantalla
            //Botones aceptar igualmente o corregir -> pantalla
                //Aceptar igualmente lleva a seguir por el flujo normal -> crea el huesped y la direccion
                //Corregir vuelve a pedir el ingreso del dni solamente -> pantalla

            //Cancelar -> segun el diagrama de secuencia lo hace la pantalla
            //mensaje ¿Desea cancelar el alta del huésped? Y los botones Si y No.
                //Si, termina el metodo
                //no, regresa al principio del metodo

        //Registramos el huesped, persistimos en bdd -> lo hace el gestor o el DAO?
        //“El huésped nombres y apellido ha sido satisfactoriamente cargado al sistema. ¿Desea cargar otro?
        //Botones Si No
        //No y termina
        //Si y volves al principio de todo
        // Segun el diagrama de secuencia no lo hace el gestor
    }

    public boolean modificarHuesped(){
        //ponemos todos los datos del huesped en pantalla
        //Botones SIGUIENTE, CANCELAR Y BORRAR

            //el actor blaquea 1 o + datos y toca SIGUIENTE
            //mostramos mensaje detallando omisiones hechas
            //volvemos al paso anterior

            //ya existe el document
            //misma logica que el dar el alta

            //Cancelar
            //misma logica que dar de alta

            //BORRAR
            //ejecutar cu11 "dar de baja huesped"

        //Modifica los datos, toca siguiente
        //actualizamos los datos, persistir con dao en la bdd
        //mensaje de exito
        //termina
    }

    public boolean darDeBajaHuesped(){
        //primero verificar que existe
        //ejecutar el buscarHuesped()

            //si no existe, no se ejecuta y mostramos mensaje de que no existe.

        //Verificar si el huesped se alojo alguna vez en el hotel (hizo el check in)
            //si lo hizo, no se pde eliminar
            //mostramos mensaje correspondiente
            //presione cualq tecla y termina el cu

        //Mostramos los datos, nombre, apellido, tipoDoc, nroDoc
        //Botones ELIMINAR CANCELAR0
            //toca Cancelar
            //termina el cu

        //toca Eliminar, borramos el huesped. Se ocupa el DAO
        //mostramos mensaje de eliminacion y presione cualq tecla,
        //termina el cu
    }
}
