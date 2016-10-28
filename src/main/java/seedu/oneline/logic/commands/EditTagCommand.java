//@@author A0140156R

package seedu.oneline.logic.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor;
import seedu.oneline.model.tag.TagColorMap;
import seedu.oneline.model.tag.TagField;
import seedu.oneline.model.task.*;
import seedu.oneline.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task to the task book.
 */
public class EditTagCommand extends EditCommand {

    public final String name;
    public final Map<TagField, String> fields;
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = EditCommand.MESSAGE_USAGE;
    
    public static final String MESSAGE_SUCCESS = "Category updated: %1$s";
    public static final String MESSAGE_INVALID_TAG = "The tag %1$s is invalid";
    public static final String MESSAGE_DUPLICATE_TAG = "The tag %1$s already exists in the task book";

    
    public EditTagCommand(String name, Map<TagField, String> fields) throws IllegalValueException, IllegalCmdArgsException {
        if (!Tag.isValidTagName(name)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS + " : " + name);
        }
        if (fields.containsKey(TagField.NAME) && !Tag.isValidTagName(fields.get(TagField.NAME))) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS + " : " + fields.get(TagField.NAME));
        }
        if (fields.containsKey(TagField.COLOR) && !TagColor.isValidColor(fields.get(TagField.COLOR))) {
            throw new IllegalValueException(TagColor.MESSAGE_COLOR_CONSTRAINTS + " : " + fields.get(TagField.COLOR));
        }
        if (fields.size() == 0) {
            throw new IllegalCmdArgsException(EditCommand.MESSAGE_USAGE);
        }
        this.name = name;
        this.fields = fields;
    }
    
    public static EditTagCommand createFromArgs(String args) throws IllegalCmdArgsException, IllegalValueException {
        Entry<String, Map<TagField, String>> parsed = Parser.getTagAndTagFieldsFromArgs(args);
        return new EditTagCommand(parsed.getKey(), parsed.getValue());
    }

    @Override
    public CommandResult execute() {
        String name = this.name; // Mutability
        List<String> results = new ArrayList<String>();
        if (fields.containsKey(TagField.NAME)) {
            String newName = fields.get(TagField.NAME);
            Tag oldTag = null;
            Tag newTag = null;
            try {
                oldTag = Tag.getTag(name);
                newTag = Tag.getTag(newName);
            } catch (IllegalValueException e) {
            }
            if (model.getTaskBook().getTagList().contains(newTag)) {
                return new CommandResult(String.format(MESSAGE_DUPLICATE_TAG, newName));
            }
            List<ReadOnlyTask> taskList = new ArrayList<ReadOnlyTask>(model.getTaskBook().getTaskList());
            Map<TaskField, String> fields = new HashMap<TaskField, String>();
            fields.put(TaskField.TAG, newTag.getTagName());
            for (ReadOnlyTask t : taskList) {
                if (t.getTag().equals(oldTag)) {
                    try {
                        Task newTask = t.update(fields);
                        model.replaceTask(t, newTask);
                    } catch (TaskNotFoundException | IllegalValueException e) {
                        assert false : e.getMessage();
                    }
                }
            }
            TagColor color = model.getTagColor(oldTag);
            model.setTagColor(oldTag, TagColor.getDefault());
            model.setTagColor(newTag, color);
            name = newName;
            results.add("renamed to " + newTag.getTagName());
        }
        if (fields.containsKey(TagField.COLOR)) {
            try {
                TagColor color = new TagColor(fields.get(TagField.COLOR));
                model.setTagColor(Tag.getTag(name), color);
                results.add(String.format("color updated to " + color.toString()));
            } catch (Exception e) {
                assert false;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_SUCCESS, name));
        sb.append(" ");
        for (int i = 0; i < results.size(); i++) {
            sb.append(results.get(i));
            if (i < results.size() - 1) {
                sb.append(", ");
            }
        }
        return new CommandResult(sb.toString());
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

}