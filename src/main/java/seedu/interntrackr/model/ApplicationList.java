package seedu.interntrackr.model;

import seedu.interntrackr.exception.InternTrackrException;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.List;

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
     * Returns the application at the given 1-based index.
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
     * Returns the number of applications in the list.
     *
     * @return The size of the list.
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
