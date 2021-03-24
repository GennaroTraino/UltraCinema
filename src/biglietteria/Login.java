package biglietteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * La classe implementa i metodi che vengono richiamati nelle fasi di Login
 * cioè dal LoginController
 */

public class Login {
    private Connection connection;

    /**
     * Metodo che controlla se l'utente ha inserito correttamente email e password
     * @param user
     * @return
     * @throws SQLException
     */
    boolean checkLogin(User user) throws SQLException {
        // Query
        String query = "SELECT * FROM users WHERE email=? AND password=?";
        // Connessione
        connection = DBConnection.connect();
        // Oggetti per interrogare il db
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2,user.getPassword());
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            //Chiusura oggetti
            if(preparedStatement!=null) preparedStatement.close();
            if(resultSet!=null) resultSet.close();
        }

        return false;
    }

    /**
     * Metodo che permette di ricevere i dati di un utente completi (avendo email e password)
     * @param user
     * @return oggetto user (privo di password)
     */
    public User getData(User user) throws SQLException {
        // Query
        String query = "SELECT * FROM users WHERE email=? AND password=?";
        // Connessione
        connection = DBConnection.connect();
        // Oggetti per interrogare il db
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2,user.getPassword());
            resultSet = preparedStatement.executeQuery();

            User u = new User();
            u.setNome(resultSet.getString("name"));
            u.setEmail(resultSet.getString("email"));
            u.setDataDiNascita(LocalDate.parse(resultSet.getString("date")));

            return u;

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            //Chiusura oggetti
            if(preparedStatement!=null) preparedStatement.close();
            if(resultSet!=null) resultSet.close();
        }
        return user;
    }

    /**
     * Metodo controlla se le credenziali inserite sono quelle di Admin e ritorna vero
     * @param user oggetto user che salva le credenziali
     * @return vero se admin, falso altrimenti
     */
    public boolean checkLoginAdmin(User user) {
        return user.getEmail().equalsIgnoreCase("ADMIN") && user.getPassword().equals("admin");
    }
}
