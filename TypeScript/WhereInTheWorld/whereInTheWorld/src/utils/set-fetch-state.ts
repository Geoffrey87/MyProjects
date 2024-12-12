import { elements } from "./elements";

type Args =
  | {
      state: "pending";
    }
  | {
      state: "success";
    }
  | {
      state: "error";
      error: Error;
    };

/**
 * Updates the UI to reflect the current fetch state (pending, success, or error).
 *
 * - When the state is "pending", disables the submit button and shows a loading spinner.
 * - When the state is "success", enables the submit button, hides the loading spinner, and clears any error message.
 * - When the state is "error", enables the submit button, hides the loading spinner, and displays an error message.
 *
 * @param {Args} args - The state to update the UI to, which can be:
 *   - `"pending"`: Show the loading spinner and disable the button.
 *   - `"success"`: Hide the loading spinner and enable the button.
 *   - `"error"`: Show an error message and enable the button.
 * @returns {void} This function does not return anything; it directly manipulates the DOM.
 */
export function setFetchState(args: Args): void {
  switch (args.state) {
    case "pending":
      // Disable the button and show the loading spinner when fetching is in progress
      elements.button.disabled = true;
      elements.loadingSpinner.classList.remove("hidden");
      return;
    case "success":
      // Enable the button and hide the loading spinner when fetching is successful
      elements.button.disabled = false;
      elements.loadingSpinner.classList.add("hidden");
      elements.state.innerHTML = "";
      return;
    case "error":
      // Enable the button, hide the loading spinner, and show the error message
      elements.button.disabled = false;
      elements.loadingSpinner.classList.add("hidden");
      elements.state.innerHTML = `
      <p class="text-red-500 text-center">
      ${args.error.message}
      </p>
      `;
  }
}
