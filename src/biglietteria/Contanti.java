package biglietteria;

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
    public void undo() {
    }

    @Override
    public void paga() {
    }
}