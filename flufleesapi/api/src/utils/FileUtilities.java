package utils;

import org.apache.commons.codec.binary.Base64;
import org.tribot.api.General;
import org.tribot.util.Util;

import java.io.*;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Fluffee on 17/07/17.
 */
public class FileUtilities {

    /**
     * Checks if file exists. File name must have the appropriate separator ahead of the name.
     * @param fileName - File name to check existance of, as string
     * @return - True if it exists, false otherwise.
     */
    public static boolean checkExistance(String fileName) {
        File testFile = Paths.get(Util.getWorkingDirectory() + fileName).toFile();
        return testFile.exists();
    }

    public static String[] readAllLines(File textFile) {
        String[] contents = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            Stream<String> fileContents = reader.lines();
            contents = fileContents.toArray(String[]:: new);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return contents;
    }

    /**
     * Gets the number of lines in a text file.
     *
     * Source: https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
     *
     * Not easier to follow, but runs extremely fast.
     *
     * @param filename String containing the name of the file to count
     * @return int containing the number of lines in the file
     * @throws IOException
     */
    public static int getLineCount(String filename) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(filename));
            byte[] c = new byte[1024];

            int readChars = is.read(c);
            if (readChars == -1) {
                // bail out if nothing to read
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i = 0; i < 1024;) {
                    if (c [i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                for (int i=0; i<readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            return count == 0 ? 1 : count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static File stringToFile(String fileName) {
        return Paths.get(Util.getWorkingDirectory() + fileName).toFile();
    }

    /**
     *
     * @param filePath
     * @param appendData
     * @return
     */
    public static boolean appendToFile (String filePath, String appendData) {
        BufferedWriter bufferedWriter = null;
        boolean success = true;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(Util.getWorkingDirectory() + filePath, true));
            bufferedWriter.write(appendData);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        } finally {
            if (bufferedWriter != null) try {
                bufferedWriter.close();
            } catch (IOException ioe2) {
                // just ignore it
            }
            return success;
        }
    }

    /**
     * Creates file given file contents and file name. If file name contains separator characters it creates the necessary subdirectories to satisfy the path.
     * @param fileContents String to write to file
     * @param fileName String containing file name, or path relative to TRiBot Directory.
     * @return File path as String if successful, null otherwise.
     */
    public static String createFile(String fileContents, String fileName) {
        String directoryPath = createSubdirectories(fileName);
        if (fileName.contains(File.separator))
            fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter( new FileWriter(directoryPath + File.separator + fileName));
            writer.write(fileContents);
        } catch ( IOException e) {
            System.out.println("Error: Unable to write to directory.");
            return null;
        } finally {
            try {
                if ( writer != null)
                    writer.close();
            } catch ( IOException e) {
                //Ignoring this due to location.
            }
        }
        return directoryPath + File.separator + fileName;
    }

    /**
     * Creates file given file contents and file name. If file name contains separator characters it creates the necessary subdirectories to satisfy the path.
     * @param fileContents byte[] to write to file
     * @param fileName String containing file name, or path relative to TRiBot Directory.
     * @return File path as String if successful, null otherwise.
     */
    public static String createFile(byte[] fileContents, String fileName) {
        String directoryPath = createSubdirectories(fileName);
        if (fileName.contains(File.separator))
            fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        BufferedOutputStream writer = null;
        try {
            writer = new BufferedOutputStream(new FileOutputStream(directoryPath + File.separator + fileName));
            writer.write(fileContents);
        } catch ( IOException e) {
            System.out.println("Error: Unable to write to directory.");
            return null;
        } finally {
            try {
                if ( writer != null)
                    writer.close();
            } catch ( IOException e) {
                //Ignoring this due to location.
            }
        }
        return directoryPath + File.separator + fileName;
    }

    public static String createSubdirectories(String fileName) {
        String subDirectory = "";
        String directoryPath = Util.getWorkingDirectory().getAbsolutePath();
        while (fileName.contains(File.separator)) {
            subDirectory = fileName.substring(0, fileName.indexOf(File.separator));
            fileName = fileName.substring(subDirectory.length() + 1);
            createDirectory(directoryPath, subDirectory);
            directoryPath = directoryPath + File.separator + subDirectory;
            General.sleep(300);
        }
        return directoryPath;
    }

    /**
     * Create's a directory given the paths
     *
     * @param parentDirectory Parent directory to make the new folder in.
     * @param childDirectory  Child directory to create
     */
    public static void createDirectory(String parentDirectory, String childDirectory) {
        File path = new File(parentDirectory, childDirectory);
        path.mkdir();
    }

    public static byte[] encodeFileToBase64Binary(String fileName)
            throws IOException {

        File file = new File(fileName);
        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.encodeBase64(bytes);
        return encoded;
    }

    public static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }
}
