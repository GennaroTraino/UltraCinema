package biglietteria;

/**
 * PATTERN STRATEGY: operazione pagamento Bancomat
 * PATTERN COMMAND: concrete command
 */
public class Bancomat implements Pagamento {
    String numero, nome, scadenza, cvv;

    public Bancomat(String numero, String nome, String scadenza, String cvv) {
        this.numero = numero;
        this.nome = nome;
        this.scadenza = scadenza;
        this.cvv = cvv;
    }

    @Override
    public void execute() {
        System.out.println("PAGAMENTO IN CORSO...");
        this.paga();
    }

    @Override
    public boolean undo() {
        System.out.println("RIMBORSO SU BANCOMAT EFFETTUATO");
        return true;
    }

    @Override
    public void paga() {
        System.out.println("PAGAMENTO EFFETTUATO, BUONA VISIONE");
    }
}
