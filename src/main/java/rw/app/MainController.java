package rw.app;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rw.battle.*;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import rw.enums.Colors;
import rw.enums.Symbol;
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

    /**
     * Handles click event in the Save menu option.  Saves the Battle information displayed in the GUI as
     * a world.txt file by default.
     * @param event Click event in the Save menu option.
     */
    @FXML
    void saveHandler(ActionEvent event) {
        try {
            Writer.writeBattleToFile(this.battle, "world.txt");
        } catch (RuntimeException e){
            statusLabel.setText(e.getMessage());
        }
        statusLabel.setText("Successfully saved world to world.txt");
    }

    /**
     * Displays Node details in details TextArea when mouse enters the node.
     * @param event Mouse entering the node.
     */
    @FXML
    void gridMouseEnterHandler(MouseEvent event){
        // Get GridPane row and column index of the clicked node
        Node source = (Node) event.getSource();
        int row = GridPane.getRowIndex(source);
        int column = GridPane.getColumnIndex(source);

        // If node is not a perimeter node and not null, then we proceed to display information about the entity
        int rows = this.battle.getRows() + 2;
        int columns = this.battle.getColumns() + 2;
        if(!(row == 0 || row == rows - 1 || column == 0 || column == columns - 1)){
            Entity entity = battle.getEntity(row - 1, column - 1);
            StringBuilder sb = new StringBuilder();
            switch (entity){
                case null -> sb.append("");
                case Wall wall -> sb.append("Type: Wall\n").append("Symbol: ").append(Symbol.WALL.getSymbol()).append("\n");
                case Maximal maximal -> {
                    sb.append("Type: Maximal\n");
                    String symbol = String.valueOf(maximal.getSymbol());
                    String name = maximal.getName();
                    String health = String.valueOf(maximal.getHealth());
                    String weaponStrength = String.valueOf(maximal.weaponStrength());
                    String armourStrength = String.valueOf(maximal.weaponStrength());
                    sb.append("Symbol: ").append(symbol).append("\n")
                            .append("Name: ").append(name).append("\n")
                            .append("Health: ").append(health).append("\n")
                            .append("Weapon Strength: ").append(weaponStrength).append("\n")
                            .append("Armour Strength: ").append(armourStrength).append("\n");
                }
                default -> {
                    PredaCon predacon = (PredaCon) entity;
                    sb.append("Type: PredaCon\n");
                    String symbol = String.valueOf(predacon.getSymbol());
                    String name = predacon.getName();
                    String health = String.valueOf(predacon.getHealth());
                    String weapon = predacon.getWeaponType().name();
                    String weaponStrength = String.valueOf(predacon.weaponStrength());
                    sb.append("Symbol: ").append(symbol).append("\n")
                            .append("Name: ").append(name).append("\n")
                            .append("Health: ").append(health).append("\n")
                            .append("Weapon: ").append(weapon).append("\n")
                            .append("Weapon Strength: ").append(weaponStrength).append("\n");
                }
            }
            detailsOutput.setText(sb.toString());
        }
    }

    /**
     * Clear information displayed in details TextArea when mouse exits a GridPane node.
     * @param event Mouse leaving the GridPane node.
     */
    @FXML
    void gridMouseExitHandler(MouseEvent event){
        detailsOutput.clear();
    }

    /**
     * Right click event handler for a GridPane node.  If the node is not a perimeter wall, then it clears
     * the node both in the GUI and battle attribute.  Finally, populate GridPane again with all changes.
     * @param event Right click event on GridPane node.
     */
    @FXML
    void gridClickHandler(ContextMenuEvent event){
        // Get GridPane row and column index of the clicked node
        Node source = (Node) event.getSource();
        int row = GridPane.getRowIndex(source);
        int column = GridPane.getColumnIndex(source);

        // If node is not a perimeter wall, then we proceed to remove it
        int rows = this.battle.getRows() + 2;
        int columns = this.battle.getColumns() + 2;
        if(!(row == 0 || row == rows - 1 || column == 0 || column == columns - 1)){
            // Remove entity
            this.battle.addEntity(row - 1, column - 1, null);
            // Repopulate grid
            populateGridPane();
        }

    }

    /**
     * Click event handler for Create New Maximal button.  Verifies correctness of input and creates a new
     * Maximal entity in Battle object in given position.
     * @param event Click event for Create New Maximal button.
     */
    @FXML
    void newMaximalHandler(ActionEvent event) {
        try {
            // Read all input fields
            String name = maximalNameInput.getText();
            String symbol = maximalSymbolInput.getText();
            int health = Integer.parseInt(maximalHealthInput.getText());
            int weaponStrength = Integer.parseInt(maximalWeaponStrengthInput.getText());
            int armourStrength = Integer.parseInt(maximalArmorStrengthInput.getText());
            int x = Integer.parseInt(maximalXInput.getText());
            int y = Integer.parseInt(maximalYInput.getText());

            // Verify fields are not empty
            if (name.isEmpty() || symbol.isEmpty()){
                throw new IllegalArgumentException("Please fill in all required Maximal fields.");
            }

            // Verify symbol is char
            if(symbol.length() != 1){
                throw new IllegalArgumentException("Please enter a single character for Maximal symbol.");
            }

            // Verify health is non-negative
            if(health < 0){
                throw new IllegalArgumentException("Please enter a non-negative integer for Maximal health.");
            }

            // Verify coordinates are valid
            if(!this.battle.valid(y,x)){
                throw new IllegalArgumentException("Please enter a Maximal coordinate that is within world bounds.");
            }

            // Add maximal to battle
            Maximal maximal = new Maximal(symbol.charAt(0), name, health, weaponStrength, armourStrength);
            this.battle.addEntity(y, x, maximal);

            // Populate grid
            populateGridPane();

            // Update status
            statusLabel.setText("Added Maximal " + name + " in row " + y + ", column " + x + ".");

            // Reset all input fields
            resetMaximalInputFields();
        } catch (NumberFormatException e){
            statusLabel.setText("Please enter integers for Maximal health, weapon strength, armour strength, x-coord, and y-coord.");
        } catch (IllegalArgumentException e){
            statusLabel.setText(e.getMessage());
        }

    }

    /**
     * Click event handler for Create New PredaCon button.  Verifies correctness of input and creates a new
     * PredaCon entity in Battle object in given position.
     * @param event Click event for Create New PredaCon button.
     */
    @FXML
    void newPredaconHandler(ActionEvent event) {
        try {
            // Read all input fields
            String name = predaconNameInput.getText();
            String symbol = predaconSymbolInput.getText();
            int health = Integer.parseInt(predaconHealthInput.getText());
            int x = Integer.parseInt(predaconXInput.getText());
            int y = Integer.parseInt(predaconYInput.getText());
            String weaponType = predaconWeaponTypeInput.getValue();

            // Verify fields are not empty
            if (name.isEmpty() || symbol.isEmpty() || weaponType == null){
                throw new IllegalArgumentException("Please fill in all required PredaCon fields.");
            }

            // Verify symbol is char
            if(symbol.length() != 1){
                throw new IllegalArgumentException("Please enter a single character for PredaCon symbol.");
            }

            // Verify health is non-negative
            if(health < 0){
                throw new IllegalArgumentException("Please enter a non-negative integer for PredaCon health.");
            }

            // Verify coordinates are valid
            if(!this.battle.valid(y,x)){
                throw new IllegalArgumentException("Please enter a PredaCon coordinate that is within world bounds.");
            }

            // Add predacon to battle
            WeaponType weapon = WeaponType.valueOf(weaponType);
            PredaCon predaCon = new PredaCon(symbol.charAt(0), name, health, weapon);
            this.battle.addEntity(y, x, predaCon);

            // Populate grid
            populateGridPane();

            // Update status
            statusLabel.setText("Added Predacon " + name + " in row " + y + ", column " + x + ".");

            // Reset all input fields
            resetPredaconInputFields();
        } catch (NumberFormatException e){
            statusLabel.setText("Please enter integers for PredaCon health, x-coord, and y-coord.");
        } catch (IllegalArgumentException e){
            statusLabel.setText(e.getMessage());
        }
    }

    /**
     * Click event handler for Create New World button.  Verifies correctness of input and creates a new
     * Battle attribute of given size.
     * @param event Click event for Create New World button.
     */
    @FXML
    void newWorldHandler(ActionEvent event) {
        try {
            int rows = Integer.parseInt(worldRowsInput.getText());
            int columns = Integer.parseInt(worldColumnsInput.getText());

            if(rows < 0 || columns < 0){
                throw new IllegalArgumentException("Rows and columns cannot be negative integers.");
            }

            // Create new battle object of corresponding size
            this.battle = new Battle(rows, columns);
            // Populate grid
            populateGridPane();

            // Reset input fields
            resetWorldInputFields();
        } catch (NumberFormatException e){
            statusLabel.setText("Please enter integer values for rows and columns.");
        } catch (IllegalArgumentException e){
            statusLabel.setText(e.getMessage());
        }
    }

    //////////////////////////////////////////// HELPER METHODS ////////////////////////////////////////////
    /**
     * Populates GridPane according to the information in battle attribute.
     */
    private void populateGridPane(){
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
                        case null -> rectangle.setFill(Colors.EMPTY.getColor());
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
                // Set black outline for rectangles and add handler
                rectangle.setStroke(Color.BLACK);
                // Add rectangle and label to pane
                gridPane.add(rectangle, column, row);
                gridPane.add(label, column, row);
                // Center label in grid
                GridPane.setHalignment(label, Pos.CENTER.getHpos());
                GridPane.setValignment(label, Pos.CENTER.getVpos());
            }
        }

        // Attach handlers to new grid
        attachGridPaneHandlers();
    }

    /**
     * Attaches handlers to every GridPane node.
     */
    private void attachGridPaneHandlers(){
        for (Node node: gridPane.getChildren()){
            // Add mouse handlers
            node.setOnMouseEntered(this::gridMouseEnterHandler);
            node.setOnMouseExited(this::gridMouseExitHandler);
            // Add right-click handler
            node.setOnContextMenuRequested(this::gridClickHandler);
        }
    }

    /**
     * Resets all input fields related to world size.
     */
    private void resetWorldInputFields(){
        worldColumnsInput.clear();
        worldRowsInput.clear();
    }

    /**
     * Resets all input fields related to PredaCons.
     */
    private void resetPredaconInputFields(){
        predaconSymbolInput.clear();
        predaconNameInput.clear();
        predaconHealthInput.clear();
        predaconWeaponTypeInput.setValue(null);
        predaconXInput.clear();
        predaconYInput.clear();
    }

    /**
     * Resets all input fields related to Maximals.
     */
    private void resetMaximalInputFields(){
        maximalSymbolInput.clear();
        maximalNameInput.clear();
        maximalHealthInput.clear();
        maximalWeaponStrengthInput.clear();
        maximalArmorStrengthInput.clear();
        maximalXInput.clear();
        maximalYInput.clear();
    }

}
