package rw.util;

import javafx.scene.paint.Color;
import rw.battle.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Class to writing to a battle file that has been edited using GUI.
 *
 * @author Paula Amaya
 * @email paula.amaya@ucalgary.ca
 * @version 1.0
 * @tutorial 09
 */
public class Writer {

    public void writeBattleToFile(Battle battle, String filepath){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            int battleRows = battle.getRows();
            int battleCols = battle.getColumns();
            // Write rows
            writer.write(battleRows);
            // Write columns
            writer.write(battleCols);

            // Iterate through each entry and write appropriate entity information
            for (int row = 0; row < battleRows; row++) {
                for (int col = 0; col < battleCols; col++) {
                    StringBuilder sb = new StringBuilder(row + "," + col);
                    Entity entity = battle.getEntity(row, col);
                    // Add additional information for non-empty spaces
                    if (entity != null){
                        if (entity instanceof Wall){
                            // WALL
                            sb.append(",WALL");
                        } else if (entity instanceof PredaCon) {
                            // PREDACON
                            PredaCon predacon = (PredaCon) entity;
                            sb.append(",PREDACON");
                            sb.append(",").append(predacon.getSymbol());
                            sb.append(",").append(predacon.getName());
                            sb.append(",").append(predacon.getHealth());
                            sb.append(",").append(predacon.getWeaponType().name().charAt(0));
                        } else {
                            // MAXIMAL
                            Maximal maximal = (Maximal) entity;
                            sb.append("MAXIMAL");
                            sb.append(",").append(maximal.getSymbol());
                            sb.append(",").append(maximal.getName());
                            sb.append(",").append(maximal.getHealth());
                            sb.append(",").append(maximal.weaponStrength());
                            sb.append(",").append(maximal.armorStrength());
                        }
                    }

                    // Write entry in a new line
                    writer.write(sb.toString());
                }

            }
        } catch (IOException e){
            throw new UncheckedIOException("Problem writing battle to file " + filepath, e);
        }
    }
}
