package Huesped;

import java.util.ArrayList;
import java.util.List;
import Dominio.Huesped;
import Usuario.DtoUsuario;
import BaseDeDatos.Coneccion;

public class DaoHuesped implements DaoHuespedInterfaz {
    public boolean crearHuesped(DtoHuesped dto){
     
    }
    
    public boolean modificarHuesped(int idUsuario){}
    public boolean eliminarHuesped(int idUsuario){}
    
    public List<DtoHuesped> obtenerTodosLosHuespedes (){
        // 1. Preparamos la lista que vamos a devolver
        List<DtoHuesped> huespedesEncontrados = new ArrayList<>();
        
        // 2. Definimos la consulta SQL (asegúrate que los nombres de las columnas coincidan)
        String sql = "SELECT apellido, nombres, tipo_documento, numero_documento FROM huesped";

        // 3. Usamos try-with-resources para manejar la conexión y la sentencia
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 4. Iteramos sobre los resultados de la consulta
            while (rs.next()) {
                // Para cada fila, creamos un nuevo DTO
                DtoHuesped huespedDTO = new DtoHuesped();
                
                // 5. Mapeamos cada columna al atributo correspondiente del DTO
                huespedDTO.setApellido(rs.getString("apellido"));
                huespedDTO.setNombres(rs.getString("nombres"));
                huespedDTO.setTipoDocumento(rs.getString("tipo_documento"));
                huespedDTO.setNumeroDocumento(rs.getString("numero_documento"));
                // ... setear los otros atributos si los hubiera ...

                // 6. Añadimos el DTO a nuestra lista
                huespedesEncontrados.add(huespedDTO);
            }

        } catch (SQLException e) {
            // Un buen manejo de errores es crucial en una aplicación real
            System.err.println("Error al obtener los huéspedes de la base de datos: " + e.getMessage());
            // Aquí podrías lanzar una excepción personalizada
        }

        // 7. Devolvemos la lista (puede estar vacía, pero nunca será nula)
        return huespedesEncontrados;
    }
    
    public DtoHuesped obtenerHuesped(int idUsuario){
    
    }
    /* Crear un nuevo huésped
    method CrearHuesped(DtoHuesped dto) -> boolean:
    iniciar conexión a la base de datos
    preparar consulta para insertar datos del huésped
    asignar valores de dto a la consulta
    ejecutar la consulta
    si la operación es exitosa:
    retornar true
    de lo contrario:
    retornar false

    // Modificar un huésped existente
    method ModificarHuesped(int idUsuario) -> boolean:
    iniciar conexión a la base de datos
    preparar consulta para actualizar datos del huésped con el idUsuario dado
    asignar valores actualizados a la consulta
    ejecutar la consulta
    si la operación es exitosa:
    retornar true
    de lo contrario:
    retornar false

    // Eliminar un huésped
    method EliminarHuesped(int idUsuario) -> boolean:
    iniciar conexión a la base de datos
    preparar consulta para eliminar el huésped con el idUsuario dado
    ejecutar la consulta
    si la operación es exitosa:
    retornar true
    de lo contrario:
    retornar false

    // Obtener un huésped por su ID
    method ObtenerHuesped(int idUsuario) -> Huesped:
    iniciar conexión a la base de datos
    preparar consulta para seleccionar datos del huésped con el idUsuario dado
    ejecutar la consulta
    si se encuentra un resultado:
    mapear los datos obtenidos a un objeto Huesped
    retornar el objeto Huesped
    de lo contrario:
    retornar null'''*/
}

//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones