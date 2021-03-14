package biglietteria;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Classe che definisce la struttura e gli attributi degli utenti
 */

public class User {
    private String nome;
    private String email;
    private LocalDate dataDiNascita;
    private String password;

    /**
     * Costruttore completo per User
     * @param nome
     * @param email
     * @param dataDiNascita
     * @param password
     */

    public User(String nome,String email,LocalDate dataDiNascita,String password){
        this.email = email;
        this.dataDiNascita = dataDiNascita;
        this.nome = nome;
        this.password = password;
    }

    /**
     * Costruttore vuoto
     */

    public User() {}

    /**
     * Metodi Setter di User
     */

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDataDiNascita(LocalDate data) {
        this.dataDiNascita = data;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Metodi Getter di User
     */

    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", password='" + password + '\'' +
                '}';
    }
}
