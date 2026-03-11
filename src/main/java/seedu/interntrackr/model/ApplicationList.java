package seedu.interntrackr.model;

import java.util.ArrayList;

/**
 * Represents the list of all internship applications.
 */
public class ApplicationList {
    private ArrayList<Application> applications;

    public ApplicationList() {
        this.applications = new ArrayList<>();
    }

    public ApplicationList(ArrayList<Application> applications) {
        this.applications = applications;
    }

    // TODO: Add methods like addApplication(), deleteApplication(), getApplication()
}