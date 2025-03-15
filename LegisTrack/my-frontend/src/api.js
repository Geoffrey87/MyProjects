// src/api.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://backend:5000'
});


export default api;
