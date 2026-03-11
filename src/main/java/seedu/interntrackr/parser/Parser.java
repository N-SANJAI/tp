package seedu.interntrackr.parser;

import seedu.interntrackr.command.Command;
import seedu.interntrackr.command.AddCommand;
import seedu.interntrackr.command.ExitCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses user input into executable commands.
 */
public class Parser {
    /**
     * Parses the full user command and returns the corresponding Command object.
     *
     * @param fullCommand The raw string inputted by the user.
     * @return The specific Command object to be executed.
     * @throws InternTrackrException If the command format is invalid.
     */
    public static Command parse(String fullCommand) throws InternTrackrException {
        // TODO: Implement string splitting and switch case for commands
        // Example:
        // if (fullCommand.startsWith("add")) { return new AddCommand("Shopee", "Backend"); }
        return new ExitCommand();
    }
}