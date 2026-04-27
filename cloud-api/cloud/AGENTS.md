# cloud-api/cloud/AGENTS.md

## Backend Scope

This directory contains the Spring Boot backend.

## Stack

- Java
- Spring Boot
- Maven
- Spring Data JPA
- PostgreSQL

## Backend Standards (Spring Boot)

### Architecture

Use layered structure:

```text
Controller -> Service -> Repository
```

### Rules

- Keep controllers thin
- Put business logic in services
- Persistence logic belongs in Repositories
- Use DTOs for requests/responses
- Validate inputs
- Reuse existing patterns

### Java Style

- Follow existing package structure
- Use constructor injection
- Prefer clear method names
- Keep methods reasonably small

### Security

- Preserve auth/authorization flow
- Never expose secrets
- Respect existing security config

### Spring Data Jpa

- Prefer query by method name convention when writing new queries
- Prefer JPQL query over SQL query


### Error Handling

- Use existing global exception handling if present
- Return consistent HTTP responses
- Do not expose stack traces to clients

---

## Database Standards

### Rules

- Prefer schema edits over migrations
- Explicitly ask permission for schema edits
- Never drop tables/data unless explicitly requested
- Keep schema backward compatible when possible
- Add indexes only when justified
- Use PostgreSQL-compatible SQL

### Data Safety

- Treat production data as sensitive
- Avoid destructive migrations
- Preserve existing constraints unless intentionally changed

---

## Before Editing Code

### Backend

Inspect:

- controller
- service
- repository
- entity
- DTOs
- tests

### For Database Changes

Check:

- existing migrations
- foreign keys
- indexes
- data compatibility

---

## Validate

- Maven build passes
- Tests pass
- App starts successfully
- Queries remain valid