package biglietteria;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PopupReportController {

    @FXML
    private Label bigliettiLabel;
    @FXML
    private Label ricaviLabel;
    @FXML
    private Label reportTime;
    @FXML
    private Label salaLabel;
    @FXML
    private ImageView logoImage;


    public void getData(String nomeSala, int n_rows, float ricavo,String reportPeriodo) {
        bigliettiLabel.setText(String.valueOf(n_rows));
        ricaviLabel.setText(String.valueOf(ricavo));
        reportTime.setText(reportPeriodo);
        salaLabel.setText(nomeSala);
        logoImage.setImage(new Image("biglietteria\\Logo.png"));
    }
}
