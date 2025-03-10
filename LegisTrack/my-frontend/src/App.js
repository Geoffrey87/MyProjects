// App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './HomePage';
import PartyList from './PartyList';

function App() {
  return (
    <Router>
      <Routes>
        {/* Landing page route */}
        <Route path="/" element={<HomePage />} />
        {/* Simulator page route */}
        <Route path="/simulator" element={<PartyList />} />
      </Routes>
    </Router>
  );
}

export default App;
