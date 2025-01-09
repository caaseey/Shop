
package main;

import model.Product;
import model.Sale;
import java.util.Scanner;
import model.Amount;


public class Shop {

    private Amount cash = new Amount(100.00);
    private Product[] inventory;
    private int numberProducts;
    private String[] sales;
    private int numberOfSales = 0;

    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new Product[10];
        sales = new String[50];
    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.loadInventory();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Agregar producto");
            System.out.println("3) Agregar stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver total de ventas");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;
                case 8:
                    shop.showTotalSales();
                    break;
                case 10:
                    System.out.println("Saliendo del programa...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while (true);
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory() {
        addProduct(new Product("Manzana", 10.00, true, 10));
        addProduct(new Product("Pera", 20.00, true, 20));
        addProduct(new Product("Hamburguesa", 30.00, true, 30));
        addProduct(new Product("Fresa", 5.00, true, 20));
    }

    /**
     * show current total cash
     */
    private void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct(Product product) {
        if (isInventoryFull()) {
            System.out.println("No se pueden agregar mas productos");
            return;
        }
        inventory[numberProducts] = product;
        numberProducts++;
    }

    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del producto: ");
        String name = scanner.nextLine();
        if (findProduct(name) != null) {
            System.out.println("Error: El producto ya existe en el inventario.");
            return;
        }
        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();
        System.out.print("Stock inicial: ");
        int stock = scanner.nextInt();
        addProduct(new Product(name, wholesalerPrice, true, stock));
        System.out.println("Producto agregado con exito.");
    }

    /**
     * add stock for a specific product
     */
    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            // ask for stock
            System.out.print("Seleccione la cantidad a agregar: ");
            int stock = scanner.nextInt();
            // update stock product
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    /**
     * set a product as expired
     */
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
        } else {
            System.out.println("Producto no encontrado");

        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale() {
        // ask for client name
        Scanner sc = new Scanner(System.in);
        System.out.println("Realizar venta, escribir nombre cliente: ");
        String client = sc.nextLine();

        Amount totalAmount = new Amount(0);
        String name;

                // sale product until input name is not 0
        while (true) {
            System.out.println("Nombre del producto (0 para terminar): ");
            name = sc.nextLine();
            
            if (name.equals("0")) {
                break;
            }
            
            Product product = findProduct(name);
            if (product != null && product.isAvailable()){
                totalAmount.add(product.getPublicPrice());
                product.setStock(product.getStock() - 1);
                if (product.getStock() == 0){
                    product.setAvailable(false);
                } 
                } else {
                    System.out.println("Producto no encontrado o sin stock");
            }
        }

        // show cost total
        totalAmount.setValue(totalAmount.getValue() * TAX_RATE);
        cash.add(totalAmount);
        sales[numberOfSales++] = "Cliente: " + client + ", Total: " + totalAmount;
        System.out.println("Venta realizada con exito, total: " + totalAmount);
    }

    /**
     * show all sales
     */
    private void showSales() {
        System.out.println("Lista de ventas:");
        for (int i = 0; i < numberOfSales; i++) {
            System.out.println(sales[i]);
            }
        }

    private void showTotalSales() {
        Amount totalSales = new Amount(0);
        for (int i = 0; i < numberOfSales; i++) {
            String[] parts = sales[i].split(", Total:");
            double saleAmount = Double.parseDouble(parts[1].replace("euros", ""));
            totalSales.add(new Amount (saleAmount));
        }
            System.out.println("Total de ventas: " + totalSales);
}


    /**
     * check if inventory is full or not
     *
     * @return true if inventory is full
     */
    public boolean isInventoryFull() {
            return numberProducts >= inventory.length;
    }

    /**
     * find product by name
     *
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product != null && product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

}