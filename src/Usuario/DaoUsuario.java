package Usuario;

import Dominio.Usuario;

public class DaoUsuario implements DaoUsuarioInterfaz {
    @Override
    public DtoUsuario ObtenerUsuario(int ID_Usuario){
        //implementacion segun base de datos u archivos a usar

    }

    @Override
    public boolean EliminarUsuario(int ID_Usuario){
        //implementacion segun base de datos u archivos a usar
    }

    @Override
    public boolean ModificarUsuario(int ID_Usuario){
        //implementacion segun base de datos u archivos a usar
    }

    @Override
    public boolean CrearUsuario(String Nombre, String Contrasenia, int ID_Usuario){   //puede ser Usuario en vez de boolean tmb
        //implementacion segun base de datos u archivos a usar
    }
}
//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones