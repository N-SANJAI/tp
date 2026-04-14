package seedu.interntrackr.parser;

import seedu.interntrackr.command.NoteCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses input for the note command.
 */
public class NoteCommandParser {

    private static final String EXPECTED_FORMAT = "note INDEX n/NOTE_CONTENT";

    /**
     * Parses the arguments for the note command.
     * Expected format: INDEX n/NOTE_CONTENT
     *
     * @param arguments The raw argument string.
     * @return A NoteCommand with the parsed index and note.
     * @throws InternTrackrException If the format is invalid.
     */
    public static NoteCommand parse(String arguments) throws InternTrackrException {
        validateArguments(arguments);
        String[] parts = splitArguments(arguments);
        int index = parseIndex(parts[0]);
        String note = parts[1].trim();
        if (note.equals("-")) {
            throw new InternTrackrException("Note content cannot be exactly '-' "
                    + "as it conflicts with the save data format.");
        }

        return new NoteCommand(index, note);
    }

    /**
     * Validates that the raw argument string is non-blank and contains the note prefix.
     *
     * @param arguments The raw argument string.
     * @throws InternTrackrException If the arguments are blank or missing the note prefix.
     */
    private static void validateArguments(String arguments) throws InternTrackrException {
        if (arguments == null || arguments.isBlank()) {
            throw new InternTrackrException("Invalid format. Usage: " + EXPECTED_FORMAT);
        }
        if (!arguments.contains(" n/")) {
            throw new InternTrackrException("Invalid format. Usage: " + EXPECTED_FORMAT);
        }
    }

    /**
     * Splits the argument string into index and note content parts.
     *
     * @param arguments The raw argument string.
     * @return A two-element array containing the index string and note content.
     * @throws InternTrackrException If the note content is empty.
     */
    private static String[] splitArguments(String arguments) throws InternTrackrException {
        String[] parts = arguments.split(" n/", 2);
        if (parts.length < 2 || parts[1].isBlank()) {
            throw new InternTrackrException("Note content cannot be empty. Usage: " + EXPECTED_FORMAT);
        }
        return parts;
    }

    /**
     * Parses the index string into a valid positive integer.
     *
     * @param indexStr The raw index string.
     * @return The parsed 1-based index.
     * @throws InternTrackrException If the index is not a valid positive number.
     */
    private static int parseIndex(String indexStr) throws InternTrackrException {
        int index;
        try {
            index = Integer.parseInt(indexStr.trim());
        } catch (NumberFormatException e) {
            throw new InternTrackrException("Index must be a valid number. Usage: " + EXPECTED_FORMAT);
        }
        if (index < 1) {
            throw new InternTrackrException("Index must be a positive number.");
        }
        return index;
    }
}
