package client.scenes;

import client.utils.LanguageConf;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.BankAccount;
import commons.Event;
import commons.Participant;
import commons.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.HashSet;
import java.util.Set;


public class EditParticipantsCtrl {
    @FXML
    private Text eventTitle;
    @FXML
    private ChoiceBox<String> chooseParticipant;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField ibanField;
    @FXML
    private TextField bicField;
    @FXML
    private Button saveButton;

    private Event event;
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private LanguageConf languageConf;

    /**
     * @param server   serverutils instance
     * @param mainCtrl main control instance
     * @param languageConf the language config instance
     */
    @Inject
    public EditParticipantsCtrl(ServerUtils server, MainCtrl mainCtrl, LanguageConf languageConf) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.languageConf = languageConf;

    }

    /**
     * Call this function when showing this page
     *
     * @param e the event to edit the participants for
     */
    public void displayEditParticipantsPage(Event e) {
        this.event = e;
        eventTitle.setText(e.getTitle());

        saveButton.setText(languageConf.get("EditP.createParticipant"));
        nameField.setText("");
        emailField.setText("");
        ibanField.setText("");
        bicField.setText("");

        chooseParticipant.getItems().clear();

        chooseParticipant.getItems().add(languageConf.get("EditP.newParticipant"));
        chooseParticipant
            .getItems()
            .addAll(
                e.getParticipants()
                    .stream()
                    .map(Participant::getName)
                    .toList()
            );

        chooseParticipant.setValue(languageConf.get("EditP.newParticipant"));


        chooseParticipant.setOnAction((event1) -> {
            int index = chooseParticipant.getSelectionModel().getSelectedIndex();
            if (index < 0) return;
            if (index == 0) {
                saveButton.setText(languageConf.get("EditP.createParticipant"));
                nameField.setText("");
                emailField.setText("");
                ibanField.setText("");
                bicField.setText("");
            } else {
                saveButton.setText(languageConf.get("EditP.save"));
                Participant p = event.getParticipants().get(index - 1);
                nameField.setText(p.getName());
                emailField.setText(p.getEmailAddress());

            }
        });

    }

    /**
     * Handler for the back button
     */
    @FXML
    private void backButtonClicked() {
        mainCtrl.showEventPage(event);
    }

    /**
     * Handler for the save button
     */
    @FXML
    private void saveButtonClicked() {
        Set<Expense> expenses = new HashSet<>();
        Set<BankAccount> bankAccounts = new HashSet<>();

        int selectedIndex = chooseParticipant.getSelectionModel().getSelectedIndex();

        Participant newParticipant = new Participant(
                nameField.getText(),
                emailField.getText(),
                expenses,
                bankAccounts
        );

        if (selectedIndex == 0) {
            event.addParticipant(newParticipant);
            chooseParticipant.getItems().add(newParticipant.getName());
            chooseParticipant.getSelectionModel().select(newParticipant.getName());
            saveButton.setText(languageConf.get("EditP.save"));

            nameField.clear();
            emailField.clear();
            ibanField.clear();
            bicField.clear();
        } else {
            event.getParticipants().set(selectedIndex - 1, newParticipant);

            chooseParticipant.getItems().set(selectedIndex, newParticipant.getName());

            chooseParticipant.getSelectionModel().select(newParticipant.getName());
        }


    }


}
