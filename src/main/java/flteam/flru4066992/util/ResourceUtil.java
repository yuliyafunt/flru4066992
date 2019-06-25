package flteam.flru4066992.util;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtil {

    private ResourceUtil() {
    }

    public static Image getImageFromResource(String name) {
        try (InputStream inputStream = ResourceUtil.class.getResourceAsStream(name)) {
            return new Image(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load image from resources!");
        }
    }

}
