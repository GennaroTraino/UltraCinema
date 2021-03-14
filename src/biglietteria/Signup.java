package biglietteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe implementa i metodi che vengono usati da SignupController
 * cioè l'inserimento dell'utente nel database
 * e il controllo se l'utente non è già registrato
 */

public class Signup {
    private Connection connection;


    /**
     * Metodo che consente di verificare se l'utente è gia registrato e quindi presente
     * nel database
     * @param user utente
     * @return valore booleano vero se presente
     * @throws SQLException puo sollevare eccezione sql in caso di problemi
     */
    boolean checkUserRegistration(User user) throws SQLException {
        // Query
        String query = "SELECT * FROM users WHERE email=?";
        // Connessione
        connection = DBConnection.connect();
        // Oggetti per interrogare il db
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());

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


    public void addUserToDatabase(User user) throws SQLException {
        PreparedStatement preparedStatement=null;
        try{
            // Query
            String query = "INSERT INTO users(email,name,password,date) VALUES(?,?,?,?)";
            // Connesione
            connection = DBConnection.connect();
            // Creazione Statement
            preparedStatement = connection.prepareStatement(query);
            // Inserimento stringa
            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2,user.getNome());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getDataDiNascita().toString());

            // Esecuzione query
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura oggetti
            if(connection!=null) connection.close();
            if(preparedStatement!=null) preparedStatement.close();
        }
    }
}
