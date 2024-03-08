package client.scenes;

import client.components.EventListItem;
import client.utils.LanguageConf;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;


import jakarta.ws.rs.WebApplicationException;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.List;


public class StartScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final LanguageConf languageConf;

    @FXML
    private TextField title;

    @FXML
    private TextField code;

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    @FXML
    private VBox eventList;

    @FXML
    private Text joinError;

    @FXML
    private Text createEventError;

    /**
     * start screen controller constructor
     *
     * @param server   utils
     * @param mainCtrl main scene controller
     * @param languageConf language config instance
     */
    @Inject
    public StartScreenCtrl(ServerUtils server, MainCtrl mainCtrl, LanguageConf languageConf) {
        this.mainCtrl = mainCtrl;
        this.server = server;

        this.languageConf = languageConf;

    }

    /**
     * Initialize method for startscreenctrl
     */
    @FXML
    private void initialize() {
        languageChoiceBox.setValue(languageConf.getCurrentLocaleString());
        languageChoiceBox.getItems().addAll(languageConf.getAvailableLocalesString());
        languageChoiceBox.setOnAction(event -> {
            languageConf.changeCurrentLocaleTo(languageChoiceBox.getValue());
        });

        wordLimitError(code, joinError, 6);
        wordLimitError(title, createEventError,100);

        List<String> testList = List.of("Test1", "random event",
                "heres one more", "idk", "try deleting this");
        List<EventListItem> list = new ArrayList<>();


        for (int i = 0; i < testList.size(); i++) {
            int finalI = i;
            list.add(new EventListItem(testList.get(i), () -> {
                eventList.getChildren().remove(list.get(finalI));
            }));
            eventList.getChildren().add(list.get(i));

        }
    }

    public void wordLimitError(TextField textField, Text errorMessage, int limit){
        String message = errorMessage.getText();
        errorMessage.setFill(Color.RED);
        errorMessage.setVisible(false);
        textField.textProperty().length().addListener((observableValue, number, t1) -> {

            if(textField.getText().length() <= limit){
                errorMessage.setVisible(false);
            }
            if(textField.getText().length() > limit){
                errorMessage.setVisible(true);
                errorMessage.textProperty().bind(Bindings.concat(
                        message, String.format(" %d/%d", textField.getText().length(), limit)));
            }
        });
    }

    /**
     * Creates and joins the event with provided title
     */
    public void create() {
        if (title.getText().isEmpty()) {
            // inform that title is empty
        }
        else if(title.getText().length() > 100){
            // if the user enters a name longer than 100
        }
        try {
            // addEvent should return the code
            //mainCtrl.showEvent(server.addEvent(title.getText()));
        } catch (WebApplicationException e) {
            //error
        }
    }


    /**
     * Tries to join the inputted event
     */
    public void join() {
        if (code.getText().isEmpty()) return;
        if(code.getText().length() > 6){
            // if the user enters an invalid code (longer than 6)
        }
        try {
            Event joinedEvent = server.getEvent(code.getText());
            mainCtrl.showEventPage(joinedEvent);
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }


}
