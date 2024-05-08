/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import entity.FreeCoupon;
import entity.DiscountCoupon;
import java.util.Random;
/**
 *
 * @author Suthasoma
 */
public class CouponController {
    
    private static Connection conn;
    private static PreparedStatement prepStat;
    private static ResultSet resultSet;
    
    public static boolean isFreeCouponExist(String code) throws Exception{
        conn = Connector.getConnection();
        String query = "SELECT * FROM coupon NATURAL JOIN free_coupon WHERE code=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, code);
            resultSet = prepStat.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
        return false;
    }
    
    public static boolean isDiscountCouponExist(String code) throws Exception{
        conn = Connector.getConnection();
        String query = "SELECT * FROM coupon NATURAL JOIN discount_coupon WHERE code=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, code);
            resultSet = prepStat.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
        return false;
    }
    
    public static FreeCoupon getFreeCoupon(String code) throws Exception{
        conn = Connector.getConnection();
        String query = "SELECT * FROM coupon NATURAL JOIN free_coupon WHERE code=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, code);
            resultSet = prepStat.executeQuery();
            if (resultSet.next()) {
                return new FreeCoupon(
                        Integer.parseInt(resultSet.getString("id_item")),
                        Integer.parseInt(resultSet.getString("n_item")),
                        Integer.parseInt(resultSet.getString("id_free")), 
                        Integer.parseInt(resultSet.getString("n_free")), 
                        Integer.parseInt(resultSet.getString("id_coupon")), 
                        code
                );
            }
        } catch (Exception e) {
            throw e;
        }
        return new FreeCoupon();
    }
    
    public static DiscountCoupon getDiscountCoupon(String code) throws Exception{
        conn = Connector.getConnection();
        String query = "SELECT * FROM coupon NATURAL JOIN discount_coupon WHERE code=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, code);
            resultSet = prepStat.executeQuery();
            if (resultSet.next()) {
                return new DiscountCoupon(
                        Integer.parseInt(resultSet.getString("percentage")),
                        Double.parseDouble(resultSet.getString("min_buy")),
                        Double.parseDouble(resultSet.getString("max_discount")), 
                        Integer.parseInt(resultSet.getString("id_coupon")), 
                        code
                );
            }
        } catch (Exception e) {
            throw e;
        }
        return new DiscountCoupon();
    }
    
    public static boolean deleteCoupon(String code) throws Exception{
        conn = Connector.getConnection();
        String query = "SELECT * FROM coupon WHERE code=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, code);
            resultSet = prepStat.executeQuery();
            if (resultSet.next()) {
                String type = resultSet.getString("type");
                String id = resultSet.getString("id_coupon");
                String query1 = "DELETE FROM coupon where code=?";
                if (type.equals("free_coupon")) {
                    String query2 = "DELETE FROM free_coupon where id_coupon=?";
                    prepStat = conn.prepareStatement(query2);
                    prepStat.setString(1, id);
                    if (prepStat.execute()) {
                        return false;
                    }
                } else {
                    String query2 = "DELETE FROM discount_coupon where id_coupon=?";
                    prepStat = conn.prepareStatement(query2);
                    prepStat.setString(1, id);
                    if (prepStat.execute()) {
                        return false;
                    }
                }
                prepStat = conn.prepareStatement(query1);
                prepStat.setString(1, code);
                return !prepStat.execute();
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static boolean deleteCoupon(int idc) throws Exception{
        conn = Connector.getConnection();
        String query = "SELECT * FROM coupon WHERE id_coupon=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, Integer.toString(idc));
            resultSet = prepStat.executeQuery();
            if (resultSet.next()) {
                String type = resultSet.getString("type");
                String id = resultSet.getString("id_coupon");
                String query1 = "DELETE FROM coupon where id_coupon=?";
                if (type.equals("free_coupon")) {
                    String query2 = "DELETE FROM free_coupon where id_coupon=?";
                    prepStat = conn.prepareStatement(query2);
                    prepStat.setString(1, id);
                    if (prepStat.execute()) {
                        return false;
                    }
                } else {
                    String query2 = "DELETE FROM discount_coupon where id_coupon=?";
                    prepStat = conn.prepareStatement(query2);
                    prepStat.setString(1, id);
                    if (prepStat.execute()) {
                        return false;
                    }
                }
                prepStat = conn.prepareStatement(query1);
                prepStat.setString(1, id);
                return !prepStat.execute();
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    public static String generateCouponCode() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        
        int length = 6 + random.nextInt(5);

        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('A' + random.nextInt(26));
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
    
    public static boolean isCodeAlreadyUsed(String code) throws Exception{
        conn = Connector.getConnection();
        String query = "SELECT * FROM coupon WHERE code=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, code);
            resultSet = prepStat.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static boolean addFreeCoupon(FreeCoupon coupon) throws Exception{
        conn = Connector.getConnection();
        try {
            String newCode = generateCouponCode();
            while (isCodeAlreadyUsed(newCode)) {
                newCode = generateCouponCode();
            }
            String query1 = "INSERT INTO coupon(code,type) VALUES (?,?)";
            prepStat = conn.prepareStatement(query1);
            prepStat.setString(1, newCode);
            prepStat.setString(2, "free_coupon");
            
            if (!prepStat.execute()) {
                
                String query11 = "SELECT * FROM coupon WHERE code=?";
                prepStat = conn.prepareStatement(query11);
                prepStat.setString(1, newCode);
                resultSet = prepStat.executeQuery();
                resultSet.next();
                String id = resultSet.getString("id_coupon");
                
                String query2 = "INSERT INTO free_coupon(id_coupon,id_item,n_item,id_free,n_free) VALUES (?,?,?,?,?)";
                prepStat = conn.prepareStatement(query2);
                prepStat.setString(1, id);
                prepStat.setString(2, Integer.toString(coupon.getIdItem()));
                prepStat.setString(3, Integer.toString(coupon.getnItem()));
                prepStat.setString(4, Integer.toString(coupon.getIdFree()));
                prepStat.setString(5, Integer.toString(coupon.getnFree()));
                
                return !prepStat.execute();
            } else {
                return false;
            }
        } catch (Exception e) {
            throw  e;
        }
    }
    
    public static boolean addDiscountCoupon(DiscountCoupon coupon) throws  Exception {
        conn = Connector.getConnection();
        try {
            String newCode = generateCouponCode();
            while (isCodeAlreadyUsed(newCode)) {
                newCode = generateCouponCode();
            }
            String query1 = "INSERT INTO coupon(code,type) VALUES (?,?)";
            prepStat = conn.prepareStatement(query1);
            prepStat.setString(1, newCode);
            prepStat.setString(2, "free_coupon");
            
            if (!prepStat.execute()) {
                
                String query11 = "SELECT * FROM coupon WHERE code=?";
                prepStat = conn.prepareStatement(query11);
                prepStat.setString(1, newCode);
                resultSet = prepStat.executeQuery();
                resultSet.next();
                String id = resultSet.getString("id_coupon");
                
                String query2 = "INSERT INTO discount_coupon(id_coupon,percentage,max_discount) VALUES (?,?,?)";
                prepStat = conn.prepareStatement(query2);
                prepStat.setString(1, id);
                prepStat.setString(2, Integer.toString(coupon.getPercentage()));
                prepStat.setString(3, Double.toString(coupon.getMaxDiscount()));
                
                return !prepStat.execute();
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static ResultSet getAllCoupon() throws Exception {
        conn = Connector.getConnection();
        String query = "SELECT * FROM coupon";
        try {
            prepStat = conn.prepareStatement(query);
            return prepStat.executeQuery();
        } catch (Exception e) {
            throw  e;
        }
    }
    
    public static boolean setEditedFreeCoupon(FreeCoupon coupon) throws Exception {
        conn = Connector.getConnection();
        String query = "UPDATE free_coupon SET id_item=?, n_item=?, id_free=?, n_free=? where id_coupon=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, Integer.toString(coupon.getIdItem()));
            prepStat.setString(2, Integer.toString(coupon.getnItem()));
            prepStat.setString(3, Integer.toString(coupon.getIdFree()));
            prepStat.setString(4, Integer.toString(coupon.getnFree()));
            prepStat.setString(5, Integer.toString(coupon.getIdCoupon()));
            return !prepStat.execute();
        } catch (Exception e) {
            throw  e;
        }
    }
    
    public static boolean setEditedDiscountCoupon(DiscountCoupon coupon) throws Exception {
        conn = Connector.getConnection();
        String query = "UPDATE discount_coupon SET percentage=?, max_discount=? where id_coupon=?";
        try {
            prepStat = conn.prepareStatement(query);
            prepStat.setString(1, Integer.toString(coupon.getPercentage()));
            prepStat.setString(2, Double.toString(coupon.getMaxDiscount()));
            return !prepStat.execute();
        } catch (Exception e) {
            throw  e;
        }
    }
}
