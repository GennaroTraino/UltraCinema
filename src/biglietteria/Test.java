package biglietteria;

import java.sql.Connection;
import java.sql.SQLException;
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
        String date = "2021-05-22";
        String time = "15:00";
        manager.execute(new AcquistaBiglietto("A1","pio","User03",
                LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time)),LocalDateTime.now(),true,30.0f));
        //manager.execute(new CartaDiCredito("2197201720170","Gennaro","23/32","345"));
        //manager.undo();
/**        DBConnection db = null;
        try {
            db = DBConnection.getInstance();
            System.out.println(db.getFilmPrice("pio"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
 */
    }
}
