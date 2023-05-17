module com.example.lab9 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab9 to javafx.fxml;
    exports com.example.lab9;
    exports com.example.lab9.Entity;
    opens com.example.lab9.Entity to javafx.fxml;
}