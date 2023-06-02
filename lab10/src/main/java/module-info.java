module com.example.lab10 {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
            
    opens com.example.lab10 to javafx.fxml;
    exports com.example.lab10;
    exports com.example.lab10.controller;
    opens com.example.lab10.controller to javafx.fxml;
}