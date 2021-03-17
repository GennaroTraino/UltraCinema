package biglietteria;

/**
 * STRATEGY PATTERN: classe che implementa la strategia: pagamento con carta in contanti
 * COMMAND PATTER: concrete command pagamento
 */
public class Contanti implements Pagamento {

    private final String nome;

    public Contanti(String nome) {
        this.nome = nome;
    }

    @Override
    public void execute() {
        this.paga();
    }

    @Override
    public boolean undo() {
        return true;
    }

    @Override
    public void paga() {
    }
}
