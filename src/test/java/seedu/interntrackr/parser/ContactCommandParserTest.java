package seedu.interntrackr.parser;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.command.ContactCommand;
import seedu.interntrackr.exception.InternTrackrException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContactCommandParserTest {

    private static final String USAGE = "Invalid format. Usage: contact INDEX c/NAME e/EMAIL";

    /*
     * Tests for the happy path.
     */
    @Test
    void parse_validArguments_success() throws InternTrackrException {
        ContactCommand command = ContactCommandParser.parse("1 c/Jane Tan e/jane@example.com");
        assertNotNull(command);
    }

    @Test
    void parse_validArgumentsWithQuotes_success() {
        assertDoesNotThrow(() ->
                ContactCommandParser.parse("1 c/\"Jane Tan\" e/\"jane@example.com\""));
    }

    @Test
    void parse_validArgumentsWithIrregularSpacing_success() {
        assertDoesNotThrow(() ->
                ContactCommandParser.parse("  1   c/Jane Tan    e/jane@example.com   "));
    }

    /*
     * Tests for blank or missing overall input.
     */
    @Test
    void parse_blankArguments_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse(""));
        assertEquals(USAGE, e.getMessage());
    }

    @Test
    void parse_whitespaceOnlyArguments_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("   "));
        assertEquals(USAGE, e.getMessage());
    }

    @Test
    void parse_missingIndex_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("c/Jane Tan e/jane@example.com"));
        assertEquals(USAGE, e.getMessage());
    }

    /*
     * Tests for missing prefixes.
     */
    @Test
    void parse_missingContactNamePrefix_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 e/jane@example.com"));
        assertEquals(USAGE, e.getMessage());
    }

    @Test
    void parse_missingContactEmailPrefix_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 c/Jane Tan"));
        assertEquals(USAGE, e.getMessage());
    }

    @Test
    void parse_missingBothPrefixes_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 Jane Tan jane@example.com"));
        assertEquals(USAGE, e.getMessage());
    }

    /*
     * Tests for index parsing.
     */
    @Test
    void parse_nonNumericIndex_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("abc c/Jane Tan e/jane@example.com"));
        assertEquals("The application index must be a number.", e.getMessage());
    }

    @Test
    void parse_decimalIndex_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1.5 c/Jane Tan e/jane@example.com"));
        assertEquals("The application index must be a number.", e.getMessage());
    }

    @Test
    void parse_negativeIndex_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("-1 c/Jane Tan e/jane@example.com"));
        assertEquals("Application index must be a positive integer.", e.getMessage());
    }

    @Test
    void parse_zeroIndex_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("0 c/Jane Tan e/jane@example.com"));
        assertEquals("Application index must be a positive integer.", e.getMessage());
    }

    @Test
    void parse_indexWithExtraWordsBeforePrefix_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 hello c/Jane Tan e/jane@example.com"));
        assertEquals("The application index must be a number.", e.getMessage());
    }

    /*
     * Tests for ordering and structure.
     */
    @Test
    void parse_wrongPrefixOrder_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 e/jane@example.com c/Jane Tan"));
        assertEquals(USAGE, e.getMessage());
    }

    @Test
    void parse_contactNamePrefixWithoutValue_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 c/ e/jane@example.com"));
        assertEquals("Contact name and contact email cannot be empty.", e.getMessage());
    }

    @Test
    void parse_contactEmailPrefixWithoutValue_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 c/Jane Tan e/"));
        assertEquals("Contact name and contact email cannot be empty.", e.getMessage());
    }

    @Test
    void parse_blankContactName_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 c/   e/jane@example.com"));
        assertEquals("Contact name and contact email cannot be empty.", e.getMessage());
    }

    @Test
    void parse_blankContactEmail_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 c/Jane Tan e/   "));
        assertEquals("Contact name and contact email cannot be empty.", e.getMessage());
    }

    @Test
    void parse_duplicateContactNamePrefix_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 c/Jane c/Tan e/jane@example.com"));
        assertEquals(USAGE, e.getMessage());
    }

    @Test
    void parse_duplicateContactEmailPrefix_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 c/Jane Tan e/jane@example.com e/test@example.com"));
        assertEquals(USAGE, e.getMessage());
    }

    @Test
    void parse_duplicateBothPrefixes_throwsException() {
        InternTrackrException e = assertThrows(InternTrackrException.class,
                () -> ContactCommandParser.parse("1 c/Jane c/Tan e/jane@example.com e/test@example.com"));
        assertEquals(USAGE, e.getMessage());
    }
}
