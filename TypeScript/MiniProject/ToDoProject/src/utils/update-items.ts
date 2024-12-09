import { elements } from "./elements";

/**
 * Updates the count of incomplete (unchecked) todo items
 * and displays it in the DOM.
 */
export function updateItemsLeft(): void {
  // Count incomplete todos (unchecked checkboxes)
  const itemsLeft = elements.todoContainer.querySelectorAll("input:not(:checked)").length;

  // Update the displayed count
  elements.itemsLeft.textContent = `${itemsLeft} item${itemsLeft === 1 ? "" : "s"} left`;
}
