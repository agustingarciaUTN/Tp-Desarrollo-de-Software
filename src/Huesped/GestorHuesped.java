package Huesped;

//PROBANDO
import enums.PosIva;
import enums.TipoDocumento;
import Dominio.Huesped;
import java.util.List;
import java.util.ArrayList;

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

    public ArrayList<DtoHuesped> buscarHuesped(DtoHuesped datos){
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
       
        ArrayList<DtoHuesped> listaHuespedes;
        
        if (datos.estanVacios()) {
            listaHuespedes = daoHuesped.obtenerTodosLosHuespedes(); 
        }
        else {
            listaHuespedes = daoHuesped.obtenerHuespedesPorCriterio(datos);
        }

        // Para cada huésped encontrado, intentamos asignar su dirección
        for (DtoHuesped huesped : listaHuespedes) {
            asignarDireccionAHuesped(huesped);
        }
        
        return listaHuespedes;
        
        // presentar los datos de los dto encontrados en pantalla de la manera correcta
        // manera: Esta lista contiene como columnas los datos mencionados en el paso 2.
        //Seleccionar una persona de alguna manera -> no lo hace el gestor
        //Presiona siguiente. -> no lo hace el gestor
        //Chequear si no selecciono nadie y apreto siguiente, ir al cu11.
        //si apreto bien ir al cu10. y terminar. -> Segun el diagrama de secuencia
        // esto tampoco lo hace el gestor
        
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
        return false;
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
        return false;
    }

    public void modificarHuesped(DtoHuesped dtoHuespedOriginal, DtoHuesped dtoHuespedModificado){
        // Aseguramos que ambos DTOs tengan sus direcciones completas antes de la modificación
        asignarDireccionAHuesped(dtoHuespedOriginal);
        asignarDireccionAHuesped(dtoHuespedModificado);
        
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

        // Validar tipo de documento
        if (dtoHuesped.getTipoDocumento() == null) {
            errores.add("Tipo de documento");
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
        if(dtoHuesped.getDireccion().getCalle() == null || dtoHuesped.getDireccion().getCalle().isBlank()) {
            errores.add("Calle");
        }
        if(dtoHuesped.getDireccion().getNumero() <= 0) {
            errores.add("Número de dirección");
        }
        if(dtoHuesped.getDireccion().getLocalidad() == null || dtoHuesped.getDireccion().getLocalidad().isBlank()) {
            errores.add("Ciudad");
        }
        if(dtoHuesped.getDireccion().getCodPostal() <= 0L ) {
            errores.add("Código postal");
        }
        if(dtoHuesped.getDireccion().getProvincia() == null || dtoHuesped.getDireccion().getProvincia().isBlank()) {
            errores.add("Provincia");
        }
        if(dtoHuesped.getDireccion().getPais() == null || dtoHuesped.getDireccion().getPais().isBlank()) {
            errores.add("País");
        }
        // Evaluamos si hay errores ( y los comentamos) o si no los hay
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

    /**
     * Obtiene y asigna los datos completos de la dirección al DtoHuesped
     * @param dtoHuesped El DTO del huésped al que se le asignará la dirección
     * @return true si se pudo asignar la dirección, false si hubo algún error
     */
    public boolean asignarDireccionAHuesped(DtoHuesped dtoHuesped) {
        if (dtoHuesped == null || dtoHuesped.getIdDireccion() <= 0) {
            return false;
        }

        DtoDireccion dtoDireccion = daoDireccion.ObtenerDireccion(dtoHuesped.getIdDireccion());
        if (dtoDireccion != null) {
            dtoHuesped.setDireccion(dtoDireccion);
            return true;
        }
        return false;
    }

}

       