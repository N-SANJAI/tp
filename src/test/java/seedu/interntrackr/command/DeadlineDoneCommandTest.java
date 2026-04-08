//@@author eugenia-cnl-lee
package seedu.interntrackr.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.model.Deadline;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

/**
 * Tests for {@link DeadlineDoneCommand}.
 */
public class DeadlineDoneCommandTest {

    @Test
    public void constructor_applicationIndexLessThanOne_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new DeadlineDoneCommand(0, 1));
        assertEquals("Application index must be a positive integer.", exception.getMessage());
    }

    @Test
    public void constructor_deadlineIndexLessThanOne_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new DeadlineDoneCommand(1, 0));
        assertEquals("Deadline index must be a positive integer.", exception.getMessage());
    }

    @Test
    public void execute_invalidApplicationIndex_throwsInternTrackrException() {
        ApplicationList applications = new ApplicationList();
        Application app = new Application("Google", "Software Engineer");
        app.getDeadlines().addDeadline(new Deadline("Online Assessment", LocalDate.of(2026, 12, 12)));
        applications.addApplication(app);

        Ui ui = new Ui();
        Storage storage = new Storage(System.getProperty("java.io.tmpdir") + "/deadline_done_invalid_app.txt");
        DeadlineDoneCommand command = new DeadlineDoneCommand(2, 1);

        InternTrackrException exception = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));

        assertEquals("Invalid application index.", exception.getMessage());
        assertFalse(applications.getApplication(1).getDeadlines().getDeadline(1).getIsDone());
    }

    @Test
    public void execute_invalidDeadlineIndex_throwsInternTrackrException() {
        ApplicationList applications = new ApplicationList();
        Application app = new Application("Google", "Software Engineer");
        app.getDeadlines().addDeadline(new Deadline("Online Assessment", LocalDate.of(2026, 12, 12)));
        applications.addApplication(app);

        Ui ui = new Ui();
        Storage storage = new Storage(System.getProperty("java.io.tmpdir") + "/deadline_done_invalid_deadline.txt");
        DeadlineDoneCommand command = new DeadlineDoneCommand(1, 2);

        InternTrackrException exception = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));

        assertEquals("Invalid deadline index.", exception.getMessage());
        assertFalse(applications.getApplication(1).getDeadlines().getDeadline(1).getIsDone());
    }

    @Test
    public void execute_alreadyDoneDeadline_throwsInternTrackrException() {
        ApplicationList applications = new ApplicationList();
        Application app = new Application("Google", "Software Engineer");
        app.getDeadlines().addDeadline(new Deadline("Online Assessment", LocalDate.of(2026, 12, 12), true));
        app.getDeadlines().addDeadline(new Deadline("Technical Interview", LocalDate.of(2026, 12, 18)));
        applications.addApplication(app);

        Ui ui = new Ui();
        Storage storage = new Storage(System.getProperty("java.io.tmpdir") + "/deadline_done_already_done.txt");
        DeadlineDoneCommand command = new DeadlineDoneCommand(1, 1);

        InternTrackrException exception = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));

        assertEquals("This deadline has already been marked as done.", exception.getMessage());
        assertTrue(applications.getApplication(1).getDeadlines().getDeadline(1).getIsDone());
        assertFalse(applications.getApplication(1).getDeadlines().getDeadline(2).getIsDone());
    }

    @Test
    public void execute_validIndices_marksSpecifiedDeadlineAsDone() {
        ApplicationList applications = new ApplicationList();
        Application app = new Application("Google", "Software Engineer");
        app.getDeadlines().addDeadline(new Deadline("Online Assessment", LocalDate.of(2026, 12, 12)));
        app.getDeadlines().addDeadline(new Deadline("Technical Interview", LocalDate.of(2026, 12, 18)));
        applications.addApplication(app);

        Ui ui = new Ui();
        Storage storage = new Storage(System.getProperty("java.io.tmpdir") + "/deadline_done_success.txt");
        DeadlineDoneCommand command = new DeadlineDoneCommand(1, 1);

        command.execute(applications, ui, storage);

        assertTrue(applications.getApplication(1).getDeadlines().getDeadline(1).getIsDone());
        assertFalse(applications.getApplication(1).getDeadlines().getDeadline(2).getIsDone());
    }

    @Test
    public void execute_validIndices_savesUpdatedApplicationsToStorage() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + "/deadline_done_storage_test.txt";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }

        ApplicationList applications = new ApplicationList();
        Application app = new Application("Google", "Software Engineer");
        app.getDeadlines().addDeadline(new Deadline("Online Assessment", LocalDate.of(2026, 12, 12)));
        app.getDeadlines().addDeadline(new Deadline("Technical Interview", LocalDate.of(2026, 12, 18)));
        applications.addApplication(app);

        Ui ui = new Ui();
        Storage storage = new Storage(path);
        DeadlineDoneCommand command = new DeadlineDoneCommand(1, 1);

        command.execute(applications, ui, storage);

        List<String> lines = Files.readAllLines(file.toPath());

        assertEquals(1, lines.size());
        assertEquals(
                "Google | Software Engineer | Applied | - | - | - | - | "
                        + "Online Assessment | 2026-12-12 | true | "
                        + "Technical Interview | 2026-12-18 | false",
                lines.get(0));
    }
}