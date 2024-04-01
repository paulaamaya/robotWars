package rw.app;

import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rw.battle.Battle;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import rw.battle.Entity;
import rw.battle.Maximal;
import rw.battle.Wall;
import rw.enums.WeaponType;
import rw.util.Reader;
import java.io.File;


/**
 * A controller class for Robot Wars World Editor.
 *
 * @author Paula Amaya
 * @email paula.amaya@ucalgary.ca
 * @tutorial 09
 * @date April 1, 2024
 */

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
        // Load with an empty 3 x 3 battle
        Battle battle = new Battle(3, 3);
        populateGridPane(battle);
    }

    @FXML
    void aboutHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Robot Wars World Editor");
        String aboutInfo = new String("A world editor for a battle simulator.\n" +
                "Author: Paula Amaya\n" + "Email: paula.amaya@ucalgary.ca\n" +
                "Version: " + Main.version + "\n");
        alert.setContentText(aboutInfo);
        alert.show();
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


    @FXML
    void quitHandler(ActionEvent event) {

    }

    @FXML
    void saveAsHandler(ActionEvent event) {

    }

    @FXML
    void saveHandler(ActionEvent event) {

    }

    //////////////////////////////////////////// HELPER METHODS ////////////////////////////////////////////

    /**
     * Takes in a battle object and populates GridPane according to the information in battle.
     * @param battle Battle to be represented in the GridPane
     */
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
                // Pad the perimeter with walls
                if (row == 0 || row == rows - 1 || column == 0 || column == columns - 1) {
                    rectangle = new Rectangle(50, 50, Color.GRAY);
                } else {
                    // Indices in grid pane are increased by one due to padding of columns and rows.
                    Entity entity = battle.getEntity(row - 1, column - 1);
                    rectangle = new Rectangle(50,50);
                    switch (entity) {
                        case null -> rectangle.setFill(Color.WHITE);
                        case Wall wall -> rectangle.setFill(Color.GRAY);
                        case Maximal maximal ->
                                rectangle.setFill(Color.BLUE);
                        default -> rectangle.setFill(Color.RED);
                    }
                }
                // Set black outline for rectangles
                rectangle.setStroke(Color.BLACK);
                // Add rectangle to pane
                gridPane.add(rectangle, column, row);
            }
        }

    }

}
