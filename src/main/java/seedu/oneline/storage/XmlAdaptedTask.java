package seedu.oneline.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String starttime;
    @XmlElement(required = true)
    private String endtime;
    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private boolean isCompleted; 
    @XmlElement(required = true)
    private String tag = "#";

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().serialize();
        starttime = source.getStartTime().serialize();
        endtime = source.getEndTime().serialize();
        deadline = source.getDeadline().serialize();
        isCompleted = source.isCompleted();
        tag = source.getTag().serialize();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final TaskName name = TaskName.deserialize(this.name);
        final TaskTime startTime = TaskTime.deserialize(this.starttime);
        final TaskTime endTime = TaskTime.deserialize(this.endtime);
        final TaskTime deadline = TaskTime.deserialize(this.deadline);
        final Tag tag = this.tag.equals("#") ? Tag.getDefault() : Tag.getTag(this.tag);
        return  new Task(name, startTime, endTime, deadline, tag, isCompleted);
    }
}
