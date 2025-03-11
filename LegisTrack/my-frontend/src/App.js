import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./HomePage";
import PartyList from "./party/PartyList";
import CreatePartyPage from "./party/CreatePartyPage";
import UpdatePartyPage from "./party/UpdatePartyPage";
import LawList from "./law/LawList";
import CreateLawPage from "./law/CreateLawPage";
import ViewLawsPage from "./law/ViewLawsPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/simulator" element={<PartyList />} />
        <Route path="/create-party" element={<CreatePartyPage />} />
        <Route path="/update-party/:id" element={<UpdatePartyPage />} />

        <Route path="/laws" element={<LawList />} />
        <Route path="/create-law" element={<CreateLawPage />} />
        <Route path="/view-laws/:id" element={<ViewLawsPage />} />
      </Routes>
    </Router>
  );
}

export default App;
