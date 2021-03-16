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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @FXML
    private ImageView logoImage;

    //Variabili
    private User user = new User();
    ObservableList list = FXCollections.observableArrayList();
    String movieName = null;
    Float prezzoFilm = 0.0f;
    String nomeSala = null;
    LocalDateTime dataeOra = null;


    public void loadData() {
        list.removeAll(list);
        String a = "pio";
        String b = "Hit";
        String c = "Avatar";
        movieList.getItems().addAll(a,b,c);
    }


    /**
     * Metodo finalizzato a ricevere dal LoginForm l'username dell'utente che ha
     * effettuato l' accesso
     * @param user utente che ha effettuato l'acceso
     */
    void getUser(User user) {
        this.user = user;
        usernameLabel.setText(user.getNome());
        logoImage.setImage(new Image("biglietteria\\Logo.png"));
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
     * Metodo che salva la stringa quando viene scelto il film dalla listaFilm.
     * Genera la scelta degli orari di proiezione sul comboBoxOrari
     * Ricava la prima sala libera dove il film è in proiezione
     */
    public void filmSelected() throws SQLException {
        movieName = movieList.getSelectionModel().getSelectedItem();
        if (movieName != null) {
            DBConnection db = DBConnection.getInstance();
            ArrayList<LocalDateTime> list = db.getDataTimeFilm(movieName);
            prezzoFilm = db.getFilmPrice(movieName);
            nomeSala = db.getSalaFilm(movieName);


            list.sort(LocalDateTime::compareTo);
            comboBoxOrari.getItems().removeAll();
            comboBoxOrari.getItems().addAll(list);
        }

    }



    public void creditCardPay(ActionEvent actionEvent) {
        dataeOra = LocalDateTime.parse(comboBoxOrari.getSelectionModel().getSelectedItem().toString());

        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("PAY WINDOW");
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("Pagamento.fxml").openStream());
            PagamentoController pag = loader.getController();
            pag.getData(user,movieName,nomeSala,prezzoFilm,dataeOra,"carta");
            Scene scene = new Scene(root, 854, 480);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void bancomatPay(ActionEvent actionEvent) {
        dataeOra = LocalDateTime.parse(comboBoxOrari.getSelectionModel().getSelectedItem().toString());

        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("PAY WINDOW");
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("Pagamento.fxml").openStream());
            PagamentoController pag = loader.getController();
            pag.getData(user,movieName,nomeSala,prezzoFilm,dataeOra,"bancomat");
            Scene scene = new Scene(root, 854, 480);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void contantiPay(ActionEvent actionEvent) {
        dataeOra = LocalDateTime.parse(comboBoxOrari.getSelectionModel().getSelectedItem().toString());

        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("PAY WINDOW");
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("Pagamento.fxml").openStream());
            PagamentoController pag = loader.getController();
            pag.getData(user,movieName,nomeSala,prezzoFilm,dataeOra,"contanti");
            Scene scene = new Scene(root, 854, 480);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
