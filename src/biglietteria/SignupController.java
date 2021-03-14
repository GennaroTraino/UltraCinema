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
import java.time.LocalDate;

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
    private Signup signup = new Signup();
    private User user = new User();

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
                !user.getDataDiNascita().equals("")) {
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
                pc.getUser(user.getNome(), user.getEmail());
                Scene scene = new Scene(root,700,425);
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
     * Ritorno al Login
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
