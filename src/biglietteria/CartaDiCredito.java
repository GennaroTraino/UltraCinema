package biglietteria;

public class CartaDiCredito implements Pagamento {

    String numero, nome, scadenza, cvv;

    public CartaDiCredito(String numero, String nome, String scadenza, String cvv) {
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
    public void undo() {
        System.out.println("RIMBORSO SU CARTA EFFETTUATO");
    }

    @Override
    public void paga() {
        System.out.println("PAGAMENTO EFFETTUATO, BUONA VISIONE");
    }
}
