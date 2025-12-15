package com.bakery.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * LoginController handles the login screen
 * Default credentials: admin / admin123
 */
public class LoginController {
    private Stage primaryStage;
    private Scene scene;
    
    public LoginController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createLoginScene();
    }
    
    private void createLoginScene() {
        // Main container
        VBox mainContainer = new VBox(30);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(40));
        mainContainer.getStyleClass().add("main-container");
        
        // Title
        Label titleLabel = new Label("Bakery Management System");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.getStyleClass().add("title-label");
        
        // Subtitle/Welcome message
        Label welcomeLabel = new Label("Welcome Back!");
        welcomeLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        welcomeLabel.getStyleClass().add("subtitle-label");
        
        // Login form container
        VBox formContainer = new VBox(20);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setMaxWidth(400);
        formContainer.setPadding(new Insets(40));
        formContainer.getStyleClass().add("form-container");
        
        // Username field
        VBox usernameBox = new VBox(8);
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefHeight(40);
        usernameField.getStyleClass().add("text-field");
        usernameBox.getChildren().addAll(usernameLabel, usernameField);
        
        // Password field
        VBox passwordBox = new VBox(8);
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefHeight(40);
        passwordField.getStyleClass().add("text-field");
        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        
        // Login button
        Button loginButton = new Button("Login");
        loginButton.setPrefHeight(45);
        loginButton.setPrefWidth(200);
        loginButton.setFont(Font.font("System", FontWeight.BOLD, 16));
        loginButton.getStyleClass().add("primary-button");
        
        // Error label
        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        
        // Login button action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please enter both username and password");
                errorLabel.setVisible(true);
            } else if (username.equals("admin") && password.equals("admin123")) {
                // Successful login - go to home screen
                HomeController homeController = new HomeController(primaryStage);
                primaryStage.setScene(homeController.getScene());
            } else {
                errorLabel.setText("Invalid username or password");
                errorLabel.setVisible(true);
            }
        });
        
        // Enter key to login
        passwordField.setOnAction(e -> loginButton.fire());
        
        // Add all elements to form
        formContainer.getChildren().addAll(
            usernameBox,
            passwordBox,
            errorLabel,
            loginButton
        );
        
        // Add all to main container
        mainContainer.getChildren().addAll(
            titleLabel,
            welcomeLabel,
            formContainer
        );
        
        // Create scene
        scene = new Scene(mainContainer, 900, 650);
        try {
            // Try modern styles first, fallback to default
            String cssPath = getClass().getResource("/modern-styles.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.err.println("Info: Using default JavaFX styling");
        }
    }
    
    public Scene getScene() {
        return scene;
    }
}
