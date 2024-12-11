import { xmlGetCountries } from "./api/xml-get-countries";
import "./styles.css";
import { elements } from "./utils/elements";

elements.form.onsubmit = (e) => {
  //e of event
  e.preventDefault();

  const input = elements.searchInput.value.trim();
  xmlGetCountries(input)
    .then((countries) => {
      console.log(countries);
    })
    .catch((error) => {
      console.log(error);
    })
    .finally(() => {
      console.log("Here!");
    });
};
