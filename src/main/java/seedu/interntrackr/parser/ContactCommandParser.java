package seedu.interntrackr.parser;

import java.util.logging.Logger;

import seedu.interntrackr.command.ContactCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses user input arguments for the contact command.
 */
public class ContactCommandParser {
    private static final Logger logger = Logger.getLogger(ContactCommandParser.class.getName());

    private static final String PREFIX_CONTACT_NAME = "c/";
    private static final String PREFIX_CONTACT_EMAIL = "e/";
    private static final String CONTACT_USAGE =
            "Invalid format. Usage: contact INDEX c/NAME e/EMAIL";

    /**
     * Parses the given arguments and returns a ContactCommand.
     *
     * @param arguments The argument string following the "contact" keyword.
     * @return A new ContactCommand with the parsed index, contact name, and contact email.
     * @throws InternTrackrException If the format is invalid or any parsed value is empty.
     */
    public static ContactCommand parse(String arguments) throws InternTrackrException {
        assert arguments != null : "arguments must not be null";

        if (arguments == null || arguments.isBlank()) {
            logger.warning("Contact command received blank arguments.");
            throw new InternTrackrException(CONTACT_USAGE);
        }

        if (!arguments.contains(" " + PREFIX_CONTACT_NAME) || !arguments.contains(" " + PREFIX_CONTACT_EMAIL)) {
            logger.warning("Contact command missing c/ or e/ parameter.");
            throw new InternTrackrException(CONTACT_USAGE);
        }

        logger.fine("Parsing contact command args: \"" + arguments + "\"");

        try {
            int nameIndex = arguments.indexOf(" " + PREFIX_CONTACT_NAME);
            int emailIndex = arguments.indexOf(" " + PREFIX_CONTACT_EMAIL);

            if (nameIndex == -1 || emailIndex == -1) {
                logger.warning("Contact command prefixes could not be located during parsing.");
                throw new InternTrackrException(CONTACT_USAGE);
            }

            if (nameIndex > emailIndex) {
                logger.warning("Contact command has incorrect parameter ordering.");
                throw new InternTrackrException(CONTACT_USAGE);
            }

            String indexString = arguments.substring(0, nameIndex).trim();
            if (indexString.isEmpty()) {
                logger.warning("Contact command missing application index.");
                throw new InternTrackrException(CONTACT_USAGE);
            }

            int index = Integer.parseInt(indexString);

            if (index < 1) {
                logger.warning("Contact command index is less than 1: " + index);
                throw new InternTrackrException("Application index must be a positive integer.");
            }

            String contactName = arguments.substring(
                            nameIndex + (" " + PREFIX_CONTACT_NAME).length(), emailIndex)
                    .trim()
                    .replace("\"", "");

            String contactEmail = arguments.substring(
                            emailIndex + (" " + PREFIX_CONTACT_EMAIL).length())
                    .trim()
                    .replace("\"", "");

            if (contactName.isEmpty() || contactEmail.isEmpty()) {
                logger.warning("Contact command has blank contact name or contact email after parsing.");
                throw new InternTrackrException("Contact name and contact email cannot be empty.");
            }

            if (containsDuplicatePrefix(arguments, PREFIX_CONTACT_NAME)
                    || containsDuplicatePrefix(arguments, PREFIX_CONTACT_EMAIL)) {
                logger.warning("Contact command contains duplicate c/ or e/ prefixes.");
                throw new InternTrackrException(CONTACT_USAGE);
            }

            logger.fine("Parsed ContactCommand: index=\"" + index
                    + "\", contactName=\"" + contactName
                    + "\", contactEmail=\"" + contactEmail + "\"");

            return new ContactCommand(index, contactName, contactEmail);

        } catch (InternTrackrException e) {
            throw e;
        } catch (NumberFormatException e) {
            logger.warning("Contact command index is not a number.");
            throw new InternTrackrException("The application index must be a number.");
        } catch (Exception e) {
            logger.warning("Unexpected error parsing contact command: " + e.getMessage());
            throw new InternTrackrException("Error parsing contact command.");
        }
    }

    /**
     * Returns true if the given prefix appears more than once in the argument string.
     *
     * @param arguments The full contact command argument string.
     * @param prefix The prefix to check.
     * @return True if the prefix appears more than once.
     */
    private static boolean containsDuplicatePrefix(String arguments, String prefix) {
        assert arguments != null : "arguments must not be null";
        assert prefix != null && !prefix.isEmpty() : "prefix must not be null or empty";

        int firstIndex = arguments.indexOf(" " + prefix);
        if (firstIndex == -1) {
            return false;
        }

        int secondIndex = arguments.indexOf(" " + prefix, firstIndex + 1);
        return secondIndex != -1;
    }
}
