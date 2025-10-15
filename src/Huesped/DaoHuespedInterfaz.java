package Huesped;

import Dominio.Huesped;

public interface DaoHuespedInterfaz {
    boolean CrearHuesped(DtoHuesped dto);
    boolean ModificarHuesped(int idUsuario);
    boolean EliminarHuesped(int idUsuario);
    Huesped ObtenerHuesped(int idUsuario);
}
//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones