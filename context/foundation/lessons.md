# Lessons Learned

> Append-only register of recurring rules and patterns. Re-read at start by /10x-frame, /10x-research, /10x-plan, /10x-plan-review, /10x-implement, /10x-impl-review.

## Standardize API Error Responses on RFC 9457 ProblemDetail

- **Context**: REST endpoints and their error-handling layer (`com.example.zgrani.*` controllers and services that surface HTTP errors).
- **Problem**: A 3-run calibration experiment on the same task (`GET /api/greetings/{id}`, 404 case) showed the shape is not stable without a rule — Run 1 produced a custom `ProblemDetail` via a hand-written `@RestControllerAdvice`, Run 2 produced the classic `{timestamp, status, error, path}` default. Same task, two incompatible response shapes.
- **Rule**: API error responses must use RFC 9457 `ProblemDetail` (enable `spring.mvc.problemdetails.enabled: true`); map new domain exceptions through a single `@ExceptionHandler` returning `ProblemDetail.forStatusAndDetail(...)`, never a hand-rolled error format.
- **Applies to**: implement, impl-review
