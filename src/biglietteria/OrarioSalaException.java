package biglietteria;

/**
 * Classe eccezione, si presenta nel caso un amministratore voglia aggiungere un orario
 * di proiezione di una sala troppo vicino all'orario di proiezione precedente!
 */

public class OrarioSalaException extends Exception {

    public OrarioSalaException(){
        super("La proiezione che vuoi programmare è troppo vicina alla proiezione precedente");
    }

    @Override
    public String toString() {
        return getMessage() + ": devono passare almeno tre ore!";
    }
}
