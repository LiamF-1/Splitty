package client.scenes;

import client.utils.LanguageConf;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Participant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import commons.Expense;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddExpenseCtrl {
    private boolean splitAll;

    @FXML
    private ChoiceBox<String> expenseAuthor;

    @FXML
    private Text purpose;

    @FXML
    private Text amount;

    @FXML
    private ChoiceBox<String> currency;

    @FXML
    private DatePicker date;

    @FXML
    private CheckBox equalSplit;

    @FXML
    private CheckBox partialSplit;

    @FXML
    private TextFlow expenseParticipants;

    @FXML
    private ChoiceBox<String> type;

    @FXML
    private Button abort;

    @FXML
    private Button add;

    private ServerUtils server;
    private MainCtrl mainCtrl;
    private LanguageConf languageConf;
    private Expense expense;

    /**
     * @param server   serverutils instance
     * @param mainCtrl main control instance
     */
    @Inject
    public AddExpenseCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }


    /**
     * method for displaying the page
     * @param e expense
     */
    public void displayAddExpensePage(Expense e) {
        this.expense = e;
        populateAuthorChoiceBox();
        purpose.setText(e.getPurpose());
        amount.setText(String.valueOf(e.getAmount()));
        populateCurrencyChoiceBox();
        date.setValue(computeDateOfExpense(e));

        if (equalSplit.isSelected()) {
            splitAll = true;
        }
        if (partialSplit.isSelected()) {
            splitAll = false;
        }



    }

    /**
     * fill the choices for the author of the expense
     */
    public void populateAuthorChoiceBox() {
        List<Participant> participants = expense.getExpenseParticipants();
        expenseAuthor.getItems().clear();
        for (Participant p : participants) {
            expenseAuthor.getItems().add(p.getName());

        }
        //default
        if (!participants.isEmpty()) {
            expenseAuthor.setValue(participants.get(0).getName());
        }
    }

    /**
     * fill the choices with currency
     */
    public void populateCurrencyChoiceBox() {
        List<String> currencies = new ArrayList<>();
        currencies.add("USD");
        currencies.add("EUR");
        currencies.add("GBP");
        currencies.add("JPY");
        currency.getItems().clear();
        for (String c : currencies) {
            currency.getItems().add(c);
        }
    }

    /**
     * compute the date of the expense creation
     * @param e expense
     * @return the date converted to LocalDate type
     */
    public LocalDate computeDateOfExpense(Expense e) {
        Date date = e.getDate();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        return instant.atZone(zoneId).toLocalDate();

    }

    /**
     * behaviour for abbort button
     * @param event
     */
    @FXML
    private void handleAbortButton(ActionEvent event) {
        Stage stage = (Stage) abort.getScene().getWindow();
        stage.close();
    }




}
