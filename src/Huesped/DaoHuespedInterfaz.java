package Huesped;

import Dominio.Huesped;

public interface DaoHuespedInterfaz {
    boolean crearHuesped(DtoHuesped dto);
    boolean modificarHuesped(int idUsuario);
    boolean eliminarHuesped(int idUsuario);
    List<DtoHuesped> obtenerTodosLosHuespedes ();
    List<DtoHuesped> obtenerHuespedesPorCriterio(DtoHuesped criterios);
}
//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones