package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Elkin Julian
 */

//Clase que realiza la conexcion a la BD
public class conexion {

    Connection conexion = null;

    public Connection getConectar() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost/empresa";
            String usuarioDB = "root";
            String passwordDB = "12345";
            conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error al conectar la base de datos: \n"
                    + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } finally {
            return conexion;
        }
    }
}
