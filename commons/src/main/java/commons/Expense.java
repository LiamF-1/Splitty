package commons;


import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "expenses")
public class Expense {
    /*
    Properties:
    Int expenseID so that one can reuse this type of expense
    String participant for the person that paid
    String purpose for the purpose of the existent expense
    Double amount for the amount paid by the participant
    String currency for the currency that was used then
    Date date for the exact date when the expense was paid
    Boolean splitMethod, 0 - equally among all participant, 1- only a part of them
    List<String> participants for all the people that are splitting the expense
    String type for the type of the current created expense
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private final int expenseID;
    @Column(name = "partipant")
    private String participant;
    @Column(name = "purpose")
    private String purpose;
    @Column(name = "amount")
    private double amount;
    @Column(name = "currency")
    private String currency;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private final Date date;
    @ElementCollection
    @CollectionTable(name = "expense_participants", joinColumns = @JoinColumn(name = "expense_id"))
    @Column(name = "participant")
    private List<String> participants;
    @Column(name = "type")
    private String type;

    /**
     * constructor for Expense class
     * @param expenseID
     * @param participant
     * @param purpose
     * @param amount
     * @param currency
     * @param date
     * @param participants
     * @param type
     */
    public Expense(int expenseID, String participant, String purpose, double amount,
                   String currency, Date date, List<String> participants, String type) {
        this.expenseID = expenseID;
        this.participant = participant;
        this.purpose = purpose;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.participants = participants;
        this.type = type;
    }

    /**
     * getter for expenseID
     * @return the expenseID
     */
    public int getExpenseID() {
        return expenseID;
    }

    /**
     * getter for participant
     * @return the participant
     */
    public String getParticipant() {
        return participant;
    }

    /**
     * getter for purpose
     * @return the purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * getter for amount
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * getter for currency
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * getter for date
     * @return the date when the expense was created
     */
    public Date getDate() {
        return date;
    }

    /**
     * getter for the list of participants
     * @return the list of participants
     */
    public List<String> getParticipants() {
        return participants;
    }

    /**
     * getter for type
     * @return the type of expense
     */
    public String getType() {
        return type;
    }

    /**
     * setter for participant
     * @param participant
     */
    public void setParticipant(String participant) {
        this.participant = participant;
    }

    /**
     * setter for purpose
     * @param purpose
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * setter for amount
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * setter for currency
     * @param currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * setter for type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * equals method
     * @param o
     * @return true if equals, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return expenseID == expense.expenseID
                && Double.compare(expense.amount, amount) == 0
                && Objects.equals(participant, expense.participant)
                && Objects.equals(purpose, expense.purpose)
                && Objects.equals(currency, expense.currency)
                && Objects.equals(date, expense.date)
                && Objects.equals(participants, expense.participants)
                && Objects.equals(type, expense.type);
    }

    /**
     * hashCode method
     * @return an hashCode for a specific object
     */
    @Override
    public int hashCode() {
        return Objects.hash(expenseID, participant, purpose,
                amount, currency, date, participants, type);
    }
}
