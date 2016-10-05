package seedu.address.model;

import javafx.collections.ObservableList;
<<<<<<< HEAD:src/main/java/seedu/address/model/AddressBook.java
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
=======
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
>>>>>>> ec9cac7... attempt 1 rename to tasks:src/main/java/seedu/address/model/TaskBook.java
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

<<<<<<< HEAD:src/main/java/seedu/address/model/AddressBook.java
    private final UniquePersonList persons;
    private final UniqueTagList tags;

    {
        persons = new UniquePersonList();
=======
	private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
    	tasks = new UniqueTaskList();
>>>>>>> ec9cac7... attempt 1 rename to tasks:src/main/java/seedu/address/model/TaskBook.java
        tags = new UniqueTagList();
    }

    public TaskBook() {}

    /**
     * Tasks and Tags are copied into this taskbook
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this taskbook
     */
    public TaskBook(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyTaskBook() {
        return new TaskBook();
    }

//// list overwrite operations
<<<<<<< HEAD:src/main/java/seedu/address/model/AddressBook.java

    public ObservableList<Person> getPersons() {
        return persons.getInternalList();
=======
//    public ObservableList<Task> getTasks() {
//        return tasks.getInternalList();
//    }
//    
    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
>>>>>>> ec9cac7... attempt 1 rename to tasks:src/main/java/seedu/address/model/TaskBook.java
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

<<<<<<< HEAD:src/main/java/seedu/address/model/AddressBook.java
//// person-level operations
=======
//// task and task-level operations
>>>>>>> ec9cac7... attempt 1 rename to tasks:src/main/java/seedu/address/model/TaskBook.java

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniquePersonList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws UniquePersonList.DuplicatePersonException {
        syncTagsWithMasterList(p);
        persons.add(p);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
<<<<<<< HEAD:src/main/java/seedu/address/model/AddressBook.java
    private void syncTagsWithMasterList(Person person) {
        final UniqueTagList personTags = person.getTags();
        tags.mergeFrom(personTags);
=======
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);
>>>>>>> ec9cac7... attempt 1 rename to tasks:src/main/java/seedu/address/model/TaskBook.java

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        person.setTags(new UniqueTagList(commonTagReferences));
    }

//    public boolean removePerson(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
//        if (tasks.remove(key)) {
//            return true;
//        } else {
//            throw new UniqueTaskList.TaskNotFoundException();
//        }
//    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.tasks.equals(((TaskBook) other).tasks)
                && this.tags.equals(((TaskBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
