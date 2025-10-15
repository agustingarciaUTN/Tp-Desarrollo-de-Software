package Huesped;

public interface DaoDireccionInterfaz {
    boolean CrearDireccion(DtoDireccion dto);
    boolean ModificarDireccion(int idDireccion);
    boolean EliminarDireccion(int idDireccion);
    DtoDireccion ObtenerDireccion(int idDireccion);
}
//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones