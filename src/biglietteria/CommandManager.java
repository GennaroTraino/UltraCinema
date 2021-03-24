package biglietteria;

import java.sql.SQLException;
import java.util.Stack;

/**
 * PATTERN COMMAND: Classe Invoker del pattern.
 * Mantiene una coda di operazioni (definite in Command) per utente, admin e pagamenti.
 */
public class CommandManager {

    //Instance e stack vari
    private Stack<CommandAdmin> stackAdmin;
    private Stack<CommandUser> stackUser;
    private Stack<Pagamento> stackPagamento;
    private static CommandManager instance = null;

    //Singleton
    static CommandManager getInstance(){
        if(instance != null)
            return instance;
        return new CommandManager();
    }

    //Costruttore
    private CommandManager() {
        stackUser = new Stack<>();
        stackAdmin = new Stack<>();
        stackPagamento = new Stack<>();
    }

    /**
     * PATTERN COMMAND: metodo execute sui CommandAdmin
     * @param command
     */
    void execute(CommandAdmin command){
        stackAdmin.push(command);
        stackAdmin.peek().execute();
    }

    /**
     * PATTERN COMMAND: metodo execute sui CommandUser
     * @param command
     */
    void execute(CommandUser command) throws PostiException, SQLException {
        if(stackUser.empty()){
            stackUser.push(command);
        }
        stackUser.peek().execute();
        stackUser.push(command);
    }

    /**
     * PATTERN COMMAND: Metodo che permette undo delle operazioni
     * utente su acquisto di biglietti -
     * Vale esclusivamente per un user
     * @return true o false se riuscita o meno
     */
    public boolean undo() {
        if (stackUser.isEmpty()) {
            return false;
        }
        stackUser.peek().undo();
        stackUser.pop();

        stackPagamento.peek().undo();
        stackPagamento.pop();
        return true;
    }

    void execute(Pagamento command) throws PostiException, SQLException {
        if (stackPagamento.empty()) {
            stackPagamento.push(command);
        }
        stackPagamento.peek().execute();
        stackPagamento.push(command);
    }

    /**
     * Consente di pulire lo stack User
     */
    void clear() {
        stackUser.clear();
    }

    /**
     * Metodo per controllare se lo stack dell' user è vuoto
     * @return true se vuoto, fals altrimenti
     */
    public boolean isStackUserEmpty() {
        if (stackUser.isEmpty()) {
            return true;
        }
        return false;
    }
}
