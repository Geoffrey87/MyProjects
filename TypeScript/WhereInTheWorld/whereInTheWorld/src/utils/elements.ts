/**
 * Utility function to retrieve an HTML element by its query selector.
 * If the element is not found, an error is thrown.
 *
 * @template T - The type of the HTML element to retrieve.
 * @param {string} query - The CSS selector used to find the element.
 * @returns {T} The HTML element matching the query selector.
 * @throws {Error} If the element is not found, an error is thrown with the query string.
 */
export function getElement<T extends HTMLElement>(query: string): T {
  const element = document.querySelector<T>(query);
  if (element === null) throw new Error(`Element not found: ${query}`);
  return element;
}

/**
 * A collection of commonly used HTML elements in the application.
 * Elements are retrieved using their respective query selectors.
 */
export const elements = {
  /**
   * The form element used for submitting search queries.
   */
  form: getElement<HTMLFormElement>("form"),

  /**
   * The input element used for entering search queries.
   */
  searchInput: getElement<HTMLInputElement>("#seach"),

  /**
   * The SVG element displayed as a loading spinner during asynchronous operations.
   */
  loadingSpinner: getElement<SVGAElement & HTMLElement>("#loading-spinner"),

  /**
   * The div element used for displaying a list of countries.
   */
  countryList: getElement<HTMLDivElement>("#country-list"),

  /**
   * The div element used for displaying the application state (e.g., error, loading).
   */
  state: getElement<HTMLDivElement>("#state"),

  /**
   * The button element used to submit the search form.
   */
  button: getElement<HTMLButtonElement>("button[type=submit]"),
};
