package seedu.interntrackr.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void showLine_printsCorrectDivider() {
        new Ui().showLine();
        assertTrue(outContent.toString().contains("____________________________________________________________"));
    }

    @Test
    public void showWelcome_containsWelcomeMessage() {
        new Ui().showWelcome();
        assertTrue(outContent.toString().contains("Welcome to InternTrackr!"));
    }

    @Test
    public void showMessage_validMessage_printsMessage() {
        new Ui().showMessage("Hello Test");
        assertTrue(outContent.toString().contains("Hello Test"));
    }

    @Test
    public void showMessage_anotherMessage_printsCorrectly() {
        new Ui().showMessage("You have 3 applications.");
        assertTrue(outContent.toString().contains("You have 3 applications."));
    }

    @Test
    public void showError_validMessage_printsWithErrorPrefix() {
        new Ui().showError("Something went wrong");
        assertTrue(outContent.toString().contains("Error: Something went wrong"));
    }

    @Test
    public void showError_unknownCommandMessage_printsErrorPrefix() {
        new Ui().showError("I don't know that command");
        String output = outContent.toString();
        assertTrue(output.contains("Error:"));
        assertTrue(output.contains("I don't know that command"));
    }

    @Test
    public void showLoadingError_printsNoDataMessage() {
        new Ui().showLoadingError();
        assertTrue(outContent.toString().contains("No existing data found"));
    }

    @Test
    public void readCommand_validInput_returnsTrimmedInput() {
        System.setIn(new ByteArrayInputStream("  list  \n".getBytes()));
        Ui ui = new Ui();
        String result = ui.readCommand();
        assertEquals("list", result);
    }

    @Test
    public void readCommand_noWhitespace_returnsInputAsIs() {
        System.setIn(new ByteArrayInputStream("overview\n".getBytes()));
        Ui ui = new Ui();
        String result = ui.readCommand();
        assertEquals("overview", result);
    }

    @Test
    public void readCommand_emptyInput_returnsEmptyString() {
        System.setIn(new ByteArrayInputStream("\n".getBytes()));
        Ui ui = new Ui();
        String result = ui.readCommand();
        assertEquals("", result);
    }

    @Test
    public void readCommand_commandWithArgs_returnsFullTrimmedInput() {
        System.setIn(new ByteArrayInputStream("  add c/Google r/SWE  \n".getBytes()));
        Ui ui = new Ui();
        String result = ui.readCommand();
        assertEquals("add c/Google r/SWE", result);
    }
}
