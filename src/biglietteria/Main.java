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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Benvenuto");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        CommandManager manager = CommandManager.getInstance();
        manager.execute(new ReportSala("A1","giornaliero"));

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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //TUTTE LE PROVE CHE VUOI
        LocalTime localTime = LocalTime.of(12,30,0);
        LocalDate localDate = LocalDate.of(1995,8,24);
        LocalDateTime ldt = LocalDateTime.of(1995,8,24,15,00);

        Film f = new Film("pee","foo","genetica",33.22f);
        Sala s = new Sala("A",2);
        s.setFilm(f);



    }
}
