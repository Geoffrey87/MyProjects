import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ButtonsBar from './ButtonsBar';

function PartyList() {
  const [parties, setParties] = useState([]);
  const userId = sessionStorage.getItem("userId");

  useEffect(() => {
    axios
      .get('http://localhost:8080/api/parties', {
        headers: { "X-User-Id": userId },
        withCredentials: true,
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

      {/* Insert the ButtonsBar below the party list */}
      <ButtonsBar />
    </div>
  );
}

export default PartyList;
