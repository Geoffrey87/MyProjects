import { Country } from "../types/countries";

export const api = {
  async getCountriesByName(name: string): Promise<Country[]> {
    const response = await fetch(`https://restcountries.com/v3.1/name/${name}`);
    if (!response.ok) {
      // response not ok
      throw new Error(`HTTO error! status: ${response.status}`);
    }
    const json = await response.json();
    return json;
  },
};
