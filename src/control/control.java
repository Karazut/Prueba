/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.HashMap;
import javax.swing.JOptionPane;
import model.*;

/**
 *
 * @author Elkin Julian
 */
public class control {

    validar validar = new validar();
    procesos process = new procesos();
    String ced, nombre, apellido, direccion, telefono;
    Integer dpto, responsable;
    int x = 0;                    //Permite seguir en el ultimo campo ingresado en insertar o modificar, donde se cometio el error
    int numMenu;                  //Guarda la opción del menu elegido - Se reinicia el valor de este en el metodo menu
    boolean primeraEntrada = true;//Para que no vuelva pedir la cedula al modificar un dato y siga en el ultimo campo 
    HashMap resultado;//Para guardar las consultas realizadas en buscar o modificar

    //Metodo para seleccionar la accion a tomar
    public void menu() {
        //Pide el dato sobre la opcion a realizar
        String msj = "Seleccione una opcion:\n1-Registrar empleado\n2-Buscar empleado\n3-Actualizar datos\n4-Borrar empleado\n0-Salir";

        limpiarCampos();//Deja vacíos las variables globales declaradas
        try {
            String opcion = JOptionPane.showInputDialog(msj);
            validar.menu(opcion); //Valida que el dato ingresado sea u número entre 0 y 5
            numMenu = Integer.parseInt(opcion);   //Realiza la conversion a entero y se utiliza en el swicht case

        } catch (throwValidar ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());//Muestra un msj al ingresar mal el dato en opcion
            menu(); //regresa al menu
        } catch (NullPointerException ex) {//Cuando presiona cancelar o esc me sale la ventana de confirmacion si deseo salir
            salir();
        } finally {
            opcionesMenu();
        }

    }

    /**
     * Se encarga de llamar el metodo correspondiente segun el numero ingresado
     * en el metodo menu
     */
    public void opcionesMenu() {

        switch (numMenu) {
            case 1:
                insertar();
                break;
            case 2:
                buscar();
                break;
            case 3:
                modificar();
                break;
            case 4:
                eliminar();
                break;
            case 0:
                salir();
                break;
        }
    }

    /**
     * Valida e inserta los datos en la BD Las validaciones las realiza en la
     * clase validar. Si ingresa un dato mal, la variable x se encarga de volver
     * al ultimo campo que ingreso En cada if la variable x se incrementa en 1
     * por si ocurre un error pueda continuar en el ultimo campo ingresado Al
     * final si no ingresa datos erroneos x vuelve a valer 0
     */
    public void insertar() {

        try {
            if (x == 0) {
                ced = JOptionPane.showInputDialog("Ingrese la cedula");
                validar.validarCed(ced);        //Valida el dato ingresado

                x++;        //
            }
            if (x == 1) {
                nombre = JOptionPane.showInputDialog("Ingrese el nombre");
                validar.validarNombre(nombre);  //Valida el dato ingresado
                x++;
            }
            if (x == 2) {
                apellido = JOptionPane.showInputDialog("Ingrese el apellido");
                validar.validarApellido(apellido);  //Valida el dato ingresado
                x++;
            }
            if (x == 3) {
                direccion = JOptionPane.showInputDialog("Ingrese la direccion");
                validar.validarDireccion(direccion);
                x++;
            }
            if (x == 4) {
                telefono = JOptionPane.showInputDialog("Ingrese el telefono");
                validar.validarTelefono(telefono);  //Valida el dato ingresado
                x++;
            }
            //Envio los datos ingresados por medio del constructor para luego insertarlos en la BD
            empleado emp = new empleado(this.ced, nombre, apellido, direccion, telefono);

            if (x == 5) {
                int resDpto = JOptionPane.showConfirmDialog(null, "¿Pertenece a un departamento?", "Departamento", JOptionPane.YES_NO_OPTION);

                //ventana de dialogo que pide confirmar si quiere proceder a registrar el departamento
                if (resDpto == JOptionPane.YES_OPTION) {
                    String dpto = JOptionPane.showInputDialog("Ingrese el código del departamento al que pertenece");                    
                    validar.validarDptoResp(dpto);   //Este metodo valida que el dato ingresado sea un numero
                    this.dpto = Integer.parseInt(dpto);
                    validar.existeDpto(process.selectDpto(this.dpto));   //Valida que el dpto ingresado existe
                    emp.setDpto(this.dpto);
                }
                x++; //Como ingresar un dpto es una opcion, debemos incrementar el valor de x por fuera del if            
            }
            if (x == 6) {
                int resResp = JOptionPane.showConfirmDialog(null, "¿Tiene algún responsable?", "responsable", JOptionPane.YES_NO_OPTION);

                //ventana de dialogo que pide confirmar si quiere proceder a registrar el departamento
                if (resResp == JOptionPane.YES_OPTION) {
                    String responsable =JOptionPane.showInputDialog("Ingrese el código del empleado que es responsable de usted");
                    validar.validarDptoResp(responsable); //Este metodo valida que el dato ingresado sea un numero
                    this.responsable = Integer.parseInt(responsable);
                    validar.validarUsuario(process.selectResponsable(this.responsable));   //Valida que el responsable ingresado existe
                    emp.setResponsable(this.responsable);
                }
                x = 0;
            }
            if (process.insert(emp) > 0) {//Llamada al metodo insert de la clase procesos
                JOptionPane.showMessageDialog(null, "Se ha registrado un nuevo empleado.");
            }

        } catch (throwValidar ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage()); //Muestra un msj en caso de un error
            regresarMenu(); //Metodo para regresar al menú principal o seguir insertando datos

        } catch (NullPointerException ex) {
            regresarMenu(); //Metodo para regresar al menú principal o seguir insertando datos
        } finally {
            menu();
        }

    }

    public void buscar() {
        try {
            ced = JOptionPane.showInputDialog(null, "Ingrese la cedula del usuario de buscar");
            validar.validarCed(ced);            //Valida el dato ingresado
            resultado = process.buscar(ced);    //HaspMap - Guarda los campos de la consulta realizada
            validar.validarList(resultado);     //comprueba que la consulta no este vacía            

            JOptionPane.showMessageDialog(null, "Datos del empleado\nNombre: " + resultado.get("nombre") //Muestra un msj con todos los datos de la consulta
                    + "\nApellido: " + resultado.get("apellido")
                    + "\nCedula: " + resultado.get("cedula")
                    + "\nDireccion: " + resultado.get("direccion")
                    + "\nTelefono: " + resultado.get("telefono")
                    + "\nDepartamento: " + resultado.get("dpto")
                    + "\nResponsable: " + resultado.get("responsable"));

        } catch (throwValidar ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());//Muestra el msj de error si ingresa la ced erronea o el rs esta vacío
            regresarMenu();//Metodo para regresar al menú principal o intentar de nuevo

        } catch (NullPointerException e) {
            regresarMenu();
        } finally { 
            menu();
        }

    }

    public void modificar() {
        try {
            if (primeraEntrada == true) {
                String cedula = JOptionPane.showInputDialog("Ingrese la cedula del usuario que quiere modificar");
                validar.validarCed(cedula);        //Valida el dato ingresado
                resultado = process.buscar(cedula);                       //Valida que la consulta tenga datos
                validar.validarList(resultado);  //Valida que la consulta si tenga campos establecidos y el usuario exista
                primeraEntrada = false;                         //Por si el usuario ingresa un dato mal, no vuelva a pedir 
            }

            if (x == 0) {
                this.ced = JOptionPane.showInputDialog("Actualizar la cedula\nCedula actual: "
                        + resultado.get("cedula"));           //Pide la nueva cedula y muestra la cedula actual
                validar.validarCed(this.ced);   //Valida el dato ingresado

                x++;//Para que no vuelva a pedir este dato por si ingreso mal otro dato y continue en ese
            }
            if (x == 1) {
                nombre = JOptionPane.showInputDialog("Actualizar el nombre\nNombre actual: "
                        + resultado.get("nombre"));           //Pide el nuevo nombre y muestra el nombre actual 
                validar.validarNombre(nombre);  //Valida el dato ingresado

                x++;//Para que no vuelva a pedir este dato por si ingreso mal otro dato y continue en ese
            }
            if (x == 2) {
                apellido = JOptionPane.showInputDialog("Actualizar el apellido\nApellido actual: "
                        + resultado.get("apellido"));             //Pide el nuevo apellido y muestra el apellido actual
                validar.validarApellido(apellido);  //Valida el dato ingresado
                x++;//Para que no vuelva a pedir este dato por si ingreso mal otro dato y continue en ese
            }
            if (x == 3) {
                direccion = JOptionPane.showInputDialog("Actualizar la direccion\nDireccion actual: "
                        + resultado.get("direccion"));    //Pide la nueva direccion y muestra la direccion actual
                validar.validarDireccion(direccion);
                x++;//Para que no vuelva a pedir este dato por si ingreso mal otro dato y continue en ese
            }

            if (x == 4) {
                telefono = JOptionPane.showInputDialog("Actualizar el telefono\nTelefono actual: "
                        + resultado.get("telefono"));             //Pide el nuevo telefono y muestra el telefono actual
                validar.validarTelefono(telefono);  //Valida el dato ingresado
                x++;  //Para que en el proximo insertar o modificar vuelva a entrar en el if de cedula      
            }
            empleado emp = new empleado(this.ced, nombre, apellido, direccion, telefono);

            int res; //Para capturar la opcion elegida en el showconfirmDialog en los siguientes if
            if (x == 5) {
                //ventana de dialogo que pide confirmar si quiere proceder a registrar el departamento
                
                res = JOptionPane.showConfirmDialog(null, "¿Desea cambiar/registar el departamento al que pertenece?\nDepartamento actuaL:"
                        + resultado.get("dpto"), "Departamento", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    String dpto = JOptionPane.showInputDialog("Ingrese el código del departamento al que pertenece");
                    validar.validarDptoResp(dpto);  
                    this.dpto = Integer.parseInt(dpto);
                    validar.existeDpto(process.selectDpto(this.dpto));   //Valida que el dpto ingresado existe                    
                    emp.setDpto(this.dpto);
                } else if(resultado.get("dpto") != null ){  //Si existe un dato actual en la BD y no desea modificar, reenvia el dato que esta actualmente
                    int x = Integer.parseInt(resultado.get("dpto").toString());
                    emp.setDpto(x);
                }
                x++; //Como ingresar un dpto es una opcion, debemos incrementar el valor de x por fuera del if
            }
            if (x == 6) {
                //ventana de dialogo que pide confirmar si quiere proceder a registrar el departamento
                res = JOptionPane.showConfirmDialog(null, "¿Desea cambiar/registrar el responsable actual?"
                        + resultado.get("responsable"), "Responsable", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    String responsable = JOptionPane.showInputDialog("Ingrese el código del empleado que es responsable de usted");
                    validar.validarDptoResp(responsable);
                    this.responsable = Integer.parseInt(responsable);
                    validar.validarUsuario(process.selectResponsable(this.responsable));
                    emp.setResponsable(this.responsable);
                } else if(resultado.get("responsable") != null ){   //Si existe un dato actual en la BD y no desea modificar, reenvia el dato que esta actualmente
                    int x = Integer.parseInt(resultado.get("responsable").toString());
                    emp.setResponsable(x);
                }
                x = 0;
            }

            //Envia el objeto empleado con los datos ingresados y el dato de la cedula a cambiar
            if (process.modificar(emp, resultado.get("cedula").toString()) > 0) {
                JOptionPane.showMessageDialog(null, "Se ha modificado los datos del empleado.");
            }

        } catch (throwValidar ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());   //Muestra el msj al generarse un error
            regresarMenu();//Metodo para regresar al menú principal o seguir modificando datos

        } catch (NullPointerException ex) {
            regresarMenu();
        } finally {
            menu();
        }
    }

    /**
     * Metodo para eliminar el usuario
     */
    public void eliminar() {
        try {
            ced = JOptionPane.showInputDialog("Ingrese la cedula del usuario a eliminar");
            validar.validarCed(ced);    //Valida el dato ingresado
            validar.validarUsuario(process.eliminar(ced)); //Si retirna un valor diferente a 0 quiere decir que elimino al usuario

        } catch (throwValidar ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());   //
            regresarMenu();     //Metodo para regresar al menú principal o seguir en el ultimo campo
        } catch (NullPointerException ex) {
            regresarMenu();
        } finally {
            menu();
        }
    }

    //Permite decidir si quiere regresar al menú o continuar donde cometió el error
    public void regresarMenu() {
        try {
            int conf = JOptionPane.showConfirmDialog(null, "¿Desea regresar al menú princpipal?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (conf == JOptionPane.YES_OPTION) {
                menu();
            } else {
                opcionesMenu();     //Permite al usuario continuar donde ingreso el dato erroneo porque numMenu aun conserva el ultimo valor dado
            }
        } finally {
            menu();
        }
    }

    //Pide confirmar si quiere cerrar la apliacación
    public void salir() {
        try {
            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea salir de la apliación?", "Salir", JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Hasta luego.");
                System.exit(0);
            } else {
                menu();
            }
        } finally {

        }
    }

    //Cada vez que se realiza un registro, se modifica o elimina, regresa al menu principal y llama este metodo para dejar todos los campos vacíos
    public void limpiarCampos() {
        x = 0;
        apellido = "";
        ced = "";
        direccion = "";
        dpto = null;
        nombre = "";
        primeraEntrada = true;
        responsable = null;
        resultado = null;
        telefono = "";

    }
}
