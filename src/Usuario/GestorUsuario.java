package Usuario;
import Dominio.Usuario;

public class GestorUsuario {
    private final DaoUsuarioInterfaz daoUsuario;

    public GestorUsuario(DaoUsuarioInterfaz dao){
        this.daoUsuario = dao;
    }

    public boolean autenticarUsuario(String nombre, String contrasenia){
        // #logica de bdd: aquí se llamaría a daoUsuario.ObtenerUsuario(...) para obtener el usuario real
        // Usuario usuarioObtenido = daoUsuario.ObtenerUsuario(...);
        // DtoUsuario dtoAlmacenado = new DtoUsuario(usuarioObtenido.getId_Usuario(), usuarioObtenido.getNombre(), usuarioObtenido.getContrasenia());

        // Simulación local sin acceso a BDD: usuario hardcodeado
        Usuario usuarioAlmacenado = new Usuario(1, "admin", "1234");
        DtoUsuario dtoAlmacenado = new DtoUsuario(
                usuarioAlmacenado.getId_Usuario(),
                usuarioAlmacenado.getNombre(),
                usuarioAlmacenado.getContrasenia()
        );

        // Generar hash MD5 de la contraseña ingresada
        String hashIngresado = new Usuario().generarHashMD5(contrasenia);

        // Comparar nombre y hash de contraseña
        if (!dtoAlmacenado.getNombre().equals(nombre)) {
            return false;
        }
        return dtoAlmacenado.getHashContrasenia().equals(hashIngresado);
    }
}
