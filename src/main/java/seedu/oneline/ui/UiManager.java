package seedu.oneline.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.oneline.MainApp;
import seedu.oneline.commons.core.ComponentManager;
import seedu.oneline.commons.core.Config;
import seedu.oneline.commons.core.LogsCenter;
import seedu.oneline.commons.events.storage.DataSavingExceptionEvent;
import seedu.oneline.commons.events.ui.JumpToListRequestEvent;
import seedu.oneline.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.oneline.commons.events.ui.ShowHelpRequestEvent;
import seedu.oneline.commons.events.ui.ChangeViewEvent; 
import seedu.oneline.commons.events.ui.TagPanelSelectionChangedEvent;
import seedu.oneline.commons.util.StringUtil;
import seedu.oneline.logic.Logic;
import seedu.oneline.logic.commands.SelectCommand;
import seedu.oneline.model.UserPrefs;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/oneline_32.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code =================================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskPane().scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String message = String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS,
                event.getNewSelection().toString());
        mainWindow.postResult(message);
    }
    
    //@@author A0142605N
    @Subscribe 
    private void handleTagPanelSelectionChangedEvent(TagPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        logic.execute("list " + event.getNewSelection().toString());
    }
    
    @Subscribe
    private void handleChangeViewEvent(ChangeViewEvent event) {
        mainWindow.resetAllButtons();
        String view = event.getNewView(); 
        switch(view) {
            case " ": case "done": case "undone": 
                mainWindow.getAllButton().setSelected(true); 
                break;
            case "today": 
                mainWindow.getDayButton().setSelected(true); 
                break;
            case "week": 
                mainWindow.getWeekButton().setSelected(true); 
                break;
            case "float": 
                mainWindow.getFloatButton().setSelected(true); 
                break;
            default: 
                assert false; 
        }
    }

}
