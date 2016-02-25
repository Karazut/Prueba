/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Elkin Julian
 */
//Esta clase contiene los procesos del CRUD
public class procesos {

    Statement st;                           //Esta variable es para ejecutar consultas realizadas en esta clase en general
    conexion coneccion = new conexion();  //Es para llamar el metodo de getConectar y realizar la conexion a la BD

    /**
     * Inserta los datos a la BD
     *
     *
     * @param emp
     */
    public int insert(empleado emp) {
        String query = "insert into empleado (empNombre, empApellido, empCedula, empDireccion, empTelefono, empDpto, empResponsable)"
                + "  values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ppst = (PreparedStatement) coneccion.getConectar().prepareStatement(query);

            //Reemplaza los signos de inyterrogacion (?) en el PreparedStatement con el orden correspondiente
            ppst.setString(1, emp.getNombre());
            ppst.setString(2, emp.getApellido());
            ppst.setString(3, emp.getCedula());
            ppst.setString(4, emp.getDireccion());
            ppst.setString(5, emp.getTelefono());

            //Las condiciones son para que me permita registrar datos nulos en caso de que en la clase control no ingrese un dato
            if (emp.getDpto() == null) {
                ppst.setString(6, null);
            } else {
                ppst.setInt(6, emp.getDpto());
            }
            if (emp.getResponsable() == null) {
                ppst.setString(7, null);

            } else {
                ppst.setInt(7, emp.getResponsable());
            }
            //ejecuta la consulta y cierra la conexion
            //Si la consulta se realizo bien x tomara el valor de 1
            int x = ppst.executeUpdate();
            ppst.close();
            coneccion.getConectar().close();
            return x;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return 0;
        }
    }

    /**
     * Busca el empleado con la cedula ingresada que lo recibe como parametro
     *
     * @param ced
     * @return
     */
    public HashMap buscar(String ced) {
        try {

            String query = "SELECT empNombre, empApellido, empCedula, empDireccion, coalesce(empTelefono, 'Sin telefono'),"
                    + " coalesce(empDpto, 'Sin departamento'), coalesce(empResponsable, 'Sin responsable')"
                    + " from empleado WHERE empCedula ='" + ced + "'";
            st = coneccion.getConectar().createStatement();
            HashMap map = new HashMap<>();
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()) {
                map.put("nombre", rs.getObject("empNombre"));
                map.put("apellido", rs.getObject("empApellido"));
                map.put("cedula", rs.getObject("empCedula"));
                map.put("direccion", rs.getObject("empDireccion"));
                map.put("telefono", rs.getObject("coalesce(empTelefono, 'Sin telefono')"));
                map.put("dpto", rs.getObject("coalesce(empDpto, 'Sin departamento')"));
                map.put("responsable", rs.getObject("coalesce(empResponsable, 'Sin responsable')").toString());                
                return map;
            } else {
                return null;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error, por favor revise nuevamente la información ingresada" + e.getMessage());
        }
        return null;
    }

    /**
     * Modifica el empleado existente
     *
     * @param emp
     * @param ced
     */
    public int modificar(empleado emp, String ced) {

        String query = "UPDATE empleado SET"
                + " empNombre = ? ,"
                + " empApellido = ? ,"
                + " empCedula = ? ,"
                + " empDireccion = ? ,"
                + " empTelefono = ? ,"
                + " empDpto = ? ,"
                + " empResponsable = ?"
                + " WHERE empCedula = '" + ced + "'";

        try {
            PreparedStatement pst = (PreparedStatement) coneccion.getConectar().prepareStatement(query);
            //Reemplaza los signos de inyterrogacion (?) en el PreparedStatement con el orden correspondiente
            pst.setString(1, emp.getNombre());
            pst.setString(2, emp.getApellido());
            pst.setString(3, emp.getCedula());
            pst.setString(4, emp.getDireccion());
            pst.setString(5, emp.getTelefono());

            //Las comndiciones son para que me permita dejar datos nulos en caso de que en la clase control no ingrese un dato
            if (emp.getDpto() == null) {
                pst.setString(6, null);
            } else {
                pst.setInt(6, emp.getDpto());
            }

            if (emp.getResponsable() == null) {
                pst.setString(7, null);

            } else {
                pst.setInt(7, emp.getResponsable());
            }

            //ejecuta la consulta y cierra la conexion
            int x = pst.executeUpdate();
            pst.close();
            coneccion.getConectar().close();
            return x;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return 0;
        }
    }

    /**
     * Elimina un dato segun la ced ingresada como parametro
     *
     * @param ced
     */
    public int eliminar(String ced) {
        String query = "DELETE FROM empleado WHERE empCedula ='" + ced + "'";
        try {
            st = coneccion.getConectar().createStatement();
            int x = st.executeUpdate(query);
            System.out.println(x);
            st.close();
            coneccion.getConectar().close();
            return x;
        } catch (Exception e) {
            return 0;
        }
    }

    //Este metodo es para comprobar si el departamento ingresado en el registro o el update existe
    public int selectDpto(int x) {
        try {
            String query = "SELECT * FROM departamento WHERE codDepartamento =" + x + "";
            st = coneccion.getConectar().createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            x = rs.getRow();
            st.close();
            rs.close();
            return x; //retorna un valor diferente a 0 si existe el empleado
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    //Este metodo es para comprobar si el responsable ingresado en el registro o el update existe
    public int selectResponsable(int x) {
        try {
            String query = "SELECT * FROM empleado WHERE codEmpleado =" + x + "";            
            st = coneccion.getConectar().createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            x = rs.getRow();
            st.close();
            rs.close();
            return x; //retorna un valor diferente a 0 si existe el empleado
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int selectEmp(String ced) {
        try {
            String query = "SELECT * FROM empleado WHERE empCedula ='" + ced + "";
            st = coneccion.getConectar().createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            int x = rs.getRow();
            st.close();
            rs.close();
            return x; //retorna un valor diferente a 0 si existe el empleado
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
