module org.example.clinicmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.clinicmanagementsystem.main to javafx.fxml;
    opens org.example.clinicmanagementsystem.controller to javafx.fxml;

    exports org.example.clinicmanagementsystem.main;
}