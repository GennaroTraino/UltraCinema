package biglietteria;

public class Contanti implements Pagamento {

    private final String nome;

    public Contanti(String nome) {
        this.nome = nome;
    }

    @Override
    public void execute() {
        System.out.println("INSERIRE L'IMPORTO ...");
        this.paga();
    }

    @Override
    public void undo() {
        System.out.println("RIMBORSO: CONTANTI EROGATI ");
    }

    @Override
    public void paga() {
        System.out.println("PAGAMENTO EFFETTUATO, BUONA VISIONE");
    }
}
