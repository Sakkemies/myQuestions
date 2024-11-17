package org.lizkozz.file;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.lizkozz.memory.Memory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
    public void loadSettings()
    {
        Properties settings = new Properties();

        try (FileInputStream fis = new FileInputStream(Memory.FILE_SETTINGS)) {
            settings.load(fis);

            Memory.setShuffle(Boolean.parseBoolean(settings.getProperty("shuffle", "false")));
            Memory.setAmountOfQuestions(Integer.parseInt(settings.getProperty("maxQuestions", "10")));
        } catch (Exception ignored) {}
    }
    public void saveSettings() {
        Properties settings = new Properties();

        settings.setProperty("shuffle", String.valueOf(Memory.isShuffle()));
        settings.setProperty("maxQuestions", String.valueOf(Memory.getAmountOfQuestions()));

        try (FileOutputStream fos = new FileOutputStream(Memory.FILE_SETTINGS)) {
            settings.store(fos, "Asetukset tallennettu");
        } catch (Exception ignored) {}
    }
}
