package Huesped;
import BaseDedatos.Coneccion;
import Excepciones.PersistenciaException;

import java.sql.*;

public class DaoDireccion implements DaoDireccionInterfaz {

        //debe ser tipo DtoDireccion porque necesitamos el ID de la Direccion creada para asignarla al Huesped
        @Override
        public DtoDireccion CrearDireccion(DtoDireccion dto) throws PersistenciaException{
            // Lógica para crear una dirección en la base de datos
            String sql = "INSERT INTO direccion (calle, numero, departamento, piso, cod_postal, localidad, provincia, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            //Cualquier objeto que pongamos dentro de los paréntesis del try (try (...)) será cerrado automáticamente por Java al final del bloque. No importa si el código termina bien o si falla con una excepción.

            //el primer try maneja la coneccion y la sentencia
            try (Connection conn = Coneccion.getConnection();//ACA HAY QUE VER COMO PASARLE EL OBJETO PARA CONECTARSE
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, dto.getCalle());
                pstmt.setInt(2, dto.getNumero());
                pstmt.setString(3, dto.getDepartamento());
                pstmt.setInt(4, dto.getPiso());
                pstmt.setInt(5, dto.getCodPostal());
                pstmt.setString(6, dto.getLocalidad());
                pstmt.setString(7, dto.getProvincia());
                pstmt.setString(8, dto.getPais());

                pstmt.executeUpdate();

                // Recuperar el ID generado
                //el segundo try maneja el resultado de la consulta
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()/*si hay al menos una fila de respuesta devuelve true*/) {
                        // Seteamos el ID al DTO
                        dto.setId(generatedKeys.getInt(1));
                        //System.out.println("Se creó la dirección con ID: " + generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Fallo al crear la dirección, no se obtuvo ID.");
                    }
                }
                return dto; // Devuelve el DTO

            }
            catch (SQLException e) {
                // El TP te pide excepciones propias, tendriamos que crear una aca para dar un mensaje personalizado
                e.printStackTrace(); // Dejamos esto para ver el error en consola.
                //lanzamos la excepcion personalizada que debe ser manejada en el GestorHuesped
                throw new PersistenciaException("Error al intentar crear la dirección en la BD", e);
            }
        }

        @Override
        public boolean ModificarDireccion(int idDireccion) {
            // Lógica para modificar una dirección en la base de datos
            return true;
        }

        @Override
        public boolean EliminarDireccion(int idDireccion) {
            // Lógica para eliminar una dirección en la base de datos
            return true;
        }

        @Override
        public DtoDireccion ObtenerDireccion(int idDireccion) {
            // Lógica para obtener una dirección de la base de datos
            return null;
        }
    }


//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones
