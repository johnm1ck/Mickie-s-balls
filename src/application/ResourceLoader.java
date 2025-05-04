/**
 * Utility class for loading resources properly in a JAR file
 */
package application;

import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import java.net.URL;

public class ResourceLoader {
    
    /**
     * Loads an image resource that works both in IDE and when packaged in a JAR
     * @param path Path to the resource, e.g., "resource/orangecat.png"
     * @return Image object or null if loading failed
     */
    public static Image loadImage(String path) {
        try {
            InputStream inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream(path);
            if (inputStream != null) {
                return new Image(inputStream);
            } else {
                System.err.println("Resource not found: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + path + " - " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Loads a media resource (audio/video) that works both in IDE and when packaged in a JAR
     * @param path Path to the resource, e.g., "resource/boom.mp3"
     * @return Media object or null if loading failed
     */
    public static Media loadMedia(String path) {
        try {
            URL resourceUrl = ResourceLoader.class.getClassLoader().getResource(path);
            if (resourceUrl != null) {
                return new Media(resourceUrl.toString());
            } else {
                System.err.println("Media resource not found: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading media: " + path + " - " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}