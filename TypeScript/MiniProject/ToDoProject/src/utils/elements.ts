/**
 * Retrieves an HTML element by query string and casts it to the specified type.
 * Throws an error if the element is not found.
 * @param query - CSS selector string to locate the element.
 * @returns The matched element cast as the specified type.
 * @throws Error if the element is not found.
 */
export function getElement<T extends HTMLElement>(query: string): T {
  const element = document.querySelector<T>(query);
  if (element === null) throw new Error(`Element not found: ${query}`);
  return element;
}

/**
 * Object that contains references to various HTML elements used in the application.
 */
export const elements = {
  form: getElement<HTMLFormElement>("form"),
  todoInput: getElement<HTMLInputElement>("#todo-input"),
  clearTodoInputButton: getElement<HTMLInputElement>("#clear-todo-input"),
  todoContainer: getElement<HTMLDataListElement>("#todo-container"),
  itemsLeft: getElement<HTMLParagraphElement>("#items-left"),
  clearCompleted: getElement<HTMLButtonElement>("#clear-completed"),
  errorPopup: {
    container: getElement<HTMLDivElement>("#error-popup"),
    message: getElement<HTMLSpanElement>("#error-message"),
    closeButton: getElement<HTMLButtonElement>("#close-error-popup"),
  },
};
