package rw.util;

import rw.battle.*;
import rw.enums.Symbol;
import rw.enums.WeaponType;

import java.io.*;

/**
 * The Reader class provides static methods for reading data from a file and constructing a Battle object.
 * It reads a text file containing information about entities and their attributes, and initializes a Battle
 * based on the data read from the file.
 *
 * @author Paula Amaya
 * @email paula.amaya@ucalgary.ca
 * @tutorial 09
 * @date March 31, 2024
 */

public final class Reader {

    /**
     * Reads data from a given file and constructs a Battle object based on the information read.
     * @param file File to read from.
     * @return a Battle object initialized with data read from the file.
     * @throws RuntimeException if an error occurs while reading the file.
     */
    public static Battle loadBattle(File file) {
        Battle battle;

        try(BufferedReader reader = new BufferedReader(new FileReader(file));){

            // BATTLE DIMENSIONS
            // Read first line as rows
            String line = reader.readLine();
            int rows = Integer.parseInt(line.trim());

            // Read second line as columns
            line = reader.readLine();
            int columns = Integer.parseInt(line.trim());
            // Verify rows and columns are positive
            if(rows < 0 || columns < 0){
                throw new ArrayIndexOutOfBoundsException("Wrong input file! World cannot have negative dimensions");
            }
            battle = new Battle(rows, columns);

            // ENTITY INFORMATION
            line = reader.readLine();
            while (line != null){

                // Extract values separated by spaces into an array.
                String[] values = line.trim().split(",");

                // If there are more than two values in the row then it has an entity and we must process
                if(values.length > 2){

                    // Verify validity of coordinates
                    int row = Integer.parseInt(values[0]);
                    int col = Integer.parseInt(values[1]);
                    if(!battle.valid(row, col)){
                        throw new ArrayIndexOutOfBoundsException("Wrong input file! Position is outside of the map.");
                    }

                    // Verify validity of EntityType (independent of letter case)
                    String entityType = values[2].toUpperCase();
                    if (!isValidEntityType(entityType)){
                        throw new IllegalArgumentException("Invalid entity type " + entityType);
                    }

                    // Process each entity type differently
                    switch (entityType){
                        case "WALL":
                            battle.addEntity(row, col, Wall.getWall());
                            break;
                        case "MAXIMAL":
                            // Check that maximal has all required entries
                            if(values.length < 8){
                                throw new IllegalArgumentException("Wrong input file! Missing Maximal robot attributes.");
                            }
                            // Check that maximal has valid symbol
                            if(values[3].length() != 1){
                                throw new IllegalArgumentException("Wrong input file! Not a valid symbol for a Maximal.");
                            }
                            // Add maximal robot according to read data
                            char symbol = values[3].charAt(0);
                            String name = values[4];
                            int health = Integer.parseInt(values[5]);
                            int weaponStrength = Integer.parseInt(values[6]);
                            int armourStrength = Integer.parseInt(values[7]);
                            battle.addEntity(row, col, new Maximal(symbol, name, health, weaponStrength, armourStrength));
                            break;
                        default:
                            // Check that predacon has all required entries
                            if(values.length < 7){
                                throw new IllegalArgumentException("Wrong input file! Missing Predacon robot attributes.");
                            }
                            // Check that predacon has valid symbol
                            if(values[3].length() != 1){
                                throw new IllegalArgumentException("Wrong input file! Not a valid symbol for a Predacon.");
                            }
                            char s = values[3].charAt(0);
                            String n = values[4];
                            int h = Integer.parseInt(values[5]);
                            String weapon = values[6].toUpperCase();
                            // Check that predacon has valid weapon
                            if(!isValidWeapon(weapon)){
                                throw new IllegalArgumentException("Wrong input file! Not a valid weapon for a Predacon.");
                            }
                            // Add predacon according to read data
                            PredaCon p;
                            if (weapon.equals("C")){
                                p = new PredaCon(s, n, h, WeaponType.CLAWS);
                            } else if (weapon.equals("L")) {
                                p = new PredaCon(s, n, h, WeaponType.LASER);
                            } else {
                                p = new PredaCon(s, n, h, WeaponType.TEETH);
                            }
                            battle.addEntity(row, col, p);
                            break;
                    }


                }

                // Move to next line
                line = reader.readLine();
            }

            // Close reader
            reader.close();
        } catch (IOException e){
            throw new UncheckedIOException("Problem reading file " + file.getName(), e);
        } catch (NumberFormatException e){
            throw new NumberFormatException("Invalid data format: Unable to parse numeric value.");
        }

        return battle;
    }

    /**
     * Takes in a string a determines if it is a valid symbol for a Predacon weapon.
     * @param weapon String representing a potential Predacon weapon.
     * @return true iff weapon is a valid symbol for a Predacon weapon.
     */
    private static boolean isValidWeapon(String weapon) {
        return weapon.equals("C") || weapon.equals("L") || weapon.equals("T");
    }

    /**
     * Takes in a String in uppercase and determines if it is a valid Entity type.
     * @param type String representing a potential Entity type.
     * @return true iff type is a valid Entity.
     */
    private static boolean isValidEntityType(String type){
        return type.equals("WALL") || type.equals("PREDACON") || type.equals("MAXIMAL");
    }
}
