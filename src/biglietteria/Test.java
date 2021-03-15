package biglietteria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Test {
    public static void main(String[] args) {

        //operazioni
        CommandManager manager = CommandManager.getInstance();
        //manager.execute(new AggiungiFilm(new Film("harry","bho","fantasy",30.0f)));
        //manager.execute(new AggiornaPrezzoFilm("pio", 25.0f));

        // Non posso fare qui il report sala mi serve un event Action
        //manager.execute(new ReportSala("A1","giornaliero"));

        //biglietti
        String date = "2021-03-20";
        String time = "18:00";
        manager.execute(new AcquistaBiglietto("A1","pio","User03",
                LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time)),true,30.0f));
        //manager.undo(); //do not work

    }
}
