import { countries, initializeLocalStorage } from "./storage/countries";
import "./styles.css";
import { elements } from "./utils/elements";
import { renderCountries } from "./utils/render-countries";

/**
 * Initializes the local storage by fetching and storing country data if necessary.
 * The countries are stored in the local storage under the key "all-countries".
 * This function runs asynchronously and populates the `countries` array.
 *
 * @returns {Promise<void>} A promise that resolves once the countries data has been fetched and stored.
 */
void initializeLocalStorage();

/**
 * Handles the form submission event to search for countries.
 * When the form is submitted, the function filters the countries based on the user input
 * and renders the matching countries.
 *
 * @param {Event} e - The form submit event.
 * @returns {void} This function doesn't return anything; it updates the UI with the filtered countries.
 */
elements.form.onsubmit = async (e) => {
  // Prevent the default form submission behavior
  e.preventDefault();

  // Get the search input value, trim whitespace, and convert it to lowercase
  const input = elements.searchInput.value.trim().toLowerCase();

  // Filter the countries array based on the input value
  const filteredCountries = countries.filter((country) =>
    country.name.common.toLowerCase().includes(input)
  );

  // Render the filtered countries to the UI
  renderCountries(filteredCountries);
};
