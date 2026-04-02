package seedu.interntrackr.command;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.util.logging.Logger;

/**
 * Adds or updates a note for a specific internship application.
 */
public class NoteCommand extends Command {
    private static final Logger logger = Logger.getLogger(NoteCommand.class.getName());

    private final int index;
    private final String note;

    /**
     * Constructs a NoteCommand with the given index and note content.
     *
     * @param index The 1-based index of the application to update.
     * @param note  The note content to set.
     */
    public NoteCommand(int index, String note) {
        assert index > 0 : "Index must be positive";
        assert note != null : "Note cannot be null";
        this.index = index;
        this.note = note;
    }

    /**
     * Executes the note command by updating the note of the specified application.
     *
     * @param applications The list of internship applications.
     * @param ui           The user interface for output.
     * @param storage      The storage handler.
     * @throws InternTrackrException If the index is out of range or storage fails.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";

        logger.info("Executing NoteCommand for index: " + index);

        Application app = applications.getActiveApplication(index);
        app.setNote(note);

        storage.save(applications.getApplications());

        ui.showMessage("Note updated for: " + app.getCompany() + " | " + app.getRole());
        ui.showMessage("  Note: " + note);

        logger.fine("Note set successfully for application at index: " + index);
    }
}
