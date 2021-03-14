package biglietteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * COMMAND PATTERN: CONCRETE COMMAND
 * Classe che definisce l'operazione di aggiornamento film in una sala
 * effettuata da un Admin
 */

public class AggiornaFilmSala implements CommandAdmin {

    private String nomeFilm;
    private String nomeSala;
    private OperationReceiver receiver = null;

    public AggiornaFilmSala(String nomeFilm,String nomeSala) {
        this.nomeFilm = nomeFilm;
        this.nomeSala = nomeSala;
    }


    @Override
    public void execute() {
        receiver = new OperationReceiver();
        receiver.aggiornaFilmSala(nomeFilm,nomeSala);
    }
}
