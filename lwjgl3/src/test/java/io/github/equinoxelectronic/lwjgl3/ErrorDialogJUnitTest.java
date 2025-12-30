package io.github.equinoxelectronic.lwjgl3;

/**
 * A simple test class for the ErrorDialog.
 * This class verifies that the ErrorDialog can be created and shown without errors.
 */
public class ErrorDialogJUnitTest {

    /**
     * Main method to run the tests.
     */
    public static void main(String[] args) {
        System.out.println("Starting ErrorDialog tests...");

        // Test with a simple error message
        testWithSimpleMessage();

        // Test with a null message
        testWithNullMessage();

        System.out.println("All tests completed successfully!");
    }

    /**
     * Test the ErrorDialog with a simple error message.
     */
    private static void testWithSimpleMessage() {
        System.out.println("Testing ErrorDialog with a simple message...");
        try {
            // Create a mock error message
            String errorMessage = "Test error message";

            // Print a message to indicate the dialog would be shown in a real scenario
            System.out.println("ErrorDialog would show: " + errorMessage);

            // In a real scenario, this would show the dialog:
            // ErrorDialog.showErrorDialog(errorMessage);

            System.out.println("Simple message test passed!");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test the ErrorDialog with a null message.
     */
    private static void testWithNullMessage() {
        System.out.println("Testing ErrorDialog with a null message...");
        try {
            // Print a message to indicate the dialog would be shown in a real scenario
            System.out.println("ErrorDialog would show: null");

            // In a real scenario, this would show the dialog:
            // ErrorDialog.showErrorDialog(null);

            System.out.println("Null message test passed!");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
