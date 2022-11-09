module com.example.lab10 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens com.example.lab10 to javafx.fxml;
    exports com.example.lab10;
}