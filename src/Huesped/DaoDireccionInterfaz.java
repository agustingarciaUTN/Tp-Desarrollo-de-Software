package Huesped;

import Excepciones.PersistenciaException;

public interface DaoDireccionInterfaz {
    DtoDireccion CrearDireccion(DtoDireccion dto) throws PersistenciaException;
    boolean ModificarDireccion(int idDireccion);
    boolean EliminarDireccion(int idDireccion);
    DtoDireccion ObtenerDireccion(int idDireccion);
}
//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones