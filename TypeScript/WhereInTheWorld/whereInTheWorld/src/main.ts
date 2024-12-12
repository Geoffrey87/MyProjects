import { type FetchError, xmlGetCountries } from "./api/xml-get-countries";
import "./styles.css";
import { elements } from "./utils/elements";
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
      console.log(countries);
      setFetchState({
        state: "sucess",
      });
    })
    .catch((error: FetchError) => {
      console.log(error);
      setFetchState({
        state: "error",
        error,
      });
    })
    .finally(() => {
      console.log("Here!");
    });
};
