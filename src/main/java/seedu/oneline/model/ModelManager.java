package seedu.oneline.model;

import javafx.collections.transformation.FilteredList;
import seedu.oneline.commons.core.ComponentManager;
import seedu.oneline.commons.core.LogsCenter;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.events.model.TaskBookChangedEvent;
import seedu.oneline.commons.util.StringUtil;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.UniqueTaskList;
import seedu.oneline.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.oneline.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given Task book
     * Task book and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task book: " + src + " and user prefs " + userPrefs);

        taskBook = new TaskBook(src);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void replaceTask(ReadOnlyTask oldTask, Task newTask) throws TaskNotFoundException, DuplicateTaskException {
//        assert taskBook.getUniqueTaskList().contains(newTask);
//        assert !taskBook.getUniqueTaskList().contains(newTask);
        taskBook.getUniqueTaskList().replaceTask(oldTask, newTask);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().toString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
