import { type Country } from "../types/countries";
import { elements } from "./elements";

/**
 * Renders a list of countries in the UI by dynamically creating and appending HTML elements.
 *
 * - Clears the current content of the country list container.
 * - Iterates through the list of countries and creates a card for each country with its flag, name, population, region, and capital.
 * - Adds an interactive "Learn more" button for each country.
 *
 * @param {Country[]} countries - An array of country objects to render.
 * @returns {void} This function does not return anything; it directly manipulates the DOM.
 */
export function renderCountries(countries: Country[]): void {
  // Clear the current content in the country list container
  elements.countryList.innerHTML = "";

  countries.forEach((country) => {
    // Create a container div for each country
    const parentDiv = document.createElement("div");
    parentDiv.className = "flex flex-col bg-neutral-900 w-72 shadow-lg rounded-lg";

    // Add country flag, name, population, region, and capital to the container
    parentDiv.insertAdjacentHTML(
      "beforeend",
      `
    <img
        class="rounded-t-lg"
        src="${country.flags.svg}"
      />

      <div class="flex flex-col pl-6 py-4 gap-1">
        <h1 class="text-sm font-medium">${country.name.common}</h1>
        <p class="font-light text-xs">
          <span class="font-medium">Population:</span> ${country.population}
        </p>
        <p class="font-light text-xs"><span class="font-medium">Region:</span> ${country.region}</p>
        <p class="font-light text-xs"><span class="font-medium">Capital:</span> ${
          country.capital?.at(0) ?? "N/A"
        }</p>
      </div>
    `
    );

    // Create a "Learn more" button
    const learnMoreButton = document.createElement("button");
    learnMoreButton.onclick = () => {
      // Placeholder for future functionality
    };
    learnMoreButton.className = "text-xs font-medium self-end px-6 py-4 hover:animate-pulse";
    learnMoreButton.type = "button";
    learnMoreButton.textContent = "Learn more →";

    // Append the button to the parent div
    parentDiv.appendChild(learnMoreButton);

    // Append the country container to the country list in the DOM
    elements.countryList.appendChild(parentDiv);
  });
}
