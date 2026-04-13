# InternTrackr User Guide

InternTrackr is a CLI-first internship application manager for university students applying to many internships. It helps you track where you applied, current status, and important dates in one place so you do not miss deadlines or lose track across spreadsheets, notes, and emails.

## Quick Start

1. Ensure you have **Java 17** or above installed.
2. Download the latest `internTrackr.jar`.
3. Copy the jar file into the folder you want to use as the home folder for Interntrackr.
4. Open a terminal, `cd` into that folder, and run:

   ```
   java -jar internTrackr.jar
   ```

   *(Optional: If you need to troubleshoot and view debug logs, append the logging flag: `java -jar internTrackr.jar --enable-logging`)*

5. Type commands and press Enter to execute.

Some commands you can try:

- `help` : Shows help.
- `add c/"Shopee" r/"Backend Intern"` : Adds a new application.
- `list` : Lists all applications.
- `list archive` : Lists all archived applications.
- `status 1 s/"Interview"` : Updates status of application 1.
- `archive 1` : Archives application 1.
- `unarchive 1` : Restores archived application 1 to the active list.
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
> - For fields containing spaces, you may optionally wrap them in quotes `"..."` for clarity.
> - Dates use the format `DD-MM-YYYY`.
> - **CRITICAL:** Do not use the pipe character (`|`) anywhere in your inputs. It is strictly prohibited and will cause an error.
> - Extra parameters for commands that do not take parameters (such as `help`, `overview`, `exit`) will be ignored.

### Viewing help : `help`

Shows a message explaining how to access the full online User Guide.

**Format:** `help`

**Example output:**
```
Need help? You can view the full User Guide with all commands here:
https://ay2526s2-cs2113-t14-1.github.io/tp/UserGuide.html
```

### Adding an application : `add`

Adds an internship application.

**Format:** `add c/COMPANY r/ROLE`

- Creates a new application with the given company and role.
- Prefixes can be provided in any order.
- Duplicate applications are not allowed.
- The count shown in the confirmation message reflects only active (non-archived) applications.

**Examples:**

- `add c/"Shopee" r/"Backend Intern"`
- `add r/"Data Analyst Intern" c/"Grab Health"`

### Listing all applications : `list`

Shows a list of all tracked internship applications.
If an application has a note, it will be displayed indented on the line
directly below that application's details.

**Format:** `list`

- Only active (non-archived) applications are shown.
  Use `list archive` to view archived applications.

**Example output:**
```
Here are your internship applications:
1. Company: Shopee | Role: Backend Intern | Status: Applied | ...
   Note: Strong Java skills required, prepare LeetCode
2. Company: Grab | Role: Data Analyst | Status: Interview | ...
```

### Deleting an application : `delete`

Deletes the specified active application, or optionally an archived application directly.

**Format:** `delete INDEX` or `delete archive INDEX`

- `delete INDEX` — deletes the active application at the given `INDEX`, as shown in the default `list` output.
- `delete archive INDEX` — deletes the archived application at the given `INDEX`, as shown in the `list archive` output. This allows you to permanently remove an archived application without first unarchiving it.
- `INDEX` must be a positive integer 1, 2, 3, ...

**Examples:**

- `delete 2`
- `delete archive 1`

### Updating application status : `status`

Updates the status of an existing application.

**Format:** `status INDEX s/STATUS`

- Updates the application at `INDEX`.
- `STATUS` should be one of the statuses supported (Applied, Pending, Interview, Offered, Rejected, Accepted).

**Examples:**

- `status 1 s/"Pending"`

**Example output:**
```
Status updated! Shopee is now marked as [Pending]
```

### Logging an offer : `offer`

Updates an application with an offered salary and automatically changes its status to "Offered".

**Format:** `offer INDEX s/SALARY`

- Updates the application at the given `INDEX`.
- Logs the `SALARY` for compensation tracking (must not contain more than 2 decimal places).

**Examples:**

- `offer 1 s/5000.00`
- `offer 2 s/4500`

### Archiving an application : `archive`

Archives an internship application so it no longer appears in the default active application list.

**Format:** `archive INDEX`

- Archives the application at the given `INDEX`.
- The index refers to the index number shown in the default application list.
- `INDEX` must be a positive integer 1, 2, 3, ...
- Archived applications are not deleted. They are hidden from the default `list` view and can still be viewed using `list archive`.

**Examples:**

- `archive 1`
- `archive 3`

### Restoring an archived application : `unarchive`

Restores an archived application back to the active list.

**Format:** `unarchive INDEX`

- Restores the application at the given `INDEX`.
- The index refers to the index number shown in the `list archive` output.
- `INDEX` must be a positive integer 1, 2, 3, ...
- The restored application will appear again in the default `list` output.

**Examples:**

- `unarchive 1`
- `unarchive 2`

### Listing archived applications : `list archive`

Shows all archived internship applications.

**Format:** `list archive`

- Displays only archived applications.
- Archived applications are shown using a separate index starting from 1 in the archived view.
- If an archived application has a note, the note will be displayed below it.

**Example output:**
```
Here are your archived internship applications:
1. Company: Shopee | Role: Backend Intern | Status: Rejected | ...
   Note: Rejected after OA
2. Company: Grab | Role: Data Analyst Intern | Status: Offered | ...
```

### Adding a contact : `contact`

Links recruiter or networking details to a specific internship application.

**Format:** `contact INDEX c/NAME e/EMAIL`

* The index refers to the index number shown in the displayed application list.
* `INDEX` must be a positive integer 1, 2, 3, ...
* **Note:** The contact name (`c/`) must be specified before the contact email (`e/`).

**Examples:**

- `contact 1 c/"John Doe" e/"john.doe@example.com"`
- `contact 3 c/"Jane Smith HR" e/"jane.smith@shopee.com"`

### Adding a note : `note`

Adds or updates a note for a specific internship application.
Notes are useful for recording interview impressions, tech stack requirements,
or any other application-specific insights.

**Format:** `note INDEX n/NOTE_CONTENT`

- Adds or **overwrites** the existing note for the application at `INDEX`.
- `INDEX` must be a positive integer 1, 2, 3, ...
- `NOTE_CONTENT` can contain any text including spaces and special characters,
  except the pipe character (`|`) which is reserved for internal storage.

**Examples:**

- `note 1 n/Strong Java skills required, prepare LeetCode`
- `note 2 n/Remote friendly, good culture fit`

> **Note:** Running `note` on an application that already has a note will
> **overwrite** the previous note entirely.

### Filtering by status : `filter`

Shows only applications that match a given status.

**Format:** `filter s/STATUS`

**Examples:**

- `filter s/"Pending"`

**Example output:**
```
Here are the applications with status: Pending
1. Company: Shopee | Role: Backend Intern | Status: Pending | ...
```

To clear the filter:

**Format:** `filter clear`

**Example output:**
```
Filter cleared. Showing all applications:
1. Company: Shopee | Role: Backend Intern | Status: Pending | ...
2. Company: Grab | Role: Data Analyst Intern | Status: Offered | ...
```
> **Note:** The `filter` command only affects the display. For all commands that take an `INDEX`
> (e.g., `status`, `contact`, `offer`, `archive`), the index always corresponds to the position
> shown in the default `list` output, not the filtered view.

### Finding applications : `find`

Finds applications whose company name or role contains the specified keyword.

**Format:** `find KEYWORD`

* The search is case-insensitive.
* Both the company name and the role are searched.

**Examples:**
* `find Shopee`
* `find Backend Intern`

**Example output:**
```
Here are the matching applications in your list:
1. Company: Shopee | Role: Backend Intern | Status: Applied | ...
```

### Adding a deadline : `deadline add`

Adds an important deadline linked to an application.

**Format:** `deadline add INDEX t/TYPE d/DATE [n/NOTES]`

- `INDEX` refers to the application's index number shown in the default application list.
- `INDEX` must be a positive integer 1, 2, 3, ...
- `TYPE` describes the deadline type.
- `DATE` must be in `DD-MM-YYYY`.

> **Note:** One application can contain multiple deadlines.
> Running `deadline add` on an application that already has deadline(s) linked to it will
> **not** overwrite the previous deadlines.

**Examples:**

- `deadline add 1 t/Submission d/01-03-2026`
- `deadline add 2 t/OfferExpiry d/10-03-2026 n/"Need to reply by email"`

### Listing deadlines : `deadline list`

Shows a list of all the deadlines linked to a specific application.

**Format:** `deadline list INDEX`

- `INDEX` refers to the application's index number shown in the default application list.
- `INDEX` must be a positive integer 1, 2, 3, ...

**Example:**

- `deadline list 1`

**Example output:**
```
Here are the deadlines for this application:
1. Deadline Type: Submission | Due Date: 2026-03-01 | Done: [ ]
2. Deadline Type: OfferExpiry | Due Date: 2026-03-10 | Done: [ ]
```

### Marking a deadline as done : `deadline done`

Marks a specific deadline as completed.

**Format:** `deadline done INDEX i/DEADLINE_INDEX`

- `INDEX` refers to the application's index number shown in the default application list.
- `DEADLINE_INDEX` refers to the deadline number shown in `deadline list`.
- `INDEX` must be a positive integer 1, 2, 3, ...
- `DEADLINE_INDEX` must be a positive integer 1, 2, 3, ...

**Example flow:**

```
deadline list 1
deadline done 1 i/1
```

### Viewing overview : `overview`

Shows a quantitative summary of your internship applications and current statuses.

**Format:** `overview`

Overview includes:

- Total number of tracked applications (active and archived).
- Count of active applications broken down by their current status.

### Clearing all data : `clear`

Clears all internship applications from the list.

**Format:** `clear`

* You will be prompted to type `yes` to confirm the action.
* This action is irreversible and wipes all saved data.

### Exiting the program : `exit`

Exits InternTrackr.

**Format:** `exit`

## Saving the Data

InternTrackr data is saved automatically after any command that changes the data. There is no need to save manually.

## Editing the Data File

InternTrackr data is stored as a file in the home folder (for example, under a `data/` folder).

> **Caution:** If you edit the data file incorrectly and make the format invalid, InternTrackr may discard the data file and start with an empty one. Make a backup before editing.

## Command Summary

| Action                       | Format                                       | Example                                                 |
|------------------------------|----------------------------------------------|---------------------------------------------------------|
| Help                         | `help`                                       | `help`                                                  |
| Add application              | `add c/COMPANY r/ROLE`                       | `add c/Shopee r/Backend Intern`                         |
| List applications            | `list`                                       | `list`                                                  |
| List archived applications   | `list archive`                               | `list archive`                                          |
| Find applications            | `find KEYWORD`                               | `find Shopee`                                           |
| Delete application           | `delete INDEX` or `delete archive INDEX`     | `delete 2` or `delete archive 1`                        |
| Update status                | `status INDEX s/STATUS`                      | `status 1 s/Interview`                                  |
| Log an offer                 | `offer INDEX s/SALARY`                       | `offer 1 s/5000.00`                                     |
| Archive application          | `archive INDEX`                              | `archive 1`                                             |
| Restore archived application | `unarchive INDEX`                            | `unarchive 1`                                           |
| Add contact                  | `contact INDEX c/NAME e/EMAIL`               | `contact 1 c/John Doe e/john.doe@example.com`           |
| Add note                     | `note INDEX n/NOTE_CONTENT`                  | `note 1 n/Remember to review OOP concepts`              |
| Filter by status             | `filter s/STATUS`                            | `filter s/Pending`                                      |
| Clear filter                 | `filter clear`                               | `filter clear`                                          |
| Add deadline                 | `deadline add INDEX t/TYPE d/DATE [n/NOTES]` | `deadline add 1 t/Submission d/01-03-2026 n/Need to reply by email` |
| List deadlines               | `deadline list INDEX`                        | `deadline list 1`                                       |
| Mark deadline as done        | `deadline done INDEX i/DEADLINE_INDEX`       | `deadline done 1 i/2`                                   |
| Overview                     | `overview`                                   | `overview`                                              |
| Clear all data               | `clear`                                      | `clear`                                                 |
| Exit                         | `exit`                                       | `exit`                                                  |
