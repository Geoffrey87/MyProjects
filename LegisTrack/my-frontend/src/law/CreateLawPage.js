// CreateLawPage.js
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function CreateLawPage() {
  const [description, setDescription] = useState("");
  const [dateProposed, setDateProposed] = useState("");
  const [partyId, setPartyId] = useState("");
  const [parties, setParties] = useState([]);
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();

  // Buscar partidos do usuário
  useEffect(() => {
    axios.get("http://localhost:8080/api/parties", {
      headers: { "X-User-Id": userId }
    })
    .then(response => setParties(response.data))
    .catch(error => console.error("Error fetching parties:", error));
  }, [userId]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const lawData = {
      description,
      dateProposed,
      proposingPartyId: partyId
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/api/laws",
        lawData,
        {
          headers: {
            "X-User-Id": userId,
            "Content-Type": "application/json"
          }
        }
      );

      if (response.status === 201) {
        navigate(`/view-laws/${partyId}`);
      }
    } catch (error) {
      console.error("Error creating law:", error);
    }
  };

  return (
    <div>
      <h2>Create New Law</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Description:</label>
          <input
            type="text"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Date Proposed:</label>
          <input
            type="date"
            value={dateProposed}
            onChange={(e) => setDateProposed(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Proposing Party:</label>
          <select
            value={partyId}
            onChange={(e) => setPartyId(e.target.value)}
            required
          >
            <option value="">Select a Party</option>
            {parties.map(party => (
              <option key={party.id} value={party.id}>
                {party.name}
              </option>
            ))}
          </select>
        </div>

        <button type="submit">Create Law</button>
      </form>
    </div>
  );
}

export default CreateLawPage;