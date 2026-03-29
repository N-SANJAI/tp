package seedu.interntrackr.parser;

import java.util.logging.Logger;

import seedu.interntrackr.command.Command;
import seedu.interntrackr.command.ClearCommand;
import seedu.interntrackr.command.OverviewCommand;
import seedu.interntrackr.command.ListCommand;
import seedu.interntrackr.command.ExitCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Dispatches raw user input to the appropriate command parser.
 */
public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    /**
     * Parses the full command string entered by the user into an executable Command object.
     *
     * @param fullCommand The raw input string from the user.
     * @return The corresponding Command object.
     * @throws InternTrackrException If the input is blank, the format is invalid, or the command is unknown.
     */
    public static Command parse(String fullCommand) throws InternTrackrException {
        assert fullCommand != null : "Input command must not be null";

        if (fullCommand == null || fullCommand.isBlank()) {
            logger.warning("parse() received null or blank input.");
            throw new InternTrackrException("Input cannot be empty. Please enter a command.");
        }

        String[] parts = fullCommand.trim().split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        logger.fine("Parsing command: \"" + commandWord + "\" with args: \"" + arguments + "\"");

        switch (commandWord) {
        case "add":
            return AddCommandParser.parse(arguments);
        case "delete":
            return DeleteCommandParser.parse(arguments);
        case "status":
            return StatusCommandParser.parse(arguments);
        case "filter":
            return FilterCommandParser.parse(arguments);
        case "deadline":
            return DeadlineCommandParser.parse(arguments);
        case "find":
            return new FindCommandParser().parse(arguments);
        case "clear":
            return new ClearCommand();
        case "overview":
            logger.fine("Parsed: OverviewCommand");
            return new OverviewCommand();
        case "list":
            logger.fine("Parsed: ListCommand");
            return new ListCommand();
        case "exit":
            logger.fine("Parsed: ExitCommand");
            return new ExitCommand();
        default:
            logger.warning("Unknown command: \"" + commandWord + "\"");
            throw new InternTrackrException("I'm sorry, but I don't know what that command means :-(");
        }
    }
}
