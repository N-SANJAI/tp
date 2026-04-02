package seedu.interntrackr.command;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.util.logging.Logger;

/**
 * Lists all non-archived internship applications currently tracked.
 */
public class ListCommand extends Command {
    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());

    /**
     * Executes the list command and prints all non-archived applications to the console.
     *
     * @param applications The list of internship applications.
     * @param ui           The user interface for output.
     * @param storage      The storage handler.
     * @throws InternTrackrException If the list cannot be displayed.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList cannot be null";
        logger.info("Executing ListCommand. Total applications: " + applications.getSize());

        int matchCount = 0;
        for (int i = 1; i <= applications.getSize(); i++) {
            Application app = applications.getApplication(i);
            if (!app.isArchived()) {
                matchCount++;
            }
        }

        if (matchCount == 0) {
            ui.showMessage("No applications found. Start adding some!");
            return;
        }

        ui.showMessage("Here are your internship applications:");
        for (int i = 1; i <= applications.getSize(); i++) {
            Application app = applications.getApplication(i);
            if (!app.isArchived()) {
                ui.showMessage(i + ". " + app.toString());
                if (app.getNote() != null && !app.getNote().isBlank()) {
                    ui.showMessage("   Note: " + app.getNote());
                }
            }
        }

        logger.fine("Listed " + matchCount + " non-archived applications.");
    }
}
