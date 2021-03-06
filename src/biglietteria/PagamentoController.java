package biglietteria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Classe che controlla l' interfaccia pagamento
 */
public class PagamentoController {

    @FXML
    private Label usernameLabel;
    @FXML
    private Label paymentLabel;
    @FXML
    private Button payButton;
    @FXML
    private Label priceLabel;


    User user;
    String nomeFilm,nomeSala;
    Float prezzoFilm;
    LocalDateTime dataeOra;
    boolean intero = true;
    private String tipo;
    CommandManager manager;

    /**
     * Metodo che parte quando viene premuto il button Paga.
     * Per scopo didattico il rilevamento dei dati delle carte o dei contanti inseriti è simulato!
     */
    @FXML
    public void Pay() {
        paymentLabel.setText("PAGAMENTO IN CORSO...");
        try {
            manager.execute(new AcquistaBiglietto(nomeSala, nomeFilm, user.getNome(),
                    dataeOra, LocalDateTime.now(), intero, 30.0f));

            switch (tipo) {
                case "carta":
                    manager.execute(new CartaDiCredito("2197201720170",user.getNome(),"23/32","345"));
                    break;
                case "bancomat":
                    manager.execute(new Bancomat("2197201720170",user.getNome(),"23/32","345"));
                    break;
                case "contanti":
                    manager.execute(new Contanti(user.getNome()));
                    break;
            }

            paymentLabel.setText("PAGAMENTO COMPLETATO");
            payButton.setVisible(false);
            //Faccio comparire Popup con il biglietto

            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Popup");
            FXMLLoader loaderbiglietto = new FXMLLoader();
            Pane root = loaderbiglietto.load(getClass().getResource("PopupBiglietto.fxml").openStream());
            PopupBigliettoController pc = loaderbiglietto.getController();
            pc.getData(nomeFilm,nomeSala,dataeOra,user);
            Scene scene = new Scene(root, 600, 200);
            secondaryStage.setScene(scene);
            secondaryStage.show();

        } catch (PostiException postiException) {
            paymentLabel.setText("ERRORE: POSTI ESAURITI");
            priceLabel.setVisible(false);
            payButton.setVisible(false);
        } catch (Exception e) {
            paymentLabel.setText("ERRORE: PROBLEMA CON IL PAGAMENTO");
            e.printStackTrace();
        }
    }


    /**
     * Metodo che si attiva alla partenza dell' interfaccia per ricevere i dati dal PrincipaleController
     * @param user
     * @param nomeFilm
     * @param nomeSala
     * @param prezzoFilm
     * @param dataeOra
     * @param tipo
     * @param manager
     */
    public void getData(User user, String nomeFilm, String nomeSala, Float prezzoFilm, LocalDateTime dataeOra,String tipo,CommandManager manager) {
        this.user = user;
        this.nomeFilm = nomeFilm;
        this.nomeSala = nomeSala;
        this.prezzoFilm = prezzoFilm;
        this.dataeOra = dataeOra;
        this.tipo = tipo;
        this.manager = manager;

        usernameLabel.setText(user.getNome());
        priceLabel.setText("EURO: " + prezzoFilm.toString());

        String giorno = LocalDate.now().getDayOfWeek().toString();
        if(giorno.equals("SATURDAY") || giorno.equals("SUNDAY")){
            this.prezzoFilm += (prezzoFilm/100)*50;
        } else
            if(user.getDataDiNascita().isBefore(LocalDate.now().minusYears(18))) {
                intero = true;
                this.prezzoFilm -= (prezzoFilm/100)*50;
            }


        switch (tipo) {
            case "carta":
                paymentLabel.setText("INSERIRE LA CARTA");
                break;
            case "bancomat":
                paymentLabel.setText("INSERIRE BANCOMAT");
                break;
            case "contanti":
                paymentLabel.setText("INSERIRE CONTANTI");
                break;
        }
    }

    /**
     * Metodo si attiva quando viene premuto il pulsante home back
     * @param actionEvent
     */
    public void homeBack(ActionEvent actionEvent) {

        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("HOME PAGE");
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("Principale.fxml").openStream());
            PrincipaleController pc = loader.getController();
            pc.getUser(user,manager);
            Scene scene = new Scene(root, 854, 480);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
