package seedu.interntrackr.command;

import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Displays a summary of all tracked internship applications, including a breakdown by status.
 */
public class OverviewCommand extends Command {
    private static final Logger logger = Logger.getLogger(OverviewCommand.class.getName());

    /**
     * Executes the overview command by printing the total number of tracked applications
     * and a breakdown of their current statuses.
     *
     * @param applications The current list of applications; must not be null.
     * @param ui The UI used to display output; must not be null.
     * @param storage Not used by this command; may be null.
     * @throws InternTrackrException Not thrown by this command, declared for interface compliance.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList must not be null";
        assert ui != null : "Ui must not be null";

        int size = applications.getSize();
        assert size >= 0 : "Application count must be non-negative";

        logger.info("Executing OverviewCommand. Application count: " + size);

        ui.showMessage("Overview:");

        if (size == 0) {
            ui.showMessage("You haven't tracked any applications yet. Start adding some!");
        } else {
            long activeCount = applications.getApplications().stream()
                    .filter(app -> !app.isArchived()).count();
            long archivedCount = size - activeCount;

            ui.showMessage("You are currently tracking " + size + " application(s) in total"
                    + " (" + activeCount + " active, " + archivedCount + " archived).");

            Map<String, Integer> statusCounts = new LinkedHashMap<>();
            for (String validStatus : Application.VALID_STATUSES) {
                statusCounts.put(validStatus, 0);
            }

            for (Application app : applications.getApplications()) {
                if (!app.isArchived()) {
                    String currentStatus = app.getStatus();
                    statusCounts.put(currentStatus, statusCounts.getOrDefault(currentStatus, 0) + 1);
                }
            }

            ui.showMessage("Active Status Breakdown:");
            boolean anyActive = false;
            for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
                if (entry.getValue() > 0) {
                    ui.showMessage(" - " + entry.getKey() + ": " + entry.getValue());
                    anyActive = true;
                }
            }
            if (!anyActive) {
                ui.showMessage(" - No active applications.");
            }
            ui.showMessage("Keep up the momentum!");
        }

        logger.fine("OverviewCommand executed successfully.");
    }
}
