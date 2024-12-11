import "./styles.css";
import { elements } from "./utils/elements";

elements;

elements.form.onsubmit = (e) => {
  //e of event
  e.preventDefault();

  const input = elements.searchInput.value.trim();
  const request = new XMLHttpRequest();

  request.open("GET", `https://restcountries.com/v3.1/name/${input}`);
  request.onload = () => {
    console.log(JSON.parse(request.response));
  };
  request.onerror = () => {
    console.log("error");
  };
  request.send();
};
