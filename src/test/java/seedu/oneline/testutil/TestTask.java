package seedu.oneline.testutil;

import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName name;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskTime deadline;
    private TaskRecurrence recurrence;
    
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }
    
    public void setName(TaskName name) {
        this.name = name;
    }
    
    public void setStartTime(TaskTime startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(TaskTime endTime) {
        this.endTime = endTime;
    }
    
    public void setDeadline(TaskTime deadline) {
        this.deadline = deadline;
    }

    public void setRecurrence(TaskRecurrence recurrence) {
        this.recurrence = recurrence;
    }

    @Override
    public TaskName getName() {
        return name;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }

    @Override
    public TaskTime getEndTime() {
        return endTime;
    }

    @Override
    public TaskTime getDeadline() {
        return deadline;
    }

    @Override
    public TaskRecurrence getRecurrence() {
        return recurrence;
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().toString() + " ");
        sb.append(".a " + this.getStartTime().toString() + " ");
        sb.append(".b " + this.getEndTime().toString() + " ");
        sb.append(".c " + this.getDeadline().toString() + " ");
        sb.append(".d " + this.getRecurrence().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }
}