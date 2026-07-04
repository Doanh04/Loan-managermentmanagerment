import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [
    react({
      jsxRuntime: "automatic"
    })
  ],

  resolve: {
    dedupe: ["react", "react-dom"]
  },

  optimizeDeps: {
    include: [
      "react",
      "react-dom",
      "react/jsx-runtime",
      "react/jsx-dev-runtime"
    ]
  },

  server: {
    port: 5173,
    strictPort: false
  }
});