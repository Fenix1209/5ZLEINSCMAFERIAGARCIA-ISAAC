/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cajero;

/**
 *
 * @author gaep6
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Cajero {

    private int saldo;
    private String contraseña;
    private ArrayList<String> movimientos;
    private String nombreIntegrante = "Integrante: Feria Garcia Isaac";  // Nombre del integrante

    public Cajero() {
        movimientos = new ArrayList<>();
        cargarDatos();
    }

    public static void main(String[] args) {
        Cajero cajero = new Cajero();
        cajero.mostrarMenu();
    }

    public void mostrarMenu(){
        JFrame frame = new JFrame("Cajero Automático");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarDatos();
                JOptionPane.showMessageDialog(null, "Gracias por su atención");
                System.exit(0);
            }
        });

        int opcion = 0;
        do{
            String cadena = JOptionPane.showInputDialog(null, nombreIntegrante + "\n\n"  // Mostrar el nombre en el menú
                    + "1. Consultar saldo\n"
                    + "2. Depositar\n"
                    + "3. Retirar\n"
                    + "4. Cambiar clave\n"
                    + "5. Movimientos\n"
                    + "6. Salir");

            if(cadena != null && ValidacionNumero.isNum(cadena)){
                opcion = Integer.parseInt(cadena);
                switch(opcion){
                    case 1:
                        consultarSaldo();
                        break;

                    case 2:
                        depositar();
                        break;

                    case 3:
                        retirar();
                        break;

                    case 4:
                        cambiarClave();
                        break;

                    case 5:
                        mostrarMovimientos();
                        break;

                    case 6:
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        break;

                    default:
                        JOptionPane.showMessageDialog(null,"Opción no disponible, vuelva a digitar");
                        break;
                }
            }

        } while(opcion != 6);
    }

    public void depositar(){
        String cadena;
        int deposito = 0;

        cadena = JOptionPane.showInputDialog(null,"Digite la cantidad que desea consignar: ");
        if(cadena != null && !cadena.equals("") && ValidacionNumero.isNum(cadena)){
            deposito = Integer.parseInt(cadena);

            if(deposito > 0){
                saldo += deposito;
                movimientos.add("Depósito de: " + deposito + ". Saldo actual: " + saldo);
                JOptionPane.showMessageDialog(null,"El saldo actual es: " + saldo);
            }
            else{
                JOptionPane.showMessageDialog(null,"Digite una cantidad mayor a cero para depositar");
            }
        }
    }

    public void retirar(){
        String cadena;
        cadena = JOptionPane.showInputDialog(null,"Digite su clave: ");

        if(cadena == null){
        }else{
            if(!cadena.equals("")){
                if(cadena.equals(contraseña)){
                    cadena = JOptionPane.showInputDialog(null,""
                            + "a)5000\n"
                            + "b)10 000\n"
                            + "c)20 000\n"
                            + "d)50 000\n"
                            + "e)100 000\n"
                            + "f)Otra cantidad");

                    if(cadena != null){
                        if(!cadena.equals("")){
                            int cantidad = 0;
                            switch(cadena){
                                case "a":
                                    cantidad = 5000;
                                    break;

                                case "b":
                                    cantidad = 10000;
                                    break;

                                case "c":
                                    cantidad = 20000;
                                    break;

                                case "d":
                                    cantidad = 50000;
                                    break;

                                case "e":
                                    cantidad = 100000;
                                    break;

                                case "f":
                                    cadena = JOptionPane.showInputDialog(null,"Digite la cantidad de dinero a retirar: ");
                                    if(cadena != null && !cadena.equals("") && ValidacionNumero.isNum(cadena)){
                                        cantidad = Integer.parseInt(cadena);
                                    }
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null,"La opción no existe, vuelva a digitar");
                                    break;
                            }

                            if(cantidad > 0 && (saldo - cantidad) >= 10000){
                                saldo -= cantidad;
                                movimientos.add("Retiro de: " + cantidad + ". Saldo actual: " + saldo);
                                JOptionPane.showMessageDialog(null,"Su saldo actual es: " + saldo);
                            } else {
                                JOptionPane.showMessageDialog(null,"No tiene suficiente saldo o la cantidad es inválida");
                            }
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"La contraseña es incorrecta");
                }
            }
        }
    }

    public void cambiarClave(){
        String auxiliar;
        String contraseñaNueva;

        auxiliar = JOptionPane.showInputDialog(null,"Digite su clave actual: ");
        if(auxiliar != null){
            if(!auxiliar.equals("")){
                if(auxiliar.equals(contraseña)){
                    contraseñaNueva = JOptionPane.showInputDialog("Digite su nueva clave: ");
                    if(contraseñaNueva != null){
                        if(!contraseñaNueva.equals("")){
                            if(contraseñaNueva.length() > 3){
                                auxiliar = contraseña;
                                movimientos.add("Cambio de clave. Clave anterior: " + auxiliar + ". Clave nueva: " + contraseñaNueva);
                                contraseña = contraseñaNueva;
                                JOptionPane.showMessageDialog(null,"Su nueva contraseña es: " + contraseñaNueva);
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"Por favor, digite una clave que tenga más de 3 letras");
                            }
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"La clave no coincide");
                }
            }
        }
    }

    public void consultarSaldo(){
        JOptionPane.showMessageDialog(null,"Su saldo actual es: " + saldo);
    }

    public void mostrarMovimientos(){
        if(movimientos.isEmpty()){
            JOptionPane.showMessageDialog(null,"No hay movimientos para mostrar");
        } else {
            JList<String> listaMovimientos = new JList<>(movimientos.toArray(new String[0]));
            JScrollPane scrollPane = new JScrollPane(listaMovimientos);
            scrollPane.setPreferredSize(new java.awt.Dimension(400, 200));
            JOptionPane.showMessageDialog(null, scrollPane, "Movimientos", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datos.dat"))) {
            oos.writeObject(contraseña);
            oos.writeInt(saldo);
            oos.writeObject(movimientos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("datos.dat"))) {
            contraseña = (String) ois.readObject();
            saldo = ois.readInt();
            movimientos = (ArrayList<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            saldo = 900000;
            contraseña = "holamundo";
            movimientos = new ArrayList<>();
        }
    }
}

class ValidacionNumero {
    public static boolean isNum(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

