package seedu.interntrackr;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.model.ApplicationList;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InternTrackrTest {

    @Test
    public void constructor_invalidFilePath_initializesEmptyList() throws Exception {
        InternTrackr tracker = new InternTrackr("non_existent_file.txt");

        Field field = InternTrackr.class.getDeclaredField("applications");
        field.setAccessible(true);
        ApplicationList applications = (ApplicationList) field.get(tracker);

        assertNotNull(applications);
        assertEquals(0, applications.getSize());
    }
}
