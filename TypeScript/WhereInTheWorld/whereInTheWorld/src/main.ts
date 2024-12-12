import "./styles.css";
import { elements } from "./utils/elements";
import { renderCountries } from "./utils/render-countries";
import { setFetchState } from "./utils/set-fetch-state";
import { api } from "./api";

elements.form.onsubmit = async (e) => {
  //e of event
  e.preventDefault();

  const input = elements.searchInput.value.trim();
  setFetchState({
    state: "pending",
  });
  /* xmlGetCountries(input)
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
    }); */
  setFetchState({
    state: "pending",
  });
  try {
    const countries = await api.getCountriesByName(input);
    renderCountries(countries);
    setFetchState({
      state: "sucess",
    });
  } catch (error: unknown) {
    if (error instanceof Error) {
      setFetchState({
        state: "error",
        error: error,
      });
    } else {
      setFetchState({
        state: "error",
        error: Error("Somenthing went wrong"),
      });
    }
  }
};
