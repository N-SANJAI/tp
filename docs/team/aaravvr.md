# Aarav Rajesh - Project Portfolio Page

## Overview

**InternTrackr** is a CLI-first internship application manager for university students applying to multiple internships.
It helps users track where they applied, current statuses, and important dates in one place so they do not miss
deadlines or lose track across spreadsheets, notes, and emails.

## Summary of Contributions

### 1. New Features

* **Adding applications (`add` command)**: Implemented the command to add a new internship application by specifying
  company and role. Supports prefix-flexible parsing (`c/` and `r/` in either order), duplicate detection via
  `ApplicationList#hasApplication()`, and immediate persistence via `Storage#save()`.

* **Deleting applications (`delete` command)**: Implemented the command to remove an application by display index,
  supporting both `delete INDEX` for active applications and `delete archive INDEX` for archived ones. Index
  resolution is handled against the relevant view only, ensuring it always matches what the user sees on screen.

* **Archiving applications (`archive` command)**: Implemented the command to hide an application from the default
  list without deleting it, persisting the state via a custom `| archived:true` storage token designed to avoid
  conflicts with deadline `isDone` fields.

* **Viewing archived applications (`list archive` command)**: Implemented using a two-pass iteration — first to
  count archived entries, then to display them — to avoid printing an orphaned header when the archive is empty.

* **Restoring archived applications (`unarchive` command)**: Implemented symmetrically with `archive`, resolving
  the index against archived entries only via `ApplicationList#getArchivedApplication()` to match the
  `list archive` display index.

* **Parser redesign**: Refactored the monolithic `Parser.java` into a centralized dispatcher backed by dedicated
  per-command `XYZCommandParser` classes, making each parser independently testable and keeping `Parser.java` as
  a thin router. Also added pipe character (`|`) rejection to protect the storage format.

### 2. Code Contributed

* [Link to RepoSense Dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=aaravvr&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=)

### 3. Enhancements and Bug Fixes

* **Active-index-aware delete and archive**: Extended `ApplicationList` with `getActiveApplication(int displayIndex)`
  and `countActive()` to support index resolution against non-archived entries only. This was necessary to keep
  `delete` and `archive` consistent with the display indices shown in `list`.

* **Archived-index-aware unarchive and delete**: Extended `ApplicationList` with `getArchivedApplication(int displayIndex)`
  and `countArchived()` to support index resolution against archived entries only, keeping `unarchive` and
  `delete archive` consistent with the display indices shown in `list archive`.

* **Direct deletion of archived applications**: Extended `DeleteCommand` and `DeleteCommandParser` to support
  `delete archive INDEX`, allowing users to permanently remove an archived application without the extra step of
  unarchiving it first. The `isArchived` flag in `DeleteCommand` selects the appropriate index-resolution path at
  execution time.

* **Correct active count in `add` confirmation**: Fixed `AddCommand` to use `ApplicationList#countActive()` instead
  of `ApplicationList#getSize()` in the post-add confirmation message. Previously, the count included archived
  entries, causing the reported number to exceed the visible list size.

* **Friendly empty-list error message**: Fixed `ApplicationList#getActiveApplication()` to detect when the active
  list is empty and throw `"No applications found. Start adding some!"` instead of the misleading
  `"Invalid application index. Please provide a number between 1 and 0."` message that resulted from an impossible
  range when no active applications existed.

* **Storage format for `isArchived`**: Designed a backward-compatible storage encoding for the archived state.
  Rather than appending a plain `true`/`false` (which conflicts with a completed deadline's `isDone` field), the
  field is encoded as `| archived:true` and only written when the application is archived. Non-archived applications
  produce identical output to before, preserving compatibility with existing data files.

* **`ListCommand` filter**: Updated `ListCommand` to skip archived applications, so the default `list` output only
  shows active entries. The empty-list message is shown only when there are no active entries.

* **Defensive coding**: Added assertions and input validation to `AddCommand`, `DeleteCommand`, `ArchiveCommand`,
  `UnarchiveCommand`, and `ListArchiveCommand` meeting the Week 9 code defensiveness requirements.

---

## Contributions to the User Guide (Extracts)

> #### **Adding an application: `add`**
> Adds an internship application with the given company and role. Prefixes can be in any order. Duplicate applications are not allowed.
> **Format:** `add c/COMPANY r/ROLE`
> * **Example:** `add c/"Shopee" r/"Backend Intern"`
>
> #### **Deleting an application: `delete`**
> Deletes an internship application by its display index. Use `delete INDEX` for active applications or
> `delete archive INDEX` to permanently remove an archived application directly, without unarchiving it first.
> **Format:** `delete INDEX` or `delete archive INDEX`
> * **Examples:** `delete 2`, `delete archive 1`
>
> #### **Archiving an application: `archive`**
> Archives an application so it no longer appears in the default list, but is not deleted. Can be viewed with `list archive`.
> **Format:** `archive INDEX`
> * **Example:** `archive 1`
>
> #### **Restoring an archived application: `unarchive`**
> Restores an archived application back to the active list. The index refers to the index shown in `list archive`.
> **Format:** `unarchive INDEX`
> * **Example:** `unarchive 1`
>
> #### **Listing archived applications: `list archive`**
> Displays all archived applications using a sequential index starting from 1.
> **Format:** `list archive`

---

## Contributions to the Developer Guide (Extracts)

> #### **Add Feature Implementation**
> The `add` command uses `AddCommandParser` to support prefix-flexible input (`c/` and `r/` in any order). `AddCommand#execute()` checks `ApplicationList#hasApplication()` to reject duplicates before insertion, then calls `Storage#save()` to persist the new entry immediately. The post-add confirmation uses `ApplicationList#countActive()` to correctly report the active list size, excluding archived entries.
>
> #### **Delete Feature Implementation**
> The `delete` command supports two forms: `delete INDEX` for active applications and `delete archive INDEX` for archived applications. `DeleteCommandParser` detects the `archive` keyword and sets an `isArchived` flag, which `DeleteCommand#execute()` uses to route index resolution to either `ApplicationList#getActiveApplication()` or `ApplicationList#getArchivedApplication()`. A linear scan by object identity then locates the backing-list position for safe removal. The confirmation message uses `ApplicationList#countActive()` to show the remaining active count. When the active list is empty, a friendly message `"No applications found. Start adding some!"` is shown instead of a misleading range error.
>
> #### **Parser Implementation**
> The original `Parser.java` was refactored from a ~100-line monolithic method into a centralized dispatcher backed by dedicated `XYZCommandParser` classes (`AddCommandParser`, `DeleteCommandParser`, `ArchiveCommandParser`, `UnarchiveCommandParser`, etc.). `Parser.java` now acts as a thin switch-based router. Pipe character (`|`) input is also rejected to protect the storage format.
>
> #### **Archive Feature Implementation**
> The `archive` command resolves the display index via `ApplicationList#getActiveApplication()` and sets `Application#isArchived` to `true`. The archived state is stored using the token `| archived:true`, only appended when the application is archived, to avoid conflict with deadline `isDone` fields.
>
> #### **List Archive Feature Implementation**
> `ListArchiveCommand` performs a two-pass iteration — first to count archived entries, then to display them with a sequential index. This avoids printing an orphaned header when no archived applications exist.
>
> #### **Unarchive Feature Implementation**
> The `unarchive` command resolves the display index against archived entries only via `ApplicationList#getArchivedApplication()`, ensuring the index matches what the user sees in `list archive`. `Application#setArchived(false)` restores the application, which immediately reappears in the default `list` output. The design is intentionally symmetric with `archive`.
