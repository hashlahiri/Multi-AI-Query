type Provider = "gemini" | "openai";

const DEFAULT_BASE = "http://localhost:9000";
const CHAT_ENDPOINTS: Record<Provider, string> = {
  gemini: "/api/gemini/query",
  openai: "/api/openai/query",
};

type ChatResponse = {
  gemini: string | null;
  openai: string | null;
  geminiError: string | null;
  openaiError: string | null;
};

const normaliseBase = (base?: string) =>
  (base && base.trim() !== "" ? base.trim() : DEFAULT_BASE).replace(
    /\/$/,
    ""
  );

const buildChatUrl = (endpoint: string) => {
  const clean = endpoint.startsWith("/") ? endpoint : `/${endpoint}`;
  return `${normaliseBase(process.env.NEXT_PUBLIC_CHAT_API_BASE_URL)}${clean}`;
};

async function fetchProviderReply(
  provider: Provider,
  prompt: string
): Promise<string> {
  const res = await fetch(buildChatUrl(CHAT_ENDPOINTS[provider]), {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ prompt }),
  });

  if (!res.ok) {
    throw new Error(
      `${provider === "gemini" ? "Gemini" : "OpenAI"} request failed (${
        res.status
      })`
    );
  }

  const data = (await res.json()) as Record<string, unknown>;
  const candidate =
    (typeof data.reply === "string" && data.reply) ||
    (typeof data.response === "string" && data.response) ||
    (typeof data.message === "string" && data.message) ||
    (typeof data.result === "string" && data.result);

  if (candidate) return candidate;

  return JSON.stringify(data);
}

export async function fetchDualResponses(prompt: string): Promise<ChatResponse> {
  const [gemini, openai] = await Promise.allSettled([
    fetchProviderReply("gemini", prompt),
    fetchProviderReply("openai", prompt),
  ]);

  return {
    gemini: gemini.status === "fulfilled" ? gemini.value : null,
    openai: openai.status === "fulfilled" ? openai.value : null,
    geminiError:
      gemini.status === "rejected"
        ? gemini.reason?.message ?? "Gemini request failed"
        : null,
    openaiError:
      openai.status === "rejected"
        ? openai.reason?.message ?? "OpenAI request failed"
        : null,
  };
}
