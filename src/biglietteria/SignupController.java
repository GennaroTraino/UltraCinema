package biglietteria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe controller per interfaccia della registrazione
 */
public class SignupController {
    @FXML
    TextField emailField;
    @FXML
    TextField nameField;
    @FXML
    PasswordField passwordField;
    @FXML
    DatePicker dataField;
    @FXML
    Label errorLabel;

    //Variabili
    private final Signup signup = new Signup();
    private final User user = new User();
    CommandManager manager = CommandManager.getInstance();

    /**
     * Metodo per registrare utente nel database
     * @param actionEvent
     */
    public void registerButtonPressed(ActionEvent actionEvent) throws SQLException, IOException {
        user.setEmail(emailField.getText());
        user.setNome(nameField.getText());
        user.setPassword(passwordField.getText());
        user.setDataDiNascita(dataField.getValue());

        // Controlla se sono stati inseriti campi vuoti
        if(!user.getNome().equals("") &&
                !user.getEmail().equals("") &&
                !user.getPassword().equals("") &&
                !user.getDataDiNascita().toString().equals("")) {
            // Controlla se l'utente non è già registrato
            if(!signup.checkUserRegistration(user)){
                // Inserimento nel database
                signup.addUserToDatabase(user);

                // Apertura nuova finestra
                ((Node)actionEvent.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                primaryStage.setTitle("HOME PAGE");
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Principale.fxml").openStream());
                PrincipaleController pc = loader.getController();
                pc.getUser(user, manager);
                Scene scene = new Scene(root,854,480);
                primaryStage.setScene(scene);
                primaryStage.show();
            } else{
                errorLabel.setText("Errore: l'utente è gia registrato");
            }
        }else{
            errorLabel.setText("Errore: Campi vuoti");
        }
    }


    /**
     * Metodo per il ritorno al Login
     * @param actionEvent
     */
    public void loginButtonPressed(ActionEvent actionEvent) {
        try {
            ((Node)actionEvent.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Registrazione");

            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
