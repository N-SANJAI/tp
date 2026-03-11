package seedu.interntrackr.command;

import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Adds a new internship application to the tracker.
 */
public class AddCommand extends Command {
    private String company;
    private String role;

    public AddCommand(String company, String role) {
        this.company = company;
        this.role = role;
    }

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        // TODO: Create an Application object, add it to applications, show UI message, save to storage
    }
}