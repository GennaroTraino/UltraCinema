package biglietteria;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PopupBigliettoController {

    @FXML
    private ImageView logoImage;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label filmLabel;
    @FXML
    private Label salaLabel;
    @FXML
    private Label dataLabel;
    @FXML
    private Label oraLabel;
    @FXML
    private Label tipoBigliettoLabel;
    @FXML
    private AnchorPane Pane;

    String nomeFilm=null,nomeSala=null;
    LocalDateTime dataeOra=null;
    User user=null;

    public void getData(String nomeFilm, String nomeSala, LocalDateTime dataeOra,User user){
        this.user = user;
        this.nomeFilm = nomeFilm;
        this.dataeOra = dataeOra;
        this.nomeSala = nomeSala;

        logoImage.setImage(new Image("biglietteria\\Logo.png"));
        usernameLabel.setText(user.getNome());
        filmLabel.setText(nomeFilm);
        salaLabel.setText(nomeSala);
        dataLabel.setText(dataeOra.toLocalDate().toString());
        oraLabel.setText(dataeOra.toLocalTime().toString());


        if(user.getDataDiNascita().isBefore(LocalDate.now().minusYears(18))) {
            tipoBigliettoLabel.setText("INTERO");
        } else {
            tipoBigliettoLabel.setText("RIDOTTO");
            Pane.setStyle("-fx-background-color: #888888");
        }
    }
}
