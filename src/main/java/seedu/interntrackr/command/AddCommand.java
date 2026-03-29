package seedu.interntrackr.command;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds a new internship application to the tracker.
 */
public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());

    private final String company;
    private final String role;

    /**
     * Creates an AddCommand with the specified company and role.
     *
     * @param company The name of the company for the internship application.
     * @param role The role applied for at the company.
     * @throws IllegalArgumentException If company or role is null or blank.
     */
    public AddCommand(String company, String role) {
        if (company == null || company.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or blank.");
        }
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or blank.");
        }

        this.company = company.trim();
        this.role = role.trim();
        logger.fine("AddCommand created: " + this.company + " | " + this.role);
    }

    /**
     * Executes the add command by adding a new application to the list and saving it.
     *
     * @param applications The current list of applications.
     * @param ui The UI object used to display output to the user.
     * @param storage The storage object used to persist the updated list.
     * @throws InternTrackrException If any required parameter is null.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";

        logger.info("Executing AddCommand: " + company + " | " + role);

        Application newApp = new Application(company, role);

        if (applications.hasApplication(newApp)) {
            logger.log(Level.INFO, "Duplicate application detected: " + company + " (" + role + ")");
            ui.showMessage("You have already tracked an application for "
                    + role + " at " + company + ".");
            return;
        }

        applications.addApplication(newApp);

        assert applications.getSize() > 0 : "List size must be positive after adding";

        ui.showMessage("Got it. I've added this application:");
        ui.showMessage("  " + newApp.toString());
        ui.showMessage("Now you have " + applications.getSize() + " application(s) in the list.");

        storage.save(applications.getApplications());
        logger.fine("AddCommand executed and saved. Total applications: " + applications.getSize());
    }
}
