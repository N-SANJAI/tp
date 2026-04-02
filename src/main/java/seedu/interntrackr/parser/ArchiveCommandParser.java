package seedu.interntrackr.parser;

import java.util.logging.Logger;

import seedu.interntrackr.command.ArchiveCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses user input arguments for the archive command.
 */
public class ArchiveCommandParser {
    private static final Logger logger = Logger.getLogger(ArchiveCommandParser.class.getName());

    /**
     * Parses the given arguments and returns an ArchiveCommand.
     *
     * @param arguments The argument string following the "archive" keyword.
     * @return A new ArchiveCommand with the parsed index.
     * @throws InternTrackrException If the index is missing, non-numeric, or non-positive.
     */
    public static ArchiveCommand parse(String arguments) throws InternTrackrException {
        if (arguments.isEmpty()) {
            logger.warning("Archive command missing index.");
            throw new InternTrackrException("Invalid format. Usage: archive INDEX");
        }
        try {
            int index = Integer.parseInt(arguments.trim());
            if (index <= 0) {
                logger.warning("Archive index is non-positive: " + index);
                throw new InternTrackrException("Index must be a positive integer.");
            }
            logger.fine("Parsed: ArchiveCommand index=" + index);
            return new ArchiveCommand(index);
        } catch (NumberFormatException e) {
            logger.warning("Archive index is not a number: \"" + arguments.trim() + "\"");
            throw new InternTrackrException("The application index must be a number.");
        }
    }
}
