package seedu.interntrackr.command;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddCommandTest {

    @Test
    public void execute_validApplication_addedToList() {
        ApplicationList applications = new ApplicationList();
        Storage storage = new Storage("data/test_add.txt");

        new AddCommand("Google", "Software Engineer").execute(applications, new Ui(), storage);

        assertEquals(1, applications.getSize());
        assertEquals("Google", applications.getApplication(1).getCompany());
    }

    @Test
    public void execute_companyWithWhitespace_trimmedAndStored() {
        ApplicationList applications = new ApplicationList();
        Storage storage = new Storage("data/test_add.txt");

        new AddCommand("  Google  ", "SWE").execute(applications, new Ui(), storage);

        assertEquals("Google", applications.getApplication(1).getCompany());
    }

    @Test
    public void constructor_nullCompany_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AddCommand(null, "SWE"));
    }

    @Test
    public void constructor_blankRole_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AddCommand("Google", "   "));
    }

    @Test
    public void execute_duplicateApplication_notAdded() {
        ApplicationList applications = new ApplicationList();
        Storage storage = new Storage("data/test_add.txt");
        Ui ui = new Ui();

        // Add the first application
        new AddCommand("Google", "SWE").execute(applications, ui, storage);
        assertEquals(1, applications.getSize());

        // Attempt to add the exact same application again
        new AddCommand("Google", "SWE").execute(applications, ui, storage);

        // Size should still be 1
        assertEquals(1, applications.getSize());
    }

    @Test
    public void execute_duplicateApplicationCaseInsensitive_notAdded() {
        ApplicationList applications = new ApplicationList();
        Storage storage = new Storage("data/test_add.txt");
        Ui ui = new Ui();

        new AddCommand("Meta", "Data Scientist").execute(applications, ui, storage);

        // Different casing should still be detected as a duplicate
        new AddCommand("META", "data scientist").execute(applications, ui, storage);

        assertEquals(1, applications.getSize());
    }
}
