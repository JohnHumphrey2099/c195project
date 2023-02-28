package com.humphrey.model;
//
import com.humphrey.DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main class.
 */
public class Main extends Application {
    /**
     * Loads the Login screen.
     * @param stage The stage to set.
     * @throws IOException IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/Login.fxml"),bundle);
        Scene scene = new Scene(fxmlLoader.load(), 900, 700);
        stage.setTitle(bundle.getString("login"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method. Opens a connection to the database and launches the JavaFX start method. Closes the connection when the program is finished.
     * @param args Arguments.
     * @throws SQLException SQLException
     */
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}