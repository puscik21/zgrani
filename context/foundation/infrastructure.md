---
project: "Zgrani"
researched_at: 2026-09-12
recommended_platform: Render
runner_up: Fly.io
context_type: mvp
tech_stack:
  language: Java 25
  framework: Spring Boot 4.1
  runtime: executable JAR (Maven), Docker deploy (no native Java runtime on Render)
---

## Recommendation

**Deploy on Render, Starter plan ($7/mo, 0.5 vCPU / 512MB, always-on).**

Render ties or wins on 4 of 5 agent-friendly criteria against Fly.io and Google Cloud Run, has the most mature MCP integration of any candidate (official, GA since August 2025, 20+ tools, plus a dedicated docs-search MCP), and is the cheapest always-on option that removes cold start — which turned out to be a must-have concern, not a nice-to-have one: a ~60-90s cold boot (Render's own ~1 minute spin-up plus JVM/Spring startup) directly breaks the product's core guardrail (US-01: at most 4 interactions, zero friction) for any participant opening the invite link more than 15 minutes after the last visit — which is the expected, normal usage pattern for this product, not an edge case.

## Platform Comparison

| Platform | CLI-first | Managed/serverless | Agent-readable docs | Stable deploy API | MCP / Integration | Verdict |
|---|---|---|---|---|---|---|
| Cloudflare Workers/Pages | Partial | Partial (Containers only) | Pass | Pass | Partial | **Hard-filtered — no JVM runtime.** Workers is a V8-isolate model (JS/TS/Python/Rust/Wasm only). Cloudflare Containers (separate product, GA Apr 2026) could run a JAR via Docker, but scale-to-zero by default and no free tier ($5/mo Workers Paid minimum). |
| Netlify | — | Fail | Pass | Pass | Pass | **Hard-filtered — no JVM runtime.** Static hosting + short-lived JS/Go functions only (10-26s timeout). No path to a persistent Java process. |
| Vercel | Pass | Partial | Fail (docs not on GitHub/markdown) | Pass | Partial (beta) | Excluded — technically possible since mid-2026 via `Dockerfile.vercel`, but the feature is ~2 months old, no persistent disk, Hobby plan forbids commercial use in ToS, docs not agent-readable. Too immature for this decision. |
| Google Cloud Run | Pass | Pass | Fail (no llms.txt found) | Pass | Partial (community npm package, not GA) | Researched head-to-head against Render (see below) — loses on cost in EU regions and complexity. |
| **Fly.io** | Pass | Pass | Pass (docs source on GitHub) | Pass | Partial (community `flymcp`) | **Runner-up.** Wins CLI-first over Render (dedicated rollback pattern via image hash vs Render's dashboard-only rollback) and ties on docs. Loses on cost (no free tier since Oct 2024) and requires manual anti-sleep + memory-sizing configuration that's easy to get wrong. |
| Railway | Pass | Pass | Pass (docs on GitHub) | Pass | Partial (community) | **Rejected after cross-check.** Best native Java/Maven auto-detection and no forced sleep by default, but: JDK version auto-detection is flaky (needs manual pinning), free tier (0.5 vCPU/0.5GB) too small for real always-on JVM, per-second metered billing makes costs unpredictable during a high-iteration dev sprint, and the platform has changed its free-tier policy multiple times in two years — a pricing-stability signal the user weighed as disqualifying. |
| **Render** | Partial (rollback is Dashboard/API only, no dedicated CLI verb — full CLI exists otherwise: `deploys create/list`, `logs`, `services`, `ssh`, `psql`, `blueprints validate`) | Pass | Pass (own `llms.txt` + `llms-full.txt` + `.md`-suffix pages + `Accept: text/markdown`, plus a second experimental docs-search MCP server) | Pass | **Pass** (official `render-oss/render-mcp-server`, GA since 2025-08-21, 20+ tools: deploy trigger, logs, metrics, datastores, env vars; cannot delete services/databases by design) | **Recommended.** |

### Shortlisted Platforms

#### 1. Render (Recommended)

Wins on cost for guaranteed always-on ($7/mo Starter vs Fly.io's ~$3.32-5.92/mo *plus* manual anti-sleep/memory configuration risk, vs Cloud Run's ~$10-15/mo in an EU region with no free-tier offset), has the strongest MCP integration of any researched platform, and its documentation is fully agent-readable (`llms.txt`/`llms-full.txt`) — a fact missed in the first research pass and confirmed only after the user pushed back on it.

#### 2. Fly.io

Objectively closest competitor — ties Render on 3 of 5 criteria and wins CLI-first (deterministic rollback via `fly releases --image` + `fly deploy -i`, vs Render's dashboard-only rollback). Loses on cost (no free tier since October 2024) and requires the user to remember two manual configuration steps that Render's Starter plan gives by default: disabling scale-to-zero (`min_machines_running=1`) and sizing the VM correctly (≥512MB to avoid JVM OOM — the 256MB default kills a Spring Boot app).

#### 3. Google Cloud Run (ad-hoc comparison, not part of the original candidate pool)

Added to this research after the user asked why hyperscaler-managed options weren't considered. Container-native and GA, with genuinely fast optimized JVM cold starts (2-5s with Startup CPU Boost, not the 60-90s originally feared) — but Cloud Run's free tier explicitly excludes EU regions, so `min-instances=1` in an EU region is billed at ~$10-15/month, more than Render, with materially higher configuration complexity (concurrency tuning, CPU Boost, region-based free-tier gotcha) and a less mature MCP integration (community npm package, not GA).

## Anti-Bias Cross-Check

Three candidates were cross-checked in sequence as the leading pick changed during research (Railway → Fly.io → Render). All three results are preserved below since each swap was driven by evidence, not preference, and the reasoning stays relevant to the risk register.

### Railway (rejected)

**Devil's advocate:**
1. Nixpacks/Railpack JDK version auto-detection is unreliable — a wrong JDK can surface only at build time, not locally.
2. The "free" plan (0.5 vCPU/0.5GB) is too small for a real always-on JVM — Hobby ($5/mo + usage) is required from day one regardless of stated cost preference.
3. Railway has changed its free-tier policy multiple times in the past two years — a pricing-stability signal, not just history.
4. Rollback is "redeploy a prior deployment," not a dedicated command.
5. MCP is community/early, not official GA — weaker than Render if agent-driven production diagnostics are ever needed.

**Pre-mortem:** Assumed "Hobby $5/mo + usage" was a flat, predictable cost — but per-second metering means the bill fluctuates with how many times the JAR was rebuilt (builds consume credits). A month with heavy deploy iteration (typical for a learning/hobby sprint) costs more than expected. In parallel, a Railpack update stopped detecting Java 21 correctly, discovered only via a failed deploy right before a meeting the deployment was supposed to support.

**Unknown unknowns:** No formal SLA on Hobby tier; volumes unsupported in "Metal" regions (irrelevant now, blocks a future local-storage pivot without a region migration); no kill-switch against unexpected metered costs from a forgotten zombie service; easy Docker exit path, but credits/subscription don't transfer.

**Decision**: rejected by the user after reviewing these risks — moved comparison to Fly.io vs Render.

### Fly.io (runner-up)

**Devil's advocate:**
1. No free tier since October 2024 — pay from day one, no trial-without-commitment.
2. Must manually disable scale-to-zero (`min_machines_running=1`, `auto_stop_machines=off`) — if forgotten, the app silently reverts to cold starts.
3. Default machine size (256MB) reliably OOMs a JVM + Spring Boot app — must be known upfront, not discovered after a crash.
4. Rollback requires pulling an image hash from `fly releases --image` — functional but less discoverable than a single named command.
5. MCP (`flymcp`) is community/early — weaker agent-integration story than Render if ever needed.

**Pre-mortem:** Three months later: the developer forgot to set `min_machines_running=1` (Fly's default is scale-to-zero after ~5 min idle) — the event reminder feature aside, this broke the core RSVP flow itself, since a friend group opening the link hours after it was shared hit a cold instance every time. Separately, the initial deploy shipped on the default 256MB image and the first real event (30 terms × 20 participants) OOM-killed the process under the full ranking computation.

**Unknown unknowns:** Fly deprecated its own managed Postgres for new deployments (irrelevant here, external DB used); no formal SLA on pay-as-you-go single-instance compute; pricing has already shifted once (free tier removed 2024), signaling the same kind of pricing-model instability flagged against Railway.

**Decision**: stayed as runner-up — objectively close to Render (ties on 3/5 criteria, wins CLI-first), but loses on cost and requires the user to get two manual configuration steps right that Render's Starter plan removes by default.

### Render (recommended)

**Devil's advocate:**
1. 512MB is still tight for JVM + Spring Boot 4.1 + an embedded React bundle — same class of OOM risk flagged for Fly.io's smaller tier; the next plan up (Standard, 1 CPU/2GB) is a large price jump, not a linear one.
2. No native Java runtime — the user must maintain their own Dockerfile; a mistake in the multi-stage build (wrong JDK/JRE layering, poor cache use) slows builds and burns build-minute quota faster than a native buildpack would.
3. Rollback is not a CLI verb — under time pressure, the recovery path is "find the Dashboard button," not a memorized command.
4. Free and Starter are separate plans the user must remember to switch between deliberately — testing on Free and forgetting to switch before real usage reintroduces the cold-start problem this whole research was meant to solve.
5. The official MCP server explicitly cannot delete services or databases — a sound safety default, but it means genuine destructive cleanup still requires a manual Dashboard action even with the agent connected.

**Pre-mortem:** Three months later: launched on Starter, confident about always-on. Early testing happened on Free before switching, and JVM memory behavior under the 512MB Starter limit was never verified under realistic load. On the one night the whole friend group rushes to submit availability before a deadline, a burst of concurrent ranking recalculations OOMs the process — on the single day the product mattered most, discovered only via a friend reporting "the site died," not through any monitoring the developer had set up.

**Unknown unknowns:** Starter's exact bandwidth allowance isn't clearly published (traffic at this project's scale makes this practically irrelevant, but worth confirming before committing budget); region is chosen at service creation and cannot be changed without recreating the service — not picking an EU region up front means a future migration is a new deploy, not a config flip; Docker build-minute quota on lower tiers could be consumed faster than expected during a high-iteration dev sprint; no formal SLA at the Starter tier (expected and acceptable for a personal project).

**Decision**: recommended, with the risks above carried into the register below.

## Operational Story

- **Preview deploys**: Render creates a preview environment per PR when Preview Environments are enabled for the service (uses a temporary URL). Not yet configured for this project — set up when the first feature branch/PR workflow is in place.
- **Secrets**: environment variables live in Render's own dashboard/env-var store per service (or via `render.yaml` for blueprint-based config, referencing secret values rather than inlining them). Not GitHub Secrets — those stay relevant only for the GitHub Actions build/test step, not for runtime secrets on Render itself.
- **Rollback**: via Render Dashboard's "Rollback" button on the Deploys page, or the `Roll back deploy` API endpoint (scriptable, just not a dedicated CLI verb). Free tier only supports rolling back to the two most recent prior deploys — enough for a solo project's iteration pace.
- **Approval**: publishing to production (i.e., the only deploy target this project has), rotating the plan from Free to Starter, and any destructive action (deleting the service/database) are done by the human via Dashboard. The official Render MCP server is deliberately unable to delete services or databases, so agent-driven cleanup always requires a manual step regardless.
- **Logs**: `render logs` (real-time tailing via CLI) or the Render MCP server's log-fetching tool (structured, filterable) once the MCP server is connected to this project.

## Risk Register

| Risk | Source | Likelihood | Impact | Mitigation |
|---|---|---|---|---|
| 512MB OOM under JVM + Spring Boot + concurrent ranking recalculation | Devil's advocate (Render) / Pre-mortem (Render) | M | H | Tune `-XX:MaxRAMPercentage`; load-test the ranking recalculation locally with an equivalent heap cap before the first real event; upgrade to Standard if it recurs |
| Forgetting to switch Free → Starter before real usage, reintroducing the 60-90s cold start on the core RSVP flow (US-01) | Pre-mortem (Render) / this project's own guardrail | M | H | Hard trigger recorded below in "Signal to revisit" — switch before the invite link is sent to real participants, not at a vague "feels ready" point |
| Rollback has no dedicated CLI verb; recovery under time pressure means finding the Dashboard | Devil's advocate (Render) | L | L | Acceptable for a solo project with infrequent deploys; know the Dashboard path before you need it, not during an incident |
| Docker multi-stage build misconfiguration slows builds / burns build-minute quota | Devil's advocate (Render) | L | M | Use a standard Maven-build-stage + slim JRE-runtime-stage Dockerfile pattern; verify build time is reasonable on the first deploy |
| Render's exact Starter bandwidth allowance isn't clearly published | Unknown unknowns (Render) | L | L | Practically irrelevant at this project's traffic (qps < 1, small JSON payloads); confirm with Render support only if actual usage nears any published cap |
| Region locked in at service creation; changing later requires recreating the service | Unknown unknowns (Render) | L | M | Choose an EU region explicitly at first deploy — do not accept a default region without checking |
| Fly.io was close on objective scoring (ties 3/5, wins CLI-first) | Research finding | — | — | Recorded as runner-up; revisit if Render's pricing or MCP maturity regresses |
| Google Cloud Run's EU free-tier gap and higher always-on cost (~$10-15/mo) | Research finding | — | — | Not pursued further; revisit only if GCP ecosystem lock-in becomes a project requirement |

## Getting Started

1. Write a multi-stage `Dockerfile`: build stage on a Maven+JDK 25 image (`mvn clean package -DskipTests` or with tests), runtime stage on a slim JRE 25 base (e.g. `eclipse-temurin:25-jre-alpine`) copying only the built JAR — this keeps the image small and build time reasonable on Render's Docker build path.
2. Create the Render service with the **Docker** runtime (not a native-language runtime — Render has none for Java) and an explicit **EU region** — this cannot be changed later without recreating the service.
3. Start on the **Free** plan for solo development/testing. Set a hard, calendar-independent trigger to switch to **Starter ($7/mo)**: before the event link is ever sent to a real participant outside the developer — not "when the app feels done."
4. Configure `server.port=${PORT:8080}` (or Render's equivalent env var) so the container binds correctly — verify against Render's actual port-injection convention before first deploy, since conventions vary by platform.
5. Connect the official Render MCP server (`render-oss/render-mcp-server`) for agent-driven log/status checks once the first deploy is live.

## Out of Scope

The following were not evaluated in this research:
- Docker image configuration (Dockerfile content itself — see Getting Started step 1 for the pattern, not the file)
- CI/CD pipeline setup (GitHub Actions wiring is a separate, later step)
- Production-scale architecture (multi-region, HA, DR) — explicitly out of scope for an MVP serving qps < 1
