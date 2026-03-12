package seedu.interntrackr.command;

import seedu.interntrackr.model.Application;
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
        Application newApp = new Application(company, role);
        applications.addApplication(newApp);

        ui.showMessage("Got it. I've added this application:");
        ui.showMessage("  " + newApp.toString());
        ui.showMessage("Now you have " + applications.getSize() + " application(s) in the list.");

        storage.save(applications.getApplications());
    }
}
