# Shyamal Varnan - Project Portfolio Page

## Overview

**InternTrackr** is a CLI-first internship application manager for university students
applying to multiple internships. It helps users track where they applied, current
statuses, and important dates in one place so they do not miss deadlines or lose track
across spreadsheets, notes, and emails.

## Summary of Contributions

### 1. New Features

* **Application Data Foundation (`Application`, `ApplicationList`)**: Designed and
  implemented the core data model classes that underpin the entire application.
  `Application` stores all fields (company, role, status, deadlines, contact, salary,
  note) with full validation via assertions and logging. `ApplicationList` manages the
  in-memory list with 1-based indexing, bounds-checked access, and an unmodifiable list
  view to prevent external mutation.

* **Persistent Storage (`Storage`)**: Implemented the human-editable file-based
  persistence layer. `Storage#save()` serializes all application data into a
  pipe-delimited text file, while `Storage#load()` reconstructs the full application
  state — including deadlines and notes — on startup. The load logic is split into
  focused helper methods (`parseLine`, `parseStatus`, `parseSalary`, `parseNote`,
  `parseDeadlines`) following SLAP principles. Corrupted data is caught and reported
  with precise line numbers.

* **List Command (`list`)**: Implemented the `list` command to display all tracked
  active applications. Enhanced in v2.0 to display notes indented below each application
  entry when present, keeping the view clean for applications without notes. Only
  non-archived applications are shown.

* **Note Command (`note`)**: Implemented the `note` command (v2.0) allowing users to
  attach free-text insights to any application — such as interview impressions, tech
  stack requirements, or salary expectations. Notes persist across sessions via storage
  and display cleanly in the `list` view. Also implemented `NoteCommandParser` to parse
  and validate the `note INDEX n/NOTE_CONTENT` format.

### 2. Code Contributed

* [Link to RepoSense Dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=shyamalv18&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&filteredFileName=)

### 3. Enhancements and Bug Fixes

* **Defensive Coding**: Added assertions, logging, and bounds-checked exceptions
  throughout `Application`, `ApplicationList`, `Storage`, `ListCommand`, and
  `NoteCommand` to meet Week 9 code quality requirements.

* **Storage Deadline Bug Fix**: Fixed a data loss bug where `Storage#load()` silently
  dropped the deadline field despite `save()` writing it. Updated the loader to
  correctly reconstruct `Deadline` objects from the storage format.

* **Unmodifiable List**: Changed `ApplicationList#getApplications()` to return
  `Collections.unmodifiableList()` instead of the raw `ArrayList`, preventing external
  callers from bypassing the bounds-checked mutation methods.

* **ListCommand UI Abstraction**: Refactored `ListCommand` to use `Ui#showMessage()`
  instead of `System.out.println()` directly, aligning it with all other commands and
  making its output testable.

* **Storage Format Migration**: Extended the storage format from 6 to 7 base fields to
  accommodate the note field, maintaining backward compatibility and updating affected
  tests accordingly.

* **Deadlines Display Fix (v2.1)**: Fixed `DeadlineList#toString()` which was returning
  the string `"null"` for empty deadline lists and a raw Java `ArrayList` dump for
  non-empty lists. Replaced with a clean user-facing format showing deadline type, date,
  and completion status, consistent with how all other empty fields display as `-`.

### 4. Testing

* **`StorageTest`**: Wrote 11 JUnit test cases covering save/load round-trips with valid
    data, empty list handling, non-existent file loading, corrupted data detection,
    file creation verification, archived application flag preservation, mixed
    archived/non-archived lists, and invalid deadline date handling.

* **`NoteCommandTest`**: Wrote 11 JUnit test cases covering valid note assignment,
  overwrite behaviour, notes on specific indices without affecting others, out-of-range
  index handling, notes with special characters, and null/assertion guard checks. Uses a
  `CapturingUi` test double to verify output without touching the console.

* **`NoteCommandParserTest`**: Wrote 12 JUnit test cases applying equivalence
  partitioning across valid inputs, missing prefix, empty/blank note content,
  non-numeric index, zero index, negative index, and null/blank arguments — raising
  `NoteCommandParser` coverage from 0% to full coverage.

### 5. Contributions to the User Guide

* Wrote the **Adding a note** section describing the `note` command format, examples,
  overwrite behaviour, and input restrictions.
* Updated the **Listing all applications** section to document the note display
  behaviour and clarify that only active applications are shown.

> #### Adding a note: `note`
>
> Adds or updates a note for a specific internship application. Notes are useful for
> recording interview impressions, tech stack requirements, or any other
> application-specific insights.
>
> **Format:** `note INDEX n/NOTE_CONTENT`
>
> * Adds or **overwrites** the existing note for the application at `INDEX`.
> * `INDEX` must be a positive integer 1, 2, 3, ...
> * `NOTE_CONTENT` can contain any text including spaces and special characters,
    >   except the pipe character (`|`) which is reserved for internal storage.
> * Multi-line input is not supported. Only single-line text can be entered as note
    >   content.
>
> **Examples:**
> * `note 1 n/Strong Java skills required, prepare LeetCode`
> * `note 2 n/Remote friendly, good culture fit`
>
> **Note:** Running `note` on an application that already has a note will overwrite
> the previous note entirely.

> #### Listing all applications: `list`
>
> Shows a list of all tracked internship applications. Only active (non-archived)
> applications are shown. If an application has a note, it will be displayed indented
> on the line directly below that application's details.
>
> **Format:** `list`
>
> **Example output:**
> ```
> Here are your internship applications:
> 1. Company: Shopee | Role: Backend Intern | Status: Applied | ...
>    Note: Strong Java skills required, prepare LeetCode
> 2. Company: Grab | Role: Data Analyst | Status: Interview | ...
> ```

### 6. Contributions to the Developer Guide

* Wrote the **Storage Component** section covering the pipe-delimited file format,
  save/load behaviour, SLAP-based helper method design, and design considerations for
  format choice and corrupted data handling. Includes two PlantUML sequence diagrams
  (`ShyamalStorageLoadSequence`, `ShyamalStorageSaveSequence`).
* Wrote the **ApplicationList Defensive Design** section covering the unmodifiable list
  pattern and index bounds checking with code snippets. Includes a class diagram
  (`ShyamalClassDiagram`) showing relationships between `Application`, `ApplicationList`,
  `Storage`, `NoteCommand`, and `ListCommand`.
* Wrote the **ListCommand and UI Abstraction** section explaining the rationale for
  routing output through `Ui` and its impact on testability. Includes one PlantUML
  sequence diagram (`ShyamalListCommandSequence`).
* Wrote the **Note Feature Implementation** section covering parsing logic, execution
  flow, and design considerations for overwrite vs. append behaviour and inline vs.
  separate-file storage. Includes one PlantUML sequence diagram
  (`ShyamalNoteCommandSequence`).

> #### Storage Component
>
> The `Storage` class handles reading from and writing to a human-editable text file.
> Each application is stored as a single pipe-delimited line:
> ```
> company | role | status | contactName | contactEmail | salary | note
> company | role | status | contactName | contactEmail | salary | note | deadlineType | dueDate | isDone ...
> ```
> On load, `Storage#load()` delegates to `parseLine()`, which further delegates to
> focused helpers — `parseStatus()`, `parseSalary()`, `parseNote()`, and
> `parseDeadlines()` — following SLAP. Corrupted lines throw an `InternTrackrException`
> with the offending line number for easy user diagnosis.

> #### ApplicationList Defensive Design
>
> `ApplicationList#getApplications()` returns `Collections.unmodifiableList()` so
> callers can read but not mutate the list directly. Both `getApplication(int index)`
> and `deleteApplication(int index)` validate the 1-based index and throw
> `InternTrackrException` with a user-friendly message if out of range, preventing raw
> `IndexOutOfBoundsException` from reaching the user.

> #### ListCommand and UI Abstraction
>
> `ListCommand#execute()` routes all output through `Ui#showMessage()`. This ensures
> output is interceptable in tests via a `CapturingUi` subclass, consistent with how
> all other commands handle output. Notes are displayed conditionally — only when
> non-null and non-blank — keeping the view clean.

> #### Note Feature Implementation
>
> `NoteCommandParser` validates the `note INDEX n/NOTE_CONTENT` format, checking for
> blank input, missing prefix, empty content, and non-positive index before constructing
> a `NoteCommand`. `NoteCommand#execute()` retrieves the target application via
> `ApplicationList#getActiveApplication()`, updates the note field, persists immediately
> via `Storage#save()`, and confirms via two `Ui#showMessage()` calls — one for the
> application identity and one for the updated note content.
