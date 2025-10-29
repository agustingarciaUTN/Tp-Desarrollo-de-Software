package Huesped;

//PROBANDO

import Dominio.Huesped;
import enums.TipoDocumento;

import java.util.List;

public class GestorHuesped {
    //debe presentarse en pantalla la opcion para ejecutar el metodo de buscar huesped
    // solo si se autentico antes el conserje

    //los daos que utilizara el gestor (son de tipo interfaz por SOLID)
    private final DaoHuespedInterfaz daoHuesped;
    private final DaoDireccionInterfaz daoDireccion;

    public GestorHuesped(DaoHuespedInterfaz daoHuesped, DaoDireccionInterfaz daoDireccion) {
        this.daoHuesped = daoHuesped;
        this.daoDireccion = daoDireccion;
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
        
        // presentar los datos de los dto encontrados en pantalla de la manera correcta
        // manera: Esta lista contiene como columnas los datos mencionados en el paso 2.
        //Seleccionar una persona de alguna manera -> no lo hace el gestor
        //Presiona siguiente. -> no lo hace el gestor
        //Chequear si no selecciono nadie y apreto siguiente, ir al cu11.
        //si apreto bien ir al cu10. y terminar. -> Segun el diagrama de secuencia
        // esto tampoco lo hace el gestor
        
    }
    
    public void seleccionaHuesped (DtoHuesped huespedSeleccionado) {
        if (huespedSeleccionado == null) {
            this.darAltaHuesped();
        }
        else {
            this.modificarHuesped();
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

    public void modificarHuesped(DtoHuesped dtoHuespedOriginal, DtoHuesped dtoHuespedModificado){
        daoHuesped.modificarHuesped(dtoHuespedOriginal, dtoHuespedModificado);
    }

 //   public boolean darDeBajaHuesped(){
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
 //   }
 public boolean validarDatos(DtoHuesped dtoHuesped, String TipoDoc, String PosIva) {
        List<String> errores = new ArrayList<>();

        if (dtoHuesped.getApellido() == null || dtoHuesped.getApellido().isBlank()) {
            errores.add("Apellido");
        }
        if (dtoHuesped.getNombres() == null || dtoHuesped.getNombres().isBlank()) {
            errores.add("Nombres");
        }

        // Validar que TipoDoc no sea nulo/blank y que exista en el enum TipoDocumento
      if (TipoDoc == null || TipoDoc.isBlank()) {
            errores.add("Tipo de documento");
        } else {
            String tipoDocNormalized = TipoDoc.trim().toUpperCase();
            try {
                dtoHuesped.setTipoDocumento(TipoDocumento.valueOf(tipoDocNormalized));
            } catch (IllegalArgumentException ex) {
                errores.add("Tipo de documento (valor inválido)");
            }
        }
        if (dtoHuesped.getDocumento() <= 0L) {
            errores.add("Número de documento");
        }
        if(dtoHuesped.getCuit() == null || dtoHuesped.getCuit().isBlank()) {
            errores.add("CUIT");
        }
        if (PosIva == null || PosIva.isBlank()) {
            // Si querés asignar por omisión:
            dtoHuesped.setPosicionIva(enums.PosIva.ConsumidorFinal);
        } else {
            String posIvaNormalized = PosIva.trim().toUpperCase();
            try {
                dtoHuesped.setPosicionIva(enums.PosIva.valueOf(posIvaNormalized));
            } catch (IllegalArgumentException ex) {
                errores.add("Posición frente al IVA (valor inválido)");
            }
        }
        
        if(dtoHuesped.getFechaNacimiento() == null) {
            errores.add("Fecha de nacimiento");
        }
        // validar dirección
        if (dtoHuesped.getTelefono() <= 0L) {
            errores.add("Teléfono");
        }
        if(dtoHuesped.getEmail() == null || dtoHuesped.getEmail().isBlank()) {
            errores.add("Email");
        }
        if(dtoHuesped.getOcupacion() == null || dtoHuesped.getOcupacion().isBlank()) {
            errores.add("Ocupación");
        }
        if(dtoHuesped.getNacionalidad() == null || dtoHuesped.getNacionalidad().isBlank()) {
            errores.add("Nacionalidad");
        }

        if (!errores.isEmpty()) {
            System.out.println("\n*** ERROR: Faltan o son inválidos los siguientes datos obligatorios: ***");
            for (String e : errores){
                System.out.println("- " + e);
            }
            System.out.println("Por favor complete/corrija los campos indicados. Los campos no se han blanqueado.\n");
            return false;
        }

        System.out.println("La operación ha culminado con éxito.\n");
        return true;
    }    

    public boolean tipoynroDocExistente(DtoHuesped dtoHuesped) {
        if(daoHuesped.docExistente(dtoHuesped)){  //consultar dao si existe un huesped con ese tipo y nro de doc
            return true; //retornar true si existe, false si no
        }
        return false;
    }
}

       