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
}
