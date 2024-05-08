/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Suthasoma
 */
public class Pair <K, V> {
    private K first;
    private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return the first
     */
    public K getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(K first) {
        this.first = first;
    }

    /**
     * @return the second
     */
    public V getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(V second) {
        this.second = second;
    }
}
