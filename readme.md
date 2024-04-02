# CPSC233A3 Robot Wars World Map Editor

---

- [Overview](#project-overview)
- [Running the Program](#running-the-program)
- [Creating a World Map](#creating-a-world-map)
- [Editing a World Map](#editing-a-world-map)
- [Saving a World Map](#saving-a-world-map)

---

## Project Overview

This application is a world map editor for a Robot War simulation.  The world 
map consists of a grid with m rows and n columns where each grid square can 
be in exactly one of the following states:

![Map States](docs/map-legend.png)

By default, the map is padded with a perimeter of walls to represent the world
boundaries.

> **Notes:** This perimeter wall is a view-only feature.  Therefore, it cannot
> be edited, should not be counted in coordinate calculations, and will not be
> be saved in the world map data. (See [Saving a World Map](#saving-a-world-map)

For our coordinate system we will use the convention `(row,col)` where `row` is
the y-coordinate and `col` is the x-coordinate.
- Rows are numbered from top to bottom in increasing order, starting from `0`.
- Columns are numbered from left to right in increading order, starting from `0`.
- Perimeter walls are not counted in coordinates.

For instance, the example below represents a `3 x 3` world with a PredaCon in 
coordinate `(0,0)` and a Maximal in coordinate `(2,1)`.

![First Map Example](docs/ex-one-map.png)

To obtain more information about the entity in a world map square, one may hover
over the desired square.  

## Running the Program

In order to run this program you need to have the JavaFX SDK 21.0+ installed in your
machine ([download link](https://gluonhq.com/products/javafx/)) and Java SDK 
21.0+ ([download link](https://jdk.java.net/21/)).  Please keep in mind
the location where JavaFX is installed as it will be needed in the following steps:

1. Download project files.
2. Open your command line and navigate to the `CPSC233A3\target\classes` directory.
3. Run the following command, replacing `"C:\ProgramFiles\Java\javafx-sdk-21.0.1.lib"` with the location of your local JavaFX install:
```bash
java --module-path "C:\ProgramFiles\Java\javafx-sdk-21.0.1.lib" --add-modules javafx.controls, javafx.fxml rw.app.Main
```
4. Alternatively, if you are running the program using a `.jar` file, you can run the following command:
```bash
java --module-path "C:\ProgramFiles\Java\javafx-sdk-21.0.1.lib" --add-modules javafx.controls, javafx.fxml -jar CPSC233A3.jar
```

## Creating a World Map
## Editing a World Map


## Saving a World Map