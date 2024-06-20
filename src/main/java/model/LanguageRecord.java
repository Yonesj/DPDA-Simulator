package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LanguageRecord {
    private final StringProperty languageName;

    public LanguageRecord(String stateName) {
        this.languageName = new SimpleStringProperty(stateName);
    }

    public StringProperty languageNameProperty() {
        return languageName;
    }

    public String getLanguageName() {
        return languageName.get();
    }
}
