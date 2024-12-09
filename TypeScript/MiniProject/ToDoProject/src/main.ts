import { todos } from "./store/todos";
import { Todo } from "./types/todos";
import { elements } from "./utils/elements";

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

  const newTodo = {
    id: todos.length + 1,
    title: value,
    completed: false,
  } satisfies Todo;

  todos.push(newTodo);
  elements.todoInput.value = "";
  console.log(todos);
};
elements.errorPopup.closeButton.onclick = hideError;

elements.clearTodoInputButton.onclick = () => {
  elements.todoInput.value = "";
  hideError();
};
