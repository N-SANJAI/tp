package seedu.interntrackr.command;

import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Updates the status of an internship application.
 */
public class StatusCommand extends Command {
    private int index;
    private String status;

    public StatusCommand(int index, String status) {
        this.index = index;
        this.status = status;
    }

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        // TODO: Update status of application, show UI message, save to storage
    }
}