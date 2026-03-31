package seedu.interntrackr.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Returns a temporary file path for testing purposes.
     *
     * @return A string file path in the system temp directory.
     */
    private String getTempFilePath() {
        return System.getProperty("java.io.tmpdir") + "/test_contact_command.txt";
    }

    /**
     * Returns an ApplicationList containing two applications for testing.
     *
     * @return A populated ApplicationList.
     */
    private ApplicationList createApplicationList() {
        ArrayList<Application> applications = new ArrayList<>();
        applications.add(new Application("Shopee", "Backend Intern", "Applied"));
        applications.add(new Application("Google", "SWE Intern", "Interview"));
        return new ApplicationList(applications);
    }

    @Test
    void execute_validDetails_updatesApplicationShowsMessageAndSaves() throws Exception {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "jane@example.com");
        command.execute(applications, ui, storage);

        Application updated = applications.getApplication(1);
        assertEquals("Jane Tan", updated.getContactName());
        assertEquals("jane@example.com", updated.getContactEmail());

        String output = outContent.toString();
        assertTrue(output.contains(
                "Contact details updated for Shopee | Backend Intern: Jane Tan | jane@example.com"));

        ArrayList<Application> loaded = storage.load();
        assertEquals("Jane Tan", loaded.get(0).getContactName());
        assertEquals("jane@example.com", loaded.get(0).getContactEmail());
    }

    @Test
    void execute_updatesCorrectApplicationOnly() throws Exception {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(2, "Alex Lim", "alex@example.com");
        command.execute(applications, ui, storage);

        assertNull(applications.getApplication(1).getContactName());
        assertNull(applications.getApplication(1).getContactEmail());

        assertEquals("Alex Lim", applications.getApplication(2).getContactName());
        assertEquals("alex@example.com", applications.getApplication(2).getContactEmail());

        ArrayList<Application> loaded = storage.load();
        assertEquals("-", loaded.get(0).getContactName());
        assertEquals("-", loaded.get(0).getContactEmail());
        assertEquals("Alex Lim", loaded.get(1).getContactName());
        assertEquals("alex@example.com", loaded.get(1).getContactEmail());
    }

    @Test
    void execute_overwritesExistingContactDetails() throws Exception {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();

        ArrayList<Application> applicationsArray = new ArrayList<>();
        applicationsArray.add(new Application("Shopee", "Backend Intern", "Applied",
                "Old Name", "old@example.com"));
        ApplicationList applications = new ApplicationList(applicationsArray);

        ContactCommand command = new ContactCommand(1, "New Name", "new@example.com");
        command.execute(applications, ui, storage);

        assertEquals("New Name", applications.getApplication(1).getContactName());
        assertEquals("new@example.com", applications.getApplication(1).getContactEmail());

        ArrayList<Application> loaded = storage.load();
        assertEquals("New Name", loaded.get(0).getContactName());
        assertEquals("new@example.com", loaded.get(0).getContactEmail());
    }

    @Test
    void execute_trimsOuterWhitespaceFromConstructorInputs() throws Exception {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "  Jane Tan  ", "  jane@example.com  ");
        command.execute(applications, ui, storage);

        assertEquals("Jane Tan", applications.getApplication(1).getContactName());
        assertEquals("jane@example.com", applications.getApplication(1).getContactEmail());

        ArrayList<Application> loaded = storage.load();
        assertEquals("Jane Tan", loaded.get(0).getContactName());
        assertEquals("jane@example.com", loaded.get(0).getContactEmail());
    }

    @Test
    void execute_acceptsUppercaseEmail() throws Exception {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "Jane.Tan@Example.COM");
        command.execute(applications, ui, storage);

        assertEquals("Jane Tan", applications.getApplication(1).getContactName());
        assertEquals("Jane.Tan@Example.COM", applications.getApplication(1).getContactEmail());
    }

    @Test
    void execute_preservesExistingDeadlinesWhenUpdatingContact() throws Exception {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();

        ArrayList<Application> applicationsArray = new ArrayList<>();
        seedu.interntrackr.model.DeadlineList deadlines = new seedu.interntrackr.model.DeadlineList();
        deadlines.addDeadline(new seedu.interntrackr.model.Deadline("OA", LocalDate.of(2026, 4, 1), false));
        deadlines.addDeadline(new seedu.interntrackr.model.Deadline("Interview", LocalDate.of(2026, 4, 10), true));

        applicationsArray.add(new Application("Meta", "Data Intern", "Applied",
                null, null, deadlines));
        ApplicationList applications = new ApplicationList(applicationsArray);

        ContactCommand command = new ContactCommand(1, "Chris Tan", "chris@meta.com");
        command.execute(applications, ui, storage);

        assertEquals(2, applications.getApplication(1).getDeadlines().getSize());

        ArrayList<Application> loaded = storage.load();
        assertEquals("Chris Tan", loaded.get(0).getContactName());
        assertEquals("chris@meta.com", loaded.get(0).getContactEmail());
        assertEquals(2, loaded.get(0).getDeadlines().getSize());
    }

    @Test
    void execute_indexZero_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(0, "Jane Tan", "jane@example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid application index.", e.getMessage());

        assertNull(applications.getApplication(1).getContactName());
        assertNull(applications.getApplication(1).getContactEmail());
    }

    @Test
    void execute_negativeIndex_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(-1, "Jane Tan", "jane@example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid application index.", e.getMessage());
    }

    @Test
    void execute_indexGreaterThanListSize_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(3, "Jane Tan", "jane@example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid application index.", e.getMessage());

        assertNull(applications.getApplication(1).getContactName());
        assertNull(applications.getApplication(1).getContactEmail());
        assertNull(applications.getApplication(2).getContactName());
        assertNull(applications.getApplication(2).getContactEmail());
    }

    @Test
    void execute_emptyApplicationList_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = new ApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "jane@example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid application index.", e.getMessage());
    }

    @Test
    void execute_emptyContactName_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "", "jane@example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Contact name cannot be empty.", e.getMessage());

        assertNull(applications.getApplication(1).getContactName());
        assertNull(applications.getApplication(1).getContactEmail());
    }

    @Test
    void execute_whitespaceOnlyContactName_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "   ", "jane@example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Contact name cannot be empty.", e.getMessage());
    }

    @Test
    void execute_nullContactName_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, null, "jane@example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Contact name cannot be empty.", e.getMessage());
    }

    @Test
    void execute_emptyContactEmail_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Contact email cannot be empty.", e.getMessage());

        assertNull(applications.getApplication(1).getContactName());
        assertNull(applications.getApplication(1).getContactEmail());
    }

    @Test
    void execute_whitespaceOnlyContactEmail_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "   ");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Contact email cannot be empty.", e.getMessage());
    }

    @Test
    void execute_nullContactEmail_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", null);

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Contact email cannot be empty.", e.getMessage());
    }

    @Test
    void execute_emailContainingSpaces_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "jane @example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid contact email format.", e.getMessage());
    }

    @Test
    void execute_emailWithoutAtSymbol_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "janeexample.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid contact email format.", e.getMessage());
    }

    @Test
    void execute_emailWithAtSymbolAsFirstCharacter_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "@example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid contact email format.", e.getMessage());
    }

    @Test
    void execute_emailWithMultipleAtSymbols_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "jane@@example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid contact email format.", e.getMessage());
    }

    @Test
    void execute_emailEndingWithAtSymbol_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "jane@");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid contact email format.", e.getMessage());
    }

    @Test
    void execute_emailWithoutDotAfterAt_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "jane@examplecom");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid contact email format.", e.getMessage());
    }

    @Test
    void execute_emailWithDotImmediatelyAfterAt_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "jane@.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid contact email format.", e.getMessage());
    }

    @Test
    void execute_emailEndingWithDot_throwsInternTrackrException() {
        String path = getTempFilePath();
        Storage storage = new Storage(path);
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "jane@example.");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertEquals("Invalid contact email format.", e.getMessage());
    }

    @Test
    void execute_saveFailureStillUpdatesApplicationBeforeThrowing() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + "/contact_command_directory_as_file";
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }

        Storage storage = new Storage(path + "/child.txt");
        Ui ui = new Ui();
        ApplicationList applications = createApplicationList();

        ContactCommand command = new ContactCommand(1, "Jane Tan", "jane@example.com");

        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> command.execute(applications, ui, storage));
        assertTrue(e.getMessage().contains("Error saving file:"));

        assertEquals("Jane Tan", applications.getApplication(1).getContactName());
        assertEquals("jane@example.com", applications.getApplication(1).getContactEmail());
        assertTrue(outContent.toString().contains(
                "Contact details updated for Shopee | Backend Intern: Jane Tan | jane@example.com"));

        file.delete();
    }
}
