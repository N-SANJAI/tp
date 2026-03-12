package seedu.interntrackr.ui;

import java.util.Scanner;

/**
 * Handles terminal reading and writing formatting.
 */
public class Ui {
    private Scanner in;
    private static final String LINE = "____________________________________________________________";

    public Ui() {
        this.in = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println("Welcome to InternTrackr! Ready to hunt for some internships?");
        System.out.println(LINE);
    }

    public String readCommand() {
        return in.nextLine();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showLoadingError() {
        System.out.println("No existing data found. Starting with a fresh tracker.");
    }
}