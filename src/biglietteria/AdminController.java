package biglietteria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Classe di controllo per interfaccia Admin
 */
public class AdminController implements Initializable{

    @FXML
    private ListView<String> listaFilm;
    @FXML
    private ComboBox<String> salaBox;
    @FXML
    private TextField prezzoText;
    @FXML
    private ComboBox<String> periodoReportBox;
    @FXML
    private ComboBox<String> salaReportBox;
    @FXML
    private Label errorLabel;

    ArrayList<String> list;
    String movieName = null,nomeSala = null;
    CommandManager manager = CommandManager.getInstance();
    DBConnection db;
    {
        try {
            db = DBConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Metodo che parte quando si seleziona un film,
     * genera le sale nel combobox
     * @throws SQLException
     */
    public void filmSelected() throws SQLException {
        movieName = listaFilm.getSelectionModel().getSelectedItem();
        if (movieName != null) {
            DBConnection db = DBConnection.getInstance();
            salaBox.getItems().removeAll();
            salaBox.getItems().addAll("A1","A2","A3","A4","A5","A6","A7");
            prezzoText.setText(db.getFilmPrice(movieName).toString());
            errorLabel.setVisible(false);
        }

    }

    /**
     * Implementando Initializable, posso richiamare il metodo initialize per
     * settare la lista dei film e le opzioni dei Combobox
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            list = db.getListaNomiFilm();
            listaFilm.getItems().addAll(list);
            errorLabel.setVisible(false);
            salaReportBox.getItems().removeAll();
            salaReportBox.getItems().addAll("A1","A2","A3","A4","A5","A6","A7");
            periodoReportBox.getItems().removeAll();
            periodoReportBox.getItems().addAll("GIORNALIERO","SETTIMANALE","MENSILE","ANNUALE");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che parte quando è premuto il pulsante Save Button, genera tramite il commandManager
     * l'operazione di aggiunta di un film in una sala
     */
    public void saveButtonPressed() {
        nomeSala = salaBox.getSelectionModel().getSelectedItem();
        Float prezzo = Float.parseFloat(prezzoText.getText());
        manager.execute(new AggiornaFilmSala(movieName,nomeSala));
        manager.execute(new AggiornaPrezzoFilm(movieName,prezzo));
        errorLabel.setText("Salvataggio Riuscito");
        errorLabel.setVisible(true);
    }

    /**
     * Metodo che parte quando è premuto il pulsante report, genera tramite il commandManager
     * l'operazione ReportSala, prendendo in input sala periodo di conteggio per il report
     */
    public void reportPressed() {
        String periodo = periodoReportBox.getSelectionModel().getSelectedItem();
        nomeSala = salaReportBox.getSelectionModel().getSelectedItem();
        if(periodo.equals("") || nomeSala.equals("")) {
            errorLabel.setText("Inserire Prima Tutti i Campi");
            return;
        }
        manager.execute(new ReportSala(nomeSala,periodo));
    }

    /**
     * Metodo per tornare al Login
     * @param actionEvent
     */
    public void exitPressed(ActionEvent actionEvent) {
        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("LOGIN");
            Pane root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
