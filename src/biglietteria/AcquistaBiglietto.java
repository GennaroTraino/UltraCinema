package biglietteria;

import java.time.LocalDateTime;

public class AcquistaBiglietto implements CommandUser{

    private String nomeSala;
    private String nomeFilm;
    private String name;
    private LocalDateTime dataOra;
    private LocalDateTime dataOraAcquisto;
    private boolean intero;
    private Float prezzo;
    private OperationReceiver operationReceiver = null;


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
    public void execute() {
        operationReceiver = new OperationReceiver();
        operationReceiver.acquistaBiglietto(nomeSala,nomeFilm,name,dataOra,dataOraAcquisto,intero,prezzo);
    }

    @Override
    public void undo() {
        operationReceiver = new OperationReceiver();
        operationReceiver.undoBiglietto(nomeSala,name,dataOra,dataOraAcquisto);
    }
}
