import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

function ViewVotesPage() {
  const { lawId } = useParams();
  const [votes, setVotes] = useState({
    IN_FAVOR: [],
    AGAINST: [],
    ABSTENTION: []
  });
  const [parties, setParties] = useState([]); // Partidos do usuário
  const userId = sessionStorage.getItem("userId");

  // Estados para armazenar a soma de deputados em cada categoria
  const [deputiesInFavor, setDeputiesInFavor] = useState(0);
  const [deputiesAgainst, setDeputiesAgainst] = useState(0);
  const [deputiesAbstention, setDeputiesAbstention] = useState(0);

  useEffect(() => {
    // 1) Buscar votos da lei
    axios
      .get(`http://localhost:8080/api/votes/law/${lawId}`, {
        headers: { "X-User-Id": userId }
      })
      .then((response) => {
        const seen = new Set();
        const cleanData = {
          IN_FAVOR: (response.data.IN_FAVOR || []).filter((p) => {
            if (seen.has(p)) return false;
            seen.add(p);
            return true;
          }),
          AGAINST: (response.data.AGAINST || []).filter((p) => {
            if (seen.has(p)) return false;
            seen.add(p);
            return true;
          }),
          ABSTENTION: (response.data.ABSTENTION || []).filter((p) => {
            if (seen.has(p)) return false;
            seen.add(p);
            return true;
          })
        };
        setVotes(cleanData);
      })
      .catch((error) => {
        console.error("Error:", error);
      });

    // 2) Buscar todos os partidos do usuário
    axios
      .get("http://localhost:8080/api/parties", {
        headers: { "X-User-Id": userId }
      })
      .then((response) => {
        setParties(response.data);
      })
      .catch((error) => {
        console.error("Error fetching parties:", error);
      });
  }, [lawId, userId]);

  // Sempre que 'votes' ou 'parties' mudarem, recalcular a soma de deputados
  useEffect(() => {
    if (!parties.length) return; // Se ainda não carregou os partidos, não faz nada

    // Função auxiliar para somar deputados de uma lista de nomes de partidos
    const sumDeputies = (partyNames) => {
      return partyNames.reduce((acc, partyName) => {
        // Procura o partido no array 'parties' (buscando por nome)
        const found = parties.find((p) => p.name === partyName);
        return acc + (found ? found.nrOfDeputies : 0);
      }, 0);
    };

    const totalFavor = sumDeputies(votes.IN_FAVOR);
    const totalAgainst = sumDeputies(votes.AGAINST);
    const totalAbstention = sumDeputies(votes.ABSTENTION);

    setDeputiesInFavor(totalFavor);
    setDeputiesAgainst(totalAgainst);
    setDeputiesAbstention(totalAbstention);
  }, [votes, parties]);

  // Determina se a lei foi aprovada
  const isApproved = deputiesInFavor > deputiesAgainst;

  // Helper para exibir a lista de partidos
  const renderVotes = (votesArray) => {
    if (!votesArray || votesArray.length === 0) {
      return "No votes in this category";
    }
    return votesArray.join(", ");
  };

  return (
    <div style={{ padding: "20px", maxWidth: "800px", margin: "0 auto" }}>
      <h2 style={{ color: "#2c3e50" }}>Votes for Law {lawId}</h2>

      {/* Exemplo de mensagem se a lei foi aprovada */}
      <div style={{ margin: "10px 0", fontWeight: "bold" }}>
        {isApproved ? (
          <span style={{ color: "green" }}>✅ Law Approved!</span>
        ) : (
          <span style={{ color: "red" }}>❌ Law Not Approved</span>
        )}
      </div>

      {/* IN_FAVOR */}
      <div
        style={{
          margin: "15px 0",
          padding: "10px",
          border: "1px solid #ecf0f1",
          borderRadius: "5px"
        }}
      >
        <strong style={{ color: "#27ae60" }}>✅ IN FAVOR:</strong>
        <span style={{ marginLeft: "10px" }}>{renderVotes(votes.IN_FAVOR)}</span>
        <br />
        <small style={{ color: "#27ae60" }}>
          Deputies in favor: {deputiesInFavor}
        </small>
      </div>

      {/* AGAINST */}
      <div
        style={{
          margin: "15px 0",
          padding: "10px",
          border: "1px solid #ecf0f1",
          borderRadius: "5px"
        }}
      >
        <strong style={{ color: "#e74c3c" }}>❌ AGAINST:</strong>
        <span style={{ marginLeft: "10px" }}>{renderVotes(votes.AGAINST)}</span>
        <br />
        <small style={{ color: "#e74c3c" }}>
          Deputies against: {deputiesAgainst}
        </small>
      </div>

      {/* ABSTENTION */}
      <div
        style={{
          margin: "15px 0",
          padding: "10px",
          border: "1px solid #ecf0f1",
          borderRadius: "5px"
        }}
      >
        <strong style={{ color: "#f39c12" }}>⚖️ ABSTENTION:</strong>
        <span style={{ marginLeft: "10px" }}>{renderVotes(votes.ABSTENTION)}</span>
        <br />
        <small style={{ color: "#f39c12" }}>
          Deputies abstention: {deputiesAbstention}
        </small>
      </div>
    </div>
  );
}

export default ViewVotesPage;
