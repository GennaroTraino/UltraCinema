package biglietteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * COMMAND PATTERN: CONCRETE COMMAND
 * Classe che definisce l'operazione di inserimento film nel db
 * effettuata da un Admin
 */

public class AggiungiFilm implements CommandAdmin {

    private Film film;
    private OperationReceiver receiver = null;

    public AggiungiFilm(Film film) {
        this.film = film;
     }

    @Override
    public void execute() {
        receiver = new OperationReceiver();
        receiver.aggiungiFilm(film);
    }
}
