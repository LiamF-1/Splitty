package client.scenes;

import client.utils.LanguageConf;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Participant;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import commons.Expense;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddExpenseCtrl {
    private boolean splitAll;

    @FXML
    private ChoiceBox<String> expenseAuthor;

    @FXML
    private TextField purpose;

    @FXML
    private TextField amount;

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
    private List<Participant> participants;

    /**
     * @param server   serverutils instance
     * @param mainCtrl main control instance
     */
    @Inject
    public AddExpenseCtrl(ServerUtils server, MainCtrl mainCtrl, List<Participant> participants) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.participants = participants;
    }

    /**
     * Method for displaying the page with a blank expense.
     * @param event the event page to return to
     */
    public void displayAddExpensePage(Event event) {

        populateAuthorChoiceBox();
        populateTypeBox();
        purpose.clear();
        amount.clear();
        populateCurrencyChoiceBox();
        date.setValue(LocalDate.now());
        splitAll = false; // Initialize splitAll to false by default


        equalSplit.setOnAction(e -> {
            if (equalSplit.isSelected()) {
                splitAll = true;
                partialSplit.setSelected(false);
            }
        });

        partialSplit.setOnAction(e -> {
            if (partialSplit.isSelected()) {
                splitAll = false;
                equalSplit.setSelected(false);
            }
        });


        add.setOnAction(x -> {
            boolean addedExpense = handleAddButton();
            if (addedExpense) {
                mainCtrl.showEventPage(event);
            } else {
                //expense was not added properly
            }
            mainCtrl.showEventPage(event); // Navigate back to the event page
        });

        abort.setOnAction(x -> mainCtrl.showEventPage(event));

    }

    /**
     * Fill the choices for the author of the expense.
     */
    public void populateAuthorChoiceBox() {
//        List<Participant> participants = expense.getExpenseParticipants();
//        expenseAuthor.getItems().clear();
//        for (Participant p : participants) {
//            expenseAuthor.getItems().add(p.getName());
//        }
//        // Default selection
//        if (!participants.isEmpty()) {
//            expenseAuthor.setValue(participants.get(0).getName());
//        }
    }

    /**
     * Fill the choices with currency.
     */
    public void populateCurrencyChoiceBox() {
        List<String> currencies = new ArrayList<>();
        currencies.add("USD");
        currencies.add("EUR");
        currencies.add("GBP");
        currencies.add("JPY");
        currency.getItems().clear();
        currency.getItems().addAll(currencies);
    }


    /**
     * Behavior for add button.
     */
    public boolean handleAddButton() {
        try {
            LocalDate expDate = date.getValue();
            String expPurpose = purpose.getText();
            Participant part = new Participant();
            double expAmount = Double.parseDouble(amount.getText());
            String expCurrency = currency.getValue();
            List<Participant> expPart = new ArrayList<>();
            String expType = type.getValue();
            Expense expense = new Expense(part, expPurpose, expAmount,
                    expCurrency, expPart, expType);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * show corresponding tags for expense
     */
    public void populateTypeBox() {
        type.getItems().add("food");
        type.getItems().add("entrance fees");
        type.getItems().add("travel");
    }
}
