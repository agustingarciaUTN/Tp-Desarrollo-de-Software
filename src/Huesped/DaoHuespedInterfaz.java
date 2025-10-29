package Huesped;

import java.util.ArrayList;
import Dominio.Huesped;

public interface DaoHuespedInterfaz {
    boolean crearHuesped(DtoHuesped dto);
    boolean modificarHuesped(DtoHuesped dtoHuespedOriginal, DtoHuesped dtoHuespedModificado);
    boolean eliminarHuesped(int idUsuario);
    ArrayList<DtoHuesped> obtenerTodosLosHuespedes ();
    ArrayList<DtoHuesped> obtenerHuespedesPorCriterio(DtoHuesped criterios);
    boolean docExistente(DtoHuesped criterios);
}
//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones