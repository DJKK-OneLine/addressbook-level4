package seedu.oneline.commons.events.ui;

import java.util.Map.Entry;

import seedu.oneline.commons.events.BaseEvent;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor;

//@@author A0142605N 
/**
 * Represents a selection change in the Tag List Panel
 */
public class TagPanelSelectionChangedEvent extends BaseEvent {


    private final Tag newSelection;

    public TagPanelSelectionChangedEvent(Tag newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Tag getNewSelection() {
        return newSelection;
    }
}
