/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Casey
 */
public class Amount {

    private double value;
    private final String currency = "euros";

    public Amount(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void add(Amount other) {
        this.value += other.getValue();
    }

    public void subtract(Amount other) {
        this.value -= other.getValue();
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }
}