package seedu.interntrackr.model;

/**
 * Represents a single deadline.
 */
public class Deadline {
    private String category;
    private String dueDate;
    private boolean isDone;

    /**
     * Constructs a Deadline with the given category, dueDate.
     *
     * @param category The category of the deadline.
     * @param dueDate  The due date of the deadline.
     */
    public Deadline(String category, String dueDate) {
        this.category = category;
        this.dueDate = dueDate;
        this.isDone = false;
    }

    /**
     * Constructs a Deadline with the given category, dueDate, isDone.
     *
     * @param category The category of the deadline.
     * @param dueDate  The due date of the deadline.
     * @param isDone   The completeness of the deadline.
     */
    public Deadline(String category, String dueDate, boolean isDone) {
        this.category = category;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    /**
     * Returns the completeness of this deadline.
     *
     * @return The deadline's isDone field.
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Sets the deadline as completed.
     *
     * @return The deadline's isDone field as true.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Sets the deadline as not completed.
     *
     * @return The deadline's isDone field as false.
     */
    public void setNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a formatted string representation of this deadline's completeness.
     *
     * @return A human-readable string of the deadline's isDone field.
     */
    private String markIsDone() {
        return this.isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns a formatted string representation of this deadline.
     *
     * @return A human-readable string.
     */
    @Override
    public String toString() {
        return "Category: " + this.category + " | Due Date: " + this.dueDate + " | Done: " + markIsDone();
    }

    /**
     * Returns a pipe-delimited string for saving to the storage file.
     *
     * @return A storage-formatted string.
     */
    public String toStorageString() {
        return this.category + " | " + this.dueDate + " | " + this.isDone;
    }
}
