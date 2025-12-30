package io.github.equinoxelectronic.lwjgl3;

import io.github.equinoxelectronic.Chancellor;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A simple error dialog that displays an error message using Java Swing.
 * This avoids conflicts with LibGDX/LWJGL OpenGL initialization.
 */
public class ErrorDialog {
    /**
     * Shows the error dialog with the specified error message.
     *
     * @param errorMessage The error message to display
     */
    public static void showErrorDialog(String errorMessage, Throwable throwable) {
        System.err.println("Displaying error dialog.");
        String stackTrace = getStackTraceAsString(throwable);
        // Handle null error messages
        final String displayMessage = errorMessage != null ? errorMessage : "Unknown error occurred";

        // Use SwingUtilities.invokeLater to ensure the dialog is created on the EDT
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the look and feel to the system default
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Ignore look and feel errors, the default will be used
            }

            // Create and configure the dialog
            JDialog dialog = new JDialog((Frame) null, "Error", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(500, 200);
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.setAlwaysOnTop(true);

            // Create the content panel
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Add the dialog header
            JTextArea titleText = new JTextArea("Chancellor v" + Chancellor.VERSION+" has crashed!");
            titleText.setEditable(false);
            titleText.setWrapStyleWord(true);
            titleText.setLineWrap(true);
            titleText.setOpaque(false);
            titleText.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
            panel.add(titleText, BorderLayout.NORTH);

            // Add the error message
            JTextArea errorText = new JTextArea("Error: " + displayMessage);
            errorText.setEditable(false);
            errorText.setWrapStyleWord(true);
            errorText.setLineWrap(true);
            errorText.setOpaque(false);
            errorText.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
            panel.add(errorText, BorderLayout.CENTER);

            // Add the OK button
            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> {
                dialog.dispose();
                System.exit(1); // Exit the application with error code
            });

            // Add the copy button
            JButton copyButton = new JButton("Copy stacktrace");
            copyButton.addActionListener(e -> {
                StringSelection selection = new StringSelection(stackTrace);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
                System.out.println("Stacktrace copied to clipboard.");
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(okButton);
            buttonPanel.add(copyButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);
            System.out.println("Error dialog created.");

            // Set the content and show the dialog
            dialog.setContentPane(panel);
            dialog.setVisible(true);
        });
    }

    private static String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
