module mvh.app {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    opens rw.app to javafx.fxml;
    exports rw.app;
}