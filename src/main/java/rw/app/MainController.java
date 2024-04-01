package rw.app;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
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
import rw.enums.Colors;
import rw.enums.WeaponType;
import rw.util.Reader;
import rw.util.Writer;

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

    private Battle battle;

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

    /**
     * Initializes GUI with an empty 3x3 battle.
     */
    @FXML
    public void initialize(){
        // Add weapons to predacon weapons choice box
        for (WeaponType weapon: WeaponType.values()){
            predaconWeaponTypeInput.getItems().add(weapon.name());
        }
        // Load with an empty 3 x 3 battle
        this.battle = new Battle(3, 3);
        populateGridPane();
    }

    /**
     * Handles click event in the About menu option.  Displays alert with project information.
     * @param event Click event in About menu option.
     */
    @FXML
    void aboutHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Robot Wars World Editor");
        String aboutInfo = "A world editor for a battle simulator.\n" +
                "Author: Paula Amaya\n" + "Email: paula.amaya@ucalgary.ca\n" +
                "Version: " + Main.version + "\n";
        alert.setContentText(aboutInfo);
        alert.show();
    }

    /**
     * Handles click event in the Load World menu option.  Prompts user to choose a .txt file from their
     * computer and attempts to create a new World to display in the GUI based on said file.
     * @param event Click event in the Load World menu option.
     */
    @FXML
    void loadHandler(ActionEvent event) {
        // Prompt user to select file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = (Stage) loadButton.getParentPopup().getOwnerWindow();
        File sourceFile = fileChooser.showOpenDialog(stage);
        // Read in source file
        try{
            this.battle = Reader.loadBattle(sourceFile);
            populateGridPane();
        } catch (RuntimeException e){
            statusLabel.setText(e.getMessage());
        }

        // Inform user successfully read file
        statusLabel.setText("Successfully imported world from " + sourceFile.getName() + " file.");
    }


    /**
     * Handles click event in the Quit menu option.  Quits application.
     * @param event Click event in the Quit menu option.
     */
    @FXML
    void quitHandler(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Handles click event in the Save As... menu option.  Prompts user to select a location and enter a name
     * for the file where they want to store the World information.  If input is valid, then proceeds to write
     * Battle information displayed in the GUI as a .txt file.
     * @param event Click event in the Save As... menu option.
     */
    @FXML
    void saveAsHandler(ActionEvent event) {
        // Prompt user to select location
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = (Stage) loadButton.getParentPopup().getOwnerWindow();
        File destinationFile = fileChooser.showSaveDialog(stage);
        try {
            Writer.writeBattleToFile(this.battle, destinationFile.getPath());
        } catch (RuntimeException e){
            statusLabel.setText(e.getMessage());
        }
        statusLabel.setText("Successfully saved world to" + destinationFile.getPath());
    }

    @FXML
    void saveHandler(ActionEvent event) {
        try {
            Writer.writeBattleToFile(this.battle, "world.txt");
        } catch (RuntimeException e){
            statusLabel.setText(e.getMessage());
        }
        statusLabel.setText("Successfully saved world to world.txt");
    }

    //////////////////////////////////////////// HELPER METHODS ////////////////////////////////////////////

    /**
     * Populates GridPane according to the information in battle attribute.
     */
    private void populateGridPane(){
        // TODO: Remove this print statement
        System.out.println(this.battle.battleString());
        // Clear previous content of grid pane if needed
        gridPane.getChildren().clear();

        // Get gridpane
        int rows = this.battle.getRows() + 2;
        int columns = this.battle.getColumns() + 2;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Rectangle rectangle;
                Label label = new Label();
                label.setFont(new Font("Candara", 12));
                label.setTextAlignment(TextAlignment.CENTER);
                // Pad the perimeter with walls
                if (row == 0 || row == rows - 1 || column == 0 || column == columns - 1) {
                    rectangle = new Rectangle(50, 50, Colors.WALL.getColor());
                    label.setText("#");
                } else {
                    // Indices in grid pane are increased by one due to padding of columns and rows.
                    Entity entity = this.battle.getEntity(row - 1, column - 1);
                    rectangle = new Rectangle(50,50);
                    switch (entity) {
                        case null -> {
                            rectangle.setFill(Colors.EMPTY.getColor());
                        }
                        case Wall wall -> {
                            rectangle.setFill(Colors.WALL.getColor());
                            label.setText(String.valueOf(entity.getSymbol()));
                        }
                        case Maximal maximal -> {
                            rectangle.setFill(Colors.MAXIMAL.getColor());
                            label.setText(String.valueOf(entity.getSymbol()));
                        }
                        default -> {
                            rectangle.setFill(Colors.PREDACON.getColor());
                            label.setText(String.valueOf(entity.getSymbol()));
                        }
                    }
                }
                // Set black outline for rectangles
                rectangle.setStroke(Color.BLACK);
                // Add rectangle and label to pane
                gridPane.add(rectangle, column, row);
                gridPane.add(label, column, row);
                // Center label in grid
                GridPane.setHalignment(label, Pos.CENTER.getHpos());
                GridPane.setValignment(label, Pos.CENTER.getVpos());
            }
        }

    }

}
