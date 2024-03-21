package client.scenes;


import client.utils.LanguageConf;
import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.Event;
import commons.Participant;
import commons.Expense;
import commons.WebsocketActions;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;

import javafx.scene.text.Text;


public class EventPageCtrl {

    @FXML
    private Text eventTitle;

    @FXML
    private Text participantText;

    @FXML
    private Tab allTab;

    @FXML
    private Tab fromTab;

    @FXML
    private Tab includingTab;

    @FXML
    private ChoiceBox<String> participantChoiceBox;
    @FXML
    private Button addExpenseButton;


    private int selectedParticipantId;

    private Websocket websocket;

    private ServerUtils server;
    private MainCtrl mainCtrl;
    private LanguageConf languageConf;
    private Event event;
    private String previousEventId = "";


    /**
     * @param server       server utils injection
     * @param mainCtrl     mainCtrl injection
     * @param languageConf the language config instance
     * @param websocket the websocket instance
     */
    @Inject
    public EventPageCtrl(
        ServerUtils server,
        MainCtrl mainCtrl,
        LanguageConf languageConf,
        Websocket websocket
    ) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.languageConf = languageConf;

        this.websocket = websocket;
        websocket.on(WebsocketActions.TITLE_CHANGE, (newTitle) -> {
            event.setTitle(((String) newTitle));
            eventTitle.setText(((String) newTitle));
        });



    }

    /**
     * call this function to set all the text on the eventpage to a given event
     *
     * @param e the event to be shown
     */
    public void displayEvent(Event e) {
        this.event = e;
        eventTitle.setText(e.getTitle());
        participantChoiceBox.getItems().clear();
        participantChoiceBox.setValue("");
        if (e.getParticipants().isEmpty()) {
            noParticipantsExist();
        } else {
            participantsExist();

            StringBuilder p = new StringBuilder();
            for (int i = 0; i < e.getParticipants().size(); i++) {
                p.append(e.getParticipants().get(i).getName());
                if (i != e.getParticipants().size() - 1) p.append(", ");
            }
            participantText.setText(p.toString());

            participantChoiceBox.getItems().addAll(
                    e.getParticipants().stream().map(Participant::getName).toList()
            );
            participantChoiceBox.setValue(e.getParticipants().get(0).getName());
            selectedParticipantId = 0;
            String name = e.getParticipants().get(selectedParticipantId).getName();
            fromTab.setText(languageConf.get("EventPage.from") + " " + name);
            includingTab.setText(languageConf.get("EventPage.including") + " " + name);
        }

        participantChoiceBox.setOnAction(event -> {
            selectedParticipantId = participantChoiceBox.getSelectionModel().getSelectedIndex();
            if (selectedParticipantId < 0) return;
            String name = e.getParticipants().get(selectedParticipantId).getName();
            fromTab.setText(languageConf.get("EventPage.from") + " " + name);
            includingTab.setText(languageConf.get("EventPage.including") + " " + name);
        });

        //if (!previousEventId.equals(event.getId())) websocket.connect(e.getId());
        registerParticipantChangeListener();
        registerExpenseChangeListener();

    }

    private void handleWS() {

    }

    /**
     * Registers all the change listeners on WS if they're not registered already
     */
    private void registerParticipantChangeListener() {
        if (previousEventId.equals(event.getId())) return;
        previousEventId = event.getId();
        websocket.resetAction(WebsocketActions.UPDATE_PARTICIPANT);
        websocket.resetAction(WebsocketActions.ADD_PARTICIPANT);
        websocket.resetAction(WebsocketActions.REMOVE_PARTICIPANT);

        websocket.on(WebsocketActions.UPDATE_PARTICIPANT, (Object part)->{
            Participant p = (Participant) part;
            int index = -1;
            for (int i = 0; i < event.getParticipants().size(); i++) {
                Participant curr = event.getParticipants().get(i);
                if (curr.getParticipantId() == p.getParticipantId()) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                throw new RuntimeException("The updated participant's ID ("
                        + p.getParticipantId()+
                        ") does not match with any ID's of the already existing participants");
            }
            event.getParticipants().remove(index);
            event.getParticipants().add(index, p);
            displayEvent(event);
        });
        websocket.on(WebsocketActions.ADD_PARTICIPANT, (Object part) -> {
            Participant p = (Participant) part;
            event.getParticipants().add(p);
            displayEvent(event);
        });
        websocket.on(WebsocketActions.REMOVE_PARTICIPANT, (Object part) -> {
            long partId = (long) part;
            int index = -1;
            for (int i = 0; i < event.getParticipants().size(); i++) {
                Participant curr = event.getParticipants().get(i);
                if (curr.getParticipantId() == partId) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                throw new RuntimeException("The deleted participant's ID ("
                        + partId+
                        ") does not match with any ID's of the already existing participants");
            }
            event.getParticipants().remove(index);
            displayEvent(event);
        });
    }

    /**
     * Sets the labels' styles for the case in which no participants exist
     */
    private void noParticipantsExist() {
        participantText.setText(languageConf.get("EventPage.noParticipantsYet"));
        allTab.setStyle("-fx-opacity:0");
        allTab.setDisable(true);
        fromTab.setStyle("-fx-opacity:0");
        fromTab.setDisable(true);
        includingTab.setStyle("-fx-opacity:0");
        includingTab.setDisable(true);
        addExpenseButton.setDisable(true);
    }

    /**
     * Sets the labels' styles for the case in which participants do exist
     */
    private void participantsExist() {
        allTab.setStyle("-fx-opacity:1");
        allTab.setDisable(false);
        fromTab.setStyle("-fx-opacity:1");
        fromTab.setDisable(false);
        includingTab.setStyle("-fx-opacity:1");
        includingTab.setDisable(false);
        addExpenseButton.setDisable(false);
    }

    /**
     * Registers all the change listeners on WS if they're not registered already
     *
     */

    private void registerExpenseChangeListener() {
        if (previousEventId.equals(event.getId())) return;
        previousEventId = event.getId();
        websocket.resetAction(WebsocketActions.ADD_EXPENSE);
        websocket.resetAction(WebsocketActions.UPDATE_EXPENSE);
        websocket.resetAction(WebsocketActions.REMOVE_EXPENSE);
        websocket.on(WebsocketActions.ADD_EXPENSE, (Object exp) -> {
            Expense expense = (Expense) exp;
            event.getExpenses().add(expense);
            displayEvent(event);
        });
        websocket.on(WebsocketActions.UPDATE_EXPENSE, (Object exp) -> {
            Expense expense = (Expense) exp;
            int index = -1;
            for (int i = 0; i < event.getExpenses().size(); i++) {
                Expense curr = event.getExpenses().get(i);
                if (curr.getExpenseID() == expense.getExpenseID()) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                throw new RuntimeException("The updated expense's ID ("
                        + expense.getExpenseID()+
                        ") does not match with any ID's of the already existing expenses");
            }
            event.getExpenses().remove(index);
            event.getExpenses().add(index, expense);
            displayEvent(event);
        });
        websocket.on(WebsocketActions.REMOVE_EXPENSE, (Object exp) -> {
            long expId = (long) exp;
            int index = -1;
            for (int i = 0; i < event.getExpenses().size(); i++) {
                Expense curr = event.getExpenses().get(i);
                if (curr.getExpenseID() == expId) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                throw new RuntimeException("The deleted expense's ID ("
                        + expId+
                        ") does not match with any ID's of the already existing expenses");
            }
            event.getExpenses().remove(index);
            displayEvent(event);
        });

    }

    /**
     * Changes the title of the event
     *
     * @param newTitle new title of the event
     */
    public void changeTitle(String newTitle) {
        event.setTitle(newTitle);
        eventTitle.setText(newTitle);

    }

    @FXML
    private void backButtonClicked() {
        websocket.disconnect();
        mainCtrl.showStartScreen();
    }

    @FXML
    private void tabSelectionChanged() {

    }


    @FXML
    private void sendInvitesClicked() {

    }

    @FXML
    private void editParticipantsClicked() {
        mainCtrl.showEditParticipantsPage(event);
    }

    @FXML
    private void addExpenseClicked() {
        mainCtrl.showAddExpensePage(event);

    }


    

}
