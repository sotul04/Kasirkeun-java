/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.*;

/**
 *
 * @author Suthasoma
 */
public class Connector {
    
    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:KasirkeunData.db");
            System.out.println("Connection Succeeded");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Connection failed");
            return null;
        }
    }
}
