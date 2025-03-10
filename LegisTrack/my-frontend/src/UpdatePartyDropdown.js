import React from 'react';
import { useNavigate } from 'react-router-dom';

function UpdatePartyDropdown({ parties }) {
  const navigate = useNavigate();

  const handleChange = (event) => {
    const partyId = event.target.value;
    if (partyId) {
      navigate(`/update-party/${partyId}`);
    }
  };

  return (
    <div>
      <label>Update Party</label>
      <select onChange={handleChange}>
        <option value="">Select a party</option>
        {parties.map((party) => (
          <option key={party.id} value={party.id}>
            {party.name}
          </option>
        ))}
      </select>
    </div>
  );
}

export default UpdatePartyDropdown;
