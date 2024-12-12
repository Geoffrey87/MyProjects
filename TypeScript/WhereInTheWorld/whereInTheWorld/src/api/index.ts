import { Country } from "../types/countries";

export const api = {
  /**
   * Fetches countries by their name.
   *
   * @param {string} name - The name of the country or a part of it to search for.
   * @returns {Promise<Country[]>} A promise that resolves to an array of country objects matching the given name.
   * @throws {Error} If the HTTP response is not OK, an error is thrown with the HTTP status.
   */
  async getCountriesByName(name: string): Promise<Country[]> {
    const response = await fetch(`https://restcountries.com/v3.1/name/${name}`, {
      method: "GET",
    });
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const json = await response.json();
    return json;
  },

  /**
   * Fetches a country by its code.
   *
   * @param {string} code - The code of the country to fetch.
   * @returns {Promise<Country[]>} A promise that resolves to an array containing the country object matching the given code.
   * @throws {Error} If the HTTP response is not OK, an error is thrown with the HTTP status.
   *
   *  @async This function performs an asynchronous HTTP request to fetch data from an external API.
   */
  async getCountryByCode(code: string): Promise<Country[]> {
    const response = await fetch(`https://restcountries.com/v3.1/alpha/${code}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const countries = await response.json();
    return countries;
  },

  /**
   * Fetches all available countries.
   *
   * @returns {Promise<Country[]>} A promise that resolves to an array of all country objects.
   * @throws {Error} If the HTTP response is not OK, an error is thrown with the HTTP status.
   */
  async getAllCountries(): Promise<Country[]> {
    const response = await fetch("https://restcountries.com/v3.1/all");
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const countries = await response.json();
    return countries;
  },
};
