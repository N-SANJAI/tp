# Navaneethan Sanjai - Project Portfolio Page

## Overview
**InternTrackr** is a CLI-first internship application manager for university students applying to multiple internships. It helps users track applications, statuses, contacts, offers, and deadlines in one place instead of juggling spreadsheets, notes, and emails.

## Summary of Contributions

### Code Contributed
[RepoSense Dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=n-sanjai&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

### Enhancements Implemented
I contributed to both the application architecture and several core user-facing features.

- Implemented the main application control flow in `InternTrackr`, including startup initialization, the read-parse-execute loop, graceful handling of invalid commands, and fallback recovery from missing or corrupted stored data.
- Implemented the `overview` command to aggregate active application statuses in a stable order, excluding archived applications from the status breakdown.
- Implemented the `offer` command for salary tracking, including automatic normalization of application status to `Offered` and immediate persistence through `Storage#save()`.
- Implemented the `help` command to direct users to the online User Guide without bloating the CLI with large embedded help text.
- Implemented advanced deadline management through `deadline undone` and `deadline delete`, including parser updates, defensive bounds checking, and prevention of redundant state changes.
- Strengthened deadline correctness by introducing strict date validation using `ResolverStyle.STRICT` and rejecting invalid or past dates.
- Implemented `toSummaryString()` in `Application` so list-based commands display concise deadline counts instead of raw internal deadline data.
- Authored the UG and DG documentation for the `contact` feature, including the sequence diagram and design rationale.

### Contributions to the User Guide
I documented the UG sections for `offer`, `overview`, `help`, `deadline undone`, and `deadline delete`, and updated command formats, examples, and the command summary table to keep the documentation aligned with the implemented behavior. I also wrote the documentation for the `contact` feature.

### Contributions to the Developer Guide
I authored and updated DG sections covering the main control flow and architecture, the UI component, `overview`, `offer`, `contact`, `help`, advanced deadline management, strict date validation, and UI list summary abstraction. I also added and refined multiple UML diagrams, including the startup sequence diagram, run loop happy-path and error-path sequence diagrams, overview command sequence diagram, overview object diagram, offer command sequence diagram, contact command sequence diagram, and help command sequence diagram.

### Contributions to Team-Based Tasks
- Reviewed and corrected sequence diagrams whose message directions did not match the actual call flow.
- Standardized diagram style by applying activation bars more consistently.
- Corrected inaccurate DG design rationale related to dependency passing to read-only commands.
- Helped fix PE-D issues related to deadline parsing, error handling, and list output formatting.

### Review and Mentoring Contributions
I helped audit documentation and diagrams for technical correctness and consistency, and identified mismatches between implementation and documentation, especially in control flow and deadline-related behavior.

### Contributions Beyond the Project Team
I contributed fixes for issues surfaced during PE-D, especially around invalid deadline dates, inconsistent deadline error handling, and raw internal deadline output in list views. These fixes improved both correctness and user experience.

---

## Contributions to the User Guide (Extracts)

### Adding recruiter contact details: `contact`
Stores the recruiter’s name and email for a specific application.

**Format:** `contact INDEX c/NAME e/EMAIL`

**Example:** `contact 1 c/John Doe e/john.doe@example.com`

### Logging an offer: `offer`
Stores the salary for an application and updates the application status to `Offered` if needed.

**Format:** `offer INDEX s/SALARY`

**Example:** `offer 1 s/5000.00`

### Unmarking a deadline as done: `deadline undone`
Marks a completed deadline as not done.

**Format:** `deadline undone INDEX i/DEADLINE_INDEX`

**Example:** `deadline undone 1 i/1`

### Deleting a deadline: `deadline delete`
Deletes a specific deadline from an application.

**Format:** `deadline delete INDEX i/DEADLINE_INDEX`

**Example:** `deadline delete 1 i/2`

---

## [Optional] Contributions to the Developer Guide (Extracts)

### Advanced Deadline Management
The application’s deadline capabilities were expanded to allow users to fully manage the state of their tasks by unmarking completed deadlines or deleting them entirely.

1. The `DeadlineCommandParser` was updated to securely parse the `undone` and `delete` subcommands, intercepting invalid non-numerical indices and preventing stack trace leaks.
2. `DeadlineUndoneCommand` and `DeadlineDeleteCommand` first resolve the active application, then retrieve its `DeadlineList`.
3. Strict bounds-checking is applied to both the application index and the deadline index.
4. For `undone`, the command throws an exception if the deadline is already incomplete, preventing redundant operations and unnecessary saves.
5. For `delete`, the command removes the selected deadline and persists the change immediately.

### Strict Date Validation
To ensure data integrity for internship timelines, the date parsing logic for deadlines was overhauled to prevent silent mutation of invalid dates.

By default, Java’s `DateTimeFormatter` uses `ResolverStyle.SMART`, which may auto-correct invalid dates. The parser was upgraded to use `ResolverStyle.STRICT` with the `uuuu` year format, and additional checks reject past dates. If a user enters a non-existent calendar date or past date, the parser throws an `InternTrackrException` immediately.

### UI List Summary Abstraction (`toSummaryString`)
To prevent CLI clutter when applications accumulate many deadlines, a new `toSummaryString()` abstraction was introduced.

Instead of relying on `toString()`, which exposes raw internal deadline data, `toSummaryString()` computes the deadline count and formats it cleanly, such as `Deadlines: 0 deadlines` or `Deadlines: 2 deadlines`. This abstraction is used consistently across `ListCommand`, `ListArchiveCommand`, `FilterCommand`, and `FindCommand`.
