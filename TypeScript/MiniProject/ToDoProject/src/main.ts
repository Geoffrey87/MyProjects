import { elements } from "./utils/elements";
import type { Todo } from "./types/todos";

const todos: Todo[] = [];

function hideError(): void {
  elements.errorPopup.container.classList.add("hidden");
}

function showError(message: string): void {
  elements.errorPopup.container.classList.remove("hidden");
  elements.errorPopup.message.textContent = message;
}

elements.form.onsubmit = (event) => {
  event.preventDefault();
  const value = elements.todoInput.value.trim();
  if (value === "") {
    showError("Todo cannot be empty");
    return;
  }
  elements.errorPopup.closeButton.onclick = hideError;
};
