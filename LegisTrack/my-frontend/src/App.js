import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './HomePage';
import PartyList from './PartyList';
import UpdatePartyPage from './UpdatePartyPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/simulator" element={<PartyList />} />
        <Route path="/update-party/:id" element={<UpdatePartyPage />} /> {/* New route */}
      </Routes>
    </Router>
  );
}

export default App;
