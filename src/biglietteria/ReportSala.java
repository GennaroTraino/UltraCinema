package biglietteria;

/**
 * COMMAND PATTERN: CONCRETE COMMAND
 * Classe che richiama l'operazione dell'Admin di richiesta report affluenza e
 * ricavi di una Sala in uno specifico periodo
 */

public class ReportSala implements CommandAdmin {

    private OperationReceiver receiver = null;
    String nomeSala;
    String reportPeriodo = "";

    public ReportSala(String nomeSala, String reportPeriodo) {
        this.nomeSala = nomeSala;
        this.reportPeriodo = reportPeriodo;
    }


    @Override
    public void execute() {
        receiver = new OperationReceiver();
        receiver.reportSala(nomeSala,reportPeriodo);
    }
}
