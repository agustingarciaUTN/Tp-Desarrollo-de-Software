package Huesped;

import java.util.ArrayList;
import Dominio.Huesped;

public interface DaoHuespedInterfaz {
    boolean crearHuesped(DtoHuesped dto);
    boolean modificarHuesped(DtoHuesped dtoHuespedOriginal, DtoHuesped dtoHuespedModificado);
    boolean eliminarHuesped(String tipoDocumento, long nroDocumento);
    ArrayList<DtoHuesped> obtenerTodosLosHuespedes ();
    ArrayList<DtoHuesped> obtenerHuespedesPorCriterio(DtoHuesped criterios);
    boolean docExistente(DtoHuesped criterios);
    int obtenerIdDireccion(String tipoDocumento, long nroDocumento);
    boolean eliminarDireccion(int idDireccion);
}
//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones