package biglietteria;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Classe che si occupa di gestire connessione al database e operazioni di creazione tabelle
 * e principali query:
 * Stabilisce una connessione con il database -
 * Crea e Popola le tabelle -
 * Metodi per ricevere liste di elementi memorizzati nel DB
 * Usa Design Pattern Singleton al fine di avere una sola istanza
 */

public class DBConnection {
    //Istanza unica
    private static DBConnection instance;

    //Costruttore privato PATTERN SINGLETON
    private DBConnection() {
    }

    /**
     * Metodo che consente la creazione e la connessione al database
     * Il database utilizzato è SQLite
     */

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:lib/mydatabase.db");
    }

    /**
     * Metodo del Singleton che crea un unica istanza del database
     * @return istanza unica del database
     * @throws SQLException può sollevare un eccezione SQL in caso di errori
     */
    public static DBConnection getInstance() throws SQLException {
        if (instance == null)
            instance = new DBConnection();
        else if (connect().isClosed())
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
        Connection connection = null;
        Statement statement = null;

        try {
            // CREAZIONE TABELLA
            String query = "CREATE TABLE IF NOT EXISTS users(" +
                    "email varchar(50) PRIMARY KEY," +
                    "name varchar(50) NOT NULL," +
                    "password varchar(20) NOT NULL," +
                    "date text NOT NULL)";

            connection = connect();
            statement = connection.createStatement();
            // Eseguo la query
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiusura Oggetti Connessione e Statement
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * Metodo Creazione Tabella Dei film
     * I campi obbligatori per film sono Nome, genere, regista e Prezzo (biglietto)
     *
     * @throws SQLException, puo lanciare un eccezione nel caso di errore
     */
    void createDBFilm() throws SQLException {
        //Oggetto per la connessione al DB e Statement per interrogarlo
        Connection connection = null;
        Statement statement = null;

        try {
            // CREAZIONE TABELLA
            String query = "CREATE TABLE IF NOT EXISTS films(" +
                    "nomefilm varchar(50) PRIMARY KEY," +
                    "regista varchar(50) NOT NULL," +
                    "genere varchar(50) NOT NULL," +
                    "prezzo real NOT NULL)";

            connection = connect();
            statement = connection.createStatement();
            // Eseguo la query
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiusura Oggetti Connessione e Statement
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * Metodo Creazione Tabella Delle sale
     * I campi obbligatori per film sono nomeSala, orario, nposti e filmAssegnato.
     * nomeSala e orario formano una chiave primaria
     *
     * @throws SQLException, puo lanciare un eccezione nel caso di errore
     */
    void createDBSale() throws SQLException {
        //Oggetto per la connessione al DB e Statement per interrogarlo
        Connection connection = null;
        Statement statement = null;

        try {
            // CREAZIONE TABELLA
            String query = "CREATE TABLE IF NOT EXISTS sale(" +
                    "nomesala varchar(50) PRIMARY KEY," +
                    "nposti INTEGER NOT NULL," +
                    "filmassegnato VARCHAR(50) NOT NULL)";

            connection = connect();
            statement = connection.createStatement();
            // Eseguo la query
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiusura Oggetti Connessione e Statement
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * Metodo Creazione Tabella Degli orari di una sala
     * I campi obbligatori per film sono Nomesala, orario
     *
     * @throws SQLException, puo lanciare un eccezione nel caso di errore
     */
    void createDBOrari() throws SQLException {
        //Oggetto per la connessione al DB e Statement per interrogarlo
        Connection connection = null;
        Statement statement = null;

        try {
            // CREAZIONE TABELLA
            String query = "CREATE TABLE IF NOT EXISTS orari(" +
                    "nomesala varchar(50) NOT NULL ," +
                    "orario TEXT NOT NULL," +
                    "FOREIGN KEY (nomesala) REFERENCES sale(nomesala))";

            connection = connect();
            statement = connection.createStatement();
            // Eseguo la query
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiusura Oggetti Connessione e Statement
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * Metodo Creazione Tabella Dei Biglietti
     * @throws SQLException, puo lanciare un eccezione nel caso di errore
     */
    void createDBBiglietti() throws SQLException {
        //Oggetto per la connessione al DB e Statement per interrogarlo
        Connection connection = null;
        Statement statement = null;

        try {
            // CREAZIONE TABELLA
            String query = "CREATE TABLE IF NOT EXISTS biglietti (" +
                    "nomesala varchar(50) NOT NULL," +
                    "nomefilm varchar(50) NOT NULL," +
                    "name varchar(50) NOT NULL," +
                    "data TEXT NOT NULL," +
                    "ora TEXT NOT NULL," +
                    "dataoraacquisto TEXT NOT NULL," +
                    "intero BOOLEAN NOT NULL," +
                    "prezzo REAL NOT NULL)";
            connection = connect();
            statement = connection.createStatement();
            // Eseguo la query
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiusura Oggetti Connessione e Statement
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * Metodo che ritorna gli orari di un film (parametro di input)
     *
     * @param nomeFilm il nome del film in questione
     * @return ritorna una lista di orari e data fino a 3 giorni dopo
     * @throws SQLException
     */
    public ArrayList<LocalDateTime> getDataTimeFilm(String nomeFilm) throws SQLException {
        //Oggetto per la connessione al DB e Statement per interrogarlo
        Connection connection = null;
          try {
            String query = "SELECT * FROM ((films JOIN sale ON films.nomefilm = sale.filmassegnato)" +
                    "JOIN orari ON sale.nomesala = orari.nomesala) WHERE filmassegnato = ?";
            connection = connect();
            PreparedStatement ps = connection.prepareStatement(query);
            // Inserimento stringa
            ps.setString(1, nomeFilm);
            //Eseguo la query di vista e tengo il risultato in un oggetto ResultSet
            ResultSet rs = ps.executeQuery();

            ArrayList<LocalDateTime> list = new ArrayList<>();
            while (rs.next()) {
                list.add(LocalDateTime.of(LocalDate.now(), LocalTime.parse(rs.getString("orario"))));
                list.add(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.parse(rs.getString("orario"))));
                list.add(LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.parse(rs.getString("orario"))));
                list.add(LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.parse(rs.getString("orario"))));
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiusura Oggetti Connessione e Statement
            if (connection != null) connection.close();
        }

        return null;
    }

    /**
     * Metodo che prende in input il nome di un film e restituisce il suo prezzo
     * @param movieName
     * @return prezzo del film
     */
    public Float getFilmPrice(String movieName) throws SQLException {
        Connection connection = null;
        try {
            String query = "SELECT * FROM ((films JOIN sale ON films.nomefilm = sale.filmassegnato)" +
                    "JOIN orari ON sale.nomesala = orari.nomesala) WHERE filmassegnato = ?";
            connection = connect();
            PreparedStatement ps = connection.prepareStatement(query);
            // Inserimento stringa
            ps.setString(1, movieName);
            //Eseguo la query di vista e tengo il risultato in un oggetto ResultSet
            ResultSet rs = ps.executeQuery();

            return rs.getFloat("prezzo");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiusura Oggetti Connessione e Statement
            if (connection != null) connection.close();
        }
        return null;
    }


    /**
     * Metodo che prende in input il nome di un film e restituisce la prima sala libera
     * in cui viene proiettato
     * @param movieName
     * @return
     */
    public String getSalaFilm(String movieName) throws SQLException {
        Connection connection = null;
        try {
            String query = "SELECT * FROM ((films JOIN sale ON films.nomefilm = sale.filmassegnato)" +
                    "JOIN orari ON sale.nomesala = orari.nomesala) WHERE filmassegnato = ?";
            connection = connect();
            PreparedStatement ps = connection.prepareStatement(query);
            // Inserimento stringa
            ps.setString(1, movieName);
            //Eseguo la query di vista e tengo il risultato in un oggetto ResultSet
            ResultSet rs = ps.executeQuery();

            return rs.getString("nomesala");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiusura Oggetti Connessione e Statement
            if (connection != null) connection.close();
        }
        return null;
    }

    /**
     * Metodo che ritorna una lista di nomi di tutti i film registrati nel db
     * @return lista dei nomi dei film
     */
    public ArrayList<String> getListaNomiFilm() throws SQLException{
        Connection connection = null;
        try {
            String query = "SELECT * FROM ((sale LEFT OUTER JOIN films ON sale.filmassegnato = films.nomefilm))";
            connection = connect();
            PreparedStatement ps = connection.prepareStatement(query);

            //Eseguo la query di vista e tengo il risultato in un oggetto ResultSet
            ResultSet rs = ps.executeQuery();
            ArrayList<String> lista = new ArrayList<>();
            while(rs.next()) {
                lista.add(rs.getString("nomefilm"));
            }
            return lista;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiusura Oggetti Connessione e Statement
            if (connection != null) connection.close();
        }
        return null;
    }

    /**
     * Metodo che ritorna la lista dei biglietti ancora non usati di un utente
     * @param nameUser
     * @return
     */
    public ArrayList<Biglietto> getTickets(String nameUser) throws SQLException {
        Connection connection = null;
        String query = "SELECT * FROM biglietti WHERE name = ?";
        connection = connect();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,nameUser);

        ResultSet rs = ps.executeQuery();

        ArrayList<Biglietto> tickets = new ArrayList<Biglietto>();
        while(rs.next()) {
            String nomesala = rs.getString("nomesala");
            String nomefilm = rs.getString("nomefilm");
            String name = rs.getString("name");
            String data = rs.getString("data");
            String ora = rs.getString("ora");
            Boolean intero = rs.getBoolean("intero");
            Float prezzo = rs.getFloat("prezzo");
            if (LocalDate.now().isBefore(LocalDate.parse(data))) {
                tickets.add(new Biglietto(name,intero,nomesala,nomefilm,prezzo,
                        LocalDateTime.of(LocalDate.parse(data),LocalTime.parse(ora))));
            }
        }
        return tickets;
    }
}
