import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function CreatePartyPage() {
  const [name, setName] = useState("");
  const [nrOfDeputies, setNrOfDeputies] = useState(1);
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();

    // Party Data
    const newParty = { name, nrOfDeputies };

    // ✅ Send POST request to backend
    axios
      .post("http://localhost:8080/api/parties", newParty, {
        headers: { "X-User-Id": userId },
      })
      .then(() => {
        alert("Party created successfully!");
        navigate("/simulator"); // ✅ Redirect back to list
      })
      .catch((error) => {
        console.error("Error creating party:", error);
      });
  };

  return (
    <div>
      <h2>Create New Party</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Party Name:</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Number of Deputies:</label>
          <input
            type="number"
            value={nrOfDeputies}
            onChange={(e) => setNrOfDeputies(e.target.value)}
            min="1"
            required
          />
        </div>

        <button type="submit">Create Party</button>
      </form>
    </div>
  );
}

export default CreatePartyPage;
