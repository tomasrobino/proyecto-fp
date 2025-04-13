package alquilercoches.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConexion {
    /*
        No registro el driver de MariaDB porque:
        "This registers the driver with DriverManager, though in modern JDBC (Java 6+), this is often optional due to auto-loading."
        No es necesario en versiones modernas de Java
     */


    private static final String URL = "";
    private static final String USUARIO = "";
    private static final String CONTRASENIA = "";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
    }
}
