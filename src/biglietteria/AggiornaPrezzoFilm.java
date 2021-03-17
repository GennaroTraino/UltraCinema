package biglietteria;

/**
 * COMMAND PATTERN: CONCRETE COMMAND
 * Classe che definisce l'operazione di aggiornamento prezzo
 * di un film, effettuata da un Admin
 */

public class AggiornaPrezzoFilm implements CommandAdmin {

    private final String nomeFilm;
    private final float prezzoAggiornato;


    public AggiornaPrezzoFilm(String nomeFilm, Float prezzoAggiornato) {
        this.nomeFilm = nomeFilm;
        this.prezzoAggiornato = prezzoAggiornato;
    }

    @Override
    public void execute() {
        OperationReceiver receiver = new OperationReceiver();
        receiver.aggiornaPrezzoFilm(nomeFilm,prezzoAggiornato);
    }
}
