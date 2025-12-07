import "./globals.css";
import type { Metadata } from "next";
import { Topbar } from "@/components/layout/Topbar";

export const metadata: Metadata = {
  title: "Multi Ai Query",
  description: "Multi AI Query by Chanakya Lahiri"
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body className="min-h-screen antialiased">
        <div className="flex min-h-screen flex-col">
          <Topbar />
          <main className="flex-1 p-6 md:p-8">{children}</main>
        </div>
      </body>
    </html>
  );
}
