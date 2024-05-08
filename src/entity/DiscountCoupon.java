/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Suthasoma
 */
public class DiscountCoupon extends Coupon {
    
    private int percentage;
    private double minBuy;
    private double maxDiscount;

    public DiscountCoupon(int percentage, double minBuy, double maxDiscount, int idCoupon, String code) {
        super(idCoupon, code);
        this.percentage = percentage;
        this.minBuy = minBuy;
        this.maxDiscount = maxDiscount;
    }

    public DiscountCoupon() {
        super();
        this.minBuy = 0;
        this.percentage = 0;
        this.maxDiscount = 0;
    }

    /**
     * @return the percentage
     */
    public int getPercentage() {
        return percentage;
    }

    /**
     * @param percentage the percentage to set
     */
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    /**
     * @return the maxDiscount
     */
    public double getMaxDiscount() {
        return maxDiscount;
    }

    /**
     * @param maxDiscount the maxDiscount to set
     */
    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }
    
    public double discount(double price) {
        
        double disc = price*percentage;
        if (disc > maxDiscount) {
            disc = maxDiscount;
        }
        return disc;
    }

    /**
     * @return the minBuy
     */
    public double getMinBuy() {
        return minBuy;
    }

    /**
     * @param minBuy the minBuy to set
     */
    public void setMinBuy(double minBuy) {
        this.minBuy = minBuy;
    }
}
