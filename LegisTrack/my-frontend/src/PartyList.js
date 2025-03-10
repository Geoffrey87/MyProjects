import React, { useEffect, useState } from 'react';
import axios from 'axios';
import UpdatePartyDropdown from './UpdatePartyDropdown';

function PartyList() {
  const [parties, setParties] = useState([]);
  const userId = sessionStorage.getItem("userId");

  useEffect(() => {
    axios.get('http://localhost:8080/api/parties', {
      headers: { "X-User-Id": userId },
      withCredentials: true
    })
    .then(response => {
      setParties(response.data);
    })
    .catch(error => {
      console.error("Error fetching parties:", error);
    });
  }, [userId]);

  return (
    <div>
      <h2>Parties</h2>
      <ul>
        {parties.map(party => (
          <li key={party.id}>
            {party.name} - Nº of Deputies: {party.nrOfDeputies}
          </li>
        ))}
      </ul>

      {/* Pass the party list to the dropdown */}
      <UpdatePartyDropdown parties={parties} />
    </div>
  );
}

export default PartyList;
