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
import java.util.Optional;

/**
 * ProductController handles product management
 * Allows adding, updating, and deleting products
 */
public class ProductController {
    private Stage primaryStage;
    private Scene scene;
    private DataStore dataStore;
    private TableView<Product> productTable;
    
    private TextField idField, nameField, priceField, quantityField;
    private ComboBox<String> categoryCombo;
    
    public ProductController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.dataStore = DataStore.getInstance();
        createProductScene();
    }
    
    private void createProductScene() {
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
        
        Label titleLabel = new Label("Manage Products");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.getStyleClass().add("title-label");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        
        header.getChildren().addAll(backButton, spacer, titleLabel, spacer2, new Label(""));
        
        // Center section - Product table and form
        VBox centerContent = new VBox(20);
        centerContent.setPadding(new Insets(20));
        
        // Product table
        Label tableLabel = new Label("Product List");
        tableLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        productTable = new TableView<>();
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productTable.setPrefHeight(250);
        
        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        
        TableColumn<Product, String> nameCol = new TableColumn<>("Product Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        
        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        
        TableColumn<Product, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        TableColumn<Product, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        productTable.getColumns().addAll(idCol, nameCol, categoryCol, priceCol, stockCol);
        
        // Load products
        loadProducts();
        
        // Product form
        VBox formBox = new VBox(15);
        formBox.getStyleClass().add("form-container");
        formBox.setPadding(new Insets(20));
        
        Label formLabel = new Label("Product Details");
        formLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        // Form fields
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(12);
        
        // Product ID
        Label idLabel = new Label("Product ID:");
        idField = new TextField();
        idField.setPromptText("Auto-generated for new products");
        idField.setEditable(false);
        
        // Product Name
        Label nameLabel = new Label("Product Name:");
        nameField = new TextField();
        nameField.setPromptText("Enter product name");
        
        // Category
        Label categoryLabel = new Label("Category:");
        categoryCombo = new ComboBox<>();
        categoryCombo.setItems(FXCollections.observableArrayList("Cake", "Pastry", "Bread", "Others"));
        categoryCombo.setPromptText("Select category");
        categoryCombo.setPrefWidth(200);
        
        // Price
        Label priceLabel = new Label("Price ($):");
        priceField = new TextField();
        priceField.setPromptText("Enter price");
        
        // Quantity
        Label quantityLabel = new Label("Quantity:");
        quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
        
        formGrid.add(idLabel, 0, 0);
        formGrid.add(idField, 1, 0);
        formGrid.add(nameLabel, 0, 1);
        formGrid.add(nameField, 1, 1);
        formGrid.add(categoryLabel, 0, 2);
        formGrid.add(categoryCombo, 1, 2);
        formGrid.add(priceLabel, 0, 3);
        formGrid.add(priceField, 1, 3);
        formGrid.add(quantityLabel, 0, 4);
        formGrid.add(quantityField, 1, 4);
        
        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addButton = new Button("Add Product");
        addButton.getStyleClass().add("primary-button");
        addButton.setPrefWidth(130);
        
        Button updateButton = new Button("Update Product");
        updateButton.getStyleClass().add("primary-button");
        updateButton.setPrefWidth(130);
        
        Button deleteButton = new Button("Delete Product");
        deleteButton.getStyleClass().add("danger-button");
        deleteButton.setPrefWidth(130);
        
        Button clearButton = new Button("Clear Form");
        clearButton.getStyleClass().add("secondary-button");
        clearButton.setPrefWidth(130);
        
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, clearButton);
        
        Label statusLabel = new Label();
        statusLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        
        formBox.getChildren().addAll(formLabel, formGrid, buttonBox, statusLabel);
        
        centerContent.getChildren().addAll(tableLabel, productTable, formBox);
        
        // Button actions
        addButton.setOnAction(e -> addProduct(statusLabel));
        updateButton.setOnAction(e -> updateProduct(statusLabel));
        deleteButton.setOnAction(e -> deleteProduct(statusLabel));
        clearButton.setOnAction(e -> clearForm());
        
        // Table selection - populate form
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                populateForm(newVal);
            }
        });
        
        // Set layout
        mainContainer.setTop(header);
        mainContainer.setCenter(new ScrollPane(centerContent));
        
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
    
    private void populateForm(Product product) {
        idField.setText(String.valueOf(product.getProductId()));
        nameField.setText(product.getProductName());
        categoryCombo.setValue(product.getCategory());
        priceField.setText(String.valueOf(product.getPrice()));
        quantityField.setText(String.valueOf(product.getQuantity()));
    }
    
    private void clearForm() {
        idField.clear();
        nameField.clear();
        categoryCombo.setValue(null);
        priceField.clear();
        quantityField.clear();
        productTable.getSelectionModel().clearSelection();
    }
    
    private void addProduct(Label statusLabel) {
        try {
            String name = nameField.getText().trim();
            String category = categoryCombo.getValue();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            
            if (name.isEmpty() || category == null) {
                showError(statusLabel, "Please fill all fields");
                return;
            }
            
            if (price <= 0 || quantity < 0) {
                showError(statusLabel, "Price must be positive and quantity cannot be negative");
                return;
            }
            
            int newId = dataStore.getNextProductId();
            Product product = new Product(newId, name, category, price, quantity);
            dataStore.addProduct(product);
            
            showSuccess(statusLabel, "Product added successfully!");
            clearForm();
            loadProducts();
            
        } catch (NumberFormatException e) {
            showError(statusLabel, "Please enter valid numbers for price and quantity");
        }
    }
    
    private void updateProduct(Label statusLabel) {
        if (idField.getText().isEmpty()) {
            showError(statusLabel, "Please select a product to update");
            return;
        }
        
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText().trim();
            String category = categoryCombo.getValue();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            
            if (name.isEmpty() || category == null) {
                showError(statusLabel, "Please fill all fields");
                return;
            }
            
            if (price <= 0 || quantity < 0) {
                showError(statusLabel, "Price must be positive and quantity cannot be negative");
                return;
            }
            
            Product product = new Product(id, name, category, price, quantity);
            dataStore.updateProduct(product);
            
            showSuccess(statusLabel, "Product updated successfully!");
            clearForm();
            loadProducts();
            
        } catch (NumberFormatException e) {
            showError(statusLabel, "Please enter valid numbers for price and quantity");
        }
    }
    
    private void deleteProduct(Label statusLabel) {
        if (idField.getText().isEmpty()) {
            showError(statusLabel, "Please select a product to delete");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this product?");
        alert.setContentText("This action cannot be undone.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int id = Integer.parseInt(idField.getText());
            dataStore.deleteProduct(id);
            
            showSuccess(statusLabel, "Product deleted successfully!");
            clearForm();
            loadProducts();
        }
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
