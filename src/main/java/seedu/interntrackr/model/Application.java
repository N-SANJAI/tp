package seedu.interntrackr.model;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a single internship application.
 */
public class Application {
    public static final List<String> VALID_STATUSES = Arrays.asList(
            "Applied", "Pending", "Interview", "Offered", "Rejected", "Accepted"
    );
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    private String company;
    private String role;
    private String status;
    private Deadline deadline;

    /**
     * Constructs an Application with the given company and role.
     *
     * @param company The name of the company.
     * @param role    The role applied for.
     */
    public Application(String company, String role) {
        assert company != null && !company.isEmpty() : "Company name cannot be null or empty";
        assert role != null && !role.isEmpty() : "Role cannot be null or empty";
        this.company = company;
        this.role = role;
        this.status = "Applied";
        this.deadline = null;
        logger.fine("Created application: " + company + " | " + role);
    }

    /**
     * Constructs an Application with the given company, role, and status.
     *
     * @param company The name of the company.
     * @param role    The role applied for.
     * @param status  The current application status.
     */
    public Application(String company, String role, String status) {
        assert company != null && !company.isEmpty() : "Company name cannot be null or empty";
        assert role != null && !role.isEmpty() : "Role cannot be null or empty";
        assert status != null && !status.isEmpty() : "Status cannot be null or empty";
        this.company = company;
        this.role = role;
        this.status = status;
        this.deadline = null;
        logger.fine("Created application: " + company + " | " + role + " | " + status);
    }

    /**
     * Constructs an Application with the given company, role, status, and deadline.
     *
     * @param company  The name of the company.
     * @param role     The role applied for.
     * @param status   The current application status.
     * @param deadline The deadline of this application.
     */
    public Application(String company, String role, String status, Deadline deadline) {
        assert company != null && !company.isEmpty() : "Company name cannot be null or empty";
        assert role != null && !role.isEmpty() : "Role cannot be null or empty";
        assert status != null && !status.isEmpty() : "Status cannot be null or empty";
        this.company = company;
        this.role = role;
        this.status = status;
        this.deadline = deadline;
        logger.fine("Created application with deadline: " + company + " | " + role);
    }

    public static boolean isValidStatus(String status) {
        for (String valid : VALID_STATUSES) {
            if (valid.equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }

    public static String getNormalizedStatus(String status) {
        for (String valid : VALID_STATUSES) {
            if (valid.equalsIgnoreCase(status)) {
                return valid;
            }
        }
        return status;
    }

    /**
     * Returns the company name of this application.
     *
     * @return The company name.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Returns the role of this application.
     *
     * @return The role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns the status of this application.
     *
     * @return The status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this application.
     *
     * @param status The new status.
     */
    public void setStatus(String status) {
        assert status != null && !status.isEmpty() : "Status cannot be null or empty";
        logger.fine("Updating status from " + this.status + " to " + status);
        this.status = status;
    }

    /**
     * Returns the deadline of this application.
     *
     * @return The deadline.
     */
    public Deadline getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline of this application.
     *
     * @param deadline The new deadline.
     */
    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns a formatted string representation of this application.
     *
     * @return A human-readable string.
     */
    @Override
    public String toString() {
        return "Company: " + company + " | Role: " + role + " | Status: " + status + " | Deadline: " + deadline;
    }

    /**
     * Returns a pipe-delimited string for saving to the storage file.
     *
     * @return A storage-formatted string.
     */
    public String toStorageString() {
        return company + " | " + role + " | " + status + " | " + deadline;
    }
}
