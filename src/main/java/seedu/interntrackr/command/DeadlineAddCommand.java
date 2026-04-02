//@@author eugenia-cnl-lee
package seedu.interntrackr.command;

import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.model.Deadline;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

/**
 * Adds an application deadline to an internship.
 */
public class DeadlineAddCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeadlineAddCommand.class.getName());

    private final int index;
    private final String deadlineType;
    private final LocalDate dueDate;

    /**
     * Constructs a DeadlineAddCommand for the specified application index, deadline type, and due date.
     *
     * @param index The 1-based index of the application.
     * @param deadlineType The type of the deadline.
     * @param dueDate The due date of the deadline.
     * @throws IllegalArgumentException If index is not positive or deadlineType is blank.
     * @throws NullPointerException If deadlineType or dueDate is null.
     */
    public DeadlineAddCommand(int index, String deadlineType, LocalDate dueDate) {
        if (index <= 0) {
            throw new IllegalArgumentException("Index must be a positive integer.");
        }

        Objects.requireNonNull(deadlineType, "Deadline type cannot be null");
        Objects.requireNonNull(dueDate, "Due date cannot be null");

        String trimmedDeadlineType = deadlineType.trim();
        if (trimmedDeadlineType.isEmpty()) {
            throw new IllegalArgumentException("Deadline type cannot be empty.");
        }

        this.index = index;
        this.deadlineType = trimmedDeadlineType;
        this.dueDate = dueDate;

        assert this.index > 0 : "Index should be positive";
        assert !this.deadlineType.isEmpty() : "Deadline type should not be empty";
        assert this.dueDate != null : "Due date should not be null";
    }

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "Application list should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        logger.info("Executing DeadlineAddCommand for application index " + index);

        if (index < 1 || index > applications.countActive()) {
            logger.warning("Invalid application index: " + index);
            throw new InternTrackrException("Invalid application index.");
        }

        // Creates a new Deadline instance
        Deadline newDeadline = new Deadline(deadlineType, dueDate);

        // Add deadline to the specified application's deadline list
        Application app = applications.getActiveApplication(index);
        app.getDeadlines().addDeadline(newDeadline);
        assert app.getDeadlines() != null : "Deadline list should not be null";

        // Show UI message
        ui.showMessage("Deadline updated! " + app.getCompany() + "'s " + app.getRole()
                + "'s " + this.deadlineType + " due date is now on the [" + this.dueDate + "]");

        // Save to storage
        storage.save(applications.getApplications());
        logger.info("Deadline saved successfully for application index " + index);
    }
}
