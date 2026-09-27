# S-01: Organizator zakłada wydarzenie i widzi je — Implementation Plan

## Overview

Wprowadzamy pierwszy pionowy przekrój produktu: organizator tworzy wydarzenie (nazwa, opis, zakres dat, dni tygodnia, spodziewana liczba uczestników, własny e-mail) i natychmiast widzi je z pełną wygenerowaną listą kandydackich terminów. Świadomie bez maila (F-01) i bez przycisku kopiowania linku (S-02) — to najwęższy możliwy dowód, że zapis danych i wire-up działają.

## Current State Analysis

- Backend to goły szkielet Spring Boot 4.1 (`ZgraniApplication.java`), zero domenowych kontrolerów, zero drivera bazy danych w `pom.xml`.
- `application.yml` ma już `spring.mvc.problemdetails.enabled: true` i `/actuator/health` — RFC 9457 jest już włączone globalnie.
- Frontend nieobecny w repo (`tech-stack.md` deklaruje React+TS jako plan, nie stan).
- Brak jakiejkolwiek trwałości (JPA, DB driver) w `pom.xml`.

## Desired End State

Organizator może wysłać `POST /api/events` z danymi wydarzenia i dostać w odpowiedzi pełny obiekt wydarzenia wraz z wygenerowaną listą kandydackich terminów; `GET /api/events/{id}` zwraca to samo dla istniejącego wydarzenia. Dane przeżywają restart aplikacji (plik H2 na dysku). Weryfikacja: `curl -X POST` tworzy wydarzenie, `curl -X GET` po zwróconym `id` zwraca identyczne dane po restarcie procesu.

### Key Discoveries:

- `pom.xml:32-53` — brak `spring-boot-starter-data-jpa` i drivera H2, trzeba dodać oba.
- `application.yml:7-9` — `problemdetails.enabled: true` już ustawione, więc `@ExceptionHandler` zwracający `ProblemDetail.forStatusAndDetail(...)` wystarczy bez dodatkowej konfiguracji (zgodnie z `context/foundation/lessons.md`).
- PRD FR-002: terminy to całe dni z zakresu dat × wybrane dni tygodnia, twardy limit 30 na wydarzenie.
- PRD NFR: adres wydarzenia nieodgadywalny przez zgadywanie kolejnych identyfikatorów → losowy UUID v4 jako klucz.

## What We're NOT Doing

- Wysyłka maila z linkiem (F-01, osobny change).
- Przycisk/endpoint kopiowania linku ze strony wydarzenia (S-02).
- Jakikolwiek frontend/UI — tylko REST API w tym slice.
- Krótszy, "ładniejszy" token linku zamiast UUID — świadomie odłożone na osobny, mały slice w przyszłości (decyzja użytkownika podczas planowania).
- Rozszerzona walidacja (górne limity liczby uczestników, długości pól) — to następny slice, jeśli będzie potrzebny; tu tylko podstawowa walidacja.
- Managed Postgres na Render — H2 plikowe wystarcza na ten etap i tę skalę.
- Uczestnicy, dostępność, ranking, potwierdzenia, finalizacja — wszystko to kolejne slice'y (S-02 do S-06).

## Implementation Approach

Trzy fazy budują od dołu w górę: trwałość → logika domenowa → API. Każda faza jest samodzielnie weryfikowalna i commitowana osobno, zgodnie z konwencją `/10x-implement <change-id> phase N`.

## Critical Implementation Details

**Lazy loading w `EventService`**: `Event.candidateTerms` to domyślnie leniwa (`FetchType.LAZY`) kolekcja `@OneToMany`. Metody `EventService.create`/`findById` muszą być `@Transactional`, inaczej mapowanie do `EventResponse` poza granicą transakcji Hibernate rzuci `LazyInitializationException` przy próbie odczytania listy terminów.

## Phase 1: Fundament trwałości

### Overview

Dodajemy JPA + H2 plikowe, encje `Event` i `CandidateTerm`, repozytoria. Bez logiki biznesowej — czysty szkielet danych.

### Changes Required:

#### 1. Zależności Maven

**File**: `pom.xml`

**Intent**: Dodać `spring-boot-starter-data-jpa` i driver H2, żeby aplikacja mogła w ogóle rozmawiać z bazą.

**Contract**: Dwie nowe `<dependency>` w istniejącej `<dependencies>` sekcji — `org.springframework.boot:spring-boot-starter-data-jpa` i `com.h2database:h2` (runtime scope).

#### 2. Konfiguracja bazy

**File**: `src/main/resources/application.yml`

**Intent**: Skonfigurować H2 w trybie plikowym (nie in-memory), żeby dane przeżyły restart procesu, plus `ddl-auto` dopasowane do braku migracji na tym etapie.

**Contract**: Nowa sekcja `spring.datasource` (`url: jdbc:h2:file:./data/zgrani`, `driver-class-name: org.h2.Driver`) oraz `spring.jpa.hibernate.ddl-auto: update` (brak narzędzia migracji w tym slice — świadomie, `ddl-auto: update` wystarcza na jedną encję na start).

#### 3. Encja Event

**File**: `src/main/java/com/example/zgrani/event/Event.java`

**Intent**: Reprezentować wydarzenie zgodnie z FR-001 — nazwa, opcjonalny opis, zakres dat, wybrane dni tygodnia, spodziewana liczba uczestników, e-mail organizatora.

**Contract**: `@Entity` z `@Id` typu `UUID` (generowany aplikacyjnie, nie sekwencją bazy — patrz Phase 3), pola: `name` (NOT NULL), `description` (nullable), `dateRangeStart`/`dateRangeEnd` (`LocalDate`, NOT NULL), `daysOfWeek` (kolekcja `DayOfWeek`, `@ElementCollection`), `expectedParticipants` (int, NOT NULL), `organizerEmail` (NOT NULL), `createdAt` (`Instant`). Relacja `@OneToMany` do `CandidateTerm` (cascade all, orphanRemoval).

#### 4. Encja CandidateTerm

**File**: `src/main/java/com/example/zgrani/event/CandidateTerm.java`

**Intent**: Reprezentować jeden kandydacki termin (cały dzień) należący do wydarzenia — fundament pod przyszły ranking (S-04), tu tylko przechowanie daty.

**Contract**: `@Entity` z `@Id` (`Long`, sekwencyjny — wewnętrzny, nigdy nie eksponowany jako link), `date` (`LocalDate`, NOT NULL), `@ManyToOne` do `Event`.

#### 5. Repozytorium

**File**: `src/main/java/com/example/zgrani/event/EventRepository.java`

**Intent**: Standardowy dostęp CRUD do `Event` (z dociągnięciem `CandidateTerm` przez relację).

**Contract**: `interface EventRepository extends JpaRepository<Event, UUID> {}`.

### Success Criteria:

#### Automated Verification:

- Build przechodzi: `./mvnw clean compile`
- Kontekst Springa startuje bez błędów: `./mvnw test -Dtest=ZgraniApplicationTests`

#### Manual Verification:

- Po starcie aplikacji plik `./data/zgrani.mv.db` istnieje na dysku

**Implementation Note**: Po zakończeniu tej fazy i przejściu automatycznej weryfikacji, zatrzymaj się i poczekaj na potwierdzenie manualne przed przejściem do kolejnej fazy.

---

## Phase 2: Logika generowania terminów

### Overview

Serwis domenowy zamieniający zakres dat + wybrane dni tygodnia na listę `CandidateTerm`, z twardym limitem 30 (FR-002) egzekwowanym jako odrzucenie żądania.

### Changes Required:

#### 1. Serwis generowania terminów

**File**: `src/main/java/com/example/zgrani/event/TermGenerationService.java`

**Intent**: Wyliczyć wszystkie daty w `[start, end]`, których dzień tygodnia znajduje się w wybranym zbiorze, w kolejności chronologicznej.

**Contract**: Metoda `List<LocalDate> generate(LocalDate start, LocalDate end, Set<DayOfWeek> daysOfWeek)`. Rzuca `TooManyTermsException` (nowy, niezaznaczony `RuntimeException`) gdy wynikowa lista przekracza 30 elementów — wyjątek niesie samą liczbę wygenerowanych terminów, do użycia w komunikacie błędu w Phase 3.

#### 2. Testy jednostkowe

**File**: `src/test/java/com/example/zgrani/event/TermGenerationServiceTest.java`

**Intent**: Pokryć: zakres bez pasujących dni tygodnia (pusta lista), dokładnie 30 dopasowań (akceptowane), 31 dopasowań (wyjątek), pojedynczy dzień w zakresie, `start == end`.

**Contract**: Standardowy JUnit 5 test class, jeden `@Test` per przypadek z listy w Intent.

### Success Criteria:

#### Automated Verification:

- Testy jednostkowe przechodzą: `./mvnw test -Dtest=TermGenerationServiceTest`

#### Manual Verification:

- (brak — logika czysto domenowa, w pełni pokryta testami automatycznymi)

**Implementation Note**: Po zakończeniu tej fazy i przejściu automatycznej weryfikacji, zatrzymaj się i poczekaj na potwierdzenie manualne przed przejściem do kolejnej fazy.

---

## Phase 3: REST API

### Overview

Kontroler wystawiający `POST /api/events` i `GET /api/events/{id}`, z walidacją Bean Validation i mapowaniem błędów na RFC 9457 `ProblemDetail`.

### Changes Required:

#### 1. Request DTO

**File**: `src/main/java/com/example/zgrani/event/CreateEventRequest.java`

**Intent**: Wejściowy kształt `POST /api/events` z podstawową walidacją (Bean Validation): pola wymagane, format e-maila, `expectedParticipants > 0`, niepusty zbiór dni tygodnia.

**Contract**: Java `record` z adnotacjami `@NotBlank name`, `@Email @NotBlank organizerEmail`, `@NotNull dateRangeStart`/`dateRangeEnd`, `@NotEmpty Set<DayOfWeek> daysOfWeek`, `@Min(1) int expectedParticipants`, `description` bez adnotacji (opcjonalne). Niestandardowa walidacja `dateRangeEnd >= dateRangeStart` żyje w kontrolerze/serwisie (Bean Validation sam nie wyrazi porównania dwóch pól bez dodatkowej adnotacji klasowej — prościej sprawdzić to jawnie w warstwie serwisu).

#### 2. Response DTO

**File**: `src/main/java/com/example/zgrani/event/EventResponse.java`

**Intent**: Pełny kształt zwracany przez `POST` i `GET` — metadane wydarzenia + pełna lista wygenerowanych terminów (decyzja z planowania: to jest dowód, że FR-002 działa).

**Contract**: Java `record`: `id` (UUID), `name`, `description`, `dateRangeStart`, `dateRangeEnd`, `daysOfWeek`, `expectedParticipants`, `organizerEmail`, `candidateTerms` (`List<LocalDate>`, posortowana chronologicznie), `createdAt`.

#### 3. Serwis aplikacyjny

**File**: `src/main/java/com/example/zgrani/event/EventService.java`

**Intent**: Orkiestracja: waliduje zakres dat, woła `TermGenerationService`, buduje i zapisuje `Event` + `CandidateTerm`, mapuje do `EventResponse`.

**Contract**: `EventResponse create(CreateEventRequest request)` i `EventResponse findById(UUID id)` (rzuca `EventNotFoundException`, nowy niezaznaczony `RuntimeException`, gdy brak wpisu).

#### 4. Kontroler

**File**: `src/main/java/com/example/zgrani/event/EventController.java`

**Intent**: Wystawić dwa endpointy zgodnie z FR-001/FR-002/US-02 (część).

**Contract**: `@RestController @RequestMapping("/api/events")`; `POST` (`@Valid @RequestBody CreateEventRequest`) → `201 Created` z `EventResponse`; `GET /{id}` → `200 OK` z `EventResponse` albo propagacja `EventNotFoundException` do handlera.

#### 5. Obsługa błędów

**File**: `src/main/java/com/example/zgrani/event/EventExceptionHandler.java`

**Intent**: Zmapować `TooManyTermsException` → `422`, `EventNotFoundException` → `404`, `IllegalArgumentException` (zły zakres dat) → `400` — wszystko przez `ProblemDetail`, zgodnie z regułą w `context/foundation/lessons.md`.

**Contract**: `@RestControllerAdvice` z trzema `@ExceptionHandler`, każdy zwracający `ProblemDetail.forStatusAndDetail(...)` z czytelnym `detail`.

#### 6. Testy integracyjne

**File**: `src/test/java/com/example/zgrani/event/EventControllerTest.java`

**Intent**: Pokryć happy path (create → 201 z poprawną listą terminów, get → 200 z tymi samymi danymi), walidację (brak wymaganego pola → 400 ProblemDetail), limit 30 (→ 422 ProblemDetail), nieistniejące id (→ 404 ProblemDetail).

**Contract**: `@SpringBootTest @AutoConfigureMockMvc`, jeden `@Test` per scenariusz z listy w Intent, asercje na status i na `application/problem+json` content-type dla ścieżek błędu.

### Success Criteria:

#### Automated Verification:

- Pełny build przechodzi: `./mvnw clean verify`
- Testy integracyjne przechodzą: `./mvnw test -Dtest=EventControllerTest`
- Testy jednostkowe wciąż przechodzą: `./mvnw test -Dtest=TermGenerationServiceTest`

#### Manual Verification:

- `curl -X POST http://localhost:8080/api/events` z poprawnym JSON-em zwraca `201` z pełną listą terminów
- `curl http://localhost:8080/api/events/{id}` po restarcie procesu zwraca identyczne dane (dowód trwałości)
- Polskie znaki diakrytyczne w `name`/`description` wracają nieuszkodzone w odpowiedzi JSON

**Implementation Note**: Po zakończeniu tej fazy i przejściu automatycznej weryfikacji, zatrzymaj się i poczekaj na potwierdzenie manualne — to ostatnia faza slice'a.

---

## Testing Strategy

### Unit Tests:

- `TermGenerationService`: brzegowe przypadki zakresu dat × dni tygodnia, limit 30.

### Integration Tests:

- `EventController`: happy path create+get, wszystkie ścieżki błędów (400/404/422) z asercją na kształt `ProblemDetail`.

### Manual Testing Steps:

1. Uruchom aplikację lokalnie, `curl -X POST` z pełnym poprawnym payloadem — sprawdź `201` i listę terminów.
2. `curl GET /api/events/{id}` — sprawdź zgodność z odpowiedzią z kroku 1.
3. Zrestartuj proces aplikacji, powtórz krok 2 — dane muszą przeżyć restart.
4. Wyślij payload z polskimi znakami w `name` — sprawdź, że wracają nieuszkodzone.

## Performance Considerations

Brak — skala PRD (dziesiątki wydarzeń, do 30 terminów) nie wymaga optymalizacji na tym etapie.

## Migration Notes

Brak istniejących danych do migracji — pierwsza encja w projekcie, `ddl-auto: update` tworzy schemat od zera.

## References

- Roadmap: `context/foundation/roadmap.md` (S-01, `create-and-view-event`)
- PRD: `context/foundation/prd.md` (FR-001, FR-002, US-02 częściowo)
- Reguła błędów API: `context/foundation/lessons.md`

## Progress

> Convention: `- [ ]` pending, `- [x]` done. Append ` — <commit sha>` when a step lands. Do not rename step titles. See `references/progress-format.md`.

### Phase 1: Fundament trwałości

#### Automated

- [x] 1.1 Build przechodzi: `./mvnw clean compile` — a7ff866
- [x] 1.2 Kontekst Springa startuje bez błędów: `./mvnw test -Dtest=ZgraniApplicationTests` — a7ff866

#### Manual

- [x] 1.3 Po starcie aplikacji plik `./data/zgrani.mv.db` istnieje na dysku — a7ff866

### Phase 2: Logika generowania terminów

#### Automated

- [x] 2.1 Testy jednostkowe przechodzą: `./mvnw test -Dtest=TermGenerationServiceTest` — 22e5804

### Phase 3: REST API

#### Automated

- [x] 3.1 Pełny build przechodzi: `./mvnw clean verify`
- [x] 3.2 Testy integracyjne przechodzą: `./mvnw test -Dtest=EventControllerTest`
- [x] 3.3 Testy jednostkowe wciąż przechodzą: `./mvnw test -Dtest=TermGenerationServiceTest`

#### Manual

- [ ] 3.4 `curl -X POST` zwraca `201` z pełną listą terminów
- [ ] 3.5 `curl GET` po restarcie procesu zwraca identyczne dane
- [ ] 3.6 Polskie znaki diakrytyczne wracają nieuszkodzone
