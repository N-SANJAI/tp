package seedu.interntrackr.command;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnarchiveCommandTest {

    @Test
    public void execute_validIndex_applicationIsUnarchived() throws InternTrackrException {
        ApplicationList applications = new ApplicationList();
        Application app = new Application("Google", "SWE");
        app.setArchived(true);
        applications.addApplication(app);
        Storage storage = new Storage("data/test_unarchive.txt");

        new UnarchiveCommand(1).execute(applications, new Ui(), storage);

        assertFalse(applications.getApplication(1).isArchived());
    }

    @Test
    public void execute_unarchiveRestorestoActiveList() throws InternTrackrException {
        ApplicationList applications = new ApplicationList();
        Application archived = new Application("Google", "SWE");
        archived.setArchived(true);
        applications.addApplication(archived);
        applications.addApplication(new Application("Meta", "Backend"));
        Storage storage = new Storage("data/test_unarchive.txt");

        new UnarchiveCommand(1).execute(applications, new Ui(), storage);

        assertFalse(applications.getApplication(1).isArchived());
    }

    @Test
    public void execute_indexOutOfRange_throwsInternTrackrException() {
        ApplicationList applications = new ApplicationList();
        Application app = new Application("Google", "SWE");
        app.setArchived(true);
        applications.addApplication(app);
        Storage storage = new Storage("data/test_unarchive.txt");

        assertThrows(InternTrackrException.class,
                () -> new UnarchiveCommand(5).execute(applications, new Ui(), storage));
    }

    @Test
    public void constructor_negativeIndex_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new UnarchiveCommand(-1));
    }
}
