package biglietteria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Classe Main, Metodo start fa partire interfaccia grafica, il main
 * fa partire la creazione delle tabelle del db
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("WELCOME");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        try {
            DBConnection db = DBConnection.getInstance();
            db.createDBUser();
            db.createDBFilm();
            db.createDBSale();
            db.createDBOrari();
            db.createDBBiglietti();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
