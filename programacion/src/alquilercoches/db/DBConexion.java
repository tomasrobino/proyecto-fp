package alquilercoches.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBConexion {
    /*
        No registro el driver de MariaDB porque:
        "This registers the driver with DriverManager, though in modern JDBC (Java 6+), this is often optional due to autoloading."
        No es necesario en versiones modernas de Java
     */


    private static final String URL = "jdbc:mariadb://localhost:3306/schema_name";
    private static final String USUARIO = "root";
    private static final String CONTRASENIA = "password";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
    }
}
