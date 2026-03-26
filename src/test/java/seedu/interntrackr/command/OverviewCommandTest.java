package seedu.interntrackr.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.ui.Ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OverviewCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream("".getBytes()));
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
        new OverviewCommand().execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("tracking 2 applications"),
                "Should show tracking count of 2");
        assertTrue(output.contains("Keep up the momentum!"),
                "Should show encouragement message");
    }

    @Test
    public void execute_singleApplication_showsCountOfOne() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Grab", "Data Analyst"));

        Ui ui = new Ui();
        new OverviewCommand().execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("tracking 1 applications"),
                "Should show tracking count of 1");
    }

    @Test
    public void execute_emptyList_showsZeroCount() {
        ApplicationList applications = new ApplicationList();
        Ui ui = new Ui();
        new OverviewCommand().execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("tracking 0 applications"),
                "Should show tracking count of 0 when list is empty");
        assertTrue(output.contains("Keep up the momentum!"),
                "Should still show encouragement even for empty list");
    }

    @Test
    public void execute_emptyList_showsOverviewHeader() {
        ApplicationList applications = new ApplicationList();
        Ui ui = new Ui();
        new OverviewCommand().execute(applications, ui, null);

        assertTrue(outContent.toString().contains("Overview:"),
                "Should always print 'Overview:' header");
    }

    @Test
    public void execute_nullApplications_throwsAssertionError() {
        Ui ui = new Ui();
        assertThrows(AssertionError.class,
                () -> new OverviewCommand().execute(null, ui, null),
                "Should throw AssertionError when ApplicationList is null");
    }

    @Test
    public void execute_nullUi_throwsAssertionError() {
        ApplicationList applications = new ApplicationList();
        assertThrows(AssertionError.class,
                () -> new OverviewCommand().execute(applications, null, null),
                "Should throw AssertionError when Ui is null");
    }

    @Test
    public void execute_nullStorage_doesNotThrow() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("ByteDance", "SWE Intern"));
        Ui ui = new Ui();
        // OverviewCommand does not use storage — null should be safe
        new OverviewCommand().execute(applications, ui, null);
        assertTrue(outContent.toString().contains("tracking 1 applications"));
    }
}
