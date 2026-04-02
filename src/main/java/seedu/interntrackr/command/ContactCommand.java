package seedu.interntrackr.command;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds or updates recruiter contact details for a specific application.
 */
public class ContactCommand extends Command {
    private static final Logger logger = Logger.getLogger(ContactCommand.class.getName());

    private final int index;
    private final String contactName;
    private final String contactEmail;

    /**
     * Constructs a ContactCommand with the given application index, contact name, and contact email.
     *
     * @param index The 1-based application index.
     * @param contactName The recruiter contact name.
     * @param contactEmail The recruiter contact email.
     */
    public ContactCommand(int index, String contactName, String contactEmail) {
        this.index = index;
        this.contactName = (contactName == null) ? "" : contactName.trim();
        this.contactEmail = (contactEmail == null) ? "" : contactEmail.trim();
    }

    /**
     * Executes the contact command.
     * Validates the application index, contact name, and contact email before updating the application.
     *
     * @param applications The list of internship applications.
     * @param ui The user interface to display messages.
     * @param storage The storage handler used to persist the updated application list.
     * @throws InternTrackrException If the index is invalid or the contact details are invalid.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList cannot be null during execution";
        assert ui != null : "Ui cannot be null during execution";
        assert storage != null : "Storage cannot be null during execution";

        logger.log(Level.INFO, "Executing ContactCommand. index: " + index
                + ", contactName: " + contactName + ", contactEmail: " + contactEmail);

        if (index < 1 || index > applications.countActive()) {
            logger.log(Level.WARNING, "Invalid application index for contact command: " + index);
            throw new InternTrackrException("Invalid application index.");
        }

        if (contactName.isEmpty()) {
            logger.log(Level.WARNING, "Contact attempt with empty contact name.");
            throw new InternTrackrException("Contact name cannot be empty.");
        }

        if (contactEmail.isEmpty()) {
            logger.log(Level.WARNING, "Contact attempt with empty contact email.");
            throw new InternTrackrException("Contact email cannot be empty.");
        }

        if (!isValidEmail(contactEmail)) {
            logger.log(Level.WARNING, "Invalid contact email format: " + contactEmail);
            throw new InternTrackrException("Invalid contact email format.");
        }

        Application app = applications.getActiveApplication(index);
        assert app != null : "Application should not be null for a valid index";

        logger.log(Level.FINE, "Updating contact details for application: "
                + app.getCompany() + " | " + app.getRole());

        app.setContactDetails(contactName, contactEmail);

        assert contactName.equals(app.getContactName()) : "Contact name should be updated correctly";
        assert contactEmail.equals(app.getContactEmail()) : "Contact email should be updated correctly";

        ui.showMessage("Contact details updated for " + app.getCompany() + " | " + app.getRole()
                + ": " + contactName + " | " + contactEmail);

        storage.save(applications.getApplications());
        logger.log(Level.INFO, "Contact details saved successfully for application index: " + index);
    }

    /**
     * Returns true if the given email has a minimally valid structure.
     *
     * @param email The email string to validate.
     * @return True if the email format is minimally valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        assert email != null : "Email should not be null during validation";

        if (email.contains(" ")) {
            return false;
        }

        int atIndex = email.indexOf('@');
        int lastAtIndex = email.lastIndexOf('@');

        if (atIndex <= 0 || atIndex != lastAtIndex || atIndex == email.length() - 1) {
            return false;
        }

        int dotIndex = email.indexOf('.', atIndex);
        return dotIndex > atIndex + 1 && dotIndex < email.length() - 1;
    }
}
