package seedu.oneline.logic.commands;

import java.util.HashMap;
import java.util.Map;

import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.logic.Logic;
import seedu.oneline.logic.LogicManager;
import seedu.oneline.model.Model;
import seedu.oneline.model.ModelManager;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.UserPrefs;
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
        books = new HashMap<String, TaskBook>();
        
        books.put("0", 
                getTaskBook(new String[] {
                        "add meeting with boss .from wed 2pm .to wed 3pm", 
                        "add dinner with Becky .from mon 6pm .to mon 9pm", 
                        "add book flight to Japan .due tmr", 
                        "add send presentation slides to Ivan .due tmr", 
                        "add get client reports from Justin",  
                        "add pick up suit from dry cleaners .due today",
                        "add call mum", 
                        "add confirm final website design .due today", 
                        "add brunch with sis .from sat 10am .to sat 1pm", 
                        "add watch The Accountant .from 5pm .to 8pm", 
                        "add get groceries .due wed", 
                        "add buy roses for Becky",  
                        "add check out new macbook pro"    
                })
            );
        
        books.put("1", 
                    getTaskBook(new String[] {
                            "add meeting with boss .from wed 2pm .to wed 3pm #work", 
                            "add dinner with Becky .from mon 6pm .to mon 9pm #decky", 
                            "add book flight to Japan .due tmr #decky", 
                            "add send presentation slides to Ivan .due tmr #work", 
                            "add get client reports from Justin #work",  
                            "add pick up suit from dry cleaners .due today #chore",
                            "add call mum #fam", 
                            "add confirm final website design .due today #work", 
                            "add brunch with sis .from sat 10am .to sat 1pm #fam", 
                            "add watch The Accountant .from 5pm .to 8pm", 
                            "add get groceries .due wed #chore", 
                            "add buy roses for Becky #decky",  
                            "add check out new macbook pro"    
                    })
                );
       
        books.put("2", 
                getTaskBook(new String[] {
                        "add meeting with boss .from wed 2pm .to wed 3pm #work", 
                        "add dinner with Becky .from mon 6pm .to mon 9pm #decky", 
                        "add book flight to Japan .due tmr #decky", 
                        "add send presentation slides to Ivan .due tmr #work", 
                        "add get client reports from Justin #work",  
                        "add pick up suit from dry cleaners .due today #chore",
                        "add call mum #fam", 
                        "add confirm final website design .due today #work", 
                        "add brunch with sis .from sat 10am .to sat 1pm #fam", 
                        "add watch The Accountant .from 5pm .to 8pm", 
                        "add get groceries .due wed #chore", 
                        "add buy roses for Becky #decky",  
                        "add check out new macbook pro",
                        "edit #work blue",
                        "edit #decky red",
                        "edit #chore green",
                        "edit #fam yellow"
                })
            );
       
        books.put("k1", 
                getTaskBook(new String[] {
                        "add Meeting with client .from 5 Nov 2pm .to 5 Nov 5pm"
                })
            );
       
        books.put("k2", 
                getTaskBook(new String[] {
                        "add Buy milk #Shop",
                        "add Dinner with family .from 7pm .to 9pm #Family",
                        "add Financial report .due tmr 12pm #Work",
                        "edit #Work green",
                        "edit #Family blue",
                        "edit #Shop purple"
                })
            );
        
        books.put("k", 
                getTaskBook(new String[] {
                        "add meeting with boss .from wed 2pm .to wed 3pm #work", 
                        "add dinner with Becky .from mon 6pm .to mon 9pm #decky", 
                        "add book flight to Japan .due tmr #decky", 
                        "add send presentation slides to Ivan .due tmr #work", 
                        "add get client reports from Justin #work",  
                        "add pick up suit from dry cleaners .due today #chore",
                        "add call mum #fam",    
                        "add confirm final website design .due today #work", 
                        "add brunch with sis .from sat 10am .to sat 1pm #fam", 
                        "add watch The Accountant .from 5pm .to 8pm", 
                        "add get groceries .due wed #chore", 
                        "add buy roses for Becky #decky",  
                        "add check out new macbook pro",
                        "add repair storage room light bulb #repair",
                        "add repair relationship with son #fam",
                        "edit #work green",
                        "edit #decky red",
                        "edit #chore purple",
                        "edit #fam blue",
                        "edit #repair yellow"
                })
            );
        
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
        model.updateFilteredListToShowAllNotDone();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    public static TaskBook getTaskBook(String[] cmds) {
        Model m = new ModelManager(new TaskBook(), new UserPrefs());
        Logic l = new LogicManager(m, null);
        for (int i = 0; i < cmds.length; i++) {
            System.out.println("Executing: " + cmds[i]);
            CommandResult r = l.execute(cmds[i]);
            System.out.println(r.feedbackToUser);
        }
        return new TaskBook(m.getTaskBook());
    }
    
    public static void init() {}
}
