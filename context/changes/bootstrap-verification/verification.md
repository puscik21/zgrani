---
bootstrapped_at: 2026-09-06T18:09:12Z
starter_id: spring
starter_name: Spring Boot
project_name: zgrani
language_family: java
package_manager: maven
cwd_strategy: subdir-then-move
bootstrapper_confidence: verified
phase_3_status: ok
audit_command: "null"
---

# Bootstrap verification — zgrani

## Hand-off

```yaml
starter_id: spring
package_manager: maven
project_name: zgrani
hints:
  language_family: java
  team_size: solo
  deployment_target: render
  ci_provider: github-actions
  ci_default_flow: auto-deploy-on-merge
  bootstrapper_confidence: verified
  path_taken: standard
  quality_override: false
  self_check_answers: null
  has_auth: false
  has_payments: false
  has_realtime: false
  has_ai: false
  has_background_jobs: false
```

**Why this stack** (verbatim z hand-offu): Spring Boot jest rekomendacją domyślną dla produktu typu web-app w rodzinie Java i przechodzi wszystkie cztery bramki jakości: jest typowany, konwencyjny, obecny w danych treningowych i udokumentowany. W rejestrze niesie najwyższy poziom pewności scaffoldingu, co przy jednym tygodniu pracy po godzinach waży więcej niż jakakolwiek przewaga architektoniczna alternatyw. Decydujące było jednak co innego: autor pracuje w tym stacku zawodowo, a całe ryzyko projektu leży w regule rankingu i w celowanej prośbie o potwierdzenie, nie w doborze frameworka — stack, którego trzeba się uczyć, przeniósłby to ryzyko na każdą godzinę realizacji. Front powstaje w React i TypeScript poza tym kontraktem, ponieważ hand-off nie ma pola na warstwę kliencką; przyjęta decyzja to jeden artefakt wdrożeniowy, w którym Spring Boot serwuje zbudowany bundel jako zasoby statyczne, a dev-serwer Vite dostaje proxy na backend. Uwierzytelnienia nie ma świadomie — produkt stoi na jednym nieodgadywalnym linku. Flaga zadań w tle pozostaje wyłączona: jedynym kandydatem jest przypomnienie na dzień przed terminem, oznaczone jako nice-to-have i ostatnie w kolejności realizacji, a Spring pokrywa je wbudowanym harmonogramem bez dodatkowej infrastruktury.

## Pre-scaffold verification

| Sygnał         | Wartość  | Waga | Uwagi                                                              |
| -------------- | -------- | ---- | ------------------------------------------------------------------ |
| pakiet npm     | nie uruchomiono | —    | starter spoza rodziny JS; `cmd_template` nie wywołuje CLI z npm     |
| repozytorium GitHub | nie uruchomiono | —    | `docs_url` karty wskazuje na docs.spring.io, nie na github.com      |

Brak sygnału o świeżości. Zgodnie z regułą bramki: odnotowane, bez ostrzeżenia, bez zatrzymania.

## Scaffold log

**Rozwinięte wywołanie**: `curl -s https://start.spring.io/starter.tgz -d dependencies=web,devtools -d type=maven-project -d javaVersion=21 -d groupId=com.example -d artifactId=zgrani | tar -xzf -`
**Strategia**: subdir-then-move
**Kod wyjścia**: 0
**Przeniesionych pozycji**: 7 (`.gitattributes`, `.mvn`, `HELP.md`, `mvnw`, `mvnw.cmd`, `pom.xml`, `src`)
**Kolizje (rodzeństwo .scaffold)**: brak
**Obsługa .gitignore**: scalony przez dopisanie (59 linii po scaleniu)
**Sprzątanie .bootstrap-scaffold**: usunięty

**Odstępstwo od reguły podstawiania — świadome.** Reguła każe podstawić `{name}` → `.bootstrap-scaffold`. Karta `spring` używa jednak `{name}` w slocie `artifactId`, a nie jako nazwy katalogu, więc dosłowne podstawienie dałoby projekt Mavena o artefakcie nazwanym `.bootstrap-scaffold`. Dodatkowo `curl | tar` rozpakowuje płasko do katalogu roboczego i sam nie tworzy podkatalogu. Zastosowano: katalog tymczasowy utworzony ręcznie, `artifactId` ustawiony na `project_name` z hand-offu. Intencja strategii — izolacja, polityka kolizji, nienaruszalność `context/` — zachowana w całości. Kształt tarballa potwierdzony wcześniej w piaskownicy poza repozytorium.

## Post-scaffold audit

**Narzędzie**: pominięte — brak wbudowanego narzędzia audytu dla rodziny `java` (`audit_commands.java: null`)
**Rekomendowane narzędzie zewnętrzne**: OWASP Dependency-Check albo Snyk

Zamiast audytu wykonano weryfikację budowania, o którą prosi zadanie praktyczne lekcji: `./mvnw -B test` → **BUILD SUCCESS**, `Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`.

**Naprawa na poziomie systemu, wykonana między próbami.** Pierwsze uruchomienie padło na `Non-resolvable parent POM … spring-boot-starter-parent:4.1.1` — globalny `~/.m2/settings.xml` kieruje `central` na wewnętrznego Nexusa firmowego, nieosiągalnego spoza sieci firmowej, a więc również z GitHub Actions i z Rendera. Dodano `.mvn/settings.xml` zawężający zależności do publicznego Maven Central oraz `.mvn/maven.config` wpinający go automatycznie, dzięki czemu `./mvnw` działa bez dodatkowych flag lokalnie, w CI i przy wdrożeniu. Ubocznie: ruch prywatnego projektu przestaje iść przez infrastrukturę pracodawcy.

## Hints recorded but not acted on

- `deployment_target: render` — bootstrapper niczego nie wdraża; wdrożenie należy do M1L5.
- `ci_provider: github-actions`, `ci_default_flow: auto-deploy-on-merge` — v1 nie generuje plików pipeline'u.
- `team_size: solo`, `path_taken: standard`, `quality_override: false` — odnotowane, bez działania.
- Wszystkie pięć flag funkcji na `false` — nic do doinstalowania.

## Next steps

1. **Front w React + TypeScript** — poza kontraktem hand-offu, do dołożenia ręcznie: bundel serwowany przez Spring Boot ze statyków, proxy w dev-serwerze Vite na backend.
2. **`CLAUDE.md` i reguły agenta** — v1 bootstrappera ich nie generuje; to temat M1L4.
3. **Pipeline CI** — nie powstał; punkt 7 ścieżki rdzeniowej, temat M1L5.
4. **`groupId: com.example`** — wartość domyślna z szablonu, do zmiany, jeśli ma znaczenie.
