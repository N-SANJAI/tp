package seedu.interntrackr.parser;

import seedu.interntrackr.command.OfferCommand;
import seedu.interntrackr.exception.InternTrackrException;

import java.util.logging.Logger;

/**
 * Parses user input arguments and creates a new OfferCommand object.
 */
public class OfferCommandParser {
    private static final Logger logger = Logger.getLogger(OfferCommandParser.class.getName());

    /**
     * Parses the given string of arguments into an OfferCommand.
     * Expected format: INDEX s/[SALARY]
     *
     * @param args The input arguments string (e.g., "1 s/5000").
     * @return A new OfferCommand instance.
     * @throws InternTrackrException If the user input does not conform to the expected format,
     * or if the numerical values are invalid.
     */
    public static OfferCommand parse(String args) throws InternTrackrException {
        assert args != null : "Arguments passed to OfferCommandParser cannot be null";

        if (args.trim().isEmpty()) {
            throw new InternTrackrException("Invalid format! Use: offer INDEX s/[SALARY]");
        }

        String[] parts = args.split(" s/", 2);
        if (parts.length < 2) {
            logger.warning("Missing salary prefix 's/' in offer command");
            throw new InternTrackrException("Please specify the salary using the 's/' prefix. "
                    + "Example: offer 1 s/5000");
        }

        try {
            int index = Integer.parseInt(parts[0].trim());
            double salary = Double.parseDouble(parts[1].trim());

            if (salary < 0) {
                throw new InternTrackrException("Salary cannot be negative.");
            }

            return new OfferCommand(index, salary);
        } catch (NumberFormatException e) {
            logger.warning("Failed to parse index or salary as numbers");
            throw new InternTrackrException("Index and salary must be valid numerical values.");
        }
    }
}