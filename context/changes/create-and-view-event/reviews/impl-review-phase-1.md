<!-- IMPL-REVIEW-REPORT -->
# Implementation Review: S-01 Organizator zakłada wydarzenie i widzi je

- **Plan**: context/changes/create-and-view-event/plan.md
- **Scope**: Phase 1 of 3
- **Date**: 2026-09-27
- **Verdict**: APPROVED
- **Findings**: 0 critical, 2 warnings, 2 observations

## Verdicts

| Dimension | Verdict |
|-----------|---------|
| Plan Adherence | PASS |
| Scope Discipline | PASS |
| Safety & Quality | WARNING |
| Architecture | PASS |
| Pattern Consistency | WARNING |
| Success Criteria | PASS |

## Findings

### F1 — Mutable JPA collections exposed directly by getters

- **Severity**: ⚠️ WARNING
- **Impact**: 🏃 LOW — quick decision; fix is obvious and narrowly scoped
- **Dimension**: Pattern Consistency
- **Location**: src/main/java/com/example/zgrani/event/Event.java:101,117 (`getDaysOfWeek()`, `getCandidateTerms()`)
- **Detail**: Both getters return the live internal `HashSet` fields directly. A caller can `event.getCandidateTerms().add(...)` or `.clear()` and silently corrupt JPA-managed state, bypassing `addCandidateTerm()` and cascade semantics.
- **Fix**: Wrap both getters in `Collections.unmodifiableSet(...)`.
- **Decision**: PENDING

### F2 — `daysOfWeek` element collection fetched eagerly

- **Severity**: ⚠️ WARNING
- **Impact**: 🏃 LOW — quick decision; fix is obvious and narrowly scoped
- **Dimension**: Safety & Quality
- **Location**: src/main/java/com/example/zgrani/event/Event.java:37 (`@ElementCollection(fetch = FetchType.EAGER)` on `daysOfWeek`)
- **Detail**: Not called out either way in the plan. Every `Event` load (including any future listing/paged endpoint) join-fetches this collection even when unneeded. Reasonable to avoid a second lazy-proxy interaction with `candidateTerms`, but undocumented and a precedent that could compound if another eager collection is added later.
- **Fix**: Leave as-is for now (single-entity MVP, low blast radius) but note the tradeoff; revisit if a listing endpoint is added.
- **Decision**: PENDING

### F3 — No entity-level invariant checks (date range order, positive participants)

- **Severity**: OBSERVATION
- **Impact**: 🏃 LOW
- **Dimension**: Safety & Quality
- **Location**: src/main/java/com/example/zgrani/event/Event.java (constructor)
- **Detail**: The `Event` constructor accepts any `dateRangeStart`/`dateRangeEnd`/`expectedParticipants` without invariant checks. This is by design — Phase 3's `EventService.create` performs the `dateRangeEnd >= dateRangeStart` check and Bean Validation on `CreateEventRequest` enforces `expectedParticipants >= 1` — so the guard exists one layer up, not on the entity itself.
- **Decision**: PENDING (informational — confirmed covered by Phase 3, no action needed)

### F4 — H2 datasource uses default credentials and CWD-relative path

- **Severity**: OBSERVATION
- **Impact**: 🏃 LOW
- **Dimension**: Safety & Quality
- **Location**: src/main/resources/application.yml:10-11
- **Detail**: No explicit `username`/`password` (H2 defaults to `sa`/no password), and `./data/zgrani` is relative to process CWD. Acceptable for local single-user dev per the plan's explicit decision (H2 file-based, chosen to avoid provisioning a separate Render DB service), but worth revisiting if this ever runs from a variable working directory.
- **Decision**: PENDING (informational — accepted tradeoff per plan-brief's Open Risks)

## Notes

- All 6 planned Phase 1 changes (pom.xml, application.yml, Event.java, CandidateTerm.java, EventRepository.java, .gitignore) verified as MATCH against the plan — no drift, no missing items, no scope creep.
- Critical detail from the plan's "Critical Implementation Details" note — `Event.candidateTerms` must be `FetchType.LAZY` to avoid `LazyInitializationException` in Phase 3 — confirmed correctly implemented.
- UUID (app-generated, unguessable link) vs. Long (DB identity, internal-only) id strategy confirmed correctly split between `Event` and `CandidateTerm`.
- Automated success criteria re-verified: `./mvnw clean compile` ✅, `./mvnw test -Dtest=ZgraniApplicationTests` ✅ (both pass on current HEAD).
- Manual success criterion 1.3 (H2 file exists on disk) already confirmed by the user during Phase 1's original implementation.
- RFC 9457 ProblemDetail rule from lessons.md: not applicable to this phase (no controllers/exception handling introduced yet).
