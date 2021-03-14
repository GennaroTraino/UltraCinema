package biglietteria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseDragEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PrincipaleController implements Initializable {

    @FXML
    ComboBox comboBoxOrari;
    @FXML
    Button logoutButton;
    @FXML
    Label usernameLabel;
    @FXML
    ListView<String> movieList;

    //Variabili
    private User user = new User();
    ObservableList list = FXCollections.observableArrayList();

    public void loadData() {
        list.removeAll(list);
        String a = "Harry Potter";
        String b = "Hit";
        String c = "Avatar";
        movieList.getItems().addAll(a,b,c);
    }


    /**
     * Metodo finalizzato a ricevere dal LoginForm l'username dell'utente che ha
     * effettuato l'accesso
     * @param username utente che ha effettuato l'acceso
     */
    void getUser(String username,String email) throws SQLException {
        this.user.setNome(username);
        this.user.setEmail(email);
        usernameLabel.setText(username);
    }


    /**
     * Metodo per effettuare il logout alla pressione del tasto logout in home page
     * @param actionEvent
     */
    public void logoutButtonPressed(ActionEvent actionEvent) {
        try {
            ((Node)actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Login");

            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo che salva la stringa quando viene scelto il film dal comboBoxFilm.
     * E Genera la scelta degli orari di proiezione sul comboBoxOrari
     */
    public void filmSelected(MouseDragEvent mouseDragEvent) {
        String movie = movieList.getSelectionModel().getSelectedItem();
        if (movie == null) {
            //TODO
        }
    }

    /**
     * Metodo che salva l'orario di proiezione scelto
     */
    public void timeSelected(MouseDragEvent mouseDragEvent) {
    }

    /**
     * Inizializzazione, utile per scaricare la lista dei film
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

}
