package com.bakery.controller;

import com.bakery.model.DataStore;
import com.bakery.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * OrderController handles the ordering/billing screen
 * Allows users to select products and create orders
 */
public class OrderController {
    private Stage primaryStage;
    private Scene scene;
    private DataStore dataStore;
    private TableView<Product> productTable;
    
    public OrderController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.dataStore = DataStore.getInstance();
        createOrderScene();
    }
    
    private void createOrderScene() {
        // Main container
        BorderPane mainContainer = new BorderPane();
        mainContainer.getStyleClass().add("main-container");
        
        // Top section - Header with back button
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20));
        
        Button backButton = new Button("← Back");
        backButton.getStyleClass().add("secondary-button");
        backButton.setOnAction(e -> {
            HomeController homeController = new HomeController(primaryStage);
            primaryStage.setScene(homeController.getScene());
        });
        
        Label titleLabel = new Label("Order Now");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.getStyleClass().add("title-label");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        
        header.getChildren().addAll(backButton, spacer, titleLabel, spacer2, new Label(""));
        
        // Center section - Product table
        VBox centerContent = new VBox(15);
        centerContent.setPadding(new Insets(20));
        
        Label tableLabel = new Label("Available Products");
        tableLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        // Create product table
        productTable = new TableView<>();
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        idCol.setPrefWidth(60);
        
        TableColumn<Product, String> nameCol = new TableColumn<>("Product Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        nameCol.setPrefWidth(200);
        
        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setPrefWidth(120);
        
        TableColumn<Product, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(100);
        
        TableColumn<Product, Integer> stockCol = new TableColumn<>("Available Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        stockCol.setPrefWidth(120);
        
        productTable.getColumns().addAll(idCol, nameCol, categoryCol, priceCol, stockCol);
        
        // Load products
        loadProducts();
        
        centerContent.getChildren().addAll(tableLabel, productTable);
        
        // Bottom section - Order form
        VBox orderForm = new VBox(15);
        orderForm.setPadding(new Insets(20));
        orderForm.getStyleClass().add("form-container");
        
        Label orderLabel = new Label("Place Order");
        orderLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        HBox inputRow = new HBox(15);
        inputRow.setAlignment(Pos.CENTER_LEFT);
        
        Label productIdLabel = new Label("Product ID:");
        productIdLabel.setPrefWidth(100);
        TextField productIdField = new TextField();
        productIdField.setPromptText("Enter Product ID");
        productIdField.setPrefWidth(150);
        
        Label quantityLabel = new Label("Quantity:");
        quantityLabel.setPrefWidth(100);
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter Quantity");
        quantityField.setPrefWidth(150);
        
        Button orderButton = new Button("Place Order");
        orderButton.getStyleClass().add("primary-button");
        orderButton.setPrefWidth(150);
        
        inputRow.getChildren().addAll(
            productIdLabel, productIdField,
            quantityLabel, quantityField,
            orderButton
        );
        
        Label statusLabel = new Label();
        statusLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        
        orderForm.getChildren().addAll(orderLabel, inputRow, statusLabel);
        
        // Order button action
        orderButton.setOnAction(e -> {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                
                if (quantity <= 0) {
                    showError(statusLabel, "Quantity must be greater than 0");
                    return;
                }
                
                Product product = dataStore.findProductById(productId);
                if (product == null) {
                    showError(statusLabel, "Product not found!");
                    return;
                }
                
                if (product.getQuantity() < quantity) {
                    showError(statusLabel, "Insufficient stock! Available: " + product.getQuantity());
                    return;
                }
                
                // Calculate total
                double total = product.getPrice() * quantity;
                
                // Show bill confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Order Summary");
                alert.setHeaderText("Order Details");
                alert.setContentText(
                    "Product: " + product.getProductName() + "\n" +
                    "Quantity: " + quantity + "\n" +
                    "Price per item: $" + String.format("%.2f", product.getPrice()) + "\n" +
                    "Total Amount: $" + String.format("%.2f", total) + "\n\n" +
                    "Confirm order?"
                );
                
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Update stock
                        dataStore.updateStock(productId, quantity);
                        // Add to sales
                        dataStore.addSale(total, quantity);
                        
                        // Show success
                        showSuccess(statusLabel, "Order placed successfully! Total: $" + String.format("%.2f", total));
                        
                        // Clear fields
                        productIdField.clear();
                        quantityField.clear();
                        
                        // Refresh table
                        loadProducts();
                    }
                });
                
            } catch (NumberFormatException ex) {
                showError(statusLabel, "Please enter valid numbers");
            }
        });
        
        // Set layout
        mainContainer.setTop(header);
        mainContainer.setCenter(centerContent);
        mainContainer.setBottom(orderForm);
        
        // Create scene
        scene = new Scene(mainContainer, 900, 650);
        try {
            String cssPath = getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.err.println("Warning: Could not load CSS file. Using default styling.");
        }
    }
    
    private void loadProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(dataStore.getProducts());
        productTable.setItems(products);
    }
    
    private void showError(Label label, String message) {
        label.setText("❌ " + message);
        label.setStyle("-fx-text-fill: #d32f2f;");
    }
    
    private void showSuccess(Label label, String message) {
        label.setText("✓ " + message);
        label.setStyle("-fx-text-fill: #388e3c;");
    }
    
    public Scene getScene() {
        return scene;
    }
}
