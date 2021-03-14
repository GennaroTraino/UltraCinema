package biglietteria;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * Classe Sala: definisce le caratteristiche di una sala
 */

public class Sala {
    private String nomesala;
    private int nPosti;
    private Film film = null;
    ArrayList<LocalTime> orari = new ArrayList<LocalTime>();

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


    /**
     * Aggiunge un orario di proiezione alla Sala
     * @param orario
     * @throws OrarioSalaException lancia un eccezione se l'orario che si vuole aggiungere
     * è inferiore a tre ore dopo uno gia esistente
     */

    public void aggiungiOrario(LocalTime orario) throws OrarioSalaException{

        for (LocalTime x : orari) {
           if( x.getHour() > orario.minusHours(3).getHour() ) {
                throw new OrarioSalaException();
           }
        }
        orari.add(orario);
    }
}
