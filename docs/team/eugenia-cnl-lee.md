# Lee Chun Nga (Eugenia) - Project Portfolio Page

## Overview

**InternTrackr** is a CLI-first internship application manager designed for students applying to multiple internships.
It helps users track applications, statuses, deadlines, and recruiter contacts
in one place to avoid missing important opportunities.

My primary contributions focus on **deadline management, model refactoring, and project coordination**,
enabling structured tracking of time-sensitive application stages.

I took ownership of the deadline subsystem, evolving it from a simple feature into a
structured and extensible component integrated across the system.

## Summary of Contributions

### 1. New Features

* **Deadline Management System**
    - Implemented `deadline add`, `deadline list`, and `deadline done`
    - Supports attaching, viewing, and updating deadlines per application
    - Introduced nested indexing (application index + deadline index)

* **Core Design Refactor: Multiple deadlines per application**
    - Replaced single-deadline model with a scalable structure supporting multiple deadlines
    - Required redesign of model, command logic, and indexing (application index + deadline index)
    - Enabled new features (`deadline list`, `deadline done`) and future extensibility (e.g., sorting, timeline)

* **Recruiter Contact Feature (`contact`)**
    - Added support for storing recruiter name and email per application
    - Implemented prefix-based parsing (`c/`, `e/`) with validation

### 2. Code Contributed

* [Link to RepoSense Dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=eugenia-cnl-lee&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

### 3. Enhancements, Technical Contributions, and Bug Fixes

* **Parsing and Validation**
    - Implemented structured parsing for deadline commands (`t/`, `d/`, `i/`)
    - Added validation for index bounds, missing prefixes, and invalid formats

* **Model and Data Design**
    - Designed `Deadline` class with completion state (`isDone`)
    - Supported clean separation between data, UI formatting, and storage

* **System Integration**
    - Integrated deadlines across model, UI, parser, and storage
    - Ensured persistence and consistent behaviour across sessions

* **Code Quality and Testing**
    - Applied defensive coding (validation, error handling)
    - Updated and maintained test cases for evolving data model

### 4. Contributions to the User Guide

* Documented:
    - `deadline add`
    - `deadline list`
    - `deadline done`
    - `contact`
* Added examples and ensured alignment with actual behaviour

* Ensured:
    - Clear command formats
    - Realistic examples
    - Consistent CLI-style documentation

### 5. Contributions to the Developer Guide

* **Deadline Subsystem Design and Documentation**
    - Designed and documented a full deadline subsystem supporting multiple deadlines per application via a `DeadlineList` abstraction
    - Documented the evolution from a single-deadline model to a scalable list-based design, including trade-offs and rationale

* **Command and Parser Architecture for Deadline Features**
    - Designed and documented a centralized `DeadlineCommandParser` handling multiple subcommands (`add`, `list`, `done`, `undone`, `delete`)
    - Explained parsing flow, subcommand dispatching, and fail-fast validation strategy

* **Feature Implementation Documentation**
    - Documented implementation details for all deadline commands:
        - `deadline add`, `deadline list`, `deadline done`, `deadline undone`, `deadline delete`
    - Included execution flow, validation steps, and interaction with model, UI, and storage layers

* **Design Trade-offs and Alternatives**
    - Documented multiple design decisions, including:
        - single vs multiple deadlines
        - per-application deadlines vs global deadline list
        - single parser vs multiple parsers
        - append vs overwrite behaviour
        - fail-fast validation strategy
    - Provided rationale for each choice to justify final architecture

* **UML and Sequence Diagrams**
    - Contributed class and sequence diagrams for:
        - deadline model structure
        - parser delegation flow
        - command execution flow for each deadline feature

### 6. Contributions to Team-Based Tasks

* Participated in:
    - Feature integration across releases (v1.0 → v2.0)
    - Ensuring consistency between implementation and documentation
    - JAR release preparation and smoke testing

* Helped maintain:
    - Code quality through defensive programming
    - Consistent command structure across features

* **Project management & coordination**
    - Organised and maintained GitHub issues and task breakdown
    - Coordinated feature workflow across releases (v1.0 → v2.0)

* **Release management**
    - Built and released **v1.1 JAR**
    - Performed smoke testing and ensured consistency with User Guide

* **Integration work**
    - Ensured features worked cohesively across parser, model, UI, and storage
    - Maintained consistency between documentation and implementation

### 7. Review / Mentoring Contributions

* Reviewed teammates’ PRs and:
    - Suggested improvements in **defensive coding and validation**
    - Ensured adherence to project structure and conventions
    - Identified mismatches between implementation and documentation

* Assisted teammates in:
    - Debugging parser-related issues
    - Structuring command and model interactions
