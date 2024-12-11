import { xmlGetCountries } from "./api/xml-get-countries";
import "./styles.css";
import { elements } from "./utils/elements";
import { setFetchState } from "./utils/set-fetch-state";

elements.form.onsubmit = (e) => {
  //e of event
  e.preventDefault();

  const input = elements.searchInput.value.trim();
  setFetchState("pending");
  xmlGetCountries(input)
    .then((countries) => {
      console.log(countries);
      setFetchState("sucess");
    })
    .catch((error) => {
      console.log(error);
      setFetchState("error");
    })
    .finally(() => {
      console.log("Here!");
    });
};
