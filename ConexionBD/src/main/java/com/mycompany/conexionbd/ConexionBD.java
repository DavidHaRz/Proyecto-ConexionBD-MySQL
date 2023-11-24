/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.conexionbd;

import static com.mysql.cj.util.SaslPrep.StringType.QUERY;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author David
 */
public class ConexionBD {
    //Datos de conexión a la base de datos

    static final String DB_URL = "jdbc:mysql://localhost:3306/jcvd";    //jdbc:mysql://ip:puerto/base_datos
    static final String USER = "david";
    static final String PASS = "1234";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {

            /*System.out.println("¿Que videojuego está en la Base de Datos?");
            String nombreBuscar = sc.nextLine();
            System.out.println(buscaNombre(nombreBuscar));*/
            
            //lanzaConsulta("SELECT * FROM `videojuegos`");
            
            //nuevoRegistroParametro("INSERT INTO `videojuegos` (`id`, `Nombre`, `Categoría`, `FechaLanzamiento`, `Compañía`, `Precio`) VALUES (NULL, 'Minecraft', 'Mundo Abierto', '2009-05-17', 'Mojang Studios', '0')");

            //nuevoRegistroTeclado();
            
            System.out.println("Introduzca el nombre del Videojuego que desea eliminar: ");
            String nombreEliminar = sc.nextLine();
            eliminarRegistro(nombreEliminar);
            
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean buscaNombre(String nombre) {
        Scanner sc = new Scanner(System.in);
        boolean salida = false;
        int contador = 0;

        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {

            String QUERY = "SELECT * FROM `videojuegos` WHERE Nombre = '" + nombre + "'";
            ResultSet rs = stmt.executeQuery(QUERY);

            while (rs.next()) {
                contador++;
            }
            if (contador > 0) {
                salida = true;
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salida;
    }

    private static void lanzaConsulta(String miQuery) {
        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(miQuery);) {
            while (rs.next()) {
                //Obtiene la información según el nombre de la columna
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Nombre: " + rs.getString("Nombre"));
                System.out.print(", Categoría: " + rs.getString("Categoría"));
                System.out.print(", FechaLanzamiento: " + rs.getDate("FechaLanzamiento"));
                System.out.print(", Compañía: " + rs.getString("Compañía"));
                System.out.println(", Precio: " + rs.getFloat("Precio"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void nuevoRegistroParametro(String miQuery) {
        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {

            stmt.executeUpdate(miQuery);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void nuevoRegistroTeclado() {
        Scanner sc = new Scanner(System.in);
        String miQuery;
        String nombre, categoria, fechaLanzamiento, compania, precio;
        //Calendar fechaLanzamiento;
        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
                Statement stmt = conn.createStatement();) {

            System.out.println("\tDATOS DEL VIDEOJUEGO");
            System.out.print("Nombre: ");
            nombre = sc.nextLine();
            System.out.print("Categoria: ");
            categoria = sc.nextLine();
            System.out.print("Fecha de Lanzamiento (yyyy-mm-dd): ");        //Si se introduce la fecha mal, peta.
            fechaLanzamiento = sc.nextLine();
            //fechaLanzamiento = Calendar.getInstance();
            System.out.print("Compania: ");
            compania = sc.nextLine();
            System.out.print("Precio: ");
            precio = sc.nextLine();
            
            miQuery =  "INSERT INTO `videojuegos` (`id`, `Nombre`, `Categoría`, `FechaLanzamiento`, `Compañía`, `Precio`) VALUES (NULL, '"  + nombre + "',  '"  + categoria + "',  '"  + fechaLanzamiento + "',  '"  + compania + "', '"  + precio + "')";
            stmt.executeUpdate(miQuery);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean eliminarRegistro(String nombreEliminar) {
    Scanner sc = new Scanner(System.in);
        boolean salida = false;
        int contador = 0;

        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {

            String QUERY = "DELETE FROM `videojuegos` WHERE nombre = '" + nombreEliminar + "'";

            int filasAfectadas = stmt.executeUpdate(QUERY);
            
            if (filasAfectadas == 0)
                System.out.println("No se ha encontrado el juego introducido.");
            else{
                System.out.println("Juego eliminado.");
                salida = true;
            }
            
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(salida);
        return salida;
    }
}
