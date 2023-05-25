module com.humphrey.c195project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.humphrey.model to javafx.fxml;
    exports com.humphrey.model;
    exports com.humphrey.controller;
    opens com.humphrey.controller to javafx.fxml;
}