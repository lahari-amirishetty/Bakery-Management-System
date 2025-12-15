module com.bakery.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    exports com.bakery.app;
    exports com.bakery.controller;
    exports com.bakery.model;
    
    opens com.bakery.app to javafx.graphics;
    opens com.bakery.controller to javafx.fxml;
    opens com.bakery.model to javafx.base;
}
