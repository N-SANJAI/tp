package seedu.interntrackr.parser;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.command.UnarchiveCommand;
import seedu.interntrackr.exception.InternTrackrException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnarchiveCommandParserTest {

    @Test
    public void parse_validIndex_returnsUnarchiveCommand() throws InternTrackrException {
        assertTrue(UnarchiveCommandParser.parse("1") instanceof UnarchiveCommand);
    }

    @Test
    public void parse_largeValidIndex_returnsUnarchiveCommand() throws InternTrackrException {
        assertTrue(UnarchiveCommandParser.parse("99") instanceof UnarchiveCommand);
    }

    @Test
    public void parse_missingIndex_throwsInternTrackrException() {
        assertThrows(InternTrackrException.class, () -> UnarchiveCommandParser.parse(""));
    }

    @Test
    public void parse_nonNumericIndex_throwsInternTrackrException() {
        assertThrows(InternTrackrException.class, () -> UnarchiveCommandParser.parse("abc"));
    }
}
