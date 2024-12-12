import { type Country } from "../types/countries";

// A Promise represents the result of an asynchronous operation. It can be in one of three states:
// pending (operation not completed yet), fulfilled (operation completed successfully), or rejected (operation failed).
// Promises provide methods like `.then` to handle success, `.catch` to handle errors, and `.finally` for cleanup,
// making asynchronous code easier to read and maintain compared to traditional callbacks.

export type FetchError = {
  status: number;
  message: string;
};

export function xmlGetCountries(nameQuery: string): Promise<Country[]> {
  return new Promise((resolve, reject) => {
    const request = new XMLHttpRequest();

    request.open("GET", `https://restcountries.com/v3.1/name/${nameQuery}`);

    request.onload = () => {
      if (request.status === 200) {
        const countries = JSON.parse(request.response); // parse is to passe the of json to javascript
        resolve(countries);
      } else {
        const error = JSON.parse(request.response);
        reject(error);
      }
    };

    request.send();
  });
}
