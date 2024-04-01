package rw.app;

import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rw.battle.Battle;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import rw.enums.WeaponType;
import rw.util.Reader;

import java.io.File;

public class MainController {

    @FXML
    private MenuItem aboutButton;

    @FXML
    private TextArea detailsOutput;

    @FXML
    private GridPane gridPane;

    @FXML
    private MenuItem loadButton;

    @FXML
    private TextField maximalArmorStrengthInput;

    @FXML
    private TextField maximalHealthInput;

    @FXML
    private TextField maximalNameInput;

    @FXML
    private TextField maximalSymbolInput;

    @FXML
    private TextField maximalWeaponStrengthInput;

    @FXML
    private TextField maximalXInput;

    @FXML
    private TextField maximalYInput;

    @FXML
    private Button newMaximalButton;

    @FXML
    private Button newPredaconButton;

    @FXML
    private Button newWorldButton;

    @FXML
    private TextField predaconHealthInput;

    @FXML
    private TextField predaconNameInput;

    @FXML
    private TextField predaconSymbolInput;

    @FXML
    private ChoiceBox<String> predaconWeaponTypeInput;

    @FXML
    private TextField predaconXInput;

    @FXML
    private TextField predaconYInput;

    @FXML
    private MenuItem quitButton;

    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField worldRowsInput;

    @FXML
    private TextField worldColumnsInput;

    @FXML
    private Font x1;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    public void initialize(){
        // Add weapons to predacon weapons choice box
        for (WeaponType weapon: WeaponType.values()){
            predaconWeaponTypeInput.getItems().add(weapon.name());
        }
    }

    @FXML
    void aboutHandler(ActionEvent event) {

    }

    @FXML
    void loadHandler(ActionEvent event) {
        // Prompt user to select file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        Stage stage = (Stage) loadButton.getParentPopup().getOwnerWindow();
        File sourceFile = fileChooser.showOpenDialog(stage);
        // Read in source file
        try{
            Battle battle = Reader.loadBattle(sourceFile);
            populateGridPane(battle);
        } catch (RuntimeException e){
            statusLabel.setText(e.getMessage());
        }

    }

    private void populateGridPane(Battle battle){
        System.out.println(battle.battleString());
        // Clear previous content of grid pane if needed
        gridPane.getChildren().clear();

        // Get gridpane
        int rows = battle.getRows() + 2;
        int columns = battle.getColumns() + 2;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Rectangle rectangle;
                if (row == 0 || row == rows - 1 || column == 0 || column == columns - 1) {
                    rectangle = new Rectangle(50, 50, Color.GRAY);
                } else {
                    rectangle = new Rectangle(50, 50, Color.WHITE);
                }
                rectangle.setStroke(Color.BLACK); // Set black outline
                gridPane.add(rectangle, column, row);
            }
        }

    }

    @FXML
    void quitHandler(ActionEvent event) {

    }

    @FXML
    void saveAsHandler(ActionEvent event) {

    }

    @FXML
    void saveHandler(ActionEvent event) {

    }

}
