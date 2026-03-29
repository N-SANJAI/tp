package seedu.interntrackr.command;

import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to clear all internship applications from the list.
 * This command requires an explicit 'yes' confirmation from the user.
 */
public class ClearCommand extends Command {
    private static final Logger logger = Logger.getLogger(ClearCommand.class.getName());

    /**
     * Executes the clear command. Prompts the user for confirmation before
     * wiping the application list and updating the storage file.
     *
     * @param applications The current list of applications.
     * @param ui The UI object used to interact with the user.
     * @param storage The storage object used to save the emptied list.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) {
        assert applications != null : "ApplicationList dependency is missing";
        assert ui != null : "Ui dependency is missing";
        assert storage != null : "Storage dependency is missing";

        ui.showMessage("Are you sure you want to clear all data? Type 'yes' to confirm:");

        String response = ui.readCommand();

        if (response != null && response.trim().equalsIgnoreCase("yes")) {
            logger.log(Level.INFO, "User confirmed clear command. Wiping data.");

            applications.clear();

            try {
                // Save the now-empty list to ensure the storage file is updated.
                storage.save(applications.getApplications());
                ui.showMessage("All data has been cleared.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to update storage after clear command.", e);
                ui.showMessage("Data was cleared locally, but failed to update the storage file.");
            }
        } else {
            logger.log(Level.INFO, "Clear command cancelled by user.");
            ui.showMessage("Clear command cancelled.");
        }
    }
}
