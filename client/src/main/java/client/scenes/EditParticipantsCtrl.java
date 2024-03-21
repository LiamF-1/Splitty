package client.scenes;

import client.utils.LanguageConf;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Participant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
     * @param server       serverutils instance
     * @param mainCtrl     main control instance
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

        resetFields();

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
                resetFields();
            } else {
                saveButton.setText(languageConf.get("EditP.save"));
                Participant p = event.getParticipants().get(index - 1);
                nameField.setText(p.getName());
                emailField.setText(p.getEmailAddress());

            }
        });

    }

    private void resetFields() {
        saveButton.setText(languageConf.get("EditP.createParticipant"));
        nameField.setText("");
        emailField.setText("");
        ibanField.setText("");
        bicField.setText("");

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
        int index = chooseParticipant.getSelectionModel().getSelectedIndex();
        System.out.println("Creating/saving participant " + index);

        String name = nameField.getText();
        String email = emailField.getText();

        if (index < 0) return;
        if (index == 0) {
            // create a new participant
            Participant newP = new Participant(name, email);
            server.createParticipant(event.getId(), newP);
        } else {
            Participant currP = event.getParticipants().get(index - 1);
            currP.setName(name);
            currP.setEmailAddress(email);
            server.updateParticipant(event.getId(), currP);
        }

    }


}
