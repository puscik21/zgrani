# S-01: Organizator zakłada wydarzenie i widzi je — Plan Brief

> Full plan: `context/changes/create-and-view-event/plan.md`

## What & Why

Pierwszy pionowy przekrój produktu (gwiazda przewodnia roadmapy): organizator tworzy wydarzenie i od razu je widzi z pełną wygenerowaną listą kandydackich terminów. Dowodzi, że zapis danych i wire-up działają, zanim dojdzie druga strona (uczestnik).

## Starting Point

Backend to goły szkielet Spring Boot 4.1 — zero domenowych kontrolerów, zero drivera bazy danych. `problemdetails.enabled: true` już włączone globalnie. Frontend nieobecny w repo.

## Desired End State

`POST /api/events` tworzy wydarzenie i zwraca je wraz z pełną listą terminów; `GET /api/events/{id}` zwraca to samo, także po restarcie procesu (dane w pliku H2 na dysku).

## Key Decisions Made

| Decision                              | Choice                                    | Why (1 sentence)                                                                 |
| -------------------------------------- | ------------------------------------------ | --------------------------------------------------------------------------------- |
| Trwałość danych                       | H2 plikowe (nie in-memory, nie Postgres)   | Przeżywa restart procesu bez zakładania osobnej usługi DB na Render.              |
| Warstwa prezentacji dla S-01           | Tylko REST API (bez UI)                    | Najszybszy dowód, że zapis+odczyt działają; UI to osobna decyzja poza tym slice.  |
| Identyfikator/link wydarzenia          | UUID v4                                    | Nieodgadywalny od razu, bez dodatkowej logiki tokenów — krótszy token to osobny, przyszły slice (decyzja użytkownika). |
| Limit 30 terminów przekroczony         | Odrzucenie żądania (422 ProblemDetail)     | Jednoznaczny sygnał do poprawy wejścia zamiast cichego ucinania listy.            |
| Walidacja pól tworzenia                | Podstawowa (wymagane pola, format, zakres) | Rozszerzona walidacja (górne limity) to świadomie następny slice, nie ten.        |
| Kształt odpowiedzi API                 | Pełne metadane + pełna lista terminów      | Realny dowód, że FR-002 (generowanie terminów) faktycznie działa.                |
| Zakres cięcia przy braku czasu         | Nic nie ciąć w S-01                        | To już najwęższa możliwa wersja gwiazdy przewodniej.                              |

## Scope

**In scope:**
- Encje `Event`/`CandidateTerm`, H2 plikowe, repozytorium
- Serwis generowania terminów (zakres dat × dni tygodnia, limit 30)
- `POST /api/events`, `GET /api/events/{id}`, walidacja Bean Validation
- Mapowanie błędów na RFC 9457 `ProblemDetail`
- Testy jednostkowe (generowanie terminów) + integracyjne (kontroler)

**Out of scope:**
- Mail z linkiem (F-01), przycisk kopiowania linku (S-02)
- Jakikolwiek frontend/UI
- Krótszy token linku, rozszerzona walidacja — przyszłe slice'y
- Uczestnicy, dostępność, ranking, potwierdzenia, finalizacja (S-02–S-06)

## Architecture / Approach

Trzy fazy budują od dołu w górę: trwałość (JPA + H2 + encje) → logika domenowa (generowanie terminów, czysta i w pełni testowalna jednostkowo) → REST API (kontroler + walidacja + obsługa błędów + testy integracyjne). Każda faza jest samodzielnie weryfikowalna i commitowana osobno.

## Phases at a Glance

| Phase                              | What it delivers                                    | Key risk                                                   |
| ------------------------------------ | ---------------------------------------------------- | ------------------------------------------------------------ |
| 1. Fundament trwałości              | Encje, repozytorium, H2 plikowe skonfigurowane        | Błędna konfiguracja `ddl-auto`/ścieżki pliku                |
| 2. Logika generowania terminów      | Serwis + testy jednostkowe, limit 30 wyegzekwowany    | Błąd na granicach zakresu (start==end, dokładnie 30)        |
| 3. REST API                         | Endpointy + walidacja + ProblemDetail + testy integr. | Niespójność między walidacją Bean Validation a limitem 30   |

**Prerequisites:** brak — S-01 nie ma prerequisitów w roadmapie.
**Estimated effort:** ~1 sesja, 3 fazy.

## Open Risks & Assumptions

- `ddl-auto: update` bez narzędzia migracji — świadomie akceptowalne przy jednej encji na start; do rewizji, gdy pojawi się druga migracja schematu.
- Krótszy token linku pozostaje jako zanotowana, nieplanowana jeszcze przyszła praca (nie w `context/foundation/roadmap.md` — do rozważenia przy `/10x-roadmap` regeneracji albo jako ad-hoc change, gdy przyjdzie czas).

## Success Criteria (Summary)

- Organizator tworzy wydarzenie przez `POST /api/events` i widzi pełną listę wygenerowanych terminów w odpowiedzi.
- Dane przeżywają restart procesu aplikacji.
- Polskie znaki diakrytyczne wracają nieuszkodzone.
