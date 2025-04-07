import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function ViewVotesPage() {
  const { lawId } = useParams();
  const navigate = useNavigate();
  const [votes, setVotes] = useState({
    IN_FAVOR: [],
    AGAINST: [],
    ABSTENTION: [],
  });
  const [parties, setParties] = useState([]);
  const [lawDescription, setLawDescription] = useState("");
  const [deputiesInFavor, setDeputiesInFavor] = useState(0);
  const [deputiesAgainst, setDeputiesAgainst] = useState(0);
  const [deputiesAbstention, setDeputiesAbstention] = useState(0);
  const userId = sessionStorage.getItem("userId");

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_API_URL}/votes/law/${lawId}`, { headers: { "X-User-Id": userId } })
      .then((response) => {
        const seen = new Set();
        const cleanData = {
          IN_FAVOR: (response.data.IN_FAVOR || []).filter((p) => !seen.has(p) && seen.add(p)),
          AGAINST: (response.data.AGAINST || []).filter((p) => !seen.has(p) && seen.add(p)),
          ABSTENTION: (response.data.ABSTENTION || []).filter((p) => !seen.has(p) && seen.add(p)),
        };
        setVotes(cleanData);
      })
      .catch(console.error);

    axios
      .get(`${process.env.REACT_APP_API_URL}/parties`, { headers: { "X-User-Id": userId } })
      .then((response) => setParties(response.data))
      .catch(console.error);

    //Get the law description
    axios
      .get(`${process.env.REACT_APP_API_URL}/laws/${lawId}`, { headers: { "X-User-Id": userId } })
      .then((response) => setLawDescription(response.data.description))
      .catch(console.error);
  }, [lawId, userId]);

  useEffect(() => {
    if (!parties.length) return;

    const sumDeputies = (partyNames) => partyNames.reduce((acc, name) => {
      const party = parties.find(p => p.name === name);
      return acc + (party ? party.nrOfDeputies : 0);
    }, 0);

    setDeputiesInFavor(sumDeputies(votes.IN_FAVOR));
    setDeputiesAgainst(sumDeputies(votes.AGAINST));
    setDeputiesAbstention(sumDeputies(votes.ABSTENTION));
  }, [votes, parties]);

  const isApproved = deputiesInFavor > deputiesAgainst;

  const renderVotes = (votesArray) => votesArray.length ? votesArray.join(", ") : "No votes in this category";

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-100 to-blue-200 py-10">
      <div className="max-w-4xl mx-auto bg-white shadow-xl rounded-xl p-8">
        <button
          onClick={() => navigate("/view-laws")}
          className="mb-4 px-4 py-2 bg-gray-400 text-white rounded-lg hover:bg-gray-500 transition"
        >
          ← Back to Laws
        </button>

        <h2 className="text-3xl font-bold text-center text-blue-700 mb-6">Votes for "{lawDescription}"</h2>

        <div className={`font-bold text-center text-xl mb-4 ${isApproved ? "text-green-600" : "text-red-600"}`}>
          {isApproved ? "✅ Law Approved!" : "❌ Law Not Approved"}
        </div>

        <div className="space-y-4">
          <div className="p-4 border border-green-300 rounded-lg">
            <strong className="text-green-600">✅ IN FAVOR:</strong> {renderVotes(votes.IN_FAVOR)}<br />
            <small>Deputies in favor: {deputiesInFavor}</small>
          </div>

          <div className="p-4 border border-red-300 rounded-lg">
            <strong className="text-red-600">❌ AGAINST:</strong> {renderVotes(votes.AGAINST)}<br />
            <small>Deputies against: {deputiesAgainst}</small>
          </div>

          <div className="p-4 border border-yellow-300 rounded-lg">
            <strong className="text-yellow-600">⚖️ ABSTENTION:</strong> {renderVotes(votes.ABSTENTION)}<br />
            <small>Deputies abstention: {deputiesAbstention}</small>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ViewVotesPage;
