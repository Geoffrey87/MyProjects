import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";

function PartyList() {
  const [parties, setParties] = useState([]);
  const [message, setMessage] = useState("");
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();
  const location = useLocation();

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

    // ✅ Show success message if law was created
    if (location.state?.lawCreated) {
      setMessage("Law successfully created!");
      setTimeout(() => setMessage(""), 3000);
    }
  }, [userId, location.state]);

  // ✅ Sort Ascending (by number of deputies)
  const sortAscending = () => {
    setParties([...parties].sort((a, b) => a.nrOfDeputies - b.nrOfDeputies));
  };

  // ✅ Sort Descending (by number of deputies)
  const sortDescending = () => {
    setParties([...parties].sort((a, b) => b.nrOfDeputies - a.nrOfDeputies));
  };

  // ✅ Function to delete a party
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

  return (
    <div>
      <h2>Parties</h2>

      {/* ✅ Sorting Buttons */}
      <div style={{ marginBottom: "10px" }}>
        <strong>Order by:</strong>
        <button onClick={sortAscending}>🔼</button>
        <button onClick={sortDescending}>🔽</button>
      </div>

      {/* ✅ Display success message if a law was created */}
      {message && <div style={{ color: "green", fontWeight: "bold" }}>{message}</div>}

      <ul>
        {parties.map((party) => (
          <li key={party.id}>
            {party.name} - Nº of Deputies: {party.nrOfDeputies}
            <button onClick={() => navigate(`/update-party/${party.id}`)}>✏️ Update</button>
            <button onClick={() => handleDelete(party.id)}>🗑 Delete</button>
          </li>
        ))}
      </ul>

      {/* ✅ Buttons */}
      <button onClick={() => navigate("/create-party")}>➕ Create New Party</button>
      <button onClick={() => navigate("/create-law")}>📜 Create Law</button>
      <button onClick={() => navigate("/view-laws")}>📖 View Laws</button>
    </div>
  );
}

export default PartyList;
