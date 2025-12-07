# Multi-AI-Query

Multi-AI-Query is a work-in-progress web app that sends the same prompt to multiple AI chat models and lets you compare responses side by side. It is aimed at developers, researchers, and product teams who need to evaluate LLM providers quickly and consistently.

## Why it matters
- Evaluate different LLMs with the exact same prompt and context
- Spot hallucinations and style differences faster with a side-by-side view
- Iterate on prompts and model settings without juggling multiple tools

> Status: Early WIP. Core architecture and comparison flow are being built; some items below are planned.

## Features
- Single prompt → multiple models in one request
- Pluggable provider layer to add new AI vendors with a consistent interface
- Chat-style history per model so context carries through
- Side-by-side comparison grid with model name and latency
- Optional metadata: token usage/costs (planned)
- Session save/load to revisit comparisons (planned)
- Per-user defaults for providers, temperature, etc. (planned)

## Tech Stack
- Backend: Java + Spring Boot, Maven build
- Frontend: Next.js (React) + Tailwind CSS
- Data/transport: REST API returning normalized responses from each provider
- Secrets/config: environment variables for provider API keys

## Architecture
```text
+--------------------+      HTTP       +----------------------+
|     Frontend       | <-------------> |       Backend        |
| Next.js + Tailwind |                 | Spring Boot REST API |
+--------------------+                 +----------------------+
                                          | Provider Layer
                                          | (OpenAI, etc.)
                                          v
                                Dispatch same prompt to each
                                selected provider and return
                                normalized responses
```

Planned repository layout:
```text
multi-ai-query/
├─ backend/
│  ├─ src/main/java/com/yourname/multiaiquery/
│  │  ├─ controller/    # REST controllers
│  │  ├─ service/       # Core business logic
│  │  ├─ provider/      # Provider integrations
│  │  ├─ config/        # API keys, settings
│  │  └─ dto/           # Request/response DTOs
│  └─ pom.xml
└─ frontend/
   ├─ app/ or src/      # Next.js routes/pages
   ├─ components/       # UI components
   ├─ lib/              # API clients, hooks
   ├─ styles/           # Tailwind setup
   └─ package.json
```

## Getting started
The codebase is still being built. The steps below describe the intended setup once the services land in the repository.

Prerequisites
- Java 17+
- Maven 3.9+
- Node.js 18+ and npm (or pnpm/yarn)

Backend (Spring Boot)
1) Copy your provider keys into environment variables (example: `OPENAI_API_KEY`).
2) From `backend/`, install and run:
   ```bash
   mvn spring-boot:run
   ```
3) The API will expose endpoints such as `POST /api/v1/query` for comparison requests.

Frontend (Next.js)
1) From `frontend/`, install dependencies and start the dev server:
   ```bash
   npm install
   npm run dev
   ```
2) Configure the frontend to point to the backend (e.g., `NEXT_PUBLIC_API_BASE=http://localhost:8080`).
3) Open http://localhost:3000 and run a prompt against selected models.

## API example
```
POST /api/v1/query
Content-Type: application/json

{
  "prompt": "Explain the concept of a programmer",
  "providers": ["openai-gpt4", "provider-b-model-x"]
}

{
  "prompt": "Explain the concept of a programmer",
  "results": [
    {
      "provider": "openai-gpt4",
      "model": "gpt-4.x",
      "response": "A programmer designs, writes, and maintains software...",
      "latencyMs": 1240
    },
    {
      "provider": "provider-b-model-x",
      "model": "model-x",
      "response": "A programmer is someone who builds solutions step by step...",
      "latencyMs": 980
    }
  ]
}
```

![Multi-AI-Query Screenshot](MultiAiQueryScreen.png)

## Roadmap
- Implement core provider abstraction and add the first provider (e.g., OpenAI)
- Ship the basic Next.js UI with side-by-side cards and latency display
- Support streaming responses where providers allow it
- Add token usage and cost estimates per response
- Persist and reload comparison sessions
- Add theming improvements and polish the comparison UX




