package seedu.oneline.logic.commands;

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
    
    private static final TaskBook[] books;
    
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
        books = new TaskBook[] {
                book0, book1, book2, book3
        };
    }
    
    public final int index;

    public LoadCommand(int index) throws IllegalCmdArgsException {
        if (index < 0 || index >= books.length) {
            throw new IllegalCmdArgsException("Index out of bounds");
        }
        this.index = index;
    }

    public static LoadCommand createFromArgs(String args) throws IllegalCmdArgsException {
        int index = Integer.parseInt(args.trim());
        return new LoadCommand(index);
    }
    
    @Override
    public CommandResult execute() {
        model.resetData(books[index]);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
