package seedu.interntrackr.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Deadline}.
 */
public class DeadlineTest {

    @Test
    public void constructor_withoutIsDone_defaultsToNotDone() {
        Deadline deadline = new Deadline("OA", LocalDate.of(2026, 3, 15));

        assertFalse(deadline.getIsDone());
    }

    @Test
    public void constructor_withIsDone_setsIsDoneCorrectly() {
        Deadline deadline = new Deadline("Interview", LocalDate.of(2026, 3, 20), true);

        assertTrue(deadline.getIsDone());
    }

    @Test
    public void setDone_deadlineInitiallyNotDone_setsIsDoneTrue() {
        Deadline deadline = new Deadline("OA", LocalDate.of(2026, 3, 15));

        deadline.setDone();

        assertTrue(deadline.getIsDone());
    }

    @Test
    public void setNotDone_deadlineInitiallyDone_setsIsDoneFalse() {
        Deadline deadline = new Deadline("OA", LocalDate.of(2026, 3, 15), true);

        deadline.setNotDone();

        assertFalse(deadline.getIsDone());
    }

    @Test
    public void toString_deadlineNotDone_returnsCorrectFormat() {
        Deadline deadline = new Deadline("OA", LocalDate.of(2026, 3, 15));

        assertEquals(
                "Deadline Type: OA | Due Date: 2026-03-15 | Done: [ ]",
                deadline.toString());
    }

    @Test
    public void toString_deadlineDone_returnsCorrectFormat() {
        Deadline deadline = new Deadline("OA", LocalDate.of(2026, 3, 15), true);

        assertEquals(
                "Deadline Type: OA | Due Date: 2026-03-15 | Done: [X]",
                deadline.toString());
    }

    @Test
    public void toStorageString_deadlineNotDone_returnsCorrectStorageFormat() {
        Deadline deadline = new Deadline("OA", LocalDate.of(2026, 3, 15));

        assertEquals("OA | 2026-03-15 | false", deadline.toStorageString());
    }

    @Test
    public void toStorageString_deadlineDone_returnsCorrectStorageFormat() {
        Deadline deadline = new Deadline("Interview", LocalDate.of(2026, 3, 20), true);

        assertEquals("Interview | 2026-03-20 | true", deadline.toStorageString());
    }

}
