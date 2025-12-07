import { MultiAiChat } from "@/components/sections/ChatSection";

export default function HomePage() {
  return (
    <div className="min-h-screen">
      <div className="mx-auto flex max-w-6xl justify-center px-4 py-8 md:px-8">
        <MultiAiChat className="w-full" />
      </div>
    </div>
  );
}
