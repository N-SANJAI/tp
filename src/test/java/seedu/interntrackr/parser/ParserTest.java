package seedu.interntrackr.parser;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.command.ExitCommand;
import seedu.interntrackr.command.ListCommand;
import seedu.interntrackr.command.OverviewCommand;
import seedu.interntrackr.command.ContactCommand;
import seedu.interntrackr.exception.InternTrackrException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    @Test
    public void parse_listCommand_returnsListCommand() throws InternTrackrException {
        assertTrue(Parser.parse("list") instanceof ListCommand);
    }

    @Test
    public void parse_overviewCommand_returnsOverviewCommand() throws InternTrackrException {
        assertTrue(Parser.parse("overview") instanceof OverviewCommand);
    }

    @Test
    public void parse_exitCommand_returnsExitCommand() throws InternTrackrException {
        assertTrue(Parser.parse("exit") instanceof ExitCommand);
    }

    @Test
    public void parse_unknownCommand_throwsInternTrackrException() {
        assertThrows(InternTrackrException.class, () -> Parser.parse("invalidCommand"));
    }

    @Test
    public void parse_blankInput_throwsInternTrackrException() {
        assertThrows(InternTrackrException.class, () -> Parser.parse("   "));
    }

    @Test
    void parse_contactCommand_success() throws Exception {
        assertTrue(Parser.parse("contact 1 c/Jane Tan e/jane@example.com") instanceof ContactCommand);
    }
}
