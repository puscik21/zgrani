# Repository Guidelines

Zgrani is a Spring Boot 4.1 / Java 21 backend, built with the Maven wrapper. It will eventually serve a React + TypeScript frontend as its own static resources — see `@context/foundation/tech-stack.md` for the full stack contract.

## Hard rules

- Run all Maven commands with `./mvnw` from the repo root, never from a subdirectory (`cd src && ../mvnw` breaks). `.mvn/settings.xml` is wired in via `.mvn/maven.config` with a project-scoped `-s` flag resolved relative to the current working directory, not the project root.
- The Maven mirror in `.mvn/settings.xml` is local to this repo. Do not touch `~/.m2/settings.xml` to fix a Nexus/mirror issue here — it is unrelated and out of scope.
- There is no separate frontend deploy target. Per `@context/foundation/tech-stack.md`, the frontend builds into a bundle that Spring Boot serves as static resources; the dev Vite server only proxies to the backend.
- API error responses must be RFC 9457 `ProblemDetail` (`application/problem+json`), enabled via `spring.mvc.problemdetails.enabled: true` in `application.yml`. Throw `ResponseStatusException` with a `detail` message; do not hand-roll a custom `@RestControllerAdvice` or the classic `{timestamp, status, error, path}` shape.

## Project Structure & Module Organization

- `src/main/java/com/example/zgrani/` — application code, currently just `ZgraniApplication.java`.
- `src/main/resources/application.yml` — Spring config (YAML, not `.properties`).
- `src/test/java/com/example/zgrani/` — mirrors the main package.
- `context/foundation/` — product docs (`prd.md`, `tech-stack.md`, `shape-notes.md`); `context/changes/` and `context/archive/` track in-flight and locked work.

## Build, Test, and Development Commands

- `./mvnw spring-boot:run` — start the app locally.
- `./mvnw test` — run the test suite.
- `./mvnw clean package` — build the deployable jar.

## Testing Guidelines

Tests use `spring-boot-starter-webmvc-test` and live under `src/test/java`. Run a single test with `./mvnw test -Dtest=ClassName`.

## Commit & Pull Request Guidelines

Commit history so far is short, imperative, single-line messages with no body (e.g. `Add project bootstrap`, `Change application to .yml`). No CI workflow exists yet; `@context/foundation/tech-stack.md` specifies GitHub Actions with auto-deploy-on-merge as the eventual target.
