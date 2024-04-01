package rw.enums;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Enumeration for common colors to be used in GUI.
 *
 * @author Paula Amaya
 * @email paula.amaya@ucalgary.ca
 * @tutorial 09
 * @date April 1, 2024
 */
public enum Colors {
    EMPTY(Color.WHITE), WALL(Color.GRAY), PREDACON(Color.LIGHTPINK), MAXIMAL(Color.LIGHTBLUE);
    private final Paint color;


    Colors(Paint color) {
        this.color = color;
    }

    public Paint getColor(){
        return this.color;
    }
}
