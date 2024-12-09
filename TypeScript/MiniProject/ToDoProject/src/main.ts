import { todos } from "./store/todos";
import { Todo } from "./types/todos";
import { elements } from "./utils/elements";
import { renderTodos } from "./utils/render-todos";

/**
 * Hides the error popup by adding a `hidden` class.
 */
function hideError(): void {
  elements.errorPopup.container.classList.add("hidden");
}

/**
 * Displays the error popup with a given message.
 * @param message - The error message to display.
 */
function showError(message: string): void {
  elements.errorPopup.container.classList.remove("hidden");
  elements.errorPopup.message.textContent = message;
}

// Event handler for form submission
elements.form.onsubmit = (event) => {
  event.preventDefault();

  // Get and trim the input value
  const value = elements.todoInput.value.trim();
  if (value === "") {
    showError("Todo cannot be empty");
    return;
  }

  // Create a new todo and add it to the list
  const newTodo = {
    id: todos.length + 1,
    title: value,
    completed: false,
  } satisfies Todo;

  todos.push(newTodo);

  // Clear the input and re-render todos
  elements.todoInput.value = "";
  renderTodos();
};

// Event handler for closing the error popup
elements.errorPopup.closeButton.onclick = hideError;

// Event handler for clearing the todo input field
elements.clearTodoInputButton.onclick = () => {
  elements.todoInput.value = "";
  hideError();
};

// Event handler for clearing completed todos
elements.clearCompleted.onclick = () => {
  const incompleteTodos = todos.filter((todo) => !todo.completed);
  todos.splice(0, todos.length, ...incompleteTodos);
  renderTodos();
};
