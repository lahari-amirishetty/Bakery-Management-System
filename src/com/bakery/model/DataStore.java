package com.bakery.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DataStore class handles file-based storage for products and sales data
 * Saves and loads data to persist between application restarts
 */
public class DataStore {
    private static final String PRODUCTS_FILE = "products.dat";
    private static final String SALES_FILE = "sales.dat";
    
    private List<Product> products;
    private double totalSales;
    private int totalProductsSold;
    
    // Singleton instance
    private static DataStore instance;
    
    // Private constructor for singleton
    private DataStore() {
        products = new ArrayList<>();
        totalSales = 0.0;
        totalProductsSold = 0;
        loadData();
        initializeDefaultProducts();
    }
    
    // Get singleton instance
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }
    
    // Initialize with some default products if empty
    private void initializeDefaultProducts() {
        if (products.isEmpty()) {
            products.add(new Product(1, "Chocolate Cake", "Cake", 24.99, 20));
            products.add(new Product(2, "Black Forest Cake", "Cake", 29.99, 15));
            products.add(new Product(3, "Vanilla Cupcake", "Cake", 2.99, 50));
            products.add(new Product(4, "Croissant", "Pastry", 3.99, 30));
            products.add(new Product(5, "Danish Pastry", "Pastry", 4.99, 25));
            products.add(new Product(6, "Wheat Bread", "Bread", 5.99, 20));
            products.add(new Product(7, "Baguette", "Bread", 4.50, 18));
            products.add(new Product(8, "Cinnamon Roll", "Others", 3.50, 35));
            saveProducts();
        }
    }
    
    // Get all products
    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
    
    // Add a new product
    public void addProduct(Product product) {
        products.add(product);
        saveProducts();
    }
    
    // Update existing product
    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == updatedProduct.getProductId()) {
                products.set(i, updatedProduct);
                saveProducts();
                return;
            }
        }
    }
    
    // Delete product
    public void deleteProduct(int productId) {
        products.removeIf(p -> p.getProductId() == productId);
        saveProducts();
    }
    
    // Find product by ID
    public Product findProductById(int productId) {
        for (Product p : products) {
            if (p.getProductId() == productId) {
                return p;
            }
        }
        return null;
    }
    
    // Update stock after order
    public boolean updateStock(int productId, int quantity) {
        Product product = findProductById(productId);
        if (product != null && product.getQuantity() >= quantity) {
            product.setQuantity(product.getQuantity() - quantity);
            saveProducts();
            return true;
        }
        return false;
    }
    
    // Add to sales
    public void addSale(double amount, int quantity) {
        totalSales += amount;
        totalProductsSold += quantity;
        saveSalesData();
    }
    
    // Get total sales
    public double getTotalSales() {
        return totalSales;
    }
    
    // Get total products sold
    public int getTotalProductsSold() {
        return totalProductsSold;
    }
    
    // Get next product ID
    public int getNextProductId() {
        int maxId = 0;
        for (Product p : products) {
            if (p.getProductId() > maxId) {
                maxId = p.getProductId();
            }
        }
        return maxId + 1;
    }
    
    // Save products to file
    @SuppressWarnings("unchecked")
    private void saveProducts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCTS_FILE))) {
            oos.writeObject(products);
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }
    
    // Load products from file
    @SuppressWarnings("unchecked")
    private void loadProducts() {
        File file = new File(PRODUCTS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                products = (List<Product>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading products: " + e.getMessage());
                products = new ArrayList<>();
            }
        }
    }
    
    // Save sales data to file
    private void saveSalesData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SALES_FILE))) {
            oos.writeDouble(totalSales);
            oos.writeInt(totalProductsSold);
        } catch (IOException e) {
            System.err.println("Error saving sales data: " + e.getMessage());
        }
    }
    
    // Load sales data from file
    private void loadSalesData() {
        File file = new File(SALES_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                totalSales = ois.readDouble();
                totalProductsSold = ois.readInt();
            } catch (IOException e) {
                System.err.println("Error loading sales data: " + e.getMessage());
                totalSales = 0.0;
                totalProductsSold = 0;
            }
        }
    }
    
    // Load all data
    private void loadData() {
        loadProducts();
        loadSalesData();
    }
}
