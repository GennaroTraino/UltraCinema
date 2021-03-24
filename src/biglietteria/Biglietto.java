package biglietteria;

import java.time.LocalDateTime;


/**
 * Classe Biglietto: definisce metodi e attributi del biglietto
 */

public class Biglietto {
    private final String utente;
    private final boolean intero;
    private final String sala;
    private final String film;
    private final float prezzo;
    private final LocalDateTime dataeora;

    /**
     * Costruttore completo Biglietto
     * @param utente
     * @param intero
     * @param sala
     * @param film
     * @param dataeora
     */

    public Biglietto(String utente,boolean intero,String sala, String film, float prezzo, LocalDateTime dataeora){
        this.film = film;
        this.sala = sala;
        this.prezzo = prezzo;
        this.utente = utente;
        this.dataeora = dataeora;
        this.intero = intero;
    }

    public String getUtente() {
        return utente;
    }

    public boolean isIntero() {
        return intero;
    }

    public String getSala() {
        return sala;
    }

    public String getFilm() {
        return film;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public LocalDateTime getDataeora() {
        return dataeora;
    }


}
