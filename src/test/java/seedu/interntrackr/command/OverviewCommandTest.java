package seedu.interntrackr.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OverviewCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void execute_validList_printsTotalCount() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "Software Engineer"));
        applications.addApplication(new Application("Meta", "Data Scientist"));

        Ui ui = new Ui();
        OverviewCommand command = new OverviewCommand();
        command.execute(applications, ui, null);

        String output = outContent.toString();

        assertTrue(output.contains("tracking 2 applications"));
        assertTrue(output.contains("Keep up the momentum!"));
    }
}
