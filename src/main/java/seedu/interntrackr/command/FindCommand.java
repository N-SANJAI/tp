//@@author EmDani3l
package seedu.interntrackr.command;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.util.ArrayList;

/**
 * Represents a command to find and list all applications in the tracker whose
 * company name or role contains the specified keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Initializes a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    /**
     * Executes the find command by searching through the application list
     * for matching company names or roles.
     *
     * @param applications The list of applications to search.
     * @param ui The user interface to display results.
     * @param storage The storage handler (unused in this command).
     * @throws InternTrackrException If an error occurs during application retrieval.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        ArrayList<Application> matchingApps = new ArrayList<>();
        ArrayList<Integer> matchingIndices = new ArrayList<>();

        int displayIndex = 1;
        for (int i = 0; i < applications.getSize(); i++) {
            Application app = applications.getApplication(i + 1);

            if (!app.isArchived()) {
                boolean isMatch = app.getCompany().toLowerCase().contains(keyword)
                        || app.getRole().toLowerCase().contains(keyword);

                if (isMatch) {
                    matchingApps.add(app);
                    matchingIndices.add(displayIndex);
                }
                displayIndex++;
            }
        }

        if (matchingApps.isEmpty()) {
            ui.showMessage("No matching applications found.");
        } else {
            ui.showMessage("Here are the matching applications in your list:");
            for (int i = 0; i < matchingApps.size(); i++) {
                ui.showMessage(matchingIndices.get(i) + ". " + matchingApps.get(i).toString());
            }
        }
    }
}
