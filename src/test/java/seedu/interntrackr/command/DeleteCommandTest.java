package seedu.interntrackr.command;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteCommandTest {

    @Test
    public void execute_validIndex_removedFromList() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "SWE"));
        Storage storage = new Storage("data/test_delete.txt");

        new DeleteCommand(1).execute(applications, new Ui(), storage);

        assertEquals(0, applications.getSize());
    }

    @Test
    public void execute_deleteMiddleEntry_correctEntryRemoved() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "SWE"));
        applications.addApplication(new Application("Meta", "Backend"));
        applications.addApplication(new Application("Apple", "iOS"));
        Storage storage = new Storage("data/test_delete.txt");

        new DeleteCommand(2).execute(applications, new Ui(), storage);

        assertEquals(2, applications.getSize());
        assertEquals("Apple", applications.getApplication(2).getCompany());
    }

    @Test
    public void execute_indexOutOfRange_throwsInternTrackrException() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "SWE"));
        Storage storage = new Storage("data/test_delete.txt");

        assertThrows(InternTrackrException.class,
                () -> new DeleteCommand(5).execute(applications, new Ui(), storage));
    }

    @Test
    public void constructor_negativeIndex_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new DeleteCommand(-1));
    }
}
