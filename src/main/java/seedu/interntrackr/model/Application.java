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
    private DeadlineList deadlines;
    private String contactName;
    private String contactEmail;

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
        this.contactName = null;
        this.contactEmail = null;
        this.deadlines = new DeadlineList();
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
        this.contactName = null;
        this.contactEmail = null;
        this.deadlines = new DeadlineList();
        logger.fine("Created application: " + company + " | " + role + " | " + status);
    }

    /**
     * Constructs an Application with the given company, role, status, and contact name.
     *
     * @param company      The name of the company.
     * @param role         The role applied for.
     * @param status       The current application status.
     * @param contactName  The contact name of this application.
     */
    public Application(String company, String role, String status, String contactName) {
        assert company != null && !company.isEmpty() : "Company name cannot be null or empty";
        assert role != null && !role.isEmpty() : "Role cannot be null or empty";
        assert status != null && !status.isEmpty() : "Status cannot be null or empty";
        this.company = company;
        this.role = role;
        this.status = status;
        this.contactName = contactName;
        this.contactEmail = null;
        this.deadlines = new DeadlineList();
        logger.fine("Created application with contact name: " + company +
                " | " + role +
                " | " + status +
                " | " + contactName);
    }

    /**
     * Constructs an Application with the given company, role, status, contact name, and contact email.
     *
     * @param company      The name of the company.
     * @param role         The role applied for.
     * @param status       The current application status.
     * @param contactName  The contact name of this application.
     * @param contactEmail The contact email for this application.
     */
    public Application(String company, String role, String status, String contactName, String contactEmail) {
        assert company != null && !company.isEmpty() : "Company name cannot be null or empty";
        assert role != null && !role.isEmpty() : "Role cannot be null or empty";
        assert status != null && !status.isEmpty() : "Status cannot be null or empty";
        this.company = company;
        this.role = role;
        this.status = status;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.deadlines = new DeadlineList();
        logger.fine("Created application with contact name and contact email: " + company +
                " | " + role +
                " | " + status +
                " | " + contactName +
                " | " + contactEmail);
    }

    /**
     * Constructs an Application with the given company, role, status, contact name, contact email, and deadlines.
     *
     * @param company      The name of the company.
     * @param role         The role applied for.
     * @param status       The current application status.
     * @param contactName  The contact name of this application.
     * @param contactEmail The contact email for this application.
     * @param deadlines    The deadlines of this application.
     */
    public Application(String company, String role, String status,
                       String contactName, String contactEmail, DeadlineList deadlines) {
        assert company != null && !company.isEmpty() : "Company name cannot be null or empty";
        assert role != null && !role.isEmpty() : "Role cannot be null or empty";
        assert status != null && !status.isEmpty() : "Status cannot be null or empty";
        this.company = company;
        this.role = role;
        this.status = status;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.deadlines = deadlines;
        logger.fine("Created application with deadlines: " + company +
                " | " + role +
                " | " + status +
                " | " + contactName +
                " | " + contactEmail +
                " | " + deadlines);
    }

    /**
     * Checks if the provided status string matches any of the allowed application statuses,
     * ignoring case sensitivity.
     *
     * @param status The status string to validate.
     * @return True if the status is valid, false otherwise.
     */
    public static boolean isValidStatus(String status) {
        for (String valid : VALID_STATUSES) {
            if (valid.equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the standardized (Proper Case) version of a valid status string.
     * If the status is invalid, it returns the original string.
     *
     * @param status The status string to normalize.
     * @return The normalized status string (e.g., "Applied" instead of "applied").
     */
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
     * Updates the status of the internship application.
     *
     * @param status The new status to be assigned.
     */
    public void setStatus(String status) {
        assert status != null && !status.isEmpty() : "Status cannot be null or empty";
        logger.fine("Updating status from " + this.status + " to " + status);
        this.status = status;
    }

    /**
     * Returns the contact name of this application.
     *
     * @return The contact name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Returns the contact email of this application.
     *
     * @return The contact email.
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the contact name and contact email of this application.
     *
     * @param contactName  The contact name.
     * @param contactEmail The contact email.
     */
    public void setContactDetails(String contactName, String contactEmail) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Returns the deadlines of this application.
     *
     * @return The deadlines.
     */
    public DeadlineList getDeadlines() {
        return deadlines;
    }

    /**
     * Sets the deadlines of this application.
     *
     * @param deadlines The new deadlines.
     */
    public void setDeadlines(DeadlineList deadlines) {
        this.deadlines = deadlines;
    }

    /**
     * Returns a formatted string representation of this application.
     *
     * @return A human-readable string.
     */
    @Override
    public String toString() {
        String contactName = (this.contactName == null) ? "-" : this.contactName;
        String contactEmail = (this.contactEmail == null) ? "-" : this.contactEmail;
        return "Company: " + company +
                " | Role: " + role +
                " | Status: " + status +
                " | Contact Name: " + contactName +
                " | Contact Email: " + contactEmail +
                " | Deadlines: " + deadlines;
    }

    /**
     * Returns a pipe-delimited string for saving to the storage file.
     *
     * @return A storage-formatted string.
     */
    public String toStorageString() {
        StringBuilder sb = new StringBuilder();
        sb.append(company).append(" | ")
                .append(role).append(" | ")
                .append(status).append(" | ")
                .append(contactName == null ? "-" : contactName).append(" | ")
                .append(contactEmail == null ? "-" : contactEmail);

        if (deadlines != null && deadlines.getSize() > 0) {
            for (Deadline deadline : deadlines.getDeadlines()) {
                sb.append(" | ").append(deadline.toStorageString());
            }
        }

        return sb.toString();
    }

    /**
     * Checks if this application is the same as another application.
     * Two applications are considered the same if they have the same company and role (case-insensitive).
     *
     * @param other The other application to compare with.
     * @return True if they represent the same internship, false otherwise.
     */
    public boolean isSameApplication(Application other) {
        if (other == this) {
            return true;
        }
        return other != null
                && other.getCompany().equalsIgnoreCase(this.getCompany())
                && other.getRole().equalsIgnoreCase(this.getRole());
    }
}
