"use client";

import { useState, type FormEvent } from "react";
import { motion } from "framer-motion";
import { fetchDualResponses } from "@/services/chatService";
import { Bot, Sparkles, Send, AlertTriangle } from "lucide-react";
import { cn } from "@/lib/utils";

type ProviderKey = "gemini" | "openai";

const providerCopy: Record<ProviderKey, { label: string; icon: typeof Bot }> = {
  gemini: { label: "Gemini", icon: Sparkles },
  openai: { label: "OpenAI", icon: Bot },
};

function ResponseCard({
  provider,
  response,
  error,
  loading,
}: {
  provider: ProviderKey;
  response: string | null;
  error: string | null;
  loading: boolean;
}) {
  const Icon = providerCopy[provider].icon;

  return (
    <div className="rounded-xl border border-zinc-200/60 bg-white/70 p-4 dark:border-zinc-800/60 dark:bg-zinc-900/40">
      <div className="mb-2 flex items-center gap-2">
        <Icon className="h-4 w-4 text-indigo-500" />
        <span className="text-sm font-semibold">{providerCopy[provider].label}</span>
      </div>

      <div className="rounded-lg bg-zinc-50 p-3 text-sm leading-relaxed text-zinc-700 dark:bg-zinc-800 dark:text-zinc-200">
        {loading && !response && !error && (
          <span className="text-zinc-500 dark:text-zinc-400">Waiting for response…</span>
        )}

        {!loading && !response && !error && (
          <span className="text-zinc-500 dark:text-zinc-400">
            Ask a question to see {providerCopy[provider].label} respond.
          </span>
        )}

        {error && (
          <span className="flex items-center gap-2 text-rose-600 dark:text-rose-400">
            <AlertTriangle className="h-4 w-4" />
            {error}
          </span>
        )}

        {response && !error && <p>{response}</p>}
      </div>
    </div>
  );
}

export function MultiAiChat({ className = "" }: { className?: string }) {
  const [prompt, setPrompt] = useState("");
  const [responses, setResponses] = useState<{ gemini: string | null; openai: string | null }>({
    gemini: null,
    openai: null,
  });
  const [errors, setErrors] = useState<{ gemini: string | null; openai: string | null }>({
    gemini: null,
    openai: null,
  });
  const [loading, setLoading] = useState(false);
  const [lastPrompt, setLastPrompt] = useState<string | null>(null);

  const submitPrompt = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const clean = prompt.trim();
    if (!clean) return;

    setLoading(true);
    setErrors({ gemini: null, openai: null });
    setResponses({ gemini: null, openai: null });
    setLastPrompt(clean);

    const result = await fetchDualResponses(clean);
    setResponses({ gemini: result.gemini, openai: result.openai });
    setErrors({ gemini: result.geminiError, openai: result.openaiError });
    setLoading(false);
  };

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className={cn(
        "rounded-xl border border-zinc-200/60 bg-white/60 p-6 backdrop-blur dark:border-zinc-800/60 dark:bg-zinc-900/40",
        className
      )}
    >
      <div className="flex items-start justify-between gap-4">
        <div>
          <h3 className="text-lg font-semibold">Dual AI Chat</h3>
          <p className="text-sm text-zinc-600 dark:text-zinc-400">
            Send your message once to get a reply from multiple AI models
          </p>
        </div>
        <Send className="h-4 w-4 text-indigo-500" />
      </div>

      <form onSubmit={submitPrompt} className="mt-4 space-y-3">
        <div className="flex flex-col gap-3 md:flex-row">
          <input
            className="w-full rounded-lg border border-zinc-300 bg-zinc-50 p-3 text-sm dark:border-zinc-700 dark:bg-zinc-800"
            placeholder="Ask anything…"
            value={prompt}
            onChange={(e) => setPrompt(e.target.value)}
            disabled={loading}
          />
          <button
            type="submit"
            disabled={loading}
            className="inline-flex items-center justify-center rounded-lg bg-indigo-600 px-4 py-2 text-sm font-medium text-white transition hover:bg-indigo-700 disabled:opacity-60 dark:bg-indigo-500 dark:hover:bg-indigo-600"
          >
            {loading ? "Sending…" : "Send"}
          </button>
        </div>

        {lastPrompt && (
          <p className="text-xs text-zinc-500 dark:text-zinc-400">
            Prompt: <span className="font-medium text-zinc-700 dark:text-zinc-200">{lastPrompt}</span>
          </p>
        )}
      </form>

      <div className="mt-4 grid gap-4 md:grid-cols-2">
        <ResponseCard
          provider="gemini"
          response={responses.gemini}
          error={errors.gemini}
          loading={loading}
        />
        <ResponseCard
          provider="openai"
          response={responses.openai}
          error={errors.openai}
          loading={loading}
        />
      </div>
    </motion.div>
  );
}
