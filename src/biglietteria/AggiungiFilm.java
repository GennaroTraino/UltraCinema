package biglietteria;

/**
 * COMMAND PATTERN: CONCRETE COMMAND
 * Classe che definisce l'operazione di inserimento film nel db
 * effettuata da un Admin
 */

public class AggiungiFilm implements CommandAdmin {

    private final Film film;

    public AggiungiFilm(Film film) {
        this.film = film;
     }

    @Override
    public void execute() {
        OperationReceiver receiver = new OperationReceiver();
        receiver.aggiungiFilm(film);
    }
}
