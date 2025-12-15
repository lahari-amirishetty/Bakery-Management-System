package com.bakery.controller;

import com.bakery.model.DataStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * SalesController handles the sales report screen
 * Displays total sales and products sold
 */
public class SalesController {
    private Stage primaryStage;
    private Scene scene;
    private DataStore dataStore;
    
    public SalesController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.dataStore = DataStore.getInstance();
        createSalesScene();
    }
    
    private void createSalesScene() {
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
        
        Label titleLabel = new Label("Sales Report");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.getStyleClass().add("title-label");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        
        header.getChildren().addAll(backButton, spacer, titleLabel, spacer2, new Label(""));
        
        // Center section - Sales statistics
        VBox centerContent = new VBox(30);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(40));
        
        // Sales summary container
        VBox summaryBox = new VBox(25);
        summaryBox.setAlignment(Pos.CENTER);
        summaryBox.setMaxWidth(600);
        summaryBox.getStyleClass().add("form-container");
        summaryBox.setPadding(new Insets(40));
        
        Label summaryTitle = new Label("📊 Sales Summary");
        summaryTitle.setFont(Font.font("System", FontWeight.BOLD, 24));
        summaryTitle.getStyleClass().add("title-label");
        
        // Total Sales Card
        VBox salesCard = createStatCard(
            "Total Sales Revenue",
            "$" + String.format("%.2f", dataStore.getTotalSales()),
            "#4CAF50"
        );
        
        // Total Products Sold Card
        VBox productsCard = createStatCard(
            "Total Products Sold",
            String.valueOf(dataStore.getTotalProductsSold()),
            "#2196F3"
        );
        
        // Average sale per product
        double avgSale = 0;
        if (dataStore.getTotalProductsSold() > 0) {
            avgSale = dataStore.getTotalSales() / dataStore.getTotalProductsSold();
        }
        
        VBox avgCard = createStatCard(
            "Average Sale per Product",
            "$" + String.format("%.2f", avgSale),
            "#FF9800"
        );
        
        summaryBox.getChildren().addAll(summaryTitle, salesCard, productsCard, avgCard);
        
        centerContent.getChildren().add(summaryBox);
        
        // Set layout
        mainContainer.setTop(header);
        mainContainer.setCenter(centerContent);
        
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
     * Create a styled statistics card
     */
    private VBox createStatCard(String label, String value, String color) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: " + color + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);"
        );
        card.setPrefWidth(500);
        
        Label titleLabel = new Label(label);
        titleLabel.setFont(Font.font("System", FontWeight.MEDIUM, 16));
        titleLabel.setStyle("-fx-text-fill: #6b5b4a;");
        
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 32));
        valueLabel.setStyle("-fx-text-fill: " + color + ";");
        
        card.getChildren().addAll(titleLabel, valueLabel);
        
        return card;
    }
    
    public Scene getScene() {
        return scene;
    }
}
