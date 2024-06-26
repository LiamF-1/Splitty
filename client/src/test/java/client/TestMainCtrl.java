package client;

import client.MockClass.MainCtrlInterface;
import client.scenes.PairCollector;
import commons.Event;
import commons.Expense;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestMainCtrl implements MainCtrlInterface {

    private String currentScene;
    private final List<String> scenes = new ArrayList<>();

    /**
     * @return current scene
     */
    public String getCurrentScene() {
        return currentScene;
    }

    /**
     * @return scene history
     */
    public List<String> getScenes() {
        return scenes;
    }
    /**
     * Initializes the UI
     *
     * @param primaryStage  stage
     * @param pairCollector collector for all of pairs
     */
    @Override
    public void initialize(Stage primaryStage, PairCollector pairCollector) {

    }

    /**
     * Display start screen
     */
    @Override
    public void showStartScreen() {
        currentScene = "StartScreen";
        scenes.add("StartScreen");
    }

    /**
     * Shows the change
     *
     * @param event current event
     */
    @Override
    public void showEditTitle(Event event) {
        scenes.add("EditTitle");
    }

    /**
     * Display admin login
     */
    @Override
    public void showAdminLogin() {
        currentScene = "AdminLogin";
        scenes.add("AdminLogin");
    }

    /**
     * shows the event page
     *
     * @param eventToShow the event to display
     */
    @Override
    public void showEventPage(Event eventToShow) {
        currentScene = "EventPage";
        scenes.add("EventPage");
    }

    /**
     * this method is used to switch back to the event
     * page from the participant/expense editors
     *
     * @param event the event to show
     */
    @Override
    public void goBackToEventPage(Event event) {
        currentScene = "EventPage";
        scenes.add("EventPage");
    }

    /**
     * shows the participant editor page
     *
     * @param eventToShow the event to show the participant editor for
     */
    @Override
    public void showEditParticipantsPage(Event eventToShow) {
        currentScene = "EditParticipantsPage";
        scenes.add("EditParticipantsPage");
    }

    /**
     * shows the admin overview
     *
     * @param password admin password
     * @param timeOut  time out time in ms
     */
    @Override
    public void showAdminOverview(String password, long timeOut) {
        currentScene = "AdminOverview";
        scenes.add("AdminOverview");
    }

    /**
     * Opens the system file chooser to save something
     *
     * @param fileChooser file chooser
     * @return opened file
     */
    @Override
    public File showSaveFileDialog(FileChooser fileChooser) {
        return null;
    }

    /**
     * Opens the system file chooser to open multiple files
     *
     * @param fileChooser file chooser
     * @return selected files
     */
    @Override
    public List<File> showOpenMultipleFileDialog(FileChooser fileChooser) {
        return List.of();
    }

    /**
     * shows the add/edit expense page
     *
     * @param eventToShow the event to show the participant editor for
     */
    @Override
    public void showAddExpensePage(Event eventToShow) {
        currentScene = "AddExpensePage";
        scenes.add("AddExpensePage");
    }

    /**
     * show the add tag page
     *
     * @param event event
     */
    @Override
    public void showAddTagPage(Event event) {
        scenes.add("AddTagPage");
    }

    /**
     * Handle editing an expense.
     *
     * @param exp The expense to edit.
     * @param ev  The event associated with the expense.
     */
    @Override
    public void handleEditExpense(Expense exp, Event ev) {
        currentScene = "AddExpensePage";
        scenes.add("AddExpensePage");
    }

    /**
     * Disconnects from the server and shows an error
     */
    @Override
    public void handleServerNotFound() {
        currentScene = "StartScreen";
        scenes.add("StartScreen");
    }

    /**
     * display the statistics page
     * @param event
     */
    @Override
    public void showStatisticsPage(Event event) {

    }

    /**
     * show the tag page
     * @param event the current event
     */
    @Override
    public void showTagPage(Event event) {

    }

    /**
     * Initializes a new stage with options
     * and opens it
     */
    @Override
    public void openOptions() {
        scenes.add("Options");
    }

    /**
     * Shows the open debts page
     *
     * @param eventToShow the event to show the open debts for
     */
    @Override
    public void showDebtsPage(Event eventToShow) {
        currentScene = "OpenDebtsPage";
        scenes.add("OpenDebtsPage");
    }

    /**
     * Display a window for adding a custom transaction
     *
     * @param event event to load
     */
    @Override
    public void showAddCustomTransaction(Event event) {
        scenes.add("AddCustomTransaction");
    }

    /**
     * @param languageChoiceBox method for initializing the language switcher
     */
    @Override
    public void initLangChoiceBox(ComboBox<String> languageChoiceBox){
        scenes.add("initLangChoiceBox");
    }


    /**
     * @param  page boolean to indicate the startPage (true) or the eventPage (false)
     */
    @Override
    public void setStartPage(boolean page){scenes.add("setStartPage");}

    /**
     *
     * @param event event to send invite to
     */
    @Override
    public void showInviteMail(Event event) {scenes.add("setInviteMail");}
}
