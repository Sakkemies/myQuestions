package org.lizkozz.file;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.lizkozz.memory.Memory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHandler
{
    public void openFile(Window owner, FileChooser.ExtensionFilter extension)
    {
        List<FileChooser.ExtensionFilter> extensionFilters = new ArrayList<>();
        extensionFilters.add(extension);

        openFile(owner, extensionFilters);
    }
    public void openFile(Window owner, List<FileChooser.ExtensionFilter> extension)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Avaa Tiedosto");

        for(FileChooser.ExtensionFilter extensionFilter: extension)
            fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(owner);
        Memory.setQuestions(file);
    }
}
