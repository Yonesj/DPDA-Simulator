package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransitionRecord {
    private final StringProperty originState;
    private final StringProperty destinationState;
    private final StringProperty inputSymbol;
    private final StringProperty popSymbol;
    private final StringProperty pushSymbol;

    public TransitionRecord(String originState, String destinationState, String inputSymbol, String popSymbol, String pushSymbol) {
        this.originState = new SimpleStringProperty(originState);
        this.destinationState = new SimpleStringProperty(destinationState);
        this.inputSymbol = new SimpleStringProperty(inputSymbol);
        this.popSymbol = new SimpleStringProperty(popSymbol);
        this.pushSymbol = new SimpleStringProperty(pushSymbol);
    }

    public String getOriginState() {
        return originState.get();
    }

    public String getDestinationState() {
        return destinationState.get();
    }

    public String getInputSymbol() {
        return inputSymbol.get();
    }

    public String getPopSymbol() {
        return popSymbol.get();
    }

    public String getPushSymbol() {
        return pushSymbol.get();
    }

    public StringProperty originStateProperty(){
        return originState;
    }

    public StringProperty destinationStateProperty() {
        return destinationState;
    }

    public StringProperty inputSymbolProperty() {
        return inputSymbol;
    }

    public StringProperty popSymbolProperty() {
        return popSymbol;
    }

    public StringProperty pushSymbolProperty() {
        return pushSymbol;
    }
}
