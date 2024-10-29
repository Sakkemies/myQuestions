package org.lizkozz.file;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.lizkozz.memory.Memory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHandler
{
    public File openFile(Window owner, FileChooser.ExtensionFilter extension)
    {
        List<FileChooser.ExtensionFilter> extensionFilters = new ArrayList<>();
        extensionFilters.add(extension);

        return openFile(owner, extensionFilters);
    }
    public File openFile(Window owner, List<FileChooser.ExtensionFilter> extension)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Avaa Tiedosto");

        for(FileChooser.ExtensionFilter extensionFilter: extension)
            fileChooser.getExtensionFilters().add(extensionFilter);

        return fileChooser.showOpenDialog(owner);
    }
}
