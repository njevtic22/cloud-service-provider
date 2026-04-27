# /cloud-ui/AGENTS.md

## Frontend Scope

This directory contains the Vue frontend.

## Stack

- Vue 3
- Vuetify
- Vue Router
- Pinia
- Axios

## Project structure

- `./src/components` folder contains components which are mostly reusable
- `./src/environment` folder contains files related to current app environment (development, production, etc...)
- `./src/pages` folder contains pages which are used by vue router
- `./src/router` folder contains files describing router config and behavior
- `./src/stores` folder contains files related to Pinia store
- `./src/styles` folder contains global styles
- `./src/util` folder contains utility functions

## Rules

- Prefer Composition API
- Use `<script setup>` for new components
- Use Vuetify components
- Keep UI responsive
- Reuse shared components
- Keep components small and maintainable
- Keep spacing, typography, and layout consistent
- Do not introduce a second UI framework unless requested

## API Integration

- Use existing API service layer
- Handle loading / error / empty states
- Do not hardcode backend URLs
- Prefer making API calls inside methods of Pinia state management

### State Management

When using Pinia:

- Reuse existing stores
- Keep API logic outside components when possible
- Avoid duplicating state

## Before Editing

Inspect:

- router setup
- stores
- reusable components
- composables
- existing page patterns

## Validate

- Build passes
- No lint errors
- UI renders correctly

## After file changes

Execute prettier code formatter on changed file

### Frontend Safety

- Do not break existing routes
- Do not remove props/events used elsewhere
- Check for reactive side effects when editing watchers/computed values
