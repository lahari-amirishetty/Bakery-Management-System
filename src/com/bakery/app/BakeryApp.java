package com.bakery.app;

import com.bakery.controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * BakeryApp - Main application class
 * Entry point for the Bakery Management System
 */
public class BakeryApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Set application title
            primaryStage.setTitle("Bakery Management System");
            
            // Set window size and prevent resizing for consistent UI
            primaryStage.setWidth(900);
            primaryStage.setHeight(650);
            primaryStage.setResizable(false);
            
            // Start with login screen
            LoginController loginController = new LoginController(primaryStage);
            primaryStage.setScene(loginController.getScene());
            
            // Show the application
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error starting application: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
