package biglietteria;

/**
 * Classe Film: Definisce il film
 */

public class Film {
    private String nome;
    private String regista;
    private String genere;
    private float prezzo;

    /**
     * Costruttore con tutti i parametri
     * @param nome
     * @param regista
     * @param genere
     * @param prezzo
     */

    public Film(String nome, String regista, String genere, float prezzo){
        this.nome = nome;
        this.genere = genere;
        this.regista = regista;
        this.prezzo = prezzo;
    }

    public Film() {}

    /**
     * Setter e getter per FILM
     */

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getRegista() { return regista; }
    public void setRegista(String regista) { this.regista = regista; }

    public String getGenere() { return genere; }
    public void setGenere(String genere) { this.genere = genere; }
    public float getPrezzo() { return prezzo; }
    public void setPrezzo(float prezzo) { this.prezzo = prezzo; }
}
