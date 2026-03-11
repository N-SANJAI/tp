package seedu.interntrackr.storage;

import seedu.interntrackr.model.Application;
import seedu.interntrackr.exception.InternTrackrException;
import java.util.ArrayList;

/**
 * Handles saving and loading of application data to a local text file.
 */
public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Application> load() throws InternTrackrException {
        // TODO: Read from text file
        return new ArrayList<>();
    }

    public void save(ArrayList<Application> applications) throws InternTrackrException {
        // TODO: Write to text file
    }
}