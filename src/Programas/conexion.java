package Programas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "Root";
    private static final String URL = "jdbc:mysql://localhost:3306/Proyecto_Brayan_BD?useLegacyDatetimeCode=false&serverTimezone=America/Mexico_City&useSSL=false";

    static {
        try {
            Class.forName(DRIVER);
            System.out.println("Conexión con MySQL");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error en el driver");
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conectado a MySQL");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error de conexión");
        }
        return con;
    }

    public void closeConnection(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Se cerró la conexión exitosamente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cerrar la conexión");
        }
    }

    public static void main(String[] args) {
        conexion db = new conexion();
        Connection con = db.getConnection();
        db.closeConnection(con);
    }
}
