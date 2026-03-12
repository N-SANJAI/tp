package seedu.interntrackr.command;

import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Adds an application deadline to an internship.
 */
public class DeadlineCommand extends Command {
    private int index;
    private String deadline;

    public DeadlineCommand(int index, String deadline) {
        this.index = index;
        this.deadline = deadline;
    }

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        if (index < 1 || index > applications.getSize()) {
            throw new InternTrackrException("Invalid application index.");
        }

        // Set deadline for the specified application
        // ApplicationList.getApplication() already handles the 0-based index conversion
        Application app = applications.getApplication(index);
        app.setDeadline(this.deadline);

        // Show UI message
        ui.showMessage("Deadline updated! Application deadline for" + app.getCompany() + "'s " + app.getRole() + " is now on the [" + this.deadline + "]");

        // Save to storage
        storage.save(applications.getApplications());
    }
}
