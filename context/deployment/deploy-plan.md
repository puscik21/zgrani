# Deploy Plan: pierwsze wdrożenie na Render

Zatwierdzone przez Plan Mode, 2026-09-12. Wsad: `@context/foundation/infrastructure.md` + `@context/foundation/tech-stack.md`. Cel: udowodnić pipeline kod → Docker → Render → publiczny URL, zamykając Moduł 1 end-to-end — nie wdrożenie MVP-a (frontend jeszcze nie istnieje w repo).

## Kroki automatyczne (zrobione przez agenta)

- [x] `pom.xml`: `java.version` 21 → 25, dodano `spring-boot-starter-actuator` (`cfacf44`)
- [x] `AGENTS.md`, `context/foundation/infrastructure.md`: wzmianki wersji Javy zaktualizowane na 25 (`8d4a338`)
- [x] `src/main/resources/application.yml`: `server.port: ${PORT:8080}`, `management.endpoints.web.exposure.include: health`, `show-details: never` (`997090e`)
- [x] Weryfikacja lokalna (bez Dockera): `./mvnw clean package` — sukces na Javie 25.0.3; `/actuator/health` → `200 {"status":"UP"}`; nieistniejąca ścieżka → `application/problem+json` (RFC 9457), zgodnie z regułą w `context/foundation/lessons.md`
- [x] `Dockerfile` (multi-stage: `eclipse-temurin:25-jdk-alpine` build → `eclipse-temurin:25-jre-alpine` runtime, `-XX:MaxRAMPercentage=75.0`) i `.dockerignore` (`ddd5468`)
- [x] Weryfikacja w kontenerze: zainstalowano `colima` + `docker` CLI (open source, bez Docker Desktop — laptop firmowy) przez Homebrew; `docker build` → `BUILD SUCCESS` (mirror `.mvn/settings.xml` poprawnie rozwiązany wewnątrz kontenera); `docker run` → start w 1.1s, `/actuator/health` → `200 UP`, czyste logi

## Kroki manualne (Ty, w tej dokładnej kolejności)

1. Załóż/potwierdź konto Render (logowanie przez GitHub upraszcza krok 2).
2. Render Dashboard → **New +** → **Web Service** → połącz GitHub, nadaj dostęp tylko do repo `zgrani` (nie do wszystkich repozytoriów).
3. Formularz tworzenia serwisu:
   - Runtime: **Docker**
   - Region: jawnie **EU** (np. Frankfurt) — nie da się zmienić później bez odtworzenia serwisu
   - Branch: `main`
   - Dockerfile path: `./Dockerfile`
   - Plan: **Free** na start
   - Auto-Deploy: **On**
4. Po pierwszym udanym deployu: Settings → Health Checks → ścieżka `/actuator/health`.
5. **Nadal na Free, bez płacenia:** zmierz i zapisz realny cold-start (`curl` na `/actuator/health` po >15 min ciszy; oczekiwane ~60-90s) — potwierdzenie liczby z researchu faktycznym pomiarem.
6. **Przed wysłaniem linku do prawdziwych uczestników** (nie wcześniej, nie "jak MVP będzie gotowe"): Settings → Plan → przełącz Free → Starter ($7/mo).
7. Opcjonalnie później: podłącz Render MCP server (`render-oss/render-mcp-server`) do agentowej diagnostyki logów/statusu.

## Sekrety

Brak wymaganych na tym etapie (brak bazy, auth, zewnętrznych API).

| Sekret | Gdzie | Kiedy potrzebny |
|---|---|---|
| — | Render env vars (Dashboard → Environment) | Dodać przy pierwszej integracji wymagającej klucza (np. wysyłka e-maili — FR-003/017/023) |

## Checklist weryfikacji po realnym deployu na Render

- [ ] `curl -i https://<serwis>.onrender.com/actuator/health` → `200 {"status":"UP"}`
- [ ] Dashboard pokazuje "Live" z przechodzącym health checkiem, nie tylko "Deployed"
- [ ] Nieistniejąca ścieżka zwraca `application/problem+json` (RFC 9457), nie Whitelabel error page
- [ ] Serwis związany z injectowanym `$PORT` (health check przechodzi = dobry znak)
- [ ] Zmierzony realny cold-start na Free — zapisany tutaj po pomiarze: `___`

## Odłożone (świadomie, nie po cichu pominięte)

- **GitHub Actions workflow** — `tech-stack.md` deklaruje `ci_provider: github-actions`, ale na ten pierwszy deploy bramką jest sam Docker build (`mvn clean package` bez `-DskipTests` w build-stage — failing test = failing image = brak deployu). Osobny workflow na szybszy feedback z PR-a to następny, odłożony krok.
- **Render MCP server** — podłączenie po pierwszym udanym deployu, nie teraz.
- **Preview environments per PR** — nieskonfigurowane, do rozważenia gdy pojawi się pierwszy realny branch/PR workflow.
