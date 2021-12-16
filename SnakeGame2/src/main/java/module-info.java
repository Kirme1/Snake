module com.example.snakegame2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens com.example.snakegame2 to javafx.fxml;
    exports com.example.snakegame2;
}