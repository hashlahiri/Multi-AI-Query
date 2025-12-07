# Multi-AI Query — Frontend (Next.js)

React/Next.js UI that compares Gemini and OpenAI answers side-by-side, plus helper widgets (weather and JSON key checker). Designed to talk to a backend running on `localhost:3000`.

## Requirements
- Node 18+
- npm (comes with Node)

## Quick start
```bash
npm install
npm run dev      # http://localhost:3000
```

## Environment
Create `.env.local` (optional). Defaults are chosen for local dev.
```
NEXT_PUBLIC_API_BASE_URL=http://localhost:9000/personaldashboard   # weather/json services
NEXT_PUBLIC_CHAT_API_BASE_URL=http://localhost:3000                # gemini/openai chat
```

## Backend expectations
- `POST /api/gemini` with `{ "prompt": "..." }` → returns JSON containing the reply in one of: `reply` | `response` | `message` | `result`.
- `POST /api/openai` with `{ "prompt": "..." }` → same shape as above.
- Weather: `GET /api/currentWeather/cityStateCountry?city=florence&countryCode=IT`
- JSON key check: `POST /api/json/ifKeyExists` with `{ payload, parentKey, childKey }`

Adjust `NEXT_PUBLIC_*` URLs if your backend differs.

## Scripts
- `npm run dev` — start dev server with hot reload
- `npm run build` — production build
- `npm start` — run the built app
- `npm run lint` — eslint check

## Notable UI
- Dual AI Chat (`components/sections/ChatSection.tsx`)
- Weather widget (`components/sections/WeatherSection.tsx`)
- JSON key checker (`components/sections/JsonKeySection.tsx`)

