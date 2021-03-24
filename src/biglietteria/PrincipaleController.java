package biglietteria;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Classe controller per interfaccia grafica Principale.
 * Gestisce tutti gli eventi grafici
 */
public class PrincipaleController implements Initializable {

    @FXML
    private ComboBox comboBoxOrari;
    @FXML
    Button logoutButton;
    @FXML
    Label usernameLabel;
    @FXML
    ListView<String> movieList;
    @FXML
    private ImageView logoImage;
    @FXML
    private Label errorLabel;
    @FXML
    private Button refundButton;

    //Variabili
    CommandManager manager;
    private User user = new User();
    String movieName = null;
    Float prezzoFilm = 0.0f;
    String nomeSala = null;
    LocalDateTime dataeOra = null;

    /**
     * Metodo finalizzato a ricevere dal LoginForm: username dell'utente che ha
     * effettuato l' accesso e istanza del CommandManager
     * @param user utente che ha effettuato l'acceso
     * @param manager
     */
    void getUser(User user, CommandManager manager) {
        this.user = user;
        this.manager = manager;
        usernameLabel.setText(user.getNome());
        logoImage.setImage(new Image("biglietteria\\Logo.png"));
    }

    /**
     * Metodo per effettuare il logout alla pressione del tasto logout in home page
     * @param actionEvent parametro per aprire nuove finestre
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

    /**
     * Metodo invocato quando viene premuto il pulsante per pagare con carta di credito
     * @param actionEvent parametro per aprire nuove finestre
     */
    public void creditCardPay(ActionEvent actionEvent) {
        if(movieName == null) {
            errorLabel.setText("ERRORE: SELEZIONA UN ORARIO ED UNA DATA");
            return;
        }
        dataeOra = LocalDateTime.parse(comboBoxOrari.getSelectionModel().getSelectedItem().toString());

        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("PAY WINDOW");
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("Pagamento.fxml").openStream());
            PagamentoController pag = loader.getController();
            pag.getData(user,movieName,nomeSala,prezzoFilm,dataeOra,"carta",manager);
            Scene scene = new Scene(root, 854, 480);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo invocato quando viene premuto il pulsante per pagare con bancomat
     * @param actionEvent parametro per aprire nuove finestre
     */
    public void bancomatPay(ActionEvent actionEvent) {
        if(movieName == null) {
            errorLabel.setText("ERRORE: SELEZIONA UN ORARIO ED UNA DATA");
            return;
        }
        dataeOra = LocalDateTime.parse(comboBoxOrari.getSelectionModel().getSelectedItem().toString());

        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("PAY WINDOW");
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("Pagamento.fxml").openStream());
            PagamentoController pag = loader.getController();
            pag.getData(user,movieName,nomeSala,prezzoFilm,dataeOra,"bancomat",manager);
            Scene scene = new Scene(root, 854, 480);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo invocato quando viene premuto il pulsante per pagare in contanti
     * @param actionEvent parametro per aprire nuove finestre
     */
    public void contantiPay(ActionEvent actionEvent) {
        if(movieName == null) {
            errorLabel.setText("ERRORE: SELEZIONA UN ORARIO ED UNA DATA");
            return;
        }
        dataeOra = LocalDateTime.parse(comboBoxOrari.getSelectionModel().getSelectedItem().toString());

        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("PAY WINDOW");
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("Pagamento.fxml").openStream());
            PagamentoController pag = loader.getController();
            pag.getData(user,movieName,nomeSala,prezzoFilm,dataeOra,"contanti",manager);
            Scene scene = new Scene(root, 854, 480);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inizializzazione, utile per scaricare la lista dei film
     * Metodo richiamabile implementando la classe Initializable
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CommandManager manager = CommandManager.getInstance();
        refundButton.setVisible(!manager.isStackUserEmpty());

        try {
            DBConnection db = DBConnection.getInstance();
            ArrayList<String> lista = db.getListaNomiFilm();
            movieList.getItems().addAll(lista);
        } catch (SQLException e) {
            errorLabel.setText("ERRORE: IMPOSSIBILE CONTATTARE IL DATABASE");
            e.printStackTrace();
        }
    }

    /**
     * Metodo richiamato per effettuare annullamento di un operazione, entro 10 minuti
     */
    public void Refund() {
        if(manager.undo()) {
            errorLabel.setText("BIGLIETTO ANNULLATO, RIMBORSO EFFETTUATO!!!");
            if (manager.isStackUserEmpty())
                refundButton.setVisible(false);
        } else {
            errorLabel.setText("NESSUN RIMBORSO POSSIBILE");
        }
    }

    public void showTickets() {
        try {
            DBConnection db = DBConnection.getInstance();
            ArrayList<Biglietto> tickets = db.getTickets(usernameLabel.getText());

            int i=0;
            ArrayList<Stage> stages = new ArrayList<>();
            for (Biglietto b : tickets) {
                stages.add(new Stage());
                stages.get(i).setTitle("Ticket");
                FXMLLoader loaderbiglietto = new FXMLLoader();
                Pane root = loaderbiglietto.load(getClass().getResource("PopupBiglietto.fxml").openStream());
                PopupBigliettoController pc = loaderbiglietto.getController();
                pc.getData(b.getFilm(),b.getSala(),b.getDataeora(),user);
                Scene scene = new Scene(root, 600, 200);
                stages.get(i).setScene(scene);
                stages.get(i).show();
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO EXCEPTION GENNY!!");
            e.printStackTrace();
        }

    }
}
