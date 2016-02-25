/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javax.swing.JOptionPane;
import model.empleado;

/**
 *
 * @author Elkin Julian
 */

//Este metodo se encarga de recibir los msj de las validaciones cuando surge un error y las envia al try cathc correpondiente
public class throwValidar extends Exception
{
    empleado emp = new empleado();

    public throwValidar(String msg) {
        super(msg);
    }    
    
}
