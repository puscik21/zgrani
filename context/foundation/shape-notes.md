---
project: Zgrani
context_type: greenfield
product_type: web-app
target_scale:
  users: small
  qps: poniżej 1
  data_volume: kilkadziesiąt wydarzeń, do 30 terminów i 20 uczestników każde
timeline_budget:
  mvp_weeks: 1
  hard_deadline: 2026-09-14
  after_hours_only: true
created: 2026-08-19
updated: 2026-09-06
checkpoint:
  current_phase: 8
  phases_completed: [1, 2, 3, 4, 5, 6, 7]
  gray_areas_resolved:
    - topic: "główny ból"
      decision: "rozmycie wątku i wielokrotne powroty do niego; ograniczenia ujawniają się partiami, po każdej kolizji kolejna runda"
    - topic: "persona główna"
      decision: "uczestnik (nie organizator) — powód: ryzyko adopcyjne, jeden zniechęcony uczestnik zawraca całą grupę na komunikator"
    - topic: "zasięg"
      decision: "własna ekipa i podobne nieformalne grupy znajomych, 4-10 osób"
    - topic: "tożsamość i role"
      decision: "jeden nieodgadywalny link do wydarzenia dla wszystkich; rolę rozstrzyga adres e-mail, a organizatorem jest ten, kto wydarzenie utworzył. Odrzucono osobny sekretny link zarządzający oraz linki per osoba: jeden rodzaj tokenu i jeden mechanizm tożsamości zamiast trzech. Przeglądarka zapamiętuje podany adres, więc powrót nie wymaga niczego wpisywać ponownie"
    - topic: "podszywanie się"
      decision: "ktoś, kto zna cudzy adres e-mail i ma link, może odpowiedzieć za tę osobę — organizatora włącznie. Świadomie akceptowane: w gronie znajomych to nie jest zagrożenie, a zabezpieczenia wymagałyby kont. → dalsze fazy"
    - topic: "kanał powiadomień"
      decision: "mail obowiązkowy dla wszystkich — bez niego celowana prośba o potwierdzenie nie istnieje; bez opcji wyciszenia w MVP, cała poczta jest transakcyjna"
    - topic: "widoczność odpowiedzi"
      decision: "przekrój odsłania się uczestnikowi dopiero po kliknięciu „Gotowe” (jak w ankiecie), by uniknąć owczego pędu; korekta własnej odpowiedzi później pozostaje dozwolona, aż do finalizacji"
    - topic: "strona główna i odnajdywanie wydarzeń"
      decision: "strona główna to wyłącznie formularz tworzenia wydarzenia. Nie ma listy ani siatki wydarzeń — byłaby albo publiczna (co przeczy nieujawnianiu niczego bez tokenu), albo wymagałaby kont. Wydarzenia są osiągalne wyłącznie przez link. Organizator dostaje ten link mailem, uczestnicy mają go w wątku na komunikatorze"
    - topic: "kto może zakładać wydarzenia"
      decision: "każdy, bez żadnej bramki — aplikacja nie zna pojęcia użytkownika uprawnionego"
    - topic: "wzorce dostępności"
      decision: "wchodzą do MVP w wariancie UI-owym (masowe zaznaczanie po dniach tygodnia w obrębie jednego wydarzenia, nic nie jest zapamiętywane między wydarzeniami); uzasadnienie: wygoda persony głównej jest wąskim gardłem adopcji. Wariant trwały wymaga kont — odrzucony"
    - topic: "przypomnienie przed wydarzeniem"
      decision: "jedno przypomnienie, T-1 dzień, bez stylowania, do osób z „tak” oraz „może”. T-1 tydzień odrzucone: przy terminie bliższym niż tydzień nigdy by nie odpaliło. Świadomie na końcu kolejności realizacji jako pozycja łatwa do wycięcia"
    - topic: "reguły per osoba z rozluźnianiem"
      decision: "poza MVP — nie jest to funkcja, a domenowa decyzja o tym, kiedy systemowi wolno złamać twarde ograniczenie. → dalsze fazy"
    - topic: "nazwa i język interfejsu"
      decision: "Zgrani; interfejs wyłącznie po polsku, jeden zaszyty język, bez warstwy i18n — produkt jest celowany w konkretną polskojęzyczną grupę znajomych. Kod i identyfikatory pozostają angielskie"
    - topic: "budżet czasowy"
      decision: "sesja shapingu prowadzona była przy błędnym założeniu, że jest 21 sierpnia. Faktyczny start realizacji: 2026-09-05, twarda data 2026-09-14, cel 2026-09-13 — nieco ponad tydzień po godzinach, realnie 12-16h. Wszystkie pozycje nice-to-have należy uznać za mało prawdopodobne"
    - topic: "uczestnicy kluczowi"
      decision: "w MVP nikt nie jest kluczowy i ranking nigdy nie odrzuca terminu — pokazuje kolejność, decyduje organizator. Wariant „każdy kluczowy” odrzucono: oznaczałby odrzucenie każdego terminu, w którym ktokolwiek powiedział „nie”, a premisą produktu jest to, że termin idealny zwykle nie istnieje. Uzasadnienie produktowe użytkownika: spotkanie połowy ekipy to nadal spotkanie"
    - topic: "ziarnistość terminów"
      decision: "całe dni, bez godzin. Odrzucono wariant dwuetapowy (najpierw dzień, potem osobna runda na godzinę) — wymagałby drugiej rundy od uczestników, czyli tego, co produkt ma likwidować"
    - topic: "punktacja odpowiedzi"
      decision: "tak=1, może=0, nie=0. Odrzucono 2/1/0 (termin mógł wygrać głosami osób niepewnych, mimo że gdzie indziej na pewno przyszłoby więcej ludzi) oraz punkty ujemne za „nie”. Ranking odpowiada na jedno pytanie: ile osób na pewno przyjdzie. Remisy rozstrzyga wcześniejszy termin"
    - topic: "postać podpowiedzi"
      decision: "nie „pojedyncza zmiana odblokowująca lepszy termin”, lecz „terminy, którym niepewne odpowiedzi dają szansę przebicia lidera” plus prośba do wszystkich niepewnych na takim terminie, z liczbą brakujących potwierdzeń. Powód: przy punktacji 1/0/0 pojedyncza zmiana zwykle daje tylko remis, więc reguła w pierwotnym brzmieniu nie wysłałaby żadnego maila dokładnie w sytuacjach, w których jest potrzebna. Gdy brakuje dokładnie jednego potwierdzenia, mail mówi wprost, że wszystko zależy od adresata"
    - topic: "kierunek proponowanej zmiany"
      decision: "prośba dotyczy wyłącznie zmiany „może” → „tak”. Zmiana „nie” → „tak” byłaby proszeniem człowieka o odwołanie zobowiązania i uczyniłaby te maile nachalnymi"
    - topic: "nazwy trzech opcji w formularzu"
      decision: "„Będę” / „Może” / „Nie dam rady”. Rozważono i odrzucono etykietę „Dopytaj mnie” dla środkowej opcji: opisywała mechanikę wprost, ale zobowiązywała społecznie — kto ma opory przed byciem dopytywanym, wybierze zamiast niej odmowę, a wtedy termin, który mógł wygrać, umiera. Środkowa opcja musi być tania społecznie, bo persona główna wybiera ją na telefonie w przelocie"
    - topic: "wykrycie kompletu odpowiedzi"
      decision: "czekamy na pełną spodziewaną liczbę. Zawieszenie na 6 z 8 nie jest groźne, bo mail jest wygodą, a źródłem prawdy jest strona z licznikiem. Świadomy koszt wspólnego linku: o osobach, które nigdy nie weszły, nie wiadomo nic — nawet że istnieją — więc nie da się ich pogonić. To jest właściwe uzasadnienie dla kont w dalszych fazach"
    - topic: "edycja wydarzenia po utworzeniu"
      decision: "zdegradowana do nice-to-have i umieszczona na końcu kolejki wobec ciasnego budżetu; nie występuje w ścieżce demonstracyjnej. Gdy powstanie: nazwa i opis zawsze, zmiana terminów tylko dopóki nikt nie zatwierdził odpowiedzi. To ograniczenie sprawia, że dopisanie tej funkcji później pozostaje tanie, bo znika problem osieroconych odpowiedzi"
    - topic: "ochrona niezatwierdzonej odpowiedzi"
      decision: "zaznaczenia żyją w przeglądarce do kliknięcia „Gotowe”, potem jeden zapis. Odrzucono autozapis po każdym kliknięciu: tworzyłby dwa stany odpowiedzi (szkic i zatwierdzona), flagę w modelu i konieczność pomijania szkiców w rankingu i liczniku. Konsekwencja wyłapana po wygenerowaniu PRD: przetrwanie zaznaczeń po zamknięciu karty spadło do nice-to-have (FR-010), bo bez autozapisu jedyną drogą jest pamięć przeglądarki, a stawką jest powtórzenie minuty pracy, nie utrata dostępu. Guardrail mówi teraz wyłącznie o dostępie do wydarzenia"
    - topic: "granice zakresu i domknięcia"
      decision: "najwyżej 30 terminów na wydarzenie — górna granica sensownego klikania. Wydarzenie staje się przeszłe po minięciu terminu sfinalizowanego albo, jeśli nigdy go nie sfinalizowano, po minięciu ostatniego z proponowanych terminów. Jest to porównanie dat przy odczycie strony, nie zadanie w harmonogramie i nie ręczna czynność organizatora"
    - topic: "zamrożenie po finalizacji"
      decision: "po sfinalizowaniu terminu odpowiedzi nie da się już zmieniać, a ranking nie jest przeliczany. Uproszczenie: nie trzeba rozstrzygać, co zrobić, gdy zmiana odpowiedzi unieważnia właśnie ogłoszony termin"
    - topic: "notatka przy odpowiedzi"
      decision: "uczestnik może dopisać krótką notatkę do własnej odpowiedzi dla danego terminu („będę 19:30+”). Zastępuje większość wartości godzin, wyrażając je tekstem zamiast modelem danych. Ograniczniki: czysty tekst, twardy limit długości, brak wpływu na ranking"
    - topic: "powtórna prośba o potwierdzenie"
      decision: "o dany termin pytamy daną osobę najwyżej raz (FR-024). Realna górna granica bez tej zasady to jeden mail na parę (osoba × termin), więc nie jest to spam bez dna; jedyny naprawdę uciążliwy przypadek to powtórzenie tego samego pytania, gdy adresat je zignorował, a ranking przeliczył się z powodu cudzej zmiany. Odrzucono pełny antyspam jako droższy od jednego znacznika. Prośba o nowy termin, na którym ta osoba też ma „Może”, wysyła się normalnie — to nowa informacja"
    - topic: "postać widoku odpowiedzi"
      decision: "lista w kolejności rankingu (FR-025), wiersz na termin, liczba i imiona osób z „Będę”, pod nimi osoby z „Może” oznaczone jako nierozstrzygnięte. Odrzucono mapę cieplną: byłaby drugą siatką 30 × 20 w produkcie, który ma już jedną i ma z nią otwarty problem 360 px, a wynik trzeba z niej dopiero wyczytać — lista jest tą samą regułą rankingu widoczną wprost"
    - topic: "cofanie finalizacji"
      decision: "poza MVP. Kaskada opisana w Parked jest mechanicznie tania (każdy krok już istnieje), ale znosi jedyny moment, po którym stan produktu przestaje się ruszać, i zmusza każdy ekran do obsługi stanu „właśnie się odwiesiło”. Realia domykają sprawę: termin został już ogłoszony na komunikatorze i tam wraca rozmowa. Wyjście awaryjne w MVP to nowe wydarzenie w węższym zakresie"
  frs_drafted: 25
  quality_check_status: warned
---

# Shape notes

## Seed

Wejście do sesji: `roadmapa-featurek.md` — fazowana lista funkcji przygotowana przed sesją.
Reguła biznesowa zapisana w roadmapie, **zrewidowana w fazie 5** (patrz `## Business Logic`):

> System wybiera termin maksymalizujący liczbę uczestników mogących wziąć udział — ważąc odpowiedzi tak/może/nie, odrzucając terminy bez uczestników kluczowych — oraz wskazuje pojedynczą zmianę odpowiedzi, która odblokowałaby lepszy termin.

Z tego brzmienia nie przetrwały trzy rzeczy: ważenie „może”, odrzucanie terminów, oraz pojedynczość wskazywanej zmiany.

Ograniczenia realizacji: start 2026-09-05, twarda data 2026-09-14, praca po godzinach, realnie 12-16h.

## Vision & Problem Statement

Grupa znajomych (4-10 osób) próbuje ustalić termin wspólnego spotkania — u autora konkretnie wieczoru z planszówkami — przez wątek na komunikatorze. Ktoś rzuca datę albo kilka dat naraz, sypią się reakcje, po czym wychodzi kolizja, której nikt nie przewidział: „wtedy nie mogę", „Mateusz w środy ma zawsze zajęcia". Pada kolejna partia dat i cykl się powtarza. Koszt: wątek rozmywa się na wiele rund i trzeba do niego wracać po parę razy, nikt nie ma pełnego przekroju kto może kiedy, a gdy termin w końcu padnie i jest odległy, część osób zdąży o nim zapomnieć.

Ograniczenia, które unieważniają kolejne propozycje, **istnieją od początku i są znane** — tylko nikt ich nie zebrał, więc grupa spala jedną pełną rundę na każde z nich. Produkt nie jest ładniejszym głosowaniem nad datami; jest zebraniem tych ograniczeń równolegle i jednorazowo, zamiast wydobywania ich po kolei kosztem kolejnych rund. Wtórnie: ustalony termin przestaje być wpisem, do którego trzeba doscrollować.

## User & Persona

**Persona główna: uczestnik** — osoba z grupy znajomych, która dostaje prośbę o podanie dostępności. Nie szuka narzędzia, nie zakłada konta, nie ma motywacji do wysiłku. Sięga po nią w momencie, gdy dostaje link od kolegi z ekipy, zwykle na telefonie, w przelocie.

Uczestnik został wybrany personą główną **świadomie, ponad organizatora**, mimo że organizator odczuwa ból mocniej. Powód: w nieformalnej grupie nikt nie narzuca narzędzia odgórnie. Jeden uczestnik, któremu wypełnianie wyda się mozolne, zawraca całą grupę na komunikator („gdzieś tam była ta appka, ale zróbmy to na szybko na messie"). Adopcja jest wąskim gardłem, nie wygoda organizatora. Wynika z tego twarde wymaganie: wejście bez konta i minimum interakcji potrzebnych do udzielenia odpowiedzi.

**Persona wtórna: organizator** — członek tej samej grupy, który zakłada wydarzenie, rozsyła link i domyka termin. To zwykle autor. Obsługiwany po uczestniku.

## Access Control

Brak kont i brak logowania. Dostęp opiera się w całości na **jednym nieodgadywalnym linku do wydarzenia**, tym samym dla wszystkich, a rolę rozstrzyga podany adres e-mail.

Odrzucono dwa wcześniejsze warianty. **Linki per osoba**, generowane przez organizatora z listy imion: organizator musiałby wpisać osiem imion i rozesłać osiem różnych wiadomości, co zabija adopcję po jego stronie. **Osobny sekretny link zarządzający** obok linku zapraszającego: drugi rodzaj tokenu do wygenerowania, dostarczenia i nieutracenia, podczas gdy adres e-mail i tak jest już zbierany i pełni tę samą funkcję lepiej — bo adresu nie da się zgubić przez zamknięcie karty.

**Każdy, kto ma link, podaje przy pierwszym wejściu imię (lub nick) oraz adres e-mail — obowiązkowo.** Przeglądarka zapamiętuje ten adres, więc powrót nie wymaga wpisywania go ponownie; przy pustej pamięci lub na innym urządzeniu wystarczy podać ten sam adres, by odzyskać własną odpowiedź. Adres jest zatem jednocześnie tożsamością i kanałem powiadomień.

**Organizatorem jest ten, czyj adres utworzył wydarzenie.** Po utworzeniu jest od razu przeniesiony na stronę wydarzenia i rozpoznany, a link do niego dostaje mailem — dzięki czemu wydarzenia nie da się bezpowrotnie stracić przez zamknięcie karty przed skopiowaniem adresu. Może: widzieć pełny przekrój odpowiedzi od początku (nie podlega blokadzie odsłaniania), finalizować termin, oraz — gdy funkcja powstanie — edytować wydarzenie.

**Uczestnik** to każdy inny adres. Może: podać i korygować własną dostępność aż do finalizacji, dopisać notatkę do własnej odpowiedzi, odrzucić prośbę o potwierdzenie, zobaczyć przekrój grupy — ale dopiero po kliknięciu „Gotowe". Nie może: finalizować terminu ani zmieniać wydarzenia.

**Świadomie przyjęte koszty.** Ktoś, kto zna cudzy adres e-mail i ma link, może odpowiedzieć za tę osobę — organizatora włącznie. Ta sama osoba może też odpowiedzieć dwa razy pod różnymi adresami. Nie ma imiennej listy brakujących odpowiedzi, tylko licznik wobec liczby podanej przez organizatora, a o osobach, które nigdy nie weszły, nie wiadomo nic. W nieformalnej grupie znajomych żadne z tego nie jest zagrożeniem, a usunięcie ich wymagałoby kont — czyli rzeczy, której persona główna nie zaakceptuje.

Nie ma listy ani siatki wydarzeń nigdzie w aplikacji: byłaby albo publiczna, albo wymagałaby kont. Wejście na adres wydarzenia bez ważnego tokenu nie ujawnia niczego.

## Success Criteria

### Primary

Grupa znajomych autora ustala termin realnego spotkania w całości w aplikacji, **bez powrotu na komunikator po dodatkowe rundy dat**. Miara: jedno prawdziwe wydarzenie, w którym zaproszeni oddają odpowiedzi, a nikt nie musi pytać autora, jak z tego korzystać.

### Secondary

- Prośba o potwierdzenie **choć raz realnie zmienia wynik** — ktoś zmienia „Może" na „Będę" i wygrywa inny termin niż przed zmianą. To jest moment demonstracyjny projektu.
- Ustalony termin przestaje uciekać z pamięci: przypomnienie na dzień przed dociera do uczestników.
- Organizator dowiaduje się o komplecie odpowiedzi bez zaglądania na stronę.

### Guardrails

- Uczestnik dochodzi od otwarcia linku do oddanej odpowiedzi w **maksymalnie czterech interakcjach** i bez zakładania czegokolwiek. To wąskie gardło adopcji i nadrzędne kryterium przy każdym sporze projektowym.
- Żadna rola nie wymaga konta ani hasła.
- Utrata dostępu do wydarzenia nie jest możliwa przez zamknięcie karty przeglądarki.
- Projekt jest gotowy do 2026-09-13, twardo do 2026-09-14.

## MVP scope

**Ścieżka rdzeniowa (must-have, w kolejności realizacji):**

1. Utworzenie wydarzenia: nazwa, opis, zakres dat, wybór dni tygodnia, spodziewana liczba osób, mail organizatora. Generowanie terminów z zakresu zamiast klikania w kalendarzu.
2. Jeden link do wydarzenia — do skopiowania na stronie i wysłany mailem organizatorowi.
3. Formularz uczestnika: imię, mail, oznaczanie „Będę” / „Może” / „Nie dam rady” z masowym zaznaczaniem po dniach tygodnia, stan trzymany w przeglądarce do kliknięcia „Gotowe”.
4. Ranking terminów według liczby potwierdzeń, pokazany jako **lista w kolejności rankingu** — wiersz na termin, liczba i imiona osób z „Będę”, pod nimi osoby z „Może” oznaczone jako nierozstrzygnięte. Przekrój grupy widoczny po „Gotowe”. Odrzucono siatkę typu mapa cieplna: to druga siatka 30 × 20 na produkcie, który ma już jedną i ma z nią otwarty problem 360 px, a wynik trzeba z niej dopiero wyczytać, podczas gdy lista jest tą samą regułą rankingu widoczną wprost.
5. Wskazanie terminów, którym niepewne odpowiedzi dają szansę przebicia lidera, plus mail do wszystkich niepewnych na takim terminie — **o dany termin pytamy daną osobę najwyżej raz**.
6. Finalizacja terminu przez organizatora, zamrażająca odpowiedzi.
7. Test E2E ścieżki rdzeniowej + CI/CD.

**Nice-to-have, w kolejności wycinania od dołu.** Wobec budżetu 12-16h należy je traktować jako mało prawdopodobne:

8. Mail do organizatora „wszyscy odpowiedzieli”.
9. Przycisk „nie mogę zmienić” przy prośbie o potwierdzenie + widoczność tego u organizatora.
10. Przypomnienie mailowe na dzień przed terminem, do osób z „Będę” i „Może”.
11. Notatka uczestnika przy odpowiedzi dla danego terminu.
12. Edycja wydarzenia po utworzeniu.
13. Przetrwanie niezatwierdzonych zaznaczeń po zamknięciu karty. Zdegradowane z must-have po tym, jak wypadł autozapis: jedyną drogą została pamięć przeglądarki, a stawką jest powtórzenie minuty pracy, nie utrata dostępu do wydarzenia.

Pozycje 8-13 są celowo ostatnie: każda jest samodzielna i wycięcie jej nie psuje niczego powyżej. Pozycja 10 jest jedyną rzeczą w całym MVP działającą bez użytkownika, więc niesie ukryty koszt testowania zachowania zależnego od czasu — i to ona przesądza, czy w projekcie w ogóle pojawi się harmonogram zadań. Pozycja 11 jest najtańsza z całej piątki (po stronie danych to jedna kolumna przy istniejącym rekordzie odpowiedzi, koszt leży wyłącznie w UI), więc można ją zrobić poza kolejnością jako tanie wzbogacenie demonstracji.

**Poza MVP mimo obecności w pierwotnym szkicu ścieżki:** lista aktywnych i przeszłych wydarzeń na stronie głównej, mail przy każdym pojedynczym wypełnieniu, oznaczanie uczestników kluczowych, oddzielny sekretny link zarządzający.

Zamknięcie wydarzenia po dacie jest **porównaniem dat przy odczycie strony** — nie zadaniem w harmonogramie i nie ręczną czynnością organizatora. W tej formie kosztuje jeden warunek.

## User Stories

### US-01: Uczestnik podaje swoją dostępność

**Given** jestem w grupie znajomych i dostałem na komunikatorze link do wydarzenia
**When** otwieram go na telefonie, podaję imię i mail, zaznaczam masowo dni tygodnia, poprawiam kilka pojedynczych terminów i klikam „Gotowe”
**Then** moja odpowiedź jest zapisana i widzę przekrój odpowiedzi całej grupy

#### Acceptance Criteria

- Nie zakładam konta ani nie ustawiam hasła
- Od otwarcia linku do kliknięcia „Gotowe” wykonuję najwyżej cztery interakcje
- Przekrój grupy nie jest widoczny przed kliknięciem „Gotowe”
- Wracając na ten sam link jestem rozpoznany bez podawania czegokolwiek, a na innym urządzeniu wystarczy ten sam adres e-mail
- Do czasu finalizacji mogę zmienić swoją odpowiedź

### US-02: Organizator zakłada wydarzenie i rozsyła je

**Given** chcę umówić wieczór planszówkowy z ośmioma znajomymi
**When** podaję nazwę, zakres dat, dni tygodnia, spodziewaną liczbę osób i swój mail
**Then** trafiam od razu na stronę wydarzenia z linkiem do skopiowania, a ten sam link dostaję mailem

#### Acceptance Criteria

- Terminy powstają z zakresu i wybranych dni tygodnia, bez klikania każdego dnia osobno
- Jest jeden link dla całej grupy, nie osobny dla każdej osoby i nie osobny dla mnie
- Zamknięcie karty przed skopiowaniem linku nie oznacza utraty wydarzenia
- Jestem rozpoznany jako organizator, bo wydarzenie powstało z mojego adresu
- Widzę odpowiedzi od początku, nie musząc sam odpowiadać

### US-03: System prosi o potwierdzenie tam, gdzie to zmieni wynik

**Given** odpowiedzieli już wszyscy i żaden termin nie zebrał kompletu potwierdzeń
**When** system porównuje potencjał każdego terminu z liczbą potwierdzeń obecnego lidera
**Then** każda osoba, która na takim terminie zaznaczyła „Może", dostaje maila z tym konkretnym terminem i liczbą brakujących potwierdzeń

#### Acceptance Criteria

- Wskazany jest konkretny termin, a nie lista możliwości
- Gdy brakuje dokładnie jednego potwierdzenia, mail mówi wprost, że wszystko zależy od adresata
- Adresat może potwierdzić albo odrzucić prośbę
- Organizator widzi wynik: potwierdzenie albo odmowa
- Ranking po zmianie odpowiedzi odzwierciedla ją natychmiast

## Functional Requirements

### Tworzenie wydarzenia

- FR-001: Organizator can create an event by giving its name, an optional short description, a date range, the days of the week to include, the expected number of participants, and own e-mail address. Priority: must-have
- FR-002: System generates the candidate terms as whole days derived from the date range and the selected days of the week, up to a ceiling of 30 terms per event. Priority: must-have
- FR-003: System delivers the event's link to the creator's e-mail address at creation time. Priority: must-have
- FR-004: Organizator can copy the event's link from the event page. Priority: must-have
- FR-005: Organizator can edit the event's name and description at any time, and can change its candidate terms only while no answer has been submitted. Priority: nice-to-have

### Udzielanie dostępności

- FR-006: Uczestnik can open the event through its link and declare own name and e-mail address. Priority: must-have
- FR-007: System recognises a returning person by the e-mail address they declared, and returns them to their own answer. Priority: must-have
- FR-008: Uczestnik can mark each candidate term as attending, undecided or unavailable. Priority: must-have
- FR-009: Uczestnik can mark every term falling on a chosen day of the week in one action, and afterwards correct individual terms. Priority: must-have
- FR-010: Uczestnik does not lose markings already made if the page is closed before the answer is submitted. Priority: nice-to-have
- FR-011: Uczestnik can submit own answer, after which it counts towards the ranking and the group cross-section becomes visible to them. Priority: must-have
- FR-012: Uczestnik can correct own answer at any time before the event is finalized. Priority: must-have
- FR-013: Uczestnik can attach a short plain-text note to own answer for a given term, visible to the rest of the group and without effect on the ranking. Priority: nice-to-have

### Ranking i prośba o potwierdzenie

- FR-014: System ranks the candidate terms by the number of participants who declared attendance, placing the earlier term first when counts are equal, and excludes no term. Priority: must-have
- FR-015: Organizator can see the full cross-section of answers without submitting an answer. Priority: must-have
- FR-016: System identifies the candidate terms whose declared and undecided attendance together exceed the declared attendance of the current leader. Priority: must-have
- FR-017: System asks every participant who is undecided on such a term to confirm, stating how many confirmations that term still needs to win, and stating when that participant is the only one it depends on. Priority: must-have
- FR-018: Uczestnik can decline the request to confirm, and Organizator can see that it was declined. Priority: nice-to-have
- FR-024: System asks a given participant about a given term at most once, however many times the ranking is later recalculated. Priority: must-have
- FR-025: System presents the candidate terms as a list in ranking order, each term showing who declared attendance and, marked as unresolved, who remained undecided. Priority: must-have

### Finalizacja i domknięcie

- FR-019: Organizator can finalize one term as the agreed one, after which no answer can be changed and the ranking is no longer recalculated. Priority: must-have
- FR-020: System shows the finalized term to everyone holding the event's link. Priority: must-have
- FR-021: System presents the event as past once its finalized term has passed, or, if no term was ever finalized, once the last of its candidate terms has passed. Priority: must-have
- FR-022: System notifies the organizer once the number of submitted answers reaches the expected participant count. Priority: nice-to-have
- FR-023: System sends a reminder one day before the finalized term to everyone who declared attendance or remained undecided. Priority: nice-to-have

## Business Logic

System porządkuje proponowane terminy według liczby uczestników, którzy potwierdzili obecność, a następnie wskazuje terminy, którym odpowiedzi niepewne dają szansę przebicia obecnego lidera, i prosi o potwierdzenie te osoby, od których to zależy.

Punktacja to `będę = 1`, `dopytaj mnie = 0`, `nie dam rady = 0`. Odrzucono wcześniejszy wariant `2/1/0`, w którym odpowiedź niepewna sama z siebie podbijała termin: prowadził do sytuacji, w której wygrywał termin wypchnięty w górę głosami osób niezdecydowanych, mimo że na innym terminie **na pewno** stawiłoby się więcej ludzi. Przy obecnej punktacji ranking odpowiada na jedno pytanie, które da się obronić jednym zdaniem: **ile osób na pewno przyjdzie**. Odrzucono także punkty ujemne za odmowę — odmowa nie jest przewinieniem, jest brakiem obecności, a ranking nigdy nie wyklucza terminu.

Odpowiedź niepewna nie punktuje, ale nie jest bezużyteczna: to ona wprowadza termin do puli kandydatów do awansu. Bez niej termin z czterema niezdecydowanymi wyglądałby na martwy i nikt by o niego nie zapytał. Konsekwencja jest taka, że **prośba o potwierdzenie nie jest ozdobą, lecz jedyną drogą, którą niepewność zamienia się w wynik** — reguła biznesowa i wyróżnik produktu to ta sama rzecz.

Prośba idzie do wszystkich niezdecydowanych na danym terminie i podaje liczbę brakujących potwierdzeń; gdy brakuje dokładnie jednego, mail mówi wprost, że wszystko zależy od adresata. Prośba dotyczy wyłącznie zmiany niepewności w potwierdzenie — nigdy odwołania odmowy, bo to byłoby proszeniem człowieka o wycofanie zobowiązania. Notatki uczestników nie wpływają na punktację. Przy równej liczbie potwierdzeń wygrywa termin wcześniejszy: grupa spotyka się szybciej i mniej osób zdąży zapomnieć.

## Non-Functional Requirements

- Uczestnik dochodzi od otwarcia linku do zatwierdzonej odpowiedzi w najwyżej czterech interakcjach, bez zakładania konta.
- Strona uczestnika jest czytelna i obsługiwalna przy szerokości ekranu 360 px, bez przewijania w poziomie, dla wydarzenia z 30 terminami.
- Zatwierdzona odpowiedź jest odzwierciedlona w rankingu natychmiast — widzi ją ta sama odsłona strony, która ją przyjęła.
- Wiadomość e-mail dociera do adresata w ciągu 5 minut od zdarzenia, które ją wywołało.
- Polskie znaki diakrytyczne pozostają nieuszkodzone we wszystkich treściach widocznych dla użytkownika, łącznie z treścią wiadomości e-mail.
- Wydarzenia i odpowiedzi pozostają dostępne po ponownym uruchomieniu usługi.
- Wejście na adres wydarzenia bez ważnego tokenu nie ujawnia żadnej informacji o wydarzeniu ani o jego uczestnikach.
- Adres wydarzenia nie daje się odgadnąć przez zgadywanie kolejnych identyfikatorów.
- Wydarzenie z 20 uczestnikami i 30 terminami wyświetla się w czasie poniżej jednej sekundy.

## Non-Goals

Świadomie poza zakresem MVP, wprost i na piśmie:

- Konta użytkowników, hasła i logowanie w jakiejkolwiek postaci
- Ochrona przed podszyciem się pod inną osobę
- Lista lub katalog wydarzeń gdziekolwiek w aplikacji
- Integracja z kalendarzem — zarówno eksport, jak i import zajętości
- Strefy czasowe; wszyscy uczestnicy są w tej samej
- Godziny spotkania; terminem jest cały dzień
- Aplikacja mobilna
- Wydarzenia cykliczne
- Mapy, lokalizacja, pogoda
- Zdjęcia i bogaty edytor opisu
- Czat wewnątrz wydarzenia
- Głosowanie nad miejscem równolegle z terminem
- Jakiekolwiek zastosowanie AI

## Forward: tech-stack

Rozstrzygnięcia techniczne podniesione w trakcie sesji, **celowo nieumieszczone w PRD** — należą do kolejnego etapu, a wpisane tam zamknęłyby wybór, zanim ten etap w ogóle się zacznie:

- Brak kolejek i przetwarzania asynchronicznego; wysyłka poczty najprostszym dostępnym sposobem
- Gotowa biblioteka komponentów zamiast pisania stylów od zera; przy budżecie 12-16h godziny na stylowanie są zabrane wprost wyróżnikom produktu
- Wbudowany mechanizm harmonogramu zamiast własnego — potrzebny wyłącznie wtedy, gdy przypomnienie T-1 zmieści się w zakresie
- **Java po stronie backendu — decyzja świadoma, podjęta 2026-09-06 przed uruchomieniem selektora stacku.** Front w rodzinie TypeScript/React. Obawa o brak wsparcia okazała się bezpodstawna: rejestr starterów ma kartę `spring` z `bootstrapper_confidence: verified` — najwyższym z trzech poziomów — i czterema bramkami jakości zdanymi. Realny koszt leży gdzie indziej: kontrakt `tech-stack.md` nie ma pola na front, więc React powstaje poza łańcuchem niezależnie od wyboru. Decyzja towarzysząca: **jeden artefakt wdrożeniowy** — Spring Boot serwuje zbudowany bundel Reacta jako zasoby statyczne, a dev-serwer Vite dostaje proxy na backend, żeby nie stracić hot reloadu. Uzasadnienie: celem kursowym jest nauka pracy z agentem we własnym stacku, a nie w cudzym — stack, którego autor nie zna, przenosi ryzyko z bootstrapu na całą resztę realizacji

## Open Questions

Wszystkie cztery pytania podniesione w kontroli jakości zostały zamknięte: powrót uczestnika rozstrzyga rozpoznawanie po adresie e-mail, finalizacja zamraża odpowiedzi, przypomnienie idzie do potwierdzonych i niezdecydowanych, a etykiety opcji brzmią „Będę” / „Może” / „Nie dam rady”. Rozmieszczenie trzech różnej długości etykiet w siatce do 30 terminów przy szerokości 360 px jest kwestią układu do rozstrzygnięcia przy widocznym ekranie, nie na tym etapie.

Otwarte pozostaje jedno, i jest to pytanie o realizację, nie o produkt:

1. **Czy w budżecie 12-16h zmieści się cała ścieżka rdzeniowa wraz z testem E2E i CI/CD.** Jeśli nie, pierwszą rzeczą do przemyślenia jest nie skrócenie ścieżki, lecz uproszczenie punktu 3 — formularz uczestnika jest najbogatszym w interakcje ekranem w całym produkcie.

## Parked — do rozstrzygnięcia w dalszych fazach

Pomysły podniesione w trakcie sesji, świadomie nierozstrzygnięte w MVP:

1. **Wzorce dostępności zapamiętywane między wydarzeniami** — wariant trwały, wymaga kont uczestników. Wariant UI-owy wszedł do MVP; ten zostaje.
2. **Reguły cykliczne per osoba z rozluźnianiem** („Mateusz zawsze zajęty w środy… chyba że inaczej się nie da, to rozważamy wariant bez Mateusza"). Nie funkcja, a domenowa decyzja: kiedy systemowi wolno złamać twarde ograniczenie.
3. **Ustalanie godziny po ustaleniu dnia** — druga runda głosowania w węższym zakresie. Odrzucone w MVP właśnie dlatego, że jest drugą rundą; wymagałoby dowodu, że notatki przy odpowiedziach nie wystarczają.
4. **Dorzucenie ustalonego terminu do kalendarza** (eksport ICS, potem Google Calendar) — drugie pół bólu „termin ucieka z pamięci”; pierwsze pół adresuje przypomnienie T-1.
5. **Przypomnienie T-1 tydzień** jako druga, wcześniejsza wysyłka — dopiero gdy istnieje potrzeba planowania, nie tylko pamiętania.
6. **Powiadomienie przy każdym wypełnieniu ankiety** — chciane przez organizatora, ale przy ośmiu osobach to osiem maili na wydarzenie. Właściwą postacią jest podsumowanie zbiorcze („od Twojej ostatniej wizyty odpowiedziały trzy osoby”), czyli debouncing.
7. **Lista „Twoje wydarzenia (z tej przeglądarki)”** — linki zapamiętane lokalnie; udogodnienie, nigdy źródło prawdy (nim jest link w mailu).
8. **Uczestnicy kluczowi** jako filtr nakładany na gotowy ranking.
9. **Powiadomienie o utykającym głosowaniu** — „odpowiedziało 6 z 8, został tydzień do pierwszego terminu, może pora zamknąć”.
10. **Cofanie finalizacji.** Kaskada: zatwierdzony termin → ktoś zmienia odpowiedź → termin przestaje być zatwierdzony, organizator dostaje powiadomienie → ranking i prośby o potwierdzenie idą od nowa → organizator zatwierdza nowy termin. Mechanicznie tanie, bo każdy krok już istnieje: cofnięcie to wyczyszczenie jednego pola, przeliczenie i prośby dzieją się przy każdej zmianie, a przypomnienie T-1 czyta zatwierdzony termin w momencie wysyłki. Prawdziwy koszt leży poza kodem: znosi jedyny moment, po którym stan przestaje się ruszać, więc każdy ekran musi obsłużyć stan „właśnie się odwiesiło" — a w rzeczywistości termin i tak został już ogłoszony na komunikatorze i tam wraca rozmowa. MVP-owe wyjście awaryjne to nowe wydarzenie w węższym zakresie.
11. **Strukturalne „spóźnię się"** zamiast notatki tekstowej, rozstrzygające remisy między terminami. Wchodzi dopiero po zobaczeniu, że notatki realnie się tak grupują — nie przed.
12. **Mapa cieplna dostępności** jako alternatywa dla listy. Ładniejsza na zrzucie ekranu; wymaga rozwiązania problemu 360 px po raz drugi.
13. **Ograniczanie liczby próśb o potwierdzenie** ponad zasadę „raz o dany termin" — realna górna granica to jeden mail na parę (osoba × termin), więc antyspam nie ma czego pilnować dopóki wydarzenia mają kilkanaście terminów.
14. **Konta użytkowników** — właściwe uzasadnienie nie brzmi „bo tak się robi”, tylko: bez nich nie da się pogonić osób, które nigdy nie weszły, ani zapamiętać wzorców dostępności między wydarzeniami, ani zabezpieczyć przed podszyciem się.
