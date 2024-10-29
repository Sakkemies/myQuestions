package org.lizkozz.resources;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import java.net.URL;
import java.util.Objects;

public class ResourceHandler
{
    public static URL getResource(Application app, String resourceName)
    {
        try {
            return app.getClass().getResource(resourceName);
        }
        catch (Exception e) {
            return null;
        }
    }
    public static String getResourceAsString(Application app, String resourceName)
    {
        try {
            return Objects.requireNonNull(getResource(app, resourceName)).toExternalForm();
        }
        catch (Exception e) {
            return null;
        }
    }
    public static <T> T loadFXMLResource(Application app, String resourceName)
    {
        try {
            return FXMLLoader.load(Objects.requireNonNull(getResource(app, resourceName)));
        }
        catch (Exception e) {
            return null;
        }
    }
}
