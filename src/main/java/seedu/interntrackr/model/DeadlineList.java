package seedu.interntrackr.model;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.List;

public class DeadlineList {

    private static final Logger logger = Logger.getLogger(DeadlineList.class.getName());

    private ArrayList<Deadline> deadlines;

    /**
     * Constructs an empty DeadlineList.
     */
    public DeadlineList() {
        this.deadlines = new ArrayList<>();
    }

    /**
     * Constructs an DeadlineList from an existing list.
     *
     * @param deadlines The preloaded list of deadlines.
     */
    public DeadlineList(ArrayList<Deadline> deadlines) {
        assert deadlines != null : "Deadlines list cannot be null";
        this.deadlines = deadlines;
    }

    /**
     * Adds a new deadline to the list.
     *
     * @param deadline The deadline to add.
     */
    public void addDeadline(Deadline deadline) {
        assert deadline != null : "Deadline cannot be null";
        deadlines.add(deadline);
        logger.fine("Added deadline: " + deadline.getDeadlineType() + " | " + deadline.getDueDate());
    }

    /**
     * Returns the number of deadlines in the list.
     *
     * @return The size of the list.
     */
    public int getSize() {
        return deadlines.size();
    }

    /**
     * Returns the deadline at the specified 1-based index.
     *
     * @param index The 1-based index of the deadline.
     * @return The deadline at the given index.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Deadline getDeadline(int index) {
        assert index > 0 && index <= deadlines.size() : "Deadline index out of bounds";
        return deadlines.get(index - 1);
    }

    /**
     * Returns an unmodifiable view of all deadlines.
     *
     * @return An unmodifiable list of deadlines.
     */
    public List<Deadline> getDeadlines() {
        return Collections.unmodifiableList(deadlines);
    }

    /**
     * Returns a formatted string representation of the deadlines assigned to this application.
     *
     * @return A human-readable string representation of this list of deadlines.
     */
    @Override
    public String toString() {
        if (deadlines.isEmpty()) {
            return "-";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < deadlines.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(deadlines.get(i).getDeadlineType())
                    .append(" (")
                    .append(deadlines.get(i).getDueDate())
                    .append(")")
                    .append(deadlines.get(i).getIsDone() ? " [Done]" : " [Pending]");
        }
        return sb.toString();
    }
}
