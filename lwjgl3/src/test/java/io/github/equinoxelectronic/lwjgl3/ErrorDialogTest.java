package io.github.equinoxelectronic.lwjgl3;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A simple test class to verify that the error dialog appears when an exception occurs.
 * This class provides multiple test methods to verify different error scenarios.
 */
public class ErrorDialogTest {
    public static void main(String[] args) {
        // Check if any specific test is requested via command line arguments
        if (args.length > 0) {
            switch (args[0]) {
                case "exception":
                    testWithException();
                    break;
                case "stacktrace":
                    testWithStackTrace();
                    break;
                default:
                    testSimpleMessage();
            }
        } else {
            // Default test: simple error message
            testSimpleMessage();
        }
    }

    /**
     * Tests the error dialog with a simple error message.
     */
    private static void testSimpleMessage() {
        System.out.println("Testing error dialog with a simple message...");
        ErrorDialog.showErrorDialog("This is a test error message", new Exception("Test exception"));
    }

    /**
     * Tests the error dialog with an actual exception.
     */
    private static void testWithException() {
        System.out.println("Testing error dialog with an exception...");
        try {
            // Intentionally throw an exception
            throw new RuntimeException("Test exception message");
        } catch (Exception e) {
            ErrorDialog.showErrorDialog(e.getMessage(), e);
        }
    }

    /**
     * Tests the error dialog with a full stack trace.
     */
    private static void testWithStackTrace() {
        System.out.println("Testing error dialog with a stack trace...");
        try {
            // Create a more complex exception scenario
            int[] array = new int[5];
            // This will cause an ArrayIndexOutOfBoundsException
            System.out.println(array[10]);
        } catch (Exception e) {
            // Convert stack trace to string
            StringBuilder sb = new StringBuilder();
            sb.append(e.toString()).append("\n");
            for (StackTraceElement element : e.getStackTrace()) {
                sb.append("\tat ").append(element.toString()).append("\n");
            }
            ErrorDialog.showErrorDialog(sb.toString(), e);
        }
    }

    private static String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
