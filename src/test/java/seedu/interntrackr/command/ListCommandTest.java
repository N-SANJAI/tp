package seedu.interntrackr.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Contains unit tests for {@link ListCommand}.
 */
public class ListCommandTest {

    private ListCommand listCommand;
    private StubApplicationList stubAppList;
    private StubUi stubUi;
    private Storage dummyStorage;

    /**
     * Initializes the necessary components and stubs before each test execution.
     */
    @BeforeEach
    public void setUp() {
        listCommand = new ListCommand();
        stubAppList = new StubApplicationList();
        stubUi = new StubUi();
        dummyStorage = null;
    }

    /**
     * A stub implementation of {@link Ui} to intercept and capture messages
     * without printing to the standard output during testing.
     */
    private static class StubUi extends Ui {
        public final List<String> printedMessages = new ArrayList<>();

        @Override
        public void showMessage(String message) {
            printedMessages.add(message);
        }
    }

    /**
     * A stub implementation of {@link ApplicationList} to inject and manage
     * isolated test data independent of the actual storage or model logic.
     */
    private static class StubApplicationList extends ApplicationList {
        public final List<Application> applications = new ArrayList<>();

        @Override
        public int getSize() {
            return applications.size();
        }

        @Override
        public Application getApplication(int index) {
            return applications.get(index - 1);
        }
    }

    /**
     * Verifies that executing the list command on an empty application list
     * outputs the appropriate empty list message.
     *
     * @throws InternTrackrException If an error occurs during execution.
     */
    @Test
    public void execute_emptyApplicationList_showsEmptyMessage() throws InternTrackrException {
        listCommand.execute(stubAppList, stubUi, dummyStorage);

        assertEquals(1, stubUi.printedMessages.size());
        assertEquals("No applications found. Start adding some!", stubUi.printedMessages.get(0));
    }

    /**
     * Verifies that executing the list command on a list containing a single application
     * outputs the header and the correctly formatted application details.
     *
     * @throws InternTrackrException If an error occurs during execution.
     */
    @Test
    public void execute_singleApplication_showsOneApplication() throws InternTrackrException {
        stubAppList.applications.add(new Application("Google", "SWE Intern", "Interview"));

        listCommand.execute(stubAppList, stubUi, dummyStorage);

        assertEquals(2, stubUi.printedMessages.size());
        assertEquals("Here are your internship applications:", stubUi.printedMessages.get(0));
        assertTrue(stubUi.printedMessages.get(1).startsWith("1. "));
        assertTrue(stubUi.printedMessages.get(1).contains("Google"));
    }

    /**
     * Verifies that executing the list command on a list containing multiple applications
     * outputs the header and all applications in the correct order.
     *
     * @throws InternTrackrException If an error occurs during execution.
     */
    @Test
    public void execute_multipleApplications_showsAllApplications() throws InternTrackrException {
        stubAppList.applications.add(new Application("Shopee", "Backend Intern", "Applied"));
        stubAppList.applications.add(new Application("TikTok", "Data Intern", "Rejected"));
        stubAppList.applications.add(new Application("Grab", "Frontend Intern", "Offered"));

        listCommand.execute(stubAppList, stubUi, dummyStorage);

        assertEquals(4, stubUi.printedMessages.size());
        assertEquals("Here are your internship applications:", stubUi.printedMessages.get(0));
        assertTrue(stubUi.printedMessages.get(1).startsWith("1. "));
        assertTrue(stubUi.printedMessages.get(1).contains("Shopee"));
        assertTrue(stubUi.printedMessages.get(2).startsWith("2. "));
        assertTrue(stubUi.printedMessages.get(2).contains("TikTok"));
        assertTrue(stubUi.printedMessages.get(3).startsWith("3. "));
        assertTrue(stubUi.printedMessages.get(3).contains("Grab"));
    }

    /**
     * Verifies that passing a null ApplicationList triggers an AssertionError.
     */
    @Test
    public void execute_nullApplicationList_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> listCommand.execute(null, stubUi, dummyStorage));
    }

    /**
     * Verifies that passing a null Ui triggers a NullPointerException when
     * the command attempts to display messages.
     */
    @Test
    public void execute_nullUi_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> listCommand.execute(stubAppList, null, dummyStorage));
    }

    /**
     * Verifies that a null Storage object does not affect the successful execution
     * of the list command, as the command does not require file I/O operations.
     *
     * @throws InternTrackrException If an error occurs during execution.
     */
    @Test
    public void execute_nullStorage_executesNormally() throws InternTrackrException {
        stubAppList.applications.add(new Application("Meta", "SWE", "Applied"));

        listCommand.execute(stubAppList, stubUi, null);

        assertEquals(2, stubUi.printedMessages.size());
    }

    /**
     * Verifies that a large volume of applications is processed and enumerated
     * correctly without index offset errors.
     *
     * @throws InternTrackrException If an error occurs during execution.
     */
    @Test
    public void execute_largeApplicationList_showsAllCorrectlyIndexed() throws InternTrackrException {
        for (int i = 0; i < 100; i++) {
            stubAppList.applications.add(new Application("Company " + i, "Role", "Status"));
        }

        listCommand.execute(stubAppList, stubUi, dummyStorage);

        assertEquals(101, stubUi.printedMessages.size());
        assertTrue(stubUi.printedMessages.get(100).startsWith("100. "));
        assertTrue(stubUi.printedMessages.get(100).contains("Company 99"));
    }

    /**
     * Verifies that applications containing special characters in their fields
     * are printed correctly without formatting issues.
     *
     * @throws InternTrackrException If an error occurs during execution.
     */
    @Test
    public void execute_applicationWithSpecialCharacters_printsCorrectly() throws InternTrackrException {
        stubAppList.applications.add(new Application("Stripe !@#$%", "Dev 1.0", "Applied -> Interview"));

        listCommand.execute(stubAppList, stubUi, dummyStorage);

        assertEquals(2, stubUi.printedMessages.size());
        assertTrue(stubUi.printedMessages.get(1).contains("Stripe !@#$%"));
        assertTrue(stubUi.printedMessages.get(1).contains("Applied -> Interview"));
    }

    /**
     * Verifies that executing the same command instance multiple times yields
     * identical and consistent results without unintended side effects.
     *
     * @throws InternTrackrException If an error occurs during execution.
     */
    @Test
    public void execute_calledMultipleTimes_executesConsistentlyWithoutSideEffects() throws InternTrackrException {
        stubAppList.applications.add(new Application("Apple", "iOS Intern", "Applied"));

        listCommand.execute(stubAppList, stubUi, dummyStorage);
        assertEquals(2, stubUi.printedMessages.size());

        stubUi.printedMessages.clear();

        listCommand.execute(stubAppList, stubUi, dummyStorage);
        assertEquals(2, stubUi.printedMessages.size());
        assertTrue(stubUi.printedMessages.get(1).contains("Apple"));
    }

    /**
     * Verifies that the {@link ListCommand} is correctly instantiated as a
     * subclass of the {@link Command} abstract base class.
     */
    @Test
    public void commandType_isInstanceOfCommand_true() {
        assertTrue(listCommand instanceof Command);
    }
}
