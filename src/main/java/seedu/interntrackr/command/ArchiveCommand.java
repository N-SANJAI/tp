package seedu.interntrackr.command;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.util.logging.Logger;

/**
 * Archives an internship application, hiding it from the default list view.
 */
public class ArchiveCommand extends Command {
    private static final Logger logger = Logger.getLogger(ArchiveCommand.class.getName());

    private final int index;

    /**
     * Creates an ArchiveCommand targeting the application at the specified 1-based index.
     *
     * @param index The 1-based index of the application to archive.
     * @throws IllegalArgumentException If the index is not a positive integer.
     */
    public ArchiveCommand(int index) {
        if (index <= 0) {
            throw new IllegalArgumentException("Index must be a positive integer (1-based).");
        }
        this.index = index;
        logger.fine("ArchiveCommand created for index: " + index);
    }

    /**
     * Executes the archive command by marking the application at the stored index as archived.
     *
     * @param applications The current list of applications.
     * @param ui The UI object used to display output to the user.
     * @param storage The storage object used to persist the updated list.
     * @throws InternTrackrException If the index is out of range.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";
        assert index > 0 : "Index must be positive at execution time";

        if (index < 1 || index > applications.getSize()) {
            logger.warning("Archive index " + index + " out of range. List size: " + applications.getSize());
            throw new InternTrackrException("Invalid application index. Please provide a valid number.");
        }

        Application app = applications.getApplication(index);

        if (app.isArchived()) {
            logger.warning("Application at index " + index + " is already archived.");
            throw new InternTrackrException("This application is already archived.");
        }

        logger.info("Executing ArchiveCommand for index: " + index);
        app.setArchived(true);

        ui.showMessage("Got it. I've archived this application:");
        ui.showMessage("  " + app.toString());
        ui.showMessage("It will no longer appear in the default list. Use 'list archive' to view it.");

        storage.save(applications.getApplications());
        logger.fine("ArchiveCommand executed and saved for: " + app.getCompany());
    }
}
