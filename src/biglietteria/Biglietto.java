package biglietteria;

import java.time.LocalDateTime;


/**
 * Classe Biglietto: definisce metodi e attributi del biglietto
 */

public class Biglietto {
    private User utente;
    private boolean intero;
    private Sala sala;
    private Film film;
    private float prezzo;
    private LocalDateTime dataeora;

    /**
     * Costruttore completo Biglietto
     * @param utente
     * @param intero
     * @param sala
     * @param film
     * @param dataeora
     */

    public Biglietto(User utente,boolean intero,Sala sala, Film film, LocalDateTime dataeora){
        this.film = film;
        this.sala = sala;
        this.utente = utente;
        this.dataeora = dataeora;

        //Ottieni prezzo dal film (se sono nel fine settimana)
        this.prezzo = film.getPrezzo();

        // if sotto per biglietto intero?? if(utente.eta)
        this.intero = intero;
    }
}
