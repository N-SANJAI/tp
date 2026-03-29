package seedu.interntrackr.command;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearCommandTest {

    @Test
    public void execute_confirmedWithYes_listCleared() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "SWE"));
        Storage storage = new Storage("data/test_clear.txt");

        // Simulate user typing "yes"
        InputStream in = new ByteArrayInputStream("yes\n".getBytes());
        System.setIn(in);
        Ui ui = new Ui();

        new ClearCommand().execute(applications, ui, storage);

        assertEquals(0, applications.getSize());
    }

    @Test
    public void execute_cancelledWithNo_listNotCleared() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "SWE"));
        Storage storage = new Storage("data/test_clear.txt");

        // Simulate user typing "no"
        InputStream in = new ByteArrayInputStream("no\n".getBytes());
        System.setIn(in);
        Ui ui = new Ui();

        new ClearCommand().execute(applications, ui, storage);

        assertEquals(1, applications.getSize());
    }
}
