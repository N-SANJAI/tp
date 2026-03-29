package seedu.interntrackr.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindCommandTest {
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
    public void execute_keywordInCompany_found() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "SWE"));

        new FindCommand("Google").execute(applications, new Ui(), null);

        assertTrue(outContent.toString().contains("1. Company: Google"));
    }

    @Test
    public void execute_keywordInRole_found() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Meta", "Data Scientist"));

        new FindCommand("Scientist").execute(applications, new Ui(), null);

        assertTrue(outContent.toString().contains("Meta"));
    }

    @Test
    public void execute_caseInsensitiveSearch_found() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Amazon", "Backend"));

        new FindCommand("amazon").execute(applications, new Ui(), null);

        assertTrue(outContent.toString().contains("Amazon"));
    }

    @Test
    public void execute_noMatches_showsErrorMessage() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Apple", "iOS"));

        new FindCommand("Netflix").execute(applications, new Ui(), null);

        assertTrue(outContent.toString().contains("No matching applications found."));
        assertFalse(outContent.toString().contains("Apple"));
    }
}
