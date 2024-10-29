package org.lizkozz.resources;

import javafx.stage.FileChooser;

public class Extensions
{
    public static FileChooser.ExtensionFilter ALL_FILES =  new FileChooser.ExtensionFilter("All Files", "*.*");
    public static FileChooser.ExtensionFilter TXT_FILES = new FileChooser.ExtensionFilter("Text Files", "*.txt");
    public static FileChooser.ExtensionFilter IMAGE_FILES = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
}
