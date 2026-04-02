# InternTrackr User Guide

InternTrackr is a CLI-first internship application manager for university students applying to many internships. It helps you track where you applied, current status, and important dates in one place so you do not miss deadlines or lose track across spreadsheets, notes, and emails.

## Quick Start

1. Ensure you have **Java 17** or above installed.
2. Download the latest `InternTrackr.jar`.
3. Copy the jar file into the folder you want to use as the home folder for InternTrackr.
4. Open a terminal, `cd` into that folder, and run:

   ```
   java -jar InternTrackr.jar
   ```

5. Type commands and press Enter to execute.

Some commands you can try:

- `help` : Shows help.
- `add c/"Shopee" r/"Backend Intern"` : Adds a new application.
- `list` : Lists all applications.
- `status 1 s/"Interview"` : Updates status of application 1.
- `exit` : Exits the app.

Refer to the [Features](#features) section below for details.

## Features

> **Notes about the command format:**
>
> - Words in `UPPER_CASE` are parameters to be supplied by you.
    >   - Example: in `add c/COMPANY`, `COMPANY` can be `add c/"Shopee"` ...
> - Items in square brackets are optional.
    >   - Example: `deadline add ID t/TYPE d/DATE [n/NOTES]`
> - Parameters can be in any order unless stated otherwise.
> - For fields containing spaces, wrap them in quotes `"..."`.
> - Dates use the format `DD-MM-YYYY`.
> - Extra parameters for commands that do not take parameters (such as `help`, `list`, `overview`, `exit`) will be ignored.

### Viewing help : `help`

Shows a message explaining how to access the help page.

**Format:** `help`

### Adding an application : `add`

Adds an internship application.

**Format:** `add c/COMPANY r/ROLE`

**Examples:**

- `add c/"Shopee" r/"Backend Intern"`
- `add r/"Data Analyst Intern" c/"Grab Health"`

### Listing all applications : `list`

Shows a list of all applications.

**Format:** `list`

### Deleting an application : `delete`

Deletes the specified application.

**Format:** `delete INDEX`

- Deletes the application at the given `INDEX`.
- The index refers to the index number shown in the displayed application list.
- `INDEX` must be a positive integer 1, 2, 3, ...

**Examples:**

- `delete 2`

### Updating application status : `status`

Updates the status of an existing application.

**Format:** `status INDEX s/STATUS`

- Updates the application at `INDEX`.
- `STATUS` should be one of the statuses supported (Applied, Pending, Interview, Offered, Rejected).

**Examples:**

- `status 1 s/"Pending"`
- `status 3 s/"Interview"`

### Filtering by status : `filter`

Shows only applications that match a given status.

**Format:** `filter s/STATUS`

**Examples:**

- `filter s/"Pending"`
- `filter s/"Interview"`

To clear the filter:

**Format:** `filter clear`

### Adding a deadline : `deadline add`

Adds an important date linked to an application (deadline, submission date, offer expiry).

**Format:** `deadline add INDEX t/TYPE d/DATE [n/NOTES]`

- `TYPE` describes the deadline type (example values: Submission, OfferExpiry).
- `DATE` must be in `DD-MM-YYYY`.

**Examples:**

- `deadline add 1 t/Submission d/01-03-2026`
- `deadline add 2 t/OfferExpiry d/10-03-2026 n/"Need to reply by email"`

### Listing deadlines : `deadline list`

Shows deadlines linked to a specific application.

**Format:** `deadline list INDEX`

**Example:**

- `deadline list 1`

### Marking a deadline as done : `deadline done`

Marks a specific deadline as completed.

**Format:** `deadline done INDEX i/DEADLINE_INDEX`

- `DEADLINE_INDEX` refers to the deadline number shown in `deadline list`.

**Example flow:**

```
deadline list 1
deadline done 1 i/1
```

### Viewing overview : `overview`

Shows a quick summary of your internship applications.

**Format:** `overview`

Overview includes:

- Total number of applications
- Count by status
- Upcoming deadlines sorted by soonest first

### Exiting the program : `exit`

Exits InternTrackr.

**Format:** `exit`

## Saving the Data

InternTrackr data is saved automatically after any command that changes the data. There is no need to save manually.

## Editing the Data File

InternTrackr data is stored as a file in the home folder (for example, under a `data/` folder).

> **Caution:** If you edit the data file incorrectly and make the format invalid, InternTrackr may discard the data file and start with an empty one. Make a backup before editing.

## Command Summary

| Action | Format | Example |
|---|---|---|
| Help | `help` | `help` |
| Add application | `add c/COMPANY r/ROLE` | `add c/"Shopee" r/"Backend Intern"` |
| List applications | `list` | `list` |
| Delete application | `delete INDEX` | `delete 2` |
| Update status | `status INDEX s/STATUS` | `status 1 s/"Interview"` |
| Filter by status | `filter s/STATUS` | `filter s/"Pending"` |
| Clear filter | `filter clear` | `filter clear` |
| Add deadline | `deadline add INDEX t/TYPE d/DATE [n/NOTES]` | `deadline add 1 t/Submission d/01-03-2026 n/"Need to reply by email"` |
| List deadlines | `deadline list INDEX` | `deadline list 1` |
| Mark deadline done | `deadline done INDEX i/DEADLINE_INDEX` | `deadline done 1 i/1` |
| Overview | `overview` | `overview` |
| Exit | `exit` | `exit` |