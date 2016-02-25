
import control.control;
import model.conexion;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Prueba
 */
public class main {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Estableciendo conexion...");
        conexion con = new conexion();
        if (con.getConectar() != null) {
            JOptionPane.showMessageDialog(null, "Conexion establecida");
            control c = new control();
            c.menu();
        } else {
            JOptionPane.showMessageDialog(null, "Conexion no establecida\nRevise los campos de conexion e ingrese nuevamente.");
        }

    }

}
