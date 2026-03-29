package seedu.interntrackr.parser;

import seedu.interntrackr.command.FindCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser {
    /**
     * Parses the given arguments in the context of the FindCommand.
     *
     * @param args The user input arguments after the command word.
     * @return A FindCommand object for execution.
     * @throws InternTrackrException If the keyword is missing.
     */
    public FindCommand parse(String args) throws InternTrackrException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new InternTrackrException("The find command requires a keyword! (e.g., find Google)");
        }
        return new FindCommand(trimmedArgs);
    }
}
