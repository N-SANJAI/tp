# Aarav Rajesh - Project Portfolio Page

## Overview

**InternTrackr** is a CLI-first internship application manager for university students applying to multiple internships. It helps users track where they applied, current statuses, and important dates in one place so they do not miss deadlines or lose track across spreadsheets, notes, and emails.

As part of this project, I focused on the application lifecycle surrounding adding, deleting, archiving, restoring, and viewing archived internship applications. My work also included restructuring the parser into a more modular design and updating the project documentation to reflect these implementation decisions clearly.

## Summary of Contributions

### Code Contributed

- [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=aaravvr&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=)

### Enhancements Implemented

#### 1. Added support for archiving and restoring applications

I implemented the `archive`, `unarchive`, and `list archive` commands to support a full archive workflow.

- `archive INDEX` hides an application from the default active list without deleting it.
- `unarchive INDEX` restores an archived application back to the active list.
- `list archive` displays archived applications separately using their own archived-only index.

This feature was more than a simple status toggle. It required coordinated changes across parsing, command execution, model behavior, storage, and list rendering. In particular, I introduced archived-index-aware behavior so that archived applications are addressed consistently using the index shown in `list archive`, while active applications continue using the index shown in the default `list`.

#### 2. Extended deletion to support archived applications directly

I implemented `delete archive INDEX`, allowing users to permanently delete archived applications without first restoring them.

This required `DeleteCommandParser` to detect the optional `archive` keyword and set an execution path accordingly. In `DeleteCommand`, the provided index is resolved against either the active view or archived view first, before being mapped back to the underlying backing list for safe deletion. This keeps the command behavior aligned with what the user actually sees on screen.

#### 3. Implemented robust application addition flow

I implemented the `add` command, including:

- flexible parsing of `c/` and `r/` in either order,
- duplicate detection through `ApplicationList#hasApplication()`,
- immediate persistence through `Storage#save()`.

I also fixed the post-add confirmation count so that it reports the number of active applications rather than the total size of the backing list, which could otherwise include archived entries and confuse users.

#### 4. Refactored the parser into a modular dispatch design

I helped redesign the parser from a monolithic parsing flow into a centralized dispatcher backed by dedicated per-command parser classes such as:

- `AddCommandParser`
- `DeleteCommandParser`
- `ArchiveCommandParser`
- `UnarchiveCommandParser`

This made the logic easier to test, reduced clutter inside `Parser.java`, and made future command additions more maintainable. I also added rejection of the pipe character (`|`) at the parser level to protect the storage format from malformed user input.

### Other Enhancements and Fixes

In addition to the main features above, I also implemented and fixed several supporting behaviors:

- Added `ApplicationList#getActiveApplication(int displayIndex)` and `countActive()` so active-only commands operate on the visible active list rather than the raw backing list.
- Added `ApplicationList#getArchivedApplication(int displayIndex)` and `countArchived()` so archived-only commands behave consistently with the archived view.
- Updated `ListCommand` so the default `list` output excludes archived applications.
- Improved empty-list handling by replacing misleading invalid-range messages with a clearer message: `"No applications found. Start adding some!"`
- Designed a safer storage encoding for archived state using `archived:true`, avoiding ambiguity with deadline `isDone` boolean values.
- Added defensive checks and assertions across my command implementations to strengthen robustness and align with the project’s defensive programming expectations.

## Contributions to the User Guide

I contributed the User Guide sections for the following commands:

- `add`
- `delete`
- `archive`
- `unarchive`
- `list archive`

My contributions focused on making command usage clear for end users by documenting:
- the command format,
- the meaning of indices in active vs archived views,
- examples of both normal and archive-specific usage,
- the behavioral distinction between hiding and permanently deleting applications.

These sections were especially important because archive-related commands introduce a second visible index space (`list archive`), which can easily confuse users if not documented carefully.

## Contributions to the Developer Guide

I contributed the Developer Guide sections for the following features:

- Add Feature Implementation
- Delete Feature Implementation
- Parser Implementation
- Archive Feature Implementation
- List Archive Feature Implementation
- Unarchive Feature Implementation

I also created or updated the following UML diagrams for these sections:

- `AaravAddCommandSequence`
- `AaravDeleteCommandSequence`
- `AaravArchiveCommandSequence`
- `AaravUnarchiveCommandSequence`
- `AaravListArchiveCommandSequence`
- `AaravParserSequence`
- `AaravClassDiagram`

In these DG contributions, I documented:
- how active and archived indices are resolved differently,
- how `delete archive INDEX` is implemented safely,
- why the parser was refactored into dedicated parser classes,
- why `list archive` is handled directly in `Parser`,
- why archived state is stored with a labeled token instead of a raw boolean,
- the main design alternatives considered for these decisions.

## Contributions to Team-Based Tasks

I contributed to team-wide integration work by helping ensure that archive-related behavior remained consistent across parsing, command execution, list rendering, storage, and documentation.

This included:
- aligning archive behavior with the list and storage model,
- ensuring index-based commands remained consistent with user-visible views,
- updating documentation and diagrams so they matched the implemented behavior.

## Review and Coordination Contributions

I reviewed teammates’ pull requests covering code defensiveness, testing, and documentation updates. Examples include:

- [PR #45](https://github.com/AY2526S2-CS2113-T14-1/tp/pull/45) 
- [PR #77](https://github.com/AY2526S2-CS2113-T14-1/tp/pull/77) 
- [PR #84](https://github.com/AY2526S2-CS2113-T14-1/tp/pull/84) 
- [PR #51](https://github.com/AY2526S2-CS2113-T14-1/tp/pull/51) 
