package seedu.interntrackr.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

import seedu.interntrackr.command.Command;
import seedu.interntrackr.command.DeadlineAddCommand;
import seedu.interntrackr.command.DeadlineListCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses user input arguments for deadline subcommands.
 */
public class DeadlineCommandParser {
    private static final Logger logger = Logger.getLogger(DeadlineCommandParser.class.getName());

    private static final String PREFIX_TYPE = " t/";
    private static final String PREFIX_DATE = " d/";
    private static final String PREFIX_NOTES = " n/";
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    /**
     * Parses the given arguments and returns the corresponding deadline command.
     *
     * @param arguments The argument string following the "deadline" keyword.
     * @return The parsed deadline command.
     * @throws InternTrackrException If the format is invalid.
     */
    public static Command parse(String arguments) throws InternTrackrException {
        if (arguments == null || arguments.isBlank()) {
            throw new InternTrackrException(
                    "Invalid format. Usage: deadline add INDEX t/TYPE d/DD-MM-YYYY or deadline list INDEX");
        }

        String[] parts = arguments.trim().split(" ", 2);
        String subcommandWord = parts[0].toLowerCase();
        String subcommandArgs = parts.length > 1 ? parts[1].trim() : "";

        switch (subcommandWord) {
        case "add":
            return parseAddCommand(subcommandArgs);
        case "list":
            return parseListCommand(subcommandArgs);
        default:
            throw new InternTrackrException(
                    "Invalid format. Usage: deadline add INDEX t/TYPE d/DD-MM-YYYY or deadline list INDEX");
        }
    }

    private static DeadlineAddCommand parseAddCommand(String arguments) throws InternTrackrException {
        if (!arguments.contains(PREFIX_TYPE) || !arguments.contains(PREFIX_DATE)) {
            logger.warning("Deadline add command missing t/ or d/ parameter.");
            throw new InternTrackrException(
                    "Invalid format. Usage: deadline add INDEX t/TYPE d/DD-MM-YYYY [n/NOTES]");
        }

        try {
            int typeIndex = arguments.indexOf(PREFIX_TYPE);
            int dateIndex = arguments.indexOf(PREFIX_DATE);

            if (typeIndex == -1 || dateIndex == -1 || typeIndex > dateIndex) {
                logger.warning("Deadline add command has incorrect parameter ordering.");
                throw new InternTrackrException(
                        "Invalid format. Usage: deadline add INDEX t/TYPE d/DD-MM-YYYY");
            }

            int index = Integer.parseInt(arguments.substring(0, typeIndex).trim());
            String deadlineType = arguments.substring(
                    typeIndex + PREFIX_TYPE.length(), dateIndex).trim().replace("\"", "");
            String dueDateStr = extractDueDateStr(arguments, dateIndex, deadlineType);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDate dueDate = LocalDate.parse(dueDateStr, formatter);

            logger.fine("Parsed: DeadlineAddCommand index=" + index + " type=" + deadlineType);
            return new DeadlineAddCommand(index, deadlineType, dueDate);

        } catch (NumberFormatException e) {
            logger.warning("Deadline index is not a number.");
            throw new InternTrackrException("The application index must be a number.");
        } catch (DateTimeParseException e) {
            logger.warning("Deadline date format invalid.");
            throw new InternTrackrException("Invalid date format. Use DD-MM-YYYY.");
        }
    }

    private static DeadlineListCommand parseListCommand(String arguments) throws InternTrackrException {
        if (arguments.isBlank()) {
            throw new InternTrackrException("Invalid format. Usage: deadline list INDEX");
        }

        try {
            int index = Integer.parseInt(arguments.trim());
            logger.fine("Parsed: DeadlineListCommand index=" + index);
            return new DeadlineListCommand(index);
        } catch (NumberFormatException e) {
            logger.warning("Deadline list index is not a number.");
            throw new InternTrackrException("The application index must be a number.");
        }
    }

    private static String extractDueDateStr(String subArgs, int dateIndex, String deadlineType)
            throws InternTrackrException {
        String dueDateStr = subArgs.substring(dateIndex + PREFIX_DATE.length()).trim().replace("\"", "");

        int notesIndex = dueDateStr.indexOf(PREFIX_NOTES);
        if (notesIndex != -1) {
            dueDateStr = dueDateStr.substring(0, notesIndex).trim();
        }

        if (deadlineType.isEmpty() || dueDateStr.isEmpty()) {
            logger.warning("Deadline type or due date is empty.");
            throw new InternTrackrException("Deadline type and due date cannot be empty.");
        }
        return dueDateStr;
    }
}