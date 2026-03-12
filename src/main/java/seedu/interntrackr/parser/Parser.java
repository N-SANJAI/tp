package seedu.interntrackr.parser;

import seedu.interntrackr.command.AddCommand;
import seedu.interntrackr.command.Command;
import seedu.interntrackr.command.DeleteCommand;
import seedu.interntrackr.command.ExitCommand;
import seedu.interntrackr.command.FilterCommand;
import seedu.interntrackr.command.ListCommand;
import seedu.interntrackr.command.OverviewCommand;
import seedu.interntrackr.command.StatusCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses raw user input into executable command objects.
 */
public class Parser {

    public static Command parse(String fullCommand) throws InternTrackrException {
        String[] parts = fullCommand.trim().split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        switch (commandWord) {
        case "add":
            if (!arguments.contains("c/") || !arguments.contains("r/")) {
                throw new InternTrackrException("Invalid format. Usage: add c/COMPANY r/ROLE");
            }
            return parseAddCommand(arguments);

        case "overview":
            return new OverviewCommand();

        case "list":
            return new ListCommand();

        case "status":
            if (!arguments.contains(" s/")) {
                throw new InternTrackrException("Invalid format. Usage: status INDEX s/STATUS");
            }
            String[] statusArgs = arguments.split(" s/", 2);
            try {
                int index = Integer.parseInt(statusArgs[0].trim());
                String parsedStatus = statusArgs[1].replace("\"", "").trim();
                return new StatusCommand(index, parsedStatus);
            } catch (NumberFormatException e) {
                throw new InternTrackrException("The application index must be a number.");
            }

        case "delete":
            if (arguments.isEmpty()) {
                throw new InternTrackrException("Invalid format. Usage: delete INDEX");
            }
            try {
                int deleteIndex = Integer.parseInt(arguments.trim());
                return new DeleteCommand(deleteIndex);
            } catch (NumberFormatException e) {
                throw new InternTrackrException("The application index must be a number.");
            }

        case "filter":
            if (arguments.isEmpty()) {
                throw new InternTrackrException("Invalid format. Usage: filter s/STATUS or filter clear");
            }
            if (arguments.equalsIgnoreCase("clear")) {
                return new FilterCommand(true);
            }
            if (!arguments.startsWith("s/")) {
                throw new InternTrackrException("Invalid format. Usage: filter s/STATUS");
            }
            String filterStatus = arguments.substring(2).replace("\"", "").trim();
            return new FilterCommand(filterStatus);

        case "exit":
            return new ExitCommand();

        default:
            throw new InternTrackrException("I'm sorry, but I don't know what that command means :-(");
        }
    }

    private static AddCommand parseAddCommand(String arguments) throws InternTrackrException {
        String company = "";
        String role = "";

        try {
            int cIndex = arguments.indexOf("c/");
            int rIndex = arguments.indexOf("r/");

            if (cIndex < rIndex) {
                company = arguments.substring(cIndex + 2, rIndex).trim().replace("\"", "");
                role = arguments.substring(rIndex + 2).trim().replace("\"", "");
            } else {
                role = arguments.substring(rIndex + 2, cIndex).trim().replace("\"", "");
                company = arguments.substring(cIndex + 2).trim().replace("\"", "");
            }

            if (company.isEmpty() || role.isEmpty()) {
                throw new InternTrackrException("Company and role cannot be empty.");
            }

            return new AddCommand(company, role);
        } catch (Exception e) {
            throw new InternTrackrException("Error parsing add command.");
        }
    }
}
