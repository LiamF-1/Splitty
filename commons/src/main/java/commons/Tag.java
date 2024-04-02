package commons;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
//import java.awt.Color;

@Entity
public class Tag implements Cloneable {

    @Id
    private String name;
    private String color;
    @Column(name = "event_id", length = 5, nullable = false)
    private String eventId;

    /**
     * constructor for tag only name
     * @param name
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * constructor for tag all parameters
     * @param name
     * @param color
     * @param eventId
     */
    public Tag(String name, String color, String eventId) {
        this.name = name;
        this.color = color;
        this.eventId = eventId;
    }

    /**
     * getter for name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for color
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * setter for color
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * getter for the event id
     * @return the event id
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * setter for the event id
     * @param eventId
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * toString method
     * @return
     */
    public String toString() {
        return name;
    }
}
