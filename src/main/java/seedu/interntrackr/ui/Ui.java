package seedu.interntrackr.ui;

import java.util.Scanner;

/**
 * Handles all user interactions, printing to the console and reading input.
 */
public class Ui {
    private Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Welcome to InternTrackr!");
    }

    public String readCommand() {
        return in.nextLine();
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showLoadingError() {
        System.out.println("Error loading file. Starting with an empty tracker.");
    }
}