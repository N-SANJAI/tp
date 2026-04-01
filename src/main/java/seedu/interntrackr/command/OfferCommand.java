package seedu.interntrackr.command;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Updates an internship application with an offered salary and changes its status to "Offered".
 */
public class OfferCommand extends Command {
    private static final Logger logger = Logger.getLogger(OfferCommand.class.getName());
    private final int index;
    private final double salary;

    /**
     * Constructs an OfferCommand with the specified application index and offered salary.
     *
     * @param index  The 1-based index of the application in the list.
     * @param salary The monetary value of the compensation package.
     */
    public OfferCommand(int index, double salary) {
        this.index = index;
        this.salary = salary;
    }

    /**
     * Executes the offer command.
     * Validates the index, updates the salary of the corresponding application,
     * auto-updates the status to "Offered" if necessary, and saves changes to storage.
     *
     * @param applications The list of current internship applications.
     * @param ui           The user interface to display success or error messages.
     * @param storage      The storage handler to save updated data.
     * @throws InternTrackrException If the index is out of bounds or saving fails.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList dependency is missing";
        assert ui != null : "Ui dependency is missing";
        assert storage != null : "Storage dependency is missing";

        logger.log(Level.INFO, "Executing OfferCommand for index " + index + " with salary: " + salary);

        if (index < 1 || index > applications.getSize()) {
            logger.log(Level.WARNING, "Offer update failed: index " + index + " out of bounds.");
            throw new InternTrackrException("Invalid application index. Please provide a number between 1 and "
                    + applications.getSize());
        }

        Application app = applications.getApplication(index);
        app.setSalary(salary);
        String previousStatus = app.getStatus();

        if (!previousStatus.equalsIgnoreCase("Offered")) {
            app.setStatus(Application.getNormalizedStatus("Offered"));
        }

        ui.showMessage("Offer updated successfully!");
        ui.showMessage("Salary logged: $" + String.format("%.2f", salary));

        if (!previousStatus.equalsIgnoreCase("Offered")) {
            ui.showMessage(app.getCompany() + " status auto-updated from [" + previousStatus + "] to [Offered].");
        }

        try {
            storage.save(applications.getApplications());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to save data after offer update.", e);
            throw new InternTrackrException("Offer updated locally, but failed to save to disk: " + e.getMessage());
        }
    }
}
