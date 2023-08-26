module com.example.hostelmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hostelmanagementsystem to javafx.fxml;
    exports com.example.hostelmanagementsystem;
}