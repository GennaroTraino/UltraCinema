package biglietteria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class LoginController {
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    @FXML
    Label errorLabel;

    //Variabili
    private final Login login = new Login();
    private User user = new User();

    /**
     * Metodo Login, si attiva quando viene premuto il puslante Login
     * @param actionEvent avvia una nuova finestra utente Principale.FXML
     *             oppure admin ADMIN.FXML
     * @throws IOException lancia un eccezione nel caso si verifichino degli errori
     */

    public void loginButtonPressed(ActionEvent actionEvent) throws SQLException, IOException {
        String email = emailField.getText();
        String password = passwordField.getText();
        user.setEmail(email);
        user.setPassword(password);

        if (!(email.equals("")) && !(password.equals(""))) {

            if (login.checkLoginAdmin(user)) {
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                primaryStage.setTitle("ADMIN CONTROL PANEL");
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("PrincipaleAdmin.fxml").openStream());
                AdminController ad = loader.getController();
                //user = login.getData(user);
                Scene scene = new Scene(root, 854, 480);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
            if (login.checkLogin(user)) {
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                primaryStage.setTitle("HOME PAGE");
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Principale.fxml").openStream());
                PrincipaleController pc = loader.getController();

                //preleva i dati dell'utente completi prima di entrare
                user = login.getData(user);
                pc.getUser(user);
                Scene scene = new Scene(root, 854, 480);
                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                errorLabel.setText("Errore: username e password errati");
            }
        }
        else {
            errorLabel.setText("Errore: spazi vuoti");
        }
    }

    public void registerButtonPressed(ActionEvent actionEvent) {
        try {
            ((Node)actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Registrazione");

            Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            primaryStage.setScene(new Scene(root, 854, 480));
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}

