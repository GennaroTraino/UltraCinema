package biglietteria;

import java.sql.SQLException;

/**
 * COMMAND PATTERN: COMMAND
 * interfaccia che definisce i command di un user
 */
public interface CommandUser {
        public void execute() throws PostiException, SQLException;
        public boolean undo();
}
