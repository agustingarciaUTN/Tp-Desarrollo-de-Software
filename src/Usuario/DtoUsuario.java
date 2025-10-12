package Usuario;

public class DtoUsuario {
    private int ID_Usuario;
    private String Nombre;
    private String Contrasenia;
    // private String HashContrasenia;

    public DtoUsuario() {
    }
    public DtoUsuario(int ID_Usuario, String Nombre, String Contrasenia) {
        this.Nombre = Nombre;
        this.Contrasenia = Contrasenia;
        this.ID_Usuario = ID_Usuario;
    }

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    public String getContrasenia() {
        return Contrasenia;
    }
    public void setContrasenia(String Contrasenia) {
        this.Contrasenia = Contrasenia;
    }
    public int getID_Usuario(){
        return ID_Usuario;
    }
    public void setID_Usuario(int ID_Usuario){
        this.ID_Usuario = ID_Usuario;
    }
}
