import { api } from "../api";
import { type Country } from "../types/countries";

/**
 * Array to store country data locally in memory.
 */
export const countries: Country[] = [];

/**
 * Key used for storing and retrieving countries data from localStorage.
 */
const key = "all-countries";

/**
 * Initializes the localStorage with country data.
 *
 * - If no data is stored in localStorage under the specified key, it fetches
 *   all countries from the API and stores them in localStorage.
 * - If data is already present in localStorage, it loads the data into the
 *   `countries` array.
 *
 * @async
 * @returns {Promise<void>} A promise that resolves when the initialization process is complete.
 */
export async function initializeLocalStorage(): Promise<void> {
  const storedCountries = localStorage.getItem(key);

  if (storedCountries === null) {
    // Fetch all countries from the API and store them in localStorage
    const fetchedCountries = await api.getAllCountries();
    localStorage.setItem(key, JSON.stringify(fetchedCountries));
    countries.push(...fetchedCountries);
  } else {
    // Parse the stored data and load it into the `countries` array
    countries.push(...JSON.parse(storedCountries));
  };
}
