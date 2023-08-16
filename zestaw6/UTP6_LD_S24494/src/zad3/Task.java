package zad3;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Task {
    String name;
    String output;
    private PropertyChangeListener listener;

    public Task(String name) {
        this.name = name;
        output = "";
    }

    public void addOutputLine(String line) {
        if (listener != null) listener.propertyChange(new PropertyChangeEvent(this, "output", output, output + line));
        output += line;
    }

    public void setPropertyChangeListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    public String toString() {
        return name;
    }
}