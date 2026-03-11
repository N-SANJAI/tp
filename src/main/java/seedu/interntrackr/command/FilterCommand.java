package seedu.interntrackr.command;

import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Filters applications based on status.
 */
public class FilterCommand extends Command {
    private String status;

    public FilterCommand(String status) {
        this.status = status;
    }

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        // TODO: Filter applications by status and display results
    }
}