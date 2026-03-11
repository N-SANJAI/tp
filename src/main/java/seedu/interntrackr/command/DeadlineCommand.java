package seedu.interntrackr.command;

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
        // TODO: Set deadline for the specified application, show UI message, save to storage
    }
}