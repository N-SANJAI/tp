package seedu.interntrackr.command;

import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Deletes an existing internship application from the tracker.
 */
public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        if (index < 1 || index > applications.getSize()) {
            throw new InternTrackrException("Invalid application index. Please provide a valid number.");
        }

        Application appToRemove = applications.getApplication(index);
        applications.deleteApplication(index);

        ui.showMessage("Noted. I've removed this application:");
        ui.showMessage("  " + appToRemove.toString());
        ui.showMessage("Now you have " + applications.getSize() + " application(s) in the list.");

        storage.save(applications.getApplications());
    }
}
