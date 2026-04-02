package seedu.interntrackr.command;

import java.util.logging.Logger;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.model.DeadlineList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

/**
 * Lists deadlines linked to a specific application.
 */
public class DeadlineListCommand extends Command {
    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());

    private final int index;

    public DeadlineListCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the deadline list command and displays deadlines for the specified application.
     *
     * @param applications           The list of internship applications.
     * @param ui                     The user interface for output.
     * @param storage                The storage handler.
     * @throws InternTrackrException If an error occurs during execution.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList cannot be null";
        assert ui != null : "Ui cannot be null";

        if (index < 1 || index > applications.countActive()) {
            throw new InternTrackrException("Invalid application index.");
        }

        Application app = applications.getActiveApplication(index);
        DeadlineList deadlines = app.getDeadlines();

        assert deadlines != null : "DeadlineList cannot be null";
        logger.info("Executing DeadlineListCommand. Total deadlines assigned to this application: "
                + deadlines.getSize());

        if (deadlines.getSize() == 0) {
            ui.showMessage("No deadlines found for this application.");
            return;
        }
        ui.showMessage("Here are the deadlines for this application:");
        for (int i = 0; i < deadlines.getSize(); i++) {
            ui.showMessage((i + 1) + ". " + deadlines.getDeadlines().get(i).toString());
        }
        logger.fine("Listed " + deadlines.getSize() + " deadlines successfully.");
    }
}
