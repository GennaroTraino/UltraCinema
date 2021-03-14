package biglietteria;
/**
 * COMMAND PATTERN: COMMAND
 * interfaccia che definisce i command di un user
 */
public interface CommandUser {
        public void execute();
        public void undo();
}
