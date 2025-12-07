import type { RawWeatherApiResponse, WeatherData } from "@/lib/types";
import { useEffect, RefObject } from 'react';

export function cn(...classes: Array<string | undefined | null | false>) {
  return classes.filter(Boolean).join(" ");
}

const normaliseBase = (base?: string) =>
  (base && base.trim() !== "" ? base : "http://localhost:9000/multi-ai-query")
    .replace(/\/$/, ""); // remove trailing slash



/** Unified URL builder – always adds /api segment */
export function buildBackendUrl(
  base: string | undefined,
  endpoint: string
): string {
  const root = normaliseBase(base);
  const clean = endpoint.startsWith("/") ? endpoint.slice(1) : endpoint;
  return `${root}/api/${clean}`;
}

export function useClickOutside(
  ref: RefObject<HTMLElement>,
  handler: () => void
) {
  useEffect(() => {
    const listener = (e: MouseEvent | TouchEvent) => {
      if (!ref.current || ref.current.contains(e.target as Node)) return;
      handler();
    };
    document.addEventListener('mousedown', listener);
    document.addEventListener('touchstart', listener);
    return () => {
      document.removeEventListener('mousedown', listener);
      document.removeEventListener('touchstart', listener);
    };
  }, [ref, handler]);
}