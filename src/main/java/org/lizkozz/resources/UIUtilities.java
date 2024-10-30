package org.lizkozz.resources;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class UIUtilities
{
    public static MenuItem getMenuItem(String text)
    {
        MenuItem item = new MenuItem(text);
        //item.setStyle("-fx-text-fill: black;");

        return item;
    }
    public static CheckMenuItem getCheckMenuItem(String text)
    {
        CheckMenuItem item = new CheckMenuItem(text);
        //item.setStyle("-fx-text-fill: black;");

        return item;
    }
    public static Menu getMenu(String text)
    {
        Menu item = new Menu(text);
        //item.setStyle("-fx-text-fill: black;");

        return item;
    }
}
