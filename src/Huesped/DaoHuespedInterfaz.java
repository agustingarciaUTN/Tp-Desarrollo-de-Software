package Huesped;

import Excepciones.PersistenciaException;
import enums.TipoDocumento;

import java.util.List;

public interface DaoHuespedInterfaz {
    boolean crearHuesped(DtoHuesped dto) throws PersistenciaException;
    boolean modificarHuesped(int idUsuario);
    boolean eliminarHuesped(int idUsuario);
    List<DtoHuesped> obtenerTodosLosHuespedes ();
    List<DtoHuesped> obtenerHuespedesPorCriterio(DtoHuesped criterios);
    DtoHuesped buscarPorTipoYNumeroDocumento(TipoDocumento tipoDoc, Long numDoc) throws PersistenciaException;
}
//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones