package seedu.oneline.logic.commands;


/**
 * Lists all tasks in the task book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_INVALID = "Argument given is invalid";

    public String listBy;

    public ListCommand() {
        this.listBy = "";
    }

    public ListCommand(String args) {
        this.listBy = args;
    }

    @Override
    public CommandResult execute() {
        switch (listBy) {
        case "":
            model.updateFilteredListToShowAllNotDone();
            break;
        case "done":
            model.updateFilteredListToShowAllDone();
            break;
        default:
            return new CommandResult(MESSAGE_INVALID);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean canUndo() {
        return true;
    }
}
