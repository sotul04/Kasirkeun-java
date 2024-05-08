/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Suthasoma
 */
public class Transaction {
    
    private int idTransaction;
    private int couponFree;
    private int couponDiscount;
    private ArrayList<Pair<Good, Pair<Integer, Double>>> items = new ArrayList<>();
    private double totalPrice;
    private String datetime;
    private double discount;

    public Transaction(int idTransaction, int couponFree, int couponDiscount, ArrayList<Pair<Good, Pair<Integer, Double>>> items, double totalPrice, String datetime, double discount) {
        this.idTransaction = idTransaction;
        this.couponFree = couponFree;
        this.couponDiscount = couponDiscount;
        this.items = items;
        this.totalPrice = totalPrice;
        this.datetime = datetime;
        this.discount = discount;
    }

    
    /**
     * @return the idTransaction
     */
    public int getIdTransaction() {
        return idTransaction;
    }

    /**
     * @param idTransaction the idTransaction to set
     */
    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    /**
     * @return the couponFree
     */
    public int getCouponFree() {
        return couponFree;
    }

    /**
     * @param couponFree the couponFree to set
     */
    public void setCouponFree(int couponFree) {
        this.couponFree = couponFree;
    }

    /**
     * @return the couponDiscount
     */
    public int getCouponDiscount() {
        return couponDiscount;
    }

    /**
     * @param couponDiscount the couponDiscount to set
     */
    public void setCouponDiscount(int couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    /**
     * @return the items
     */
    public ArrayList<Pair<Good, Pair<Integer, Double>>> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<Pair<Good, Pair<Integer, Double>>> items) {
        this.items = items;
    }

    /**
     * @return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the date time
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * @param datetime the date time to set
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    /**
     * @return the discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    /**
     addition
     */
    public Pair<Good, Pair<Integer, Double>> getItem(int index) {
        return items.get(index);
    }
    
    public Good getItemGood(int index) {
        return items.get(index).getFirst();
    }
    
    public int getItemQuantity(int index) {
        return items.get(index).getSecond().getFirst();
    }
    
    public double getItemPrices(int index) {
        return items.get(index).getSecond().getSecond();
    }
    
    public void setItem(int index, Pair<Good, Pair<Integer, Double>> item) {
        items.set(index, item);
    }
    
    public int getItemPosition(Good item) {
        for (int i = 0; i < items.size(); i++) {
            if (getItemGood(i).equalsGood(item)) {
                return i;
            }
        }
        return -1;
    }
    
    public void add(Pair<Good, Pair<Integer, Double>> item) {
        int pos = getItemPosition(item.getFirst());
        if (pos < 0) {
            items.add(item);
        } else {
            int quantity = item.getSecond().getFirst() + getItemQuantity(pos);
            double prices = item.getSecond().getSecond() + getItemPrices(pos);
            Pair<Good, Pair<Integer, Double>> newItem = new Pair<Good, Pair<Integer, Double>>(item.getFirst(), new Pair<>(quantity, prices));
            items.set(pos, newItem);
        }
    }
    
    public void add(Good good, int quantity, double prices) {
        Pair<Good, Pair<Integer, Double>> newItem = new Pair<Good, Pair<Integer, Double>>(good, new Pair<>(quantity, prices));
        add(newItem);
    }
}
