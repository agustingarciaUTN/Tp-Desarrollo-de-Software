
package BaseDedatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Coneccion {
    
    private static final String DRIVER="jdbc:postgresql://ep-red-truth-aczpiy6y-pooler.sa-east-1.aws.neon.tech/neondb?user=neondb_owner&password=npg_HzBE38OvfPXs&sslmode=require&channelBinding=require";
    private static final String USUARIO="neondb_owner";
    private static final String CONTRASENIA="npg_QufXyUrW7aL9";
    private static final String URL="https://console.neon.tech/app/projects/wild-queen-99662535?branchId=br-damp-resonance-ac1sm2fv&database=neondb";
    
    private Coneccion (){
        
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection (URL, USUARIO, CONTRASENIA);
    }
    
}
