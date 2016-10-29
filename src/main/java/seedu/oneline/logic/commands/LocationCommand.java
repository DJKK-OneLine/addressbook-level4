//@@author A0121657H
package seedu.oneline.logic.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import seedu.oneline.commons.core.Config;
import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.events.storage.StorageLocationChangedEvent;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.storage.Storage;

public class LocationCommand extends Command {

    public static final String COMMAND_WORD = "loc";

    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Sets the folder to be used for storage\n" 
            + "Parameters: FOLDERPATH\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Bob/Desktop/";

    public static final String MESSAGE_SET_STORAGE_SUCCESS = "Storage location succesfully set to %1$s\\taskbook.xml";
    public static final String MESSAGE_LOCATION = "Storage location is currently at ";
    public static final String MESSAGE_SET_STORAGE_FAILURE_PATH_INVALID = "Cannot set storage location to \"%1$s\", path is invalid!";
    public static final String MESSAGE_SET_STORAGE_FAILURE_NOT_DIRECTORY = "Cannot set storage location to \"%1$s\", this is not a directory!";
    public static final String MESSAGE_SET_STORAGE_FAILURE_CANNOT_READ = "Cannot set storage location to \"%1$s\", cannot read from here!"; 
    public static final String MESSAGE_SET_STORAGE_FAILURE_CANNOT_WRITE = "Cannot set storage location to \"%1$s\", cannot write to here!"; 

    String storageLocation;
    protected Storage storage;

    public LocationCommand(String storageLocation) {
        this.storageLocation = storageLocation.trim();
    }

    public static LocationCommand createFromArgs(String args) {
        return new LocationCommand(args);
    }

    @Override
    public CommandResult execute() {
        if (storageLocation == null || storageLocation.isEmpty()) {
            String currentPath = model.getTaskBookFilePath();
            return new CommandResult(MESSAGE_LOCATION + currentPath + ".");            
        }

        Optional<Path> path = getValidPath(storageLocation);
        if (!path.isPresent()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(MESSAGE_SET_STORAGE_FAILURE_PATH_INVALID, storageLocation));
        } else {
            Path actualPath = path.get();
            if (!isDirectory(actualPath)) {
                return new CommandResult(String.format(MESSAGE_SET_STORAGE_FAILURE_NOT_DIRECTORY, actualPath.toAbsolutePath()));
            } else if (!isReadable(actualPath)) {
                return new CommandResult(String.format(MESSAGE_SET_STORAGE_FAILURE_CANNOT_READ, actualPath.toAbsolutePath()));
            } else if (!isWritable(actualPath)) {
                return new CommandResult(String.format(MESSAGE_SET_STORAGE_FAILURE_CANNOT_WRITE, actualPath.toAbsolutePath()));
            }
        }
        Path actualPath = path.get();
        EventsCenter.getInstance().post(new StorageLocationChangedEvent(storageLocation));
        return new CommandResult(String.format(MESSAGE_SET_STORAGE_SUCCESS, actualPath.toAbsolutePath()));    
    }

    private Optional<Path> getValidPath(String folderpath) {
        try {
            Path path = Paths.get(folderpath);
            return Optional.of(path);
        } catch (InvalidPathException ipe) {
            return Optional.empty();
        } catch (SecurityException sece) {
            return Optional.empty();
        }

    }

    private boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }

    private boolean isWritable(Path path) {
        return Files.isWritable(path);
    }

    private boolean isReadable(Path path) {
        return Files.isReadable(path);
    }

    @Override
    public boolean canUndo() {
        return true;
    }
}
