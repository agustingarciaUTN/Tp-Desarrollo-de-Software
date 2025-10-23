
package BaseDedatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Coneccion {
    
    private static final String DRIVER="";
    private static final String USUARIO="";
    private static final String CONTRASENIA="";
    private static final String URL="";
    
    private Coneccion (){
        
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection (URL, USUARIO, CONTRASENIA);
    }
    
}
