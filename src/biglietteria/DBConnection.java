package biglietteria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Stabilisce una connessione con il database -
 * Crea e Popola le tabelle -
 * Usa Design Pattern Singleton al fine di avere una sola istanza
 */

public class DBConnection {
    //Istanza unica
    private static DBConnection instance;

    //Costruttore privato PATTERN SINGLETON
    private DBConnection(){}

    /**
     * Metodo che consente la creazione e la connesione al database
     * Il database utilizzato è l'sqlite
     */

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:C:/sqlite/mydatabase.db");
    }

    /**
     * Metodo del Singleton che crea l'unica istanza del database
     * @return istanza unica del database
     * @throws SQLException può sollevare un eccezione SQL in caso di errori
     */
    public static DBConnection getInstance() throws SQLException {
        if(instance == null)
            instance = new DBConnection();
        else if(connect().isClosed())
            instance = new DBConnection();

        return instance;
    }


    /**
     * Metodo Creazione Tabella Degli Utenti Registrati
     * I campi obbligatori per ogni utente sono Nome, Email, Password e DatadiNascita
     * @throws SQLException, puo lanciare un eccezione nel caso di errore
     */
    void createDBUser() throws SQLException {
        //Oggetto per la connessione al DB e Statement per interrogarlo
        Connection connection=null;
        Statement statement=null;

        try{
            // CREAZIONE TABELLA
            String query = "CREATE TABLE IF NOT EXISTS users(" +
                    "email varchar(50) PRIMARY KEY,"+
                    "name varchar(50) NOT NULL,"+
                    "password varchar(20) NOT NULL,"+
                    "date text NOT NULL)";

            connection = connect();
            statement = connection.createStatement();
            // Eseguo la query
            statement.execute(query);
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura Oggetti Connessione e Statement
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
    }

    /**
     * Metodo Creazione Tabella Dei film
     * I campi obbligatori per film sono Nome, genere, regista e Prezzo (biglietto)
     * @throws SQLException, puo lanciare un eccezione nel caso di errore
     */
    void createDBFilm() throws SQLException {
        //Oggetto per la connessione al DB e Statement per interrogarlo
        Connection connection=null;
        Statement statement=null;

        try{
            // CREAZIONE TABELLA
            String query = "CREATE TABLE IF NOT EXISTS films(" +
                    "nomefilm varchar(50) PRIMARY KEY,"+
                    "regista varchar(50) NOT NULL,"+
                    "genere varchar(50) NOT NULL,"+
                    "prezzo real NOT NULL)";

            connection = connect();
            statement = connection.createStatement();
            // Eseguo la query
            statement.execute(query);
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura Oggetti Connessione e Statement
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
    }


    /**
     * Metodo Creazione Tabella Delle sale
     * I campi obbligatori per film sono Nomesala, orario, nposti e filmassegnato.
     * Nomesala e orario formano una chiave primaria
     * @throws SQLException, puo lanciare un eccezione nel caso di errore
     */
    void createDBSale() throws SQLException {
        //Oggetto per la connessione al DB e Statement per interrogarlo
        Connection connection=null;
        Statement statement=null;

        try{
            // CREAZIONE TABELLA
            String query = "CREATE TABLE IF NOT EXISTS sale(" +
                    "nomesala varchar(50) PRIMARY KEY,"+
                    "nposti INTEGER NOT NULL,"+
                    "filmassegnato VARCHAR(50) NOT NULL)";

            connection = connect();
            statement = connection.createStatement();
            // Eseguo la query
            statement.execute(query);
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura Oggetti Connessione e Statement
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
    }

    /**
     * Metodo Creazione Tabella Degli orari di una sala
     * I campi obbligatori per film sono Nomesala, orario
     * @throws SQLException, puo lanciare un eccezione nel caso di errore
     */
    void createDBOrari() throws SQLException {
        //Oggetto per la connessione al DB e Statement per interrogarlo
        Connection connection=null;
        Statement statement=null;

        try{
            // CREAZIONE TABELLA
            String query = "CREATE TABLE IF NOT EXISTS orari(" +
                    "nomesala varchar(50) PRIMARY KEY,"+
                    "orario TEXT NOT NULL)";

            connection = connect();
            statement = connection.createStatement();
            // Eseguo la query
            statement.execute(query);
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura Oggetti Connessione e Statement
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
    }


    void createDBBiglietti() throws SQLException {
        //Oggetto per la connessione al DB e Statement per interrogarlo
        Connection connection=null;
        Statement statement=null;

        try{
            // CREAZIONE TABELLA
            String query = "CREATE TABLE IF NOT EXISTS biglietti (" +
                    "nomesala varchar(50) NOT NULL,"+
                    "nomefilm varchar(50) NOT NULL,"+
                    "name varchar(50) NOT NULL,"+
                    "data TEXT NOT NULL,"+
                    "ora TEXT NOT NULL,"+
                    "dataoraacquisto TEXT NOT NULL,"+
                    "intero BOOLEAN NOT NULL,"+
                    "prezzo REAL NOT NULL)";
            connection = connect();
            statement = connection.createStatement();
            // Eseguo la query
            statement.execute(query);
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura Oggetti Connessione e Statement
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
    }
}
