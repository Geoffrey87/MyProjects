import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function PartyList() {
  const [parties, setParties] = useState([]);
  const [selectedParty, setSelectedParty] = useState(""); // Stores selected party ID
  const [lawDescription, setLawDescription] = useState(""); // Stores law description
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/parties", {
        headers: { "X-User-Id": userId },
        withCredentials: true,
      })
      .then((response) => {
        setParties(response.data);
      })
      .catch((error) => {
        console.error("Error fetching parties:", error);
      });
  }, [userId]);

  // Function to delete a party
  const handleDelete = (id) => {
    axios
      .delete(`http://localhost:8080/api/parties/${id}`, {
        headers: { "X-User-Id": userId },
      })
      .then(() => {
        setParties(parties.filter((party) => party.id !== id));
      })
      .catch((error) => {
        console.error("Error deleting party:", error);
      });
  };

  // Navigate to Update Page
  const handleUpdate = (id) => {
    navigate(`/update-party/${id}`);
  };

  // Navigate to View Laws Page
  const handleViewLaws = (id) => {
    navigate(`/view-laws/${id}`);
  };

  // Handle creating a law
  const handleCreateLaw = () => {
    if (!selectedParty || !lawDescription) {
      alert("Please select a party and enter a law description.");
      return;
    }

    const newLaw = {
      description: lawDescription,
      proposingPartyId: selectedParty,
      dateProposed: new Date().toISOString().split("T")[0], // Today's date
    };

    axios
      .post("http://localhost:8080/api/laws", newLaw, {
        headers: { "X-User-Id": userId },
      })
      .then(() => {
        alert("Law successfully created!");
        setSelectedParty("");
        setLawDescription("");
      })
      .catch((error) => {
        console.error("Error creating law:", error);
      });
  };

  return (
    <div>
      <h2>Parties</h2>

      {/* Sorting Buttons */}
      <button onClick={() => setParties([...parties].sort((a, b) => a.nrOfDeputies - b.nrOfDeputies))}>🔼</button>
      <button onClick={() => setParties([...parties].sort((a, b) => b.nrOfDeputies - a.nrOfDeputies))}>🔽</button>

      <ul>
        {parties.map((party) => (
          <li key={party.id}>
            {party.name} - Nº of Deputies: {party.nrOfDeputies}
            <button onClick={() => handleUpdate(party.id)}>✏️ Update</button>
            <button onClick={() => handleDelete(party.id)}>🗑 Delete</button>
            <button onClick={() => handleViewLaws(party.id)}>📜 View Laws</button>
          </li>
        ))}
      </ul>

      {/* Dropdown for Creating a Law */}
      <div style={{ marginTop: "20px" }}>
        <label htmlFor="partySelect">➕ Create Law for:</label>
        <select
          id="partySelect"
          value={selectedParty}
          onChange={(e) => setSelectedParty(e.target.value)}
          style={{ marginLeft: "10px" }}
        >
          <option value="">Select a party</option>
          {parties.map((party) => (
            <option key={party.id} value={party.id}>
              {party.name}
            </option>
          ))}
        </select>

        {/* Input for Law Description */}
        {selectedParty && (
          <>
            <input
              type="text"
              placeholder="Enter Law Description"
              value={lawDescription}
              onChange={(e) => setLawDescription(e.target.value)}
              style={{ marginLeft: "10px" }}
            />
            <button onClick={handleCreateLaw} style={{ marginLeft: "10px" }}>
              ✅ Submit Law
            </button>
          </>
        )}
      </div>

      {/* Button to Create Party */}
      <button onClick={() => navigate("/create-party")} style={{ marginTop: "10px" }}>
        ➕ Create New Party
      </button>
    </div>
  );
}

export default PartyList;
