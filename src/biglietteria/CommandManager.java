package biglietteria;

import java.util.Stack;

/**
 * PATTERN COMMAND: Classe Invoker del pattern.
 * Mantiente una coda di operazioni (definite in Command)
 */
public class CommandManager {

    //
    private Stack<CommandAdmin> stackAdmin = null;
    private Stack<CommandUser> stackUser = null;
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
    void execute(CommandUser command){
        if(stackUser.empty()){
            stackUser.push(command);
        }
        stackUser.peek().execute();
        stackUser.push(command);
    }

    /**
     * PATTERN COMMAND: Metodo che permette l'undo delle operazioni
     * dell'utente sull'acquisto dei biglietti -
     * Vale esclusivamente per l'user
     */
    void undo() {
        stackUser.peek().undo();
        stackUser.pop();
    }

    /**
     * Consente di pulire lo stack User
     */
    void clear() {
        stackUser.clear();
    }
}
