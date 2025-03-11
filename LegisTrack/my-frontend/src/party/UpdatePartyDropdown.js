import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function UpdatePartyDropdown({ parties }) {
  const [selectedParty, setSelectedParty] = useState("");
  const navigate = useNavigate();

  const handleUpdate = () => {
    if (!selectedParty) {
      alert("Please select a party to update.");
      return;
    }
    navigate(`/update-party/${selectedParty}`); // ✅ Navigate to update page
  };

  return (
    <div>
      <h3>Update Party</h3>
      <select value={selectedParty} onChange={(e) => setSelectedParty(e.target.value)}>
        <option value="">Select a party</option>
        {parties.map((party) => (
          <option key={party.id} value={party.id}>
            {party.name}
          </option>
        ))}
      </select>
      <button onClick={handleUpdate}>Update</button>
    </div>
  );
}

export default UpdatePartyDropdown;
