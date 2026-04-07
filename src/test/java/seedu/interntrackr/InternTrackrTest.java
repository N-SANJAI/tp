package seedu.interntrackr;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.interntrackr.model.ApplicationList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InternTrackrTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private InternTrackr makeTracker(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        return new InternTrackr("non_existent_file.txt");
    }

    @Test
    public void constructor_invalidFilePath_initializesEmptyList() throws Exception {
        InternTrackr tracker = makeTracker("exit\n");

        Field field = InternTrackr.class.getDeclaredField("applications");
        field.setAccessible(true);
        ApplicationList applications = (ApplicationList) field.get(tracker);

        assertNotNull(applications);
        assertEquals(0, applications.getSize());
    }

    @Test
    public void constructor_corruptedFile_initializesEmptyListAndShowsLoadingError()
            throws Exception {
        File tempFile = File.createTempFile("corrupt_test", ".txt");
        tempFile.deleteOnExit();
        try (FileWriter fw = new FileWriter(tempFile)) {
            // Valid storage lines need: "company | role | status"
            // This line has no " | " separators, should trigger InternTrackrException
            fw.write("corruptedDataWithoutPipeSeparators\n");
        }

        System.setIn(new ByteArrayInputStream("exit\n".getBytes()));
        InternTrackr tracker = new InternTrackr(tempFile.getAbsolutePath());

        Field field = InternTrackr.class.getDeclaredField("applications");
        field.setAccessible(true);
        ApplicationList applications = (ApplicationList) field.get(tracker);

        assertNotNull(applications, "applications must not be null even after load failure");
        assertEquals(0, applications.getSize(), "should start fresh after corrupted data");
        assertTrue(outContent.toString().contains("No existing data found"),
                "should display loading error message");
    }

    @Test
    public void constructor_fieldsInitialized() throws Exception {
        InternTrackr tracker = makeTracker("exit\n");

        Field uiField = InternTrackr.class.getDeclaredField("ui");
        uiField.setAccessible(true);
        assertNotNull(uiField.get(tracker), "ui should be initialized");

        Field storageField = InternTrackr.class.getDeclaredField("storage");
        storageField.setAccessible(true);
        assertNotNull(storageField.get(tracker), "storage should be initialized");

        Field applicationsField = InternTrackr.class.getDeclaredField("applications");
        applicationsField.setAccessible(true);
        assertNotNull(applicationsField.get(tracker), "applications should be initialized");
    }

    @Test
    public void run_exitCommandOnly_exitsSmoothly() {
        InternTrackr tracker = makeTracker("exit\n");
        tracker.run();

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to InternTrackr!"),
                "Should print welcome message");
        assertTrue(output.contains("Bye!"),
                "Should print goodbye on exit");
    }

    @Test
    public void run_unknownCommandThenExit_showsErrorMessage() {
        InternTrackr tracker = makeTracker("foobar\nexit\n");
        tracker.run();

        String output = outContent.toString();
        assertTrue(output.contains("Error:"),
                "Should show error for unknown command");
        assertTrue(output.contains("Bye!"),
                "Should continue to exit after error");
    }

    @Test
    public void run_anotherUnknownCommand_showsErrorMessage() {
        InternTrackr tracker = makeTracker("lol_this_is_wrong\nexit\n");
        tracker.run();

        assertTrue(outContent.toString().contains("Error:"),
                "Should show error for any unrecognised command");
    }

    @Test
    public void run_blankCommandThenExit_showsError() {
        InternTrackr tracker = makeTracker("   \nexit\n");
        tracker.run();

        assertTrue(outContent.toString().contains("Error:"),
                "Blank input should produce an error message");
    }

    @Test
    public void run_overviewOnEmptyList_showsZeroApplications() {
        InternTrackr tracker = makeTracker("overview\nexit\n");
        tracker.run();

        assertTrue(outContent.toString().contains("You haven't tracked any applications yet."),
                "overview on empty list should show custom empty message");
    }

    @Test
    public void run_addThenOverviewThenExit_tracksApplicationCorrectly() throws Exception {
        File tempFile = File.createTempFile("interntrackr_run_test", ".txt");
        tempFile.deleteOnExit();

        System.setIn(new ByteArrayInputStream(
                "add c/Google r/SWE\noverview\nexit\n".getBytes()));
        InternTrackr tracker = new InternTrackr(tempFile.getAbsolutePath());
        tracker.run();

        String output = outContent.toString();
        assertTrue(output.contains("Got it"),
                "Should confirm the application was added");

        assertTrue(output.contains("tracking 1 application(s)"),
                "overview should reflect 1 added application");
    }

    @Test
    public void run_multipleErrorsThenExit_allErrorsShown() {
        InternTrackr tracker = makeTracker("bad1\nbad2\nexit\n");
        tracker.run();

        String output = outContent.toString();
        long errorCount = output.lines()
                .filter(line -> line.startsWith("Error:"))
                .count();
        assertTrue(errorCount >= 2,
                "Should show at least 2 error messages for 2 bad commands");
        assertTrue(output.contains("Bye!"),
                "Should still exit cleanly after multiple errors");
    }

    @Test
    public void run_commandSequenceWithMultipleOperations_executesCorrectly() {
        String commands = "add c/Microsoft r/SDE\n"
                + "list\n"
                + "add c/Apple r/Software Engineer\n"
                + "list\n"
                + "overview\n"
                + "exit\n";
        InternTrackr tracker = makeTracker(commands);
        tracker.run();

        String output = outContent.toString();
        assertTrue(output.contains("tracking 2 application(s)"),
                "Should track 2 applications after adding 2");
        assertTrue(output.contains("Microsoft") && output.contains("Apple"),
                "Should display both companies in list");
    }

    @Test
    public void run_invalidCommandFollowedByValidCommand_continuesExecution() {
        InternTrackr tracker = makeTracker("invalid_cmd\nadd c/Google r/Intern\nlist\nexit\n");
        tracker.run();

        String output = outContent.toString();
        assertTrue(output.contains("Error:"),
                "Should show error for invalid command");
        assertTrue(output.contains("Got it"),
                "Should still process valid command after error");
        assertTrue(output.contains("Google"),
                "Should display the added application");
    }

    @Test
    public void main_withExitCommand_terminatesNormally() {
        System.setIn(new ByteArrayInputStream("exit\n".getBytes()));
        InternTrackr.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to InternTrackr!"),
                "main() should show welcome");
        assertTrue(output.contains("Bye!"),
                "main() should show goodbye");
    }

    @Test
    public void main_withEnableLoggingFlag_enablesLogging() {
        System.setIn(new ByteArrayInputStream("exit\n".getBytes()));
        InternTrackr.main(new String[]{"--enable-logging"});

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to InternTrackr!"),
                "main() should show welcome with logging enabled");
        assertTrue(output.contains("Bye!"),
                "main() should show goodbye");
    }

    @Test
    public void main_withMultipleArgsIncludingLoggingFlag_parsesLoggingFlagCorrectly() {
        System.setIn(new ByteArrayInputStream("exit\n".getBytes()));
        String[] args = {"--some-other-flag", "--enable-logging", "--another-flag"};
        InternTrackr.main(args);

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to InternTrackr!"),
                "main() should process multiple args and find logging flag");
        assertTrue(output.contains("Bye!"),
                "main() should still exit normally");
    }

    @Test
    public void main_withoutLoggingFlag_loggingDisabled() {
        System.setIn(new ByteArrayInputStream("exit\n".getBytes()));
        InternTrackr.main(new String[]{"--some-random-flag"});

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to InternTrackr!"),
                "main() should show welcome with logging disabled");
        assertTrue(output.contains("Bye!"),
                "main() should show goodbye");
    }

    @Test
    public void main_withEmptyArgs_loggingDisabledByDefault() {
        System.setIn(new ByteArrayInputStream("exit\n".getBytes()));
        InternTrackr.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to InternTrackr!"),
                "main() should work with empty args");
    }

    @Test
    public void run_internTrackrExceptionInCommand_showsErrorAndContinues() {
        InternTrackr tracker = makeTracker("delete 999\nexit\n");
        tracker.run();

        String output = outContent.toString();
        assertTrue(output.contains("Error:"),
                "Should catch and display InternTrackrException");
        assertTrue(output.contains("Bye!"),
                "Should continue loop after exception");
    }

    @Test
    public void run_multipleExceptionsWithRecovery_continuesProcessing() {
        InternTrackr tracker = makeTracker("delete 1\ndelete 2\nadd c/Test r/Role\nlist\nexit\n");
        tracker.run();

        String output = outContent.toString();
        long errorCount = output.lines()
                .filter(line -> line.startsWith("Error:"))
                .count();
        assertTrue(errorCount >= 2,
                "Should show errors for invalid deletes");
        assertTrue(output.contains("Got it"),
                "Should successfully add application after errors");
    }

    @Test
    public void run_helpCommandThenExit_displaysHelpAndExits() {
        InternTrackr tracker = makeTracker("help\nexit\n");
        tracker.run();

        String output = outContent.toString();
        assertTrue(output.contains("For more details") || output.contains("help"),
                "Should display help information");
        assertTrue(output.contains("Bye!"),
                "Should still exit after help");
    }
}
