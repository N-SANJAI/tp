package seedu.interntrackr.parser;

import java.util.logging.Logger;

import seedu.interntrackr.command.AddCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses user input arguments for the add command.
 */
public class AddCommandParser {
    private static final Logger logger = Logger.getLogger(AddCommandParser.class.getName());

    private static final String PREFIX_COMPANY = "c/";
    private static final String PREFIX_ROLE = "r/";

    /**
     * Parses the given arguments and returns an AddCommand.
     *
     * @param arguments The argument string following the "add" keyword.
     * @return A new AddCommand with the parsed company and role.
     * @throws InternTrackrException If the format is invalid or company/role values are empty.
     */
    public static AddCommand parse(String arguments) throws InternTrackrException {
        assert arguments != null : "arguments must not be null";

        String paddedArgs = " " + arguments;
        String searchCompany = " " + PREFIX_COMPANY;
        String searchRole = " " + PREFIX_ROLE;

        if (!paddedArgs.contains(searchCompany) || !paddedArgs.contains(searchRole)) {
            logger.warning("Add command missing c/ or r/ parameter.");
            throw new InternTrackrException("Invalid format. Usage: add c/COMPANY r/ROLE");
        }

        logger.fine("Parsing add command args: \"" + arguments + "\"");

        String company;
        String role;

        try {
            int cIndex = paddedArgs.lastIndexOf(searchCompany);
            int rIndex = paddedArgs.lastIndexOf(searchRole);

            if (cIndex < rIndex) {
                company = paddedArgs.substring(cIndex + searchCompany.length(), rIndex).trim().replace("\"", "");
                role = paddedArgs.substring(rIndex + searchRole.length()).trim().replace("\"", "");
            } else {
                role = paddedArgs.substring(rIndex + searchRole.length(), cIndex).trim().replace("\"", "");
                company = paddedArgs.substring(cIndex + searchCompany.length()).trim().replace("\"", "");
            }

            if (company.isEmpty() || role.isEmpty()) {
                logger.warning("Add command has blank company or role after parsing.");
                throw new InternTrackrException("Company and role cannot be empty.");
            }

            logger.fine("Parsed AddCommand: company=\"" + company + "\", role=\"" + role + "\"");
            return new AddCommand(company, role);
        } catch (InternTrackrException e) {
            throw e;
        } catch (Exception e) {
            logger.warning("Unexpected error parsing add command: " + e.getMessage());
            throw new InternTrackrException("Error parsing add command.");
        }
    }
}
