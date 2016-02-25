package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import model.empleado;

/**
 *
 * @author Elkin Julian Esta clase se encarga de todas las validaciones
 * realizadas para el controlador
 */
class validar {

    /**
     * Valida la cedula ingresada, que sea numeros y tenga un rango entr 7 y 12
     *
     * @param ced
     * @return
     * @throws throwValidar
     */
    public void validarCed(String ced) throws throwValidar {
        try {
            if (!ced.matches("[^0]+[0-9]{6,11}")) {
                throw new throwValidar("Debe ingresar una cedúla valida de 7 a 12 digítos");
            }
        } finally {

        }
    }

    /**
     * Valida el nombre ingresado permite la 1ra letra en minuscula o mayuscula
     * y el resto en minuscula Si tiene 2 nombres solo puede haber 1 espacio
     * entre ellos
     *
     * @param nom
     * @return
     * @throws throwValidar
     */
    public void validarNombre(String nom) throws throwValidar {
        try {
            if (nom.matches("[A-Za-z][a-z]{3,12}|[A-Za-z][a-z]{3,12}+\\s?+[A-Za-z][a-z]{3,12}")) {

            } else {
                throw new throwValidar("Debe ingresar un nombre valido entre 3 a 12 letras.\n"
                        + "La primera letra puede ser mayúscula y el resto en minúscula.\n"
                        + "Si tiene mas de un nombre solo se admite un espacio entre ellos.");
            }
        } finally {

        }
    }

    /**
     * Valida el apellido ingresado permite la 1ra letra en minuscula o
     * mayuscula y el resto en minuscula Si tiene 2 apellido solo puede haber 1
     * espacio entre ellos
     *
     * @param nom
     * @return
     * @throws throwValidar
     */
    public void validarApellido(String ape) throws throwValidar {

        try {
            /**
             * Valida que el nombre la primer letra sea minuscula o mayuscula
             * Que sea un nombre de minimo 3 caracteres, maximo 12
             */
            if (!ape.matches("[A-Za-z][a-z]{3,12}|[A-Za-z][a-z]{3,12}+\\s?+[A-Za-z][a-z]{3,12}")) {
                throw new throwValidar("Debe ingresar un apellido valido entre 3 a 12 letras.\n"
                        + "La primera letra puede ser mayúscula y el resto en minúscula.\n"
                        + "Si tiene mas de un apellido solo se admite un espacio entre ellos.");
            }
        } finally {

        }

    }

    public void validarDireccion(String dir) throws throwValidar {
        try {
            if (!dir.matches("[^!ª?¿=)@(/&%$^¨Ç*¨¡':;,._]*")) {
                throw new throwValidar("Hay un caracter no valido en la direccion");
            } else if (dir.equals("")) {
                throw new throwValidar("El campo no puede estar vacío");
            }
        } finally {

        }
    }

    /**
     * Valida que el telefono sea de 7 digitos(fijo) 0 de 10(Celular) Que el
     * numero celular debe empezar en 3 Valida que todos los caracteres sean
     * numeros con el metodo isNumber
     */
    public void validarTelefono(String tel) throws throwValidar {
        try {

            if (!tel.matches("\\d{7,7}|^3\\d{9}")) {
                throw new throwValidar("Formato de número de telefono incorrecto\n");
            }
        } catch (NullPointerException e) {

        }
    }

    /**
     * Valida que el dato ingresado en responsable o Dppto sea numerico y no sea
     * mayor a 5 numeros Que el numero celular debe empezar en 3 Valida que
     * todos los caracteres sean numeros con el metodo isNumber
     */
    public void validarDptoResp(String num) throws throwValidar {
        try {
            if (!num.matches("\\d{1,5}")) {
                throw new throwValidar("El código ingresado no es válido");
            }
        } finally {
        }
    }

    /**
     * Comprueba que al ingresar el codigo del departamento en los metodos
     * insertar o modificar de la clase control existan
     *
     * @param x
     * @throws throwValidar
     */
    public void existeDpto(int x) throws throwValidar {
        if (x == 0) {
            throw new throwValidar("El departamento ingresado no existe");
        }
    }

    public void validarUsuario(int x) throws throwValidar {
        if (x > 0) {
            
        } else {
            throw new throwValidar("El usuario ingresado no existe");
        }
    }

    /**
     * Valida que el resultado arrojado de una consulta no sea un valor nulo
     *
     * @param rs
     * @return
     * @throws throwValidar
     * @throws SQLException
     */
    public void validarList(HashMap emp) throws throwValidar {
        try {
            if (emp == null) {
                throw new throwValidar("No existe el usuario");
            }
        } finally {

        }

    }

    /**
     * vewrifica que el dato ingresado en la ventana de menu sea valido
     *
     * @param num
     * @return
     * @throws throwValidar
     */
    public void menu(String num) throws throwValidar {
        if (!num.matches("[0-5]")) {
            throw new throwValidar("El dato ingresado no es valido");
        }
    }

}
