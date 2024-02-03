package duke;

/**
 * Provides static methods for handling various types of errors throughout the
 * Duke application.
 * This includes file not found errors, I/O errors, and number format errors.
 */
public class ErrorHandler {

    /**
     * Handles cases where a file cannot be found.
     * Prints an error message specifying the missing file path.
     *
     * @param filePath The path to the file that was not found.
     */
    public static void handleFileNotFoundException(String filePath) {
        System.out.println("File not found: " + filePath);
    }

    /**
     * Handles generic I/O errors that occur during file operations.
     * Prints a general error message indicating an issue with file access.
     */
    public static void handleIOException() {
        System.out.println("An error occurred while accessing the file.");
    }

    /**
     * Handles errors resulting from invalid number formats in user input.
     * Prints an error message indicating the invalid input that caused the error.
     *
     * @param input The user input that was expected to be in a valid number format
     *              but was not.
     */
    public static void handleNumberFormatException(String input) {
        System.out.println("Invalid number format: " + input);
    }
}
