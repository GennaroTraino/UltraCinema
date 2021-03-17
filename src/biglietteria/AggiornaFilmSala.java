package biglietteria;


/**
 * COMMAND PATTERN: CONCRETE COMMAND
 * Classe che definisce l'operazione di aggiornamento film in una sala
 * effettuata da un Admin
 */

public class AggiornaFilmSala implements CommandAdmin {

    private final String nomeFilm;
    private final String nomeSala;

    public AggiornaFilmSala(String nomeFilm,String nomeSala) {
        this.nomeFilm = nomeFilm;
        this.nomeSala = nomeSala;
    }


    @Override
    public void execute() {
        OperationReceiver receiver = new OperationReceiver();
        receiver.aggiornaFilmSala(nomeFilm,nomeSala);
    }
}
