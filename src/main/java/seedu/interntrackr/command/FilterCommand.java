//@@author EmDani3l
package seedu.interntrackr.command;

import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Filters applications based on status.
 */
public class FilterCommand extends Command {
    private static final Logger logger = Logger.getLogger(FilterCommand.class.getName());
    private final String status;
    private final boolean isClear;

    /**
     * Constructs a FilterCommand to filter applications by a specific status.
     *
     * @param status The status string to filter by.
     */
    public FilterCommand(String status) {
        this.status = (status == null) ? "" : status.trim();
        this.isClear = false;
    }

    /**
     * Constructs a FilterCommand to either clear existing filters or apply a blank filter.
     *
     * @param isClear True to clear the filter and show all applications.
     */
    public FilterCommand(boolean isClear) {
        this.status = "";
        this.isClear = isClear;
    }

    /**
     * Executes the filter command.
     * Validates the status, normalizes it for a case-insensitive search, and
     * displays matching applications to the user.
     *
     * @param applications The list of current internship applications.
     * @param ui The user interface to display the filtered list.
     * @param storage The storage handler (not used for filtering).
     * @throws InternTrackrException If the filter status is empty or invalid.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList cannot be null during execution";
        assert ui != null : "Ui cannot be null during execution";

        logger.log(Level.INFO, "Executing FilterCommand. isClear: " + isClear + ", status: " + status);

        if (isClear) {
            handleClearFilter(applications, ui);
            return;
        }

        if (this.status.isEmpty()) {
            logger.log(Level.WARNING, "Filter attempt with empty status.");
            throw new InternTrackrException("Filter status cannot be empty. Use 'filter clear' to reset.");
        }

        if (!Application.isValidStatus(this.status)) {
            logger.log(Level.WARNING, "Invalid filter status: " + this.status);
            throw new InternTrackrException("Invalid status assigned. Please use one of the following:\n"
                    + "Applied\n"
                    + "Pending\n"
                    + "Interview\n"
                    + "Offered\n"
                    + "Rejected\n"
                    + "Accepted");
        }

        String searchStatus = Application.getNormalizedStatus(this.status);
        ui.showMessage("Here are the applications with status: " + searchStatus);
        int matchCount = 0;
        int displayIndex = 1;

        for (int i = 1; i <= applications.getSize(); i++) {
            Application app = applications.getApplication(i);

            if (app != null && !app.isArchived()) {
                if (app.getStatus().equals(searchStatus)) {
                    ui.showMessage(displayIndex + ". " + app.toString());
                    matchCount++;
                }
                displayIndex++;
            }
        }

        if (matchCount == 0) {
            ui.showMessage("No applications found with status: " + searchStatus);
        }
    }

    /**
     * Clears any active filters and displays the full list of internship applications.
     *
     * @param applications The list of all internship applications.
     * @param ui The user interface to display the full list.
     */
    private void handleClearFilter(ApplicationList applications, Ui ui) {
        ui.showMessage("Filter cleared. Showing all applications:");
        int displayIndex = 1;
        for (int i = 1; i <= applications.getSize(); i++) {
            Application app = applications.getApplication(i);
            if (app != null && !app.isArchived()) {
                ui.showMessage(displayIndex + ". " + app.toString());
                displayIndex++;
            }
        }
    }
}
