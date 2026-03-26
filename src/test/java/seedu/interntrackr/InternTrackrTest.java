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
    public void constructor_corruptedFile_initializesEmptyListAndShowsLoadingError() throws Exception {
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

        assertTrue(outContent.toString().contains("tracking 0 applications"),
                "overview on empty list should say 0 applications");
    }

    @Test
    public void run_addThenOverviewThenExit_tracksApplicationCorrectly() throws Exception {
        File tempFile = File.createTempFile("interntrackr_run_test", ".txt");
        tempFile.deleteOnExit();

        System.setIn(new ByteArrayInputStream("add c/Google r/SWE\noverview\nexit\n".getBytes()));
        InternTrackr tracker = new InternTrackr(tempFile.getAbsolutePath());
        tracker.run();

        String output = outContent.toString();
        assertTrue(output.contains("Got it"),
                "Should confirm the application was added");
        assertTrue(output.contains("tracking 1 applications"),
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
    public void main_withExitCommand_terminatesNormally() {
        System.setIn(new ByteArrayInputStream("exit\n".getBytes()));
        InternTrackr.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to InternTrackr!"),
                "main() should show welcome");
        assertTrue(output.contains("Bye!"),
                "main() should show goodbye");
    }
}
