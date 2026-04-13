# Navaneethan Sanjai - Project Portfolio Page

## Overview
**InternTrackr** is a CLI-first internship application manager for university students applying to multiple internships. It helps users track where they applied, current statuses, and important dates in one place so they do not miss deadlines or lose track across spreadsheets, notes, and emails.

## Summary of Contributions

### Code Contributed
[RepoSense Dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=n-sanjai&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

### Project Architecture & Core Infrastructure

**Core File Structure (v1)**
* Designed and implemented the initial project package structure and core classes
* Established the foundational MVC architecture: `seedu.interntrackr.command`, `seedu.interntrackr.model`, `seedu.interntrackr.parser`, `seedu.interntrackr.ui`, `seedu.interntrackr.storage`, `seedu.interntrackr.exception`


**InternTrackr.java (Main Application Controller)**
* Implemented main application entry point with full lifecycle management: initialization, read-parse-execute loop, graceful error handling
* Designed the startup sequence: `Ui` → `Storage` → `ApplicationList` initialization with fallback to empty list on corrupted data
* Implemented the command loop that processes user input until exit, with error recovery (catches `InternTrackrException`, displays error, continues running)
* Handled logging setup (`setupLogging()` method) for debugging support across the entire application
* Result: Clean separation of concerns; all other features built on top of this solid foundation

---

### Enhancements Implemented

**1. Recruiter Networking (`contact` command)**
* Implemented a command to link recruiter names and emails directly to specific applications
* Handles email validation (exactly one `@`, one dot after `@`, no spaces) and defensive index bounds checking
* Result: Users can quickly access follow-up contacts without switching platforms

**2. Status Analytics (`overview` command)**
* Designed and implemented an aggregation system that computes application counts by status
* Used `LinkedHashMap` to maintain consistent status ordering (Applied → Pending → Interview → Offered → Rejected → Accepted)
* Read-only command that queries `ApplicationList` without invoking `Storage`, demonstrating understanding of command side effects
* Post-release fix: identified a UX ambiguity where archived applications were silently excluded from the status breakdown with no indication. Updated the output label to **"Active Status Breakdown"** and audited the filtering logic to confirm `isArchived()` is checked correctly before aggregating counts.
* Result: Users see job hunt momentum at a glance, with a clear distinction between active and archived applications

**3. Compensation Tracking (`offer` command)**
* Implemented salary logging with automatic status normalization to "Offered"
* Salary validation: rejects scientific notation, enforces max 2 decimal places, caps at $10M to prevent overflow
* Fixed bug where large doubles displayed as scientific notation (e.g., `8.92E33` → proper currency format)
* Data persists immediately via `Storage#save()` to prevent financial data loss
* Result: Users can track and compare compensation packages securely

**4. In-App Assistance (`help` command)**
* Implemented lightweight help command that points to external online documentation
* Design rationale: Keeps the application lightweight while ensuring documentation stays current
* Result: Smooth onboarding without requiring app rebuild

### Contributions to the User Guide
* Documented all four commands (`contact`, `offer`, `overview`, `help`) with clear format specifications and practical examples
* Added usage notes clarifying constraints (e.g., contact name must precede email, salary max 2 decimal places)
* Added a clarifying note under the `filter` command specifying that it only affects the display - the `INDEX` for all commands always refers to the position in the default `list` output, not the filtered view
* Updated the `overview` command description to accurately reflect that the status breakdown covers active applications only

### Contributions to the Developer Guide
* Explained implementation of each feature with sequence diagrams
* **Architecture Overview:** Documented MVC pattern, package structure, and command execution flow
* **Main Application Controller (InternTrackr.java):** Detailed startup sequence, error recovery loop, and logging setup with sequence diagrams
* **Overview Feature:** Documented read-only query pattern, `LinkedHashMap` usage for status ordering, and `isArchived()` filtering logic for the Active Status Breakdown
* **Offer Feature:** Detailed salary validation pipeline, automatic status update logic, and salary formatting bug fix
* **Contact Feature:** Explained email validation, index bounds checking, and defensive design
* **Help Feature:** Documented URL-based design for maintainability
* **Object Diagram:** Added `SanjaiObjectDiagram`
* **Diagram quality fixes (PE-D):** Identified and corrected incorrect message flow direction across 5 sequence diagrams (arrows were drawn `UI → InternTrackr` when the actual code has `InternTrackr` calling `ui.readCommand()`); added activation bars consistently across all 8 authored diagrams; removed a redundant end-to-end diagram that duplicated the overview sequence; corrected a factually inaccurate design consideration that claimed `null` was passed to read-only commands (audited `InternTrackr.java` to confirm `storage` is always passed uniformly)

---

## Contributions to the Developer Guide - Extracts

### Overview Feature Implementation
The `OverviewCommand` queries `ApplicationList` for the total count and iterates through all applications, calling `isArchived()` on each to skip archived entries. Status frequencies for active applications are aggregated using a `LinkedHashMap` to preserve insertion order defined by `Application.VALID_STATUSES`. The results are displayed under the heading "Active Status Breakdown", making it explicit to users that archived applications are intentionally excluded. This read-only operation never invokes `Storage`, demonstrating the principle of minimising side effects.

### Offer Feature Implementation
The `OfferCommand` follows a strict validation pipeline: verify index bounds, parse and validate salary (rejecting scientific notation and enforcing 2 decimal place limit), update the application's salary field, check if status needs normalization to "Offered", then immediately trigger `Storage#save()` to ensure financial data persists. The salary formatting uses `String.format("$%.2f", salary)` to handle floating-point display issues and prevent scientific notation in output.

### Contact Feature Implementation
The `ContactCommand` validates the application index using `getActiveApplication()` to resolve the display index against active entries only, verifies email format (exactly one `@`, at least one dot after `@`, no spaces), updates the application's recruiter details via `setContactDetails()`, and immediately invokes `Storage#save()` to persist networking information. Email validation is implemented as a private helper method within the command class.

### Help Feature Implementation
The `HelpCommand` displays a hardcoded URL to the exhaustive online User Guide via `Ui`. This design keeps the application lightweight and ensures documentation can be updated without rebuilding the binary.
