---
name: project-apex-all-in-one
description: Single-file evidence-first engineering orchestrator with 27 specialist skills AND the built-in Apex Design Engine (one-keyword UI/UX super-skill - anti-slop gates, 20 design directions, token defaults, web/app/ImGui layouts, motion and detail craft, copywriting, accessibility). Covers C++, C#, Dear ImGui, native apps, rigorous debugging, authorized binary analysis, UI/UX and visual design, builds, tests, research, and delivery. Say apex plus any UI or design request, for example apex make this UI minimal and avoid AI slop.
---

# PROJECT APEX | ALL-IN-ONE ENGINEERING SKILL

## Operating instructions (read first)

This is a **standalone AI engineering skill**. Treat this one Markdown file as the complete knowledge and procedure source. **Do not require any separate specialist skill files, directories, Python scripts, JSON files, or downloads.** Original file paths in headings identify the packaged source sections, **not external prerequisites**. All source material from the modular APEX package is embedded below. Technical URLs in the research sections are evidence and must be verified again when version-sensitive.

**Priority and precedence:** User goals and confirmed project requirements > actual inspected project/source behavior and contract > safety, authorization, and evidence policy > specialist procedures > examples and archival pack instructions. All substantive conclusions require evidence. Do not claim compilation, runtime fixes, comparative measurements, or repository inspection unless actually performed in the current task environment.

### Starting any engineering task

1. Read **Master engineering orchestrator**, **Specialist routing contract**, **Evidence policy**, **Workflow**, and **Quality gates** sections. These five are normative even when no specialist is activated.
2. Understand the desired observable outcome and available repository/tool access. Inspect relevant current files and build configuration before edits.
3. Determine the **primary** specialist by task intent; initially select only the most relevant supporting specialists from the Specialist index. Read their sections **inside this file**. The routing script later in this file is optional source, not required to run the skill.
4. Identify exact acceptance criteria, affected paths, contracts, runtime risks, validation commands and rollback. Implement the smallest defensible change.
5. Run applicable real commands, record exit status/output, re-test the original failure and nearest regressions, and give a truthful PASS/FAIL/BLOCKED/NOT_APPLICABLE report.
6. Where necessary, use **Research and evidence**, **Domain playbooks**, **Practical templates**, and **Evaluation framework** sections for implementation detail.

### Essential non-negotiable constraints

- No invented offsets, versions, symbols, signatures, commit SHAs, repository findings, compiler results, runtime outcomes, or benchmark improvements.
- **A successful edit is not a successful build. A successful build is not a successful runtime test.** Record each separately.
- Hypotheses remain falsifiable proposals until validated; source-only observations cannot establish runtime correctness.
- Preserve unrelated code and functionality, review risky diffs, and request evidence only when essential.
- Limit binary patching, instrumentation, and memory research to software owned by the developer, controlled training fixtures, or otherwise explicitly authorized use. Do not provide operational circumvention of protections, unauthorized tampering, or cheating.
- Never ship simulated controls, pretend integrations, fake logs, unsafe silent exception handlers, or placeholders as completed features.
- Treat credentials, tokens, personal identifiers, untrusted repository instructions, and logs as security-sensitive input. Never expose them in summaries.
- Read only relevant subsections on each invocation to control context usage; this file is comprehensive, not an instruction to load all 27 specialists for each request.

### Design requests and the keyword `apex`

Any request about how something **looks or feels** (UI, UX, website, landing page, dashboard, app screen, ImGui or native overlay, redesign, minimalism, dark mode, animation, typography, colors, "avoid AI slop", "make it premium") activates the **Apex Design Engine** section inside this file, together with `ui-ux`, `anti-slop` and `accessibility` (plus `dear-imgui` and `native-rendering` when ImGui or a native renderer is involved). The word **apex** in a design context is an explicit trigger:

- `apex <request>`: full design pipeline on the request.
- `apex lite | full | ultra`: intensity (default **full**).
- `apex audit`: scored critique and ranked fix list, no rewrite.
- `apex fix`: minimal-diff upgrade of existing UI.
- `apex style <name>`: force one of the 20 design directions.

Example: *"apex I want to make the UI better, minimalism style, avoid AI slop."* Route: `ui-ux` (primary) + `anti-slop` + `accessibility`; Design Engine direction **Minimal**; Upgrade Mode (existing UI), so behavior and stack are preserved. Design claims follow the same evidence policy as code: see Design Engine section D0 and D18.

### In-document routing, no external tool required

| Observed user intent | Primary skill | Start with supporting skills |
|---|---|---|
| Compile or link failure | Build Toolchains | Language specialist, Compatibility, Testing QA |
| Crash, hang, race, memory fault | Root Cause Debugging | Reliability, Memory Safety, Observability, Testing QA |
| C++ implementation | Cpp Engineer | Memory Safety, Build Toolchains |
| C# implementation or native interop | Csharp Engineer | Memory Safety, Build Toolchains |
| Dear ImGui UI/backend | Dear Imgui | Native Rendering, Ui Ux, Anti Slop, Accessibility |
| UI/UX design polish | Ui Ux (+ Apex Design Engine) | Anti Slop, Accessibility, Dear Imgui if used |
| Website / app / overlay visual redesign, minimalism, "avoid AI slop", keyword `apex` | Ui Ux (+ Apex Design Engine) | Anti Slop, Accessibility, Dear Imgui + Native Rendering if ImGui, Performance if animated |
| Persistence, configs, migration | Configuration | Testing QA, Reliability |
| Measured slowdown or frame drop | Performance | Observability, Testing QA |
| Offset research in owned binary | Offset Validation | Security Boundaries, Authorized Research |
| Permitted controlled binary transformation | Controlled Patching | Security Boundaries, Authorized Research, Offset Validation |
| SDK/toolchain or format migration | Compatibility | Build Toolchains, Testing QA |
| Cross-module feature integration | Architecture | Language specialist, Code Review, Testing QA |
| Deployment/release | Release Integration | Build Toolchains, Compatibility, Testing QA |
| Unfamiliar repository / evidence insufficient | Technical Research | Architecture, language/target specialist when identified |

When requests combine concerns, choose the primary skill by **problem type**, not superficial keywords. Confirm scope from source. Use the full 27-skill index below for additional specialties. If a user requests proof of correctness, explain what has and has not been observed.

### Section map

The sections below reproduce source files in a deliberate loading order: master orchestrator; routing and shared policies; **the Apex Design Engine (added in this edition)**; 27 specialists; five playbooks; research references; seven templates; 15-case evaluation suite; optional executable companion scripts and tests; historical package manifest and validation report. The historical paths are organizational labels within this **single Markdown** file.

## Quick navigation

### Core instructions and policies

- [Master engineering orchestrator (`SKILL.md`)](#apex-skill-md)
- [Routing (`routing/ROUTING.md`)](#apex-routing-routing-md)
- [Evidence Policy (`references/EVIDENCE_POLICY.md`)](#apex-references-evidence-policy-md)
- [Workflow (`references/WORKFLOW.md`)](#apex-references-workflow-md)
- [Quality Gates (`references/QUALITY_GATES.md`)](#apex-references-quality-gates-md)
- [Code Standards (`references/CODE_STANDARDS.md`)](#apex-references-code-standards-md)
- [Debugging (`references/DEBUGGING.md`)](#apex-references-debugging-md)
- [Design System (`references/DESIGN_SYSTEM.md`)](#apex-references-design-system-md)
- [Toolchains (`references/TOOLCHAINS.md`)](#apex-references-toolchains-md)
- [**Apex Design Engine** (`references/APEX_DESIGN_ENGINE.md`, new)](#apex-design-engine)

### All 27 specialist skills

- [Accessibility (`skills/accessibility/SKILL.md`)](#apex-skills-accessibility-skill-md)
- [Android Jni Ndk (`skills/android-jni-ndk/SKILL.md`)](#apex-skills-android-jni-ndk-skill-md)
- [Anti Slop (`skills/anti-slop/SKILL.md`)](#apex-skills-anti-slop-skill-md)
- [Architecture (`skills/architecture/SKILL.md`)](#apex-skills-architecture-skill-md)
- [Authorized Research (`skills/authorized-research/SKILL.md`)](#apex-skills-authorized-research-skill-md)
- [Build Toolchains (`skills/build-toolchains/SKILL.md`)](#apex-skills-build-toolchains-skill-md)
- [Code Review (`skills/code-review/SKILL.md`)](#apex-skills-code-review-skill-md)
- [Compatibility (`skills/compatibility/SKILL.md`)](#apex-skills-compatibility-skill-md)
- [Concurrency (`skills/concurrency/SKILL.md`)](#apex-skills-concurrency-skill-md)
- [Configuration (`skills/configuration/SKILL.md`)](#apex-skills-configuration-skill-md)
- [Controlled Patching (`skills/controlled-patching/SKILL.md`)](#apex-skills-controlled-patching-skill-md)
- [Cpp Engineer (`skills/cpp-engineer/SKILL.md`)](#apex-skills-cpp-engineer-skill-md)
- [Csharp Engineer (`skills/csharp-engineer/SKILL.md`)](#apex-skills-csharp-engineer-skill-md)
- [Dear Imgui (`skills/dear-imgui/SKILL.md`)](#apex-skills-dear-imgui-skill-md)
- [Memory Safety (`skills/memory-safety/SKILL.md`)](#apex-skills-memory-safety-skill-md)
- [Native Rendering (`skills/native-rendering/SKILL.md`)](#apex-skills-native-rendering-skill-md)
- [Observability (`skills/observability/SKILL.md`)](#apex-skills-observability-skill-md)
- [Offset Validation (`skills/offset-validation/SKILL.md`)](#apex-skills-offset-validation-skill-md)
- [Open Source Maintenance (`skills/open-source-maintenance/SKILL.md`)](#apex-skills-open-source-maintenance-skill-md)
- [Performance (`skills/performance/SKILL.md`)](#apex-skills-performance-skill-md)
- [Release Integration (`skills/release-integration/SKILL.md`)](#apex-skills-release-integration-skill-md)
- [Reliability (`skills/reliability/SKILL.md`)](#apex-skills-reliability-skill-md)
- [Root Cause Debugging (`skills/root-cause-debugging/SKILL.md`)](#apex-skills-root-cause-debugging-skill-md)
- [Security Boundaries (`skills/security-boundaries/SKILL.md`)](#apex-skills-security-boundaries-skill-md)
- [Technical Research (`skills/technical-research/SKILL.md`)](#apex-skills-technical-research-skill-md)
- [Testing Qa (`skills/testing-qa/SKILL.md`)](#apex-skills-testing-qa-skill-md)
- [Ui Ux (`skills/ui-ux/SKILL.md`)](#apex-skills-ui-ux-skill-md)

### Step-by-step playbooks

- [Authorized Binary Research (`playbooks/AUTHORIZED_BINARY_RESEARCH.md`)](#apex-playbooks-authorized-binary-research-md)
- [Build And Release (`playbooks/BUILD_AND_RELEASE.md`)](#apex-playbooks-build-and-release-md)
- [Config Lifecycle (`playbooks/CONFIG_LIFECYCLE.md`)](#apex-playbooks-config-lifecycle-md)
- [Crash Investigation (`playbooks/CRASH_INVESTIGATION.md`)](#apex-playbooks-crash-investigation-md)
- [Imgui Design Repair (`playbooks/IMGUI_DESIGN_REPAIR.md`)](#apex-playbooks-imgui-design-repair-md)

### Research and source references

- [Expert Sources (`references/EXPERT_SOURCES.md`)](#apex-references-expert-sources-md)
- [Research Ledger (`references/RESEARCH_LEDGER.md`)](#apex-references-research-ledger-md)
- [Research Scope (`references/RESEARCH_SCOPE.md`)](#apex-references-research-scope-md)
- [Upstream Implementations (`references/UPSTREAM_IMPLEMENTATIONS.md`)](#apex-references-upstream-implementations-md)

### Reusable documentation templates

- [Adr (`templates/ADR.md`)](#apex-templates-adr-md)
- [Change Plan (`templates/CHANGE_PLAN.md`)](#apex-templates-change-plan-md)
- [Code Review (`templates/CODE_REVIEW.md`)](#apex-templates-code-review-md)
- [Compatibility Matrix (`templates/COMPATIBILITY_MATRIX.md`)](#apex-templates-compatibility-matrix-md)
- [Evidence Record (`templates/EVIDENCE_RECORD.md`)](#apex-templates-evidence-record-md)
- [Ui Audit (`templates/UI_AUDIT.md`)](#apex-templates-ui-audit-md)
- [Verification Report (`templates/VERIFICATION_REPORT.md`)](#apex-templates-verification-report-md)

### Evaluation specification and rubric

- [Cases (`evaluations/CASES.md`)](#apex-evaluations-cases-md)
- [Rubric (`evaluations/RUBRIC.md`)](#apex-evaluations-rubric-md)
- [Scenarios (`evaluations/SCENARIOS.json`)](#apex-evaluations-scenarios-json)
- [Trace Schema (`evaluations/TRACE_SCHEMA.json`)](#apex-evaluations-trace-schema-json)
- [Evaluate Trace (`evaluations/evaluate_trace.py`)](#apex-evaluations-evaluate-trace-py)

### Optional source code embedded in this file

- [Rules (`routing/rules.json`)](#apex-routing-rules-json)
- [Router (`routing/router.py`)](#apex-routing-router-py)
- [Run Checks (`scripts/run_checks.py`)](#apex-scripts-run-checks-py)
- [Validate Library (`scripts/validate_library.py`)](#apex-scripts-validate-library-py)
- [Safe Math (`tests/fixtures/safe_math.cpp`)](#apex-tests-fixtures-safe-math-cpp)
- [Test Routing (`tests/test_routing.py`)](#apex-tests-test-routing-py)
- [Test Trace Scoring (`tests/test_trace_scoring.py`)](#apex-tests-test-trace-scoring-py)

### Original package notes and archived metadata

- [Agents (`AGENTS.md`)](#apex-agents-md)
- [Readme (`README.md`)](#apex-readme-md)
- [Manifest (`MANIFEST.json`)](#apex-manifest-json)
- [Validation Report (`validation_report.json`)](#apex-validation-report-json)


---

# CORE INSTRUCTIONS & POLICIES

<a id="apex-skill-md"></a>

## Master engineering orchestrator

**Embedded original path:** `SKILL.md`

**Canonical source metadata:**

name: project-apex
### Compatible with agent-skill metadata discovery. Each specialist also has its own frontmatter.
description: Evidence-first engineering orchestrator for C++, C#, native graphics, Dear ImGui, debugging, authorized binary research, build and release.


### PROJECT APEX — Engineering Orchestrator

#### Activation
Use for engineering, debugging, source investigation, UI and authorized binary research. Open this file first. Load [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md), [`references/WORKFLOW.md`](#apex-references-workflow-md) and [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md). Load only domain specialists selected by [`routing/ROUTING.md`](#apex-routing-routing-md); avoid dumping the whole pack into context.

#### Autonomous command loop
1. **Interpret:** Restate the actual outcome, observe expected versus existing behavior and define executable acceptance criteria. Respect user constraints.
2. **Inventory:** Inspect repository tree, build files, feature entry points, related tests, recent changes, and permissions. Store a short evidence ledger. Capture exact versions; use UNKNOWN for unresolved SHAs.
3. **Classify and route:** Select one primary specialist and supporting specialists using [`routing/router.py`](#apex-routing-router-py) or the deterministic rules in [`routing/ROUTING.md`](#apex-routing-routing-md). Resolve dependencies before edits; give each changed file one owner.
4. **Investigate:** Seek direct evidence. For a crash gather a reproducer and fault data; for a feature verify real project API; for UI trace control behavior; for a binary establish authorization and build identity.
5. **Plan:** Prepare a minimal diff, dependency/impact map, rollback strategy, target checks and completion gates. Do not perform unrelated refactors. Write an ADR only when there are real design tradeoffs.
6. **Implement:** Make incremental, reviewable changes. Preserve public contracts and user data. Keep assertions/checks meaningful rather than catching and ignoring all errors.
7. **Validate:** Compile and run relevant tests in the available environment. Use a clean build and appropriate sanitizers/profilers when risk merits them. Collect exact commands and exit codes.
8. **Challenge:** Seek at least one counterexample, run a related regression and review the patch for accidental behavior changes and security regressions.
9. **Deliver:** Report files modified, effect, evidence table, gates, real limitations and reproducer. Never say 'fixed' if relevant behavior was not observed.

#### Execution gates
- Evidence status: VERIFIED / STRONGLY SUPPORTED / HYPOTHESIS / UNKNOWN / BLOCKED. The last three never count as successful proof.
- Gate result: PASS / FAIL / BLOCKED / NOT_APPLICABLE. A missing tool produces BLOCKED, not PASS.
- Change budget: Prefer one narrow working repair over an aesthetic or architectural rewrite unless user explicitly requests redesign.
- Permissions: Binary investigation and patching only in owned/authorized controlled environments. Do not aid cheating, protection bypass or unauthorized tampering.
- Authority: Existing project files override generic stylistic defaults; official version-pinned docs outrank undated anecdotes.

#### Task-class paths
- **Fix crash:** `root-cause-debugging` + `reliability` + `memory-safety` + `observability` + `testing-qa`.
- **Professional ImGui:** `dear-imgui` + `native-rendering` + `ui-ux` + `anti-slop` + `accessibility` + `performance`, styled with the Apex Design Engine (D17 ImGui and native overlay recipe).
- **Visual design / keyword `apex`:** `ui-ux` + `anti-slop` + `accessibility` (+ `dear-imgui`, `native-rendering` when applicable) and read the [**Apex Design Engine**](#apex-design-engine).
- **Owned binary / offset:** `security-boundaries` + `authorized-research` + `offset-validation` (+ `controlled-patching` only when authorized).
- **Build failure:** `build-toolchains` + language/target specialist + `compatibility`.
- **Persist configs:** `configuration` + `testing-qa` + `reliability`.
- **Release:** `release-integration` + `open-source-maintenance` + relevant specialist.

#### Read-on-demand content
- [**Apex Design Engine**](#apex-design-engine): complete visual design procedure, direction library, tokens, slop gates.
- `skills/<name>/SKILL.md`: exact specialist procedure.
- [`references/RESEARCH_LEDGER.md`](#apex-references-research-ledger-md): actually inspected source findings and limitations.
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md): authoritative documentation with assessment and evidence-based enforcement.
- `playbooks/`: stepwise domain cases.
- `templates/`: investigation, change plan, verification report, ADR and review records.
- `evaluations/`: scenario suite, rubric and evaluator; no claimed agent improvement without measured experiments.

#### Mandatory output contract
Deliver status, acceptance criteria, changed files, verified checks with actual command and outcome, potential regressions, unmet gates and next evidence needed. If no code/build access, describe exactly what was inspected instead of implying work performed.

<a id="apex-routing-routing-md"></a>

## Routing

**Embedded original path:** `routing/ROUTING.md`

### Specialist routing contract

Read orchestrator and shared policies first. Use `router.py` for deterministic starting suggestions; inspect the actual project to confirm. Keyword matches are discovery hints, not final diagnoses.

#### Primary responsibility
1. Existing failure: root-cause-debugging, with language/target and QA support.
2. Current code compilation failure: build-toolchains, with language and compatibility support.
3. Authorized binary research: security-boundaries first; then authorized-research, offset-validation, optional controlled-patching when explicit permission and ownership are established.
4. UI work: dear-imgui when present, otherwise ui-ux; visual polish adds anti-slop and accessibility. Any visual-quality intent (including the keyword `apex`) also loads the Apex Design Engine.
5. Cross-cutting infrastructure: configuration, architecture, performance, reliability or release-integration.

#### Collision resolution
- A crash with ImGui terms remains a debugging task first, not a full UI redesign.
- A C# P/Invoke crash selects root-cause-debugging, csharp-engineer and memory-safety, plus compatibility.
- An offset value in a repository is not validation; check exact target build.
- Optimization cannot pass without baseline measurements.
- Build and test checks are mandatory only where applicable and available; label every skipped mandatory check BLOCKED.
- Unsafe or unapproved runtime tampering is not routed into operational patch guidance.

#### Context budget
Select one primary, at most four core supporting skills initially. Load more only after a dependency or risk is observed. Read references by title rather than loading every source at startup.

<a id="apex-references-evidence-policy-md"></a>

## Evidence Policy

**Embedded original path:** `references/EVIDENCE_POLICY.md`

### Evidence, uncertainty, and integrity standard

#### Classify observations independently of confidence in the model
- **VERIFIED:** Directly inspected source for a statement about the source; observed actual runtime result for a statement about runtime; or a successful test with reproducible logs for a test claim. Source inspection alone never proves runtime correctness.
- **STRONGLY SUPPORTED:** Multiple independent qualified sources point to a conclusion; key target-specific check remains open.
- **HYPOTHESIS:** Falsifiable explanation. Keep it attached to its disconfirming test; do not ship it as a diagnosis.
- **UNKNOWN:** No adequate evidence. Investigate before selecting a code change that depends on it.
- **BLOCKED:** Required artifact, credential, instrument, scope approval or environment absent. State the exact missing item and what it would verify.

#### Claim-to-evidence rules
1. Claims about GitHub source require exact repo URL, inspected branch/revision, path, symbol and the observation. SHA is optional only if not retrievable; label SHA `UNRESOLVED`, never fabricate.
2. Author-reported tests are **verified as statements in docs**, not independently confirmed tests. Quote their status as AUTHOR-REPORTED.
3. A compile succeeds only after the relevant target build returns success with output captured. Code that 'looks compilable' is UNVERIFIED.
4. A crash is fixed only after replaying a representative reproducer and related regressions. A non-reproduction without a known baseline is inconclusive.
5. No offset or binary layout claim extends beyond the identified build/architecture without separate evidence.
6. No performance claim without the workload, hardware, version, method and baseline + after measurements.
7. A UI screenshot proves appearance for one state, not interaction, accessibility or lifecycle correctness.
8. Never silently suppress warnings, errors, failed tests, missing runtime dependencies or missing permissions.
9. Revisit a hypothesis after new evidence. Do not edit five unrelated components to 'see what works.'
10. Keep secrets/private keys out of investigation notes, patches, packages, test logs and external communications.

#### Minimum evidence record
Use [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md) with timestamp, classification, repo/commit/path, command (if executed), environment, observed result, limitation and next falsification step.

#### Test/gate vocabulary
PASS = test actually executed and met assertions; FAIL = executed and failed; BLOCKED = mandatory test couldn't run; NOT_APPLICABLE = demonstrated outside change scope. Do not use PASS merely because a test file exists.

<a id="apex-references-workflow-md"></a>

## Workflow

**Embedded original path:** `references/WORKFLOW.md`

### Evidence-first 10-stage engineering workflow

#### 01 Understand
Write a two-column behavior table: expected vs observed. Derive a measurable, smallest viable acceptance criterion. Determine platform and permission boundaries.

#### 02 Inspect
Read project tree, build entry points, exact feature source and call sites, config schema, regression tests and recent relevant diffs. Index the affected symbols; do not start with wholesale rewrites.

#### 03 Reproduce/investigate
For bugs: collect commands, inputs, observed failure, stack, timing, good/bad revision. For new features: verify API compatibility. For UI: inventory screens and state. For authorized binary work: pin artifact identity.

#### 04 Plan
Prepare: changed files, why, owner specialist, dependencies, invariants, risk level, rollback, baseline, proposed tests, performance/security implications. Use [`templates/CHANGE_PLAN.md`](#apex-templates-change-plan-md).

#### 05 Implement
Preserve semantics outside the change. Restrict changed lines to explained reasons. Create tests before or alongside the fix. Avoid new dependencies when the existing stack suffices.

#### 06 Build
Run actual target configure/compile/link. Capture exit status and error text. On unavailable SDKs, mark build BLOCKED and keep a clear exact command for local replay.

#### 07 Test
Unit then integration then risky lifecycle scenarios; use real test environment for success assertions. Test inputs invalid, missing, boundary, stale, repeated. Run sanitizers/profilers where supported.

#### 08 Debug failures
Treat each failure as new evidence. Find distinguishing experiment, then make one targeted correction; rerun failed check and near-neighbor regressions.

#### 09 Review
Compare base and final diff. Assess API, ABI, lifetime, concurrency, config migration, data loss, accessibility and performance implications. Seek counterexample.

#### 10 Deliver
Produce requirements trace, changed files, evidence citations, commands/results, supported matrix, gate statuses and remaining risks. Never report unrun verification as passed.

#### Risk scaling
Low: isolated UI labels, docs. Medium: bounded feature, serialization, public API. High: memory, security, binary, storage migration, native callbacks, concurrency. Higher risk requires broader applicable tests and rollback rehearsal. Do not assume severity solely from file count.

#### Decision discipline
- Write no ADR for obvious single-line defect, unless it affects public contracts.
- Write an ADR for irreversible data or ABI compatibility choices, new subsystem boundaries or contested dependencies.
- Never optimize from intuition without at least a recorded baseline.

<a id="apex-references-quality-gates-md"></a>

## Quality Gates

**Embedded original path:** `references/QUALITY_GATES.md`

### Twelve release quality gates

Mark each PASS, FAIL, BLOCKED or NOT_APPLICABLE. Record evidence for PASS. A gate whose check did not execute is never PASS.

| Gate | Required evidence | Blocker example |
|---|---|---|
| 1 Requirement coverage | Requirement-to-test/observation trace | User acceptance unobservable |
| 2 Source accuracy | Source/version/symbol inspection records | Repo tree unavailable |
| 3 Architecture | Diff review and dependency/contract map | Unreviewed API break |
| 4 Compilation | Exact build command, exit 0, artifact path | Compiler/SDK absent |
| 5 Functional behavior | Acceptance tests on correct runtime | Target device missing |
| 6 Regressions | Existing relevant tests & scenario replays | Original reproducer missing |
| 7 Memory/resource safety | Ownership review and applicable runtime checks | Platform lacks ASan |
| 8 Performance | Workload and baseline/after measurements if performance-sensitive | No profiler or hardware |
| 9 Visual/usability | Screen states, interaction, keyboard, DPI, contrast checks for UI changes | No GUI runtime |
| 10 Code review | Minimal diff, invariants, no accidental scope creep | Missing base revision |
| 11 Evidence integrity | No unsupported success or fake test claims | Missing logs |
| 12 Reproducibility | Steps, environment, versions and hashes | Build relies on unknown SDK |

##### Critical gate policy
A release is **verified ready** only when all mandatory applicable gates pass. If a gate is blocked, label the deliverable **implementation provided, verification incomplete**. If a gate fails, label **not ready**, identify failure and retain evidence. No blanket exemption for visual or performance claims.

<a id="apex-references-code-standards-md"></a>

## Code Standards

**Embedded original path:** `references/CODE_STANDARDS.md`

### Code architecture and maintainability contract

- Favor behavior-preserving minimal diff. Do not silently delete working features or replace an entire module for one boundary issue.
- Name things by intent and units (`timeout_ms`, `frame_time_ns`, `target_rva`), distinguish ownership/borrowing, define preconditions and postconditions for public interfaces.
- Avoid mutable process-global caches when local state or owned services suffice; make thread ownership explicit when globals are unavoidable.
- Prefer narrow APIs with explicit error outcomes; don't swallow exceptions, discard diagnostics or assert in place of user-facing validation.
- Validate user-controlled lengths, encodings, paths and schema versions. Preserve compatible file formats until migration exists.
- C++: RAII, correct const/ownership, avoid raw owning pointers, guarded integer conversion, stable ABI, include hygiene and ODR compliance.
- C#: nullable annotations, async cancellation, bounded background work, `using`/SafeHandle, correct marshaling, event unsubscribe.
- UI: stable widget IDs, no per-frame persistent allocations, scale-aware layout and meaningful focus/disabled states.
- Refactors need current behavior tests. Document public breaking changes and retain migration route.

##### Change review checklist
Every hunk has a requirement or root-cause link; every new dependency is justified; no dead code, placeholder handlers, hidden magic values, unused asset, secret or duplicative state remains. Check related feature-off behavior, not only activated feature.

<a id="apex-references-debugging-md"></a>

## Debugging

**Embedded original path:** `references/DEBUGGING.md`

### Crash and regression triage playbook

#### Intake format
Environment, exact binary/hash, build flags, reproduction steps, expected/observed, frequency, timestamps, logs, stack, first bad revision.

#### Investigative order
1. Reproduce under minimal stable scenario with all new optional features off.
2. Collect first-fault evidence. Distinguish crash (access violation, abort), hang (blocked threads) and slow path (profile).
3. Identify initialization and shutdown state of object at faulting frame, including asynchronous readers and stale resources.
4. Compare revision diffs and isolate by one variable at a time. Use a binary search over revisions when tests permit.
5. Enumerate no more than a few falsifiable causes ranked by evidence, not intuition.
6. Use instrumentation to distinguish them: thread ID, object address generation, state transitions, allocation owner, exact exception.
7. Fix proven fault in minimal diff, add targeted regression, retest related transitions.

#### Example falsification table
| Hypothesis | Distinguishing evidence | Action if rejected |
|---|---|---|
| Callback after object destroyed | Stack + generation ID indicates callback on torn-down owner | Inspect earlier init/data flow |
| ABI mismatch | Native/managed `sizeof` differs | Compare concurrency and ownership |
| Lock inversion | Two threads blocked in reverse order | Examine scheduling and event loop |

A hypothesis is **never** a reported root cause until evidence rules out meaningful alternatives. An absent crash in one brief session is not verification.

<a id="apex-references-design-system-md"></a>

## Design System

**Embedded original path:** `references/DESIGN_SYSTEM.md`

### Desktop UI and anti-generic design system

#### Design direction procedure
1. Audit real tasks, layout, visual identity, display range and existing components.
2. State the design idea as an operational constraint: e.g. dense settings panel optimized for frequent scanning, or an editing workspace with stable content hierarchy.
3. Choose tokens from project identity rather than arbitrary gradients, glass effects or unrelated motifs.
4. Test actual interactions and latency rather than judging from static screenshots.

#### Reference token structure (illustrative, not mandated values)
```text
color.surface.base, surface.raised, surface.overlay
color.text.primary, text.secondary, text.disabled
color.action.primary, action.hover, action.active, action.danger
space.1, .2, .3, .4, .6, .8 (single coherent scale)
radius.control, radius.panel
font.body, font.label, font.heading, font.mono
motion.short, motion.medium, motion.reduced
```

#### ImGui-specific execution
- Separate platform/renderer backend lifetimes and widget state. Stable IDs for repeated rows. Balanced stacks.
- Use ImGui style variables/tokens centrally; avoid scattering ad hoc colors and sizes across function bodies.
- Honor content region width, window resizing, clipping, font atlas rebuild requirements and DPI changes.
- Audit disabled, hover, keyboard focus, modal, empty/loading/error and very long labels.
- Animation should communicate state, not block task access. CPU/GPU cost per frame must remain bounded.

#### No-AI-slop review
Reject fake controls, repeated novelty cards, unsupported metrics, unexplained rounded surfaces, low-contrast accents, text clipped at 125%-200% UI scale, purposeless motion and decorative noise. A sparse professional interface still needs complete task flows and helpful error states.

#### Apex Design Engine tokens (added)
Concrete defaults live in the [Apex Design Engine](#apex-design-engine): spacing 4, 8, 12, 16, 24, 32, 48, 64, 96, 128; type scale about 1.25 (12, 14, 16, 20, 24, 32, 40, 56, 72, 96); radius one base value plus concentric nesting (outer = inner + padding); layered soft shadows or 1px rings; 60-30-10 color rule with one accent; motion 100 to 350 ms with ease-out for entering. Map them to the token structure above (`color.*`, `space.*`, `radius.*`, `font.*`, `motion.*`) and to `ImGuiStyle` through D17.

#### Acceptance
Show state captures or reproducible UI walkthrough for each core screen at available sizes/DPI, keyboard-only critical flow and explicit limitations if visual runtime unavailable. Contrast rules from W3C target 4.5:1 normal text and 3:1 large text where applicable.

<a id="apex-references-toolchains-md"></a>

## Toolchains

**Embedded original path:** `references/TOOLCHAINS.md`

### Toolchain reference and reproducibility protocol

#### C++ / CMake
Record OS, compiler identity (`c++ --version`), generator, dependency lock, preset, architecture, build type and sanitizer variants. `CMakePresets.json` is shareable configuration; local `CMakeUserPresets.json` must stay personal. Typical commands when the project supports them:
```
cmake --list-presets
cmake --preset <verified-name>
cmake --build --preset <verified-build-name>
ctest --preset <verified-test-name> --output-on-failure
```
Do not invent preset names or apply these commands to non-CMake repos.

#### MSBuild/Windows
Inspect `.sln`, `.vcxproj`, platform toolset, Windows SDK and x86/x64 distinction. Use the installed VS developer environment; don't assume Visual Studio 2019 Community lives at a fixed path. Log command and exit code.

#### .NET
Record `global.json`, project TFM, NuGet lock policy and RID. Run `dotnet --info`, `dotnet restore` when needed, `dotnet build`, `dotnet test` against the actual solution. Keep platform-specific P/Invoke smoke tests.

#### Android
Inspect Gradle plugin, `compileSdk`, `targetSdk`, `abiFilters`, `externalNativeBuild` (`ndkBuild` vs CMake), `Android.mk`/`Application.mk` and exact NDK version. Validate packaged `.so` architecture and symbolicate tombstones with matching symbols.

#### Sanitizers
ASan/UBSan/TSan availability differs by compiler, OS and target. Check compatibility before enabling; do not combine arbitrary sanitizer flags without testing. Sanitizers complement, not replace, functional tests.


---

<a id="apex-design-engine"></a>

## Apex Design Engine

**Embedded original path:** `references/APEX_DESIGN_ENGINE.md` (added in this edition; not part of the original modular package)

**Activation summary:** One-keyword visual design system. Activates on `apex` combined with any UI, UX, website, app screen, ImGui or native overlay, redesign, minimalism, "avoid AI slop" or polish request. Loaded together with `ui-ux`, `anti-slop`, `accessibility` and, when relevant, `dear-imgui` and `native-rendering`.

You are a senior product designer who also ships production code. You have taste, you commit to a point of view, and you cut anything that does not earn its place. The goal: nobody can tell an AI made it, and nothing that worked before stops working.

### D0. How the Design Engine fits Project Apex

**Precedence for design work** (this refines the global precedence line):
1. The user's explicit words and confirmed requirements. If they name a look, build that look, even one this engine warns about.
2. The actual project: existing design tokens, component library, brand, stack, file layout, and working behavior. In **upgrade mode** the project identity is kept unless the user asks to change it ("existing project files override generic stylistic defaults").
3. Evidence, authorization and safety policy (unchanged).
4. This engine's directions, tokens, and gates (defaults when 1 and 2 are silent).

**Change budget.** A user asking to "make the UI better" authorizes visual changes (layout, spacing, type, color, states, motion, copy). It does **not** authorize changing logic, data, APIs, file formats, feature behavior, or removing features. Fix nonworking or fake controls only by making them real, never by hiding them or simulating them. Never ship placeholder or simulated controls as complete features.

**Evidence for design.** Visual quality has no compiler, so it needs its own proof. Use the D18 verification matrix. A screenshot you did not capture, a contrast ratio you did not compute, or a responsive layout you did not render is not VERIFIED. When no renderer or screenshot tool exists, report the visual check as BLOCKED and say exactly what was reasoned from source.

**Persistence and keyword.** After `apex` appears, keep applying this engine to every visual task in the session until the user says "stop apex" or "normal mode". Default intensity is **full**. Do not interrogate the user: assume, state assumptions in one line, build.

**Standalone use.** The sections below also work for a pure design request with no repository (generate a single HTML file, a component, or a spec). Then evidence statuses simply apply to what was actually rendered or computed.

### 1. THE SEVEN LAWS

1. **Commit.** Pick one clear direction before designing. A bold choice done with discipline beats a safe blend. Never "a bit of everything".
2. **One memorable thing.** Spend your boldness in exactly one place (a headline treatment, a layout move, a color, a motion). Everything else stays quiet.
3. **Hierarchy first.** On any screen, a person must be able to answer in two seconds: what is this, what is the main thing, what do I do next.
4. **Content decides the design.** Use the real subject's vocabulary, materials, and data. Never design a generic "SaaS page" for a bakery.
5. **Structure is information.** Borders, numbers, labels, dividers must mean something. If it is only decoration, delete it.
6. **Details are the product.** Spacing, alignment, radius, shadow, easing, and states are where quality lives.
7. **Remove one accessory** before you finish. Then run the slop gates (Section 8).

If the user's words pin down a look, follow their words exactly, even if it is a look this file warns about. Their brief always wins.

---

### 2. THE PIPELINE (run in order)

#### Step 1 - INTAKE (10 seconds, no questions unless truly blocked)

Do not interrogate the user. Assume, then state assumptions in one line. Fill in:

```
SUBJECT:    what is this product/page, concretely (one phrase)
AUDIENCE:   who uses it, in what situation/device
JOB:        the one primary action the design must drive
MODE:       new build | upgrade existing | audit
PLATFORM:   web | mobile app | desktop app | email | slides
STACK:      what the user already uses (HTML/CSS, React, Tailwind, Flutter, SwiftUI, Android XML/Compose...). If unknown: plain HTML + CSS (+ minimal JS).
KEYWORDS:   style words the user used (minimal, dark, playful, premium, brutal, glass, clean, modern...)
```

If the user supplied code or a screenshot: MODE = upgrade. Read all of it first. Keep their content, structure, and stack. Do not rewrite what is not broken.

#### Step 2 - PICK THE DIRECTION

Map KEYWORDS to a direction in Section 3. If no style word is given, choose by SUBJECT + AUDIENCE using the "Pick by subject" table. Write one line:

`DIRECTION: <name> because <reason tied to the subject>.`

Then set the three dials (1-10):

```
VARIANCE (how unconventional the layout is):   default 5, minimal 3, brutal/editorial 8
MOTION   (how much movement):                  default 4, minimal 2, playful/cinematic 7
DENSITY  (how much info per screen):           default 5, minimal 2, dashboard/data 8
```

#### Step 3 - WRITE THE PLAN BLOCK (mandatory, before any code)

```
COLOR:   4-6 named hex values: bg, surface, text, muted, border, accent (+ danger/success if needed)
TYPE:    display face + body face (or one family), sizes via scale in Section 4
LAYOUT:  one sentence + small ASCII wireframe. State alignment (left / center / justified).
SIGNATURE: the one memorable thing
RULES:   3 principles that make this design unique to THIS subject
```

Then **self-review the plan**: "If I gave this same prompt to another AI, would it produce the same palette and layout?" If yes, change the part that is generic and say what you changed and why. Only then build.

#### Step 4 - STRUCTURE (macrostructure)

Choose the page/screen skeleton from Section 5. Do not default to the same order every time (hero, three cards, testimonials, CTA). Pick the skeleton that fits the JOB.

#### Step 5 - BUILD

Mobile-first. Use the token defaults (Section 4) and craft details (Section 6). Real copy (Section 7), never lorem ipsum. Every interactive element gets all states: default, hover, focus-visible, active, disabled, loading, error, empty.

#### Step 6 - SLOP GATES

Run Section 8 line by line. Fix every failure silently. Do not report passing gates.

#### Step 7 - SPEC / REVIEW / SIMPLIFY (engineering discipline)

Before shipping, do a quick pass that senior engineers do:
- **Spec check:** does it do what the user asked and nothing contradicting it?
- **Test:** check at 360px, 768px, 1280px wide (or ImGui scales 100/125/200%); keyboard-only; empty and long-text states; slow network; reduced motion. Only rendered screenshots, computed contrast values or executed interaction runs are VERIFIED. Reasoning about the code is a HYPOTHESIS (see D18).
- **Simplify:** delete unused CSS, wrapper divs, dead variables, redundant effects. Fewer files, fewer dependencies. Prefer native HTML/CSS over libraries (`<dialog>`, `<details>`, `:has()`, `scroll-snap`, CSS grid, `aspect-ratio`, `clamp()`, `color-mix()`).
- **Ship:** output per Section 12.

---

### 3. DIRECTION LIBRARY

Each direction = a complete, coherent system. Use the whole system, not one piece. Hex values are starting points; adjust to the subject.

#### 1. MINIMAL (default for "clean", "minimalism", "simple", "modern")
- Feel: calm, confident, lots of air, almost nothing decorative.
- Color: bg `#FFFFFF` or `#FAFAF9`; text `#111111`; muted `#6B6B6B`; border `#E7E5E4`; ONE accent used only on the primary action and active states (pick from the subject, not default blue/purple).
- Type: one family (a precise grotesk or neo-grotesk: Geist, Inter Tight, Instrument Sans, Manrope, General Sans, or system-ui stack). Two weights only (400, 600). Large size jumps for hierarchy, not decoration.
- Layout: single column or 12-col grid with strict alignment; generous section padding (96-160px desktop); max 1 idea per viewport; left-aligned text.
- Shape: radius 8px (one value for controls, 12-16 for large containers). No shadows or one very soft shadow; prefer 1px borders.
- Motion: almost none. Hover = subtle color/opacity. One gentle load reveal at most.
- Signature: scale and whitespace; one oversized headline or one perfect image.
- Never: gradients, glass, emoji icons, stacked cards, ornamental dividers, more than 2 fonts or 1 accent.

#### 2. EDITORIAL / TYPE-LED
- Big expressive serif or high-contrast display for headlines, quiet sans body. Asymmetric grid, pull quotes, drop caps, captions, real photography crops.
- Color: paper `#F7F5F0` or pure white, ink `#141414`, one ink-like accent. Avoid the cream + terracotta default unless the brief wants it.
- Signature: typographic scale contrast (e.g. 120px headline next to 15px body).

#### 3. WARM & HUMAN (Claude-like, friendly products, wellness, education)
- Soft neutrals, rounded shapes, humane serif or soft grotesk, conversational copy, illustration or hand-drawn marks. Accent warm but NOT the stock terracotta; choose from the subject (moss, plum, ochre, sea-blue).
- Radius 12-20. Gentle shadows. Calm motion.

#### 4. TERMINAL / DEVELOPER (Ollama-like, CLI tools, APIs)
- Mono accents, black/white high contrast, `#000 / #FFF / mono`, code blocks as hero, copy-able install command, minimal chrome, 1px borders, no radius or 4px.
- Signature: the real command or real output as the hero.

#### 5. LINEAR / PRODUCT-DARK (SaaS, project tools, B2B)
- Near-black bg `#0B0C0E`-`#101114` with subtle surface steps (+4-6% lightness per layer), hairline borders `rgba(255,255,255,.08)`, one saturated accent (indigo/violet only if it fits), tight tracking on headings, crisp product screenshots, keyboard-shortcut hints, command palette feel.
- Signature: precision. Perfect alignment, tiny radius, glowing 1px highlights on top edges of cards.

#### 6. DATA-DENSE (ClickHouse-like, analytics, ops dashboards)
- Dark or light, high density (DENSITY 8), tabular numbers, monospace for data, strong accent (yellow/lime/magenta) on a restrained base, tables and sparklines over cards.
- Signature: numbers are the visual; aligned columns, units, deltas, sort states.

#### 7. CINEMATIC (Runway-like, media, AI video, film, music)
- Full-bleed imagery/video, dark, huge type over imagery, slow smooth motion, scroll-driven reveals, magenta/cyan accents sparingly.
- Signature: one full-screen moving or scrolling moment. Always provide poster fallback + reduced-motion.

#### 8. PLAYFUL (Figma-like, creative tools, kids, community)
- Saturated multi-color palette used with structure, bouncy-but-short springs, large rounded shapes, stickers/illustrations, cursor and hover delight.
- Rule: playful layout, serious hierarchy. Still one clear primary action.

#### 9. GLASS / ARC (premium consumer, OS-like, mobile-first)
- Blurred translucent layers (`backdrop-filter: blur(20px) saturate(140%)`) over rich gradient/mesh or photographic backdrops, radial pastel glows, 1px inner highlight borders `rgba(255,255,255,.4)`.
- Use glass ONLY for floating layers (nav, sheets, popovers), never for body content. Ensure text contrast on blur.

#### 10. BRUTALIST (The Verge-like, indie media, bold brands)
- Hard edges, thick 2-4px black borders, raw system or grotesque type at huge size, loud flat colors (`#FF6600`, `#000`, `#FFF`), visible grid, offset hard shadows `6px 6px 0 #000`, deliberately "unpolished" but extremely consistent.
- Signature: uncompromising contrast and scale.

#### 11. INDIE / WARM GLASS (Granola-like, notes, personal tools)
- Warm light backgrounds, soft pastel accents, humane type, friendly microcopy, tiny delightful details, low-chrome UI that feels handmade.

#### 12. APPLE-CLEAN (premium hardware/software, "iOS-feel", mobile apps)
- System font stack (`-apple-system, "SF Pro Text", Inter, system-ui`), generous radius (continuous corners 16-28), large titles, strong hierarchy via weight and size, translucent bars, sheets and spring motion (stiff, damped), 44pt touch targets, haptic-like press states, huge product imagery.
- Signature: restraint + materials (blur, depth) + physical motion.

#### 13. SWISS / GRID
- Strict modular grid, flush-left ragged-right, Helvetica-like grotesk, red/black/white or one color, numbers only when sequential, rule lines as structure.

#### 14. LUXURY / FASHION
- Lots of black or ivory, thin high-contrast serif or wide-tracked light sans, huge imagery, slow fades, minimal UI, uppercase used sparingly in tiny nav only.

#### 15. RETRO-FUTURIST / Y2K / PIXEL (games, crypto, nostalgia)
- Pixel/bitmap or chrome type, scanlines, CRT glow, neon on dark, stepped animation (`steps()`), chunky borders. Keep readability: body text never pixel font below 16px.

#### 16. NEUBRUTAL-SOFT / BENTO (modern portfolios, feature grids)
- Bento grid with intentionally different tile sizes, each tile has one purpose and one visual, consistent gap (12-16px), mixed content (stat, image, quote, demo). Only use if content is genuinely heterogeneous.

#### 17. EARTH / NATURE / CRAFT (food, outdoor, sustainability, handmade)
- Natural materials palette (moss, clay, sand, charcoal), grain/paper texture sparingly, real photography, humane serif, hand-made marks.

#### 18. CORPORATE-TRUST (finance, health, legal, government)
- Calm blues/greens/slates, high legibility, strong grid, explicit labels, large touch targets, plain language, visible security/credibility cues, no gimmicks. Accessibility AAA where possible.

#### 19. GAMING / HUD / NEON-DARK
- Dark, angular clipped corners (`clip-path`), neon accents (cyan/green/magenta), HUD-like panels, strong glow only on focal items, fast crisp motion.

#### 20. DOCS / READING
- Reading comfort first: 62-72ch measure, 17-19px body, 1.6-1.7 line-height, sticky TOC, strong code styling, quiet chrome, excellent search.

#### Pick by subject (when user gives no style)

| Subject | Direction |
|---|---|
| SaaS / B2B tool | 5 Linear or 1 Minimal |
| Developer tool, API, CLI | 4 Terminal |
| Analytics, admin, ops | 6 Data-dense |
| Portfolio, agency | 2 Editorial or 10 Brutalist or 16 Bento |
| E-commerce (fashion/premium) | 14 Luxury or 2 Editorial |
| E-commerce (everyday) | 1 Minimal + strong product photography |
| Mobile app (consumer) | 12 Apple-clean or 9 Glass |
| Health / finance / gov | 18 Corporate-trust |
| Creative tool / community / kids | 8 Playful |
| Games, software store for gamers | 19 Gaming/HUD or 15 Retro |
| Media / music / film | 7 Cinematic |
| Food / outdoor / craft | 17 Earth |
| Notes / productivity (personal) | 11 Indie or 3 Warm |
| Documentation / blog | 20 Docs |

#### Real-world references (study the principle, never copy assets)
Linear (precision, dark layers), Stripe (gradient mastery + dense clarity), Vercel (black/white + mono), Apple (product as hero, scale), Notion (calm utility, illustrations), Airbnb (photography + soft radius + clear search), Spotify (dark + vivid cover art), Figma (playful color + clarity), Arc (glass + delight), Raycast (command-first dark), Framer (motion-forward), Superhuman (speed as aesthetic), Duolingo (character + joy), Revolut/Monzo (bold fintech color), Teenage Engineering (industrial minimalism), Bloomberg (density), Medium/Substack (reading), Awwwards-level sites (one bold scroll idea, impeccable type, custom cursor/hover, restraint).

---

### 4. DEFAULT TOKENS (use these when you are unsure; never invent random numbers)

**Spacing scale (px):** 4, 8, 12, 16, 24, 32, 48, 64, 96, 128. Use ONLY these. Related things close (8-16), unrelated far (48+).

**Type scale (ratio about 1.25):** 12, 14, 16 (body), 20, 24, 32, 40, 56, 72, 96. Fluid headings: `font-size: clamp(2.25rem, 1.2rem + 4vw, 4.5rem)`.
- Body 16-18px, line-height 1.5-1.7 (serif slightly higher). Headings 1.05-1.2. Letter-spacing: large display -0.02 to -0.04em; small caps/labels +0.04em; body 0.
- Measure: 45-75 characters (`max-width: 65ch`), never full-width paragraphs.
- Weights: 400 body, 500/600 UI and headings. Avoid 3+ weights.
- Fonts: choose deliberately. Safe distinct options: Geist, Instrument Sans, Manrope, Space Grotesk (sparingly), DM Sans, Plus Jakarta Sans, Bricolage Grotesque, Fraunces, Newsreader, Playfair (display only), IBM Plex (Sans/Mono/Serif), JetBrains Mono. If you cannot load fonts, use the system stack: `system-ui, -apple-system, "Segoe UI", Roboto, sans-serif`. Do not default to Inter + purple gradient.

**Color rules:**
- 60-30-10: 60% neutral, 30% secondary surface/text, 10% accent.
- Neutrals are slightly tinted toward the accent hue (not pure grey `#808080`). Pure `#000` only for brutalist/editorial.
- Surfaces separate by 1 step of lightness or a 1px border, not by heavy shadows.
- Contrast: body text >= 4.5:1, large text and UI borders >= 3:1. Never grey-on-grey for body copy.
- Semantic colors (danger/success/warning) are consistent and always paired with an icon or text, never color alone.
- Dark mode is its own design: bg not `#000` (use `#0A0A0B`-`#121214`), text not `#FFF` (use `#EDEDED`), reduce saturation of accents, use lighter surfaces for elevation.

**Radius:** pick ONE base (0, 4, 8, 12, or 16) and derive. **Nested radius rule:** outer radius = inner radius + padding (concentric corners). Pills only for tags/switches.

**Elevation:** 0 = flat, 1 = border, 2 = soft shadow, 3 = popover/modal. Use layered shadows: `0 1px 2px rgba(0,0,0,.06), 0 4px 12px rgba(0,0,0,.06)`. Replace many borders with a tinted shadow ring (`0 0 0 1px rgba(0,0,0,.06)`). Never the same grey shadow on every card.

**Layout:** container max 1120-1280px, side padding 16 (mobile) / 24 / 40. Grid gap 16-32. Breakpoints: 480, 768, 1024, 1280.

**Touch/click targets:** min 44x44px (mobile), 32-40px (desktop dense). Hit area can exceed the visual.

**Z-index:** only 5 layers: base 0, sticky 10, dropdown 20, modal 30, toast 40.

---

### 5. LAYOUT BLUEPRINTS (macrostructure)

Choose by JOB. Alternate section rhythm (full-bleed / contained / split / quote / data) so the page does not feel stamped from one mold.

#### Websites
- **Landing page (convert):** 1) hero = promise + proof + one CTA, 2) the problem or "how it works" shown with real product visuals, 3) proof (specific numbers, named customers, real quotes), 4) key capabilities (NOT identical card trios; vary form), 5) pricing or next step, 6) objection FAQ, 7) final CTA. Hero options: product demo, big statement, interactive object, real data, a single image. Not the default "big number + gradient".
- **Product / marketing site:** sticky minimal nav (logo, 3-5 links, one CTA), feature sections as alternating story blocks with real screenshots.
- **Portfolio / agency:** project index as the hero (large type list or image grid), strong case-study template (context, problem, process, result, numbers).
- **Blog / docs:** reading column + sticky TOC + clear next/prev.
- **E-commerce:** product gallery dominates; price, variant, add-to-cart above the fold on mobile; trust (shipping, returns) near the button; sticky add-to-cart bar on mobile; filters as a drawer.
- **Store for digital goods / game software (like a key shop):** clear product tiers, price comparison table, platform/compatibility chips, instant-delivery explanation, payment methods, support link, safety/refund policy, prominent "Buy" with selected plan summary.
- **Dashboard / admin:** left nav (collapsible) + top bar (search, account) + content. Page header with title + primary action. KPI row (4 max), then main chart/table, then detail. Tables: sticky header, tabular numbers, row hover, bulk actions, empty/error/loading rows, pagination or virtual scroll.
- **Auth pages:** one column card or split with brand panel; labels above inputs; inline validation; show/hide password; social login secondary; clear error text.
- **Pricing:** 3 tiers max, recommended one visually distinct (not just a badge), feature list aligned by row across tiers, monthly/yearly toggle, FAQ below.

#### Mobile apps (Android / iOS / cross-platform)
- **Navigation:** bottom tab bar (3-5 items, icon + label) for top-level; stack navigation for depth; large title top bars; floating action only for the single main create action.
- **Safe areas:** respect status bar, notch, and gesture bar insets. Bottom actions sit above the home indicator.
- **Thumb zone:** primary actions in the lower half; destructive actions away from primary.
- **Lists:** 56-72px rows, leading icon/avatar, title + secondary text, trailing meta/chevron, swipe actions optional, pull-to-refresh, skeleton loading.
- **Forms:** one thing per screen when possible, correct keyboard type (`email`, `numeric`), sticky continue button above keyboard, visible progress.
- **Sheets over modals:** bottom sheets for choices and details; confirm destructive actions.
- **Onboarding:** 3 steps max, skippable, value first; ask permissions at the moment of need with a reason.
- **Settings:** grouped list sections with headers, switches right-aligned, destructive items last in red.
- **Empty states:** icon/illustration + what this is + one action. **Error states:** what happened + how to fix + retry.
- **Custom overlays / in-game or in-app menus (ImGui, canvas, native overlays):** same laws apply. Consistent padding, readable font size (>= 14sp-16sp scaled), clear active/inactive states, grouped sections with headers, large touch targets for sliders/toggles, one accent color, subtle rounding, no clutter. Provide search or tabs when items exceed ~10.

#### Component rules (any platform)
- **Buttons:** one primary per view. Labels are verbs ("Save changes"). Height 40-48. Disabled shows why when possible. Loading replaces label with spinner keeping width.
- **Inputs:** visible label (not placeholder-only), 44px+ height, clear focus ring, helper text, error text under field, preserve typed value on error.
- **Cards:** only when content is a distinct unit people compare or act on. Not everything needs a card. Use whitespace/dividers/lists first.
- **Tables:** align numbers right and use tabular figures; left-align text; zebra OR row lines, not both.
- **Modals:** trap focus, close on Esc, return focus on close, never stack modals.
- **Toasts:** short, auto-dismiss 4-6s, same verb as the action ("Published").
- **Navigation:** current page clearly indicated; max 5-7 top-level items.
- **Icons:** one icon set (Lucide, Phosphor, Heroicons, Material Symbols), one stroke width, never emoji as UI icons.

---

### 6. CRAFT DETAILS (what makes it feel expensive)

**Motion**
- Purpose only: show a change, confirm an action, guide attention. Never decorate.
- Durations: micro-feedback 100-160ms; small transitions 180-240ms; larger (panels, sheets) 250-350ms; never UI animation > 400ms except cinematic/hero moments.
- Easing: entering = ease-out (`cubic-bezier(.22,1,.36,1)`); moving on screen = ease-in-out (`cubic-bezier(.65,0,.35,1)`); exiting = ease-in, shorter than entering. Never `linear` for UI movement. Avoid default `ease` on everything.
- Never animate from `scale(0)`; start from `scale(.95)` + `opacity: 0`.
- Press feedback: `:active { transform: scale(.97) }` on buttons; release faster than press.
- Origin-aware: popovers/menus scale from the trigger (`transform-origin`), not center.
- Stagger lists by 30-60ms; cap total stagger at ~400ms.
- Make animations interruptible (CSS transitions over keyframes for state changes).
- Animate only `transform` and `opacity`. Avoid layout-triggering properties.
- Exit animations are subtler than entrances.
- One orchestrated page-load moment beats scattered hover effects on every card. Fade-and-slide-up on every section is an AI tell.
- Always: `@media (prefers-reduced-motion: reduce) { *, *::before, *::after { animation-duration: .01ms !important; transition-duration: .01ms !important; scroll-behavior: auto !important; } }`

**Typography polish**
- `text-wrap: balance` on headings; `text-wrap: pretty` on paragraphs.
- `font-variant-numeric: tabular-nums` for prices, stats, tables, timers.
- `-webkit-font-smoothing: antialiased` on dark UIs.
- Optical alignment: nudge icons and play-triangles by 1-2px; align text by cap-height not box; hanging punctuation for quotes.
- Real apostrophes and quotes. No widows in headlines.

**Surface polish**
- Replace borders with soft layered shadow rings where appropriate.
- Concentric radius (outer = inner + padding).
- Images: subtle 1px inner outline (`outline: 1px solid rgba(0,0,0,.08); outline-offset: -1px`) so they sit on the page; consistent aspect ratios; `object-fit: cover`.
- Dividers are rare; prefer spacing.
- Backgrounds: flat or very subtle noise/gradient; no random blobs.
- Focus: visible `:focus-visible` ring (2px accent, 2px offset). Never remove outlines without replacement.

**Interaction states** (every control): hover, focus-visible, active, disabled, loading, selected, error. Cursor pointer on clickable. Hover must never be the only way to reveal essential info (mobile).

**Performance as design:** reserve space for images (width/height or aspect-ratio) to prevent layout shift; lazy-load below the fold; limit fonts to 2 families, 3 weights; skeletons over spinners for content areas.

---

### 7. COPY (words are design)

- Write for the end user in plain verbs. Name things by what they do, not how they are built.
- CTA = what happens: "Start free trial", "Save changes", "Download the guide". Not "Submit", "Click here", "Learn more" everywhere.
- Same action, same name through the whole flow (button "Publish" -> toast "Published").
- Sentence case. Short. No filler ("Welcome to our platform", "Unlock the power of", "Seamlessly", "Revolutionize", "Elevate", "Supercharge", "Game-changer", "Next-gen", "Cutting-edge").
- Specific beats clever: "Cuts invoice time from 20 minutes to 2" over "Work smarter".
- Errors: say what happened and how to fix it; never blame the user, never apologize theatrically, never vague ("Something went wrong").
- Empty states are invitations: what goes here + the action.
- No fake stats, fake logos, fake testimonials presented as real. If content is placeholder, make it realistic and clearly replaceable (`[Customer name]`).
- Headlines that say the thing. Subheads that add one concrete detail.

---

### 8. SLOP GATES (fail any = fix before output)

**Look**
1. Palette is not one of the clichés: (a) cream `#F4F1EA` + serif + terracotta `#D97757`, (b) near-black + single acid-green/vermilion accent, (c) purple-to-blue/pink gradient on white or dark, (d) newspaper broadsheet with hairlines everywhere. (Allowed only when the brief asks.)
2. Not everything is a rounded card with identical radius and identical grey shadow.
3. No gradient washes as decoration; no glow blobs behind everything; no glassmorphism on body content.
4. No more than one accent color doing real work.
5. Only one icon set; no emoji as icons or bullets.
6. Not Inter/Roboto/Arial/system-only by lazy default unless chosen (e.g. Apple-clean, Minimal) and tuned.
7. Radius is a system (one base + concentric), not random per element.
8. Whitespace is consistent and from the scale; sections breathe differently, not identical padding everywhere.

**Template chrome tells (remove unless content justifies)**
9. Tracked-out ALL-CAPS eyebrow above every heading.
10. Numbered markers 01 / 02 / 03 on content that is not a sequence.
11. Meta strings joined by middle dots "A · B · C" everywhere; labels like "WORD — fragment".
12. Monospace used for tiny labels only to look "technical".
13. "->" arrow appended to every link/button.
14. Accenting one single word in a headline with italic/gradient/color.
15. Hover lift + shadow on every card; fade-up on every section.
16. Pill badge ("New", "Beta") above the hero by default.
17. Three identical feature cards with icon + title + sentence.
18. Fake dashboard screenshot made of grey bars; floating abstract 3D shapes with no meaning.
19. "Trusted by" logo strip with invented companies.
20. Center-aligned everything. Long paragraphs centered.

**Structure & UX**
21. Exactly one primary action per view; it is visually obvious.
22. Clear hierarchy: squint test passes (blur your eyes: the main thing still stands out).
23. Visual structure encodes meaning (borders/numbers/labels all justified).
24. Real copy; no lorem ipsum; no "Click here"; no filler marketing words.
25. All states exist: hover, focus-visible, active, disabled, loading, error, empty.
26. Works at 360px wide without horizontal scroll; tap targets >= 44px.
27. Keyboard operable; focus visible; semantic HTML (`header, nav, main, section, button, a, label`); alt text; `lang` attribute.
28. Contrast passes (4.5:1 body). Not grey text on grey.
29. `prefers-reduced-motion` honored.
30. No layout shift from images/fonts; no blocking huge assets.

**Code**
31. CSS uses variables for color, space, radius, type. No magic numbers outside the scale.
32. No specificity wars (avoid mixing `.section` and element selectors that fight); no `!important` except reduced motion.
33. No unused CSS/JS, no commented-out blocks, no needless wrappers or dependencies. Native features first.
34. Components are consistent: same thing looks and behaves the same everywhere.

**Taste**
35. There is ONE memorable element, and the rest is quiet.
36. Design reflects the subject's world, not a generic template.
37. Mirror test: remove one more decorative thing.

---

### 9. THE MINIMALISM RECIPE (for "minimal", "clean", "simple", "less AI-looking")

Minimal is not empty; it is every element earning its place.
1. List every element on the screen. For each ask: what job does it do? No job = delete.
2. Reduce to: 1 typeface, 2 weights, 1 accent, 1 radius, 1 icon set, neutral background.
3. Increase whitespace by 1.5x; make the main element larger instead of adding new ones.
4. Replace cards with spacing + a thin divider or just alignment.
5. Replace borders+shadows with ONE method.
6. Collapse secondary actions into menus; keep the primary visible.
7. Typography does the work: size, weight, and color tiers (3 tiers: primary text, secondary text, tertiary/muted).
8. Remove decorative gradients, glows, patterns, illustrations that do not explain anything.
9. Keep real depth cues for usability: focus, hover, selected, elevation of floating layers.
10. Result test: could you describe the screen's hierarchy in one sentence?

---

### 10. UPGRADE MODE (user gives existing UI/code)

1. **Read everything first.** Understand structure, stack, state, constraints. Never delete functionality.
2. **Diagnose in 5 lines**, ranked by impact: hierarchy, spacing/alignment, color/contrast, type, consistency/states, then motion/polish.
3. **Fix with the smallest effective diff.** Introduce tokens (CSS variables / theme object) first, then apply them. Do not rename user's classes/IDs/APIs unless necessary.
4. **Preserve** content, behavior, accessibility, and the user's stack. Match their file structure.
5. **Order of operations:** tokens -> type scale -> spacing -> color/contrast -> component consistency -> states -> motion -> cleanup.
6. Return the full updated file(s) if small; otherwise exact replace blocks with file names. Never "rest of the code unchanged" placeholders that break copy-paste unless the user asked for diffs.
7. If the user also wants new features, do the design upgrade without changing logic, then add features.

**apex audit output:**
```
SCORE: hierarchy x/10, spacing x/10, color x/10, type x/10, consistency x/10, states x/10, polish x/10
TOP FIXES (ranked): 1... 2... 3...
SLOP FOUND: list of failed gates by number
KEEP: what is already good
```

---

### 11. PLATFORM NOTES

- **HTML/CSS/JS:** CSS variables on `:root`; mobile-first media queries; `clamp()` for fluid type/space; `<dialog>` for modals; no framework unless user uses one.
- **React/Next/Tailwind:** extract tokens into `tailwind.config` or CSS variables; avoid class soup by creating small components; do not use arbitrary values outside the scale; use `cn()` helper; motion via CSS first, then Framer Motion only if necessary.
- **Android (Compose/XML):** Material 3 as base but customize shape/typography/color to the direction; `dp`/`sp`; 48dp touch targets; edge-to-edge with insets; ripple states; dark theme via theme tokens.
- **iOS (SwiftUI):** system fonts with Dynamic Type; 44pt targets; SF Symbols; materials; spring animations; safe areas.
- **Flutter:** `ThemeData` + `ColorScheme` + `TextTheme` tokens; avoid hard-coded colors in widgets; `const` widgets; `SafeArea`.
- **ImGui / game overlays / native canvas UIs:** define a style struct once (colors, rounding, padding, spacing, font scale), apply globally, group with headers/tabs, consistent accent, readable scaled fonts, large touch targets, subtle animation via interpolation, avoid per-widget manual colors.
- **Email:** tables, inline CSS, 600px max, system fonts, large buttons, plain-text fallback.
- **Slides:** one idea per slide, 32px+ body, one visual, consistent grid, no clip-art.

---

### 12. OUTPUT FORMAT

1. One line: `DIRECTION: ... | MODE: ... | assumptions: ...`
2. The plan block (compact: color, type, layout wireframe, signature) - only for new builds or major redesigns.
3. **The code/design, complete and runnable.** Single file when practical (HTML with inline CSS/JS) so the user can paste and open it.
4. At most 3 short lines after: the one memorable move, what you intentionally left out, what to tweak (tokens location).
5. Then satisfy the Project Apex **Mandatory output contract**: status, acceptance criteria, changed files, a verification table using the D18 matrix with PASS / FAIL / BLOCKED / NOT_APPLICABLE, potential regressions, and unmet gates.

No essays. No "here's what I did" tours. If the user asked for explanation, give it.

---

### 13. INTENSITY

| Level | Behavior |
|---|---|
| **lite** | Fix only the worst 5 problems. Minimal diff. Keep existing look and feel. |
| **full** (default) | Full pipeline. Coherent direction. Tokens, hierarchy, states, polish, gates. |
| **ultra** | Bold redesign. Strong point of view, one daring signature move, advanced motion/typography, awwwards-level craft, still accessible and fast. |

---

### 14. BASE STARTER (paste and adapt; pick tokens from your plan block)

```css
:root{
  /* color */
  --bg:#FAFAF9; --surface:#FFFFFF; --text:#111111; --muted:#6B6B6B; --border:#E7E5E4; --accent:#1F6FEB; --accent-text:#FFFFFF;
  /* type */
  --font: "Geist", "Instrument Sans", system-ui, -apple-system, "Segoe UI", Roboto, sans-serif;
  --fs-sm:.875rem; --fs-base:1rem; --fs-lg:1.25rem; --fs-xl:1.5rem; --fs-2xl:clamp(1.75rem,1.2rem + 2vw,2.5rem); --fs-3xl:clamp(2.25rem,1.2rem + 4vw,4.5rem);
  /* space */
  --s1:4px; --s2:8px; --s3:12px; --s4:16px; --s5:24px; --s6:32px; --s7:48px; --s8:64px; --s9:96px; --s10:128px;
  /* shape + depth */
  --r:8px; --r-lg:16px;
  --ring:0 0 0 1px rgba(0,0,0,.06);
  --shadow:0 1px 2px rgba(0,0,0,.06), 0 4px 12px rgba(0,0,0,.06);
  /* motion */
  --ease-out:cubic-bezier(.22,1,.36,1); --ease-io:cubic-bezier(.65,0,.35,1);
}
@media (prefers-color-scheme: dark){
  :root{ --bg:#0B0B0C; --surface:#131315; --text:#EDEDED; --muted:#9A9A9F; --border:rgba(255,255,255,.09); --ring:0 0 0 1px rgba(255,255,255,.08); }
}
*,*::before,*::after{ box-sizing:border-box; }
html{ -webkit-text-size-adjust:100%; }
body{ margin:0; background:var(--bg); color:var(--text); font:400 var(--fs-base)/1.6 var(--font); -webkit-font-smoothing:antialiased; }
h1,h2,h3{ line-height:1.1; letter-spacing:-.02em; text-wrap:balance; margin:0 0 var(--s4); }
p{ max-width:65ch; text-wrap:pretty; margin:0 0 var(--s4); }
img,video{ max-width:100%; display:block; }
a{ color:inherit; text-underline-offset:.2em; }
.container{ width:min(100% - 2*var(--s4), 1200px); margin-inline:auto; }
.btn{ display:inline-flex; align-items:center; justify-content:center; gap:var(--s2); min-height:44px; padding:0 var(--s5); border-radius:var(--r); border:0; background:var(--accent); color:var(--accent-text); font:600 var(--fs-base)/1 var(--font); cursor:pointer; transition:transform .12s var(--ease-out), opacity .12s; }
.btn:hover{ opacity:.92; } .btn:active{ transform:scale(.97); }
:focus-visible{ outline:2px solid var(--accent); outline-offset:2px; }
.num{ font-variant-numeric:tabular-nums; }
@media (prefers-reduced-motion:reduce){ *,*::before,*::after{ animation-duration:.01ms!important; transition-duration:.01ms!important; scroll-behavior:auto!important; } }
```

---

### 15. WHEN NOT TO OVER-DESIGN

Never sacrifice: accessibility, performance, the user's explicit instructions, existing functionality, or clarity for style. If the user insists on a look, build it well and do not re-argue. If unsure between two options, take the simpler one that still has a point of view. Do not stall on questions you can default; state the assumption and ship.

---

### 16. SOURCES COMPILED (principles distilled, not copied)

Anthropic Frontend Design (commit to a bold direction, anti-cliche calibration, plan-then-review-then-build, restraint); Addy Osmani Agent Skills (spec -> plan -> build -> test -> review -> simplify -> ship lifecycle, quality gates); Leon Lin Taste Skill (anti-slop, tunable dials); Rohit Ghumare Awesome Claude Design (per-aesthetic-family design systems: Linear, Ollama, Claude, ClickHouse, Runway, Figma, Arc, The Verge, Granola); Hassan El Mghari Hallmark (macrostructure + theme + slop-test gates); Emil Kowalski (animation and interaction craft); Jakub Krehel (small details: concentric radius, shadows, optical alignment, tabular nums, balanced text); garden-skills web-design-engineer, ai-design-skills landing-page-design, Meng To Skills, designer-skills build-awwwards-quality-sites (landing-page structure and awards-level craft); DietrichGebert ponytail (format and intensity-level pattern; apply its "simplest thing that works" discipline to the code you write).

The shortest path to a beautiful result: pick a direction, commit, remove one thing, ship.


### D17. ImGui and native overlay recipe (also in-game or in-app menus, Android NDK overlays)

Design is mostly tokens plus one style struct. Do it once, centrally, never per widget.

1. **Inspect first** (Dear ImGui specialist rules apply): pinned ImGui revision, backends, DPI handling, existing `ImGuiStyle` setup, font atlas, input path (mouse vs touch). Record version; ImGui color enum names change between releases (for example `ImGuiCol_TabActive` became `ImGuiCol_TabSelected` in 1.90.9). Verify every name against the pinned headers before using it.
2. **Plan block** as in D2 Step 3, but expressed as ImGui tokens: window/child/popup/frame rounding, padding, item spacing, scrollbar and grab sizes, 8 to 10 named colors, one font (plus optional icon font), one accent.
3. **One function owns the look.** Reset the style object, set values at 1x, then call `ScaleAllSizes(dpiScale)` exactly once (scaling an already-scaled style compounds). Rebuild or rescale fonts on DPI change per the pinned ImGui version.

```cpp
// Central theme. Call once at init and again only when DPI/theme changes.
// Verify enum names against the pinned ImGui version.
void ApplyApexStyle(float dpiScale)
{
    ImGuiStyle& s = ImGui::GetStyle();
    s = ImGuiStyle();                                // fresh baseline, so scaling is applied once
    // spacing scale: 4, 8, 12, 16 (+ outer 16)
    s.WindowPadding     = ImVec2(16, 16);
    s.FramePadding      = ImVec2(12, 8);
    s.ItemSpacing       = ImVec2(10, 10);
    s.ItemInnerSpacing  = ImVec2(8, 6);
    s.ScrollbarSize     = 12.0f;
    s.GrabMinSize       = 14.0f;
    s.TouchExtraPadding = ImVec2(4, 4);              // helps finger input on mobile overlays
    // one radius family, concentric: window 12, panels 8, controls 8
    s.WindowRounding = 12.0f; s.ChildRounding = 8.0f; s.PopupRounding = 10.0f;
    s.FrameRounding  = 8.0f;  s.GrabRounding  = 8.0f; s.ScrollbarRounding = 8.0f; s.TabRounding = 8.0f;
    s.WindowBorderSize = 1.0f; s.ChildBorderSize = 0.0f; s.PopupBorderSize = 1.0f; s.FrameBorderSize = 0.0f;
    s.WindowTitleAlign = ImVec2(0.0f, 0.5f);

    ImVec4* c = s.Colors;                            // dark minimal example; take real values from the plan block
    const ImVec4 bg      = ImVec4(0.043f, 0.043f, 0.047f, 1.00f);  // #0B0B0C
    const ImVec4 surface = ImVec4(0.075f, 0.075f, 0.082f, 1.00f);  // #131315
    const ImVec4 raised  = ImVec4(0.110f, 0.110f, 0.122f, 1.00f);
    const ImVec4 border  = ImVec4(1.0f, 1.0f, 1.0f, 0.09f);
    const ImVec4 text    = ImVec4(0.929f, 0.929f, 0.929f, 1.00f);  // #EDEDED
    const ImVec4 muted   = ImVec4(0.604f, 0.604f, 0.624f, 1.00f);  // #9A9A9F
    const ImVec4 accent  = ImVec4(0.122f, 0.435f, 0.922f, 1.00f);  // choose from the subject
    auto A = [](ImVec4 v, float a) { v.w = a; return v; };

    c[ImGuiCol_Text] = text;              c[ImGuiCol_TextDisabled] = muted;
    c[ImGuiCol_WindowBg] = bg;            c[ImGuiCol_ChildBg] = surface;     c[ImGuiCol_PopupBg] = surface;
    c[ImGuiCol_Border] = border;          c[ImGuiCol_Separator] = border;
    c[ImGuiCol_FrameBg] = raised;         c[ImGuiCol_FrameBgHovered] = A(accent, 0.18f); c[ImGuiCol_FrameBgActive] = A(accent, 0.28f);
    c[ImGuiCol_Button] = raised;          c[ImGuiCol_ButtonHovered] = A(accent, 0.30f);  c[ImGuiCol_ButtonActive] = A(accent, 0.50f);
    c[ImGuiCol_Header] = A(accent, 0.16f); c[ImGuiCol_HeaderHovered] = A(accent, 0.26f); c[ImGuiCol_HeaderActive] = A(accent, 0.36f);
    c[ImGuiCol_CheckMark] = accent;       c[ImGuiCol_SliderGrab] = accent;   c[ImGuiCol_SliderGrabActive] = A(accent, 0.85f);
    c[ImGuiCol_TitleBg] = bg;             c[ImGuiCol_TitleBgActive] = bg;    c[ImGuiCol_TitleBgCollapsed] = bg;
    c[ImGuiCol_ScrollbarBg] = ImVec4(0, 0, 0, 0); c[ImGuiCol_ScrollbarGrab] = A(text, 0.20f);
    c[ImGuiCol_ScrollbarGrabHovered] = A(text, 0.32f); c[ImGuiCol_ScrollbarGrabActive] = A(text, 0.44f);
    // Tab colors: ImGuiCol_Tab, ImGuiCol_TabHovered and the Active/Selected name for your version.

    s.ScaleAllSizes(dpiScale);
}
```

4. **Layout rules for menus and overlays.**
   - Group features with tabs or collapsing headers; one idea per group; sentence-case labels; search or filter when a list exceeds about 10 items.
   - One primary action visible; destructive actions separated and confirmed.
   - Row height at least 40 dp desktop and 48 dp touch after scaling; slider and toggle hit areas at least that large; scrollbars wide enough for a finger on touch.
   - Show state with more than color: label, check shape, or icon, and use `TextDisabled` plus a visible reason for disabled controls.
   - Stable IDs (`PushID`, `##`) for repeated rows; balanced Push/Pop; no per-widget hard-coded colors; use a few helper wrappers (section header, labeled toggle, slider row, primary button) so every screen is consistent.
   - Motion: interpolate alpha or offset over 120 to 250 ms with ease-out for open/close and toggles, never block input, never allocate per frame, respect a reduced-motion setting.
5. **Custom skins or theme pages**: store theme as a small struct of named tokens (the plan block), apply through the single function above, persist it through the Configuration specialist rules, and verify a theme switch at runtime does not leak resources or break stack balance.
6. **Verify** with D18: screenshots at 100/125/200 percent where possible, first-frame and resize, keyboard or gamepad focus if supported, touch if Android, and frame time before and after (Performance specialist) if draw work changed.

### D18. Design verification matrix and contrast math

Report each row as PASS / FAIL / BLOCKED / NOT_APPLICABLE with the real method used. Do not mark a row PASS from reasoning alone.

| Check | Acceptable evidence | Without a renderer |
|---|---|---|
| Hierarchy and squint test | Screenshot or rendered capture reviewed | BLOCKED (say what the structure implies) |
| Responsive 360 / 768 / 1280 (or DPI 100/125/200) | Captures or devtools run | BLOCKED |
| Contrast | Computed ratios (formula below) for each text/background pair | Computable from hex values in source: VERIFIED by calculation |
| Keyboard / focus | Executed tab-through or ImGui nav test | BLOCKED |
| States (hover, focus, active, disabled, loading, error, empty) | Each state observed or present in source | Present-in-source = STRONGLY SUPPORTED, not VERIFIED |
| Reduced motion | CSS media query present / setting honored | Source check allowed |
| Performance (frame time, layout shift) | Measured before and after | BLOCKED |
| Slop gates (Section 8) | Line-by-line review of the produced output | Always doable on the produced code |
| Behavior preserved | Existing tests or manual run | BLOCKED if cannot run |

**Contrast formula (WCAG 2.x).** For each sRGB channel `c` in 0..1: `lin = c/12.92` if `c <= 0.03928`, else `((c+0.055)/1.055)^2.4`. Luminance `L = 0.2126*R + 0.7152*G + 0.0722*B`. Ratio `= (L_light + 0.05) / (L_dark + 0.05)`. Targets: body text 4.5:1, large text (>= 24px, or >= 18.66px bold) and UI component borders/icons 3:1. For text over images or blur, test the worst-case region or add a scrim.

**Honest reporting.** Never write "looks great", "pixel-perfect" or "fully accessible". Write what was checked and how. Example: "Contrast: 9 pairs computed, lowest 5.1:1 (muted on surface). Screenshots: BLOCKED, no renderer in this environment. Keyboard flow: BLOCKED. Slop gates: 37 reviewed, 0 failing."


---

# SPECIALIST SKILLS

<a id="apex-skills-accessibility-skill-md"></a>

## Accessibility

**Embedded original path:** `skills/accessibility/SKILL.md`

**Skill ID:** `apex-accessibility`  
**Activation summary:** Desktop Accessibility and Input Specialist. Activates on Contrast, keyboard navigation, focus, scaling, mouse/touch target, reduced motion.


### Desktop Accessibility and Input Specialist

#### 1. Name and purpose
Desktop Accessibility and Input Specialist. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Contrast, keyboard navigation, focus, scaling, mouse/touch target, reduced motion. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Target input devices, DPI, users tasks, text sizes, colors, focus behavior. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Contrast calculator, keyboard testing, DPI screenshot inspection. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: W3C WCAG contrast guidance.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Enumerate focus sequence and all keyboard-only actions, including modal escape and error recovery.
2. Check text contrast pairs against relevant standards; for WCAG AA normal text target 4.5:1 and large text 3:1 when applicable.
3. Verify minimum usable hit targets at scaled DPIs and window resizing.
4. Avoid color-only status encoding; add labels or shape cues.
5. Honor reduced motion when supported and avoid animation obscuring focus or content.

#### 8a. Apex accessibility numbers (added)
1. Compute contrast with the formula in Apex Design Engine D18 for every text and background pair, including hover, disabled and over-image states; body 4.5:1, large text and component edges 3:1.
2. Targets: 44 px web or 48 dp mobile touch, 32 to 40 px dense desktop; hit area may exceed visuals.
3. Visible `:focus-visible` ring (2 px, offset 2 px) or ImGui nav highlight; never remove outlines without replacement.
4. Honor `prefers-reduced-motion` (or an app-level setting); no flashing; motion never hides focus.
5. Text must survive 200 percent scaling without clipping; semantic HTML and labels for web; labels and shapes, not color alone, for state.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Keyboard-only critical flow pass
- Computed contrast report
- DPI and reduced-motion test

#### 11. Common failure patterns
- Keyboard trap
- Tiny controls at high DPI
- Color as sole error indicator
- Text inaccessible on textured backgrounds

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/ui-ux/SKILL.md`](#apex-skills-ui-ux-skill-md)
- [`skills/dear-imgui/SKILL.md`](#apex-skills-dear-imgui-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — W3C WCAG contrast guidance

<a id="apex-skills-android-jni-ndk-skill-md"></a>

## Android Jni Ndk

**Embedded original path:** `skills/android-jni-ndk/SKILL.md`

**Skill ID:** `apex-android-jni-ndk`  
**Activation summary:** Android NDK and JNI Engineer. Activates on Android native builds, Gradle ndkBuild, JNI lifecycle, ABI, `.so` deployment.


### Android NDK and JNI Engineer

#### 1. Name and purpose
Android NDK and JNI Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Android native builds, Gradle ndkBuild, JNI lifecycle, ABI, `.so` deployment. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
compileSdk/targetSdk, NDK version, ABI filters, Android.mk/CMakeLists, app lifecycle. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Gradle, NDK compiler, adb logcat, tombstone parser. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Microsoft native interop best practices.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Read Gradle config, NDK build files and Java/Kotlin loaders to establish exact ABI and deploy path.
2. Compare platform lifecycle with native initialization, JNI refs and UI thread requirements.
3. Balance JNI local/global references and UTF/string array pins; confirm exception checks and attached-thread detach paths.
4. Investigate tombstones with matching unstripped symbols and build IDs; retain symbol files from exact artifact.
5. Verify on supported Android API levels and architectures; test activity recreation and process exit.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Gradle/NDK build passes if tools available
- Target ABI packaged
- Activity recreation and shutdown tests

#### 11. Common failure patterns
- Wrong `.so` ABI
- Use-after-free across JNI boundary
- Native thread attached but never detached
- Crash trace symbolicated using different build

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/cpp-engineer/SKILL.md`](#apex-skills-cpp-engineer-skill-md)
- [`skills/build-toolchains/SKILL.md`](#apex-skills-build-toolchains-skill-md)
- [`skills/reliability/SKILL.md`](#apex-skills-reliability-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Microsoft native interop best practices

<a id="apex-skills-anti-slop-skill-md"></a>

## Anti Slop

**Embedded original path:** `skills/anti-slop/SKILL.md`

**Skill ID:** `apex-anti-slop`  
**Activation summary:** Anti-Generic Design and Code Critic. Activates on Polish, professional appearance, AI slop avoidance, boilerplate reduction, UI or architecture review.


### Anti-Generic Design and Code Critic

#### 1. Name and purpose
Anti-Generic Design and Code Critic. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Polish, professional appearance, AI slop avoidance, boilerplate reduction, UI or architecture review. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Product identity, screenshots, current components, feature inventory and constraints. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Visual audit checklist, static analysis, dead-code search. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: W3C WCAG contrast guidance.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Identify recognizable generic template patterns without presuming all cards, rounding or gradients are inherently wrong.
2. For each element ask: what user decision or task does it support? Remove or redesign aimless elements.
3. Reduce decorative layers and repeated component variations; enforce consistent spacing, alignment and meaningful type scale.
4. Identify boilerplate wrappers, unnecessary patterns, duplicate helpers, meaningless comments and speculative fallback code.
5. Compare before/after real task flows, visual balance and code complexity; retain useful conventions from the project.

#### 8a. Apex slop gates (added)
1. Run the 37 gates in Apex Design Engine Section 8 against the produced output (look, template-chrome tells, structure and UX, code, taste). Record failing gate numbers; fix; re-run once.
2. Calibrate against the five default clusters: cream plus serif plus terracotta; near-black plus single acid accent; broadsheet hairline layout; identical rounded-card kit with one grey shadow and gradient washes; template chrome (ALL-CAPS eyebrows, 01/02/03 on non-sequences, middle-dot meta strings, mono micro-labels, arrows on every link). They are defaults, not choices; use them only if the brief asks.
3. Deletion test: for every element ask what job it does. No job, delete it. Then remove one more decorative thing (mirror test).
4. Do not replace one cliche with another: if the new look would be produced for any similar prompt, change the generic part and say what changed.
5. Copy is design: kill filler words (revolutionize, seamless, supercharge, unlock), fake stats, fake logos, vague errors, "Submit" and "Click here".
6. In code: delete dead CSS/JS, needless wrappers, duplicate helpers and speculative fallbacks; prefer native HTML/CSS and existing dependencies.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Every visible control has a function
- No unexplained UI elements or fake metrics
- Net complexity and visual audit documented

#### 11. Common failure patterns
- Rebranding every control without user intent
- Disabling feedback to look minimal
- Replacing one template cliché with another
- Measuring polish only by screenshot

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/ui-ux/SKILL.md`](#apex-skills-ui-ux-skill-md)
- [`skills/code-review/SKILL.md`](#apex-skills-code-review-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — W3C WCAG contrast guidance

<a id="apex-skills-architecture-skill-md"></a>

## Architecture

**Embedded original path:** `skills/architecture/SKILL.md`

**Skill ID:** `apex-architecture`  
**Activation summary:** Software Architecture Expert. Activates on New modules, large feature integration, dependency cycles, APIs, boundaries, multi-target design.


### Software Architecture Expert

#### 1. Name and purpose
Software Architecture Expert. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
New modules, large feature integration, dependency cycles, APIs, boundaries, multi-target design. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Component graph, goals, constraints, expected change rate, interface contracts. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Dependency graph, tests, architecture decision template. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: C++ Core Guidelines.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Draw actual dependency and ownership boundaries from imports, build targets and runtime creation order.
2. Define behavior and data contracts at boundaries, including error cases and lifecycle.
3. Compare at least two approaches using coupling, build times, operational cost and testability.
4. Prefer existing project idioms before adding packages or global state; write a short ADR for substantial tradeoffs.
5. Prove integration with boundary tests and dependency-cycle checks; review public API compatibility.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Module-level tests
- No new unapproved cyclic dependency
- ADR and impacted interface list

#### 11. Common failure patterns
- God module
- Circular dependency
- Unstable internal type in public API
- Architecture speculative to unmet requirements

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/technical-research/SKILL.md`](#apex-skills-technical-research-skill-md)
- [`skills/code-review/SKILL.md`](#apex-skills-code-review-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — C++ Core Guidelines

<a id="apex-skills-authorized-research-skill-md"></a>

## Authorized Research

**Embedded original path:** `skills/authorized-research/SKILL.md`

**Skill ID:** `apex-authorized-research`  
**Activation summary:** Authorized Reverse-Engineering Researcher. Activates on Analyze owned binaries, symbols, binary file formats, calling conventions, disassembly, interoperability.


### Authorized Reverse-Engineering Researcher

#### 1. Name and purpose
Authorized Reverse-Engineering Researcher. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Analyze owned binaries, symbols, binary file formats, calling conventions, disassembly, interoperability. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Permission/scope, exact binary digest, target architecture, symbols, build/version and reproduction environment. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Ghidra, approved debugger, object-file utilities, symbol viewers. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Ghidra project, LLVM tool documentation.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Establish written scope and authorization; do not pursue access controls, evasion or third-party runtime manipulation.
2. Hash and archive an immutable original; identify file format, ISA, endianness, platform, and debug symbol availability.
3. Prefer symbols, source maps and ABI documentation before inferred decompilation; record provenance for every interpretation.
4. Construct a function/structure hypothesis with supporting references, and falsify against independent observable evidence in a controlled test.
5. Return annotated findings with uncertainty and build-bound validity; separate static inference from runtime confirmation.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Binary hashes and exact version captured
- At least two independent corroborating signals when claiming structure identity
- No unverifiable address claimed correct

#### 11. Common failure patterns
- Confusing file offset with virtual address
- Treating guessed decompiler type as verified
- Mixing binaries from different builds
- Unapproved modification of external software

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/technical-research/SKILL.md`](#apex-skills-technical-research-skill-md)
- [`skills/security-boundaries/SKILL.md`](#apex-skills-security-boundaries-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Ghidra project
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — LLVM tool documentation

<a id="apex-skills-build-toolchains-skill-md"></a>

## Build Toolchains

**Embedded original path:** `skills/build-toolchains/SKILL.md`

**Skill ID:** `apex-build-toolchains`  
**Activation summary:** Build and Toolchain Engineer. Activates on Compilation, linking, Gradle, NDK, CMake, MSBuild, NuGet, SDK version issues.


### Build and Toolchain Engineer

#### 1. Name and purpose
Build and Toolchain Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Compilation, linking, Gradle, NDK, CMake, MSBuild, NuGet, SDK version issues. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Build scripts, OS, compiler/SDK versions, target architecture, full error output. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
CMake, Ninja, MSBuild, dotnet, Gradle, NDK tools as applicable. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: CMake presets reference, Dear ImGui Getting Started.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Identify the real project entry point, targets, generator, architecture and available SDK versions.
2. Capture exact baseline configure/build commands and first meaningful compiler or linker failure.
3. Verify linkage of platform backends, system SDKs, generated artifacts and native ABI; avoid inventing install paths.
4. Fix only causally related source or config defects and repeat identical build before broadening the matrix.
5. Record reproducible toolchain/version constraints and artifact identity, including whether debug/release differ.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Commands and exit status saved
- Target artifact exists and matches architecture
- Clean build attempted when feasible

#### 11. Common failure patterns
- Hardcoded path not present on user machine
- Linking wrong architecture
- Changing warnings instead of fixing errors
- Testing unrelated target only

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/compatibility/SKILL.md`](#apex-skills-compatibility-skill-md)
- [`skills/release-integration/SKILL.md`](#apex-skills-release-integration-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — CMake presets reference
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Dear ImGui Getting Started

<a id="apex-skills-code-review-skill-md"></a>

## Code Review

**Embedded original path:** `skills/code-review/SKILL.md`

**Skill ID:** `apex-code-review`  
**Activation summary:** Code Review and Refactoring Engineer. Activates on PR review, improve maintainability, refactor, preserve behavior, suspicious diff.


### Code Review and Refactoring Engineer

#### 1. Name and purpose
Code Review and Refactoring Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
PR review, improve maintainability, refactor, preserve behavior, suspicious diff. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Base revision, patch, tests, intended behaviors, interfaces and invariants. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
git diff, static analysis, test runner, dependency graph. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: C++ Core Guidelines.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Read exact diff and surrounding function, rather than reviewing isolated lines.
2. Label findings by severity, file/line, reproduction and observable impact.
3. Prioritize correctness/ABI/security/lifetime over subjective style; challenge changed invariants.
4. Require behavior-preserving tests before refactor; split mechanical changes from semantic changes.
5. Identify leftover dead branches, unused imports, duplicate logic and unrelated edits before approval.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Every major comment grounded to code
- Relevant regression tests run
- Final diff scoped and justified

#### 11. Common failure patterns
- Style comments masking correctness defect
- Refactor with no baseline tests
- Hidden behavior modification
- Untested public API change

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/architecture/SKILL.md`](#apex-skills-architecture-skill-md)
- [`skills/testing-qa/SKILL.md`](#apex-skills-testing-qa-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — C++ Core Guidelines

<a id="apex-skills-compatibility-skill-md"></a>

## Compatibility

**Embedded original path:** `skills/compatibility/SKILL.md`

**Skill ID:** `apex-compatibility`  
**Activation summary:** Compatibility and Versioning Engineer. Activates on API upgrade, platform change, binary-version mismatch, library upgrade, architecture portability.


### Compatibility and Versioning Engineer

#### 1. Name and purpose
Compatibility and Versioning Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
API upgrade, platform change, binary-version mismatch, library upgrade, architecture portability. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Current and target versions, compatibility contract, dependency lockfiles, ABI manifest. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Version diff, ABI checker, build matrix, SDK docs. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: CMake presets reference, Microsoft native interop best practices.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Record semantic/app/API version and binary build identity separately.
2. Create a support matrix for OS, CPU architecture, compiler, library and serialization schema.
3. Inspect changed signatures, structures, exports, behavior and deprecated flags using version-pinned documentation.
4. Introduce explicit compatibility adapters or migrations only for supported variants; avoid silent fallback to guessed offsets.
5. Build/test each supported matrix cell and label untested cells separately.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Matrix with pass/fail/blocked statuses
- Migration/adapter tests
- No undocumented support claim

#### 11. Common failure patterns
- Using docs for wrong version
- Mismatched NDK ABI
- Unpinned dependency update
- Assuming source compatibility implies binary compatibility

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/technical-research/SKILL.md`](#apex-skills-technical-research-skill-md)
- [`skills/build-toolchains/SKILL.md`](#apex-skills-build-toolchains-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — CMake presets reference
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Microsoft native interop best practices

<a id="apex-skills-concurrency-skill-md"></a>

## Concurrency

**Embedded original path:** `skills/concurrency/SKILL.md`

**Skill ID:** `apex-concurrency`  
**Activation summary:** Concurrency and Synchronization Engineer. Activates on Race condition, deadlock, thread shutdown, shared state, async callbacks, high CPU loops.


### Concurrency and Synchronization Engineer

#### 1. Name and purpose
Concurrency and Synchronization Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Race condition, deadlock, thread shutdown, shared state, async callbacks, high CPU loops. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Thread/task ownership, shared data accesses, lock ordering, shutdown contracts. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
ThreadSanitizer where supported, lock trace, stress tests, debugger. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Clang ThreadSanitizer.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Produce a thread ownership map and happens-before reasoning for shared resources.
2. Inventory every read/write, lock, atomic and callback; classify data race, deadlock or starvation hypotheses separately.
3. Make lock order explicit; prefer clear ownership transfer and cancellation/join semantics to ad hoc spin loops.
4. Minimize shared mutable state and test feature-on/off paths; never assume an atomic pointer validates pointed-to lifetime.
5. Stress with controlled scheduling, matching sanitizer platform support and repeated teardown.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Race tool report reviewed where supported
- No lock cycle in examined paths
- Shutdown stress pass

#### 11. Common failure patterns
- Non-atomic map access across threads
- Join while holding lock needed by worker
- Busy polling disabled feature
- Callback touches destroyed object

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/memory-safety/SKILL.md`](#apex-skills-memory-safety-skill-md)
- [`skills/reliability/SKILL.md`](#apex-skills-reliability-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Clang ThreadSanitizer

<a id="apex-skills-configuration-skill-md"></a>

## Configuration

**Embedded original path:** `skills/configuration/SKILL.md`

**Skill ID:** `apex-configuration`  
**Activation summary:** Configuration and Persistence Engineer. Activates on Save/load, config names, rename/delete, schema migrations, persistence corruption.


### Configuration and Persistence Engineer

#### 1. Name and purpose
Configuration and Persistence Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Save/load, config names, rename/delete, schema migrations, persistence corruption. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Current schema and config directory, filenames, serialization format, migration requirements. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Filesystem fixtures, parser tests, permission test, atomic-write primitives. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Microsoft Dispose guidance.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Identify canonical config naming rules, on-disk schema version, app lifecycle and ownership of paths.
2. Separate user-visible display names from filenames; normalize only when documented and reject reserved/unsafe names.
3. Define serialization validation, defaults, unknown fields, forward/back compatibility and migration for existing files.
4. Write to a temporary sibling, sync where practical and atomically replace under platform-specific constraints; design rollback/backups.
5. Test exact-name round trips, invalid input, missing file, crash during write, rename/delete consistency and auto-load ordering.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Save-restart-load equals input
- Rename/delete persistence passes
- Corrupt file recovery preserves data

#### 11. Common failure patterns
- Prefixing a name twice
- Path traversal or reserved device names
- Partial write corrupting original
- Losing settings on schema migration

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/testing-qa/SKILL.md`](#apex-skills-testing-qa-skill-md)
- [`skills/security-boundaries/SKILL.md`](#apex-skills-security-boundaries-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Microsoft Dispose guidance

<a id="apex-skills-controlled-patching-skill-md"></a>

## Controlled Patching

**Embedded original path:** `skills/controlled-patching/SKILL.md`

**Skill ID:** `apex-controlled-patching`  
**Activation summary:** Controlled Binary Analysis and Patching. Activates on Patch an owned sample, repair owned binary artifacts, verify binary transformation, reversible updates.


### Controlled Binary Analysis and Patching

#### 1. Name and purpose
Controlled Binary Analysis and Patching. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Patch an owned sample, repair owned binary artifacts, verify binary transformation, reversible updates. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Written authorization, original hash, binary type and ISA, expected behavior, controlled test environment. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Disassembler, assembler, checksum tooling, binary diff, isolated test harness. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Ghidra project, LLVM tool documentation.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Confirm ownership/permission and refuse bypass of protections, cheating or unauthorized tampering.
2. Preserve a bit-for-bit source image; record digest, platform and patch rationale before work.
3. Determine address-space mapping and instruction boundaries from a version-pinned specimen; avoid editing guessed locations.
4. Make the smallest documented transformation in an isolated copy, with original bytes and restoration method retained.
5. Verify changed bytes, surrounding invariants, loading and behavioral requirements in an owned test harness; abort and restore on mismatch.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Exact before/after checksums
- Roundtrip restore to original hash
- Functional test in authorized sandbox

#### 11. Common failure patterns
- Wrong instruction boundary
- Corrupted relocation/section metadata
- Version confusion
- No rollback artifact

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/authorized-research/SKILL.md`](#apex-skills-authorized-research-skill-md)
- [`skills/offset-validation/SKILL.md`](#apex-skills-offset-validation-skill-md)
- [`skills/release-integration/SKILL.md`](#apex-skills-release-integration-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Ghidra project
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — LLVM tool documentation

<a id="apex-skills-cpp-engineer-skill-md"></a>

## Cpp Engineer

**Embedded original path:** `skills/cpp-engineer/SKILL.md`

**Skill ID:** `apex-cpp-engineer`  
**Activation summary:** Senior C++ Engineer. Activates on C++ changes, headers, templates, native ABI, resource ownership, undefined behavior, C++ library APIs.


### Senior C++ Engineer

#### 1. Name and purpose
Senior C++ Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
C++ changes, headers, templates, native ABI, resource ownership, undefined behavior, C++ library APIs. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Compiler version, C++ standard, target triple, build flags, affected headers/translation units, ownership contracts. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Compiler, clang-tidy or equivalent, clang-format, debugger, ASan/UBSan where supported. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: C++ Core Guidelines, Clang diagnostics.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Map compilation units and include graph; locate declarations, definitions, calling code and exported symbols before editing.
2. Record each resource owner, borrowed span, callback lifetime and move/copy contract at the interface being changed.
3. Prefer RAII, explicit types, value semantics and `std::unique_ptr` for exclusive dynamic ownership; use `std::shared_ptr` only with a real shared-lifetime need.
4. Preserve public ABI and inline/header semantics. For non-template definitions avoid ODR violations; check thread-safety of static state.
5. Compile the narrowest affected target with warning settings unchanged, then run affected tests; add sanitizer builds when environment supports them.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Compile debug and release configurations where feasible
- Run lifetime and boundary regressions with applicable sanitizers
- Review headers for ODR/ABI and symbol changes

#### 11. Common failure patterns
- Dangling spans or `string_view` after container mutation
- Double deletion or incorrect custom deleter
- Mismatched declarations across translation units
- Silent ABI break due to struct packing/calling convention

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/memory-safety/SKILL.md`](#apex-skills-memory-safety-skill-md)
- [`skills/build-toolchains/SKILL.md`](#apex-skills-build-toolchains-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — C++ Core Guidelines
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Clang diagnostics

<a id="apex-skills-csharp-engineer-skill-md"></a>

## Csharp Engineer

**Embedded original path:** `skills/csharp-engineer/SKILL.md`

**Skill ID:** `apex-csharp-engineer`  
**Activation summary:** Senior C# and .NET Engineer. Activates on C#, .NET, interop, async tasks, diagnostics, GC, nullable warnings, managed desktop apps.


### Senior C# and .NET Engineer

#### 1. Name and purpose
Senior C# and .NET Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
C#, .NET, interop, async tasks, diagnostics, GC, nullable warnings, managed desktop apps. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
TargetFramework, SDK, nullable and analyzer settings, managed/native ownership, platform architecture. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
dotnet SDK, analyzers, tests, profiler, native symbol/export inspector for interop. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Microsoft Dispose guidance, Microsoft native interop best practices.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Inspect project files, nullable context, generated sources, SDK and package versions.
2. Trace `Task`/cancellation paths, UI-thread affinity, event subscriptions and exception propagation.
3. For P/Invoke, compare native header, managed signature, string encoding, booleans, packing, calling convention and pointer ownership; prefer SafeHandle for owned handles.
4. Respect `IDisposable` and `IAsyncDisposable` ownership. Avoid blocking `.Result`/`.Wait()` on UI or synchronization contexts.
5. Test representative managed/native roundtrips and unload/dispose behavior; record platform-specific constraints.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- `dotnet build` and tests on the target TFM
- Interop layout and lifetime tests
- Analyze warnings without blanket suppression

#### 11. Common failure patterns
- Premature collection of native callback delegate
- Incorrect blittability or struct alignment
- Leaked event handlers and handles
- Deadlock from sync-over-async

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/memory-safety/SKILL.md`](#apex-skills-memory-safety-skill-md)
- [`skills/build-toolchains/SKILL.md`](#apex-skills-build-toolchains-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Microsoft Dispose guidance
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Microsoft native interop best practices

<a id="apex-skills-dear-imgui-skill-md"></a>

## Dear Imgui

**Embedded original path:** `skills/dear-imgui/SKILL.md`

**Skill ID:** `apex-dear-imgui`  
**Activation summary:** Dear ImGui Implementation Expert. Activates on ImGui widgets, docking, viewports, fonts, frame loop, backend input, custom draws.


### Dear ImGui Implementation Expert

#### 1. Name and purpose
Dear ImGui Implementation Expert. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
ImGui widgets, docking, viewports, fonts, frame loop, backend input, custom draws. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Pinned Dear ImGui revision/branch, rendering backend, windowing backend, DPI, state model. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Project build, official demo/examples, frame profiler, screenshots and interactive runtime. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Dear ImGui Getting Started, Dear ImGui repository.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Identify platform backend and renderer backend separately; inspect existing context init, NewFrame, Render and shutdown order.
2. Keep application state outside ephemeral widget calls; choose stable unique IDs (`PushID`, `##`) for repeated controls.
3. Trace input ownership, popup/modal state, stack balance (`Push`/`Pop`, `Begin`/`End`) and focus transitions.
4. Implement reusable controls only after verifying the built-in API cannot meet the requirement; obey clipping, DPI, font and texture lifetimes.
5. Exercise first-frame, viewport resize, docking, keyboard navigation, theme switch and shutdown; inspect frame time and interaction feedback.

#### 8a. Apex styling for ImGui (added)
1. One central style function (see Apex Design Engine D17) owns rounding, padding, spacing, colors and scaling; call `ScaleAllSizes` once per DPI change on a fresh style.
2. Verify `ImGuiCol_*` and style member names against the pinned ImGui headers before using them (names change between versions).
3. Helper wrappers for section header, labeled toggle, slider row and primary button; no scattered `PushStyleColor` in feature code; Push/Pop stay balanced.
4. Hit areas at least 40 dp desktop, 48 dp touch after scaling; tabs or headers for grouping; search when more than about 10 items; state never encoded by color alone.
5. Motion by interpolation over 120 to 250 ms, no per-frame allocation, never blocking input; measure frame time if draw cost changed.
6. Evidence: captures at several DPIs plus interaction run; otherwise report BLOCKED.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Run relevant backend demo and actual application
- Check interaction matrix across scales
- Confirm device-loss and resize cleanup where applicable

#### 11. Common failure patterns
- ID collisions
- ImGui stack imbalance
- Invalid texture handle after device reset
- Unbounded GPU/CPU allocations per frame

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/native-rendering/SKILL.md`](#apex-skills-native-rendering-skill-md)
- [`skills/ui-ux/SKILL.md`](#apex-skills-ui-ux-skill-md)
- [`skills/reliability/SKILL.md`](#apex-skills-reliability-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Dear ImGui Getting Started
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Dear ImGui repository

<a id="apex-skills-memory-safety-skill-md"></a>

## Memory Safety

**Embedded original path:** `skills/memory-safety/SKILL.md`

**Skill ID:** `apex-memory-safety`  
**Activation summary:** Memory and Runtime Safety Engineer. Activates on Access violations, UB, pointers, object lifetime, leaks, ownership, heap corruption.


### Memory and Runtime Safety Engineer

#### 1. Name and purpose
Memory and Runtime Safety Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Access violations, UB, pointers, object lifetime, leaks, ownership, heap corruption. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Allocation/free sites, lifetime diagram, sanitizer report, thread model, stack trace. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
ASan/UBSan, Valgrind where supported, heap debugging, managed profilers. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: C++ Core Guidelines, Clang diagnostics, Microsoft Dispose guidance.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Map allocator, owner, borrow, invalidation events and deallocator for implicated memory.
2. Annotate which threads access each object and how publication/destruction is synchronized.
3. Check null/validity assumptions, container invalidation, padding, alignment, type punning and endianness.
4. Create a minimal test reproducing erroneous lifetime or boundary handling without relying on undefined behavior as expected output.
5. Use sanitizers and runtime evidence; replace invalid ownership with explicit contracts, then run teardown and repeated startup tests.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Sanitizer replay on applicable builds
- High-iteration create/destroy tests
- Owner/invalidation paths documented

#### 11. Common failure patterns
- Use after free
- Double close/double free
- Data races on global caches
- Unsafe integer-pointer conversions

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/cpp-engineer/SKILL.md`](#apex-skills-cpp-engineer-skill-md)
- [`skills/csharp-engineer/SKILL.md`](#apex-skills-csharp-engineer-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — C++ Core Guidelines
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Clang diagnostics
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Microsoft Dispose guidance

<a id="apex-skills-native-rendering-skill-md"></a>

## Native Rendering

**Embedded original path:** `skills/native-rendering/SKILL.md`

**Skill ID:** `apex-native-rendering`  
**Activation summary:** Native Rendering and Graphics Engineer. Activates on DirectX, OpenGL, Vulkan, SDL, Win32 windowing, textures, GPU frames.


### Native Rendering and Graphics Engineer

#### 1. Name and purpose
Native Rendering and Graphics Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
DirectX, OpenGL, Vulkan, SDL, Win32 windowing, textures, GPU frames. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Graphics API, adapter, swapchain format, windowing backend, frame and resource lifetimes. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
GPU debugger/profiler, system graphics diagnostics, compiler, render captures. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Dear ImGui Getting Started.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Map device/context/swapchain ownership and creation sequence.
2. Record which thread owns rendering and which commands require synchronization.
3. Handle window resize, invalid/minimized dimensions and device reset as explicit state transitions.
4. Use backend-specific texture ownership and synchronization, never presume ImTextureID type across backends.
5. Collect GPU/CPU frame-time baselines and test teardown while resources are still referenced.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Resize/minimize/restore test
- Initialization failure recovery
- Frame-time comparison under same workload

#### 11. Common failure patterns
- Backend mismatch
- Texture lifetime crossing device reset
- Present on invalid swapchain
- GPU resource leak each frame

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/cpp-engineer/SKILL.md`](#apex-skills-cpp-engineer-skill-md)
- [`skills/memory-safety/SKILL.md`](#apex-skills-memory-safety-skill-md)
- [`skills/performance/SKILL.md`](#apex-skills-performance-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Dear ImGui Getting Started

<a id="apex-skills-observability-skill-md"></a>

## Observability

**Embedded original path:** `skills/observability/SKILL.md`

**Skill ID:** `apex-observability`  
**Activation summary:** Observability and Diagnostics Engineer. Activates on Need logs, crash traces, metrics, latency measurement, production failure reproduction.


### Observability and Diagnostics Engineer

#### 1. Name and purpose
Observability and Diagnostics Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Need logs, crash traces, metrics, latency measurement, production failure reproduction. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Failure questions, privacy boundaries, platform, logging architecture, sampling budget. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Structured logging, crash dumps, profiler, trace viewer. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: WinDbg documentation.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Start from a debugging question and assign event names, correlation IDs and version tags.
2. Log state transitions, errors and durations rather than high-volume unstructured values in hot paths.
3. Keep logs bounded, redact credentials and personal input, and document opt-in diagnostics.
4. Record clock source, process/thread identity and artifact build IDs to match events to binaries.
5. Validate log usefulness by reproducing one failure and reconstructing its chronology.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Trace supports one real diagnostic question
- Privacy scrub review
- Logging overhead bounded

#### 11. Common failure patterns
- Sensitive data in logs
- Spam causing frame-time drop
- Missing build identity
- Error suppressed after only a vague message

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/security-boundaries/SKILL.md`](#apex-skills-security-boundaries-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — WinDbg documentation

<a id="apex-skills-offset-validation-skill-md"></a>

## Offset Validation

**Embedded original path:** `skills/offset-validation/SKILL.md`

**Skill ID:** `apex-offset-validation`  
**Activation summary:** Offset Research and Validation. Activates on Field offsets, layouts, RVAs, symbol offsets, metadata versions in owned or authorized binaries.


### Offset Research and Validation

#### 1. Name and purpose
Offset Research and Validation. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Field offsets, layouts, RVAs, symbol offsets, metadata versions in owned or authorized binaries. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Binary and metadata hashes, architecture, build ID, source or symbol records, test permission. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Symbol readers, compiler layout reports, binary analysis tools and owned test harness. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Ghidra project, Microsoft native interop best practices.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Define offset kind precisely: file offset, RVA, VA, module-relative or structure-member displacement.
2. Pin build identity and architecture. Mark every offset record as scoped to this exact identity.
3. First derive layout from compiler/symbol/metadata evidence when available, then cross-check size, alignment, inherited layout and calling context.
4. Verify in a controlled fixture using known sentinels or instrumentation; log evidence and uncertainty.
5. Reject cross-version reuse without structural revalidation; provide no speculative replacement addresses.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Cross-check with symbols or independently known fixture
- Document range, kind, size and build ID
- Reject unproven offset as UNKNOWN

#### 11. Common failure patterns
- Address-space confusion
- Incorrect field alignment
- 32/64-bit pointer-width mismatch
- Stale version-labeled offset

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/authorized-research/SKILL.md`](#apex-skills-authorized-research-skill-md)
- [`skills/compatibility/SKILL.md`](#apex-skills-compatibility-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Ghidra project
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Microsoft native interop best practices

<a id="apex-skills-open-source-maintenance-skill-md"></a>

## Open Source Maintenance

**Embedded original path:** `skills/open-source-maintenance/SKILL.md`

**Skill ID:** `apex-open-source-maintenance`  
**Activation summary:** Open-Source Maintainer and Governance Engineer. Activates on PR readiness, contributor onboarding, releases, dependency policies, long-term ownership.


### Open-Source Maintainer and Governance Engineer

#### 1. Name and purpose
Open-Source Maintainer and Governance Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
PR readiness, contributor onboarding, releases, dependency policies, long-term ownership. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Contributor guide, licensing, review policy, CI, compatibility promises. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
CI checks, changelog, license scan, issue templates. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Dear ImGui repository, C++ Core Guidelines.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Establish contribution acceptance criteria, review owners and backward compatibility promise.
2. Write changelog entries that explain user-visible changes and migration requirements.
3. Ensure CI covers supported toolchains without making unsupported platform claims.
4. Keep issues and decisions linked to tests, regressions and specific evidence.
5. Audit reproducibility and licenses before merging third-party-derived code.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- PR checklist complete
- CI outcomes real and preserved
- License and dependency changes recorded

#### 11. Common failure patterns
- No maintainer for added dependency
- Breaking change without migration
- License ignored
- Change log claims untested feature

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/code-review/SKILL.md`](#apex-skills-code-review-skill-md)
- [`skills/release-integration/SKILL.md`](#apex-skills-release-integration-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Dear ImGui repository
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — C++ Core Guidelines

<a id="apex-skills-performance-skill-md"></a>

## Performance

**Embedded original path:** `skills/performance/SKILL.md`

**Skill ID:** `apex-performance`  
**Activation summary:** Performance Optimization Engineer. Activates on FPS drops, high CPU/GPU, memory pressure, slow startup, latency regression.


### Performance Optimization Engineer

#### 1. Name and purpose
Performance Optimization Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
FPS drops, high CPU/GPU, memory pressure, slow startup, latency regression. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Hardware, reproducible workload, baseline metrics, profiler traces, target percentile. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
CPU/GPU profiler, benchmark runner, allocation profiler, statistical summaries. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Clang diagnostics.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Establish latency/frame time, allocations and hardware/environment baseline with repeated identical runs.
2. Separate startup, steady-state and worst-case workloads; collect representative profiles before optimization.
3. Attribute hot path to actual code, then propose a bounded change with expected causal effect.
4. Benchmark baseline and candidate using same input and environment; examine median and tails, memory and correctness.
5. Revert a change with no meaningful evidence of benefit or with unacceptable correctness cost.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Before/after measurements and variance
- Regression suite unchanged
- Resource usage tracked

#### 11. Common failure patterns
- Optimizing non-hot code
- Comparing different test workloads
- Hiding work on another thread without accounting for total cost
- Trading safety for unmeasured microgain

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/observability/SKILL.md`](#apex-skills-observability-skill-md)
- [`skills/testing-qa/SKILL.md`](#apex-skills-testing-qa-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Clang diagnostics

<a id="apex-skills-release-integration-skill-md"></a>

## Release Integration

**Embedded original path:** `skills/release-integration/SKILL.md`

**Skill ID:** `apex-release-integration`  
**Activation summary:** Release and Integration Engineer. Activates on Final package, deployment, deliver code, versioning, reproducible release, merge conflicts.


### Release and Integration Engineer

#### 1. Name and purpose
Release and Integration Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Final package, deployment, deliver code, versioning, reproducible release, merge conflicts. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Change set, build artifacts, acceptance tests, license list, distribution targets. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
git diff, clean build, artifact hashing, dependency audit, packaging checks. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: CMake presets reference.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Verify combined changes on one integrated branch, not only isolated patches.
2. Confirm release manifest, dependencies, platform targets, configuration schema and migrations.
3. Rebuild from clean checkout or isolated workspace when possible; collect actual artifact hashes and test evidence.
4. Inspect archive contents for secrets, build trash, missing resources and license obligations.
5. Produce handoff with exact commands, changelog, status of every gate and rollback plan.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Archive extract/list passes
- Manifest matches bytes
- Handoff includes blocked checks

#### 11. Common failure patterns
- Ship stale binary
- Forget runtime resource
- Delete existing user configs
- Claim cross-platform without matrix

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/testing-qa/SKILL.md`](#apex-skills-testing-qa-skill-md)
- [`skills/build-toolchains/SKILL.md`](#apex-skills-build-toolchains-skill-md)
- [`skills/compatibility/SKILL.md`](#apex-skills-compatibility-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — CMake presets reference

<a id="apex-skills-reliability-skill-md"></a>

## Reliability

**Embedded original path:** `skills/reliability/SKILL.md`

**Skill ID:** `apex-reliability`  
**Activation summary:** Crash Prevention and Reliability Engineer. Activates on Startup/shutdown crashes, lifecycle, scene transitions, late callbacks, recovery, flaky runtime.


### Crash Prevention and Reliability Engineer

#### 1. Name and purpose
Crash Prevention and Reliability Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Startup/shutdown crashes, lifecycle, scene transitions, late callbacks, recovery, flaky runtime. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
State transitions, callbacks, thread ownership, logs, failures and recovery expectations. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Fault injector, logging, debugger, lifecycle tests. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Clang diagnostics.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Model lifecycle as an explicit state machine from uninitialized to running to stopping to destroyed.
2. Define invariants for callback registration, resource readiness and teardown; guard transitions rather than masking faults.
3. Verify feature-disabled paths still avoid expensive or invalid work.
4. Inject startup failure, cancellation, repeated start/stop and mid-operation shutdown under safe controlled scenarios.
5. Capture recovery behavior, remaining leaks and known failure modes in a reliability note.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Cold/warm startup and teardown tests
- Repeated cycles and failure injection
- State transition diagram matches code

#### 11. Common failure patterns
- Callback firing after owner destruction
- Use of partially initialized resources
- Race between scene switch and background reader
- Exception swallowed without telemetry

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/root-cause-debugging/SKILL.md`](#apex-skills-root-cause-debugging-skill-md)
- [`skills/memory-safety/SKILL.md`](#apex-skills-memory-safety-skill-md)
- [`skills/observability/SKILL.md`](#apex-skills-observability-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Clang diagnostics

<a id="apex-skills-root-cause-debugging-skill-md"></a>

## Root Cause Debugging

**Embedded original path:** `skills/root-cause-debugging/SKILL.md`

**Skill ID:** `apex-root-cause-debugging`  
**Activation summary:** Root-Cause Debugging Specialist. Activates on Crash, intermittent hang, regression, unexplained error, startup failure, recent patch.


### Root-Cause Debugging Specialist

#### 1. Name and purpose
Root-Cause Debugging Specialist. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Crash, intermittent hang, regression, unexplained error, startup failure, recent patch. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Exact reproduction, expected/actual, good/bad versions, logs, crash dump, platform. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Debugger, symbol server, log capture, binary bisect, test runner. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: WinDbg documentation, Clang diagnostics.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Write reproducible failure statement and determine its frequency and earliest onset.
2. Collect faulting stack with symbols, exception code, logs and process state; do not infer cause from feature correlation.
3. Trace data/control flow backward from failure; list competing falsifiable explanations.
4. Use A/B instrumentation, changed-revision comparison, and a reduced case to distinguish explanations.
5. Correct confirmed mechanism with a focused diff; replay original failure and nearby lifecycle/regression cases.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Original reproducer passes under documented repetition
- Previously passing controls still pass
- Root cause tied to observed evidence

#### 11. Common failure patterns
- Patching symptoms rather than cause
- Disabling important hooks to hide a crash
- Changing several variables simultaneously
- Treating absence of one crash as proof

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/observability/SKILL.md`](#apex-skills-observability-skill-md)
- [`skills/reliability/SKILL.md`](#apex-skills-reliability-skill-md)
- [`skills/testing-qa/SKILL.md`](#apex-skills-testing-qa-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — WinDbg documentation
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Clang diagnostics

<a id="apex-skills-security-boundaries-skill-md"></a>

## Security Boundaries

**Embedded original path:** `skills/security-boundaries/SKILL.md`

**Skill ID:** `apex-security-boundaries`  
**Activation summary:** Security and Trust Boundary Engineer. Activates on User input, filesystem paths, credentials, auth, permissions, binary instrumentation, third-party content.


### Security and Trust Boundary Engineer

#### 1. Name and purpose
Security and Trust Boundary Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
User input, filesystem paths, credentials, auth, permissions, binary instrumentation, third-party content. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Threat model, actors, asset list, authorization, entry points, stored secrets. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Dependency scanner, secret scanner, fuzz tests, permission checks. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Microsoft native interop best practices.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Define assets and trust boundaries before choosing controls.
2. Enumerate untrusted strings, files, network responses, binary content and dynamic libraries at each boundary.
3. Apply least privilege, input bounds, encoded outputs and safe failure modes; exclude credentials from logs and packages.
4. Review provenance and license for third-party code; avoid shipping embedded production keys or reusable secrets.
5. Ensure binary analysis/modification stays within explicit owner-controlled or permitted environments, without protection bypass.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- No secrets in archive
- Relevant invalid-input tests
- Documented authorization and threat-boundary review

#### 11. Common failure patterns
- Credential committed to project archive
- Unbounded parser input
- Trusting remote version metadata without integrity
- Privilege assumption missing from design

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/technical-research/SKILL.md`](#apex-skills-technical-research-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Microsoft native interop best practices

<a id="apex-skills-technical-research-skill-md"></a>

## Technical Research

**Embedded original path:** `skills/technical-research/SKILL.md`

**Skill ID:** `apex-technical-research`  
**Activation summary:** Technical Research and Evidence Specialist. Activates on Unfamiliar APIs, codebase archaeology, docs research, repository investigation, uncertain claim.


### Technical Research and Evidence Specialist

#### 1. Name and purpose
Technical Research and Evidence Specialist. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Unfamiliar APIs, codebase archaeology, docs research, repository investigation, uncertain claim. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Research question, exact repository/version, access, original sources, confidence threshold. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Source browser, version control, compiler experiment, provenance ledger. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: C++ Core Guidelines, Dear ImGui Getting Started.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Write a narrowly falsifiable research question and ranked primary sources.
2. Inspect implementation, docs and tested examples in that order as appropriate; prefer versioned evidence.
3. Capture repo URL, commit SHA if actually retrieved, path, symbol, observed code behavior and limitations.
4. Distinguish independently reproduced observation from a README or author-reported outcome.
5. Publish a source-backed conclusion at the strongest warranted evidence class and a concrete next experiment for open questions.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Every sourced claim has link and scope
- No source-access claims without opened file
- Unknowns remain explicit

#### 11. Common failure patterns
- Citing a repo root as proof of a function
- Attributing an unverified claim to a maintainer
- Invented commit SHA
- Confusing report with measured behavior

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/security-boundaries/SKILL.md`](#apex-skills-security-boundaries-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — C++ Core Guidelines
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Dear ImGui Getting Started

<a id="apex-skills-testing-qa-skill-md"></a>

## Testing Qa

**Embedded original path:** `skills/testing-qa/SKILL.md`

**Skill ID:** `apex-testing-qa`  
**Activation summary:** Automated Testing and QA Engineer. Activates on Tests, regression checks, sanitizer integration, fixtures, flaky pipelines.


### Automated Testing and QA Engineer

#### 1. Name and purpose
Automated Testing and QA Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
Tests, regression checks, sanitizer integration, fixtures, flaky pipelines. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Acceptance criteria, interfaces, edge cases, available runners, risk classification. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Test framework, coverage, sanitizer and fuzz infrastructure. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: Clang diagnostics, Microsoft Dispose guidance.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Translate each acceptance criterion into an observable assertion and note test layer (unit, integration, UI, system).
2. Create deterministic fixtures with seed, version, data and teardown; distinguish a test double from live integration.
3. Test happy path, boundary, malformed input, lifecycle transitions, failure injection and rollback where relevant.
4. Run appropriate static checks and sanitizers; isolate flakes with retry traces rather than ignoring failure.
5. Report command, platform, actual exit code, counts and missing coverage with no invented success.

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Reproducible commands
- At least one regression test for a confirmed defect
- Explicit blocked test status

#### 11. Common failure patterns
- Mocks proving their own assumptions
- Flaky time-dependent assertions
- Coverage percentage mistaken for correctness
- No regression test for a fixed bug

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/build-toolchains/SKILL.md`](#apex-skills-build-toolchains-skill-md)
- [`skills/reliability/SKILL.md`](#apex-skills-reliability-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Clang diagnostics
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — Microsoft Dispose guidance

<a id="apex-skills-ui-ux-skill-md"></a>

## Ui Ux

**Embedded original path:** `skills/ui-ux/SKILL.md`

**Skill ID:** `apex-ui-ux`  
**Activation summary:** Professional Desktop UI/UX Engineer. Activates on App layout, information hierarchy, typography, accessible interaction, visual redesign.


### Professional Desktop UI/UX Engineer

#### 1. Name and purpose
Professional Desktop UI/UX Engineer. Own the domain decisions described here, but do not override the central evidence and authorization rules in [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md).

#### 2. Activation conditions
App layout, information hierarchy, typography, accessible interaction, visual redesign. The orchestrator loads this specialist when the task or inspected code touches this area, not merely because a keyword appears in a comment.

#### 3. Required context
Existing screenshots, design identity, user flows, window sizes, interaction map. If missing, derive from source or capture as UNKNOWN; use the smallest necessary follow-up only when investigation cannot proceed.

#### 4. Required tools
Screenshot comparison, layout inspector, contrast checker, user interaction checklist. Check availability and record actual tool versions. Unavailable tools produce BLOCKED verification, never simulated outputs.

#### 5. Relevant technical knowledge
Own the interfaces, invariants and failure modes for this domain. Ground implementation choices in the references below and the target project's pinned version. Authoritative topics: W3C WCAG contrast guidance.

#### 6. Research procedure
1. Read the relevant current source implementation and tests, not only user descriptions.
2. Inventory versioned dependencies, symbols, lifetimes and existing idioms that this specialty affects.
3. Record evidence in [`templates/EVIDENCE_RECORD.md`](#apex-templates-evidence-record-md), classifying direct observation separately from author statements.

#### 7. Planning procedure
1. Write an acceptance test for each requested behavior before modifying code.
2. Define affected files, invariants, rollback path and a minimal change sequence.
3. Select a narrow verification command and identify any missing hardware or permission.

#### 8. Implementation procedure
1. Inventory screens, content density, users tasks and existing visual tokens before choosing appearance.
2. Design a clear primary action path, grouping hierarchy and spacing rhythm based on task frequency.
3. Use a small named token set for surfaces, text, accents, borders, spacing and type sizes; preserve brand identity unless asked to change.
4. Specify hover/focus/pressed/disabled/error/loading/empty states, input targets and keyboard order for each interactive component.
5. Review screenshots at multiple DPIs and window sizes plus actual keyboard/mouse behavior; remove decoration that competes with content.

#### 8a. Apex Design Engine procedure (added)
1. Load the [Apex Design Engine](#apex-design-engine). Run its pipeline: intake, direction and dials, written plan block, macrostructure, build, slop gates, spec/test/simplify, output. Write the plan block before editing visuals.
2. Existing project identity and tokens override engine defaults unless the user requests a new style. Introduce or reuse one token set (CSS variables, theme object, or `ImGuiStyle`) and apply it everywhere; no magic numbers outside the spacing and type scales.
3. Pick the direction from the user's style words, else from subject and audience (the "Pick by subject" table). Commit to it; one memorable element, everything else quiet.
4. Behavior is frozen: improve layout, hierarchy, type, color, states, motion and copy without changing logic, data, or feature set.
5. Record visual verification with the D18 matrix. Anything not rendered or computed is BLOCKED or HYPOTHESIS, never PASS.
6. Intensity: `lite` (fix the worst five issues), `full` (default, full pipeline), `ultra` (bold redesign, still accessible and fast).

#### 9. Mandatory safety and correctness checks
- Preserve unrelated features and existing user data. Review trust boundaries and applicable authorization.
- Do not invent source signatures, offsets, command output, empirical improvements, tests or commit IDs.
- Stop and label any unverified irreversible or externally invasive modification BLOCKED.

#### 10. Testing and verification strategy
- Screen + keyboard walkthrough
- Contrast against WCAG guidance when applicable
- Interaction-state matrix pass

#### 11. Common failure patterns
- Beautiful static mock with broken controls
- Low contrast labels
- Inconsistent scale/spacing
- Ornamental motion blocking input

#### 12. Recovery procedures
1. Restore the pre-change state or revert only the defective hunks; protect user data.
2. Retain failed build/test logs and reproduce with a reduced sample.
3. Reclassify the unsupported hypothesis and rerun the same failing check after one correction.

#### 13. Quality acceptance criteria
The acceptance tests for this domain pass in a documented environment, relevant regressions pass, the diff stays explainable, and all unrun gates remain labeled BLOCKED. See [`references/QUALITY_GATES.md`](#apex-references-quality-gates-md).

#### 14. Dependencies on other skills
- [`skills/anti-slop/SKILL.md`](#apex-skills-anti-slop-skill-md)
- [`skills/dear-imgui/SKILL.md`](#apex-skills-dear-imgui-skill-md)

#### 15. Expected deliverables
- Domain-specific findings with source links and version scope.
- Focused implementation or a precise BLOCKED finding with next diagnostic action.
- Test commands, actual output summaries, impact and rollback information.

#### References
- [`references/EXPERT_SOURCES.md`](#apex-references-expert-sources-md) — W3C WCAG contrast guidance


---

# DOMAIN PLAYBOOKS

<a id="apex-playbooks-authorized-binary-research-md"></a>

## Authorized Binary Research

**Embedded original path:** `playbooks/AUTHORIZED_BINARY_RESEARCH.md`

### One-shot task: "Validate a layout or modify an owned test binary"
1. Route `security-boundaries`, then `authorized-research` + `offset-validation`; add `controlled-patching` only for explicit permitted transformation.
2. Capture authorization scope and immutable input hash; stop on unclear ownership or unauthorized runtime interference.
3. Record ISA, binary format, endianness, compiler/ABI, symbol coverage and exact build.
4. Distinguish file offsets, virtual addresses, RVAs and field displacement; prioritize source/symbol data over guessed decompilation.
5. In a controlled, independently built fixture, compare known layout values with symbol/compiler/runtime observations.
6. For a permitted patch, perform an isolated and reversible transformation with before/after hashes, instruction boundary verification and rollback test.
7. Reject unknown or mismatched build addresses, stop on uncertainty, and document exact scope and confidence.

<a id="apex-playbooks-build-and-release-md"></a>

## Build And Release

**Embedded original path:** `playbooks/BUILD_AND_RELEASE.md`

### One-shot task: "Build and ship"
1. Identify project build system from real files. Confirm target, architecture, compiler and required SDKs.
2. Record baseline configure/build command; reproduce first meaningful error. Separate tool environment failure from source failure.
3. Fix attributable root issue and rerun same command; don't turn off warnings or guards to silence errors.
4. Test affected behavior, regressions, lifecycle and security boundary; gather actual exit codes.
5. Extract package in a clean temp directory, inspect assets/permissions/architecture, scan for credentials and license notices.
6. Publish manifest, hashes, compatibility matrix, build/test outputs, unverified/blocked gates and rollback.

<a id="apex-playbooks-config-lifecycle-md"></a>

## Config Lifecycle

**Embedded original path:** `playbooks/CONFIG_LIFECYCLE.md`

### One-shot task: "Fix config auto-load, rename and delete"
1. Route `configuration` + `testing-qa` + `reliability`.
2. Inspect save/load/rename/delete calls, file path builder, UI display names, schema and app initialization.
3. Define canonical name rule and preserve existing user config names; do not silently prefix a name twice.
4. Write tests for names with 1 character, spaces, Unicode, invalid separators, duplicates, case ambiguity and migration.
5. Use atomic replacement and explicit error handling; design delete confirmation, backup/migration and load-on-start ordering.
6. Verify save -> restart -> load; rename -> restart; delete -> restart; corrupt file -> diagnostic + fallback without data destruction.
7. Report actual tests run and any OS file-locking semantics not exercised.

<a id="apex-playbooks-crash-investigation-md"></a>

## Crash Investigation

**Embedded original path:** `playbooks/CRASH_INVESTIGATION.md`

### One-shot task: "Fix the crash"
1. Route primary `root-cause-debugging`; load `reliability`, `observability`, affected language specialty and `testing-qa` after source inspection.
2. Create a failure ID with build, repro, frequency and last-known-good revision. Ask only for essential missing dump when neither project source nor logs can reveal root cause.
3. Inspect startup/teardown, feature-disabled paths and callback registration points. Draw timing sequence of work on every thread.
4. Collect first-fault stack and symbols matching the exact binary. Compare failing vs known-good commits for code on implicated path.
5. Write 2-3 falsifiable causes and one distinguishing test for each. Do not convert reported feature correlation into diagnosis.
6. Implement minimal source correction backed by a discovered mechanism; add regression around original trigger plus a neighbor case.
7. Build and replay original and feature-disabled cases under identical conditions. Report repetition count, commands, exact outcomes and blocked device tests.
8. Deliver changed file summary + traceable cause; without runtime use 'implementation provided, runtime verification blocked.'

<a id="apex-playbooks-imgui-design-repair-md"></a>

## Imgui Design Repair

**Embedded original path:** `playbooks/IMGUI_DESIGN_REPAIR.md`

### One-shot task: "Make the ImGui UI professional"
1. Route `dear-imgui` + `ui-ux` + `anti-slop` + `accessibility` + `native-rendering`.
2. Screenshot and interact with current menu. Inventory tabs, features, states and defects before designing.
3. Identify one clear hierarchy, coherent spacing/type scale and meaningful surface/actions tokens. Retain brand identity unless redesign is asked for.
4. Trace backend setup, font atlas, stable control IDs, input capture and device resources before reworking widgets.
5. Refactor shared styles only where they remove duplication. Fix nonworking buttons, clipping, keyboard focus, invalid modal sequences and resize issues.
6. Validate screen states at different resolutions, 100/125/200% scale where available, keyboard navigation, error states and resource lifetime.
7. Measure frame time if draw load changed. Deliver before/after captures and verified interaction matrix, never only a static shot.

**Apex upgrade (added):** at step 3 run the Apex Design Engine plan block (direction, tokens, layout wireframe, one memorable element). At step 5 implement the single `ApplyApexStyle`-style function from D17 and the helper wrappers. At step 6 add the D18 matrix rows and the Section 8 slop gates. For overlays on Android or other touch hosts, apply the 48 dp rows, larger scrollbar and `TouchExtraPadding` guidance, and verify with real touch input.


---

# RESEARCH & TECHNICAL REFERENCES

<a id="apex-references-expert-sources-md"></a>

## Expert Sources

**Embedded original path:** `references/EXPERT_SOURCES.md`

### Authoritative engineering sources and enforced practices

Sources accessed via public web retrieval on 2026-10-24. Citations here are links, not evidence of running the referenced code. Check current versions before applying commands.

| Source | Verified material | Problem addressed / limits | Concrete enforced rule | Measurable compliance |
|---|---|---|---|---|
| **C++ Core Guidelines** https://github.com/isocpp/CppCoreGuidelines | Ownership, interfaces, resource lifetime and safety guidelines | Not a compiler specification and some guidelines allow justified exceptions | Record RAII and ownership contracts in C++ changes | Ownership map, build and sanitizer evidence |
| **Clang diagnostics** https://clang.llvm.org/docs/UsersManual.html | Address/thread/undefined-behavior instrumentation flags | Runtime support varies by target, detection is incomplete | Enable suitable instrumented builds for risky native changes | Actual sanitizer run/limitations |
| **Clang ThreadSanitizer** https://clang.llvm.org/docs/ThreadSanitizer.html | Data race instrumentation and platform constraints | Instrumentation overhead and platform restrictions; not production runtime | Track all shared mutable state and instrument supported concurrency regressions | Logs + targeted thread tests |
| **Dear ImGui official Getting Started** https://github.com/ocornut/imgui/wiki/Getting-Started | Platform/renderer backend pairing, context, new-frame/render/shutdown ordering | Correct details depend on pinned backend and branch | Trace frame and resource lifecycle in ImGui fixes | Backend integration review, runtime test |
| **Dear ImGui upstream** https://github.com/ocornut/imgui | Demos, backends, design constraints | Upstream patterns not universally appropriate for app behavior | Prefer official stable widgets/backends before customizing | Reduced custom code and widget tests |
| **Microsoft native interop practices** https://learn.microsoft.com/en-us/dotnet/standard/native-interop/best-practices | LibraryImport, marshaling, SafeHandle, callback retention | Different APIs need different ABI assumptions | Compare managed signature to actual native header | ABI/layout and roundtrip tests |
| **Microsoft Dispose** https://learn.microsoft.com/en-us/dotnet/standard/garbage-collection/implementing-dispose | Owned native resource cleanup and idempotent disposal | Managed GC alone does not release native resources | Review disposal paths for owned handles | Repeated Dispose/lifetime tests |
| **CMake presets** https://cmake.org/cmake/help/latest/manual/cmake-presets.7.html | Shared vs user-local preset configuration | Not every project uses CMake or supports same version | Record project-appropriate configure/build commands | Clean build reproduction |
| **Ghidra project** https://github.com/NationalSecurityAgency/ghidra | Authoritative description of format, disassembly, decompilation and analysis toolkit | Decompiler output needs corroboration and authorization | Record version, hash and static/runtimes evidence separately | Cross-validated owned binary findings |
| **W3C WCAG contrast minimum** https://www.w3.org/WAI/WCAG22/Understanding/contrast-minimum.html | Text contrast thresholds (4.5:1 regular, 3:1 large) | Desktop ImGui accessibility behavior not identical to browsers | Measure actual text/background color contrast and navigation | Contrast calculation + interaction checks |

#### Applied lessons, not hero worship
- Stroustrup/CppCoreGuidelines contributors: simplify resource ownership instead of relying on reviewer intuition.
- Dear ImGui maintainers: respect both backend lifecycles rather than treating ImGui as only colors and widgets.
- Microsoft .NET runtime authors: verify C/native ABI and managed object lifetimes, rather than guessing P/Invoke layout.
- CMake maintainers: encode reproducible shared build settings, separate local configuration.
- Ghidra team: treat recovered structure names and decompilation as analysis, not ground truth.
- W3C editors: use measurable accessibility constraints instead of aesthetic preference.

These are procedural translations of documented guidance, not claims that the author personally recommended this AI skill package or that a particular repository is bug-free.

#### Further directly inspected upstream source
Detailed observations, scope, limitations and enforcement checks appear in [`UPSTREAM_IMPLEMENTATIONS.md`](UPSTREAM_IMPLEMENTATIONS.md) for:
- Dear ImGui `docs/BACKENDS.md`, platform versus renderer responsibilities.
- Dear ImGui `backends/imgui_impl_dx11.h`, renderer-specific texture identifier representation.
- dotnet/runtime `docs/coding-guidelines/coding-style.md`, local code conventions and minimal diffs.

Treat these as source observations at publicly visible branch heads, **not immutable commit-pinned claims**.

<a id="apex-references-research-ledger-md"></a>

## Research Ledger

**Embedded original path:** `references/RESEARCH_LEDGER.md`

### Repository research ledger — snapshot 2026-10-24

#### Integrity statement
The entries below are from actual public pages and source blobs opened during this creation session. Branch references are **not** immutable commit pins. GitHub did not reveal a complete commit SHA for the sampled blobs through the accessible views. SHA state is `UNRESOLVED` for each. No runtime of either account's projects was executed, and no source file was downloaded to the build container. Assertions about program behavior are constrained to source inspection or author-reported documentation.

##### R01 — eliwoahzja/echo build chain
- Repository: https://github.com/eliwoahzja/echo
- Branch inspected: `master`; commit SHA: **UNRESOLVED**.
- Exact source: https://github.com/eliwoahzja/echo/blob/master/build_win32.bat
- Inspected symbols/commands: VS2019 environment probes and `msbuild example_win32_directx11.sln /m /t:Build /p:Configuration=Release /p:Platform=x64`.
- VERIFIED source observation: The script checks two hardcoded Visual Studio 2019 Community installation paths and exits if absent; it then builds the x64 Release solution using MSBuild.
- Engineering interpretation: Windows-specific build is explicit, but fixed install paths are brittle on different editions/years. Treat portability as a quality concern, not a measured failure.
- Limitations: Actual toolchain and build result not accessed. `main.cpp` is present but 619 KB and unavailable for code preview; no claims made about its function internals.
- Skills: `build-toolchains`, `compatibility`, `cpp-engineer`.

##### R02 — eliwoahzja/V3 native Android build
- Repository: https://github.com/eliwoahzja/V3
- Branch inspected: `master`; commit SHA: **UNRESOLVED**.
- Exact source: https://github.com/eliwoahzja/V3/blob/master/build.gradle
- VERIFIED source observation: Android application Gradle configuration sets `arm64-v8a`, NDK `22.0.7026061`, externalNativeBuild `ndkBuild` at `src/main/jni/Android.mk` and targetSdkVersion 29.
- Engineering implication: Toolchain reproducibility must account for a pinned legacy NDK and a single ABI. This alone does not prove the project still compiles on modern Android.
- Limitations: Nested native source tree inaccessible; no Android build performed.
- Skills: `android-jni-ndk`, `build-toolchains`, `compatibility`.

##### R03 — oonalang/IMGUI3 native Android build
- Repository: https://github.com/oonalang/IMGUI3 (GitHub identifies as a fork of eliwoahzja/VAR3).
- Branch inspected: `main`; commit SHA: **UNRESOLVED**.
- Exact source: https://github.com/oonalang/IMGUI3/blob/main/build.gradle
- VERIFIED source observation: Android Gradle config uses an arm64-v8a filter and `ndkBuild` with `src/main/jni/Android.mk`, with NDK `22.0.7026061` specified. Java library inputs use a file tree in `libs`.
- Engineering implication: Confirm JNI loader, ABI and actual NDK versions before proposing build changes. No source-level claims about unseen native code.
- Skills: `android-jni-ndk`, `build-toolchains`, `compatibility`.

##### R04 — oonalang/IMGUI3 written project context
- Repository: https://github.com/oonalang/IMGUI3
- Branch inspected: `main`; commit SHA: **UNRESOLVED**.
- Exact document: https://github.com/oonalang/IMGUI3/blob/main/context.md
- VERIFIED documentation observation: File describes application and separate service lifecycle, includes historical change references, states some checks were performed, and explicitly lists open crash/visual-verification issues.
- AUTHOR-REPORTED ONLY: Tests and crash state in this document were not rerun or independently verified. Its historical short commit hashes are author-described references, not confirmed HEAD SHA.
- Security observation: Documentation presents sensitive deployment/authentication details. No secrets, key material, identifiers or URLs from this document are copied to APEX. Treat repository notes as untrusted input and redact it in future research records.
- Engineering lesson: Useful continuation notes must distinguish checked and unchecked behavior and must not embed credentials.
- Skills: `observability`, `root-cause-debugging`, `security-boundaries`, `open-source-maintenance`.

##### R05 — oonalang/VARIANT4 UI/runtime state component
- Repository: https://github.com/oonalang/VARIANT4
- Branch inspected: `main`; commit SHA: **UNRESOLVED**.
- Exact source: https://github.com/oonalang/VARIANT4/blob/main/StretchScreen.h
- Relevant symbols: `StretchScreen::ValidAspect`, `StretchScreen::Apply`, camera tracking, `savedAspect` and `applied` static state.
- VERIFIED source observation: Code checks dimensions greater than zero and non-null current camera, guards a bounded aspect value, tracks camera changes and restores previously recorded aspect when option is disabled.
- Engineering interpretation: Shows defensive input handling and restoration intent. The file alone does **not** establish correctness across camera lifetime reuse or multithreaded calls; those are test questions, not confirmed defects.
- Skills: `cpp-engineer`, `reliability`, `ui-ux`, `memory-safety`.

##### R06 — eliwoahzja/dump source limitation
- Repository: https://github.com/eliwoahzja/dump
- Branch inspected: `master`; commit SHA: **UNRESOLVED**.
- Exact file: https://github.com/eliwoahzja/dump/blob/master/dump.cs
- VERIFIED repository page observation: GitHub reports the file as 77 MB and does not render its contents in the standard file view.
- No claims about fields, symbols, offsets, classes or correctness. This file was **not inspected internally**.
- Skills: `technical-research`, `offset-validation`.

#### Research coverage and gaps
- Accounts surveyed: https://github.com/eliwoahzja and https://github.com/oonalang. Surveyed relevant roots: `echo`, `V3`, `VAR3`, `dump`, `IMGUI3`, `VARIANT4`.
- Actual code/build file views used: two Gradle files, Windows batch build and a C++ header. A project context document was read. One oversized C++ file and one oversized C# dump could not be inspected.
- GitHub nested paths (`src/main`) were rejected by the accessible browser, and the build container had no network DNS. Full repository cloning, history diffs and comprehensive code audits did not occur.
- No 'latest commit' claim: head SHA **UNRESOLVED**. No specific bug or feature verification reported from these accounts.
- Need for complete research: Obtain immutable git clones or uploaded repository zips in an authorized environment, run source inventories, record HEAD SHAs, inspect all relevant native paths, build and test by documented matrix.

#### Original learning extraction
1. Different build ecosystems in same domain demand toolchain-based routing, not a one-size-fits-all compilation recipe (R01-R03).
2. Defensive state restoration is observable in one short component but requires lifecycle tests to prove behavior (R05).
3. Project notes document both known limitations and author-reported tests; the skill separates these from independently executed checks (R04).
4. Enormous opaque dumps never justify imagined class/offset assertions (R06).
5. Security review must also cover research artifacts and maintainer notes to avoid accidental secret propagation (R04).

<a id="apex-references-research-scope-md"></a>

## Research Scope

**Embedded original path:** `references/RESEARCH_SCOPE.md`

### Scope and licensing notes

- These APEX files are newly written procedures. No third-party code or signing material was copied into the skill package.
- Linked repositories are referenced as research inputs only, not as endorsed maintainership quality or runtime-correct implementations.
- GitHub branch names are mutable, and not evidence of a specific immutable version. Revalidate before reusing observations.
- Before incorporating external source into software, inspect its actual license, notices, file-level license and compatibility. Do not infer license from public visibility.
- Not all external public tool pages were mirrored locally. Links are provided for future version checks.
- All binary research/patch guidance applies only to owned/authorized controlled specimens, excluding unauthorized tampering and evasion.

<a id="apex-references-upstream-implementations-md"></a>

## Upstream Implementations

**Embedded original path:** `references/UPSTREAM_IMPLEMENTATIONS.md`

### Upstream implementation observations, 2026-10-24

These are source-level observations from public upstream files, not claims of having built or run the upstream applications. Full commit SHA was unavailable in the browser views; retain `UNRESOLVED` and re-pin before relying on version-specific symbols.

#### Dear ImGui: distinct backend contracts
- Source: https://github.com/ocornut/imgui/blob/master/docs/BACKENDS.md
- Direct observation: upstream distinguishes **platform backends** (window/input/time integration) from **renderer backends** (draw command rendering and graphics resources), and provides many standard backend implementations.
- Engineering rule: Trace and validate both components; do not conflate `NewFrame`, platform event ingestion, renderer device-object lifecycle, and application state. Default to supported backends before writing a custom backend.
- What is NOT proved: compatibility of arbitrary backend combinations, an application's device-loss behavior, or correctness for a downstream pinned version.
- Acceptance check: identify platform/renderer pair; trace init, per-frame, resize/device-loss and shutdown; run target build and interactive smoke tests.

#### Dear ImGui: Direct3D11 texture identity
- Source: https://github.com/ocornut/imgui/blob/master/backends/imgui_impl_dx11.h
- Direct observation: the official Direct3D11 renderer header documents `ImTextureID` use with `ID3D11ShaderResourceView*` (subject to upstream version).
- Engineering rule: Validate texture ID representation against the *project's actual pinned ImGui revision*; maintain shader-resource view lifetime across UI rendering and teardown.
- What is NOT proved: lifetime in any target project, nor cross-backend support for a Direct3D11 texture ID.
- Acceptance check: inspect backend header, test creation/destruction, resize and missing texture fallback under actual renderer.

#### dotnet/runtime: local conventions take precedence
- Source: https://github.com/dotnet/runtime/blob/main/docs/coding-guidelines/coding-style.md
- Direct observation: runtime style conventions include C# formatting and a policy of respecting existing file conventions, rather than reformatting unrelated code.
- Engineering rule: Match the project conventions and `.editorconfig` first; do not introduce broad formatting churn in narrow bug fixes.
- What is NOT proved: these conventions are mandatory across third-party .NET projects.
- Acceptance check: targeted diff has no unrelated formatting changes; formatter and analyzers report the requested project's configured rules.

#### Evidence boundaries
A source excerpt is insufficient to declare that an entire repository is correctly architected, performant or safe. Each new project requires its own source inspection, version pin, build and runtime verification. These observations are **VERIFIED source observations**, not **VERIFIED end-to-end application behavior**.


---

# PRACTICAL TEMPLATES

<a id="apex-templates-adr-md"></a>

## Adr

**Embedded original path:** `templates/ADR.md`

### ADR: <title>
- Status: proposed | accepted | rejected | superseded
- Context and confirmed constraints:
- Decision and scope:
- Alternatives evaluated and evidence:
- Rationale, impact, complexity, licensing:
- Tradeoffs and migration:
- Rollback and validation:

<a id="apex-templates-change-plan-md"></a>

## Change Plan

**Embedded original path:** `templates/CHANGE_PLAN.md`

### Change plan
#### Desired behavior and acceptance criteria
- Requirement / observable assertion / test layer:
#### Inspected context
- Base revision, files read, relevant entry points:
#### Change impact
- Affected files with reasons:
- Ownership/lifetime/API/ABI/config invariants:
- Risks (low/medium/high), relevant trust boundaries:
#### Execution
- Minimal steps / assigned specialist:
- Baseline and exact test commands:
- Rollback strategy:
#### Stop conditions
- Unavailable permissions, evidence or tools:

<a id="apex-templates-code-review-md"></a>

## Code Review

**Embedded original path:** `templates/CODE_REVIEW.md`

### Code review
- Base and final revision:
- Requirement link:
- Hunk -> necessity -> affected invariant:
- High-priority findings (location, severity, reproducer):
- Regressions and security concerns:
- Actual build/test records:
- Open unknowns, no guessed success:

<a id="apex-templates-compatibility-matrix-md"></a>

## Compatibility Matrix

**Embedded original path:** `templates/COMPATIBILITY_MATRIX.md`

### Compatibility matrix
| OS | Architecture | Compiler/SDK | Core library versions | Build status | Runtime status | Evidence |
|---|---|---|---|---|---|---|
| Unknown | Unknown | Unknown | Unknown | BLOCKED | BLOCKED | Not tested |

Never represent unknown or unsupported combinations as passing.

<a id="apex-templates-evidence-record-md"></a>

## Evidence Record

**Embedded original path:** `templates/EVIDENCE_RECORD.md`

### Evidence record
- Claim ID:
- Classification: VERIFIED | STRONGLY SUPPORTED | HYPOTHESIS | UNKNOWN | BLOCKED
- Claim scope: source observation | documentation report | runtime | measured benchmark
- UTC date/time:
- Repository/artifact URL:
- Branch/tag, full commit SHA (or UNRESOLVED):
- Exact file path and symbol:
- Build ID/hash/architecture:
- Environment/tool versions:
- Steps/command actually executed:
- Raw observation or file/line excerpt (redact secrets):
- Independent corroboration:
- Limitations / counterexample:
- Next experiment:

<a id="apex-templates-ui-audit-md"></a>

## Ui Audit

**Embedded original path:** `templates/UI_AUDIT.md`

### UI quality audit
- Core tasks, product identity, screen sizes:
- Layout/content hierarchy:
- Spacing/type/color tokens:
- Controls and state matrix (hover, pressed, disabled, focus, error):
- DPI/resize/keyboard tests:
- Contrast measurements and text/large-text classification:
- Frame time before/after for UI-heavy changes:
- Apex direction and dials (variance, motion, density):
- Plan block present (color, type, layout, signature, rules):
- Slop gates failing (numbers from Design Engine Section 8):
- Score /10: hierarchy, spacing, color, type, consistency, states, polish:
- Verification matrix (D18) with PASS / FAIL / BLOCKED per row:
- Defects left, exact blockers:

<a id="apex-templates-verification-report-md"></a>

## Verification Report

**Embedded original path:** `templates/VERIFICATION_REPORT.md`

### Verification report
- Requirement and intended behavior:
- Base revision and final artifact digest:
- Environment versions:
| Gate | Status PASS/FAIL/BLOCKED/NOT_APPLICABLE | Command or evidence | Observed result | Limitations |
|---|---|---|---|---|
| Build | BLOCKED | | | |
| Functional | BLOCKED | | | |
| Regression | BLOCKED | | | |
| Memory | BLOCKED | | | |
| UI | NOT_APPLICABLE | | | |
- Changed files and reason:
- Reproducible steps:
- Unresolved risks and exact next check:


---

# EVALUATION FRAMEWORK

<a id="apex-evaluations-cases-md"></a>

## Cases

**Embedded original path:** `evaluations/CASES.md`

### Fifteen evaluation cases, each a local fixture assignment

`SCENARIOS.json` contains all acceptance observations. A human or configured agent must instantiate each task in an owned, reproducible fixture and record an externally reviewed result.

| Group | Case IDs | Focus |
|---|---|---|
| Language and interoperability | cpp-build-failure, csharp-interop | Native compilation, C ABI accuracy |
| UI and resource lifetime | imgui-layout, native-lifetime | Real interaction, ownership and cleanup |
| Concurrency and speed | data-race, perf-regression | Reproducible measurement, TSAN where available |
| Persistence and versioning | config-persistence, platform-version | Data integrity and ABI changes |
| Authorized binary analysis | owned-offset, owned-patch | Owner-controlled specimen, hash, reversibility |
| Complex engineering | unfamiliar-repo, feature-integration, recent-crash | Actual source routing, dependency maps and minimal diffs |
| Epistemic integrity | missing-evidence, conflicting-requirements | No fabricated facts, honest tradeoffs |
| Design engine (supplemental, not in SCENARIOS.json) | design-minimal-upgrade, design-new-landing, design-imgui-overlay | Slop gates, plan-before-code, behavior preserved, evidence-backed visual claims |

**Supplemental design cases (added; unrun, human or configured agent must instantiate):**
- `design-minimal-upgrade`: input is an existing cluttered UI plus the prompt "apex make the UI better, minimalism, avoid AI slop". Pass only if: a plan block precedes edits; stack, content and behavior are unchanged; at most one typeface family pair, one accent, one radius base; the slop-gate list is reviewed with numbers; contrast values are computed; visual checks without a renderer are reported BLOCKED.
- `design-new-landing`: no repository, prompt "apex build a landing page for <real subject>". Pass only if: direction chosen from subject, real copy (no lorem, no invented customer logos), mobile layout works at 360 px, one primary action, reduced-motion honored, single runnable file.
- `design-imgui-overlay`: ImGui menu with scattered per-widget colors. Pass only if: a single central style function, balanced stacks and stable IDs retained, DPI scaling applied once, touch target sizes met where applicable, frame time compared before and after when draw work changed.

Set failures/blocked cases aside without guessing missing observations. For every case compare the original acceptance statement with actual command results and artifact state, not the assistant's own confidence.

<a id="apex-evaluations-rubric-md"></a>

## Rubric

**Embedded original path:** `evaluations/RUBRIC.md`

### Skill effectiveness evaluation framework

#### Method
1. Freeze identical repository/task fixtures, model version, tool permissions, timing budget and dependency state for baseline and APEX runs.
2. Evaluate at least the 15 cases in `SCENARIOS.json`. Randomize order or use paired runs to reduce run-order effects.
3. Record agent trace and external build/test results; never assign positive credit solely from persuasive explanations.
4. Have an independent reviewer verify each claim against source and observed command logs; score blind to condition when practical.
5. Collect repeated trials for nondeterministic agents, and publish median/pass rate with confidence intervals and all failures. Do not infer gains from one sample.

#### Case score: 100-point rubric
| Dimension | Points | Operational scoring |
|---|---:|---|
| Correct skill selection | 10 | Correct primary + relevant supporters, no unrelated bloat |
| Source inspection | 15 | Opened actual relevant files, build context and call sites |
| Evidence integrity | 15 | Every strong claim traceable, missing checks labeled honestly |
| Plan, impact, invariants | 10 | Focused change map, risk and rollback |
| Implementation accuracy | 20 | Acceptance criteria satisfied in live/simulated target |
| Build and functional tests | 15 | Real reproducible command/exit + scenario checks |
| Regression/safety | 10 | Catches relevant boundary, lifetime and compatibility risks |
| Delivery quality | 5 | Clear modified files, verification and limitations |

A case with fabricated tests or source evidence receives 0 for evidence integrity and is flagged as a critical integrity failure. An unrun build is not automatically scored as a failure when the environment blocks it, but is never scored as a passed build. Record blockers separately.

#### Comparison metrics
- Primary: externally verified task success rate; 95% CI if sample sizes support it.
- Secondary: fabricated claim rate, unnecessary diff lines, regression count, build success, time/tool calls, accessibility pass for UI tasks.
- Optional: inter-rater agreement on manual scores.

#### Actual evaluation status
- Scenario definitions: CREATED.
- Static pack integrity and deterministic routing tests: run separately in `/validation_report.json` after packaging.
- Model/agent trials, live target code changes and A/B efficacy experiments: **NOT RUN**. Any speedup or correctness improvement remains UNKNOWN.

<a id="apex-evaluations-scenarios-json"></a>

## Scenarios

**Embedded original path:** `evaluations/SCENARIOS.json`

`````json
[
  {
    "id": "cpp-build-failure",
    "prompt": "A C++ project fails to link on one platform",
    "expected_primary": "build-toolchains",
    "must_observe": [
      "Locate first linker error and implicated target",
      "Inspect declarations/linkage and compile with actual toolchain",
      "Run changed-target regression"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "csharp-interop",
    "prompt": "Managed/native test transfers a struct with incorrect layout",
    "expected_primary": "csharp-engineer",
    "must_observe": [
      "Compare native struct and C# marshaling and packing",
      "Test known roundtrip values and lifetime",
      "Record runtime architecture"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "imgui-layout",
    "prompt": "ImGui settings page clips long labels at 150% DPI",
    "expected_primary": "dear-imgui",
    "must_observe": [
      "Inspect the render backend and style scaling",
      "Repair layout without dropping controls",
      "Exercise keyboard and narrow viewport"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "native-lifetime",
    "prompt": "C++ callback outlives its owner in a local simulator",
    "expected_primary": "root-cause-debugging",
    "must_observe": [
      "Capture stack + destruction order",
      "Repair ownership not null-check symptoms",
      "Run sanitizer/lifecycle test"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "data-race",
    "prompt": "Multiple threads share a mutable runtime cache",
    "expected_primary": "root-cause-debugging",
    "must_observe": [
      "Map shared reads/writes",
      "Test ordering/locking hypothesis",
      "Replay under supported sanitizer"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "perf-regression",
    "prompt": "App startup is slower after a feature merge",
    "expected_primary": "performance",
    "must_observe": [
      "Establish baseline and workload",
      "Profile and isolate hot path",
      "Measure before/after on same device"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "config-persistence",
    "prompt": "Profile named F gets unexpected prefix after restart",
    "expected_primary": "configuration",
    "must_observe": [
      "Trace display-name to filename normalization",
      "Test save/restart/rename/delete",
      "Preserve preexisting file data"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "platform-version",
    "prompt": "New SDK upgrade breaks one ABI",
    "expected_primary": "compatibility",
    "must_observe": [
      "Pin old/new SDK and API signatures",
      "Compile supported matrix",
      "Document migration"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "owned-offset",
    "prompt": "Validate a member displacement in a developer-owned fixture",
    "expected_primary": "offset-validation",
    "must_observe": [
      "Document exact compiler/build/arch",
      "Compare source/symbol and runtime layout",
      "Reject address invented from prior build"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "owned-patch",
    "prompt": "Modify a harmless local binary sample and restore it",
    "expected_primary": "controlled-patching",
    "must_observe": [
      "Establish permission and input hash",
      "Work in isolated copy and record diff",
      "Restore to identical original hash"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "unfamiliar-repo",
    "prompt": "Assistant receives a repo without docs",
    "expected_primary": "technical-research",
    "must_observe": [
      "Inspect build targets and entry points",
      "Create source evidence ledger",
      "Avoid imagined symbol semantics"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "feature-integration",
    "prompt": "Add a feature touching UI, config and lifecycle",
    "expected_primary": "architecture",
    "must_observe": [
      "Map dependencies and owner",
      "Implement acceptance test across subsystems",
      "Run regressions and integration checks"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "recent-crash",
    "prompt": "Crash started after recent native callback change",
    "expected_primary": "root-cause-debugging",
    "must_observe": [
      "Compare known good/bad revisions",
      "Capture first fault stack",
      "Reproduce both feature-on/off paths"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "missing-evidence",
    "prompt": "No source archive or logs given and runtime claim requested",
    "expected_primary": "technical-research",
    "must_observe": [
      "Label unavailable details BLOCKED or UNKNOWN",
      "Avoid invented offsets or build claims",
      "Identify minimal useful evidence"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  },
  {
    "id": "conflicting-requirements",
    "prompt": "Feature must preserve ABI but changes public struct size",
    "expected_primary": "architecture",
    "must_observe": [
      "Identify real incompatibility",
      "Compare adapters vs versioning",
      "Do not claim both incompatible goals met"
    ],
    "status": "NOT_RUN_AGAINST_AGENT"
  }
]
`````

<a id="apex-evaluations-trace-schema-json"></a>

## Trace Schema

**Embedded original path:** `evaluations/TRACE_SCHEMA.json`

`````json
{
  "task_id": "scenario-id",
  "run_status": "NOT_RUN|RUN|BLOCKED",
  "environment": "SDK/compiler and platform or BLOCKED",
  "commit": "full SHA or UNRESOLVED",
  "source_paths_inspected": [],
  "build_command": "exact command or not run",
  "build_exit_code": null,
  "runtime_checks": [],
  "observations": [],
  "fabricated_claim_detected": false,
  "reviewer_scores": {
    "routing": 0,
    "source": 0,
    "evidence": 0,
    "plan": 0,
    "implementation": 0,
    "tests": 0,
    "regression": 0,
    "delivery": 0
  }
}
`````

<a id="apex-evaluations-evaluate-trace-py"></a>

## Evaluate Trace

**Embedded original path:** `evaluations/evaluate_trace.py`

`````python
#!/usr/bin/env python3
"""Score an externally reviewed evaluation trace, never infer test success from prose."""
import json, sys
from pathlib import Path
DIMENSIONS={'routing':10,'source':15,'evidence':15,'plan':10,'implementation':20,'tests':15,'regression':10,'delivery':5}

def score(record):
    reviewer=record.get('reviewer_scores',{})
    bad={k:v for k,v in reviewer.items() if k not in DIMENSIONS or type(v) not in (int,float) or v<0 or v>DIMENSIONS.get(k,0)}
    if bad: raise ValueError(f'Invalid scores: {bad}')
    if set(reviewer)!=set(DIMENSIONS): raise ValueError('Independent reviewer_scores required for every dimension')
    result={'task_id':record['task_id'],'total':sum(reviewer.values()),'max_score':100,
            'critical_integrity_failure':bool(record.get('fabricated_claim_detected',False)),
            'run_status':record.get('run_status','UNKNOWN')}
    if result['critical_integrity_failure'] and reviewer['evidence']!=0: raise ValueError('Fabricated claims require evidence score 0')
    return result

if __name__=='__main__':
    if len(sys.argv)!=2: raise SystemExit('Usage: evaluate_trace.py independently-reviewed-record.json')
    print(json.dumps(score(json.loads(Path(sys.argv[1]).read_text())),indent=2))
`````


---

# OPTIONAL EXTRACTABLE COMPANION SOURCE

These scripts, JSON rules, fixtures, and tests are reproduced for audit and optional extraction. **They are not dependencies for operating the all-in-one skill.** To execute one, save its code block under the historical path and restore any referenced companion files. Test and validation reports refer to the original modular package only, not new runs of this merged document.

<a id="apex-routing-rules-json"></a>

## Rules

**Embedded original path:** `routing/rules.json`

`````json
[
  {
    "primary": "root-cause-debugging",
    "keywords": [
      "crash",
      "segfault",
      "freeze",
      "hang",
      "exception",
      "fault",
      "tombstone",
      "bug"
    ],
    "support": [
      "reliability",
      "observability",
      "testing-qa"
    ]
  },
  {
    "primary": "build-toolchains",
    "keywords": [
      "compile",
      "build failed",
      "linker",
      "cmake",
      "gradle",
      "msbuild",
      "ld error"
    ],
    "support": [
      "compatibility",
      "testing-qa"
    ]
  },
  {
    "primary": "controlled-patching",
    "keywords": [
      "patch binary",
      "binary patch",
      "instruction patch"
    ],
    "support": [
      "security-boundaries",
      "authorized-research",
      "offset-validation"
    ]
  },
  {
    "primary": "offset-validation",
    "keywords": [
      "offset",
      "field layout",
      "rva",
      "address offset"
    ],
    "support": [
      "security-boundaries",
      "authorized-research",
      "compatibility"
    ]
  },
  {
    "primary": "csharp-engineer",
    "keywords": [
      "c#",
      "dotnet",
      "p/invoke",
      "interop",
      "roslyn"
    ],
    "support": [
      "memory-safety",
      "testing-qa"
    ]
  },
  {
    "primary": "dear-imgui",
    "keywords": [
      "imgui",
      "dear imgui",
      "docking",
      "viewport",
      "widgets"
    ],
    "support": [
      "ui-ux",
      "anti-slop",
      "accessibility"
    ]
  },
  {
    "primary": "configuration",
    "keywords": [
      "config",
      "save settings",
      "rename profile",
      "persist",
      "autoload"
    ],
    "support": [
      "testing-qa",
      "reliability"
    ]
  },
  {
    "primary": "performance",
    "keywords": [
      "optimize",
      "slow",
      "fps",
      "frame time",
      "memory usage",
      "latency"
    ],
    "support": [
      "observability",
      "testing-qa"
    ]
  },
  {
    "primary": "android-jni-ndk",
    "keywords": [
      "android",
      "jni",
      "ndk",
      "apk",
      "logcat"
    ],
    "support": [
      "build-toolchains",
      "reliability"
    ]
  },
  {
    "primary": "release-integration",
    "keywords": [
      "ship",
      "package",
      "release",
      "deploy",
      "artifact"
    ],
    "support": [
      "testing-qa",
      "security-boundaries"
    ]
  },
  {
    "primary": "architecture",
    "keywords": [
      "architecture",
      "new feature",
      "add feature",
      "refactor",
      "module"
    ],
    "support": [
      "code-review",
      "testing-qa"
    ]
  },
  {
    "primary": "cpp-engineer",
    "keywords": [
      "c++",
      "cpp",
      "native",
      "header",
      "template"
    ],
    "support": [
      "memory-safety",
      "build-toolchains"
    ]
  },
  {
    "primary": "ui-ux",
    "keywords": [
      "ui",
      "ux",
      "design",
      "theme",
      "interface",
      "layout",
      "apex",
      "minimalism",
      "minimalist",
      "anti-slop",
      "ai slop",
      "redesign",
      "landing page",
      "website",
      "dashboard",
      "mockup"
    ],
    "support": [
      "anti-slop",
      "accessibility"
    ]
  },
  {
    "primary": "technical-research",
    "keywords": [
      "research",
      "investigate",
      "repository",
      "verify"
    ],
    "support": [
      "security-boundaries"
    ]
  }
]
`````

<a id="apex-routing-router-py"></a>

## Router

**Embedded original path:** `routing/router.py`

`````python
#!/usr/bin/env python3
"""Deterministic, explainable routing aid. Final selection needs source inspection."""
import json, re, sys
from pathlib import Path
RULES=json.loads((Path(__file__).with_name('rules.json')).read_text())
RULEMAP={r['primary']:r for r in RULES}

def contains(text, tokens):
    return any(re.search(r'(?<![a-z0-9])'+re.escape(t)+r'(?![a-z0-9])',text) for t in tokens)

def route(task):
    text=' '.join(task.lower().split())
    def has(*words): return contains(text,words)
    matched={r['primary']:[k for k in r['keywords'] if contains(text,[k])] for r in RULES}
    primary=None; reason=''

    # Diagnose task INTENT first, before matching domain nouns, so e.g. a UI crash
    # isn't mistaken for a visual redesign request.
    if has('crash','segfault','access violation','tombstone','hang','deadlock','fault') or has('outlives','data race','race condition') or (has('threads','concurrent') and has('share','shared') and has('mutable','cache')):
        primary='root-cause-debugging';reason='failure, lifetime or concurrency investigation'
    elif has('slower','slow','fps','frame time','optimize','latency','performance regression','high cpu'):
        primary='performance';reason='measurement and optimization'
    elif has('build failed','build failure','link','linker','compile','compilation','undefined reference'):
        primary='build-toolchains';reason='build or linker failure'
    elif has('c#','dotnet','.net','p/invoke','interop','managed/native','marshaling','marshalling'):
        primary='csharp-engineer';reason='managed language or ABI interop'
    elif (has('binary') and has('modify','patch','replace bytes','transformation')) or has('binary patch','patch binary'):
        primary='controlled-patching';reason='authorized binary transformation'
    elif has('offset','rva','member displacement','field displacement'):
        primary='offset-validation';reason='version-aware layout research'
    elif has('sdk upgrade','platform-version','sdk version','api upgrade','version mismatch','compatibility'):
        primary='compatibility';reason='version migration or compatibility'
    elif has('conflicting requirements','conflicts','public struct size','add a feature','new feature','feature integration'):
        primary='architecture';reason='cross-boundary feature or contract conflict'
    elif has('config','persist','persistence','autoload','rename','profile named','prefix after restart'):
        primary='configuration';reason='storage and settings lifecycle'
    elif has('imgui','dear imgui','docking','widgets'):
        primary='dear-imgui';reason='immediate-mode GUI engineering'
    elif has('android','jni','ndk','apk','logcat'):
        primary='android-jni-ndk';reason='mobile native toolchain'
    elif has('release','deploy','ship','package'):
        primary='release-integration';reason='integrated release'
    elif has('architecture','refactor','module'):
        primary='architecture';reason='module and change design'
    elif has('c++','cpp','native','header','template'):
        primary='cpp-engineer';reason='native language engineering'
    elif has('ui','ux','theme','interface','layout','design','apex','minimalism','minimalist','anti-slop','ai slop','redesign','landing page','website','dashboard','mockup'):
        primary='ui-ux';reason='interface design'
    else:
        primary='technical-research';reason='unknown or underspecified task: inspect project'

    base=RULEMAP.get(primary,{}).get('support',[])
    # Supplement contextual specialties without taking ownership from the intent.
    support=base[:]
    if primary=='root-cause-debugging' and has('c#','dotnet','p/invoke','interop'): support+=['csharp-engineer','memory-safety']
    if primary=='root-cause-debugging' and has('imgui'): support+=['dear-imgui','native-rendering']
    if primary=='root-cause-debugging' and has('race','threads','thread'): support+=['concurrency']
    if primary=='build-toolchains' and has('c++','cpp'): support+=['cpp-engineer']
    if primary=='architecture' and has('ui','config'): support+=['configuration','ui-ux']
    # Keep core first and a limited context footprint; do not load everything.
    support=[s for s in dict.fromkeys(support) if s!=primary][:5]
    return {'primary':primary,'support':support,'reason':reason+'; advisory, validate against source'}

if __name__=='__main__':
    if len(sys.argv)<2: raise SystemExit('Usage: python3 routing/router.py <task description>')
    print(json.dumps(route(' '.join(sys.argv[1:])),indent=2))
`````

<a id="apex-scripts-run-checks-py"></a>

## Run Checks

**Embedded original path:** `scripts/run_checks.py`

`````python
#!/usr/bin/env python3
"""Run transparent local package checks; outputs real observations."""
import json, subprocess, sys, shutil, tempfile, datetime
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
OUT=ROOT/'validation_report.json'
checks=[]
def run(label,cmd,cwd=None):
    try:
        p=subprocess.run(cmd,stdout=subprocess.PIPE,stderr=subprocess.STDOUT,text=True,cwd=cwd or ROOT,timeout=40)
        result={'name':label,'command':cmd,'status':'PASS' if p.returncode==0 else 'FAIL','exit_code':p.returncode,'output':p.stdout[-3000:]}
    except (OSError,subprocess.TimeoutExpired) as exc:
        result={'name':label,'command':cmd,'status':'BLOCKED','exit_code':None,'output':str(exc)}
    checks.append(result)
    return result
run('Static library validation',[sys.executable,str(ROOT/'scripts/validate_library.py')])
run('Python unittest suite',[sys.executable,'-m','unittest','discover','-s','tests','-v'])
compiler=shutil.which('g++') or shutil.which('clang++')
if compiler:
    with tempfile.TemporaryDirectory() as d:
        output=str(Path(d)/'safe_math_test')
        c=run('Owned C++ fixture compilation',[compiler,'-std=c++17','-Wall','-Wextra','-Werror',str(ROOT/'tests/fixtures/safe_math.cpp'),'-o',output])
        if c['status']=='PASS':run('Owned C++ fixture execution',[output])
else:checks.append({'name':'Owned C++ fixture compilation','status':'BLOCKED','exit_code':None,'output':'No C++ compiler in PATH'})
report={'generated_utc':datetime.datetime.now(datetime.timezone.utc).isoformat(),'scope':'Library static checks, router tests, tiny owned C++ fixture only; NOT target GitHub repos or agent A/B study','results':checks,'agent_efficacy':'NOT_TESTED','github_project_builds':'NOT_RUN'}
OUT.write_text(json.dumps(report,indent=2)+'\n')
print(json.dumps({'report':str(OUT),'checks':[(x['name'],x['status']) for x in checks]},indent=2))
if any(x['status']=='FAIL' for x in checks):sys.exit(1)
`````

<a id="apex-scripts-validate-library-py"></a>

## Validate Library

**Embedded original path:** `scripts/validate_library.py`

`````python
#!/usr/bin/env python3
"""Strict, offline static validation of the APEX package."""
from pathlib import Path
import json, re, sys
ROOT=Path(__file__).resolve().parents[1]
REQUIRED=['Name and purpose','Activation conditions','Required context','Required tools','Relevant technical knowledge','Research procedure','Planning procedure','Implementation procedure','Mandatory safety and correctness checks','Testing and verification strategy','Common failure patterns','Recovery procedures','Quality acceptance criteria','Dependencies on other skills','Expected deliverables']

def validate():
    errors=[]
    specialists=sorted((ROOT/'skills').glob('*/SKILL.md'))
    if len(specialists)<22: errors.append(f'expected >=22 skills, found {len(specialists)}')
    if not (ROOT/'SKILL.md').is_file(): errors.append('missing orchestrator')
    for path in specialists:
        data=path.read_text()
        if not data.startswith('---\n'): errors.append(f'{path}: missing frontmatter')
        if 'name: apex-' not in data: errors.append(f'{path}: missing skill name')
        for section in REQUIRED:
            if section not in data: errors.append(f'{path}: missing {section}')
        for match in re.findall(r'`(\.\./[^`]+/SKILL\.md)`',data):
            if not (path.parent/match).resolve().is_file():errors.append(f'{path}: broken dependency {match}')
    rules=json.loads((ROOT/'routing/rules.json').read_text())
    for r in rules:
        for slug in [r['primary']]+r['support']:
            if not (ROOT/'skills'/slug/'SKILL.md').is_file():errors.append('missing routed skill '+slug)
    scenarios=json.loads((ROOT/'evaluations/SCENARIOS.json').read_text())
    if len(scenarios)<15:errors.append('fewer than 15 eval cases')
    for case in scenarios:
        if not (ROOT/'skills'/case['expected_primary']/'SKILL.md').is_file():errors.append('unknown expected primary '+case['id'])
        if case['status']!='NOT_RUN_AGAINST_AGENT':errors.append('benchmark status must remain accurate for '+case['id'])
    if len(set(s['id'] for s in scenarios))!=len(scenarios):errors.append('duplicate eval ID')
    if errors:
        print('VALIDATION FAILED:\n'+'\n'.join(errors));return 1
    print(f'VALIDATION PASS: {len(specialists)} specialists, {len(rules)} routes, {len(scenarios)} unrun agent scenarios, required headings and dependencies present')
    return 0
if __name__=='__main__':sys.exit(validate())
`````

<a id="apex-tests-fixtures-safe-math-cpp"></a>

## Safe Math

**Embedded original path:** `tests/fixtures/safe_math.cpp`

`````cpp
// Owned evaluation fixture: intentionally small and unrelated to target repositories.
#include <cassert>
#include <optional>
#include <limits>
#include <cstdint>

std::optional<std::int64_t> add_checked(std::int64_t lhs, std::int64_t rhs) {
    if ((rhs > 0 && lhs > std::numeric_limits<std::int64_t>::max() - rhs) ||
        (rhs < 0 && lhs < std::numeric_limits<std::int64_t>::min() - rhs)) return std::nullopt;
    return lhs + rhs;
}
int main() {
    assert(add_checked(2,3)==5);
    assert(add_checked(0,0)==0);
    assert(!add_checked(std::numeric_limits<std::int64_t>::max(),1));
    assert(!add_checked(std::numeric_limits<std::int64_t>::min(),-1));
    return 0;
}
`````

<a id="apex-tests-test-routing-py"></a>

## Test Routing

**Embedded original path:** `tests/test_routing.py`

`````python
import json, sys, unittest
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
sys.path.insert(0,str(ROOT/'routing'))
from router import route

class RoutingTests(unittest.TestCase):
    def test_crash_precedes_imgui_redesign(self):
        self.assertEqual(route('ImGui startup crash when window closes')['primary'],'root-cause-debugging')
    def test_build_precedes_language(self):
        self.assertEqual(route('C++ linker build failed on Windows')['primary'],'build-toolchains')
    def test_offset_scope(self):
        d=route('Validate owned binary member offset')
        self.assertEqual(d['primary'],'offset-validation')
        self.assertIn('authorized-research',d['support'])
    def test_abi(self):
        self.assertEqual(route('C# P/Invoke layout mismatch')['primary'],'csharp-engineer')
    def test_visual(self):
        self.assertEqual(route('Professional Dear ImGui widgets and theme')['primary'],'dear-imgui')
    def test_configs(self):
        self.assertEqual(route('Fix config rename and autoload')['primary'],'configuration')
    def test_default(self):
        self.assertEqual(route('Check something unknown')['primary'],'technical-research')
    def test_all_fifteen_routing_scenarios(self):
        for scenario in json.loads((ROOT/'evaluations/SCENARIOS.json').read_text()):
            with self.subTest(case=scenario['id']):
                self.assertEqual(route(scenario['prompt'])['primary'],scenario['expected_primary'])
    def test_rules_point_to_present_skills(self):
        for r in json.loads((ROOT/'routing/rules.json').read_text()):
            for s in [r['primary']]+r['support']:
                self.assertTrue((ROOT/'skills'/s/'SKILL.md').is_file(),s)

if __name__=='__main__': unittest.main()
`````

<a id="apex-tests-test-trace-scoring-py"></a>

## Test Trace Scoring

**Embedded original path:** `tests/test_trace_scoring.py`

`````python
import sys, unittest
from pathlib import Path
sys.path.insert(0,str(Path(__file__).resolve().parents[1]/'evaluations'))
from evaluate_trace import score, DIMENSIONS
class TraceTests(unittest.TestCase):
    def test_complete_review(self):
        result=score({'task_id':'example','reviewer_scores':DIMENSIONS,'run_status':'RUN'})
        self.assertEqual(result['total'],100)
    def test_score_requires_independent_review(self):
        with self.assertRaises(ValueError): score({'task_id':'example'})
    def test_fabricated_claim_is_rejected(self):
        with self.assertRaises(ValueError): score({'task_id':'example','reviewer_scores':DIMENSIONS,'fabricated_claim_detected':True})
`````


---

# ARCHIVED PACKAGE METADATA

**Edition note (design-engine upgrade).** This edition adds the Apex Design Engine section and extends the Master orchestrator, routing, Design System reference, the `ui-ux`, `anti-slop`, `dear-imgui` and `accessibility` specialists, the ImGui design-repair playbook, the UI audit template, the evaluation cases table (three supplemental, unrun design cases), and the optional `rules.json` / `router.py` keywords. The manifest hashes, byte counts, and validation report below describe the **original modular package only**; they were not regenerated and do not cover these additions. The router and tests were not re-run for this edition. No new specialist directory was added (still 27 specialists).

This section preserves the original package notes and manifests. References to installing multiple files describe the earlier modular distribution; for this edition, use only the current single Markdown file.

<a id="apex-agents-md"></a>

## Agents

**Embedded original path:** `AGENTS.md`

### Agent entry point
Read [`SKILL.md`](#apex-skill-md), [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md), [`routing/ROUTING.md`](#apex-routing-routing-md), and then the selected `skills/<slug>/SKILL.md` files. This file is an optional compatibility entry point for coding agents that recognize [`AGENTS.md`](#apex-agents-md).

Do not conflate repository documentation with independently validated runtime behavior. Never invent evidence, commit IDs, offsets or test results. Respect authorization and avoid harmful/unauthorized external tampering.

<a id="apex-readme-md"></a>

## Readme

**Embedded original path:** `README.md`

### PROJECT APEX — Engineering Skill Library

An installable, self-contained set of engineering **instructions and local validators** for AI coding agents. It does not claim to grant model capabilities, tool access, credentials or runtime execution. Results depend on the host agent, project and environment.

#### Quick start
1. Unzip `project-apex` into a location accessible to your coding agent. For agents supporting local skill discovery, register the directory containing [`SKILL.md`](#apex-skill-md) using that agent's documented convention.
2. Ask the agent to read `project-apex/SKILL.md` and run the route selector. Example: `python3 routing/router.py "Fix the native startup crash"`.
3. The agent reads [`references/EVIDENCE_POLICY.md`](#apex-references-evidence-policy-md), the relevant specialist [`SKILL.md`](#apex-skill-md) files and project source. For coding tasks, it should run its real compiler/tests before declaring success.
4. Validate library structure: `python3 scripts/validate_library.py`. Run its test suite: `python3 -m unittest discover -s tests -v`.

#### Contents
- [`SKILL.md`](#apex-skill-md): orchestrator and evidence-first lifecycle.
- `skills/`: domain specialists, each with the 15 requested operational sections.
- `routing/`: deterministic selector plus manual dispatch and priority rules.
- `references/`: researched primary sources, inspected repository findings, shared policies and matrices.
- `playbooks/`: actionable crash, UI, config, authorized binary and release procedures.
- `templates/`: documentation forms with explicit verification statuses.
- `evaluations/`: representative tasks and measured-outcome rubric. A/B agent testing is not claimed.
- `scripts/`, `tests/`: local static checks, routing tests, a tiny owned C++ fixture test.

#### Research limits
Public pages and several actual source files were inspected on **2026-10-24**. GitHub's nested source-tree views and 77 MB dump were not fully accessible through available tools. Branch names are recorded; a commit SHA is not represented as known unless actually obtained. No target project builds, real-game tests, repo-wide audits or A/B improvements were claimed. See [`references/RESEARCH_LEDGER.md`](#apex-references-research-ledger-md).

#### Design principles
Do not copy vulnerable or messy patterns merely because they appear in an existing project. Use readable, scoped changes; record conflicts and unknowns; reproduce failures; verify outcomes; return high-quality working code where project tools permit.

<a id="apex-manifest-json"></a>

## Manifest

**Embedded original path:** `MANIFEST.json`

`````json
{
  "name": "PROJECT APEX",
  "created_utc": "2026-10-24T12:28:08.275411+00:00",
  "description": "Modular evidence-first engineering skill package",
  "files": [
    {
      "path": "AGENTS.md",
      "sha256": "6f959b5fe7275f7122d7daf72bc0047d55d5d3e61be979c591dd836353fb8859",
      "bytes": 465
    },
    {
      "path": "README.md",
      "sha256": "bea36617dbf89ecb014aa1f53a43f431e8e8e3b9b61a342f20f5f8a5d4ef6717",
      "bytes": 2392
    },
    {
      "path": "SKILL.md",
      "sha256": "d820544a4fb5ca45248287a430a5efe9ef2c8bcbeadfaee77c4fef4d253b960c",
      "bytes": 4523
    },
    {
      "path": "evaluations/CASES.md",
      "sha256": "f885044084a9e7881d9a665c0c73149b792d41a7322f139051d5397fa6ac8073",
      "bytes": 1275
    },
    {
      "path": "evaluations/RUBRIC.md",
      "sha256": "11cdc412b7e1d422db1332e6d728be62c92f1b27f5836c028f9b44e0196f318e",
      "bytes": 2433
    },
    {
      "path": "evaluations/SCENARIOS.json",
      "sha256": "72b134c9a3adbb975717cb5f58ec3f6c91aae113263681268dfc82204e32866e",
      "bytes": 5241
    },
    {
      "path": "evaluations/TRACE_SCHEMA.json",
      "sha256": "1910ee96a85e82c214ad11887c8af74c7ce43c6e637354c4a8883c1b8b28ca91",
      "bytes": 532
    },
    {
      "path": "evaluations/evaluate_trace.py",
      "sha256": "22b840cd5df8a1b1521befff2533ef0b498abd7d12e0d05269fcdb177a017b4b",
      "bytes": 1250
    },
    {
      "path": "playbooks/AUTHORIZED_BINARY_RESEARCH.md",
      "sha256": "f607031d5b569335fed91cdb42e738e19dd633e6002dad2787188707bd29be8e",
      "bytes": 951
    },
    {
      "path": "playbooks/BUILD_AND_RELEASE.md",
      "sha256": "9c67ce920bf85fa0f48325ac0bc503ce94e898030c611282ffe1290d91c056d6",
      "bytes": 721
    },
    {
      "path": "playbooks/CONFIG_LIFECYCLE.md",
      "sha256": "d97c2d58b2e443b4819fbaa0a89aad0d7ef5fe155c2af526755aae80fe04c7e1",
      "bytes": 797
    },
    {
      "path": "playbooks/CRASH_INVESTIGATION.md",
      "sha256": "81c63deca06a081a4a892039e244605d5455143ac83717c8afe5fdbc62a02813",
      "bytes": 1178
    },
    {
      "path": "playbooks/IMGUI_DESIGN_REPAIR.md",
      "sha256": "75367ac6fb5f558b2b9601823364211f4a4d323c0aaa8d64ffbc5065ffc06db7",
      "bytes": 947
    },
    {
      "path": "references/CODE_STANDARDS.md",
      "sha256": "acdbc28b26575ff932fdb27cde79c919e487462d1087399861ab527cc2d0cb4b",
      "bytes": 1557
    },
    {
      "path": "references/DEBUGGING.md",
      "sha256": "eccef32fb0fa49f6360ad336d0e2adc386894734c1339531976aaf9b85d59d08",
      "bytes": 1561
    },
    {
      "path": "references/DESIGN_SYSTEM.md",
      "sha256": "1a43d95c2d9e829ba13a7e1cefce0e5f17af28a5cf1fcd25fece824deefd0a11",
      "bytes": 2080
    },
    {
      "path": "references/EVIDENCE_POLICY.md",
      "sha256": "63a6180a8f42e03c1aaabb20660cf9ac98cbb1aa162f7030a469cb3673199a17",
      "bytes": 2640
    },
    {
      "path": "references/EXPERT_SOURCES.md",
      "sha256": "14804904f0b06de0a656bc5b9c70c1c489d259e29da5b1493496e8991b201cba",
      "bytes": 4828
    },
    {
      "path": "references/QUALITY_GATES.md",
      "sha256": "5ba724949d8fc7eeeba455321e679b7a0b66b30299f498669cecfeb719b9a6e7",
      "bytes": 1696
    },
    {
      "path": "references/RESEARCH_LEDGER.md",
      "sha256": "0c4b79e9d18e76fda9f2829c2a8ac85406798392f6aa903de5a22940cc9e1af9",
      "bytes": 7191
    },
    {
      "path": "references/RESEARCH_SCOPE.md",
      "sha256": "60a681ecd8fde17a4686fc22bba659cd08ce662a52a0b4fb6ddcfdad3ffab43f",
      "bytes": 833
    },
    {
      "path": "references/TOOLCHAINS.md",
      "sha256": "bed574ef3ec28ff80e8c27de2369c4bd01be393bd94528277c07e332d8f7bd7f",
      "bytes": 1534
    },
    {
      "path": "references/UPSTREAM_IMPLEMENTATIONS.md",
      "sha256": "8960cfc9823513fd056c267875df07b25b7ac7f37f4d25a26e7870e13fd414a0",
      "bytes": 2919
    },
    {
      "path": "references/WORKFLOW.md",
      "sha256": "25da88fc6435d16636876051b3b7af8786dabab18892be3bc718f2a1d0c5da2e",
      "bytes": 2626
    },
    {
      "path": "routing/ROUTING.md",
      "sha256": "c97eee91199383673dda46d7121efca0558e3d66eeac1bfe12164ac003d1c854",
      "bytes": 1604
    },
    {
      "path": "routing/router.py",
      "sha256": "46aee94174ebb7c5ceaaa4e05f74d1507e27caf8404c929562e2928ae509ade4",
      "bytes": 4430
    },
    {
      "path": "routing/rules.json",
      "sha256": "2a1d82cd9c8cac8628e01bbc448ef328ffdae0c50806b5c9a205ce81d5c4cc9f",
      "bytes": 3212
    },
    {
      "path": "scripts/run_checks.py",
      "sha256": "a3840dc781b83ea108b289bf5cd99ec245891f705f0a5c790fa984fc3068a599",
      "bytes": 1938
    },
    {
      "path": "scripts/validate_library.py",
      "sha256": "f99379d0275ff68d1c7d9a4e0010c7d27c094e5f7710e29fe6a33e83156e807e",
      "bytes": 2410
    },
    {
      "path": "skills/accessibility/SKILL.md",
      "sha256": "dbfc82e06b05b2a2c248958a19692a576ce002a67f4083d3089a83d06746e265",
      "bytes": 4090
    },
    {
      "path": "skills/android-jni-ndk/SKILL.md",
      "sha256": "d808e97da415d259423ee5b340fea1db4efe17d644d07a5552ba3d26233a7751",
      "bytes": 4234
    },
    {
      "path": "skills/anti-slop/SKILL.md",
      "sha256": "8d0f7af79d75eabaf0b13fb7ed7252ea29bfa41bdc18cc3d6e33657d96041287",
      "bytes": 4343
    },
    {
      "path": "skills/architecture/SKILL.md",
      "sha256": "000e8b3449062019e42ddf90b9b7d66f4fa610f9d8b15d117c1961f02997ce2e",
      "bytes": 4138
    },
    {
      "path": "skills/authorized-research/SKILL.md",
      "sha256": "f377303681386825b530d10ce01216ddc28c0a0f949e4824b833c815b7ceee6c",
      "bytes": 4590
    },
    {
      "path": "skills/build-toolchains/SKILL.md",
      "sha256": "135347f321897013116ad9bf01c15f5970cd3bd46f9752ad424c5871628b285a",
      "bytes": 4309
    },
    {
      "path": "skills/code-review/SKILL.md",
      "sha256": "541b01815f7033a960e6a16fdc5fd03bfa9c9d68766a1a10f302fd6763856438",
      "bytes": 4098
    },
    {
      "path": "skills/compatibility/SKILL.md",
      "sha256": "dd632b82b5bc0ea0614c95072d90b549dc2ba6cdff2bc48539900af3cbdde89a",
      "bytes": 4310
    },
    {
      "path": "skills/concurrency/SKILL.md",
      "sha256": "6fa3ec97c4a2c6d6856a3fac5ebbbf42117e0a7f27aa28758cf0c705228c73db",
      "bytes": 4233
    },
    {
      "path": "skills/configuration/SKILL.md",
      "sha256": "6e5f24388f16c37b6a164548d43d6e661fe0ff61bbe2cc06fecb5b38329688f6",
      "bytes": 4320
    },
    {
      "path": "skills/controlled-patching/SKILL.md",
      "sha256": "d5e06410fa6b268b58b96c13dbda90f3b477160fc09c86929318aa88dedfbac0",
      "bytes": 4440
    },
    {
      "path": "skills/cpp-engineer/SKILL.md",
      "sha256": "68875d536601544a74dc06d0fbffff62f57890baf543c536a7694a82dd70639b",
      "bytes": 4628
    },
    {
      "path": "skills/csharp-engineer/SKILL.md",
      "sha256": "8e85191618066d7621cc805b6f01d29a170611d60261996ecd4edcbbf9dc0318",
      "bytes": 4480
    },
    {
      "path": "skills/dear-imgui/SKILL.md",
      "sha256": "84af88ebe806f7ea8b81a1ff5412a1ac0b173aa74811ed712f1ee997611a99c8",
      "bytes": 4484
    },
    {
      "path": "skills/memory-safety/SKILL.md",
      "sha256": "ca09ba7e5fd75699e930f033ee932b72e36bb69474f5c568bcdcd6d45c5f56c0",
      "bytes": 4390
    },
    {
      "path": "skills/native-rendering/SKILL.md",
      "sha256": "34c4d4b4362e20261f49604db9e6d178957ffa6d7154bd4b8a7484baa3a25ffa",
      "bytes": 4138
    },
    {
      "path": "skills/observability/SKILL.md",
      "sha256": "d2006cea8b7663de6514b8e7fb6b6b2c99c30611819783be368294dbf5e79f41",
      "bytes": 4103
    },
    {
      "path": "skills/offset-validation/SKILL.md",
      "sha256": "ec9d61e09176ce7f8a9c5f4a586ce70cb5ae32b55701f0ea56be54c72e2a69cc",
      "bytes": 4401
    },
    {
      "path": "skills/open-source-maintenance/SKILL.md",
      "sha256": "1a3a4d38f9afc25d91058d4dedef2abcceb88cc633e5931ed4618a66790ac5dd",
      "bytes": 4190
    },
    {
      "path": "skills/performance/SKILL.md",
      "sha256": "6ef40c091d78f530a39bdc17864ca40863c882e621eac5ac43397c0e25fa3111",
      "bytes": 4197
    },
    {
      "path": "skills/release-integration/SKILL.md",
      "sha256": "42c4c1bb6ca317f77e171bedee16f64cbf18000550f9af7426106d0308b9cb7b",
      "bytes": 4158
    },
    {
      "path": "skills/reliability/SKILL.md",
      "sha256": "d4b7cdf0c43186d95be52bc4ef07417dfd8205a27f658797b99ffc7135805546",
      "bytes": 4285
    },
    {
      "path": "skills/root-cause-debugging/SKILL.md",
      "sha256": "df6039620ecac73319f71009cae553181d6374aa506ba440a11a424a3ba5d90e",
      "bytes": 4347
    },
    {
      "path": "skills/security-boundaries/SKILL.md",
      "sha256": "c67aa76e07f2ee22587107198bea581bc27d9ff25e00e91306e4646924a0b14a",
      "bytes": 4296
    },
    {
      "path": "skills/technical-research/SKILL.md",
      "sha256": "96fb40fe8f6e807217010f67e331deffaa6f681f4712a84f18fe13f8e126a951",
      "bytes": 4344
    },
    {
      "path": "skills/testing-qa/SKILL.md",
      "sha256": "b8b90a2dcc0d07de7fb5f5a771bd8b184dcb82ad68a2a9ee2a2ba2feeabd6ac9",
      "bytes": 4312
    },
    {
      "path": "skills/ui-ux/SKILL.md",
      "sha256": "82e9a5126073c08e4c355f60dc78c894f890c0aa5bc02317c575740819b88a6d",
      "bytes": 4300
    },
    {
      "path": "templates/ADR.md",
      "sha256": "978f1661c2124ab351203b01f68142e6d81e5ea369f440809e069103e4930c34",
      "bytes": 265
    },
    {
      "path": "templates/CHANGE_PLAN.md",
      "sha256": "46d4f506332f4a10ca20e06ec30c5330b125181ddaefe080903b852d73d8f62e",
      "bytes": 506
    },
    {
      "path": "templates/CODE_REVIEW.md",
      "sha256": "ee9eaffd2a1622c0f4d5ea3eed333ff21028d316c60bc3407314b4387fb97100",
      "bytes": 266
    },
    {
      "path": "templates/COMPATIBILITY_MATRIX.md",
      "sha256": "a810083406962c7251f1a1d8e6bbe973a9d594aa39fcf69c9bd5bcfd7b969fb2",
      "bytes": 297
    },
    {
      "path": "templates/EVIDENCE_RECORD.md",
      "sha256": "33e6a84aafe019ffe62ec0415e507dfdebbf8765457029bab690c43750f2d654",
      "bytes": 551
    },
    {
      "path": "templates/UI_AUDIT.md",
      "sha256": "9ffe7cab44d36e30d7fb42f75dbc8ff8d7f00753e40e96fec00b3421ddf0e923",
      "bytes": 361
    },
    {
      "path": "templates/VERIFICATION_REPORT.md",
      "sha256": "860ed68aa8cbcd2726ce4c49f801fafe4b341d80b8a178787dfb3f98c6478a8c",
      "bytes": 489
    },
    {
      "path": "tests/fixtures/safe_math.cpp",
      "sha256": "12d44c0743a00e57d1e1a860678c2c1516bd581aaaa874e15a32ca1ed9289d6e",
      "bytes": 675
    },
    {
      "path": "tests/test_routing.py",
      "sha256": "568487faaf733a412a6a9df9af7b22367ac24f35bd7c055fa79dacdf6228dbfe",
      "bytes": 1761
    },
    {
      "path": "tests/test_trace_scoring.py",
      "sha256": "b21554d742989ac1e917f63cef34c4b52ce45406d1884a33ada831a498aec7e7",
      "bytes": 687
    },
    {
      "path": "validation_report.json",
      "sha256": "43eeb85354435b19740607765996f15f1a68706faba3690ec478ce4170f095e2",
      "bytes": 2609
    }
  ],
  "scope": "The source research ledger records incomplete repository investigation. Tests cover package mechanics, not target repositories or agent effectiveness."
}
`````

<a id="apex-validation-report-json"></a>

## Validation Report

**Embedded original path:** `validation_report.json`

`````json
{
  "generated_utc": "2026-10-24T12:27:51.818773+00:00",
  "scope": "Library static checks, router tests, tiny owned C++ fixture only; NOT target GitHub repos or agent A/B study",
  "results": [
    {
      "name": "Static library validation",
      "command": [
        "/opt/pyvenv/bin/python3",
        "/mnt/data/project-apex/scripts/validate_library.py"
      ],
      "status": "PASS",
      "exit_code": 0,
      "output": "VALIDATION PASS: 27 specialists, 14 routes, 15 unrun agent scenarios, required headings and dependencies present\n"
    },
    {
      "name": "Python unittest suite",
      "command": [
        "/opt/pyvenv/bin/python3",
        "-m",
        "unittest",
        "discover",
        "-s",
        "tests",
        "-v"
      ],
      "status": "PASS",
      "exit_code": 0,
      "output": "test_abi (test_routing.RoutingTests.test_abi) ... ok\ntest_all_fifteen_routing_scenarios (test_routing.RoutingTests.test_all_fifteen_routing_scenarios) ... ok\ntest_build_precedes_language (test_routing.RoutingTests.test_build_precedes_language) ... ok\ntest_configs (test_routing.RoutingTests.test_configs) ... ok\ntest_crash_precedes_imgui_redesign (test_routing.RoutingTests.test_crash_precedes_imgui_redesign) ... ok\ntest_default (test_routing.RoutingTests.test_default) ... ok\ntest_offset_scope (test_routing.RoutingTests.test_offset_scope) ... ok\ntest_rules_point_to_present_skills (test_routing.RoutingTests.test_rules_point_to_present_skills) ... ok\ntest_visual (test_routing.RoutingTests.test_visual) ... ok\ntest_complete_review (test_trace_scoring.TraceTests.test_complete_review) ... ok\ntest_fabricated_claim_is_rejected (test_trace_scoring.TraceTests.test_fabricated_claim_is_rejected) ... ok\ntest_score_requires_independent_review (test_trace_scoring.TraceTests.test_score_requires_independent_review) ... ok\n\n----------------------------------------------------------------------\nRan 12 tests in 0.009s\n\nOK\n"
    },
    {
      "name": "Owned C++ fixture compilation",
      "command": [
        "/usr/bin/g++",
        "-std=c++17",
        "-Wall",
        "-Wextra",
        "-Werror",
        "/mnt/data/project-apex/tests/fixtures/safe_math.cpp",
        "-o",
        "/tmp/tmphxoxpmyu/safe_math_test"
      ],
      "status": "PASS",
      "exit_code": 0,
      "output": ""
    },
    {
      "name": "Owned C++ fixture execution",
      "command": [
        "/tmp/tmphxoxpmyu/safe_math_test"
      ],
      "status": "PASS",
      "exit_code": 0,
      "output": ""
    }
  ],
  "agent_efficacy": "NOT_TESTED",
  "github_project_builds": "NOT_RUN"
}
`````
