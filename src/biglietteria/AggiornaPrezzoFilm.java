package biglietteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * COMMAND PATTERN: CONCRETE COMMAND
 * Classe che definisce l'operazione di aggiornamento prezzo
 * di un film, effettuata da un Admin
 */

public class AggiornaPrezzoFilm implements CommandAdmin {

    private String nomeFilm;
    private float prezzoAggiornato;
    private OperationReceiver receiver = null;


    public AggiornaPrezzoFilm(String nomeFilm, Float prezzoAggiornato) {
        this.nomeFilm = nomeFilm;
        this.prezzoAggiornato = prezzoAggiornato;
    }

    @Override
    public void execute() {
        receiver = new OperationReceiver();
        receiver.aggiornaPrezzoFilm(nomeFilm,prezzoAggiornato);
    }
}
