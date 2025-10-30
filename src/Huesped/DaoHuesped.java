package Huesped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


import Excepciones.PersistenciaException;
import BaseDedatos.Coneccion;
import enums.TipoDocumento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DaoHuesped implements DaoHuespedInterfaz {

    //estos son los atributos que necesita la clase para conectarse a la DB
    //private static final String DB_URL =;
    //private static final String USER =;
    //private static final String PASS =;

    //metodo para realizarla coneccion a la DB
    //private Connection getConnection() throws SQLException{
    //    return DriverManager.getConnection(DB_URL, USER, PASS);
    //}

    //public boolean CrearHuesped(DtoHuesped dto){}
    public boolean modificarHuesped(int idUsuario){return false;}
    //public boolean EliminarHuesped(int idUsuario){}
    //public DtoHuesped ObtenerHuesped(int idUsuario){}


    public boolean crearHuesped(DtoHuesped dto) throws PersistenciaException {

        // Asume que la columna en tu BD se llama 'id_direccion' preguntar como se llama bien
        String sql = "INSERT INTO huesped (nombres, apellido, telefono, tipo_documento, numero_documento, " +
                "cuit, posicion_iva, fecha_nacimiento, email, ocupacion, nacionalidad, id_direccion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Coneccion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dto.getNombres());
            pstmt.setString(2, dto.getApellido());
            pstmt.setString(3, dto.getTelefono());
            pstmt.setString(4, dto.getTipoDocumento().name()); // Guardamos el Enum como String
            pstmt.setLong(5, dto.getDocumento());
            pstmt.setString(6, dto.getCuit());
            pstmt.setString(7, dto.getPosicionIva().name());

            // Convertir java.util.Date (del DTO) a java.sql.Date (para JDBC)
            pstmt.setDate(8, new java.sql.Date(dto.getFechaNacimiento().getTime()));

            pstmt.setString(9, dto.getEmail());
            pstmt.setString(10, dto.getOcupacion());
            pstmt.setString(11, dto.getNacionalidad());


            // Obtenemos el ID de la dirección (que ya se creó y seteó en el DTO)
            pstmt.setInt(12, dto.getDireccion().getId());

            int filasAfectadas = pstmt.executeUpdate();

            // Devuelve true si se insertó al menos 1 fila
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenciaException("Error al intentar crear el huésped en la BD", e);
        }
    }
    
    //public boolean modificarHuesped(int idUsuario){}
    public boolean eliminarHuesped(int idUsuario){return true;}
    public List<DtoHuesped> obtenerTodosLosHuespedes (){return null;}
    /*public List<DtoHuesped> obtenerTodosLosHuespedes (){
       
        List<DtoHuesped> huespedesEncontrados = new ArrayList<>();

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
    }*/
    
    public List<DtoHuesped> obtenerHuespedesPorCriterio(DtoHuesped criterios){
    
        List<DtoHuesped> huespedesEncontrados = new ArrayList<>();
        
        StringBuffer sql = new StringBuffer("SELECT apellido, nombres, tipo_documento, numero_documento FROM huesped WHERE 1=1");
        
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



    public DtoHuesped buscarPorTipoYNumeroDocumento(TipoDocumento tipoDoc, Long numDoc) throws PersistenciaException {
        // Devuelve un DTO si lo encuentra, o null si no existe
        String sql = "SELECT * FROM huesped WHERE tipo_documento = ? AND numero_documento = ?";

        try (Connection conn = Coneccion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Usamos .name() para convertir el Enum a String
            pstmt.setString(1, tipoDoc.name());
            pstmt.setLong(2, numDoc);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    //encuentra un duplicado, guardamos los datos principales del existente para mostrarlos
                    //ESTO NO ESTABA EN EL CU9, PERO ME PARECE UN BUEN ANIADIDO
                    DtoHuesped h = new DtoHuesped();
                    h.setNombres(rs.getString("nombres"));
                    h.setApellido(rs.getString("apellido"));
                    h.setTipoDocumento(TipoDocumento.valueOf(rs.getString("tipo_documento")));
                    h.setDocumento(rs.getLong("numero_documento"));

                    return h;
                }
            }
        } catch (SQLException e) {
            //por si hay error en la coneccion con la db
            throw new PersistenciaException("Error al buscar huésped por documento", e);
        }
        return null; // No encontró duplicado
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