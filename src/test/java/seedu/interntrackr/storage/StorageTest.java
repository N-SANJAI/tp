package seedu.interntrackr.storage;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.Deadline;
import seedu.interntrackr.model.DeadlineList;

import java.time.LocalDate;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StorageTest {

    /**
     * Returns a temporary file path for testing purposes.
     *
     * @return A string file path in the system temp directory.
     */
    private String getTempFilePath() {
        return System.getProperty("java.io.tmpdir") + "/test_interntrackr.txt";
    }

    @Test
    void saveAndLoad_validData_success() throws InternTrackrException {
        Storage storage = new Storage(getTempFilePath());

        ArrayList<Application> toSave = new ArrayList<>();
        toSave.add(new Application("Shopee", "Backend Intern", "Applied",
                "Jane Tan", "jane@shopee.com"));
        toSave.add(new Application("Google", "SWE Intern", "Interview",
                "Alex Lim", "alex@google.com"));

        storage.save(toSave);
        ArrayList<Application> loaded = storage.load();

        assertEquals(2, loaded.size());

        assertEquals("Shopee", loaded.get(0).getCompany());
        assertEquals("Backend Intern", loaded.get(0).getRole());
        assertEquals("Applied", loaded.get(0).getStatus());
        assertEquals("Jane Tan", loaded.get(0).getContactName());
        assertEquals("jane@shopee.com", loaded.get(0).getContactEmail());

        assertEquals("Google", loaded.get(1).getCompany());
        assertEquals("SWE Intern", loaded.get(1).getRole());
        assertEquals("Interview", loaded.get(1).getStatus());
        assertEquals("Alex Lim", loaded.get(1).getContactName());
        assertEquals("alex@google.com", loaded.get(1).getContactEmail());
    }

    @Test
    void saveAndLoad_applicationWithDeadlines_success() throws InternTrackrException {
        Storage storage = new Storage(getTempFilePath());

        ArrayList<Application> toSave = new ArrayList<>();

        DeadlineList deadlines = new DeadlineList();
        deadlines.addDeadline(new Deadline("OA", LocalDate.of(2026, 4, 1), false));
        deadlines.addDeadline(new Deadline("Interview", LocalDate.of(2026, 4, 10), true));

        Application app = new Application("Meta", "Data Intern", "Applied",
                "Chris Tan", "chris@meta.com", deadlines);
        toSave.add(app);

        storage.save(toSave);
        ArrayList<Application> loaded = storage.load();

        assertEquals(1, loaded.size());

        Application loadedApp = loaded.get(0);
        assertEquals("Meta", loadedApp.getCompany());
        assertEquals("Data Intern", loadedApp.getRole());
        assertEquals("Applied", loadedApp.getStatus());
        assertEquals("Chris Tan", loadedApp.getContactName());
        assertEquals("chris@meta.com", loadedApp.getContactEmail());

        assertEquals(2, loadedApp.getDeadlines().getSize());
        assertEquals("OA", loadedApp.getDeadlines().getDeadlines().get(0).getDeadlineType());
        assertEquals(LocalDate.of(2026, 4, 1), loadedApp.getDeadlines().getDeadlines().get(0).getDueDate());
        assertEquals(false, loadedApp.getDeadlines().getDeadlines().get(0).getIsDone());

        assertEquals("Interview", loadedApp.getDeadlines().getDeadlines().get(1).getDeadlineType());
        assertEquals(LocalDate.of(2026, 4, 10), loadedApp.getDeadlines().getDeadlines().get(1).getDueDate());
        assertEquals(true, loadedApp.getDeadlines().getDeadlines().get(1).getIsDone());
    }

    @Test
    void load_nonExistentFile_returnsEmptyList() throws InternTrackrException {
        Storage storage = new Storage("data/nonexistent_xyz_file.txt");
        ArrayList<Application> result = storage.load();
        assertTrue(result.isEmpty());
    }

    @Test
    void saveAndLoad_emptyList_returnsEmptyList() throws InternTrackrException {
        Storage storage = new Storage(getTempFilePath());

        storage.save(new ArrayList<>());
        ArrayList<Application> loaded = storage.load();

        assertTrue(loaded.isEmpty());
    }

    @Test
    void load_corruptedData_throwsInternTrackrException() throws InternTrackrException {
        // Write a corrupted line directly to the temp file
        String path = System.getProperty("java.io.tmpdir") + "/corrupted_test.txt";
        Storage storage = new Storage(path);

        // Save a valid app first then manually corrupt the file
        ArrayList<Application> bad = new ArrayList<>();
        bad.add(new Application("OnlyCompany", "missingStatus"));
        storage.save(bad);

        // Now overwrite file with corrupted content
        try {
            java.io.FileWriter fw = new java.io.FileWriter(path);
            fw.write("corruptedlinewithoutseparators\n");
            fw.close();
        } catch (java.io.IOException e) {
            // ignore for test setup
        }

        assertThrows(InternTrackrException.class, () -> storage.load());
    }

    @Test
    void load_invalidDeadlineDate_throwsInternTrackrException() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + "/invalid_deadline_date_test.txt";
        Storage storage = new Storage(path);

        try (java.io.FileWriter fw = new java.io.FileWriter(path)) {
            fw.write("Shopee | Backend Intern | Applied | Jane Tan | jane@shopee.com | OA | not-a-date | false\n");
        }

        assertThrows(InternTrackrException.class, storage::load);
    }

    @Test
    void save_createsFileIfNotExists() throws InternTrackrException {
        String path = System.getProperty("java.io.tmpdir") + "/newfile_test.txt";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }

        Storage storage = new Storage(path);
        ArrayList<Application> list = new ArrayList<>();
        list.add(new Application("Meta", "Data Intern", "Applied"));
        storage.save(list);

        assertTrue(file.exists());
    }
}
