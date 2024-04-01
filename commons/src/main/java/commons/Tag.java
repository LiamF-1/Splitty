package commons;

import java.awt.*;

public class Tag {

    private String name;
    private Color color;
    private Event event;

    public Tag(String name, Color color, Event event) {
        this.name = name;
        this.color = color;
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String toString() {
        return name;
    }
}
