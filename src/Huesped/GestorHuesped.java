package Huesped;

//PROBANDO
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import enums.TipoDocumento;
import Dominio.Huesped;
import Estadia.GestorEstadia;
import Estadia.DaoEstadia;
import java.util.List;
import java.util.ArrayList;

public class GestorHuesped {
    //debe presentarse en pantalla la opcion para ejecutar el metodo de buscar huesped
    // solo si se autentico antes el conserje

    //los daos que utilizara el gestor (son de tipo interfaz por SOLID)
    private final DaoHuespedInterfaz daoHuesped;
    private final DaoDireccionInterfaz daoDireccion;
    private GestorEstadia gestorEstadia;

    public GestorHuesped(DaoHuespedInterfaz daoHuesped, DaoDireccionInterfaz daoDireccion) {
        this.daoHuesped = daoHuesped;
        this.daoDireccion = daoDireccion;
        this.gestorEstadia = new GestorEstadia(new DaoEstadia());
    }

    public ArrayList<DtoHuesped> buscarHuesped(DtoHuesped datos){

        ArrayList<DtoHuesped> listaHuespedes;

        
        if (datos.estanVacios()) {
            listaHuespedes = daoHuesped.obtenerTodosLosHuespedes(); 
        }
        else {
            listaHuespedes = daoHuesped.obtenerHuespedesPorCriterio(datos);
        }
        
        return listaHuespedes;

    }
    

   /* public boolean darAltaHuesped(){
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
    }*/


    /* Valida si un huésped puede ser eliminado del sistema
     * Un huésped solo puede eliminarse si NUNCA se alojó en el hotel
     * @param tipoDocumento Tipo de documento del huésped
     * @param nroDocumento Número de documento del huésped
     * @return true si puede eliminarse (no tiene estadías), false si no puede
     */
    public boolean puedeEliminarHuesped(String tipoDocumento, long nroDocumento) {
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty()) {
            System.err.println("El tipo de documento no puede estar vacío");
            return false;
        }

        if (nroDocumento <= 0) {
            System.err.println("El número de documento no es válido");
            return false;
        }

        // Verificar si el huésped tiene estadías registradas
        boolean tieneEstadias = gestorEstadia.huespedSeAlojoAlgunaVez(tipoDocumento, nroDocumento);

        return !tieneEstadias; // Retorna true solo si NO tiene estadías
    }

    /**
     * Elimina un huésped del sistema (borrado físico)
     * También elimina su dirección asociada
     * @param tipoDocumento Tipo de documento del huésped
     * @param nroDocumento Número de documento del huésped
     * @return true si se eliminó exitosamente
     */
    public boolean eliminarHuesped(String tipoDocumento, long nroDocumento) {
        try {
            // NIVEL 2.1: Verificar que puede eliminarse (ya valida que no tenga estadías)
            if (!puedeEliminarHuesped(tipoDocumento, nroDocumento)) {
                System.err.println("El huésped no puede ser eliminado porque tiene estadías registradas");
                registrarAuditoriaFallida(tipoDocumento, nroDocumento, "Tiene estadías registradas");
                return false;
            }

            // NIVEL 2.2: Verificar que no tenga reservas pendientes/activas
            if (tieneReservasPendientes(tipoDocumento, nroDocumento)) {
                System.err.println("El huésped tiene reservas pendientes y no puede ser eliminado");
                registrarAuditoriaFallida(tipoDocumento, nroDocumento, "Tiene reservas pendientes");
                return false;
            }

            // 2. Obtener el ID de la dirección antes de eliminar el huésped
            int idDireccion = daoHuesped.obtenerIdDireccion(tipoDocumento, nroDocumento);

            // 3. Eliminar el huésped
            boolean huespedEliminado = daoHuesped.eliminarHuesped(tipoDocumento, nroDocumento);

            if (!huespedEliminado) {
                System.err.println("No se pudo eliminar el huésped");
                registrarAuditoriaFallida(tipoDocumento, nroDocumento, "Error en eliminación de BD");
                return false;
            }

            // 4. Si tenía dirección, eliminarla también
            if (idDireccion > 0) {
                boolean direccionEliminada = daoHuesped.eliminarDireccion(idDireccion);
                if (!direccionEliminada) {
                    System.err.println("Advertencia: El huésped fue eliminado pero hubo un error al eliminar su dirección");
                }
            }

            // NIVEL 2.3: Registrar auditoría de eliminación exitosa
            registrarAuditoriaExitosa(tipoDocumento, nroDocumento);

            return true;

        } catch (Exception e) {
            System.err.println("Error al eliminar huésped: " + e.getMessage());
            registrarAuditoriaFallida(tipoDocumento, nroDocumento, "Excepción: " + e.getMessage());
            return false;
        }
    }

    /**
     * NIVEL 2.2: Verifica si un huésped tiene reservas pendientes o activas
     * Reserva pendiente = fecha_inicio >= HOY y no tiene check-in
     */
    private boolean tieneReservasPendientes(String tipoDocumento, long nroDocumento) {
        // Consultar si existe en reserva_huesped con reservas activas/pendientes
        // Por ahora retornamos false (implementar cuando tengas el módulo de reservas)

        // TODO: Implementar cuando tengas DaoReserva
        // return daoReserva.tieneReservasPendientes(tipoDocumento, nroDocumento);

        return false; // Por ahora permite eliminar sin verificar reservas
    }

    /**
     * NIVEL 2.3: Registra en log la eliminación exitosa de un huésped
     */
    private void registrarAuditoriaExitosa(String tipoDocumento, long nroDocumento) {
        String timestamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        String mensaje = String.format("[AUDITORÍA] %s - Huésped eliminado: %s %d - Usuario: Sistema",
                timestamp, tipoDocumento, nroDocumento);

        System.out.println(mensaje);

        // Opcional: Escribir a archivo de log
        escribirLog(mensaje);
    }

    /**
     * NIVEL 2.3: Registra en log un intento fallido de eliminación
     */
    private void registrarAuditoriaFallida(String tipoDocumento, long nroDocumento, String motivo) {
        String timestamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        String mensaje = String.format("[AUDITORÍA] %s - Intento fallido de eliminar huésped: %s %d - Motivo: %s",
                timestamp, tipoDocumento, nroDocumento, motivo);

        System.err.println(mensaje);

        // Opcional: Escribir a archivo de log
        escribirLog(mensaje);
    }

    /**
     * NIVEL 2.3: Escribe un mensaje en el archivo de auditoría
     */
    private void escribirLog(String mensaje) {
        try (java.io.FileWriter fw = new java.io.FileWriter("auditoria_huespedes.log", true);
             java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
             java.io.PrintWriter out = new java.io.PrintWriter(bw)) {

            out.println(mensaje);

        } catch (java.io.IOException e) {
            System.err.println("No se pudo escribir en el archivo de auditoría: " + e.getMessage());
        }
    }

// FIN DE LOS MÉTODOS A AGREGAR
}
