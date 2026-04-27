# AGENTS.md

## Project Overview

This repository contains a full-stack business web application built with:

- **Frontend:** Vue 3 + Vuetify
- **Backend:** Spring Boot + Maven
- **Database:** PostgreSQL

The frontend consumes REST APIs exposed by the backend.

---

## Main Goals for AI Agents

When working in this repository:

1. Make **small, safe, production-ready changes**
2. Preserve existing architecture and naming conventions
3. Reuse existing code before creating new code
4. Avoid unnecessary refactors unless explicitly requested
5. Read surrounding files before editing
6. Keep code maintainable and easy to review

---

## Repository Structure

```text
/cloud-ui                                Vue application
/cloud-api/cloud                         Spring Boot application
/cloud-api/cloud/src/main/resources      SQL scripts / migrations
/diagram                                 UML class diagram
```

## Instruction Priority

When working inside a subdirectory, also read:

- `/cloud-ui/AGENTS.md`
- `/cloud-api/cloud/AGENTS.md`

Nearest file takes precedence over root instructions.

---

## Global Rules

- Make minimal, safe changes
- Preserve existing architecture
- Do not break API contracts
- Reuse existing code before creating new code
- Avoid unnecessary refactors
- Never expose secrets
- Ask before destructive changes

## Cross-Stack Rules

If changing frontend and backend together:

- Keep request/response contracts aligned
- Update DTOs/types consistently
- Preserve authentication flow
- Verify CORS/security compatibility
- Document breaking changes










## REST API Conventions

Use consistent REST patterns:

```text
GET    /api/organizations
GET    /api/organizations/{id}
POST   /api/organizations
PUT    /api/organizations/{id}
PATCH  /api/organizations/{id}
DELETE /api/organizations/{id}
```

### Responses

- Use proper HTTP status codes
- Return JSON
- Keep response shapes consistent

---

## Authentication / Security

- Preserve existing auth flow which uses JWT
- Do not weaken authorization checks
- Never hardcode secrets
- Never commit credentials
- Sanitize and validate user input
- Respect CORS/security configuration already in place

---

## Preferred Change Strategy

1. Understand existing implementation
2. Make smallest effective change
3. Keep backward compatibility
4. Add/update tests if relevant
5. Explain notable tradeoffs

---

## Avoid Unless Requested

- Large refactors
- Renaming many files
- Rewriting working modules
- Introducing new frameworks/libraries
- Schema-breaking DB changes
- Style-only churn

---

## If Requirements Are Unclear

Ask clarifying questions before making architectural or destructive changes.

---

## Project-Specific Notes

Replace placeholders when known:

```text
Java version: 17
Spring Boot version: 3.4.2
Vue version: 3.5.13
Vuetify version: 3.8.1
Auth method: JWT
Build commands:
Frontend run command: npm run dev
Backend run command: spring-boot:run
```

---

## Example Commands

```bash
# frontend
npm install
npm run dev

# backend
mvn clean
mvn spring-boot:run
```

---

## Final Instruction to AI Agents

Be practical, conservative, and precise.
Improve the codebase without creating unnecessary complexity.
