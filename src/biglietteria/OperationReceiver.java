package biglietteria;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * COMMAND PATTERN: RECEIVER Class.
 * Svolge i comandi definiti con le interfacce CommandUser & CommandAdmin
 */

public class OperationReceiver {

    public OperationReceiver() { }

    /**
     * PATTERN COMMAND:(CommandAdmin) Metodo del receiver che implementa
     * l' operazione di generazione del report sulle sale.
     * Richiamabile Da Admin Tramite CommandManager
     */
    public void reportSala(String nomeSala, String reportPeriodo) {
        //Variabili
        float ricavo = 0.0f;

        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try{
            //QUERY
            String query = "SELECT * From ((biglietti JOIN sale ON (biglietti.nomesala = sale.nomesala)) JOIN films" +
                    " ON (biglietti.nomefilm = films.nomefilm)) WHERE sale.nomesala = ?";
            // Connessione
            connection = DBConnection.connect();
            // Creazione Statement
            preparedStatement = connection.prepareStatement(query);
            // Inserimento stringa
            preparedStatement.setString (1,nomeSala);
            //Eseguo la query di vista e tengo il risultato in un oggetto ResultSet
            ResultSet rs = preparedStatement.executeQuery();

            //Definisco variabili per calcolo di somma entrate, numero biglietti
            int n_rows = 0;
            int posti = rs.getInt("nposti");

            //PERIODO stabilisce la data per il report
            LocalDate reportDate;
            switch (reportPeriodo.toUpperCase()) {
                case "GIORNALIERO":
                    reportDate = LocalDate.now();
                    break;
                case "SETTIMANALE":
                    reportDate = LocalDate.now().minusDays(7);
                    break;
                case "MENSILE":
                    reportDate = LocalDate.now().minusMonths(1);
                    break;
                case "ANNUALE":
                    reportDate = LocalDate.now().minusMonths(12);
                    break;
                default:
                    reportDate = LocalDate.now().minusMonths(36);
                    break;
            }

            //Scorri tuple restituite dalla query e mi calcolo in base al periodo stabilito dal report le informazioni
            while (rs.next()) {
                if(LocalDate.parse(rs.getString("data")).toEpochDay() > reportDate.toEpochDay()){
                    n_rows = rs.getRow();
                    ricavo += rs.getFloat("prezzo");
                }
            }
            //TODO POPUP REPORT
            System.out.println("Dati: " + n_rows + " " + ricavo);

            try {
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Popup");
                FXMLLoader loader = new FXMLLoader();
                Pane root = null;
                root = loader.load(getClass().getResource("PopupReport.fxml").openStream());
                PopupReportController pc = loader.getController();
                pc.getData(nomeSala,n_rows,ricavo,reportPeriodo);
                Scene scene = new Scene(root,700,425);
                secondaryStage.setScene(scene);
                secondaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura oggetti
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(preparedStatement!=null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * PATTERN COMMAND:(CommandAdmin) Metodo del receiver che implementa
     * l'operazione di aggiornamento del prezzo di biglietto di un film.
     * Richiamabile Da Admin Tramite CommandManager
     */
    public void aggiornaPrezzoFilm(String nomeFilm, Float prezzoAggiornato) {

        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try{
            // Query
            String query = "UPDATE films SET prezzo = ? WHERE nomefilm = ?";
            // Connessione
            connection = DBConnection.connect();
            // Creazione Statement
            preparedStatement = connection.prepareStatement(query);
            // Inserimento stringa
            preparedStatement.setFloat (1,prezzoAggiornato);
            preparedStatement.setString(2,nomeFilm);

            // Esecuzione query
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura oggetti
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(preparedStatement!=null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }

    /**
     * PATTERN COMMAND:(CommandAdmin) Metodo del receiver che implementa
     * l'operazione di aggiunta di un film al database.
     * Richiamabile Da Admin Tramite CommandManager
     */
    public void aggiungiFilm(Film film) {
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try{
            // Query
            String query = "INSERT INTO films(nomefilm,regista,genere,prezzo) VALUES(?,?,?,?)";
            // Connessione
            connection = DBConnection.connect();
            // Creazione Statement
            preparedStatement = connection.prepareStatement(query);
            // Inserimento stringa
            preparedStatement.setString (1,film.getNome());
            preparedStatement.setString(2,film.getRegista());
            preparedStatement.setString(3,film.getGenere());
            preparedStatement.setFloat(4,film.getPrezzo());

            // Esecuzione query
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("film gia aggiunto");

        }finally {
            // Chiusura oggetti
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(preparedStatement!=null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * PATTERN COMMAND:(CommandAdmin) Metodo del receiver che implementa
     * l'operazione di aggiornamento di una sala per il suo film in proiezione.
     * Richiamabile Da Admin Tramite CommandManager
     */
    public void aggiornaFilmSala(String nomeFilm,String nomeSala) {
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try{
            // Query
            String query = "UPDATE sale SET filmassegnato = ? WHERE nomesala = ?";
            // Connessione
            connection = DBConnection.connect();
            // Creazione Statement
            preparedStatement = connection.prepareStatement(query);
            // Inserimento stringa
            preparedStatement.setString (1,nomeFilm);
            preparedStatement.setString(2,nomeSala);

            // Esecuzione query
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura oggetti
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * PATTERN COMMAND:(CommandUser) Metodo del receiver che implementa
     * l'operazione di acquisto di un biglietto da parte di un user.
     * Richiamabile Da User tramite CommandUser
     */
    //TODO da fare tutta
    public void acquistaBiglietto(String nomeSala, String nomeFilm, String name,
                                  LocalDateTime dataOra, boolean intero, Float prezzo) {

        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try{
            // Query
            String query = "INSERT INTO biglietti(nomesala,nomefilm,name,data,ora,intero,prezzo) VALUES(?,?,?,?,?,?,?)";
            // Connessione
            connection = DBConnection.connect();
            // Creazione Statement
            preparedStatement = connection.prepareStatement(query);
            // Inserimento stringa
            preparedStatement.setString (1,nomeSala);
            preparedStatement.setString(2,nomeFilm);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,dataOra.toLocalDate().toString());
            preparedStatement.setString(5,dataOra.toLocalTime().toString());
            preparedStatement.setBoolean(6,intero);
            preparedStatement.setFloat(7,prezzo);

            // Esecuzione query
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("biglietto gia venduto");
        }finally {
            // Chiusura oggetti
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(preparedStatement!=null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * PATTERN COMMAND:(CommandUser) Metodo del receiver che implementa
     * l'operazione di undo sull' acquisto di un biglietto da parte di un user.
     * Richiamabile Da User tramite CommandUser
     */
    //TODO da fare tutta
    public void undoBiglietto(String nomeSala,String name,LocalDateTime dataOra) {
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try{
            // Query
            String query = "DELETE FROM biglietti WHERE (nomesala = ? AND name = ? AND data = ? AND ora = ?)";
            // Connessione
            connection = DBConnection.connect();
            // Creazione Statement
            preparedStatement = connection.prepareStatement(query);
            // Inserimento stringa
            preparedStatement.setString (1,nomeSala);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,dataOra.toLocalDate().toString());
            preparedStatement.setString(4,dataOra.toLocalTime().toString());
            System.out.println("DATA" + dataOra.toLocalDate().toString());
            System.out.println("ORA" + dataOra.toLocalTime().toString());
//TODO SE NON SONO PASSATI GIA DIECI MINUTI
            if (LocalDateTime.now().minusMinutes(10).getMinute() > dataOra.getMinute()) {
                System.out.println("Passati gia 10 minuti");
            } else {
                //POSSO ESEGUIRE LA QUERY
                preparedStatement.executeUpdate();
            }
        }catch (Exception e){
            System.out.println("Nessun biglietto trovato");
        }finally {
            // Chiusura oggetti
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(preparedStatement!=null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
