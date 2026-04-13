package seedu.interntrackr.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import seedu.interntrackr.exception.InternTrackrException;

/**
 * Manages the in-memory list of internship applications.
 */
public class ApplicationList {
    private static final Logger logger = Logger.getLogger(ApplicationList.class.getName());

    private ArrayList<Application> applications;

    /**
     * Constructs an empty ApplicationList.
     */
    public ApplicationList() {
        this.applications = new ArrayList<>();
    }

    /**
     * Constructs an ApplicationList from an existing list.
     *
     * @param applications The pre-loaded list of applications.
     */
    public ApplicationList(ArrayList<Application> applications) {
        assert applications != null : "Applications list cannot be null";
        this.applications = applications;
    }

    /**
     * Adds a new application to the list.
     *
     * @param application The application to add.
     */
    public void addApplication(Application application) {
        assert application != null : "Application cannot be null";
        applications.add(application);
        logger.fine("Added application: " + application.getCompany());
    }

    /**
     * Deletes the application at the given 1-based index.
     *
     * @param index The 1-based index of the application to remove.
     * @throws InternTrackrException If the index is out of range.
     */
    public void deleteApplication(int index) throws InternTrackrException {
        if (index < 1 || index > applications.size()) {
            logger.warning("Invalid delete index: " + index);
            throw new InternTrackrException("Invalid index: " + index + ". Please enter a number between 1 and "
                    + applications.size() + ".");
        }
        logger.fine("Deleting application at index: " + index);
        applications.remove(index - 1);
    }

    /**
     * Returns the application at the given 1-based index (over the full backing list).
     *
     * @param index The 1-based index.
     * @return The Application object.
     * @throws InternTrackrException If the index is out of range.
     */
    public Application getApplication(int index) throws InternTrackrException {
        if (index < 1 || index > applications.size()) {
            logger.warning("Invalid get index: " + index);
            throw new InternTrackrException("Invalid index: " + index + ". Please enter a number between 1 and "
                    + applications.size() + ".");
        }
        return applications.get(index - 1);
    }

    /**
     * Returns the application corresponding to the given 1-based display index
     * among only the non-archived (active) entries.
     *
     * <p>Commands such as {@code archive}, {@code delete}, {@code status}, etc.
     * should use this method so that the index the user provides always matches
     * what they see in the output of {@code list}.</p>
     *
     * @param displayIndex The 1-based index as shown in the {@code list} output.
     * @return The matching active Application object.
     * @throws InternTrackrException If the display index is out of range.
     */
    public Application getActiveApplication(int displayIndex) throws InternTrackrException {
        int activeCount = 0;
        for (Application app : applications) {
            if (!app.isArchived()) {
                activeCount++;
                if (activeCount == displayIndex) {
                    return app;
                }
            }
        }
        int totalActive = countActive();
        logger.warning("Invalid active display index: " + displayIndex + ". Active count: " + totalActive);
        if (totalActive == 0) {
            if (applications.isEmpty()) {
                throw new InternTrackrException("No applications found. Start adding some!");
            } else {
                throw new InternTrackrException("No active applications. Use 'list archive' to view archived ones.");
            }
        }
        throw new InternTrackrException("Invalid application index. Please provide a number between 1 and "
                + totalActive + ".");
    }

    /**
     * Returns the application corresponding to the given 1-based display index
     * among only the archived entries.
     *
     * <p>The {@code unarchive} command should use this method so that the index
     * the user provides always matches what they see in the output of
     * {@code list archive}.</p>
     *
     * @param displayIndex The 1-based index as shown in the {@code list archive} output.
     * @return The matching archived Application object.
     * @throws InternTrackrException If the display index is out of range.
     */
    public Application getArchivedApplication(int displayIndex) throws InternTrackrException {
        int archivedCount = 0;
        for (Application app : applications) {
            if (app.isArchived()) {
                archivedCount++;
                if (archivedCount == displayIndex) {
                    return app;
                }
            }
        }
        int totalArchived = countArchived();
        logger.warning("Invalid archived display index: " + displayIndex + ". Archived count: " + totalArchived);
        if (totalArchived == 0) {
            throw new InternTrackrException("There are no archived applications.");
        }
        throw new InternTrackrException("Invalid application index. Please provide a number between 1 and "
                + totalArchived + ".");
    }

    /**
     * Returns the number of archived applications in the list.
     *
     * @return The number of archived applications.
     */
    public int countArchived() {
        int count = 0;
        for (Application app : applications) {
            if (app.isArchived()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the number of non-archived (active) applications in the list.
     *
     * @return The number of active applications.
     */
    public int countActive() {
        int count = 0;
        for (Application app : applications) {
            if (!app.isArchived()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the number of applications in the list (including archived).
     *
     * @return The size of the backing list.
     */
    public int getSize() {
        return applications.size();
    }

    /**
     * Returns an unmodifiable view of all applications.
     *
     * @return An unmodifiable list of applications.
     */
    public List<Application> getApplications() {
        return Collections.unmodifiableList(applications);
    }

    /**
     * Checks if the list already contains an application equivalent to the given one.
     *
     * @param toCheck The application to check for duplicates.
     * @return True if a duplicate exists, false otherwise.
     */
    public boolean hasApplication(Application toCheck) {
        for (int i = 0; i < applications.size(); i++) {
            if (applications.get(i).isSameApplication(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all application entries from the tracker.
     */
    public void clear() {
        applications.clear();
    }
}
