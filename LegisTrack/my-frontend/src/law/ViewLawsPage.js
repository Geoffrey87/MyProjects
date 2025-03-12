import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function ViewLawsPage() {
  const [laws, setLaws] = useState([]);
  // Novo estado para armazenar os partidos do usuário
  const [userParties, setUserParties] = useState([]);
  const [loadingLawId, setLoadingLawId] = useState(null);
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();

  useEffect(() => {
    // Carrega as leis
    axios
      .get("http://localhost:8080/api/laws", {
        headers: { "X-User-Id": userId },
      })
      .then((response) => setLaws(response.data))
      .catch((error) => console.error("Error fetching laws:", error));

    // Carrega os partidos do usuário (ou todos, se preferir)
    axios
      .get("http://localhost:8080/api/parties", {
        headers: { "X-User-Id": userId },
      })
      .then((response) => {
        // Filtra para remover partidos com userId "GLOBAL"
        const filtered = response.data.filter(
          (party) => party.userId !== "GLOBAL"
        );
        setUserParties(filtered);
      })
      .catch((error) =>
        console.error("Error fetching parties:", error)
      );
  }, [userId]);

  // Gera votos apenas para os partidos atualmente exibidos
  const handleGenerateVotes = async (lawId) => {
    setLoadingLawId(lawId);
    try {
      // Extrai os IDs dos partidos que estão no estado "userParties"
      const partyIds = userParties.map((party) => party.id);
      await axios.post(
        `http://localhost:8080/api/votes/generate/${lawId}`,
        { partyIds }, // envia o array de IDs no corpo da requisição
        { headers: { "X-User-Id": userId } }
      );
      alert("Votes successfully generated! ✅");
    } catch (error) {
      console.error("Error generating votes:", error);
      alert("Failed to generate votes. ❌");
    } finally {
      setLoadingLawId(null);
    }
  };

  return (
    <div>
      <h2>Proposed Laws</h2>
      <ul>
        {laws.map((law) => (
          <li key={law.id}>
            📜 <strong>{law.description}</strong> <br />
            🎭 Proposed by: {law.proposingPartyName} <br />
            📅 Date Proposed: {law.dateProposed} <br />

            <button
              onClick={() => handleGenerateVotes(law.id)}
              disabled={loadingLawId === law.id}
            >
              {loadingLawId === law.id ? "Generating..." : "⚙️ Generate Votes"}
            </button>

            <button onClick={() => navigate(`/view-votes/${law.id}`)}>
              📊 View Votes
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ViewLawsPage;
