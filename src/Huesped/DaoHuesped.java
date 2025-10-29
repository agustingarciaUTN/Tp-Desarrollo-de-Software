package Huesped;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import Dominio.Huesped;
import Usuario.DtoUsuario;
import BaseDedatos.Coneccion;
import enums.TipoDocumento;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoHuesped implements DaoHuespedInterfaz {

    public boolean crearHuesped(DtoHuesped dto){
     
    }
    
    public boolean modificarHuesped(DtoHuesped original, DtoHuesped modificado){
        String sql = "UPDATE huesped SET apellido = ?, nombres = ?, tipo_documento = ?, numero_documento = ?, cuit = ?, posicion_iva = ?, fecha_nacimiento = ?, telefono = ?, email = ?, ocupacion = ?, nacionalidad = ?, direccion_id = ? WHERE tipo_documento = ? AND numero_documento = ?";

        try (Connection conn = Coneccion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, modificado.getApellido());
            pstmt.setString(2, modificado.getNombres());
            pstmt.setString(3, modificado.getTipoDocumento() != null ? modificado.getTipoDocumento().name() : null);
            pstmt.setLong(4, modificado.getDocumento());
            pstmt.setString(5, modificado.getCuit());
            pstmt.setString(6, modificado.getPosicionIva() != null ? modificado.getPosicionIva().name() : null);

            if (modificado.getFechaNacimiento() != null) {
                pstmt.setDate(7, new java.sql.Date(modificado.getFechaNacimiento().getTime()));
            } else {
                pstmt.setNull(7, java.sql.Types.DATE);
            }

            pstmt.setInt(8, modificado.getTelefono());
            pstmt.setString(9, modificado.getEmail());
            pstmt.setString(10, modificado.getOcupacion());
            pstmt.setString(11, modificado.getNacionalidad());

            if (modificado.getDireccion() != null && modificado.getDireccion().getId() > 0) {
                pstmt.setInt(12, modificado.getDireccion().getId());
            } else {
                pstmt.setNull(12, java.sql.Types.INTEGER);
            }

            // WHERE params: original tipo + numero
            pstmt.setString(13, original.getTipoDocumento() != null ? original.getTipoDocumento().name() : null);
            pstmt.setLong(14, original.getDocumento());

            int updated = pstmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar huésped: " + e.getMessage());
            return false;
        }
    }
    public boolean eliminarHuesped(int idUsuario){}
    
    public ArrayList<DtoHuesped> obtenerTodosLosHuespedes (){
       
        ArrayList<DtoHuesped> huespedesEncontrados = new ArrayList<>();

        String sql = "SELECT apellido, nombres, tipo_documento, numero_documento FROM huesped";
        //Usamos try-with-resources para manejar la conexión y la sentencia
        try (Connection conn = Coneccion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
        //Crear los recursos para la conexion y las interacciones con la base de datos
        // aca hace que Java invoque .close() automaticamente para cerrarlos en caso de error
        // o que termine el metodo y te ahorra un bloque finally
      
            while (rs.next()) {
                // Para cada fila, creamos un nuevo DTO
                DtoHuesped huespedDTO = new DtoHuesped();
                
                //Mapeamos cada columna al atributo correspondiente del DTO
                huespedDTO.setApellido(rs.getString("apellido"));
                huespedDTO.setNombres(rs.getString("nombres"));
                huespedDTO.setDocumento(rs.getLong("numero_documento"));
                
                //Para el enum cambia
                //Primero leemos el tipo de documento como string y despues lo convertimos a enum
                String tipoDocumentoStr = rs.getString("tipo_documento");
                try {
                 if (tipoDocumentoStr != null) {
                 huespedDTO.setTipoDocumento(TipoDocumento.valueOf(tipoDocumentoStr.toUpperCase()));
                }
                } catch (IllegalArgumentException e) {
                  System.err.println("Valor de tipo_documento no válido en la BD: " + tipoDocumentoStr);
                }

                //Añadimos el DTO a nuestra lista
                huespedesEncontrados.add(huespedDTO);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los huéspedes de la base de datos: " + e.getMessage());
        }

        //Devolvemos la lista (puede estar vacía, pero nunca será nula)
        return huespedesEncontrados;
    }
    
    public ArrayList<DtoHuesped> obtenerHuespedesPorCriterio(DtoHuesped criterios){
    
        ArrayList<DtoHuesped> huespedesEncontrados = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder("SELECT apellido, nombres, tipo_documento, numero_documento FROM huesped WHERE 1=1");
        
        //Creamos una lista para guardar los parámetros que realmente usaremos
        List<Object> params = new ArrayList<>();

        //Añadimos las condiciones a la consulta dinámicamente
        if (criterios.getApellido() != null && !criterios.getApellido().trim().isEmpty()) {
            sql.append(" AND apellido LIKE ?");
            // Usamos LIKE para buscar apellidos que "empiecen con" el criterio
            params.add(criterios.getApellido() + "%"); 
        }
        if (criterios.getNombres() != null && !criterios.getNombres().trim().isEmpty()) {
            sql.append(" AND nombres LIKE ?");
            params.add(criterios.getNombres() + "%");
        }
        if (criterios.getTipoDocumento() != null) {
            sql.append(" AND tipo_documento = ?");
            params.add(criterios.getTipoDocumento().name()); //.name() es para devolver el valor del enum como string
        }
        if (criterios.getDocumento() > 0) {
            sql.append(" AND numero_documento = ?");
            params.add(criterios.getDocumento());
        }

        //Ejecutamos la consulta con los parámetros recolectados
        try (Connection conn = Coneccion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {

            // Asignamos los parámetros con sus tipos correctos
           for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    pstmt.setString(i + 1, (String) param);
                } else if (param instanceof Long) {
                    pstmt.setLong(i + 1, (Long) param);
                }
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                //Mapeamos los resultados a DTOs
                 while (rs.next()) {
                // Para cada fila, creamos un nuevo DTO
                DtoHuesped huespedDTO = new DtoHuesped();
                
                //Mapeamos cada columna al atributo correspondiente del DTO
                huespedDTO.setApellido(rs.getString("apellido"));
                huespedDTO.setNombres(rs.getString("nombres"));
                huespedDTO.setDocumento(rs.getLong("numero_documento"));
                
                //Para el enum cambia
                //Primero leemos el tipo de documento como string y despues lo convertimos a enum
                String tipoDocumentoStr = rs.getString("tipo_documento");
                try {
                 if (tipoDocumentoStr != null) {
                 huespedDTO.setTipoDocumento(TipoDocumento.valueOf(tipoDocumentoStr.toUpperCase()));
                }
                } catch (IllegalArgumentException e) {
                  System.err.println("Valor de tipo_documento no válido en la BD: " + tipoDocumentoStr);
                }

                //Añadimos el DTO a nuestra lista
                huespedesEncontrados.add(huespedDTO);
            }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar huéspedes por criterio: " + e.getMessage());
        }

        return huespedesEncontrados;
        
    }


    public boolean docExistente(DtoHuesped criterios){
    
        ArrayList<DtoHuesped> huespedesEncontrados = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder("tipo_documento, numero_documento FROM huesped WHERE 1=1");
        
        //Creamos una lista para guardar los parámetros que realmente usaremos
        List<Object> params = new ArrayList<>();

        if (criterios.getTipoDocumento() != null) {
            sql.append(" AND tipo_documento = ?");
            params.add(criterios.getTipoDocumento().name()); //.name() es para devolver el valor del enum como string
        }
        if (criterios.getDocumento() > 0) {
            sql.append(" AND numero_documento = ?");
            params.add(criterios.getDocumento());
        }

        //Ejecutamos la consulta con los parámetros recolectados
        try (Connection conn = Coneccion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {

            // Asignamos los parámetros con sus tipos correctos
           for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    pstmt.setString(i + 1, (String) param);
                } else if (param instanceof Long) {
                    pstmt.setLong(i + 1, (Long) param);
                }
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                //Mapeamos los resultados a DTOs
                 while (rs.next()) {
                // Para cada fila, creamos un nuevo DTO
                DtoHuesped huespedDTO = new DtoHuesped();
                
                //Mapeamos cada columna al atributo correspondiente del DTO
                huespedDTO.setDocumento(rs.getLong("numero_documento"));
                
                //Para el enum cambia
                //Primero leemos el tipo de documento como string y despues lo convertimos a enum
                String tipoDocumentoStr = rs.getString("tipo_documento");
                try {
                 if (tipoDocumentoStr != null) {
                 huespedDTO.setTipoDocumento(TipoDocumento.valueOf(tipoDocumentoStr.toUpperCase()));
                }
                } catch (IllegalArgumentException e) {
                  System.err.println("Valor de tipo_documento no válido en la BD: " + tipoDocumentoStr);
                }

                //Añadimos el DTO a nuestra lista
                huespedesEncontrados.add(huespedDTO);
            }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar huéspedes por criterio: " + e.getMessage());
        }

        if(huespedesEncontrados.size()>0){
            return true;
        } else {
            return false;
        }
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