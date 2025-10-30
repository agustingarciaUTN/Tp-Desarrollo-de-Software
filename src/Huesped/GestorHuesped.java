package Huesped;

//PROBANDO

import Dominio.Huesped;
import Excepciones.PersistenciaException;
import enums.PosIva;

import java.util.ArrayList;
import java.util.Date;
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

    public List<String> validarDatosHuesped(DtoHuesped datos){
        List<String> errores = new ArrayList<>();

        // Campos Obligatorios según CU09 especificación
        if (datos.getApellido() == null || datos.getApellido().trim().isEmpty()) {
            errores.add("El Apellido es obligatorio.");
        }
        if (datos.getNombres() == null || datos.getNombres().trim().isEmpty()) {
            errores.add("Los Nombres son obligatorios.");
        }
        if (datos.getTipoDocumento() == null) {
            errores.add("El Tipo de Documento es obligatorio.");
        }
        if (datos.getDocumento() == null || datos.getDocumento() <= 0) {
            // Asumiendo que Documento ahora es Long y lo pediste con pedirLongOpcional
            errores.add("El Número de Documento es obligatorio y debe ser positivo.");
        }
        if (datos.getFechaNacimiento() == null) {
            errores.add("La Fecha de Nacimiento es obligatoria.");
        } else {
            //regla de que la fecha no puede ser futura
            if (datos.getFechaNacimiento().after(new Date())) { // new Date() es la fecha/hora actual
                errores.add("La Fecha de Nacimiento no puede ser futura.");
            }
            // Podrías añadir validación de mayoría de edad si es necesario aquí
        }

        // Validación de Dirección (asumiendo que DtoHuesped tiene getDireccion())
        DtoDireccion direccion = datos.getDireccion();
        if (direccion == null) {
            errores.add("Los datos de la Dirección son obligatorios.");
        } else {
            if (direccion.getCalle() == null || direccion.getCalle().trim().isEmpty()) {
                errores.add("La Calle de la dirección es obligatoria.");
            }
            if (direccion.getNumero() == null || direccion.getNumero() <= 0) {
                errores.add("El Número de la dirección es obligatorio y debe ser positivo.");
            }
            if (direccion.getLocalidad() == null || direccion.getLocalidad().trim().isEmpty()) {
                errores.add("La Localidad de la dirección es obligatoria.");
            }
            if (direccion.getProvincia() == null || direccion.getProvincia().trim().isEmpty()) {
                errores.add("La Provincia de la dirección es obligatoria.");
            }
            if (direccion.getPais() == null || direccion.getPais().trim().isEmpty()) {
                errores.add("El País de la dirección es obligatorio.");
            }
            if (direccion.getCodPostal() == null || direccion.getCodPostal() <= 0) {
                errores.add("El Código Postal es obligatorio y debe ser positivo.");
            }
        }


        if (datos.getTelefono() == null) {
            errores.add("El Teléfono es obligatorio.");
        }
        if (datos.getOcupacion() == null || datos.getOcupacion().trim().isEmpty()) {
            errores.add("La Ocupación es obligatoria.");
        }
        if (datos.getNacionalidad() == null || datos.getNacionalidad().trim().isEmpty()) {
            errores.add("La Nacionalidad es obligatoria.");
        }

        // Regla especial CUIT/IVA
        if (datos.getPosicionIva() == PosIva.Responsable_Inscripto) {
            if (datos.getCuit() == null || datos.getCuit().trim().isEmpty()) {
                errores.add("El CUIT es obligatorio para Responsables Inscriptos.");
            } else {
                // Podrías añadir una validación básica de formato CUIT aquí si quieres
                if (!validarFormatoCUIT(datos.getCuit())) {
                    errores.add("El formato del CUIT ingresado no es válido (formato CUIT: XX-XXXXXXXX-X).");
                }
            }
        }

        return errores;
    }

    // Metodo de ayuda simple para validar formato CUIT (XX-XXXXXXXX-X)
    private boolean validarFormatoCUIT(String cuit) {
        if (cuit == null) return false;
        // Expresión regular básica: 2 dígitos, guión, 8 dígitos, guión, 1 dígito
        return cuit.matches("\\d{2}-\\d{8}-\\d{1}");
    }

    public DtoHuesped chequearDuplicado(DtoHuesped datos) throws PersistenciaException {
        // La validación de null ya se hizo en el paso anterior (validarDatosHuesped)
        return daoHuesped.buscarPorTipoYNumeroDocumento(datos.getTipoDocumento(), datos.getDocumento());
    }

    public DtoHuesped crearHuespedCompleto(DtoHuesped datosHuesped) throws PersistenciaException {

        try {
            //Crear la Dirección
            // Llamamos al DAO de Dirección. Este metodo actualiza el DTO de dirección
            // con el nuevo ID generado por la BD
            DtoDireccion direccionConId = daoDireccion.CrearDireccion(datosHuesped.getDireccion());


            //Crear el Huésped
            // Ahora llamamos al DAO de Huésped, pasándole el DTO completo.
            // El DAO de Huésped sabrá sacar el ID de la dirección desde datosHuesped.getDireccion().getId()
            daoHuesped.crearHuesped(datosHuesped);


            return datosHuesped;

        } catch (PersistenciaException e) {
            // Si algo falló (crear dirección O crear huésped), capturamos la excepcion
            System.err.println("Error en la capa de Gestor al crear el huésped completo:");
            // y la relanzamos para que la Pantalla se entere.
            throw e;
        }
    }

    /*public boolean buscarHuesped(DtoHuesped datos){
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
    }*/

    //public boolean darAltaHuesped(){
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

   // public boolean modificarHuesped(){
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
    //}

    //public boolean darDeBajaHuesped(){
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
    //}
//}




