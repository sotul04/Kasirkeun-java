/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import entity.Good;
import entity.Pair;
import entity.Transaction;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Suthasoma
 */
public class TransactionController {
    
    private static Connection conn;
    private static PreparedStatement prepStat;
    private static ResultSet resultSet;
    
    public static ResultSet getAll() throws Exception {
        conn = Connector.getConnection();
        String query = "SELECT * FROM transactions";
        try {
            prepStat = conn.prepareStatement(query);
            return prepStat.executeQuery();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static ResultSet getDetails(int idTransaction) throws Exception {
        conn = Connector.getConnection();
        String query = "SELECT * FROM sold_goods WHERE id_transaction=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, Integer.toString(idTransaction));
            return prepStat.executeQuery();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static ArrayList<Pair<Good, Pair<Integer, Double>>> getSoldGoods(int idTransaction) throws Exception {
        conn = Connector.getConnection();
        String query = "SELECT * FROM sold_goods NATURAL JOIN goods WHERE id_transaction=?";
        try {
            prepStat = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            prepStat.setString(1, Integer.toString(idTransaction));
            resultSet = prepStat.executeQuery();
            
            ArrayList<Pair<Good, Pair<Integer, Double>>> data = new ArrayList<>();
            
            while (resultSet.next()) {
                Good temp = new Good(
                        Integer.parseInt(resultSet.getString("id_item")),
                        resultSet.getString("name"), 
                        Integer.parseInt(resultSet.getString("stock")), 
                        Double.parseDouble(resultSet.getString("price")), 
                        resultSet.getString("img_source")
                );
                int quantity = Integer.parseInt(resultSet.getString("quantity"));
                double prices = Double.parseDouble(resultSet.getString("prices"));
                Pair<Good, Pair<Integer, Double>> newItem = new Pair<Good, Pair<Integer, Double>>(temp, new Pair<>(quantity, prices));
                data.add(newItem);
            }
            return data;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static Transaction getTransaction(int id) throws Exception {
        conn = Connector.getConnection();
        String query = "SELECT * FROM transactions WHERE id_transaction=?";
        try {
            ArrayList<Pair<Good, Pair<Integer, Double>>> items = getSoldGoods(id);
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, Integer.toString(id));
            resultSet = prepStat.executeQuery();
            
            resultSet.next();
            
            Transaction transaction = new Transaction(
                    Integer.parseInt(resultSet.getString("id_transaction")), 
                    Integer.parseInt(resultSet.getString("id_free_coupon")), 
                    Integer.parseInt(resultSet.getString("id_discount_coupon")), 
                    items, 
                    Double.parseDouble(resultSet.getString("total_price")), 
                    resultSet.getString("datetime"),
                    Double.parseDouble(resultSet.getString("discount"))
            );
            
            return transaction;
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    public static void saveDetails(int id, Pair<Good, Pair<Integer, Double>> details) throws Exception {
        conn = Connector.getConnection();
        String query = "INSERT INTO sold_goods(id_transaction, id_item, quantity, prices) VALUES (?,?,?,?)";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, Integer.toString(id));
            prepStat.setString(2, Integer.toString(details.getFirst().getIdItem()));
            prepStat.setString(3, Integer.toString(details.getSecond().getFirst()));
            prepStat.setString(4, Double.toString(details.getSecond().getSecond()));
            boolean succeed = !prepStat.execute();
            System.out.println(succeed);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static int getMaxId() throws Exception{
        conn = Connector.getConnection();
        String query = "SELECT id_transaction from transactions order by id_transaction desc";
        try {
            prepStat = conn.prepareStatement(query);
            resultSet = prepStat.executeQuery();
            if (resultSet.next()) {
                return Integer.parseInt(resultSet.getString("id_transaction"));
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw  e;
        }
    }
    
    public static void saveTransaction(Transaction transaction) throws Exception {
        conn = Connector.getConnection();
        String query = "INSERT INTO transactions(id_transaction, id_free_coupon, id_discount_coupon, total_price, datetime, discount) VALUES (?,?,?,?,?,?)";
        try {
            int currentid = getMaxId() + 1;
            transaction.setIdTransaction(currentid);
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, Integer.toString(transaction.getIdTransaction()));
            
            if (transaction.getCouponFree() == 0) {
                prepStat.setString(2, null);
            } else {
                prepStat.setString(2, Integer.toString(transaction.getCouponFree()));
            }
            
            if (transaction.getCouponDiscount() == 0) {
                prepStat.setString(3, null);
            } else {
                prepStat.setString(3, Integer.toString(transaction.getCouponDiscount()));
            }
            
            prepStat.setString(4, Double.toString(transaction.getTotalPrice()));
            prepStat.setString(5, transaction.getDatetime());
            prepStat.setString(6, Double.toString(transaction.getDiscount()));
            if (!prepStat.execute()) {
                        
                if (transaction.getCouponDiscount() != 0) {
                    CouponController.deleteCoupon(transaction.getCouponDiscount());
                }
                if (transaction.getCouponFree() != 0) {
                    CouponController.deleteCoupon(transaction.getCouponFree());
                }
                
                for (int i = 0; i < transaction.getItems().size(); i++) {
                    saveDetails(transaction.getIdTransaction(), transaction.getItem(i));
                }
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
}
