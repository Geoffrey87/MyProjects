import React, { useState, useEffect } from "react";
import axios from "axios";

function LawList() {
  const [laws, setLaws] = useState([]);
  const userId = sessionStorage.getItem("userId");

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/laws", {
        headers: { "X-User-Id": userId },
      })
      .then((response) => {
        setLaws(response.data);
      })
      .catch((error) => {
        console.error("Error fetching laws:", error);
      });
  }, [userId]);

  return (
    <div>
      <h2>Proposed Laws</h2>
      <ul>
        {laws.map((law) => (
          <li key={law.id}>
            📜 {law.description} <br />
            🎭 Proposed by: {law.proposingPartyName} <br />
            📅 Date Proposed: {law.dateProposed}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default LawList;
