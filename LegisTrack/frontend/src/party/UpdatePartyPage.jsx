import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";

function UpdatePartyPage() {
  const { id } = useParams();
  const [party, setParty] = useState({ name: "", nrOfDeputies: 1 });
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_API_URL}/parties/${id}`, {
        headers: { "X-User-Id": userId },
      })
      .then((response) => setParty(response.data))
      .catch((error) => console.error("Error fetching party:", error));
  }, [id, userId]);

  const handleSubmit = (event) => {
    event.preventDefault();

    axios
      .put(`${process.env.REACT_APP_API_URL}/parties/${id}`, party, {
        headers: { "X-User-Id": userId },
      })
      .then(() => {
        alert("Party updated successfully!");
        navigate("/simulator");
      })
      .catch((error) => console.error("Error updating party:", error));
  };

  return (
    <div>
      <h2>Update Party</h2>
      <form onSubmit={handleSubmit}>
        <label>Party Name:</label>
        <input type="text" value={party.name} onChange={(e) => setParty({ ...party, name: e.target.value })} required />

        <label>Number of Deputies:</label>
        <input type="number" value={party.nrOfDeputies} onChange={(e) => setParty({ ...party, nrOfDeputies: e.target.value })} min="1" required />

        <button type="submit">Update Party</button>
      </form>
    </div>
  );
}

export default UpdatePartyPage;
