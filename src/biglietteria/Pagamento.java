package biglietteria;

/**
 * Interfaccia per pagamenti: PATTERN STRATEGY
 */
public interface Pagamento extends CommandUser {
    void paga();
}
