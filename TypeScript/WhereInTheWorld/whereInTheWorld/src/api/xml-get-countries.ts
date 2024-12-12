import { type Country } from "../types/countries";

// A Promise represents the result of an asynchronous operation. It can be in one of three states:
// pending (operation not completed yet), fulfilled (operation completed successfully), or rejected (operation failed).
// Promises provide methods like `.then` to handle success, `.catch` to handle errors, and `.finally` for cleanup,
// making asynchronous code easier to read and maintain compared to traditional callbacks.

/**
 * Represents an error that occurs during a fetch operation.
 */
export type FetchError = {
  status: number;
  message: string;
};

/**
 * Fetches countries by their name using the XMLHttpRequest API.
 *
 * @param {string} nameQuery - The name or part of the name of the country to search for.
 * @returns {Promise<Country[]>} A promise that resolves to an array of country objects if the request is successful.
 * @throws {FetchError} If the HTTP request fails or returns a non-200 status, the promise is rejected with an error.
 */
export function xmlGetCountries(nameQuery: string): Promise<Country[]> {
  return new Promise((resolve, reject) => {
    const request = new XMLHttpRequest();
    // Open a GET request to fetch data from the API
    request.open("GET", `https://restcountries.com/v3.1/name/${nameQuery}`);

    // Define what happens when the request is completed
    request.onload = () => {
      if (request.status === 200) {
        // Parse the JSON response and resolve the promise with the countries
        const countries = JSON.parse(request.response);
        resolve(countries);
      } else {
        // Parse the JSON error response and reject the promise with the error
        const error = JSON.parse(request.response);
        reject(error);
      }
    };

    // Send the request
    request.send();
  });
}
