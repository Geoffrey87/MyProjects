import { elements } from "./elements";

export function setFetchState(state: "pending" | "sucess" | "error"): void {
  switch (state) {
    case "pending":
      elements.button.disabled = true;
      elements.loadingSpinner.classList.remove("hidden");
      return;
    case "sucess":
      elements.button.disabled = false;
      elements.loadingSpinner.classList.add("hidden");
      return;
    case "error":
      elements.button.disabled = false;
      elements.loadingSpinner.classList.add("hidden");
      elements.state.innerHTML = `<p class="text-red-500 text-center">Error! Please try again. </p>`;
  }
}
