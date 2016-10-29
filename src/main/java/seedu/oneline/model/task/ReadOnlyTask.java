package seedu.oneline.model.task;

import java.util.Map;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    public TaskName getName();
    public TaskTime getStartTime();
    public TaskTime getEndTime();
    public TaskTime getDeadline();
    public TaskRecurrence getRecurrence();
    public boolean isCompleted();
    public boolean isFloating();
    public boolean isEvent();
    public boolean hasDeadline();

    /**
     * Returns the tag of the current task
     */
    public Tag getTag();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getDeadline().equals(this.getDeadline())
                && other.getRecurrence().equals(this.getRecurrence()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getStartTime())
                .append(" to ")
                .append(getEndTime())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Recurrence: ")
                .append(getRecurrence())
                .append(" Tag: ")
                .append(getTag());
        return builder.toString();
    }

    Task update(Map<TaskField, String> fields) throws IllegalValueException;
    
    //@@author A0121657H
    /**
     * Copies data over to new Task and marks it as done
     * @param taskToDone
     * @return
     */
    public Task markDone(ReadOnlyTask taskToDone);
    
    /**
     * Copies data over to new Task and marks it as not done
     * @param taskToDone
     * @return
     */
    public Task markUndone(ReadOnlyTask taskToDone);
    //@@author
}
