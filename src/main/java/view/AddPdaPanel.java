package view;

import controller.PDAController;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.PDAs;
import model.StateRecord;
import model.TransitionRecord;
import java.io.IOException;


public class AddPdaPanel {
    private Stage stage;
    private PDAController controller;

    private TextField languageTextField;
    private TextField stateTextField;
    private CheckBox isFinalCheckBox;
    private Button addStateBtn;
    private TableView<StateRecord> statesTable;
    private TextField originStateTextField;
    private TextField destinationTextField;
    private TextField inputTextField;
    private TextField popsTextField;
    private TextField pushTextField;
    private Button addTransitionBtn;
    private TableView<TransitionRecord> transitionsTable;
    private Button backBtn;
    private Button submitBtn;

    public AddPdaPanel(Stage stage) {
        this.stage = stage;
        this.controller = new PDAController();
        stage.setOnCloseRequest(this::savePDAs);
    }

    public AnchorPane getPanel() {
        // Initialize UI components
        AnchorPane root = new AnchorPane();

        languageTextField = new TextField();
        languageTextField.setLayoutX(51);
        languageTextField.setLayoutY(160);
        languageTextField.setPrefHeight(42);
        languageTextField.setPrefWidth(793);
        languageTextField.setPromptText("language");
        languageTextField.setFont(new Font(18));

        Label titleLabel = new Label("PDA insertion");
        titleLabel.setLayoutX(337);
        titleLabel.setLayoutY(14);
        titleLabel.setPrefHeight(54);
        titleLabel.setPrefWidth(226);
        titleLabel.setFont(new Font(36));

        backBtn = new Button("back");
        backBtn.setLayoutX(51);
        backBtn.setLayoutY(38);
        backBtn.setPrefHeight(46);
        backBtn.setPrefWidth(73);
        backBtn.setStyle("-fx-background-color: #DC143C; -fx-background-radius: 10;");
        backBtn.setTextFill(javafx.scene.paint.Color.WHITE);
        backBtn.setOnMouseClicked(this::backToHomePanel);

        submitBtn = new Button("submit");
        submitBtn.setLayoutX(771);
        submitBtn.setLayoutY(38);
        submitBtn.setPrefHeight(46);
        submitBtn.setPrefWidth(73);
        submitBtn.setStyle("-fx-background-color: #DC143C; -fx-background-radius: 10;");
        submitBtn.setTextFill(javafx.scene.paint.Color.WHITE);
        submitBtn.setOnMouseClicked(this::addPDA);

        Separator separator = new Separator();
        separator.setLayoutY(108);
        separator.setPrefHeight(23);
        separator.setPrefWidth(900);

        stateTextField = new TextField();
        stateTextField.setLayoutX(51);
        stateTextField.setLayoutY(239);
        stateTextField.setPrefHeight(42);
        stateTextField.setPrefWidth(117);
        stateTextField.setPromptText("state name");
        stateTextField.setFont(new Font(18));

        isFinalCheckBox = new CheckBox("is final");
        isFinalCheckBox.setLayoutX(188);
        isFinalCheckBox.setLayoutY(239);
        isFinalCheckBox.setPrefHeight(42);
        isFinalCheckBox.setPrefWidth(103);
        isFinalCheckBox.setFont(new Font(18));

        addStateBtn = new Button("+");
        addStateBtn.setLayoutX(291);
        addStateBtn.setLayoutY(242);
        addStateBtn.setPrefHeight(36);
        addStateBtn.setPrefWidth(36);
        addStateBtn.setStyle("-fx-background-color: #DC143C; -fx-background-radius: 23;");
        addStateBtn.setTextFill(javafx.scene.paint.Color.WHITE);
        addStateBtn.setOnMouseClicked(this::addState);

        statesTable = new TableView<>();
        statesTable.setLayoutX(51);
        statesTable.setLayoutY(311);
        statesTable.setPrefHeight(251);
        statesTable.setPrefWidth(274);

        TableColumn<StateRecord, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<>("stateName"));
        TableColumn<StateRecord, String> isFinalCol = new TableColumn<>("IsFinal");
        isFinalCol.setCellValueFactory(new PropertyValueFactory<>("isFinal"));
        statesTable.getColumns().add(stateCol);
        statesTable.getColumns().add(isFinalCol);
        statesTable.setOnMouseClicked(this::removeState);

        ScrollPane statesScrollPane = new ScrollPane(statesTable);
        statesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        statesScrollPane.setLayoutX(51);
        statesScrollPane.setLayoutY(311);
        statesScrollPane.setPrefHeight(251);
        statesScrollPane.setPrefWidth(274);

        originStateTextField = new TextField();
        originStateTextField.setLayoutX(396);
        originStateTextField.setLayoutY(239);
        originStateTextField.setPrefHeight(42);
        originStateTextField.setPrefWidth(74);
        originStateTextField.setPromptText(" origin state");
        originStateTextField.setFont(new Font(10));

        destinationTextField = new TextField();
        destinationTextField.setLayoutX(481);
        destinationTextField.setLayoutY(239);
        destinationTextField.setPrefHeight(42);
        destinationTextField.setPrefWidth(74);
        destinationTextField.setPromptText("destination state");
        destinationTextField.setFont(new Font(10));

        inputTextField = new TextField();
        inputTextField.setLayoutX(563);
        inputTextField.setLayoutY(239);
        inputTextField.setPrefHeight(42);
        inputTextField.setPrefWidth(74);
        inputTextField.setPromptText("input symbol");
        inputTextField.setFont(new Font(10));

        popsTextField = new TextField();
        popsTextField.setLayoutX(645);
        popsTextField.setLayoutY(239);
        popsTextField.setPrefHeight(42);
        popsTextField.setPrefWidth(74);
        popsTextField.setPromptText("pop symbol");
        popsTextField.setFont(new Font(10));

        pushTextField = new TextField();
        pushTextField.setLayoutX(727);
        pushTextField.setLayoutY(239);
        pushTextField.setPrefHeight(42);
        pushTextField.setPrefWidth(74);
        pushTextField.setPromptText("push symbol");
        pushTextField.setFont(new Font(10));

        addTransitionBtn = new Button("+");
        addTransitionBtn.setLayoutX(808);
        addTransitionBtn.setLayoutY(242);
        addTransitionBtn.setPrefHeight(36);
        addTransitionBtn.setPrefWidth(36);
        addTransitionBtn.setStyle("-fx-background-color: #DC143C; -fx-background-radius: 23;");
        addTransitionBtn.setTextFill(javafx.scene.paint.Color.WHITE);
        addTransitionBtn.setOnMouseClicked(this::addTransition);

        transitionsTable = new TableView<>();
        transitionsTable.setLayoutX(396);
        transitionsTable.setLayoutY(311);
        transitionsTable.setPrefHeight(251);
        transitionsTable.setPrefWidth(446);

        TableColumn<TransitionRecord, String> originCol = new TableColumn<>("origin");
        originCol.setCellValueFactory(new PropertyValueFactory<>("originState"));
        TableColumn<TransitionRecord, String> destinationCol = new TableColumn<>("destination");
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destinationState"));
        TableColumn<TransitionRecord, String> inputCol = new TableColumn<>("input symbol");
        inputCol.setCellValueFactory(new PropertyValueFactory<>("inputSymbol"));
        TableColumn<TransitionRecord, String> popCol = new TableColumn<>("pop symbol");
        popCol.setCellValueFactory(new PropertyValueFactory<>("popSymbol"));
        TableColumn<TransitionRecord, String> pushCol = new TableColumn<>("push symbol");
        pushCol.setCellValueFactory(new PropertyValueFactory<>("pushSymbol"));

        transitionsTable.getColumns().add(originCol);
        transitionsTable.getColumns().add(destinationCol);
        transitionsTable.getColumns().add(inputCol);
        transitionsTable.getColumns().add(popCol);
        transitionsTable.getColumns().add(pushCol);

        transitionsTable.setOnMouseClicked(this::removeTransition);

        ScrollPane transitionsScrollPane = new ScrollPane(transitionsTable);
        transitionsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        transitionsScrollPane.setLayoutX(396);
        transitionsScrollPane.setLayoutY(311);
        transitionsScrollPane.setPrefHeight(251);
        transitionsScrollPane.setPrefWidth(446);

        root.getChildren().addAll(languageTextField, titleLabel, backBtn, submitBtn, separator, stateTextField, isFinalCheckBox, addStateBtn, statesScrollPane, originStateTextField, destinationTextField, inputTextField, popsTextField, pushTextField, addTransitionBtn, transitionsScrollPane);
        return root;
    }

    private void addState(MouseEvent event) {
        String stateName = stateTextField.getText();
        boolean isFinal = isFinalCheckBox.isSelected();
        stateTextField.clear();

        if (stateName.isBlank()) {
            showErrorAlert("Please enter a valid state");
        } else {
            statesTable.getItems().add(controller.addState(stateName, isFinal));
        }
    }

    private void removeState(MouseEvent event){
        if (event.getClickCount() == 2){
            StateRecord selectedRecord = statesTable.getSelectionModel().getSelectedItem();
            if (selectedRecord != null) {
                statesTable.getItems().remove(selectedRecord);
                controller.removeState(selectedRecord.getStateName());
            }
        }
    }

    private void addTransition(MouseEvent event) {
        String originState = originStateTextField.getText();
        String destinationState = destinationTextField.getText();
        String inputSymbol = inputTextField.getText();
        String popSymbol = popsTextField.getText();
        String pushSymbol = pushTextField.getText();

        // Clear the text fields after adding the transition
        originStateTextField.clear();
        destinationTextField.clear();
        inputTextField.clear();
        popsTextField.clear();
        pushTextField.clear();

        if (originState.isBlank() || destinationState.isBlank() || inputSymbol.isBlank() || popSymbol.isBlank() || pushSymbol.isBlank()) {
            showErrorAlert("All fields must be filled to add a transition");
            return;
        }

        try {
            transitionsTable.getItems().add(controller.addTransition(originState, destinationState, inputSymbol, popSymbol, pushSymbol));
        } catch (IllegalArgumentException e){
            showErrorAlert(e.getMessage());
        }
    }

    private void removeTransition(MouseEvent event){
        if (event.getClickCount() == 2){
            TransitionRecord selectedRecord = transitionsTable.getSelectionModel().getSelectedItem();
            if (selectedRecord != null) {
                transitionsTable.getItems().remove(selectedRecord);
                controller.removeTransition(selectedRecord.getOriginState(), selectedRecord.getDestinationState(),
                        selectedRecord.getInputSymbol(), selectedRecord.getPopSymbol(), selectedRecord.getPushSymbol());
            }
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void backToHomePanel(MouseEvent event) {
        stage.hide();
        savePDAs(event);
        HomePanel homePanel = new HomePanel(stage);
        Scene homeScene = new Scene(homePanel.getPanel(), 1280, 720);
        stage.setScene(homeScene);
        stage.setTitle("home panel");
        stage.show();
    }

    private void addPDA(MouseEvent event) {
        if (languageTextField.getText().isEmpty()){
            showErrorAlert("language text field must be filled");
            return;
        }

        try {
            controller.addPDA(languageTextField.getText());
        } catch (IllegalArgumentException e){
            showErrorAlert(e.getMessage());
        }

        backToHomePanel(event);
    }

    private void savePDAs(Event event){
        try {
            PDAs.saveConfiguration();
        }catch (IOException e){
            showErrorAlert(e.toString());
        }
    }
}
