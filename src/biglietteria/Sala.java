package biglietteria;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Classe Sala: definisce le caratteristiche di una sala
 */

public class Sala {
    private String nomesala;
    private int nPosti;
    private Film film = null;
    ArrayList<LocalTime> orari = new ArrayList<>();

    //Costruttore con nPosti
    public Sala(String nomesala,int nPosti){
        this.nomesala = nomesala;
        this.nPosti = nPosti;
    }

    public Sala() {}


    /**
     * Setter e getter di Film e nPosti
     */
    public void setFilm(Film film) {
        this.film = film;
    }
    public Film getFilm() {
        return film;
    }
    public String getNomesala() { return nomesala; }
    public void setNomesala(String nomesala) { this.nomesala = nomesala; }
    public int getnPosti() {
        return nPosti;
    }
    public void setnPosti(int nPosti) {
        this.nPosti = nPosti;
    }
}
