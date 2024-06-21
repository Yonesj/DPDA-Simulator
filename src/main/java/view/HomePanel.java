package view;

import controller.PDAController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.SplitPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Record;
import model.PDA;
import model.PDAs;

import java.util.Set;

public class HomePanel {
    private Stage stage;

    private PDA pda;
    private String input;
    private int inputIndex = 0;

    private TableView<Record> languagesTable;

    private AnchorPane rightPane;
    private Label remainingInputTextField;
    private TableView<Record> stackTable;
    private TableView<Record> transitionsTable;
    private AnchorPane stackContent;

    private AnchorPane lowerRightPane;
    private Label languageNameTextField;
    private TextField inputTextField;
    private Button submitBtn;
    private ToolBar toolBar;
    private Button firstStateBtn;
    private Button nextStateBtn;
    private Button previousStateBtn;
    private Button lastStateBtn;

    public HomePanel(Stage stage) {
        this.stage = stage;
    }

    public AnchorPane getPanel() {
        // Create the main AnchorPane
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1280, 720);

        // Create the main SplitPane
        SplitPane mainSplitPane = new SplitPane();
        mainSplitPane.setDividerPositions(0.3);
        mainSplitPane.setPrefSize(1280, 720);
        AnchorPane.setTopAnchor(mainSplitPane, 0.0);
        AnchorPane.setRightAnchor(mainSplitPane, 0.0);
        AnchorPane.setBottomAnchor(mainSplitPane, 0.0);
        AnchorPane.setLeftAnchor(mainSplitPane, 0.0);

        // Create the left AnchorPane
        AnchorPane leftPane = new AnchorPane();
        leftPane.setPrefSize(317, 719);
        leftPane.setStyle("-fx-background-color: #e8e8e8;");

        Label dpdasLabel = new Label("DPDAs");
        dpdasLabel.setLayoutX(37);
        dpdasLabel.setLayoutY(55);
        dpdasLabel.setPrefSize(226, 42);
        dpdasLabel.setFont(new Font("SansSerif Regular", 48));

        Separator separator = new Separator();
        separator.setLayoutY(157);
        separator.setPrefSize(252, 11);
        AnchorPane.setLeftAnchor(separator, 0.0);
        AnchorPane.setRightAnchor(separator, 0.0);

        Button addLanguageBtn = new Button("+");
        addLanguageBtn.setStyle("-fx-background-color: #DC143C; -fx-background-radius: 29;");
        addLanguageBtn.setTextFill(javafx.scene.paint.Color.WHITE);
        addLanguageBtn.setFont(new Font(20));
        addLanguageBtn.setLayoutX(291);
        addLanguageBtn.setLayoutY(53);
        addLanguageBtn.setPrefSize(58, 58);
        AnchorPane.setRightAnchor(addLanguageBtn, 30.6);
        addLanguageBtn.setOnMouseClicked(this::addLanguage);

        ScrollPane leftScrollPane = new ScrollPane();
        leftScrollPane.setLayoutX(26);
        leftScrollPane.setLayoutY(190);
        leftScrollPane.setPrefSize(200, 472);
        AnchorPane.setLeftAnchor(leftScrollPane, 26.0);
        AnchorPane.setRightAnchor(leftScrollPane, 26.0);

        AnchorPane leftContent = new AnchorPane();
        leftContent.setPrefSize(320, 1000);
        languagesTable = new TableView<>();
        languagesTable.setPrefSize(320, 1000);

        TableColumn<Record, String> languageCol = new TableColumn<>("language");
        languageCol.setCellValueFactory(new PropertyValueFactory<>("record"));
        languagesTable.getColumns().add(languageCol);
        languageCol.setPrefWidth(320);
        languagesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        languagesTable.setOnMouseClicked(this::selectLanguage);

        AnchorPane.setLeftAnchor(languagesTable, 0.0);
        AnchorPane.setRightAnchor(languagesTable, 0.0);
        leftContent.getChildren().add(languagesTable);
        leftScrollPane.setContent(leftContent);

        fillLanguageTable();

        leftPane.getChildren().addAll(dpdasLabel, separator, addLanguageBtn, leftScrollPane);

        // Create the right AnchorPane
        rightPane = new AnchorPane();
        rightPane.setPrefSize(1238, 907);

        SplitPane rightSplitPane = new SplitPane();
        rightSplitPane.setOrientation(Orientation.VERTICAL);
        rightSplitPane.setDividerPositions(0.7);
        rightSplitPane.setPrefSize(160, 200);
        AnchorPane.setTopAnchor(rightSplitPane, 0.0);
        AnchorPane.setRightAnchor(rightSplitPane, 0.0);
        AnchorPane.setBottomAnchor(rightSplitPane, 0.0);
        AnchorPane.setLeftAnchor(rightSplitPane, 0.0);

        // Upper part of the right split pane
        AnchorPane upperRightPane = new AnchorPane();
        upperRightPane.setPrefSize(212, 189);

        ScrollPane transitionsScrollPane = new ScrollPane();
        transitionsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        transitionsScrollPane.setLayoutX(326);
        transitionsScrollPane.setLayoutY(116);
        transitionsScrollPane.setPrefSize(506, 338);

        AnchorPane transitionsContent = new AnchorPane();
        transitionsContent.setPrefSize(492, 994);
        transitionsTable = new TableView<>();
        transitionsTable.setPrefSize(494, 1002);
        TableColumn<Record, String> transitionCol = new TableColumn<>("transitions");
        transitionCol.setCellValueFactory(new PropertyValueFactory<>("record"));
        transitionsTable.getColumns().add(transitionCol);
        transitionCol.setPrefWidth(494);
        transitionsContent.getChildren().add(transitionsTable);
        transitionsScrollPane.setContent(transitionsContent);

        ScrollPane stackScrollPane = new ScrollPane();
        stackScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        stackScrollPane.setLayoutX(86);
        stackScrollPane.setLayoutY(116);
        stackScrollPane.setPrefSize(158, 338);

        stackContent = new AnchorPane();
        stackContent.setPrefSize(144, 1000);

        stackTable = new TableView<>();
        stackTable.setPrefSize(146, 1000);
        TableColumn<Record, String> stackCol = new TableColumn<>("stack");
        stackCol.setCellValueFactory(new PropertyValueFactory<>("record"));
        stackTable.getColumns().add(stackCol);
        stackCol.setPrefWidth(146);


        stackContent.getChildren().add(stackTable);
        stackScrollPane.setContent(stackContent);

        Label remainingInputLabel = new Label("Remaining input");
        remainingInputLabel.setLayoutX(86);
        remainingInputLabel.setLayoutY(32);
        remainingInputLabel.setPrefSize(146, 46);
        remainingInputLabel.setTextFill(javafx.scene.paint.Color.web("#969696"));
        remainingInputLabel.setFont(new Font("SansSerif Regular", 18));

        remainingInputTextField = new Label("");
        remainingInputTextField.setLayoutX(326);
        remainingInputTextField.setLayoutY(28);
        remainingInputTextField.setPrefSize(506, 54);
        remainingInputTextField.setFont(new Font(36));

        upperRightPane.getChildren().addAll(transitionsScrollPane, stackScrollPane, remainingInputLabel, remainingInputTextField);

        // Lower part of the right split pane
        lowerRightPane = new AnchorPane();
        lowerRightPane.setPrefSize(1019, 350);

        languageNameTextField = new Label("Label");
        languageNameTextField.setLayoutX(38);
        languageNameTextField.setLayoutY(51);
        languageNameTextField.setPrefSize(943, 54);
        languageNameTextField.setFont(new Font(36));
        AnchorPane.setLeftAnchor(languageNameTextField, 38.0);
        AnchorPane.setRightAnchor(languageNameTextField, 38.0);
        AnchorPane.setTopAnchor(languageNameTextField, 51.0);

        inputTextField = new TextField();
        inputTextField.setLayoutX(38);
        inputTextField.setLayoutY(114);
        inputTextField.setPromptText("input your string");
        inputTextField.setPrefSize(642, 46);
        inputTextField.setFont(new Font(18));
        AnchorPane.setLeftAnchor(inputTextField, 38.0);
        AnchorPane.setTopAnchor(inputTextField, 114.0);

        submitBtn = new Button("submit");
        submitBtn.setDefaultButton(true);
        submitBtn.setStyle("-fx-background-color: #DC143C; -fx-background-radius: 10;");
        submitBtn.setTextFill(javafx.scene.paint.Color.web("#e8e8e8"));
        submitBtn.setPrefSize(146, 46);
        submitBtn.setLayoutX(834);
        submitBtn.setLayoutY(114);
        submitBtn.setOnMouseClicked(this::submitInput);
        AnchorPane.setRightAnchor(submitBtn, 38.8);
        AnchorPane.setTopAnchor(submitBtn, 114.0);

        toolBar = new ToolBar();
        toolBar.setPrefSize(1019, 26);
        AnchorPane.setTopAnchor(toolBar, 0.0);
        AnchorPane.setLeftAnchor(toolBar, 0.0);
        AnchorPane.setRightAnchor(toolBar, 0.0);

        firstStateBtn = new Button("<<--");
        firstStateBtn.setPrefSize(52, 26);
        firstStateBtn.setOnMouseClicked(this::first);

        previousStateBtn = new Button("<--");
        previousStateBtn.setPrefSize(52, 26);
        previousStateBtn.setOnMouseClicked(this::previous);

        nextStateBtn = new Button("-->");
        nextStateBtn.setPrefSize(52, 26);
        nextStateBtn.setOnMouseClicked(this::next);

        lastStateBtn = new Button("-->>");
        lastStateBtn.setPrefSize(52, 26);
        lastStateBtn.setOnMouseClicked(this::last);

        toolBar.getItems().addAll(firstStateBtn, previousStateBtn, nextStateBtn, lastStateBtn);
        toolBar.setPadding(new Insets(25, 0, 15, 25));

        lowerRightPane.getChildren().addAll(languageNameTextField, inputTextField, submitBtn, toolBar);

        rightSplitPane.getItems().addAll(upperRightPane, lowerRightPane);

        rightPane.getChildren().add(rightSplitPane);

        // Add left and right panes to the main split pane
        mainSplitPane.getItems().addAll(leftPane, rightPane);

        // configure right panel
        rightPane.setVisible(false);
        firstStateBtn.setDisable(true);
        nextStateBtn.setDisable(true);
        previousStateBtn.setDisable(true);
        lastStateBtn.setDisable(true);

        root.getChildren().add(mainSplitPane);
        return root;
    }

    private void fillLanguageTable(){
        ObservableList<Record> items = languagesTable.getItems();
        Set<String> keys = PDAs.getPDAs().keySet();

        if (keys == null){
            return;
        }

        for (String name : keys){
            items.add(new Record(name));
        }
    }

    private void addLanguage(MouseEvent event){
        stage.hide();
        AddPdaPanel pdaPanel = new AddPdaPanel(stage);
        stage.setScene(new Scene(pdaPanel.getPanel(), 900, 600));
        stage.setTitle("PDA panel");
        stage.show();
    }

    private void selectLanguage(MouseEvent event){
        Record selectedItem = languagesTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null){
            return;
        }
        String name = selectedItem.getRecord();
        languageNameTextField.setText(name);
        this.pda = PDAs.getPDAs().getOrDefault(name, null);

        if (pda == null){
            return;
        }

        PDAController.setPDA(pda);
        stackTable.getItems().add(new Record(pda.getSpecialSymbol()));
        rightPane.setVisible(true);
    }

    private void submitInput(MouseEvent event){
        input = inputTextField.getText();
        if (input.isEmpty()){
            return;
        }
        remainingInputTextField.setText(input);
        nextStateBtn.setDisable(false);
        lastStateBtn.setDisable(false);
        submitBtn.setDisable(true);
    }

    private void next(MouseEvent event){
        String currentState = pda.getCurrentState().getElement();
        String popItem = pda.getStackTopItem();
        char inputItem = input.charAt(inputIndex);
        String pushItem = pda.readNextInput(inputItem);

        if (pushItem == null) {
            showResult();
            return;
        }

        ObservableList<Record> stackItems = stackTable.getItems();
        stackItems.removeLast();

        for(char c : pushItem.toCharArray()){
            stackItems.addLast(new Record(Character.toString(c)));
        }

        stackTable.setItems(stackItems);
        transitionsTable.getItems().add(new Record(String.format("T(%s, %c, %s) = {%s, %s}", currentState, inputItem,
                popItem, pda.getCurrentState().getElement(), pushItem)));
        inputIndex++;
        remainingInputTextField.setText(input.substring(inputIndex));

        if (inputIndex == input.length()){
            showResult();
        }
    }

    private void previous(MouseEvent event){}

    private void last(MouseEvent event){
        while (inputIndex != input.length()){
            next(event);
        }
        showResult();
    }

    private void first(MouseEvent event){}

    private void showResult(){
        nextStateBtn.setDisable(true);
        lastStateBtn.setDisable(true);
        submitBtn.setDisable(false);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Result");
        alert.setHeaderText(null);
        alert.setContentText(String.format("string is %s", pda.isInFinalState() ? "Accepted" : "Rejected"));
        alert.showAndWait();
    }
}
