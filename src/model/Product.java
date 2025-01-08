package model;

public class Product {

    private int id;
    private String name;
    private Amount publicPrice;
    private Amount wholesalerPrice;
    private boolean available;
    private int stock;
    private static int totalProducts;

    public static double EXPIRATION_RATE = 0.60;

    public Product(String name, double wholesalerPrice, boolean available, int stock) {
        this.id = totalProducts + 1;
        this.name = name;
        this.wholesalerPrice = new Amount(wholesalerPrice);
        this.publicPrice = new Amount(wholesalerPrice * 1.2);
        this.available = available;
        this.stock = stock;
        totalProducts++;
    }

    public String getName() {
        return name;
    }

    public Amount getPublicPrice() {
        return publicPrice;
    }

    public void setPublicPrice(double publicPrice) {
        this.publicPrice = new Amount(publicPrice);
    }

    public Amount getWholesalerPrice() {
        return wholesalerPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void expire() {
        this.publicPrice = new Amount(this.wholesalerPrice.getValue() * EXPIRATION_RATE);
    }

    @Override
    public String toString() {
        return "Producto: " + name + ", Precio publico: " + publicPrice + ", Stock " + stock;
    }
}