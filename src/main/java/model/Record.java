package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Record {
    private final StringProperty record;

    public Record(String record) {
        this.record = new SimpleStringProperty(record);
    }

    public StringProperty recordProperty() {
        return record;
    }

    public String getRecord() {
        return record.get();
    }
}
