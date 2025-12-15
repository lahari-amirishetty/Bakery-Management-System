package com.bakery.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Optional;

/**
 * HomeController handles the main home/dashboard screen
 * Shows navigation buttons to different features
 */
public class HomeController {
    private Stage primaryStage;
    private Scene scene;
    
    public HomeController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createHomeScene();
    }
    
    private void createHomeScene() {
        // Main container
        BorderPane mainContainer = new BorderPane();
        mainContainer.getStyleClass().add("main-container");
        
        // Top section - Header
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(30, 20, 20, 20));
        
        Label titleLabel = new Label("🍰 Bakery Management System");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 32));
        titleLabel.getStyleClass().add("title-label");
        
        Label subtitleLabel = new Label("Welcome! Choose an option below");
        subtitleLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        subtitleLabel.getStyleClass().add("subtitle-label");
        
        header.getChildren().addAll(titleLabel, subtitleLabel);
        
        // Center section - Menu buttons
        GridPane menuGrid = new GridPane();
        menuGrid.setAlignment(Pos.CENTER);
        menuGrid.setHgap(30);
        menuGrid.setVgap(30);
        menuGrid.setPadding(new Insets(40));
        
        // Create menu buttons
        Button orderButton = createMenuButton("🛒 Order Now", "Take customer orders");
        Button manageButton = createMenuButton("📦 Manage Products", "Add, update, delete products");
        Button salesButton = createMenuButton("📊 Sales Report", "View sales statistics");
        Button exitButton = createMenuButton("❌ Exit", "Close application");
        
        // Button actions
        orderButton.setOnAction(e -> {
            OrderController orderController = new OrderController(primaryStage);
            primaryStage.setScene(orderController.getScene());
        });
        
        manageButton.setOnAction(e -> {
            ProductController productController = new ProductController(primaryStage);
            primaryStage.setScene(productController.getScene());
        });
        
        salesButton.setOnAction(e -> {
            SalesController salesController = new SalesController(primaryStage);
            primaryStage.setScene(salesController.getScene());
        });
        
        exitButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("Click OK to close the application.");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                primaryStage.close();
            }
        });
        
        // Add buttons to grid (2x2 layout)
        menuGrid.add(orderButton, 0, 0);
        menuGrid.add(manageButton, 1, 0);
        menuGrid.add(salesButton, 0, 1);
        menuGrid.add(exitButton, 1, 1);
        
        // Set layout
        mainContainer.setTop(header);
        mainContainer.setCenter(menuGrid);
        
        // Create scene
        scene = new Scene(mainContainer, 900, 650);
        try {
            String cssPath = getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.err.println("Warning: Could not load CSS file. Using default styling.");
        }
    }
    
    /**
     * Create a styled menu button
     */
    private Button createMenuButton(String text, String description) {
        VBox buttonContent = new VBox(8);
        buttonContent.setAlignment(Pos.CENTER);
        
        Label mainText = new Label(text);
        mainText.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        Label descText = new Label(description);
        descText.setFont(Font.font("System", FontWeight.NORMAL, 12));
        descText.setStyle("-fx-text-fill: #6b5b4a;");
        
        buttonContent.getChildren().addAll(mainText, descText);
        
        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setPrefSize(350, 120);
        button.getStyleClass().add("menu-button");
        
        return button;
    }
    
    public Scene getScene() {
        return scene;
    }
}
