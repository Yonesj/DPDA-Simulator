package model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class StateRecord {
    private final StringProperty stateName;
    private final StringProperty isFinal;

    public StateRecord(String stateName, String isFinal) {
        this.stateName = new SimpleStringProperty(stateName);
        this.isFinal = new SimpleStringProperty(isFinal);
    }

    public StringProperty stateNameProperty() {
        return stateName;
    }

    public StringProperty isFinalProperty() {
        return isFinal;
    }

    public String getIsFinal() {
        return isFinal.get();
    }

    public String getStateName() {
        return stateName.get();
    }
}

