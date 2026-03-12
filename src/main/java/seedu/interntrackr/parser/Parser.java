package seedu.interntrackr.parser;

import seedu.interntrackr.command.Command;
import seedu.interntrackr.command.ExitCommand;
import seedu.interntrackr.command.FilterCommand;
import seedu.interntrackr.command.StatusCommand;
import seedu.interntrackr.command.OverviewCommand;
import seedu.interntrackr.command.ListCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses raw user input into executable command objects.
 */
public class Parser {
    /**
     * Translates a string input into its corresponding Command class.
     *
     * @param fullCommand The raw string inputted by the user.
     * @return The specific Command object to be executed.
     * @throws InternTrackrException If the command format is invalid.
     */
    public static Command parse(String fullCommand) throws InternTrackrException {
        String[] parts = fullCommand.trim().split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        switch (commandWord) {
        case "overview":
            return new OverviewCommand();
        case "list":
            return new ListCommand();
        case "status":
            if (arguments.isEmpty() || !arguments.contains(" s/")) {
                throw new InternTrackrException("Invalid format. Usage: status INDEX s/STATUS (e.g., status 1 s/\"Interview\")");
            }

            String[] statusArgs = arguments.split(" s/", 2);
            try {
                int index = Integer.parseInt(statusArgs[0].trim());
                String parsedStatus = statusArgs[1].replace("\"", "").trim();
                return new StatusCommand(index, parsedStatus);
            } catch (NumberFormatException e) {
                throw new InternTrackrException("The application index must be a number.");
            }
        case "filter":
            return new FilterCommand(arguments);
        case "exit":
            return new ExitCommand();
        default:
            throw new InternTrackrException("I'm sorry, but I don't know what that command means :-(");
        }
    }
}
