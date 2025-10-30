
package BaseDedatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Coneccion {
    
    // URL JDBC corregida (esta es la que va en getConnection)
    private static final String URL = "jdbc:postgresql://ep-red-truth-aczpiy6y-pooler.sa-east-1.aws.neon.tech:5432/neondb?sslmode=require";
    private static final String USUARIO = "neondb_owner";
    private static final String CONTRASENIA = "npg_QufXyUrW7aL9";

    private Coneccion() {
        // Constructor privado para clase utilitaria
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
    }
    
}
