/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.conexionbd;

import static com.mysql.cj.util.SaslPrep.StringType.QUERY;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.zip.DataFormatException;

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

            nuevoRegistroTeclado();
            
            /*System.out.println("Introduzca el nombre del Videojuego que desea eliminar: ");
            String nombreEliminar = sc.nextLine();
            eliminarRegistro(nombreEliminar);*/
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un videojuego
     * @param nombre introduce el juego pedido por teclado
     * @return true si encuentra el videojuego y false si no está
     */
    private static boolean buscaNombre(String nombre) {
        Scanner sc = new Scanner(System.in);
        boolean salida = false;
        int contador = 0;

        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {

            String QUERY = "SELECT * FROM `videojuegos` WHERE Nombre = '" + nombre + "'";
            ResultSet rs = stmt.executeQuery(QUERY);

            //Bucle para leer lo que devuelve
            while (rs.next()) {
                contador++;
            }
            //Si encuentra un juego salida = true
            if (contador > 0) {
                salida = true;
            }

            //Cierra la conexión.
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salida;
    }

    /**
    * Muestra todos los datos de la tabla videojuegos
    * @param miQuery Sentencia predefinida
    */
    
    private static void lanzaConsulta(String miQuery) {
        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(miQuery);) {
            //Se repite mientras haya más resultados
            while (rs.next()) {
                //Obtiene la información según el nombre de la columna
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Nombre: " + rs.getString("Nombre"));
                System.out.print(", Categoría: " + rs.getString("Categoría"));
                System.out.print(", FechaLanzamiento: " + rs.getDate("FechaLanzamiento"));
                System.out.print(", Compañía: " + rs.getString("Compañía"));
                System.out.println(", Precio: " + rs.getFloat("Precio"));
            }
            //Cierra la conexión
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
    * Añade un videojuego en una sentencia predefinida
    * @param miQuery 
    */
    private static void nuevoRegistroParametro(String miQuery) {
        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {

            //Actualiza la tabla.
            stmt.executeUpdate(miQuery);

            //Cierra la conexión.
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    * Introduce datos por teclado
    */    
    private static void nuevoRegistroTeclado() {
        Scanner sc = new Scanner(System.in);
        String miQuery;
        String nombre, categoria, fechaLanzamiento, compania;
        float precio;
        //Calendar fechaLanzamiento;
        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
                Statement stmt = conn.createStatement();) {
            
                System.out.println("\tDATOS DEL VIDEOJUEGO");
                //Obligatorio poner el Nombre.
                do{
                    System.out.print("Nombre: ");
                    nombre = sc.nextLine();
                }while (nombre.length() == 0);
                System.out.print("Categoria: ");
                categoria = sc.nextLine();
                
                System.out.print("Fecha de Lanzamiento (yyyy-mm-dd): ");        //Si se introduce la fecha mal, peta.
                fechaLanzamiento = sc.nextLine();
                
                System.out.print("Compania: ");
                compania = sc.nextLine();
                System.out.print("Precio: ");
                precio = sc.nextFloat();
            
            //Sentencia final.
            miQuery =  "INSERT INTO `videojuegos` (`id`, `Nombre`, `Categoría`, `FechaLanzamiento`, `Compañía`, `Precio`) VALUES (NULL, '"  + nombre + "',  '"  + categoria + "',  '"  + fechaLanzamiento + "',  '"  + compania + "', '"  + precio + "')";
            stmt.executeUpdate(miQuery);

            //Cierra la conexión.
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    * Elimina un videojuego.
    * @param nombreEliminar Introduce nombre del juego a eliminar.
    * @return true si el juego ha sido eliminado, false si no ha sido eliminado.
    */    
    private static boolean eliminarRegistro(String nombreEliminar) {
    Scanner sc = new Scanner(System.in);
        boolean salida = false;
        int contador = 0;

        //Abre la conexión
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {

            String QUERY = "DELETE FROM `videojuegos` WHERE nombre = '" + nombreEliminar + "'";

            //Elimina la tabla y recoge el resultado.
            int filasAfectadas = stmt.executeUpdate(QUERY);
            
            //Si no devulve nada, no hay videojuego.
            if (filasAfectadas == 0)
                System.out.println("No se ha encontrado el juego introducido.");
            else{
                System.out.println("Juego eliminado.");
                salida = true;
            }
            
            //Cierra la conexión.
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(salida);
        return salida;
    }
}
