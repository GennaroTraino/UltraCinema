package biglietteria;

/**
 * Classe eccezione, si presenta nel caso un amministratore voglia aggiungere un orario
 * di proiezione di una sala troppo vicino all'orario di proiezione precedente!
 */

public class PostiException extends Exception {

    public PostiException(){
        super("Posti Esauriti in tutte le sale");
    }

    @Override
    public String toString() {
        return getMessage() + "Scegli un altro giorno";
    }
}
