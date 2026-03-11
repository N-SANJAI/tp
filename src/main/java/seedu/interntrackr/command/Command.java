package seedu.interntrackr.command;

import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Represents an executable command.
 */
public abstract class Command {
    /**
     * Executes the command using the provided application list, UI, and storage.
     *
     * @param applications The list of internship applications.
     * @param ui The user interface for interacting with the user.
     * @param storage The storage for saving data.
     * @throws InternTrackrException If an error occurs during execution.
     */
    public abstract void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException;

    /**
     * Returns true if the command should exit the application.
     *
     * @return True if the application should exit, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}