import axios from "axios";

const baseURL = process.env.REACT_APP_API_URL || "/api";

console.log("API URL:", baseURL);

const api = axios.create({
  baseURL,
  withCredentials: true,
});

export default api;
