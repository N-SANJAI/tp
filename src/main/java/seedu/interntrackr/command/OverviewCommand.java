package seedu.interntrackr.command;

import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

/**
 * Displays a summary of all tracked internship applications.
 */
public class OverviewCommand extends Command {

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) {
        // TODO: Tally statuses (e.g., how many 'Pending', how many 'Interview')
        // TODO: Find the soonest upcoming deadline
    }
}