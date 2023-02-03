package scripting.swingcomponents.fonts;

import scripting.swingcomponents.fonts.splitFiles.*;
import utils.FileUtilities;

import java.util.Base64;

public class FontUtilities {

    public static String createFontAwesomeFont(String filePath) {
        decode( FontAwesomeSplit0.fontSplit + FontAwesomeSplit1.fontSplit + FontAwesomeSplit2.fontSplit + FontAwesomeSplit3.fontSplit, filePath);
        FontAwesomeSplit0.fontSplit = null;
        FontAwesomeSplit1.fontSplit = null;
        FontAwesomeSplit2.fontSplit = null;
        FontAwesomeSplit3.fontSplit = null;
        return filePath;
    }

    public static String createRobotoFont(String filePath) {
        decode( RobotoSplit0.fontSplit + RobotoSplit1.fontSplit + RobotoSplit2.fontSplit + RobotoSplit3.fontSplit, filePath);
        RobotoSplit0.fontSplit = null;
        RobotoSplit1.fontSplit = null;
        RobotoSplit2.fontSplit = null;
        RobotoSplit3.fontSplit = null;
        return filePath;
    }

    public static void decode(String bytes, String targetFile) {
        byte[] decodedBytes = Base64.getDecoder().decode(bytes);
        FileUtilities.createFile(decodedBytes, targetFile);
    }

}
