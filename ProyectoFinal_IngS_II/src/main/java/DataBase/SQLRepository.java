package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLRepository {
    
    //private static final String URL = "jdbc:sqlite:piedraAzul.db";
    private static final String URL = "jdbc:sqlite:C:\\Users\\User\\Desktop\\piedraAzul.db";
    //private static final String URL = "jdbc:sqlite:C:\\Semestre 6\\ProyectoFinal_IngS_II\\Proyecto-Final-Ing-Software-II\\piedraAzul.db";


    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Conexion exitosa a SQLite");
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return conn;
    }
}
