import { type FetchError, xmlGetCountries } from "./api/xml-get-countries";
import "./styles.css";
import { elements } from "./utils/elements";
import { renderCountries } from "./utils/render-countries";
import { setFetchState } from "./utils/set-fetch-state";

elements.form.onsubmit = (e) => {
  //e of event
  e.preventDefault();

  const input = elements.searchInput.value.trim();
  setFetchState({
    state: "pending",
  });
  xmlGetCountries(input)
    .then((countries) => {
      setFetchState({
        state: "sucess",
      });
      renderCountries(countries);
    })
    .catch((error: FetchError) => {
      setFetchState({
        state: "error",
        error,
      });
    });
};
