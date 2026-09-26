---
project: "Zgrani"
version: 1
status: draft
created: 2026-09-27
updated: 2026-09-27
prd_version: 1
main_goal: speed
top_blocker: time
milestone_id: core-availability-loop
milestone_seq: 1
milestone_status: open
---

# Roadmap: Zgrani

> Wygenerowane z `context/foundation/prd.md` + auto-zbadanego baseline'u kodu.
> Edytuj w miejscu; archiwizuj przy pełnej regeneracji.
> Slice'y poniżej są w kolejności zależności. Tabela „At a glance” to indeks.

## Milestone

**M-1: Rdzeniowa ścieżka ustalania terminu** — Status: open

- **Intent:** Dostarczyć pełną ścieżkę must-have PRD — od założenia wydarzenia, przez zadeklarowanie dostępności i ranking, po celowaną prośbę o potwierdzenie i finalizację — tak, żeby prawdziwa grupa znajomych mogła domknąć termin w całości w produkcie, zgodnie z podstawowym kryterium sukcesu z PRD.
- **Source materials:** `context/foundation/prd.md` (v1)
- **Done when:** F-01 oraz S-01 do S-06 poniżej mają status `done`.
- **Scope anchors:** FR-001–FR-025, US-01, US-02, US-03 (pełne PRD — patrz `## Functional Requirements` w `prd.md`)

## Vision recap

Grupa znajomych (4-10 osób) ustala termin spotkania przez wątek na komunikatorze: kolejne propozycje dat rozbijają się o ograniczenia, które istniały od początku, ale nikt ich nie zebrał naraz — stąd wielokrotne rundy i rozmyty wątek. Produkt zbiera te ograniczenia równolegle i jednorazowo zamiast wydobywać je po kolei. Persona główna to uczestnik, nie organizator — bo to jeden zniechęcony uczestnik zawraca całą grupę na komunikator, więc wejście bez konta i minimum interakcji jest twardym wymaganiem.

**Uwaga do ramowania:** PRD niesie twardy termin `hard_deadline: 2026-09-14` — ten dzień już minął (dziś jest 2026-09-27). Priorytet `speed` został świadomie utrzymany mimo to (potwierdzone przez użytkownika) — realizacja trwa dalej najszybszą możliwą ścieżką, tylko bez fikcji, że deadline wciąż obowiązuje kalendarzowo.

## North star

**S-01: Organizator zakłada wydarzenie i widzi je** — najmniejszy koniec-do-końca krok, jaki można zweryfikować pierwszy: dowodzi, że zapis danych i wire-up działają, zanim dojdzie druga strona (uczestnik).

> Gwiazda przewodnia (ang. north star) oznacza tu: najmniejszy przepływ, którego udane dostarczenie jako pierwsze pokazuje, że reszta ma sens budować dalej — dlatego stoi na początku kolejności, nie na końcu.

## At a glance

| ID   | Change ID                       | Outcome (user can …)                                              | Prerequisites | PRD refs                          | Status   |
| ---- | -------------------------------- | ------------------------------------------------------------------ | -------------- | ---------------------------------- | -------- |
| S-01 | create-and-view-event             | (north star) Organizator tworzy wydarzenie i od razu je widzi      | —              | FR-001, FR-002, US-02 (część)      | ready    |
| F-01 | outbound-email-capability         | (foundation) Aplikacja potrafi wysłać jednego maila                | —              | NFR (mail ≤5 min, PL znaki)        | ready    |
| S-02 | deliver-and-copy-event-link       | Organizator dostaje link mailem i kopiuje go ze strony wydarzenia  | S-01, F-01     | FR-003, FR-004, US-02 (reszta)     | proposed |
| S-03 | participant-submits-availability  | Uczestnik podaje i koryguje własną dostępność                     | S-01           | FR-006, 007, 008, 009, 011, 012, US-01 | proposed |
| S-04 | term-ranking-and-crosssection     | Organizator i uczestnik widzą ranking terminów i przekrój odpowiedzi | S-03         | FR-014, 015, 025, US-01, US-02     | proposed |
| S-05 | targeted-confirmation-request     | Niezdecydowany uczestnik dostaje celowaną prośbę o potwierdzenie   | S-04, F-01     | FR-016, 017, 018, 024, US-03       | proposed |
| S-06 | finalize-and-close-event          | Organizator finalizuje termin; wydarzenie staje się przeszłe po dacie | S-04        | FR-019, 020, 021, US-03            | proposed |

## Streams

Pomoc nawigacyjna — kanoniczna kolejność wciąż żyje w grafie zależności poniżej.

| Stream | Theme                       | Chain                | Note                                                                 |
| ------ | ---------------------------- | --------------------- | ---------------------------------------------------------------------- |
| A      | Rdzeń wydarzenia              | `S-01` → `S-02`       | Gwiazda przewodnia + jej dopełnienie (link mailem/kopiowanie); dołącza się do Stream B przy `S-02` |
| B      | Fundament mailowy              | `F-01`                | Odblokowuje `S-02` i `S-05` — patrz Note tamtych wierszy               |
| C      | Odpowiedzi i ranking           | `S-03` → `S-04`       | Główna pętla wartości z PRD; startuje po `S-01` (Stream A)             |
| D      | Domknięcie                    | `S-05`, `S-06`        | Oba odgałęziają się od `S-04` (Stream C); `S-05` domyka się dodatkowo z fundamentem mailowym (Stream B) |

## Baseline

Stan na `2026-09-27` (auto-zbadany + potwierdzony przez użytkownika). Foundations poniżej zakładają ten stan i go nie odtwarzają.

- **Frontend:** absent — brak jakiegokolwiek `package.json`/Reacta w repo; `tech-stack.md` deklaruje React+TS jako plan, nie stan.
- **Backend / API:** partial — szkielet Spring Boot 4.1 (`ZgraniApplication.java`), zero domenowych kontrolerów.
- **Data:** absent — brak drivera DB/ORM/migracji w `pom.xml`.
- **Auth:** per tech-stack.md — `has_auth: false`, świadomie brak (produkt stoi na jednym nieodgadywalnym linku).
- **Deploy / infra:** present — Render Free już wdrożony i żywy (`context/deployment/deploy-plan.md`), health check zielony.
- **Observability:** partial — `/actuator/health` obecny, RFC 9457 `ProblemDetail` wymuszony (`context/foundation/lessons.md`); brak logowania strukturalnego poza tym.

## Foundations

### F-01: Minimalna zdolność wysyłki maila

- **Outcome:** (foundation) Aplikacja potrafi wysłać pojedynczego maila (tekst/HTML) z zachowaniem polskich znaków diakrytycznych, dostarczanego w ciągu 5 minut od zdarzenia, które go wywołało.
- **Change ID:** outbound-email-capability
- **PRD refs:** NFR („Wiadomość dociera do adresata w ciągu 5 minut”, „Polskie znaki diakrytyczne pozostają nieuszkodzone”), `tech-stack.md` („wysyłka poczty najprostszym dostępnym sposobem”)
- **Unlocks:** S-02 (FR-003 — mail z linkiem), S-05 (FR-017 — prośby o potwierdzenie)
- **Prerequisites:** —
- **Parallel with:** S-01
- **Blockers:** —
- **Unknowns:**
  - Jaki dokładnie mechanizm wysyłki (Spring Mail + zewnętrzny SMTP, dostawca transakcyjny)? — Owner: user. Block: no — `/10x-plan` może przyjąć najprostszy wariant zgodny z `tech-stack.md` bez czekania na tę odpowiedź.
- **Risk:** Dwa późniejsze slice'y (S-02, S-05) zależą od tej zdolności — taniej ustalić ją raz niż importować ad hoc w każdym z nich osobno.
- **Status:** ready

## Slices

### S-01: Organizator zakłada wydarzenie i widzi je

- **Outcome:** organizator can create an event (nazwa, zakres dat, dni tygodnia, spodziewana liczba uczestników, własny e-mail) i od razu je zobaczyć — na stronie wydarzenia i/lub przez API.
- **Change ID:** create-and-view-event
- **PRD refs:** FR-001, FR-002, US-02 (częściowo — reszta w S-02)
- **Prerequisites:** —
- **Parallel with:** F-01
- **Blockers:** —
- **Unknowns:** —
- **Risk:** Celowo zawężone (decyzja użytkownika) do samego utworzenia i wyświetlenia — bez maila i przycisku kopiowania linku — żeby dostać najszybszy możliwy dowód, że zapis danych i wire-up działają, zanim dojdzie druga strona (uczestnik).
- **Status:** ready

### S-02: Organizator otrzymuje i kopiuje link do wydarzenia

- **Outcome:** organizator dostaje link do wydarzenia mailem w chwili utworzenia i może go skopiować ze strony wydarzenia.
- **Change ID:** deliver-and-copy-event-link
- **PRD refs:** FR-003, FR-004, US-02 (reszta)
- **Prerequisites:** S-01, F-01
- **Parallel with:** S-03
- **Blockers:** —
- **Unknowns:** —
- **Risk:** Zależy od F-01 — jeśli fundament mailowy się poślizgnie, mail czeka, ale samo kopiowanie linku ze strony nie zależy od poczty i może wejść pierwsze.
- **Status:** proposed

### S-03: Uczestnik podaje dostępność

- **Outcome:** uczestnik może otworzyć wydarzenie przez link, podać imię i e-mail, oznaczyć każdy termin (będę / może / nie dam rady), zaznaczyć masowo cały dzień tygodnia i poprawić pojedyncze terminy, zatwierdzić odpowiedź i skorygować ją później aż do finalizacji.
- **Change ID:** participant-submits-availability
- **PRD refs:** FR-006, FR-007, FR-008, FR-009, FR-011, FR-012, US-01
- **Prerequisites:** S-01
- **Parallel with:** S-02
- **Blockers:** —
- **Unknowns:**
  - Układ trzech różnej długości etykiet („Będę” / „Może” / „Nie dam rady”) w siatce do 30 terminów przy szerokości 360px (PRD `## Open Questions` #2) — Owner: user. Block: no — rozstrzyga się przy projektowaniu formularza, nie teraz.
- **Risk:** Najbogatszy w interakcje ekran w całym produkcie, na nim stoi guardrail czterech interakcji — jeśli budżet czasu się nie zmieści, to tu najpierw upraszczać, nie skracać ścieżkę (PRD wprost tak mówi).
- **Status:** proposed

### S-04: Ranking terminów i przekrój odpowiedzi

- **Outcome:** organizator (od razu) i uczestnik (po zatwierdzeniu własnej odpowiedzi) widzą kandydujące terminy jako uporządkowaną listę — wg liczby deklaracji „będę”, remisy rozstrzyga wcześniejszy termin — z widocznym kto jest zapisany, a kto pozostaje niezdecydowany.
- **Change ID:** term-ranking-and-crosssection
- **PRD refs:** FR-014, FR-015, FR-025, US-01 (obietnica „widzę przekrój”), US-02 (widoczność dla organizatora), `## Business Logic`
- **Prerequisites:** S-03
- **Parallel with:** —
- **Blockers:** —
- **Unknowns:** —
- **Risk:** To jest właściwe domknięcie US-01 i jedyne miejsce, gdzie odrzucona wcześniej reguła punktacji (2/1/0, ważenie niepewności) mogłaby po cichu wrócić — implementacja musi trzymać się reguły z PRD: liczy się wyłącznie potwierdzenie.
- **Status:** proposed

### S-05: System prosi o potwierdzenie tam, gdzie to zmieni wynik

- **Outcome:** uczestnik niezdecydowany na terminie, któremu potencjał (deklaracje + niepewni) przewyższa obecnego lidera, dostaje mailem celowaną prośbę o potwierdzenie z liczbą brakujących potwierdzeń (i informacją, gdy zależy wyłącznie od niego); może potwierdzić albo odmówić; ta sama para osoba×termin jest pytana najwyżej raz; organizator widzi wynik.
- **Change ID:** targeted-confirmation-request
- **PRD refs:** FR-016, FR-017, FR-018, FR-024, US-03, `## Business Logic`
- **Prerequisites:** S-04, F-01
- **Parallel with:** S-06
- **Blockers:** —
- **Unknowns:** —
- **Risk:** To jest wyróżnik produktu wg PRD („reguła domenowa i wyróżnik produktu to ta sama rzecz”) — inwestycja tu jest celowo głęboka (patrz ramowanie interview); błąd w regule podbija złe terminy albo zasypuje ludzi powtórkami próśb.
- **Status:** proposed

### S-06: Finalizacja terminu i domknięcie wydarzenia

- **Outcome:** organizator finalizuje jeden termin jako uzgodniony, po czym żadna odpowiedź nie może się już zmienić, a ranking przestaje być przeliczany; każdy z linkiem widzi sfinalizowany termin; wydarzenie staje się przeszłe po minięciu sfinalizowanego terminu (albo, gdy nigdy go nie sfinalizowano, po minięciu ostatniego kandydującego terminu).
- **Change ID:** finalize-and-close-event
- **PRD refs:** FR-019, FR-020, FR-021, US-03 (widoczność wyniku dla organizatora)
- **Prerequisites:** S-04
- **Parallel with:** S-05
- **Blockers:** —
- **Unknowns:** —
- **Risk:** Zamrożenie po finalizacji celowo nie obsługuje cofania (PRD `## Non-Goals`) — upraszcza model stanu, ale każda przyszła zmiana zdania wraca na komunikator, nie do produktu.
- **Status:** proposed

## Backlog Handoff

| Roadmap ID | Change ID                       | Suggested issue title                                               | Ready for `/10x-plan` | Notes                              |
| ---------- | -------------------------------- | ---------------------------------------------------------------------- | --------------------- | ------------------------------------- |
| S-01       | create-and-view-event             | Organizator zakłada wydarzenie i widzi je (gwiazda przewodnia)          | yes                   | Uruchom `/10x-plan create-and-view-event` |
| F-01       | outbound-email-capability         | Minimalna zdolność wysyłki maila                                       | yes                   | Może iść równolegle z S-01             |
| S-02       | deliver-and-copy-event-link       | Organizator dostaje link mailem i kopiuje go                            | no                    | Czeka na S-01 i F-01                   |
| S-03       | participant-submits-availability  | Uczestnik podaje dostępność                                             | no                    | Czeka na S-01                          |
| S-04       | term-ranking-and-crosssection     | Ranking terminów i przekrój odpowiedzi                                  | no                    | Czeka na S-03                          |
| S-05       | targeted-confirmation-request     | Celowana prośba o potwierdzenie                                         | no                    | Czeka na S-04 i F-01                   |
| S-06       | finalize-and-close-event          | Finalizacja terminu i domknięcie wydarzenia                             | no                    | Czeka na S-04                          |

## Open Roadmap Questions

1. **Czy ścieżka rdzeniowa zmieści się w pozostałym budżecie czasowym?** Pierwotny twardy termin PRD (2026-09-14) już minął — pytanie żyje dalej, tylko bez fikcyjnej daty: przy realizacji po godzinach, na 19 wymagań must-have wraz z testami. — Owner: autor. Block: roadmap-wide, nie: nie wstrzymuje planowania pojedynczych slice'ów.
2. **Jak rozmieścić trzy opcje odpowiedzi w siatce do 30 terminów przy szerokości 360px?** (PRD `## Open Questions` #2, kopiowane) — Owner: autor. Block: S-03.

## Parked

- **FR-005 — edycja wydarzenia po utworzeniu** — Why parked: nice-to-have, świadomie na końcu kolejki wobec ciasnego budżetu (`shape-notes.md`); nie występuje w ścieżce demonstracyjnej.
- **FR-010 — przetrwanie niezatwierdzonych zaznaczeń po zamknięciu karty** — Why parked: nice-to-have; zdegradowane po odrzuceniu autozapisu — jedyną drogą pozostaje pamięć przeglądarki, a stawką jest powtórzenie minuty pracy, nie utrata dostępu.
- **FR-013 — notatka tekstowa przy odpowiedzi** — Why parked: nice-to-have; najtańsza z odłożonych pozycji (jedna kolumna danych, koszt tylko w UI) — dobry kandydat do zrobienia poza kolejnością, jeśli zostanie czas.
- **FR-022 — mail do organizatora „wszyscy odpowiedzieli”** — Why parked: nice-to-have, pierwsze w kolejności cięcia wg `shape-notes.md`.
- **FR-023 — przypomnienie T-1 dzień przed terminem** — Why parked: nice-to-have; jedyna rzecz w całym MVP działająca bez użytkownika — niesie ukryty koszt testowania zachowania zależnego od czasu, i to ona przesądzałaby, czy w projekcie w ogóle pojawia się harmonogram zadań.
- **Integracja z kalendarzem, godziny spotkania, strefy czasowe, wydarzenia cykliczne, cofanie finalizacji, mapa cieplna, konta użytkowników, lista wydarzeń, czat, głosowanie nad miejscem, AI** — Why parked: `## Non-Goals` w PRD, świadomie poza zakresem MVP.

## Milestone History

(puste — pierwszy milestone)

## Done

(puste na starcie generowania)
