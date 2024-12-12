# Country Search App

A simple web app that allows users to search for countries and view detailed information about them. The app fetches country data from the [Rest Countries API](https://restcountries.com) and displays it in an interactive and user-friendly interface. Users can search by country name, view detailed information, and explore neighboring countries.

## Features

- **Search for countries** by name.
- **View country details** including population, region, capital, native names, currencies, languages, and borders.
- **Interactive UI** with a loading spinner during fetch operations.
- **Displays country flags** and other relevant information.
- **Explore neighboring countries** by clicking on borders.

## Technologies Used

- **HTML**: For the structure of the app.
- **CSS**: For styling and responsiveness.
- **JavaScript**: For interactivity and API integration.
- **TypeScript**: For type safety and better development experience.
- **Rest Countries API**: For fetching country data.

## Setup and Installation

To run the app locally, follow these steps:

### 1. Clone the repository

```bash
git clone https://github.com/your-username/country-search-app.git
cd country-search-app
```

### 2. Install dependencies

If you're using npm, run:

```bash
npm install
```

Or if you're using yarn:

```bash
yarn install
```

### 3. Run the development server

To start the development server and view the app in your browser, run:

```bash
npm start
```

Or with yarn:

```bash
yarn start
```

The app should now be running at `http://localhost:5173.

## Usage

1. Open the app in your browser.
2. Use the search bar to type the name of a country. The app will display a list of countries that match the search term.
3. Click on a country to view detailed information about it.
4. If the country has borders, you can click on the border buttons to explore neighboring countries.

## File Structure

```
.
├── src
│   ├── api
│   │   └── xml-get-countries.ts        # Contains API integration logic for fetching countries
│   ├── storage
│   │   └── countries.ts                # Local storage and initialization logic
│   ├── types
│   │   └── countries.ts                # Type definitions for Country object
│   ├── utils
│   │   ├── elements.ts                 # DOM elements selector utility
│   │   ├── render-countries.ts         # Logic for rendering countries on the UI
│   │   └── set-fetch-state.ts          # Updates UI based on fetch state (pending, success, error)
│   ├── index.ts                        # Main entry point for the application
│   ├── styles.css                      # Styling for the app
└── README.md
```

## Functions Overview

### `initializeLocalStorage`

Initializes the local storage by fetching country data from the API if not already stored. This function ensures that the country data is available even after a page reload.

### `renderCountries`

Renders a list of countries on the page. It creates HTML elements for each country and populates them with data such as the country's flag, population, region, and capital. This function also adds "Learn More" buttons for detailed views.

### `renderLearnMore`

Displays detailed information about a specific country when a user clicks on a country from the list. It shows additional details such as native name, subregion, currencies, languages, and neighboring countries. It also allows users to navigate back to the country list.

### `setFetchState`

Updates the UI based on the current fetch state:

- **Pending**: Shows a loading spinner and disables the search button.
- **Success**: Hides the loading spinner and enables the button.
- **Error**: Displays an error message if the fetch request fails.

### `getElement`

A helper function to get an HTML element by its query selector. It ensures that the element exists and throws an error if it is not found.

## Credits

Based on a project from a Udemy course: Fundamentals of Web Development with JavaScript and TypeScript by Lucas Barake.

---
