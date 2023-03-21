package com.mycompany.adrecuperacioneje1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibreriaImporter {

private static final String JDBC_URL = "jdbc:mysql://localhost/examen";
private static final String JDBC_USER = "root";
private static final String JDBC_PASSWORD = "";
private static final String CSV_SEPARATOR = ";";

public static void importar(String archivo) {
  try (BufferedReader br = new BufferedReader(new FileReader(archivo));
       Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
    String linea;
    int contador = 0;
    while ((linea = br.readLine()) != null) {
      if (contador > 0) {
        String[] campos = linea.split(CSV_SEPARATOR);
        int id = 0;
        try {
          id = Integer.parseInt(campos[0]);
        } catch (NumberFormatException e) {
          // La primera línea del archivo es el encabezado, así que solo la ignoramos
          continue;
        }
        String titulo = campos[1];
        String autor = campos[2];
        String año = campos[3];
        String genero = campos[4];
        String idioma = campos[5];
        String original = campos[6];
        String sinopsis = campos[7];
        insertarLibro(conn, id, titulo, autor, año, genero, idioma, original, sinopsis);
      }
      contador++;
    }
  } catch (IOException e) {
    System.err.println("Error al leer el archivo " + archivo + ": " + e.getMessage());
  } catch (SQLException e) {
    System.err.println("Error al insertar el libro: " + e.getMessage());
  }
}

private static void insertarLibro(Connection conn, int id, String titulo, String autor, String año, String genero, String idioma, String original, String sinopsis)
throws SQLException {
String sql = "INSERT INTO libros (id, titulo, autor, año, genero, idioma, original, sinopsis) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
try (PreparedStatement ps = conn.prepareStatement(sql)) {
ps.setInt(1, id);
ps.setString(2, titulo);
ps.setString(3, autor);
ps.setString(4, año);
ps.setString(5, genero);
ps.setString(6, idioma);
ps.setString(7, original);
ps.setString(8, sinopsis);
ps.executeUpdate();
}
}
}