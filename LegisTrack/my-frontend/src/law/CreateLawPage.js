import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function CreateLawPage() {
  const [description, setDescription] = useState("");
  const [dateProposed, setDateProposed] = useState("");
  const [partyId, setPartyId] = useState("");
  const [parties, setParties] = useState([]);
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/parties", {
        headers: { "X-User-Id": userId },
      })
      .then((response) => setParties(response.data))
      .catch((error) => console.error("Error fetching parties:", error));
  }, [userId]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const lawData = {
      description,
      dateProposed,
      proposingPartyId: partyId,
    };

    try {
      const response = await axios.post("http://localhost:8080/api/laws", lawData, {
        headers: {
          "X-User-Id": userId,
          "Content-Type": "application/json",
        },
      });

      if (response.status === 201) {
        navigate("/view-laws", { state: { lawCreated: true } });
      }
    } catch (error) {
      console.error("Error creating law:", error);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-100 to-blue-200 py-10">
      <div className="max-w-3xl mx-auto bg-white shadow-xl rounded-xl p-8">
        <h2 className="text-3xl font-bold text-center text-blue-700 mb-6">Create New Law</h2>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="block text-gray-700 font-semibold mb-2">Description:</label>
            <input
              type="text"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              required
              className="w-full border border-gray-300 p-2 rounded"
            />
          </div>

          <div>
            <label className="block text-gray-700 font-semibold mb-2">Date Proposed:</label>
            <input
              type="date"
              value={dateProposed}
              onChange={(e) => setDateProposed(e.target.value)}
              required
              className="w-full border border-gray-300 p-2 rounded"
            />
          </div>

          <div>
            <label className="block text-gray-700 font-semibold mb-2">Proposing Party:</label>
            <select
              value={partyId}
              onChange={(e) => setPartyId(e.target.value)}
              required
              className="w-full border border-gray-300 p-2 rounded"
            >
              <option value="">Select a Party</option>
              {parties.map((party) => (
                <option key={party.id} value={party.id}>
                  {party.name}
                </option>
              ))}
            </select>
          </div>

          <div className="flex justify-center gap-4">
            <button
              type="submit"
              className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
            >
              Create Law
            </button>

            <button
              type="button"
              onClick={() => navigate("/simulator")}
              className="px-6 py-2 bg-gray-400 text-white rounded-lg hover:bg-gray-500 transition"
            >
              Back to Party List
            </button>
            </div>
        </form>
      </div>
    </div>
  );
}

export default CreateLawPage;