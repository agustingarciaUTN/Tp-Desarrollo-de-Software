package Usuario;
import Dominio.Usuario;

public interface DaoUsuarioInterfaz {
    boolean CrearUsuario(String nombre, String contrasenia, int idUsuario);
    boolean ModificarUsuario(int idUsuario);
    boolean EliminarUsuario(int idUsuario);
    Usuario ObtenerUsuario(int idUsuario);
}
