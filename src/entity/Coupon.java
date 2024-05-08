/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Suthasoma
 */
public class Coupon {
    
    protected int idCoupon;
    private String code;

    public Coupon(int idCoupon, String code) {
        this.idCoupon = idCoupon;
        this.code = code;
    }
    
    public Coupon() {
        this.idCoupon = 0;
        code = "";
    }

    /**
     * @return the idCoupon
     */
    public int getIdCoupon() {
        return idCoupon;
    }

    /**
     * @param idCoupon the idCoupon to set
     */
    public void setIdCoupon(int idCoupon) {
        this.idCoupon = idCoupon;
    }
    
    public boolean isAvailable() {
        return idCoupon > 0;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
}



