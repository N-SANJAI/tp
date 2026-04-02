package seedu.interntrackr.command;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ArchiveCommandTest {

    @Test
    public void execute_validIndex_applicationIsArchived() throws InternTrackrException {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "SWE"));
        Storage storage = new Storage("data/test_archive.txt");

        new ArchiveCommand(1).execute(applications, new Ui(), storage);

        assertTrue(applications.getApplication(1).isArchived());
    }

    @Test
    public void execute_archivedApplicationHiddenFromList() throws InternTrackrException {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "SWE"));
        applications.addApplication(new Application("Meta", "Backend"));
        Storage storage = new Storage("data/test_archive.txt");

        new ArchiveCommand(1).execute(applications, new Ui(), storage);

        assertFalse(applications.getApplication(2).isArchived());
        assertTrue(applications.getApplication(1).isArchived());
    }

    @Test
    public void execute_indexOutOfRange_throwsInternTrackrException() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "SWE"));
        Storage storage = new Storage("data/test_archive.txt");

        assertThrows(InternTrackrException.class,
                () -> new ArchiveCommand(5).execute(applications, new Ui(), storage));
    }

    @Test
    public void execute_alreadyArchived_throwsInternTrackrException() throws InternTrackrException {
        ApplicationList applications = new ApplicationList();
        Application app = new Application("Google", "SWE");
        app.setArchived(true);
        applications.addApplication(app);
        Storage storage = new Storage("data/test_archive.txt");

        assertThrows(InternTrackrException.class,
                () -> new ArchiveCommand(1).execute(applications, new Ui(), storage));
    }
}
