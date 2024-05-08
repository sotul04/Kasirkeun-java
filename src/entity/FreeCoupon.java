/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Suthasoma
 */
public class FreeCoupon extends Coupon {
    
    private int idItem;
    private int nItem;
    private int idFree;
    private int nFree;

    public FreeCoupon(int idItem, int nItem, int idFree, int nFree, int idCoupon, String code) {
        super(idCoupon, code);
        this.idItem = idItem;
        this.nItem = nItem;
        this.idFree = idFree;
        this.nFree = nFree;
    }

    public FreeCoupon() {
        super();
        this.idItem = 0;
        this.nItem = 0;
        this.idFree = 0;
        this.nFree = 0;
    }

    /**
     * @return the idItem
     */
    public int getIdItem() {
        return idItem;
    }

    /**
     * @param idItem the idItem to set
     */
    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    /**
     * @return the nItem
     */
    public int getnItem() {
        return nItem;
    }

    /**
     * @param nItem the nItem to set
     */
    public void setnItem(int nItem) {
        this.nItem = nItem;
    }

    /**
     * @return the idFree
     */
    public int getIdFree() {
        return idFree;
    }

    /**
     * @param idFree the idFree to set
     */
    public void setIdFree(int idFree) {
        this.idFree = idFree;
    }

    /**
     * @return the nFree
     */
    public int getnFree() {
        return nFree;
    }

    /**
     * @param nFree the nFree to set
     */
    public void setnFree(int nFree) {
        this.nFree = nFree;
    }
}
