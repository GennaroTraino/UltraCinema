package biglietteria;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * COMMAND PATTERN: Concrete command operazione utente di acquisto biglietto
 * si rivolge al receiver (Operation Receiver)
 */
public class AcquistaBiglietto implements CommandUser{

    private final String nomeSala;
    private final String nomeFilm;
    private final String name;
    private final LocalDateTime dataOra;
    private final LocalDateTime dataOraAcquisto;
    private final boolean intero;
    private final Float prezzo;
    private OperationReceiver operationReceiver = null;

    //Costruttore COMPLETO
    public AcquistaBiglietto(String nomeSala, String nomeFilm, String name,
                             LocalDateTime dataOra, LocalDateTime dataOraAcquisto, boolean intero, Float prezzo) {
        this.nomeSala = nomeSala;
        this.nomeFilm = nomeFilm;
        this.name = name;
        this.dataOra = dataOra;
        this.intero = intero;
        this.prezzo = prezzo;
        this.dataOraAcquisto = dataOraAcquisto;
    }

    @Override
    public void execute() throws PostiException, SQLException {
        operationReceiver = new OperationReceiver();
        operationReceiver.acquistaBiglietto(nomeSala,nomeFilm,name,dataOra,dataOraAcquisto,intero,prezzo);
    }

    @Override
    public boolean undo() {
        operationReceiver = new OperationReceiver();
        operationReceiver.undoBiglietto(nomeSala,name,dataOra,dataOraAcquisto);
        return true;
    }
}
