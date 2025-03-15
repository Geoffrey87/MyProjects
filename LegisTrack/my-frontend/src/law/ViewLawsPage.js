import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function ViewLawsPage() {
  const [laws, setLaws] = useState([]);
  const [userParties, setUserParties] = useState([]);
  const [loadingLawId, setLoadingLawId] = useState(null);
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_API_URL}/api/laws`, { headers: { "X-User-Id": userId } })
      .then((response) => setLaws(response.data))
      .catch((error) => console.error("Error fetching laws:", error));

    axios
      .get(`${process.env.REACT_APP_API_URL}/api/parties`, { headers: { "X-User-Id": userId } })
      .then((response) => {
        const filtered = response.data.filter((party) => party.userId !== "GLOBAL");
        setUserParties(filtered);
      })
      .catch((error) => console.error("Error fetching parties:", error));
  }, [userId]);

  const handleGenerateVotes = async (lawId) => {
    setLoadingLawId(lawId);
    try {
      const partyIds = userParties.map((party) => party.id);
      await axios.post(
        `${process.env.REACT_APP_API_URL}/api/votes/generate/${lawId}`,
        { partyIds },
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
    <div className="min-h-screen bg-gradient-to-br from-blue-100 to-blue-200 py-10">
      <div className="max-w-4xl mx-auto bg-white shadow-xl rounded-xl p-8">
        <button
          onClick={() => navigate("/simulator")}
          className="mb-4 px-4 py-2 bg-gray-400 text-white rounded-lg hover:bg-gray-500 transition"
        >
          ← Back
        </button>

        <h2 className="text-3xl font-bold text-center text-blue-700 mb-6">Proposed Laws</h2>

        <ul className="space-y-4">
          {laws.map((law) => (
            <li key={law.id} className="p-4 bg-gray-50 shadow rounded-lg">
              <div className="font-semibold text-lg">📜 {law.description}</div>
              <div>🎭 Proposed by: {law.proposingPartyName}</div>
              <div>📅 Date Proposed: {law.dateProposed}</div>

              <div className="mt-2 flex gap-2">
                <button
                  onClick={() => handleGenerateVotes(law.id)}
                  disabled={loadingLawId === law.id}
                  className="px-3 py-1 bg-blue-500 text-white rounded hover:bg-blue-600 transition"
                >
                  {loadingLawId === law.id ? "Generating..." : "⚙️ Generate Votes"}
                </button>

                <button
                  onClick={() => navigate(`/view-votes/${law.id}`)}
                  className="px-3 py-1 bg-green-500 text-white rounded hover:bg-green-600 transition"
                >
                  📊 View Votes
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default ViewLawsPage;
