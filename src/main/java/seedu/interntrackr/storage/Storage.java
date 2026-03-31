package seedu.interntrackr.storage;

import seedu.interntrackr.exception.InternTrackrException;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.Deadline;
import seedu.interntrackr.model.DeadlineList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.List;

/**
 * Handles reading from and writing to the local human-editable text file.
 */
public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());

    private String filePath;

    /**
     * Constructs a Storage object with the given file path.
     *
     * @param filePath Path to the data file.
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.isEmpty() : "File path cannot be null or empty";
        this.filePath = filePath;
    }

    /**
     * Loads applications from the data file on disk.
     *
     * @return ArrayList of Application objects.
     * @throws InternTrackrException If the file cannot be read or data is corrupted.
     */
    public ArrayList<Application> load() throws InternTrackrException {
        ArrayList<Application> applications = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            logger.info("No data file found at " + filePath + ". Starting fresh.");
            return applications;
        }

        logger.info("Loading data from " + filePath);
        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                lineNumber++;
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(" \\| ", -1);
                if (parts.length < 5) {
                    logger.warning("Corrupted data at line " + lineNumber + ": " + line);
                    throw new InternTrackrException("Corrupted data at line " + lineNumber + ": " + line);
                }

                if ((parts.length - 5) % 3 != 0) {
                    logger.warning("Corrupted data at line " + lineNumber + ": " + line);
                    throw new InternTrackrException("Corrupted data at line " + lineNumber + ": " + line);
                }

                String company = parts[0].trim();
                String role = parts[1].trim();
                String status = parts[2].trim();
                String contactName = parts[3].trim();
                String contactEmail = parts[4].trim();

                if (!Application.isValidStatus(status)) {
                    logger.warning("Invalid status at line " + lineNumber + ": " + status);
                    throw new InternTrackrException("Corrupted data at line " + lineNumber
                            + ": Invalid status '" + status + "'");
                }

                status = Application.getNormalizedStatus(status);

                DeadlineList deadlineList = new DeadlineList();

                if (parts.length > 5) {
                    try {
                        for (int i = 5; i < parts.length; i += 3) {
                            String deadlineType = parts[i].trim();
                            LocalDate dueDate = LocalDate.parse(parts[i + 1].trim());
                            boolean isDone = Boolean.parseBoolean(parts[i + 2].trim());

                            Deadline deadline = new Deadline(deadlineType, dueDate, isDone);
                            deadlineList.addDeadline(deadline);
                        }

                        applications.add(new Application(company, role, status,
                                contactName, contactEmail, deadlineList));
                        logger.fine("Loaded application with deadline at line " + lineNumber);
                    } catch (DateTimeParseException e) {
                        logger.warning("Invalid deadline date at line " + lineNumber + ": " + line);
                        throw new InternTrackrException("Corrupted deadline date at line "
                                + lineNumber + ": " + line);
                    }
                } else {
                    applications.add(new Application(company, role, status, contactName, contactEmail));
                    logger.fine("Loaded application without deadline at line " + lineNumber);
                }
            }
        } catch (IOException e) {
            logger.severe("Failed to read file: " + e.getMessage());
            throw new InternTrackrException("Error reading file: " + e.getMessage());
        }

        logger.info("Loaded " + applications.size() + " applications.");
        assert applications != null : "Loaded applications list should not be null";
        return applications;
    }

    /**
     * Saves the current list of applications to disk.
     *
     * @param applications The list to save.
     * @throws InternTrackrException If the file cannot be written.
     */
    public void save(List<Application> applications) throws InternTrackrException {
        assert applications != null : "Applications list cannot be null";
        logger.info("Saving " + applications.size() + " applications to " + filePath);
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            for (Application app : applications) {
                writer.write(app.toStorageString() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            logger.severe("Failed to save file: " + e.getMessage());
            throw new InternTrackrException("Error saving file: " + e.getMessage());
        }
        logger.info("Save successful.");
    }
}
