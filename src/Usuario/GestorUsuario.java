package Usuario;
import Dominio.Usuario;

public class GestorUsuario {
    private final DaoUsuarioInterfaz DaoUsuario;

    public GestorUsuario(DaoUsuarioInterfaz dao){
        this.DaoUsuario = dao;
    }

    public boolean autenticarUsuario(String nombre, String contrasenia){

    }
}
