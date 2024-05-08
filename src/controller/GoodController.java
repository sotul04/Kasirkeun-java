/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.*;
import entity.Good;
import java.awt.Component;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 *
 * @author Suthasoma
 */
public class GoodController {
    
    private static Connection conn;
    private static PreparedStatement prepStat;
    private static ResultSet resultSet;
    
    public static Good getItem(int id) throws Exception {
        conn = Connector.getConnection();
        String query = "SELECT * from goods WHERE id_item=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, Integer.toString(id));
            resultSet = prepStat.executeQuery();
            
            if (resultSet.next()) {
                return new Good(
                        Integer.parseInt(resultSet.getString("id_item")), 
                        resultSet.getString("name"), 
                        Integer.parseInt(resultSet.getString("stock")), 
                        Double.parseDouble(resultSet.getString("price")), 
                        resultSet.getString("img_source")
                );
            } else {
                return new Good(0, "", 0, 0, Good.DEFAULT_IMAGE);  
            }
        } catch (NumberFormatException | SQLException e) {
            throw e;
        }
    }
    
    public static boolean isItemExist(int id) throws Exception {
        conn = Connector.getConnection();
        String query = "SELECT * from goods WHERE id_item=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, Integer.toString(id));
            resultSet = prepStat.executeQuery();
            
            return resultSet.next();
        } catch (NumberFormatException | SQLException e) {
            throw e;
        }
    }
    
    public static boolean setEditedGood(String imageBaseString, Good target) throws Exception{
        conn = Connector.getConnection();
        String query = "UPDATE goods SET name=?, stock=?, price=?, img_source=? WHERE id_item=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, target.getName());
            prepStat.setString(2, Integer.toString(target.getStock()));
            prepStat.setString(3, Double.toString(target.getPrice()));
            prepStat.setString(4, target.getImgSource());
            prepStat.setString(5, Integer.toString(target.getIdItem()));
            return !prepStat.execute();
        } catch (HeadlessException | SQLException e) {
            throw e;
        }
    }
    
    public static boolean deleteItem(int id) throws Exception{
        conn = Connector.getConnection();
        String query = "DELETE FROM goods WHERE id_item=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, Integer.toString(id));
            return !prepStat.execute();
        } catch (HeadlessException | SQLException e) {
            throw e;
        }
    }
    
    public static boolean addItem(Good good) throws Exception {
        conn = Connector.getConnection();
        String query = "INSERT INTO goods(name,stock,price,img_source) VALUES (?,?,?,?)";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, good.getName());
            prepStat.setString(2, Integer.toString(good.getStock()));
            prepStat.setString(3, Double.toString(good.getPrice()));
            prepStat.setString(4, good.getImgSource());
            return !prepStat.execute();
        } catch (HeadlessException | SQLException e) {
            throw e;
        }
    }
    
    public static ResultSet getAll() throws Exception {
        conn = Connector.getConnection();
        String query = "SELECT * FROM goods";
        try {
            prepStat = conn.prepareStatement(query);
            return prepStat.executeQuery();
        } catch (HeadlessException | SQLException e) {
            throw e;
        }
    }
}
