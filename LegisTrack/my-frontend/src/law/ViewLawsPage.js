import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function ViewLawsPage() {
  const { id } = useParams(); // Get party ID from URL
  const [laws, setLaws] = useState([]);
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/laws`, {
        headers: { "X-User-Id": userId },
      })
      .then((response) => {
      const partyLaws = response.data.filter(law =>
                law.proposingPartyId.toString() === id
                );
        setLaws(partyLaws);
      })
      .catch((error) => {
        console.error("Error fetching laws:", error);
      });
  }, [id, userId]);

  return (
    <div>
      <h2>Proposed Laws for Party</h2>
      {laws.length === 0 ? (
        <p>No laws found for this party.</p>
      ) : (
        <ul>
          {laws.map((law) => (
            <li key={law.id}>
              📜 {law.description} <br />
              📅 Date Proposed: {law.dateProposed}
            </li>
          ))}
        </ul>
      )}
      <button onClick={() => navigate("/simulator")}>⬅️ Back</button>
    </div>
  );
}

export default ViewLawsPage;
