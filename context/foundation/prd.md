---
project: "Zgrani"
version: 1
status: draft
created: 2026-09-05
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
---

# PRD: Zgrani

## Vision & Problem Statement

Grupa znajomych licząca od czterech do dziesięciu osób próbuje ustalić termin wspólnego spotkania — konkretnie wieczoru z planszówkami — przez wątek na komunikatorze. Ktoś rzuca datę albo kilka dat naraz, sypią się reakcje, po czym wychodzi kolizja, której nikt nie przewidział: „wtedy nie mogę", „Mateusz w środy ma zawsze zajęcia". Pada kolejna partia dat i cykl się powtarza. Koszt jest potrójny: wątek rozmywa się na wiele rund i trzeba do niego wracać po parę razy, nikt nie ma pełnego przekroju kto może kiedy, a gdy termin w końcu padnie i okaże się odległy, część osób zdąży o nim zapomnieć.

Ograniczenia, które unieważniają kolejne propozycje, istnieją od początku i są znane — tylko nikt ich nie zebrał, więc grupa spala jedną pełną rundę na każde z nich. Produkt nie jest ładniejszym głosowaniem nad datami; jest zebraniem tych ograniczeń równolegle i jednorazowo, zamiast wydobywania ich po kolei kosztem kolejnych rund. Wtórnie: ustalony termin przestaje być wpisem, do którego trzeba doscrollować.

## User & Persona

**Persona główna: uczestnik.** Osoba z nieformalnej grupy znajomych, która dostaje prośbę o podanie dostępności. Nie szuka narzędzia, nie zakłada konta, nie ma motywacji do wysiłku. Sięga po produkt w jednym konkretnym momencie: gdy dostaje link od kolegi z ekipy — zwykle na telefonie, w przelocie, między innymi sprawami.

Uczestnik został wybrany personą główną świadomie, ponad organizatora, mimo że organizator odczuwa ból mocniej. W nieformalnej grupie nikt nie narzuca narzędzia odgórnie: jeden uczestnik, któremu wypełnianie wyda się mozolne, zawraca całą grupę na komunikator („gdzieś tam była ta appka, ale zróbmy to na szybko"). Wąskim gardłem jest adopcja, nie wygoda organizatora. Wynika stąd twarde wymaganie: wejście bez konta i minimum interakcji potrzebnych do udzielenia odpowiedzi.

### Secondary persona

**Organizator.** Członek tej samej grupy, który zakłada wydarzenie, rozsyła link i domyka termin. Obsługiwany po uczestniku — wolno mu wymagać więcej wysiłku, bo to on ma interes w doprowadzeniu sprawy do końca.

## Success Criteria

### Primary

- Grupa ustala termin realnego spotkania w całości w produkcie, bez powrotu na komunikator po dodatkowe rundy dat. Miara: jedno prawdziwe wydarzenie, w którym zaproszeni oddają odpowiedzi, a nikt nie musi pytać organizatora, jak z tego korzystać.

### Secondary

- Prośba o potwierdzenie choć raz realnie zmienia wynik: ktoś zmienia odpowiedź niepewną na potwierdzenie i wygrywa inny termin niż przed zmianą.
- Ustalony termin przestaje uciekać z pamięci — przypomnienie na dzień przed dociera do uczestników.
- Organizator dowiaduje się o komplecie odpowiedzi bez zaglądania na stronę wydarzenia.

### Guardrails

- Uczestnik dochodzi od otwarcia linku do oddanej odpowiedzi w maksymalnie czterech interakcjach i bez zakładania czegokolwiek. To nadrzędne kryterium przy każdym sporze projektowym.
- Żadna rola nie wymaga konta ani hasła.
- Zamknięcie karty przeglądarki nie powoduje utraty dostępu do wydarzenia. Niezatwierdzone zaznaczenia mogą przepaść — ich zachowanie jest wygodą, nie gwarancją.
- Produkt jest gotowy do 2026-09-13, twardo do 2026-09-14.

## User Stories

### US-01: Uczestnik podaje swoją dostępność

- **Given** jestem w grupie znajomych i dostałem na komunikatorze link do wydarzenia
- **When** otwieram go na telefonie, podaję imię i adres e-mail, zaznaczam masowo dni tygodnia, poprawiam kilka pojedynczych terminów i zatwierdzam odpowiedź
- **Then** moja odpowiedź jest zapisana i widzę przekrój odpowiedzi całej grupy

#### Acceptance Criteria

- Nie zakładam konta ani nie ustawiam hasła
- Od otwarcia linku do zatwierdzenia wykonuję najwyżej cztery interakcje
- Przekrój grupy nie jest widoczny przed zatwierdzeniem własnej odpowiedzi
- Wracając na ten sam link z tego samego urządzenia jestem rozpoznany bez podawania czegokolwiek ponownie; z innego urządzenia wystarczy ten sam adres e-mail
- Do czasu finalizacji mogę zmienić swoją odpowiedź

### US-02: Organizator zakłada wydarzenie i rozsyła je

- **Given** chcę umówić wieczór planszówkowy z ośmioma znajomymi
- **When** podaję nazwę, zakres dat, dni tygodnia, spodziewaną liczbę osób i swój adres e-mail
- **Then** trafiam od razu na stronę wydarzenia z linkiem do skopiowania, a ten sam link dostaję pocztą

#### Acceptance Criteria

- Terminy powstają z zakresu dat i wybranych dni tygodnia, bez klikania każdego dnia osobno
- Jest jeden link dla całej grupy — nie osobny dla każdej osoby i nie osobny dla organizatora
- Zamknięcie karty przed skopiowaniem linku nie oznacza utraty wydarzenia
- Jestem rozpoznany jako organizator, ponieważ wydarzenie powstało z mojego adresu
- Widzę odpowiedzi od początku, nie musząc sam odpowiadać

### US-03: Produkt prosi o potwierdzenie tam, gdzie to zmieni wynik

- **Given** odpowiedzieli już wszyscy i żaden termin nie zebrał kompletu potwierdzeń
- **When** porównywany jest potencjał każdego terminu z liczbą potwierdzeń obecnego lidera
- **Then** każda osoba, która na takim terminie odpowiedziała niepewnie, dostaje wiadomość z tym konkretnym terminem i liczbą brakujących potwierdzeń

#### Acceptance Criteria

- Wskazany jest konkretny termin, a nie lista możliwości
- Gdy brakuje dokładnie jednego potwierdzenia, wiadomość mówi wprost, że wszystko zależy od adresata
- Adresat może potwierdzić albo odrzucić prośbę
- Organizator widzi wynik: potwierdzenie albo odmowę
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

## Non-Functional Requirements

- Uczestnik dochodzi od otwarcia linku do zatwierdzonej odpowiedzi w najwyżej czterech interakcjach, bez zakładania konta.
- Strona uczestnika pozostaje czytelna i obsługiwalna przy szerokości ekranu 360 px, bez przewijania w poziomie, dla wydarzenia liczącego 30 terminów.
- Zatwierdzona odpowiedź jest odzwierciedlona w rankingu natychmiast — widzi ją ta sama odsłona strony, która ją przyjęła.
- Wiadomość dociera do adresata w ciągu 5 minut od zdarzenia, które ją wywołało.
- Polskie znaki diakrytyczne pozostają nieuszkodzone we wszystkich treściach widocznych dla użytkownika, łącznie z treścią wysyłanych wiadomości.
- Wydarzenia i odpowiedzi pozostają dostępne po ponownym uruchomieniu produktu.
- Osoba, która nie dysponuje linkiem do wydarzenia, nie dowiaduje się o nim niczego — ani o jego istnieniu, ani o uczestnikach.
- Adres wydarzenia nie daje się odgadnąć przez zgadywanie kolejnych identyfikatorów.
- Wydarzenie z 20 uczestnikami i 30 terminami wyświetla się w czasie poniżej jednej sekundy.

## Business Logic

Produkt porządkuje proponowane terminy według liczby uczestników, którzy potwierdzili obecność, a następnie wskazuje terminy, którym odpowiedzi niepewne dają szansę przebicia obecnego lidera, i prosi o potwierdzenie te osoby, od których to zależy.

Wejściem reguły są odpowiedzi uczestników na każdy z proponowanych terminów, w trzech stopniach: potwierdzenie obecności, niepewność, odmowa. Do wyniku liczy się wyłącznie potwierdzenie. Odrzucono wcześniejszy wariant, w którym niepewność podbijała termin o połowę wartości potwierdzenia: prowadził do sytuacji, w której wygrywał termin wypchnięty w górę głosami osób niezdecydowanych, mimo że na innym terminie na pewno stawiłoby się więcej ludzi. Przy obecnej regule ranking odpowiada na jedno pytanie, które da się obronić jednym zdaniem: ile osób na pewno przyjdzie. Odrzucono także ujemną wartość odmowy — odmowa nie jest przewinieniem, jest brakiem obecności, a ranking nigdy nie wyklucza terminu. Przy równej liczbie potwierdzeń wyżej stoi termin wcześniejszy: grupa spotyka się szybciej i mniej osób zdąży zapomnieć.

Odpowiedź niepewna nie liczy się do wyniku, ale nie jest bezużyteczna — to ona wprowadza termin do puli kandydatów do awansu. Bez niej termin z czterema osobami niezdecydowanymi wyglądałby na martwy i nikt by o niego nie zapytał. Konsekwencja jest taka, że prośba o potwierdzenie nie jest ozdobą, lecz jedyną drogą, którą niepewność zamienia się w wynik: reguła domenowa i wyróżnik produktu to ta sama rzecz.

Wyjściem reguły są dwie rzeczy, które uczestnik i organizator napotykają w różnych miejscach. Organizator widzi uporządkowaną listę terminów wraz z tym, kto może kiedy. Osoby niezdecydowane na terminie mającym szansę wygrać dostają prośbę o potwierdzenie, z konkretnym terminem i liczbą brakujących potwierdzeń; gdy brakuje dokładnie jednego, prośba mówi wprost, że wszystko zależy od adresata. Prośba dotyczy wyłącznie zamiany niepewności w potwierdzenie i nigdy nie prosi o wycofanie odmowy — to byłoby proszeniem człowieka o odwołanie zobowiązania. Notatki dopisywane przez uczestników do własnych odpowiedzi nie wpływają na wynik.

## Access Control

Brak kont i brak logowania. Dostęp opiera się w całości na jednym nieodgadywalnym linku do wydarzenia, tym samym dla wszystkich, a rolę rozstrzyga podany adres e-mail.

Odrzucono dwa wcześniejsze warianty. Linki generowane osobno dla każdej zaproszonej osoby: organizator musiałby wpisać osiem imion i rozesłać osiem różnych wiadomości, co zabija adopcję po jego stronie. Osobny sekretny link zarządzający obok linku zapraszającego: drugi adres do wygenerowania, dostarczenia i nieutracenia, podczas gdy adres e-mail i tak jest zbierany i pełni tę samą funkcję lepiej, bo nie ginie wraz z zamkniętą kartą.

Każdy, kto ma link, podaje przy pierwszym wejściu imię (lub nick) oraz adres e-mail — obowiązkowo. Adres jest jednocześnie tożsamością i kanałem powiadomień. Powrót z tego samego urządzenia nie wymaga podawania go ponownie; z innego urządzenia wystarczy podać ten sam adres, by odzyskać własną odpowiedź.

**Organizator** to ten, czyj adres utworzył wydarzenie. Po utworzeniu jest od razu przeniesiony na stronę wydarzenia i rozpoznany, a link dostaje dodatkowo pocztą. Może: widzieć pełny przekrój odpowiedzi od początku, nie podlegając blokadzie odsłaniania; finalizować termin; oraz — gdy funkcja powstanie — edytować wydarzenie.

**Uczestnik** to każdy inny adres. Może: podać i korygować własną dostępność aż do finalizacji; dopisać notatkę do własnej odpowiedzi; odrzucić prośbę o potwierdzenie; zobaczyć przekrój grupy, ale dopiero po zatwierdzeniu własnej odpowiedzi. Nie może: finalizować terminu ani zmieniać wydarzenia.

Zakładać wydarzenia może każdy, bez żadnej bramki — produkt nie zna pojęcia użytkownika uprawnionego. Nie ma listy ani katalogu wydarzeń: wydarzenia są osiągalne wyłącznie przez link.

Świadomie przyjęte koszty tego modelu: ktoś, kto zna cudzy adres e-mail i ma link, może odpowiedzieć za tę osobę — organizatora włącznie; ta sama osoba może odpowiedzieć dwa razy pod różnymi adresami; nie ma imiennej listy brakujących odpowiedzi, a jedynie licznik wobec liczby podanej przez organizatora, więc o osobach, które nigdy nie weszły, nie wiadomo nic. W nieformalnej grupie znajomych żadne z tego nie jest zagrożeniem, a usunięcie ich wymagałoby kont — czyli rzeczy, której persona główna nie zaakceptuje.

## Non-Goals

**Niecele funkcjonalne:**

- Konta użytkowników, hasła i logowanie w jakiejkolwiek postaci — persona główna nie założy konta, a bez niej produkt nie zostanie zaadoptowany.
- Lista lub katalog wydarzeń gdziekolwiek w produkcie — byłaby albo publiczna, albo wymagałaby kont.
- Integracja z kalendarzem, zarówno eksport terminu, jak i import zajętości — najdroższa z odłożonych funkcji.
- Godziny spotkania; terminem jest cały dzień — godziny mnożą liczbę terminów i rozsadzają masowe zaznaczanie. Zastępuje je krótka notatka przy odpowiedzi.
- Strefy czasowe — wszyscy uczestnicy są w tej samej.
- Wydarzenia cykliczne.
- Cofanie finalizacji: zmiana odpowiedzi po ustaleniu terminu, odwieszenie ustalonego terminu i ponowna runda próśb o potwierdzenie — mechanicznie tanie, ale znosi jedyny moment, po którym stan produktu przestaje się ruszać, i każe każdemu ekranowi obsłużyć stan „właśnie się odwiesiło".
- Siatka dostępności typu mapa cieplna — zastępuje ją lista uporządkowana rankingiem.
- Ograniczanie liczby próśb o potwierdzenie ponad zasadę „raz o dany termin" — pozostałe wiadomości niosą nową informację.
- Oznaczanie uczestników kluczowych i odrzucanie terminów bez nich — reguła odrzucałaby każdy termin z choćby jedną odmową, a premisą produktu jest to, że termin idealny zwykle nie istnieje.
- Reguły dostępności zapamiętywane między wydarzeniami — wymagają trwałej tożsamości uczestnika.
- Mapy, lokalizacja, pogoda.
- Zdjęcia i bogaty edytor opisu.
- Czat wewnątrz wydarzenia — produkt nie zastępuje komunikatora, tylko wyjmuje z niego jedną czynność.
- Głosowanie nad miejscem równolegle z terminem.
- Jakiekolwiek zastosowanie AI.

**Niecele pozafunkcjonalne:**

- Ochrona przed podszyciem się pod inną osobę — świadomie przyjęty koszt braku kont, akceptowalny w gronie znajomych.
- Wielojęzyczność — produkt jest wyłącznie polskojęzyczny, bez warstwy tłumaczeń.
- Dostępność produktu jako aplikacji instalowanej na telefonie.

## Open Questions

1. **Czy ścieżka rdzeniowa zmieści się w budżecie czasowym?** Do 2026-09-14 pozostaje nieco ponad tydzień pracy po godzinach, czyli realnie 12-16 godzin, na 17 wymagań must-have wraz z testami i uruchomieniem. Kontrola jakości w `/10x-shape` zakończyła się statusem `warned` właśnie z tego powodu. Wszystkie wymagania oznaczone `nice-to-have` należy przy tym budżecie traktować jako mało prawdopodobne. — Właściciel: autor. Termin: 2026-09-14. Blokujące: nie.
2. **Jak rozmieścić trzy opcje odpowiedzi w siatce do 30 terminów przy szerokości 360 px?** Etykiety „Będę", „Może" i „Nie dam rady" różnią się długością, a formularz uczestnika jest najbogatszym w interakcje ekranem produktu i jednocześnie tym, na którym stoi guardrail czterech interakcji. Gdyby budżet okazał się za ciasny, upraszczać należy ten ekran, a nie skracać ścieżkę. — Właściciel: autor. Termin: przy projektowaniu formularza. Blokujące: nie.
Rozstrzygnięte po wygenerowaniu PRD: notatka tekstowa zastępuje wymiar godzinowy — decyzja przyjęta, godziny są w `## Non-Goals`, a strukturalne „spóźnię się" jako rozstrzygacz remisów czeka w dalszych fazach.
