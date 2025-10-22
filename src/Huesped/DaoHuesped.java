package Huesped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Dominio.Huesped;
import Usuario.DtoUsuario;

public class DaoHuesped implements DaoHuespedInterfaz {

    //estos son los atributos que necesita la clase para conectarse a la DB
    private static final String DB_URL =;
    private static final String USER =;
    private static final String PASS =;

    //metodo para realizarla coneccion a la DB
    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public boolean CrearHuesped(DtoHuesped dto){}
    public boolean ModificarHuesped(int idUsuario){}
    public boolean EliminarHuesped(int idUsuario){}
    public DtoHuesped ObtenerHuesped(int idUsuario){}


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