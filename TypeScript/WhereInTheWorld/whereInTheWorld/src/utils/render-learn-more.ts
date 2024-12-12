import { countries } from "../storage/countries";
import { Country } from "../types/countries";
import { elements, getElement } from "./elements";

/**
 * Renders detailed information about a specific country, replacing the current content.
 *
 * - Displays information such as the flag, name, population, region, subregion, capital, top-level domain, currencies, and languages.
 * - Includes a back button to return to the previous view.
 * - If the country has borders, creates buttons for each bordering country to display its details.
 *
 * @param {Country} country - The country object containing details to display.
 * @returns {void} This function does not return anything; it directly manipulates the DOM.
 */
export function renderLearnMore(country: Country): void {
  // Clear the current content in the country list container
  elements.countryList.innerHTML = "";

  // Create the parent div for detailed information
  const parentDiv = document.createElement("div");
  parentDiv.className = "flex flex-col items-center justify-between gap-4 px-24 mb-20";
  parentDiv.id = "learn-more";

  // Create and append the "Back" button
  const backButton = document.createElement("button");
  backButton.type = "button";
  backButton.className = "px-2 py-1 bg-[#2b3743] text-medium text-xs shadow-md rounded-md";
  backButton.textContent = "← Back";
  backButton.onclick = () => {
    getElement<HTMLDivElement>("div#learn-more").remove();
  };
  parentDiv.appendChild(backButton);

  // Insert detailed country information
  parentDiv.insertAdjacentHTML(
    "beforeend",
    `
    <img
        src="${country.flags.svg}" />
        
      <h1>${country.name.common}</h1>

      <p class="text-sm font-light"><span class="font-medium">Native Name:</span> ${
        Object.values(
          country.name.nativeName ?? {
            common: "N/A",
          }
        ).at(0)?.common
      }</p>

      <p class="text-sm font-light"><span class="font-medium">Population:</span> ${country.population.toLocaleString()}</p>

      <p class="text-sm font-light"><span class="font-medium">Region:</span> ${country.region}</p>

      <p class="text-sm font-light"><span class="font-medium">Sub Region:</span> ${
        country.subregion
      }</p>

      <p class="text-sm font-light"><span class="font-medium">Capital:</span> ${
        country.capital ?? "N/A"
      }</p>

      <p class="text-sm font-light"><span class="font-medium">Top Level Domain:</span> ${
        country.tld?.at(0) ?? "N/A"
      }</p>

      <p class="text-sm font-light"><span class="font-medium">Currencies:</span> ${Object.values(
        country.currencies ?? {}
      )
        .map((currency) => `(${currency.symbol}) ${currency.name}`)
        .join(", ")}</p>
      
      <p class="text-sm font-light">
        <span class="font-medium">Languages:</span> ${Object.values(
          country.languages ?? ["N/A"]
        ).join(", ")}
      </p>
    `
  );

  // Create and populate a container for border country buttons
  const bordersContainer = document.createElement("div");
  bordersContainer.className = "grid items-center grid-cols-4 gap-2";

  country.borders?.forEach((border) => {
    const button = document.createElement("button");
    button.type = "button";
    button.className = "px-2 py-1 bg-gray-900 text-medium text-xs shadow-md rounded-md";
    button.textContent = border;
    button.onclick = async () => {
      // Filter the country from the local list using its border code
      const filteredCountries = countries.filter((country) => country.cca3 === border);
      renderLearnMore(filteredCountries[0]);
    };
    bordersContainer.appendChild(button);
  });

  // Append the borders container to the parent div
  parentDiv.appendChild(bordersContainer);

  // Append the detailed view container to the country list
  elements.countryList.appendChild(parentDiv);
}
