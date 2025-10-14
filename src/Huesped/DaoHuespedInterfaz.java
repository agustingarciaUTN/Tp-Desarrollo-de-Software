package Huesped;

import Dominio.Huesped;

public interface DaoHuespedInterfaz {
    boolean CrearHuesped(DtoHuesped dto);
    boolean ModificarHuesped(int idUsuario);
    boolean EliminarHuesped(int idUsuario);
    Huesped ObtenerHuesped(int idUsuario);
}
