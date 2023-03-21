package com.mycompany.adrecuperacioneje1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.hibernate.jpa.AvailableSettings.JDBC_PASSWORD;
import static org.hibernate.jpa.AvailableSettings.JDBC_URL;
import static org.hibernate.jpa.AvailableSettings.JDBC_USER;

public class Main {
 private static final String JDBC_URL = "jdbc:mysql://localhost/examen";
 private static final String JDBC_USER = "root";
  private static final String JDBC_PASSWORD = "";
public static void main(String[] args) {
    
LibreriaImporter.importar("libreria.csv");
mostrarLibros();
}

private static void mostrarLibros() {
try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM libro")) {
while (rs.next()) {
int id = rs.getInt("id");
String titulo = rs.getString("titulo");
String autor = rs.getString("autor");
String año = rs.getString("año");
String genero = rs.getString("genero");
String idioma = rs.getString("idioma");
String original = rs.getString("original");
String sinopsis = rs.getString("sinopsis");
LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
System.out.printf("%d - %s, %s, %s, %s, %s, %s, %s, %s, %s%n", id, titulo, autor, año, genero, idioma, original, sinopsis, created);
}
} catch (SQLException e) {
System.err.println("Error al mostrar los libros: " + e.getMessage());
}
}
}