package seedu.oneline.logic.commands;

import java.util.HashMap;
import java.util.Map;

import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Lists all tasks in the task book to the user.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "l";

    public static final String MESSAGE_SUCCESS = "File loaded.";

    public static final String MESSAGE_INVALID = "Argument given is invalid. \n" +
                                                "Supported formats: l [1/2/...]";
    
    private static final Map<String, TaskBook> books;
    
    static {
        TaskBook book0 = new TaskBook();
        try {
            for (int i = 0; i < 10; i++) {
                book0.addTask(GenerateCommand.generateTask());
            }
        } catch (DuplicateTaskException e) {
        }
        TaskBook book1 = new TaskBook();
        try {
            for (int i = 0; i < 10; i++) {
                book1.addTask(GenerateCommand.generateTask());
            }
        } catch (DuplicateTaskException e) {
        }
        TaskBook book2 = new TaskBook();
        try {
            for (int i = 0; i < 10; i++) {
                book2.addTask(GenerateCommand.generateTask());
            }
        } catch (DuplicateTaskException e) {
        }
        TaskBook book3 = new TaskBook();
        try {
            for (int i = 0; i < 10; i++) {
                book3.addTask(GenerateCommand.generateTask());
            }
        } catch (DuplicateTaskException e) {
        }
        books = new HashMap<String, TaskBook>();
        books.put("0", book0);
        books.put("1", book1);
        books.put("2", book2);
        books.put("3", book3);
    }
    
    public final String key;

    public LoadCommand(String key) throws IllegalCmdArgsException {
        if (!books.containsKey(key)) {
            throw new IllegalCmdArgsException("Key " + key + " not found");
        }
        this.key = key;
    }

    public static LoadCommand createFromArgs(String args) throws IllegalCmdArgsException {
        return new LoadCommand(args.trim());
    }
    
    @Override
    public CommandResult execute() {
        model.resetData(books.get(key));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
